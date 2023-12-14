open Core
open Printf

let lines = In_channel.read_lines "./input/day02.txt"

(** let read_input = List.map lines (fun line -> Str.global_replace (Str.regexp " ") "" line) *)

type result = {
    colour : string;
    count: int;
}

let print_result (result: result) = print_endline ("{ " ^ result.colour ^ ": " ^ (string_of_int result.count) ^ " }")

type display = {
    blue : int;
    red : int;
    green : int;
}

type game = {
    number : int;
    displays : display list;
}

let parse_result single_result = 
    {
        colour = List.nth_exn single_result 1;
        count = int_of_string (List.nth_exn single_result 0);
    } 

let get_colour (results : result list) (colour: string): int =  match List.nth (List.filter results (fun result -> (String.equal result.colour colour))) 0 with
    | None -> 0
    | Some result -> result.count

let parse_display (results: result list) = {
        blue = get_colour results "blue";
        red = get_colour results "red";
        green = get_colour results "green";

    }

let print_display (display: display) = print_endline (
    "{ blue: " ^ (string_of_int display.blue) ^
    ", red: " ^ (string_of_int display.red) ^ 
    ", green: " ^ (string_of_int display.green) ^ " }")

let print_game (game: game) = print_endline (
    "{ number: " ^ (string_of_int game.number) ^
    ", displays: { ");
    List.iter game.displays print_display;
    print_endline "} }"


let read_input = List.map lines (fun line -> 
        let ln = String.split_on_chars line [':'] in

        let game = List.nth_exn ln 0 in
        let game_number = int_of_string (List.nth_exn (String.split_on_chars game [' ']) 1) in

        let tail = List.nth_exn ln 1 in
        (** print_endline ("tail:\n" ^ tail); *)

        let split_tail = String.split_on_chars tail [';'] in

        let split_split_tail = List.map split_tail (fun x -> String.split_on_chars x [',']) in

        let split_split_split_tail = List.map split_split_tail (fun x -> List.map x (fun y -> String.split_on_chars (Stdlib.String.trim y) [' '])) in

        let results = List.map split_split_split_tail (fun x -> List.map x (fun y -> parse_result y)) in

        let displays = List.map results parse_display in



        let game = {
            number = game_number;
            displays = displays
        } in
        game
    )


let print xs = List.iter xs (fun x -> printf "[%s]," x)

let check_display (display : display) (limits : display) = 
    (display.green <= limits.green) &&
    (display.blue <= limits.blue) &&
    (display.red <= limits.red)

let is_possible (game : game) (limits : display): bool = List.fold game.displays ~init:true ~f:(fun acc display -> acc && (check_display display limits))

let max_green (displays : display list) = List.fold displays ~init:0 ~f:(fun acc a -> if a.green > acc then a.green else acc)
let max_blue (displays : display list) = List.fold displays ~init:0 ~f:(fun acc a -> if a.blue > acc then a.blue else acc)
let max_red (displays : display list) = List.fold displays ~init:0 ~f:(fun acc a -> if a.red > acc then a.red else acc)

let power (display : display) = display.green * display.red * display.blue

let rec sum ls = match ls with
    | [] -> 0
    | x :: xs -> x + sum xs

let part1 = 
    let games = read_input in
    (**List.iter inp (fun line -> print line; printf "\n"); *)
    List.iter games print_game;

    let limits = {
        red = 12;
        green = 13;
        blue = 14;
    } in
    print_endline "\nlimits:";
    print_display limits;

    let possibles = List.map games (fun game -> (game.number, (is_possible game limits))) in
    List.iter possibles (fun (a, b) -> print_endline (string_of_int(a) ^ (string_of_bool b)));

    let actually_possible = List.filter_map possibles (fun (a, b) -> if b then Some a else None) in
    List.iter actually_possible (fun x -> print_endline (string_of_int x));
    print_endline (string_of_int (sum actually_possible));

    let maxes = List.map games (fun game -> {
        green = max_green game.displays;
        red = max_red game.displays;
        blue = max_blue game.displays;
    }) in
    List.iter maxes (fun x -> print_display x);

    let powers = List.map maxes power in
    List.iter powers (fun x -> print_endline (string_of_int x));


    print_endline (string_of_int (sum powers))


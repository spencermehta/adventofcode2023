open Core

let lines = In_channel.read_lines "./input/day03.txt"

type coord = {
    x : int;
    y : int;
}

type number = {
    number: int;
    start : coord;
    finish : coord;
}

let print_number (number : number) = Printf.printf "{%d: (%d, %d) -> (%d, %d)}" number.number number.start.x number.start.y number.finish.x number.finish.y

let not_symbols = [
    '0'; '1'; '2'; '3'; '4'; '5'; '6'; '7'; '8'; '9'; '.'
]

let digits = [
    '0'; '1'; '2'; '3'; '4'; '5'; '6'; '7'; '8'; '9'
]

let get_coord (coord : coord) lines : char = String.get (List.nth_exn lines coord.y) coord.x

let is_symbol (c : char) : bool = not (List.exists not_symbols ~f:(fun character -> Char.equal character c))

let is_digit (c : char) : bool = List.exists digits ~f:(fun character -> Char.equal character c)

let explode s = List.init (String.length s) (String.get s)

let get_raw_numbers (line : string) = (String.split_on_chars line ['.';'*';'$';'#';'+';'&';'%';'/';'=';'@';'-'])

let sanitise_numbers (line : string list) : string list = List.map line (fun x -> Str.global_replace (Str.regexp "[^0-9.]") "" x)

let get_numbers (line : string) = List.filter (sanitise_numbers (get_raw_numbers line)) (fun x -> String.length x <> 0)

let pos_of_substr (substr : string) (line : string) = String.substr_index_all ~may_overlap:false ~pattern:substr line

let get_positions numbers lines : number list list list = List.mapi numbers (fun i row -> 
    List.map row (fun num -> 
        let starts = pos_of_substr num (List.nth_exn lines i) in
        List.map starts (fun start -> 
            {
                number = int_of_string num;
                start = { x = start; y = i };
                finish = { x = start + (String.length num); y = i };
            }
    )))

let get_x_range (number : number) (x_size : int) = 
    List.filter 
        (List.range ~start:`inclusive ~stop:`inclusive (number.start.x-1) (number.finish.x))
            (fun num -> (num >= 0) && (num < x_size))

let get_y_range (number : number) (y_size : int) = 
    List.filter 
        (List.range ~start:`inclusive ~stop:`inclusive (number.start.y-1) (number.finish.y+1))
            (fun num -> (num >= 0) && (num < y_size))

let check_coords (x_range : int list) (y_range : int list) (lines : string list) =
    let bools = List.map x_range (fun x -> List.map y_range (fun y -> 
        let c = get_coord { x = x; y = y } lines in
        is_symbol c
    )) in
    List.exists bools (fun x -> List.exists x (fun y -> y))
    
let check_number number x_length y_length lines = 
    let x_range = get_x_range number x_length in
    let y_range = get_y_range number y_length in
    print_string "Number: "; print_number number; print_string " | ";
    List.iter x_range (fun y -> print_string ((string_of_int y) ^ ";"));
    print_string ", ";
    List.iter y_range (fun y -> print_string (string_of_int y));
    let check = check_coords x_range y_range lines in
    Printf.printf " | %b\n" check;
    check

let rec sum ls = match ls with
    | [] -> 0
    | x :: xs -> x + sum xs

let part1 =
    List.iter lines print_endline;
    print_endline "\n";

    let dup_numbers = List.map lines get_numbers in
    let numbers = List.map dup_numbers (fun row -> List.dedup_and_sort row ~compare:(fun a b -> String.compare a b)) in
    let zip = List.zip_exn (List.map dup_numbers List.length) (List.map numbers List.length) in
    let r = List.map zip (fun (a, b) -> a <> b) in
    List.iteri r (fun i b -> Printf.printf "%d: %b\n" i b);
    List.iter numbers (fun x -> List.iter x (fun y -> Printf.printf "%s, " y); print_endline "");

    let positions = get_positions numbers lines in
    let flat_positions : number list = List.concat (List.concat positions) in
    (**
    List.iter flat_positions print_number;
    print_endline "";
    *)

    let x_length = (String.length (List.nth_exn lines 0)) in
    let y_length = List.length lines in

    let parts = List.filter flat_positions (fun pos -> check_number pos x_length y_length lines) in
    (**
    List.iter parts print_number;
    *)

    let nums = List.map parts (fun number -> number.number) in
    let sum = sum nums in
    Printf.printf "\n\n%d\n" sum


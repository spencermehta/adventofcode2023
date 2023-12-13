open Core
open Printf

let lines = In_channel.read_lines "./pt1.txt"

let rec print_list list = match list with
    | [] -> ()
    | x :: xs -> print_endline x; print_list xs

let rec print_char_list list = match list with
    | [] -> ()
    | x :: xs -> printf "%c" x; print_char_list xs

let rec print_int_list list = match list with
    | [] -> ()
    | x :: xs -> printf "%d\n" x; print_int_list xs

let first xs = match xs with 
    | [] -> failwith "firstk"
    | x :: _ -> x

let digit_mapping = [
        ("^one", "1");
        ("^two", "2");
        ("^three", "3");
        ("^four", "4");
        ("^five", "5");
        ("^six", "6");
        ("^seven", "7");
        ("^eight", "8");
        ("^nine", "9");
    ]

let digit_mapping_end = [
        ("one$", "1");
        ("two$", "2");
        ("three$", "3");
        ("four$", "4");
        ("five$", "5");
        ("six$", "6");
        ("seven$", "7");
        ("eight$", "8");
        ("nine$", "9");
    ]

let replace_string_digit_at_pos_single_mapping line (digit_string, actual_digit) =
    Str.global_replace (Str.regexp digit_string) actual_digit line

let replace_string_digit_at_pos line mappings = List.fold mappings ~init:line ~f:(fun acc m -> replace_string_digit_at_pos_single_mapping acc m)

let rec replace_string_digit line mappings = 
    if String.length(line) = 0 then "" else
    let res = replace_string_digit_at_pos line mappings in
        (String.sub res 0 1) ^ (replace_string_digit (String.sub res 1 (String.length(res) - 1)) mappings)

let rec replace_string_digit_end line mappings = 
    if String.length(line) = 0 then "" else
    let res = replace_string_digit_at_pos line mappings in
    let len = String.length(res) in
         (replace_string_digit_end (String.sub res 0 (String.length(res) - 1)) mappings) ^ (String.sub res (len - 1) 1)

let explode s = List.init (String.length s) (String.get s)

let get x = match x with 
    | Some x -> x
    | None -> raise (Invalid_argument "Invalid")

let rec get_numbers char_list = match char_list with 
    | [] -> []
    | x :: xs -> if is_none (Char.get_digit x) then get_numbers xs else get (Char.get_digit x) :: get_numbers xs 

let concat_ints ints = String.concat (List.map ints (fun x -> string_of_int x))

let rec last ints = match ints with 
    | [] -> failwith "last"
    | [x] -> x
    | _ :: xs -> last xs

let first_and_last ints = first ints :: [last ints]

let rec sum ls = match ls with 
    | [] -> 0
    | x :: xs -> x + sum xs

let get_concat_of_line line = int_of_string (concat_ints (first_and_last (get_numbers (explode line))))

let day01 = 
    (**print_list lines;*)
    (** printf "%s" (replace_string_words_with_numbers (first lines));*)
    let forward = (List.map lines (fun line -> replace_string_digit line digit_mapping)) in
    let backward = (List.map lines (fun line -> replace_string_digit_end line digit_mapping_end)) in
    print_list forward;
    print_list backward;

    let joined = (List.zip_exn forward backward) in
    let concatted = List.map joined (fun (a, b) -> a ^ b) in
    print_list concatted;


    let int_list = (List.map concatted get_concat_of_line) in
    print_int_list int_list;
    printf "%d" (sum int_list)


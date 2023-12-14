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


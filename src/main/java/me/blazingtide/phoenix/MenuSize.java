package me.blazingtide.phoenix;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MenuSize {

    ROW_1(9),
    ROWS_2(18),
    ROWS_3(9 * 3), //forgot math at this point
    ROWS_4(9 * 4),
    ROWS_5(9 * 5),
    ROWS_6(9 * 6),;

    private int value;

}
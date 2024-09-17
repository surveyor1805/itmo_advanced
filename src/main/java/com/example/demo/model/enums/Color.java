package com.example.demo.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Color {
    YELLOW("Желтый"),
    BLACK("Черный"),
    WHITE("Белый"),
    BLUE("Синий"),
    RED("Красный"),
    GREEN("Зеленый"),
    BROWN("Коричневый");

    private final String description;
}

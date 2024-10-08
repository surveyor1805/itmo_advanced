package com.example.demo.model.dto.request;

import com.example.demo.model.enums.Brand;
import com.example.demo.model.enums.Color;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarInfoReq {
    Brand brand;
    String model;
    Color color;
    Integer year;
    Boolean isNew;
    BigDecimal price;
}

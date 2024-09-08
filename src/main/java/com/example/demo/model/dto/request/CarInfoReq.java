package com.example.demo.model.dto.request;

import com.example.demo.model.enums.Brand;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarInfoReq {
    String licenseNumber;
    String VINnumber;
    String ownerFirstName;
    String ownerLastName;
    Brand brand;
}

package com.example.demo.model.dto.request;

import com.example.demo.model.enums.Brand;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarInfoReq {
    @NotEmpty
    String licenseNumber;
    @NotEmpty
    String VINNumber;
    String ownerFirstName;
    @NotEmpty
    String ownerLastName;
    Brand brand;
}

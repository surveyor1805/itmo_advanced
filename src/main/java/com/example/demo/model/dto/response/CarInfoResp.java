package com.example.demo.model.dto.response;

import com.example.demo.model.dto.request.CarInfoReq;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarInfoResp extends CarInfoReq {
    Long id;
}

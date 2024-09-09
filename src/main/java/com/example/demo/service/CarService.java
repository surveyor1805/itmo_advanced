package com.example.demo.service;

import com.example.demo.model.dto.request.CarInfoReq;
import com.example.demo.model.dto.response.CarInfoResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class CarService {
    public CarInfoResp createCar(CarInfoReq request) {
        return CarInfoResp.builder()
                .licenseNumber(request.getLicenseNumber())
                .ownerFirstName(request.getOwnerFirstName())
                .ownerLastName(request.getOwnerLastName())
                .brand(request.getBrand())
                .VINNumber(request.getVINNumber())
                .id(1L)
                .build();
    }

    public CarInfoResp getCar(Long id) {
        return null;
    }

    public CarInfoResp updateCar(Long id, CarInfoReq request) {
        return CarInfoResp.builder()
                .licenseNumber(request.getLicenseNumber())
                .ownerFirstName(request.getOwnerFirstName())
                .ownerLastName(request.getOwnerLastName())
                .brand(request.getBrand())
                .VINNumber(request.getVINNumber())
                .build();
    }

    public void deleteCar(Long id) {
    }

    public List<CarInfoResp> getAllCars() {
        return Collections.emptyList();
    }
}

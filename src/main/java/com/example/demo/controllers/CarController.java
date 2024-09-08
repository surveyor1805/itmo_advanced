package com.example.demo.controllers;

import com.example.demo.model.dto.request.CarInfoReq;
import com.example.demo.model.dto.response.CarInfoResp;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.example.demo.constants.Constants.CARS;

@RestController
@RequestMapping(CARS)
public class CarController {

    @PostMapping()
    public CarInfoResp createCar(@RequestBody CarInfoReq request) {
        return new CarInfoResp();
    }

    @GetMapping("/{id}")
    public CarInfoResp getCar(@PathVariable Long id) {
        return new CarInfoResp();
    }

    @PutMapping("/{id}")
    public CarInfoResp updateCar(@PathVariable Long id, @RequestBody CarInfoReq request) {
        return new CarInfoResp();
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        throw new RuntimeException("Car deletion error");
    }

    @GetMapping("/all")
    public List<CarInfoResp> getAllCars() {
        return Collections.singletonList(new CarInfoResp());
    }
}

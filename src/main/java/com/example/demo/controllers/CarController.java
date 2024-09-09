package com.example.demo.controllers;

import com.example.demo.model.dto.request.CarInfoReq;
import com.example.demo.model.dto.response.CarInfoResp;
import com.example.demo.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.constants.Constants.CARS;

@Tag(name = "Автомобили")
@RestController
@RequestMapping(CARS)
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping()
    @Operation(summary = "Создать автомобиль")
    public CarInfoResp createCar(@RequestBody CarInfoReq request) {
        return carService.createCar(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить данные об автомобиле по id")
    public CarInfoResp getCar(@PathVariable Long id) {
        return carService.getCar(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные об автомобиле по id")
    public CarInfoResp updateCar(@PathVariable Long id, @RequestBody CarInfoReq request) {
        return carService.updateCar(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить данные об автомобиле по id")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить данные обо всех автомобилях")
    public List<CarInfoResp> getAllCars() {
        return carService.getAllCars();
    }
}

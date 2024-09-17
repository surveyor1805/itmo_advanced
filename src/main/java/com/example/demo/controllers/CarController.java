package com.example.demo.controllers;

import com.example.demo.model.dto.request.CarInfoReq;
import com.example.demo.model.dto.request.CarToUserReq;
import com.example.demo.model.dto.response.CarInfoResp;
import com.example.demo.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Выполнено успешно"),
            @ApiResponse(responseCode = "404", description = "Не найдено"),
            @ApiResponse(responseCode = "401", description = "Не авторизован"),
    })
    @Operation(summary = "Получить данные обо всех автомобилях")
    public Page<CarInfoResp> getAllCars(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer sizePerPage,
                                        @RequestParam(defaultValue = "brand") String sort,
                                        @RequestParam(defaultValue = "ASC") Sort.Direction order,
                                        @RequestParam(required = false) String filter) {
        return carService.getAllCars(page, sizePerPage, sort, order, filter);
    }

    @PostMapping("/carToUser")
    @Operation(summary = "Добавить автомобиль пользователю")
    public void addCarToUser(@RequestBody @Valid CarToUserReq request) {
        carService.addCarToUser(request);
    }

    @GetMapping("/allById/")
    @Operation(summary = "Получить данные обо всех автомобилях пользователя по его id")
    public Page<CarInfoResp> getAllCarsByUserId(@RequestParam Long userId,
                                                @RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer sizePerPage,
                                                @RequestParam(defaultValue = "brand") String sort,
                                                @RequestParam(defaultValue = "ASC") Sort.Direction order) {
        return carService.getAllCarsByUserId(userId, page, sizePerPage, sort, order);
    }
}

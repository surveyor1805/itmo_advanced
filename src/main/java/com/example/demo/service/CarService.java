package com.example.demo.service;

import com.example.demo.exceptions.CustomException;
import com.example.demo.model.db.entity.Car;
import com.example.demo.model.db.entity.User;
import com.example.demo.model.db.repository.CarRepository;
import com.example.demo.model.dto.request.CarInfoReq;
import com.example.demo.model.dto.request.CarToUserReq;
import com.example.demo.model.dto.response.CarInfoResp;
import com.example.demo.model.enums.CarStatus;
import com.example.demo.model.enums.UserStatus;
import com.example.demo.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class CarService {

    private final ObjectMapper objectMapper;
    private final CarRepository carRepository;
    private final UserService userService;

    public CarInfoResp createCar(CarInfoReq request) {
        Car car = objectMapper.convertValue(request, Car.class);
        car.setCreatedAt(LocalDateTime.now());
        car.setStatus(CarStatus.CREATED);

        Car savedCar = carRepository.save(car);

        return objectMapper.convertValue(savedCar, CarInfoResp.class);
    }

    private Car getCarFromDB(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new CustomException(String.format("Car with id %d is not found", id), HttpStatus.NOT_FOUND));
    }

    public CarInfoResp getCar(Long id) {
        return objectMapper.convertValue(getCarFromDB(id), CarInfoResp.class);
    }

    public CarInfoResp updateCar(Long id, CarInfoReq request) {
        Car car = getCarFromDB(id);

        car.setBrand(request.getBrand() == null ? car.getBrand() : request.getBrand());
        car.setModel(request.getModel() == null ? car.getModel() : request.getModel());
        car.setColor(request.getColor() == null ? car.getColor() : request.getColor());
        car.setYear(request.getYear() == null ? car.getYear() : request.getYear());
        car.setIsNew(request.getIsNew() == null ? car.getIsNew() : request.getIsNew());
        car.setPrice(request.getPrice() == null ? car.getPrice() : request.getPrice());

        car.setUpdatedAt(LocalDateTime.now());
        car.setStatus(CarStatus.UPDATED);

        Car savedCar = carRepository.save(car);

        return objectMapper.convertValue(savedCar, CarInfoResp.class);
    }

    public void deleteCar(Long id) {
        Car car = getCarFromDB(id);
        car.setUpdatedAt(LocalDateTime.now());
        car.setStatus(CarStatus.DELETED);
        carRepository.save(car);
    }

    public Page<CarInfoResp> getAllCars(Integer page, Integer sizePerPage, String sort, Sort.Direction order, String filter) {
        Pageable pageRequest = PaginationUtil.getPageRequest(page, sizePerPage, sort, order);

        Page<Car> allByStatusNot;
        if (filter == null) {
            allByStatusNot = carRepository.findAllByStatusNot(pageRequest, CarStatus.DELETED);
        } else {
            allByStatusNot = carRepository.findAllByStatusNotFiltered(pageRequest, CarStatus.DELETED, filter.toLowerCase());
        }

        List<CarInfoResp> content = allByStatusNot.getContent().stream()
                .map(car -> objectMapper.convertValue(car, CarInfoResp.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageRequest, allByStatusNot.getTotalElements());

//        return carRepository.findAll().stream()
//                .map(car -> objectMapper.convertValue(car, CarInfoResp.class))
//                .collect(Collectors.toList());
    }

    public Page<CarInfoResp> getAllCarsByUserId(Long userId, Integer page, Integer sizePerPage, String sort, Sort.Direction order) {
        User user = userService.getUserFromDB(userId);

        if (user.getStatus().equals(UserStatus.DELETED)) {
            throw new CustomException(String.format("User Info with id: %d is DELETED from DataBase", userId), HttpStatus.NO_CONTENT);
        }
        Pageable pageRequest = PaginationUtil.getPageRequest(page, sizePerPage, sort, order);

        Page<Car> userCars = carRepository.getUserCars(pageRequest, userId);

        List<CarInfoResp> content = userCars.getContent().stream()
                .map(car -> objectMapper.convertValue(car, CarInfoResp.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageRequest, userCars.getTotalElements());
    }

    public void addCarToUser(@Valid CarToUserReq request) {
        Car car = getCarFromDB(request.getCarId());
        User user = userService.getUserFromDB(request.getUserId());

        user.getCars().add(car);
        userService.updateUserData(user);

        car.setUser(user);
        carRepository.save(car);
    }

    public Car getSomeCar() {
        return carRepository.getSomeCar(true);
    }
}

package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.Car;
import com.example.demo.model.enums.Brand;
import com.example.demo.model.enums.CarStatus;
import com.example.demo.model.enums.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{
    Optional<Car> findByBrandAndStatus(Brand brand, CarStatus status);

    Optional<Car> findByBrandAndStatusAndColor(Brand brand, CarStatus status, Color color);

    List<Car> findAllByModel(String model);

    @Query(nativeQuery = true, value = "select * from cars where id > 2 limit 1")
    Car getCar();

    @Query(nativeQuery = true, value = "select * from cars where is_new = :isNew limit 1")
    Car getSomeCar(@Param("isNew") boolean isNew);

    @Query("select c from Car c where c.status <> 'DELETED'")
    List<Car> findAllNotDeletedCars();

    @Query("select c from Car c where c.status <> :status")
    List<Car> findAllNotInStatus(@Param("status") CarStatus status);

    @Query("select c from Car c where c.status <> :status")
    Page<Car> findAllByStatusNot(Pageable request, CarStatus status);

    @Query("select c from Car c where c.status <> :status and lower(c.model) like %:filter%")  //like %:filter% if string
    Page<Car> findAllByStatusNotFiltered(Pageable request, CarStatus status, @Param("filter") String filter);

    @Query("select c from Car c where c.user.id =:userId")
    Page<Car> getUserCars(Pageable pageRequest, @Param("userId") Long userId);
}

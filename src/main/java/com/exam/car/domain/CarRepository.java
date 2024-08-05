package com.exam.car.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByLengthAndWeightAndVelocityAndColor(
            double length,double weight, double velocity, String color);

    @Query("SELECT c FROM Car c WHERE " +
            "(:length IS NULL OR c.length = :length) AND " +
            "(:weight IS NULL OR c.weight = :weight) AND " +
            "(:velocity IS NULL OR c.velocity = :velocity) AND " +
            "(:color IS NULL OR c.color = :color)")
    List<Car> findByCriteria(
            @Param("length") Optional<Double> length,
            @Param("weight") Optional<Double> weight,
            @Param("velocity") Optional<Double> velocity,
            @Param("color") Optional<String> color);
}


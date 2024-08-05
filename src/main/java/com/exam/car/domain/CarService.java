package com.exam.car.domain;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) { this.carRepository = carRepository;}
//searchCar using stream and filter predicates as criteria at the same time
    public List<Car> searchCars(double length, double weight, double velocity, String color) {
        return carRepository.findAll().stream()
                .filter(car -> car.getLength() == length && car.getWeight() == weight
                        && car.getVelocity() == velocity && car.getColor().equalsIgnoreCase(color))
                .toList();
    }
//add car something to test search
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    public List<Car> findCarsByCriteria(Optional<Double> length, Optional<Double> weight, Optional<Double> velocity, Optional<String> color) {
        return carRepository.findByCriteria(length, weight, velocity, color) ;
    }

}


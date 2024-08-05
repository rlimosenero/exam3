package com.exam.car;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.exam.car.domain.Car;
import com.exam.car.domain.CarRepository;
import com.exam.car.domain.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchCars() {
        // Setup mock data
        Car car1 = new Car(1L, 4.5, 1000, 200, "Red");
        Car car2 = new Car(2L, 4.5, 1000, 200, "Blue");
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);

        when(carRepository.findAll()).thenReturn(cars);

        // Test the searchCars method
        List<Car> result = carService.searchCars(4.5, 1000, 200, "Red");

        assertEquals(1, result.size());
        assertEquals("Red", result.get(0).getColor());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    public void testAddCar() {
        Car car = new Car(3L, 4.6, 1100, 210, "Green");

        when(carRepository.save(any(Car.class))).thenReturn(car);

        // Test the addCar method
        Car result = carService.addCar(car);

        assertEquals("Green", result.getColor());
        assertEquals(4.6, result.getLength());
        assertEquals(1100, result.getWeight());
        assertEquals(210, result.getVelocity());
        assertEquals(3L, result.getId());
    }

    @Test
    public void testFindCarsByCriteria() {
        // Setup mock data
        Car car1 = new Car(1L, 4.5, 1000.00, 200.00, "Red");
        Car car2 = new Car(2L, 4.5, 1000.00 ,200.00, "Blue");
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);

        when(carRepository.findByCriteria(any(Optional.class), any(Optional.class), any(Optional.class), any(Optional.class)))
                .thenReturn(cars);

        // Test the findCarsByCriteria method
        List<Car> result = carService.findCarsByCriteria(Optional.of(4.5), Optional.of(1000.00), Optional.of(200.00), Optional.of("Red"));

        assertEquals(2, result.size());
        assertEquals("Red", result.get(0).getColor());
        assertEquals("Blue", result.get(1).getColor());
    }
}

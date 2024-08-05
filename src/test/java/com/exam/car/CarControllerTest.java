package com.exam.car;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import com.exam.car.domain.Car;
import com.exam.car.domain.CarController;
import com.exam.car.domain.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CarController.class)
@ExtendWith(SpringExtension.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    public void testAddCar() throws Exception {
        Car car = new Car(1L, 4.5, 1.8, 150.0, "Red");

        when(carService.addCar(any(Car.class))).thenReturn(car);

        mockMvc.perform(post("/v1/cars/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"length\":4.5,\"weight\":1.8,\"velocity\":150.0,\"color\":\"Red\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.length").value(4.5))
                .andExpect(jsonPath("$.weight").value(1.8))
                .andExpect(jsonPath("$.velocity").value(150.0))
                .andExpect(jsonPath("$.color").value("Red"));
    }

    @Test
    public void testSearchCarsJSON() throws Exception {
        List<Car> cars = Arrays.asList(new Car(1L, 4.5, 1.8, 150.0, "Red"));

        when(carService.searchCars(4.5, 1.8, 150.0, "Red")).thenReturn(cars);

        mockMvc.perform(post("/v1/cars/search")
                        .param("length", "4.5")
                        .param("weight", "1.8")
                        .param("velocity", "150.0")
                        .param("color", "Red")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].length").value(4.5))
                .andExpect(jsonPath("$[0].weight").value(1.8))
                .andExpect(jsonPath("$[0].velocity").value(150.0))
                .andExpect(jsonPath("$[0].color").value("Red"));
    }

    @Test
    public void testSearchCarsXML() throws Exception {
        List<Car> cars = Arrays.asList(new Car(1L, 4.5, 1.8, 150.0, "Red"));

        when(carService.searchCars(4.5, 1.8, 150.0, "Red")).thenReturn(cars);

        mockMvc.perform(post("/v1/cars/search")
                        .param("length", "4.5")
                        .param("weight", "1.8")
                        .param("velocity", "150.0")
                        .param("color", "Red")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"cars.xml\""))
                .andExpect(xpath("/Cars/car[1]/id").string("1"))
                .andExpect(xpath("/Cars/car[1]/length").string("4.5"))
                .andExpect(xpath("/Cars/car[1]/weight").string("1.8"))
                .andExpect(xpath("/Cars/car[1]/velocity").string("150.0"))
                .andExpect(xpath("/Cars/car[1]/color").string("Red"));
    }
}

package com.exam.car.domain;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/add")
    public Car addProperty(@RequestBody Car car) {
        return carService.addCar(car);
    }



    @PostMapping(
            value = "/search",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> searchCars(
            @RequestParam double length,
            @RequestParam double weight,
            @RequestParam double velocity,
            @RequestParam String color,
            @RequestHeader HttpHeaders headers
    ) throws JAXBException {
        List<Car> cars = carService.searchCars(length, weight, velocity, color);
        String acceptHeader = headers.getFirst(HttpHeaders.ACCEPT);
        if (acceptHeader != null && acceptHeader.contains(MediaType.APPLICATION_XML_VALUE)) {
            // Convert list of Car objects to XML
            JAXBContext jaxbContext = JAXBContext.newInstance(Cars.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            Cars carListWrapper = new Cars();
            carListWrapper.setCar(cars);

            StringWriter sw = new StringWriter();
            marshaller.marshal(carListWrapper, sw);
            String xmlString = sw.toString();

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.APPLICATION_XML);
            responseHeaders.setContentDisposition(ContentDisposition.attachment().filename("cars.xml").build());

            return new ResponseEntity<>(xmlString, responseHeaders, HttpStatus.OK);
        } else {
            // Default to JSON
            return ResponseEntity.ok(cars);
        }
    }

}


//    @GetMapping("/cars/search")
//    public ResponseEntity<List<Car>> searchCars(@RequestParam(required = false) double length,
//                                                @RequestParam(required = false)  double weight,
//                                                @RequestParam(required = false)  double velocity,
//                                                @RequestParam(required = false)  String color)
//    {
//        return ResponseEntity.ok(carService.searchCars(length, weight, velocity, color));
//    }
////using post as this may be a sensitive data, for this purpose is not...
//    @PostMapping(
//            value = "/cars/download",
//            produces = {MediaType.APPLICATION_JSON_VALUE,
//                    MediaType.APPLICATION_XML_VALUE}
//    )
//    public ResponseEntity<?> getCar( @RequestHeader String contenType) throws JAXBException {
//
//
//
//        if (contenType.contains(MediaType.APPLICATION_XML_VALUE)) {
//            // Convert Car object to XML
//            JAXBContext jaxbContext = JAXBContext.newInstance(Car.class);
//            Marshaller marshaller = jaxbContext.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//
//            StringWriter sw = new StringWriter();
//            if(cars.isPresent());
//            marshaller.marshal(cars.get(), sw);
//            String xmlString = sw.toString();
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_XML);
//            headers.setContentDispositionFormData("attachment", "car.xml");
//
//            return new ResponseEntity<>(xmlString, headers, HttpStatus.OK);
//        } else {
//            // Default to JSON
//            return ResponseEntity.ok(cars);
//        }
//    }
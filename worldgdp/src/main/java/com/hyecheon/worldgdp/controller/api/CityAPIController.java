package com.hyecheon.worldgdp.controller.api;

import com.hyecheon.worldgdp.dao.CityDAO;
import com.hyecheon.worldgdp.model.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/cities")
public class CityAPIController {

    @Autowired
    CityDAO cityDao;

    @GetMapping("/{countryCode}")
    public ResponseEntity<?> getCities(@PathVariable String countryCode,
                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        try {
            return new ResponseEntity<>(cityDao.getCities(countryCode, pageNo),
                    HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error while getting cities for country: {} {}", countryCode, ex);
            return new ResponseEntity<>("Error while getting cities", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/{countryCode}")
    public ResponseEntity<?> addCity(@PathVariable String countryCode, @Valid @RequestBody City city) {
        try {
            cityDao.addCity(countryCode, city);
            return new ResponseEntity<>(city, HttpStatus.CREATED);
        } catch (Exception ex) {
            log.error("Error while adding city: {} to country: {} {}", city, countryCode, ex);
            return new ResponseEntity<>("Error while adding city",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<?> deleteCity(@PathVariable Long cityId) {
        try {
            cityDao.deleteCity(cityId);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            log.error("Error occurred while deleting city : {}{}", cityId, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting the city: " + cityId);
        }
    }

}

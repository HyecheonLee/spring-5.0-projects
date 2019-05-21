package com.hyecheon.worldgdp.controller.view;

import com.hyecheon.worldgdp.dao.CityDAO;
import com.hyecheon.worldgdp.dao.CountryDAO;
import com.hyecheon.worldgdp.dao.LookupDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/")
public class ViewController {

    @Autowired
    CountryDAO countryDao;
    @Autowired
    LookupDAO lookupDao;
    @Autowired
    CityDAO cityDao;

    @GetMapping({"/countries", "/"})
    public String countries(Model model, @RequestParam Map<String, Object> params) {
        model.addAttribute("continents", lookupDao.getContinents());
        model.addAttribute("regions", lookupDao.getRegions());
        model.addAttribute("countries", countryDao.getCountries(params));
        model.addAttribute("count", countryDao.getCountriesCount(params));
        return "countries";
    }

    @GetMapping("/countries/{code}")
    public String countryDetail(@PathVariable String code, Model model) {
        //Logic to Populate the country detail in model
        return "country";
    }

    @GetMapping("/countries/{code}/form")
    public String editCountry(@PathVariable String code, Model model) {
        //Logic to call CountryDAO to update the country
        return "country-form";
    }

}
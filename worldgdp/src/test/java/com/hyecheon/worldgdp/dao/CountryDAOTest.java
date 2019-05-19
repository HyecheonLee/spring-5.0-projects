package com.hyecheon.worldgdp.dao;

import com.hyecheon.worldgdp.model.Country;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryDAOTest {
    @Autowired
    CountryDAO countryDao;
    @Autowired
    @Qualifier("testTemplate")
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Before
    public void setUp() throws Exception {
        countryDao.setNamedParamJdbcTemplate(namedParameterJdbcTemplate);
    }

    @Test
    public void testGetCountries() {
        final var countries = countryDao.getCountries(new HashMap<>());
        assertThat(countries).hasSize(20);
    }

    @Test
    public void testGetCountriesSearchByName() {
        final var params = new HashMap<String, Object>();
        params.put("search", "Aruba");
        final var countries = countryDao.getCountries(params);
        assertThat(countries).hasSize(1);
    }

    @Test
    public void testGetCountries_searchByContinent() {
        var params = new HashMap<String, Object>();
        params.put("continent", "Asia");
        List<Country> countries = countryDao.getCountries(params);

        assertThat(countries).hasSize(20);
    }

    @Test
    public void testGetCountryDetail() {
        Country c = countryDao.getCountryDetail("IND");
        assertThat(c).isNotNull();
        assertThat(c.toString()).isEqualTo("Country(code=IND, name=India, "
                + "continent=Asia, region=Southern and Central Asia, "
                + "surfaceArea=3287263.0, indepYear=1947, population=1013662000, "
                + "lifeExpectancy=62.5, gnp=447114.0, localName=Bharat/India, "
                + "governmentForm=Federal Republic, headOfState=Kocheril Raman Narayanan, "
                + "capital=City(id=1109, name=New Delhi, countryCode=null, "
                + "country=null, district=null, population=null), code2=IN)");
    }

    @Test
    public void testEditCountryDetail() {
        Country c = countryDao.getCountryDetail("IND");
        c.setHeadOfState("Ram Nath Kovind");
        c.setPopulation(1324171354l);
        countryDao.editCountryDetail("IND", c);

        c = countryDao.getCountryDetail("IND");
        assertThat(c.getHeadOfState()).isEqualTo("Ram Nath Kovind");
        assertThat(c.getPopulation()).isEqualTo(1324171354l);
    }

    @Test
    public void testGetCountriesCount() {
        Integer count = countryDao.getCountriesCount(Collections.EMPTY_MAP);
        assertThat(count).isEqualTo(239);
    }
}
package com.hyecheon.worldgdp.dao;


import com.hyecheon.worldgdp.dao.mapper.CityRowMapper;
import com.hyecheon.worldgdp.model.City;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Setter
public class CityDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbcTemplate;
    private static final Integer PAGE_SIZE = 20;

    public List<City> getCities(String countryCode, Integer pageNo) {
        var params = new HashMap<String, Object>();
        params.put("code", countryCode);
        if (pageNo != null) {
            Integer offset = (pageNo - 1) * PAGE_SIZE;
            params.put("offset", offset);
            params.put("size", PAGE_SIZE);
        }

        return namedParamJdbcTemplate.query("SELECT "
                        + " id, name, countrycode country_code, district, population "
                        + " FROM city WHERE countrycode = :code"
                        + " ORDER BY Population DESC"
                        + ((pageNo != null) ? " LIMIT :offset , :size " : ""),
                params, new CityRowMapper());
    }

    public List<City> getCities(String countryCode) {
        return getCities(countryCode, null);
    }

    public City getCityDetail(Long cityId) {
        var params = new HashMap<String, Object>();
        params.put("id", cityId);
        return namedParamJdbcTemplate.queryForObject("SELECT id, "
                        + " name, countrycode country_code, "
                        + " district, population "
                        + " FROM city WHERE id = :id",
                params, new CityRowMapper());
    }

    public Long addCity(String countryCode, City city) {
        SqlParameterSource paramSource = new MapSqlParameterSource(getMapForCity(countryCode, city));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParamJdbcTemplate.update("INSERT INTO city("
                        + " name, countrycode, "
                        + " district, population) "
                        + " VALUES (:name, :country_code, "
                        + " :district, :population )",
                paramSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private Map<String, Object> getMapForCity(String countryCode, City city) {
        var map = new HashMap<String, Object>();
        map.put("name", city.getName());
        map.put("country_code", countryCode);
        map.put("district", city.getDistrict());
        map.put("population", city.getPopulation());
        return map;
    }

    public void deleteCity(Long cityId) {
        var params = new HashMap<String, Object>();
        params.put("id", cityId);
        namedParamJdbcTemplate.update("DELETE FROM city WHERE id = :id", params);
    }
}

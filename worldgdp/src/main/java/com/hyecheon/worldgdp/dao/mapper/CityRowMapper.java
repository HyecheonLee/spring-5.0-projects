package com.hyecheon.worldgdp.dao.mapper;

import com.hyecheon.worldgdp.model.City;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CityRowMapper implements RowMapper<City> {
    @Override
    public City mapRow(ResultSet rs, int i) throws SQLException {
        final var city = new City();
        city.setCountryCode(rs.getString("country_code"));
        city.setDistrict(rs.getString("district"));
        city.setId(rs.getLong("id"));
        city.setName(rs.getString("name"));
        city.setPopulation(rs.getLong("population"));
        return city;
    }
}

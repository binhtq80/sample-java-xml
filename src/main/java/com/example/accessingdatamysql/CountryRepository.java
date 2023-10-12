package com.example.accessingdatamysql;


import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
//import java.util.Iterator;

//import com.example.accessingdatamysql.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import io.spring.guides.gs_producing_web_service.Country;
//import io.spring.guides.gs_producing_web_service.Currency;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class CountryRepository {
	private static final Map<String, Country> countries = new HashMap<>();
    //private UserRepository userRepository;

	@PostConstruct
	public void initData() {
		String sqlSelectAllPersons = "SELECT * FROM country";
		String connectionUrl = "jdbc:mysql://database-1.cgj943tbq0gj.ap-southeast-1.rds.amazonaws.com:3306/test_database";

		try (Connection conn = DriverManager.getConnection(connectionUrl, "admin", "Time4fun1!"); 
				PreparedStatement ps = conn.prepareStatement(sqlSelectAllPersons); 
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					String name = rs.getString("name");
					String capital = rs.getString("capital");
					String currency = rs.getString("currency");
					int poppulation = rs.getInt("poppulation");


					// do something with the extracted data...
					Country country = new Country();
					country.setName(name);
					country.setCapital(capital);
					country.setCurrency(currency);
					country.setPopulation(poppulation);
					countries.put(name, country);

					//System.out.println(name);

				}
		} catch (SQLException e) {
			// handle the exception
			System.out.print(e);
			
		}



		
	}

	public Country findCountry(String name) {
		Assert.notNull(name, "The country's name must not be null");
		return countries.get(name);
	}
}
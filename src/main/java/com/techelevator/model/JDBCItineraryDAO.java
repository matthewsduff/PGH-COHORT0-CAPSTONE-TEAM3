package com.techelevator.model;

import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;


import com.techelevator.security.PasswordHasher;



@Component
public class JDBCItineraryDAO implements ItineraryDAO {

	private JdbcTemplate jdbcTemplate;
	private PasswordHasher hashMaster;

	@Autowired
	public JDBCItineraryDAO(DataSource dataSource, PasswordHasher hashMaster) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.hashMaster = hashMaster;
	}
	
	
	@Override
	public void addItinerary(Itinerary NewItinerary) {
			String sqlInsertNewItinerary = "INSERT INTO itineraries(itinerary_id,user_id,google_id_one,google_id_two,google_id_three,google_id_four,google_id_five,visible) "
					+ "VALUES (?,?,?,?,?,?,?) RETURNING itinerary_id;";
		
			int id = jdbcTemplate.queryForObject(sqlInsertNewItinerary, Integer.class, NewItinerary.getUser_id(),
					NewItinerary.getGoogle_id_one(), NewItinerary.getGoogle_id_two(), NewItinerary.getGoogle_id_three(), NewItinerary.getGoogle_id_four(),
					NewItinerary.getGoogle_id_five(), NewItinerary.isVisible());
			NewItinerary.setItinerary_id(id);
		
	}

	@Override
	public void deleteItinerary(int itinerary_id) {
		String sqlSearchByItineraryId ="UPDATE itineraries set visible = 'false' WHERE itinerary_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchByItineraryId, itinerary_id);
	}
	
	

	@Override
	public List<Itinerary> getItinerarysByUser(int user_id) {

		   LinkedList<Itinerary> itineraryByUser = new LinkedList<>();
		   String sqlSearchByUsername ="SELECT * FROM itineraries WHERE user_id = ?;";
		    SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchByUsername, user_id);
		    while (results.next()) {
		   
		    	itineraryByUser.add(mapRowToItinerary(results));

		    }
		    for(int i= 0; i < itineraryByUser.size(); i++) {
		   
		    }
		    return itineraryByUser;
		}
	

	@Override
	public List<Itinerary> getItineraryforGuests() {
		 LinkedList<Itinerary> itineraryByUser = new LinkedList<>();
		   String sqlSearchByUsername ="SELECT * FROM itineraries WHERE user_id = *;";
		    SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchByUsername);
		    while (results.next()) {
		       
		    	itineraryByUser.add(mapRowToItinerary(results));

		    }
		    for(int i= 0; i < itineraryByUser.size(); i++) {
		   
		    }
		    return itineraryByUser;
	}

	
	private Itinerary mapRowToItinerary(SqlRowSet results) {

		Itinerary theItinerary = new Itinerary();
		
		theItinerary.setUser_id(results.getInt("user_id"));
		theItinerary.setGoogle_id_one(results.getString("google_id_one"));
		theItinerary.setGoogle_id_two(results.getString("google_id_two"));
		theItinerary.setGoogle_id_three(results.getString("google_id_three"));
		theItinerary.setGoogle_id_four(results.getString("google_id_four"));
		theItinerary.setGoogle_id_five(results.getString("google_id_five"));
		theItinerary.setVisible(results.getBoolean("visible"));

		return theItinerary;
	}
	
	
	
}

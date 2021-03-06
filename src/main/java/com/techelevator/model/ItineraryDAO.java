package com.techelevator.model;

import java.util.List;

public interface ItineraryDAO {

	public void addItinerary(Itinerary NewItinerary);
	public void deleteItinerary(int itinerary_id);
	//public Itinerary getItineraryById(int id, int itinerary_id);
	public List <Itinerary> getItinerarysByUser(int user_id);
	public List <Itinerary> getItineraryforGuests();
	
}

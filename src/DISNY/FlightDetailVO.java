package DISNY;

//Class to represent flight details
public class FlightDetailVO {

	// Instance variables to store flight details
	private String flightDepartureTime, flightDestiationTime, flightOriginShort, flightDestinationShort, flightDuration;
	private String flightNumberOfStops, flightAirlineName, flightWebsite;
	private int price;
	
	// Constructor to initialize flight details
	public FlightDetailVO(String flghtDepartureTime, String flghtDestiationTime, String flghtOriginShort, String flghtDestinationShort, String flghtDuration,
			String flghtNumberOfStops, String flghtAirlineName, String flghtWebsite, int flghtPrice) {
		this.flightDepartureTime = flghtDepartureTime;
		this.flightDestiationTime = flghtDestiationTime;
		this.flightOriginShort = flghtOriginShort;
		this.flightDestinationShort = flghtDestinationShort;
		this.flightDuration = flghtDuration;
		this.flightNumberOfStops = flghtNumberOfStops;
		this.flightAirlineName = flghtAirlineName;
		this.flightWebsite = flghtWebsite;
		this.price = flghtPrice;
	}
	
	// Getter method for departure time
	public String getDepartureTime() {
		return flightDepartureTime;	
	}
	
	// Getter method for destination time
	public String getDestinationTime() {
		return flightDestiationTime;	
	}
	
	// Getter method for origin airport code
	public String getOriginShort() {
		return flightOriginShort;	
	}
	
	// Getter method for destination airport code
	public String getDestinationShort() {
		return flightDestinationShort;	
	}
	
	// Getter method for flight duration
	public String getDuration() {
		return flightDuration;	
	}
	
	// Getter method for number of stops
	public String getNumberOfStops() {
		return flightNumberOfStops;	
	}
	
	// Getter method for airline name
	public String getAirlineName() {
		return flightAirlineName;	
	}
	
	// Getter method for website name
	public String getWebsite() {
		return flightWebsite;	
	}
	
	// Getter method for flight price
	public int getPrice() {
		return price;	
	}
	
}


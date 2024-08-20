package DISNY;

import java.io.File;

import org.jsoup.Jsoup;

public class HTML_Parser_Booking {

	// Method to parse booking information from a HTML file
	public FlightDetailVO[] Booking_Parser(String orgn, String dstntn, int dt_mnth_strng, int dt_yr, int dt_day_inpt,
			String fldrPath) {
		// Formatting month and day to ensure consistency in file naming
		String dt_mnth = "" + dt_mnth_strng;
		if (dt_mnth_strng < 10) {
			dt_mnth = "0" + dt_mnth_strng;
		}

		String dt_dy = null;

		if (dt_day_inpt < 10) {
			dt_dy = "0" + dt_day_inpt;
		} else {
			dt_dy = "" + dt_day_inpt;
		}

		// Array to hold the best booking flights
		FlightDetailVO[] bestBkngFlights = new FlightDetailVO[3];

		try {
			String pth = fldrPath + "//" + orgn.toLowerCase() + "_to_" + dstntn.toLowerCase() + "_" + dt_dy + "_"
					+ dt_mnth + "_" + dt_yr + "_Booking" + ".html";
			File inpt = new File(pth.toLowerCase());
			// Parsing HTML file using Jsoup
			org.jsoup.nodes.Document docmnt = Jsoup.parse(inpt, "UTF-8");

			// Looping through the flight cards in the HTML document
			for (int i = 0; i < 3; i++) {
				// Selecting flight card element based on index
				org.jsoup.nodes.Element flghtCrd = docmnt.select("#flightcard-" + i).first();

				// Checking if flight card element is null
				if (flghtCrd == null) {
					System.out.println("Error: flightCard is null for index " + i);
					continue;
				}

				// Extracting departure time from flight card
				org.jsoup.nodes.Element deprtureTime = flghtCrd
						.selectFirst("[data-testid=flight_card_segment_departure_time_0]");
				String deprtureTm = deprtureTime.text();

				// Extracting destination time from flight card
				org.jsoup.nodes.Element dstntnTime = flghtCrd
						.selectFirst("div[data-testid=flight_card_segment_destination_time_0]");
				String dstntnTm = dstntnTime.text();

				// Extracting duration from flight card
				org.jsoup.nodes.Element durtnTime = flghtCrd
						.selectFirst("div[data-testid=flight_card_segment_duration_0]");
				String durtn = durtnTime.text();

				// Extracting stops from flight card
				org.jsoup.nodes.Element elmnt = flghtCrd.select("div[data-testid=flight_card_segment_stops_0]").first();
				String stps = elmnt.text();

				// Extracting origin airport from flight card
				org.jsoup.select.Elements orgnElmnt = flghtCrd
						.select("div[data-testid=flight_card_segment_departure_airport_0]");
				String orgnArpt = orgnElmnt.select("div[data-testid=flight_card_segment_departure_airport_0]").text();

				// Extracting origin airport from flight card
				org.jsoup.nodes.Element dstntnArptElmnt = flghtCrd
						.selectFirst("[data-testid=flight_card_segment_destination_airport_0]");
				String dstntnArpt = dstntnArptElmnt.text();

				// Extracting price from flight card
				org.jsoup.nodes.Element prceElmnt = flghtCrd
						.selectFirst(".FlightCardPrice-module__priceContainer___nXXv2");
				String[] prce_objct = prceElmnt.text().split(",");
				String prce = prce_objct[0].replace("C$", "").replace(".", "");

				// Extracting airline name from flight card
				org.jsoup.select.Elements arlneNmDiv = flghtCrd
						.select("div[data-testid=flight_card_carrier_" + i + "]");
				String arlnenm = arlneNmDiv.text();

				// Creating Flight_Detail object with extracted information
				FlightDetailVO currentFlight = new FlightDetailVO(deprtureTm, dstntnTm, orgnArpt, dstntnArpt, durtn, stps,
						arlnenm, "Booking", Integer.parseInt(prce));
				bestBkngFlights[i] = currentFlight;
			}
		} catch (Exception e) {
			// Handling exceptions
			System.out.println("Error in parsing Booking.com data: " + e.getMessage());
			System.out.println("There are no flights available on Booking.com for this particular route or date.");
		}
		// Returning array of best booking flights
		return bestBkngFlights;
	}
}

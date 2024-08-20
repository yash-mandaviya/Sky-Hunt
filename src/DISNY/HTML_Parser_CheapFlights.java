package DISNY;

import java.io.File;

import org.jsoup.Jsoup;

public class HTML_Parser_CheapFlights {

	// Method to parse flight details from a HTML file obtained from Cheapflights.com
	public FlightDetailVO[] Cheapflights_File_Parser(String orign, String destntn, int dt_month_strng, int dt_yr,
			int dtDayInput, String foldrPath) {
		// Formatting month to ensure consistency in file naming
		String dt_month = "" + dt_month_strng;
		if (dt_month_strng < 10) {
			dt_month = "0" + dt_month_strng;
		}

		String dt_day = null;

		// Formatting day to ensure consistency in file naming
		if (dtDayInput < 10) {
			dt_day = "0" + dtDayInput;
		} else {
			dt_day = "" + dtDayInput;
		}

		// Array to hold the best deals on Cheapflights.com
		FlightDetailVO[] bestDealsOnChealFlights = new FlightDetailVO[3];

		try {
			// Assigning rightmost occurrence index for characters in the pattern
			// Constructing file path based on inputs
			String path = foldrPath + "//" + orign.toLowerCase() + "_to_" + destntn.toLowerCase() + "_" + dt_day + "_"
					+ dt_month + "_" + dt_yr + "_Cheapflights" + ".html";
			File input = new File(path.toLowerCase());
			// Parsing HTML file using Jsoup
			org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8");

			// Selecting elements containing flight results
			org.jsoup.select.Elements resulttss = doc.select("div[class='nrc6-wrapper']");
			org.jsoup.select.Elements airlineess = doc.select("div[class='J0g6-operator-text']");
			org.jsoup.select.Elements durationnss = doc.select("span[class='JWEO-stops-text']");
			org.jsoup.select.Elements priceess = doc.select("div[class='f8F1-price-text']");

			// Loop through the text
			// Looping through flight results
			for (int k = 0; k < 3; k++) {
				org.jsoup.select.Elements divisionns = resulttss.get(k).select("div");
				org.jsoup.select.Elements spannss = divisionns.select("span");

				int ij = 0;
				// Handling variations in HTML structure for different flights
				if (spannss.get(5).text().length() == 0) {
					ij = 1;
				}
				if (spannss.get(6).text().length() == 0) {
					ij = 2;
				}
				if (spannss.get(7).text().length() == 0) {
					ij = 3;
				}
				if (spannss.get(0).text().length() != 0) {
					ij = -5;
				}

				// Extracting flight details
				String timeFrom = spannss.get((ij + 5)).text();
				String timeTo = spannss.get((ij + 7)).text();
				String originShort = spannss.get((ij + 9)).text();
				String destinationShort = spannss.get((ij + 13)).text();
				String numOfStops = spannss.get((ij + 10)).text();
				String airlineName = airlineess.get(k).text();
				String durationOfFlight = durationnss.get(k).text();
				String[] priceObject = priceess.get(k).text().split(" ");
				String priceFlight = priceObject[1].replace(",", "");

				// Creating Flight_Detail object with extracted information
				FlightDetailVO currentCheapFlight = new FlightDetailVO(timeFrom, timeTo, originShort, destinationShort,
						durationOfFlight, numOfStops, airlineName, "Cheapflights", Integer.parseInt(priceFlight));

				bestDealsOnChealFlights[k] = currentCheapFlight;
			}
		} catch (Exception e) {
			// Handling exceptions
			System.out.println("Parsing of data from Cheapflights.com encountered an error: " + e.getMessage());
			System.out.println("There are no flights listed on Cheapflights.com for this specific route or date.");
		}
		// Initializing rightmost occurrence array with -1 for all characters
		// Returning array of best deals on Cheapflights.com
		return bestDealsOnChealFlights;
	}
}

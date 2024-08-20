package DISNY;

import java.io.*;
import java.util.*;

public class ConvertLocationCodes {

	// Method to retrieve airport codes from city names
	public static String gettingCodes(String cityName) {

		// Initialize a HashMap to store city names and their corresponding airport
		// codes
		Map<String, String> dictionaryShortCodes = new HashMap<>();
		String cityyy; // Variable to store each city name read from the cities.txt file
		String coddee; // Variable to store each airport code read from the codes.txt file

		try {
			// Open and read the cities.txt and codes.txt files
			BufferedReader cities_txtFile = new BufferedReader(new FileReader(
					"C:/Users/Admin/eclipse-workspace/FlightPriceAnalysis/FlightPriceAnalysis/cities.txt"));
			BufferedReader codes_txtFile = new BufferedReader(new FileReader(
					"C:/Users/Admin/eclipse-workspace/FlightPriceAnalysis/FlightPriceAnalysis/codes.txt"));

			// Loop through each line in the cities.txt file
			while (((cityyy = cities_txtFile.readLine()) != null)) {
				coddee = codes_txtFile.readLine(); // Read the corresponding airport code from codes.txt
				dictionaryShortCodes.put(cityyy.toLowerCase().trim(), coddee); // Store city name and its airport code
																				// in the HashMap
			}

			// Close the files
			cities_txtFile.close();
			codes_txtFile.close();

			// Return the airport code corresponding to the given city name
			return dictionaryShortCodes.get(cityName.toLowerCase().trim());
		} catch (Exception e) {
			// Handle any exceptions that occur during file reading or HashMap access
			return null;
		}
	}
}

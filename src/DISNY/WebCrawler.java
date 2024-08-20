package DISNY;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.WebDriver;

public class WebCrawler {

	public void runtime_crawl(String departure, String arrival, int ddm_Date, int ddm_Month, int ddm_Year,
			String pathOfTheFolder, WebDriver declaringDriver) throws Exception {

		ConvertLocationCodes changeToCodesOfCity = new ConvertLocationCodes();

//		Getting the codes of the cities as it is required for the URL pattern 
		String codesForCities_Departure = changeToCodesOfCity.gettingCodes(departure),
				codesForCities_Arrival = changeToCodesOfCity.gettingCodes(arrival);

//Converting the date to the format required by the URL of the Website to redirect to the required page 
		String format_Day = "" + ddm_Date;
		if (ddm_Date < 10) {
			format_Day = "0" + ddm_Date;
		}

//		Converting the Month to the format required by the URL of the Website to redirect to the required page
		String format_Month = "" + ddm_Month;
		if (ddm_Month < 10) {
			format_Month = "0" + ddm_Month;
		}

		declaringDriver.manage().window().maximize();
//		Creating the specific URL pattern for the "KAYAK" website so that the driver gets the required page and providing the best flights
		declaringDriver
				.get("https://www.ca.kayak.com/flights/" + codesForCities_Departure + "-" + codesForCities_Arrival + "/"
						+ ddm_Year + "-" + format_Month + "-" + format_Day + "?sort=bestflight_a");

		Thread.sleep(20000);

//		Here we are creating the html file for the webpage that was crawled after entering the required specification by the user
		String creating_HTML_File = declaringDriver.getPageSource();
		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(
					"" + pathOfTheFolder + "//" + departure.toLowerCase() + "_to_" + arrival.toLowerCase() + "_"
							+ format_Day + "_" + format_Month + "_" + ddm_Year + "_Kayak" + ".html"));
			buffer.write(creating_HTML_File);
			buffer.close();
			System.out.println("Successfully created the HTML file for the Date " + format_Day + " / " + format_Month
					+ " / " + ddm_Year + " of " + departure + " to " + arrival + " from Kayak");
		}
//		Catching Exception if Any
		catch (IOException handlingError) {
			System.out.println("Got into Error while creating HTML file!!!! " + handlingError.getMessage());
		}

//		Creating the specific URL pattern for the "Booking.com" website so that the driver gets the required page and providing the Best flights
		declaringDriver.get("https://flights.booking.com/flights/" + codesForCities_Departure + ".AIRPORT-"
				+ codesForCities_Arrival + ".AIRPORT/?type=ONEWAY&adults=1&cabinClass=ECONOMY&children=&from="
				+ codesForCities_Departure + ".AIRPORT&to=" + codesForCities_Arrival + "&Airport&depart=" + ddm_Year
				+ "-" + format_Month + "-" + format_Day + "&sort=BEST");

		Thread.sleep(20000);

//		Here we are creating the html file for the webpage that was crawled after entering the required specification by the user
		creating_HTML_File = declaringDriver.getPageSource();

		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(
					"" + pathOfTheFolder + "//" + departure.toLowerCase() + "_to_" + arrival.toLowerCase() + "_"
							+ format_Day + "_" + format_Month + "_" + ddm_Year + "_Booking" + ".html"));
			buffer.write(creating_HTML_File);
			buffer.close();
			System.out.println("Successfully created the HTML file for the Date" + format_Day + " / " + format_Month
					+ " / " + ddm_Year + " of " + departure + " to " + arrival + " from Booking");
		}
//		Catching Exception if Any
		catch (IOException handlingError) {
			System.out.println("Got into Error while creating HTML file!!!! " + handlingError.getMessage());
		}

//		Creating the specific URL pattern for the "CheapFlights.com" website so that the driver gets the required page and providing the Best flights
		declaringDriver.get(
				"https://www.cheapflights.ca/flight-search/" + codesForCities_Departure + "-" + codesForCities_Arrival
						+ "/" + ddm_Year + "-" + format_Month + "-" + format_Day + "?csort=bestflight_a");

//		Here we are creating the html file for the webpage that was crawled after entering the required specification by the user

		creating_HTML_File = declaringDriver.getPageSource();
//		
		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(
					"" + pathOfTheFolder + "//" + departure.toLowerCase() + "_to_" + arrival.toLowerCase() + "_"
							+ format_Day + "_" + format_Month + "_" + ddm_Year + "_Cheapflights" + ".html"));
			buffer.write(creating_HTML_File);
			buffer.close();
			System.out.println("Successfully created the HTML file for the Date " + format_Day + " / " + format_Month
					+ " / " + ddm_Year + " of " + departure + " to " + arrival + " from Cheapflights");
		}
//		Catching Exception if Any
		catch (IOException handlingError) {
			System.out.println("Got into Error while creating HTML file!!!!" + handlingError.getMessage());
		}

		Thread.sleep(20000);
		
		declaringDriver.manage().window().minimize();

	}
}

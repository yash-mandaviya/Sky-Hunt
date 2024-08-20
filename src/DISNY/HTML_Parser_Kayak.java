package DISNY;

import java.io.File;

import org.jsoup.Jsoup;

//It returns an array of Flight_Detail objects representing the best flights found on Kayak for the given parameters
public class HTML_Parser_Kayak {
	// Method for parsing flight details from a Kayak HTML file
	public FlightDetailVO[] Kayak_Parser(String Starting, String Ending, int Dateofmonth, int Dateofyear, int Dateofday,
			String Dirpthfile) {
		// Initialize variables
		String Website1kayak = "";
		String Datewithmonth = "" + Dateofmonth;
		// Format month with leading zero if needed
		if (Dateofmonth < 10) {
			Datewithmonth = "0" + Dateofmonth;
		}

		String Datewithday = null;
		// Format month with leading zero if needed
		if (Dateofday < 10) {
			Datewithday = "0" + Dateofday;
		} else {
			Datewithday = "" + Dateofday;
		}

		FlightDetailVO[] BestKayak_Flights = new FlightDetailVO[3];

		try {
			String path = Dirpthfile + "//" + Starting.toLowerCase() + "_to_" + Ending.toLowerCase() + "_" + Datewithday
					+ "_" + Datewithmonth + "_" + Dateofyear + "_Kayak" + ".html";
			File input = new File(path.toLowerCase());
			org.jsoup.nodes.Document UpdtedDOC = Jsoup.parse(input, "UTF-8");

			org.jsoup.select.Elements Final_RSLT = null;
			try {
				// Select elements containing flight details
				Final_RSLT = UpdtedDOC.select("div[data-resultid]");
			} catch (Exception expt) {
				System.out.print("here");
			}
			// Select elements containing flight details
			org.jsoup.select.Elements Aircmpny = UpdtedDOC.select("div[class='J0g6-operator-text']");
			org.jsoup.select.Elements Ttljourney = UpdtedDOC.select("div[class='vmXl vmXl-mod-variant-default']");
			org.jsoup.select.Elements prices = UpdtedDOC.select("div[class='f8F1-price-text']");
			// Iteration through top 3 search results will be executed here
			for (int kxl = 0; kxl < 3; kxl++) {
				org.jsoup.select.Elements divisions = Final_RSLT.get(kxl).select("div");
				org.jsoup.select.Elements spans = divisions.select("span");
				// Determine index variation based on empty spans

				int ivy = 0;
				if (spans.get(5).text().length() == 0) {
					ivy = 1;
				}
				if (spans.get(6).text().length() == 0) {
					ivy = 2;
				}
				if (spans.get(7).text().length() == 0) {
					ivy = 3;
				}
				if (spans.get(0).text().length() != 0) {
					ivy = -5;
				}
				// Extraction of flight details
				String Frm_time = spans.get((ivy + 5)).text();
				String To_time = spans.get((ivy + 7)).text();
				String Orign_point = spans.get((ivy + 9)).text();
				String Destniton_ponint = spans.get((ivy + 13)).text();
				String Total_Haults = spans.get((ivy + 15)).text();
				String NameofAircmpny = Aircmpny.get(kxl).text();
				String Total_journey = Ttljourney.get(kxl).text();
				String[] Amount_price = prices.get(kxl).text().split(" ");
				String amnt = Amount_price[1].replace(",", "");
				// Now the new object is created for the latest flight named below
				FlightDetailVO currentFlight = new FlightDetailVO(Frm_time, To_time, Orign_point, Destniton_ponint,
						Total_journey, Total_Haults, NameofAircmpny, "Kayak.com", Integer.parseInt(amnt));

				BestKayak_Flights[kxl] = currentFlight;
			}
		} catch (Exception expt) {
			// If any errors occur during parsing, such as file not found or invalid HTML
			// structure,
			// appropriate error messages are printed. In such cases, an array of
			// Flight_Detail objects with null values is returned.
			System.out.println("ERROR DETECTED FOR KAYAK DATA PARSING: " + expt.getMessage());

			System.out.println("\n-------------------------------------------------------");
			System.out.println("FLIGHTS YOU ARE LOOKING FOR......MAY NOT EXIST ON KAYAK.COM");
		}

		return BestKayak_Flights;// Return array of best Kayak flights to the called method.
	}
}

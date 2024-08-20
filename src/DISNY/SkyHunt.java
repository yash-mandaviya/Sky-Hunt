package DISNY;

import java.io.File;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SkyHunt {// Class declaration

	// Regular expression for date validation
	private static final String DT_REGEX = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$";

	// Pattern object for date validation
	private static final Pattern DT_PTRN = Pattern.compile(DT_REGEX);

	// Method implementation
	public static void main(String[] args) throws Exception {
		// Getting today's date
		LocalDate tday = LocalDate.now();
		// Splitting today's date into components
		String[] todaysDate = tday.toString().split("-");
		// Extracting day, month, and year from today's date
		int valDay = Integer.parseInt(todaysDate[2]), valMonth = Integer.parseInt(todaysDate[1]),
				valYear = Integer.parseInt(todaysDate[0]);
		// Regular expression for date validation
		String rgex = "^(0?[1-9]|[1-2][0-9]|3[0-1])\\/(0?[1-9]|1[0-2])\\/\\d{4}$";
		String input_dt = "";
		Scanner scan = new Scanner(System.in);
		// Flag for quitting the loop
		boolean qut = false;
		// Creating objects of various classes
		SearchFrequency srch_frqncy = new SearchFrequency();
		WordCompletion wrd_cmpltn = new WordCompletion();

		// Main loop
		while (qut == false) {
			boolean flg = true;
			int ftre = 1;// Initializing feature selection
			// Nested loop for feature selection
			while (flg) {
				// Exception handling for feature selection
				try {
					System.out.println(
							"\n*------------------------------------------ SkyHunt ---------------------------------------------*");
					System.out.println(
							"|                                                                                                |");
					System.out.println(
							"|                                                                                                |");
					System.out.println(
							"|                                         Welcome to SkyHunt                                     |");
					System.out.println(
							"|                                                                                                |");
					System.out.println(
							"|                     Your One Stop solution for Airflight Ticket Analysis                       |");
					System.out.println(
							"|                                                                                                |");
					System.out.println(
							"*------------------------------------------ SkyHunt ---------------------------------------------*");
					System.out.println("\n                          Please choose one of the following options:");
					System.out.println("\n[1] Start Searching for your Flight Prices.");
					System.out.println("[2] Want to End Your Search??\n");
					System.out.print("Enter your preference 1 Or 2 : ");
					String ftre_slct = scan.next();

					if (checkNum(ftre_slct)) {
						// Handle the valid numeric input here if needed
					}

					int slctdOptn = Integer.parseInt(ftre_slct);

					if (slctdOptn == 1) {
						ftre = 1;
						flg = false;
					} else if (slctdOptn == 2) {
						ftre = 2;
						flg = false;
						System.out.println(
								"\n*****---------------------------------- Thank You Visit Again --------------------------------*****");
					} else {
						System.out.println("\n Invalid input! Please enter either 1 or 2. \n");
					}
				} catch (NumberFormatException e) {
					System.out.println("\n Invalid input! Please provide a valid number.\n");

				}
			}
			// Feature 1
			if (ftre == 1) {
				// Feature 1 implementation
				// Create a new instance of the Runtime_Crawler class to handle runtime crawling
				WebCrawler rntime_crwl = new WebCrawler();
				System.out.print("\nPlease enter the departure location: ");
				// Prompt the user to enter the departure location and consume the newline
				// character
				scan.nextLine();
				String orgn = scan.nextLine().toLowerCase();
				// Read the departure location input from the user and convert it to lowercase
				wrd_cmpltn.Word_Completor(orgn);
				// Complete the departure location word if needed
				orgn = srch_frqncy.Search_Frequency(orgn);
				// Search for the frequency of the departure location in the database

				boolean ifSem = true;
				String dstntn = null;
				// Initialize variables for the destination location
				while (ifSem) {
					System.out.print("\nPlease enter the arrival location : ");
					// Prompt the user to enter the arrival location and convert it to lowercase
					dstntn = scan.nextLine().toLowerCase();
					wrd_cmpltn.Word_Completor(dstntn);
					// Complete the arrival location word if needed
					dstntn = srch_frqncy.Search_Frequency(dstntn);
					// Search for the frequency of the arrival location in the database

					// Check if origin and destination are the same
					if (!orgn.equals(dstntn)) {
						ifSem = false;
						break;
					} else {
						System.out.println(
								"\nError: The departure and arrival cities cannot be identical. Please enter different locations.");
					}

				}

				while (flg == false) {
					try {
						System.out.print("\nDate [dd/mm/yyyy] : ");
						input_dt = scan.next();
						String[] input_dt_arr = input_dt.split("/");

						if (input_dt.matches(rgex)) {
							// Parse the entered date string into a LocalDate object
							LocalDate entrdDt = LocalDate.of(Integer.parseInt(input_dt_arr[2]),
									Integer.parseInt(input_dt_arr[1]), Integer.parseInt(input_dt_arr[0]));

							// Check if the entered date is a valid future date
							if (entrdDt.isAfter(LocalDate.now()) && isValidDate(entrdDt)) {
								// Valid date
								flg = true;
							} else {
								System.out
										.println("\nPlease input a valid date after today in the format dd/mm/yyyy.\n");
							}
						} else {
							System.out.println("\nPlease input a valid date using the format dd/mm/yyyy.\n");
						}
					} catch (Exception e) {
						System.out.println("\nInvalid date format! Please try again.\n");
					}
				}

				// Split the entered date string into an array
				String[] dt_objct = input_dt.split("/");
				WebDriver drvr = new EdgeDriver();
				// Create a new instance of the WebDriver for web scraping

				String fldrPath = "C:/Users/Admin/eclipse-workspace/FlightPriceAnalysis/FlightPriceAnalysis/src/crawledFiles";
				// Define the path to the directory where crawled files will be stored
				rntime_crwl.runtime_crawl(orgn, dstntn, Integer.parseInt(dt_objct[0]), Integer.parseInt(dt_objct[1]),
						Integer.parseInt(dt_objct[2]), fldrPath, drvr);
				// Perform runtime crawling for flight data from the departure to the arrival
				// location on the specified date

				try {
					// Parse flight data from Kayak for the specified locations and date
					HTML_Parser_Kayak kayak_prsr = new HTML_Parser_Kayak();
					FlightDetailVO[] kayak_dataArray = kayak_prsr.Kayak_Parser(orgn, dstntn,
							Integer.parseInt(dt_objct[1]), Integer.parseInt(dt_objct[2]), Integer.parseInt(dt_objct[0]),
							fldrPath);

					// Parse flight data from Booking.com for the specified locations and date
					HTML_Parser_Booking booking_prsr = new HTML_Parser_Booking();
					FlightDetailVO[] booking_dataArray = booking_prsr.Booking_Parser(orgn, dstntn,
							Integer.parseInt(dt_objct[1]), Integer.parseInt(dt_objct[2]), Integer.parseInt(dt_objct[0]),
							fldrPath);

//					    Cheapflights_File_Parser cheapFlight_prsr = new Cheapflights_File_Parser();
//					    Flight_Detail[] cheapFlights_dataArray = cheapFlight_prsr.Cheapflights_File_Parser(origin, destination, Integer.parseInt(dt_objct[1]), Integer.parseInt(dt_objct[2]), Integer.parseInt(dt_objct[0]), folderPath);

					if (kayak_dataArray == null || booking_dataArray == null || kayak_dataArray.length == 0
							|| booking_dataArray.length == 0) {
						System.out.println("Error: No data found from one or more parsers");
					} else {
						FlightDetailVO[] rslt = new FlightDetailVO[9];

						// Merge the results from Kayak and Booking.com parsers
						System.arraycopy(kayak_dataArray, 0, rslt, 0, Math.min(kayak_dataArray.length, 3));
						System.arraycopy(booking_dataArray, 0, rslt, 3, Math.min(booking_dataArray.length, 3));
						// System.arraycopy(cheapFlights_dataArray, 0, result, 6,
						// Math.min(cheapFlights_dataArray.length, 3));

						// Sort the flight results based on price
						SortFlightsBasedOnPrice srtObjct = new SortFlightsBasedOnPrice();
						srtObjct.sortFlights(rslt);

						System.out.println("\nTop 3 Best Deals are: \n");
						System.out.println(
								"<------------------------------------------------------------------------->\n");
						for (int i = 0; i < Math.min(rslt.length, 3); i++) {
							if (rslt[i] != null) {
								System.out.println(rslt[i].getOriginShort() + " - " + rslt[i].getDestinationShort()
										+ " " + rslt[i].getDepartureTime() + " - " + rslt[i].getDestinationTime());
								System.out.println(rslt[i].getDuration() + " " + rslt[i].getNumberOfStops() + " "
										+ rslt[i].getAirlineName());
								System.out.println(
										"$ " + rslt[i].getPrice() + " CAD From: " + rslt[i].getWebsite() + "\n");
								System.out.println(
										"<------------------------------------------------------------------------->\n");
							} else {
								System.out.println("Error: Flight_Detail object is null at index " + i);
							}
						}
					}
				} catch (NumberFormatException e) {
					System.out.println("Error: Unable to parse integers from date_object");
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Error: Array index out of bounds");
				} catch (NullPointerException e) {
					System.out.println("Error: Flight_Detail object is null");
				} catch (Exception e) {
				}

				// Pause the execution for 1 second to avoid overload or rapid user interaction
				Thread.sleep(1000);

				// Define the path to the directory containing crawled files
				String fldrPaths = "C:/Users/Admin/eclipse-workspace/FlightPriceAnalysis/FlightPriceAnalysis/src/crawledFiles";
				// Create a File object representing the directory
				File folder = new File(fldrPaths);

				// Iterate through each file in the directory
				for (File f : folder.listFiles()) {
					// Check if the file is a regular file and ends with ".html"
					if (f.isFile() && f.getName().endsWith(".html")) {
						// Print the name of the HTML file
						System.out.println(f.getName());
					}
				}
				// Invoke the HTML parsing method to parse HTML files in the specified directory
				Parser_HTMLtoText.htmlParsing(fldrPaths);

				// Inverted Indexing
				Scanner newScan = new Scanner(System.in);
//				String key = newScan.nextLine();
				initiateInvertedIndexing(newScan);

//				System.out.println("\n<-------------------------------   Inverted Indexing    ------------------------------->");
//				System.out.println("\nEnter a Keyword for Inverted Indexing : ");
//				// Define the path to the directory where parsed files will be stored
				String fldrToIndx = "C:/Users/Admin/eclipse-workspace/FlightPriceAnalysis/FlightPriceAnalysis/src/parsedFiles";
//				// Prompt the user to enter a keyword for inverted indexing
//				String keyy = sc.nextLine();
//
//				InvertedIndexing.invertedFinal(fldrToIndx, keyy);

				// Pattern finding

				Scanner sc = new Scanner(System.in);
				System.out.println(
						"\n<-------------------------------   Pattern Finding    ------------------------------->");
				System.out.println("\nInput anything to get the price pattern : ");
				// Read the keyword input from the user
				String anyInpt = sc.next();

				if (anyInpt != null) {
					// Check if user input is not null
					PatternFindingUsingRegex.patterns("C\\$[\\s]+[0-9]*", fldrToIndx);
					// Perform pattern finding using regular expression on the parsed files
				}

				// Frequency Count
				FrequencyCount frqncy_cnt = new FrequencyCount(0, null);
				System.out.println(
						"\n<-------------------------------   Frequency Count    ------------------------------->");
				System.out.println("\nEnter a Keyword for Frequency Count : ");
				// Prompt the user to enter a keyword for frequency count
				String pat = scan.next();
				// Read the keyword input from the user
				frqncy_cnt.Frequency_Counter(fldrPaths, pat);
				// Perform frequency count on the crawled files using the specified keyword

				// Page Ranking
				PageRanking pg_rnkng = new PageRanking(0, null);
				System.out.println(
						"\n<-------------------------------   Page Ranking    ------------------------------->");
				System.out.println("\nPlease provide a keyword for page ranking: ");
				// Prompt the user to provide a keyword for page ranking

				pat = scan.next();
				// Read the keyword input from the user
				pg_rnkng.Page_Ranker(fldrPaths, pat);
				// Perform page ranking on the crawled files using the specified keyword

				File fldr2 = new File(fldrPath);

				File[] fles = fldr2.listFiles();

				for (File fle : fles) {
					if (fle.isFile()) {
						fle.delete();
					}
				}
				File fldr3 = new File(
						"C:/Users/Admin/eclipse-workspace/FlightPriceAnalysis/FlightPriceAnalysis/src/parsedFiles");
				// Get the list of files in the folder
				File[] fls3 = fldr3.listFiles();

				// Loop through the files and delete each one
				for (File file : fls3) {
					if (file.isFile()) {
						file.delete();
					}
				}
			}
			if (ftre == 2) {
				// If the user chooses to exit the program
				System.exit(0);
			}

			// Exit prompt
			String qt_strng = "";
			flg = true;
			while (flg == true) {
				// Display exit prompt
				System.out.println("\n<----------------------------------------------------------------------------->");
				System.out.println("Do you want to end your search? Enter Yes Or No: ");
				qt_strng = scan.next().toLowerCase();

				// Handle user input for exit confirmation
				if (qt_strng.matches("yes") == true) {
					// If the user confirms to exit, set flag and exit
					qut = true;
					flg = false;
					System.out.println("Thank you, Visit Again");
					System.exit(0);
				} else if (qt_strng.matches("no") == true) {
					// If the user chooses not to exit, set flag to exit prompt loop
					flg = false;
				} else {
					// If the user provides invalid input, prompt again
					System.out.println("\n ...Input not valid! Please enter Yes or No...\n");
				}
			}
		}
		scan.close();
	}

	// Inverted Indexing
	private static void initiateInvertedIndexing(Scanner userInput) {
		System.out.println();
		System.out
				.println("\n<-------------------------------   Inverted Indexing    ------------------------------->");
		System.out.println("\nEnter a keyword to Perform Inverted Indexing: ");
		String inputWord = userInput.nextLine();

		try {
			InvertedIndexing.performInvertedIndexing(inputWord);
		} catch (Exception e) {
			System.out.println("An error occurred during inverted indexing: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static boolean isValidDate(LocalDate date) {
		// Extracting year, month, and day from the LocalDate object
		int yr = date.getYear();
		int mnth = date.getMonthValue();
		int dayy = date.getDayOfMonth();

		// Check for February in a leap year
		if (mnth == 2) {
			// Checking if the day is greater than 28
			if (dayy > 28) {
				// Leap year check
				if (!((yr % 4 == 0 && yr % 100 != 0) || (yr % 400 == 0))) {
					// Print error message if February in the given year is not a leap year
					System.out.println("\nInvalid date. February " + dayy + ", " + yr + " is not a leap year.\n");
					return false;// Return false indicating an invalid date
				} else if (dayy > 29) {
					// Print error message if February in the given leap year has more than 29 days
					System.out.println(
							"\nInvalid date. February " + dayy + ", " + yr + " has at most 29 days in a leap year.\n");
					return false;// Return false indicating an invalid date
				}
			}
		}

		// Check for valid day values based on the month
		if ((mnth == 4 || mnth == 6 || mnth == 9 || mnth == 11) && dayy > 30) {
			// Print error message if the day exceeds 30 in April, June, September, or
			// November
			System.out.println("\nInvalid date. Month " + mnth + " has at most 30 days.\n");
			return false;// Return false indicating an invalid date
		} else if (dayy > 31) {
			// Print error message if the day exceeds 31 in other months
			System.out.println("\nInvalid date. Month " + mnth + " has at most 31 days.\n");
			return false;// Return false indicating an invalid date
		}
		return true;
	}

	private static boolean checkNum(String string) throws Exception {
		int intVal; // Variable to hold the parsed integer value
		// Check if the input string is null or empty
		if (string == null || string.equals("")) {
			System.out.println("\nString cannot be parsed, it is null or empty.");
			return false;// Return false indicating parsing failure
		}
		try {
			intVal = Integer.parseInt(string);// Attempt to parse the input string to an integer
			return true;// If parsing is successful, return true
		} catch (NumberFormatException e) {// Catch NumberFormatException if parsing fails
			System.out.println("\nInput String cannot be parsed to Integer.");
		}
		return false;// Return false if parsing fails
	}
}

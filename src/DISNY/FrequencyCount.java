package DISNY;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class FrequencyCount {

	private int intVal;// Declaring private integer variable intVal
	private String strngVal;// Declaring private string variable strngVal

	// Constructor to initialize intVal and strngVal
	public FrequencyCount(int integerVal, String stringVal) {
		this.intVal = integerVal;
		this.strngVal = stringVal;
	}

	// Getter method for intVal
	public int getIntValue() {
		return intVal;
	}

	// Getter method for strngVal
	public String getStringValue() {
		return strngVal;
	}

//List to store Frequency_Count objects
	List<FrequencyCount> frqncyObj = new ArrayList<>();

//Method to count frequency of a pattern in files within a folder
	public void Frequency_Counter(String fldrPathh, String pat) throws Exception {

		{
			fldrPathh = "C:/Users/Admin/eclipse-workspace/FlightPriceAnalysis/FlightPriceAnalysis/src/parsedFiles";
			// Creating a File object for the specified folder path
			File fldrr = new File(fldrPathh);

			// Getting list of files in the folder
			File[] listOfFileess = fldrr.listFiles();

			// Looping through each file in the folder
			for (int i = 0; i < listOfFileess.length; i++) {
				// Checking if the current item is a file
				if (listOfFileess[i].isFile()) {
					// Initializing a string to store file content
					String texxt = "";

					// Creating a File object for the current file
					File fiile = new File(fldrPathh + "/" + listOfFileess[i].getName());

					// Creating a Scanner object to read from the file
					Scanner sccan = new Scanner(fiile);

					// Setting delimiter for Scanner to read entire file content
					sccan.useDelimiter("\\Z");

					// Reading file content and converting to lowercase
					String fileCntnt = sccan.nextLine().toLowerCase();

					// Initializing StringTokenizer to tokenize the file content
					StringTokenizer strngToken = new StringTokenizer(fileCntnt);

					// Looping through each token
					while (strngToken.hasMoreTokens()) {
						String str = strngToken.nextToken();
						// Checking if token matches alphanumeric pattern
						if (Pattern.matches("[a-zA-Z0-9]+", str)) {
							// Appending valid token to txt string
							texxt = texxt + str + " ";
						}
					}
					// Calculating length of pattern and file content
					int M = pat.length();
					int N = texxt.length();
					int freq = 0;
					int nwIndx = 0;
					// Creating BoyerMoore object with pattern
					BoyerMooreAlgorithm boyerMore = new BoyerMooreAlgorithm(pat);
					// Searching for pattern in text
					int offSet = boyerMore.search(texxt, nwIndx);

					// Looping until end of text
					while (nwIndx < (N - M)) {
						// Creating new BoyerMoore object with pattern
						boyerMore = new BoyerMooreAlgorithm(pat);
						// Searching for pattern in text from current index
						offSet = boyerMore.search(texxt, nwIndx);
						// Incrementing index
						nwIndx = 1 + offSet;
						// Incrementing frequency if pattern found
						if (offSet != N) {
							freq += 1;
						}
					}
					// Adding Frequency_Count object to list
					frqncyObj.add(new FrequencyCount(freq, listOfFileess[i].getName()));
					// Closing scanner
					sccan.close();
				}
			}
			// Printing frequency count for each file
			System.out.println("");
			for (FrequencyCount obj : frqncyObj) {
				System.out.println(obj.getIntValue() + " " + obj.getStringValue());
			}
			System.out.println("");
		}
	}
}
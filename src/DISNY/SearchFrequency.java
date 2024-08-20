package DISNY;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class SearchFrequency {

//	Providing the Path of the CSV file where we are storing the frequency of the cities 
//	Frequency counts here the number of times a particular city is searched
	private static final String filePathOfCSV = "C:/Users/Admin/eclipse-workspace/FlightPriceAnalysis/FlightPriceAnalysis/src/Search_Frequency/searchFrequency.csv";

	public String Search_Frequency(String validateTheString) throws Exception {
//    	Filling up the word if the entered name of the city doesnot match with the declared cities names
		WordCompletion FillUptheWord = new WordCompletion();
//    	Checking whether the word entered i.e name of the city is correctly spelled or not 
		SpellChecking validateSpell = new SpellChecking();
//    	Validating the Inputed Word 
		String findWord = validateTheString;
		boolean validateTF = validateSpell.checkandSuggestWords(findWord);
		Scanner enter = new Scanner(System.in);

//		Created a loop to correct the input word if misspelled
		while (validateTF == false) {
			System.out.print("\nEnter again : ");
			findWord = enter.nextLine().toLowerCase();
			FillUptheWord.Word_Completor(findWord);
			validateTF = validateSpell.checkandSuggestWords(findWord);
		}

//		Providing Existing word frequencies from the CSV file 
		Map<String, Integer> frequencyOftheWord = provideFrequencyOfWord();

		findWord = findWord.toLowerCase();
		int getWrdFreq = frequencyOftheWord.getOrDefault(findWord, 0);
//      Displaying the Frequency of the input word 
		System.out.print("\n<-------------------------------   Search Frequency    ------------------------------->");
		System.out.println("\n" + "The word " + findWord + " has been searched for: " + getWrdFreq + " times.");
//      Incrementing the frequency of the input word and saving it in the CSV file   
		frequencyOftheWord.put(findWord, getWrdFreq + 1);
		saveWordFrequency(frequencyOftheWord);
		return findWord;

	}

//  Created a method to provide the word frequencies from the CSV file 
	private static Map<String, Integer> provideFrequencyOfWord() throws IOException {
		Map<String, Integer> frequencyOftheWord = new TreeMap<>();
		try (BufferedReader bufRdr = new BufferedReader(new FileReader(filePathOfCSV))) {
			String readingRow;
			while ((readingRow = bufRdr.readLine()) != null) {
				String[] prts = readingRow.split(",");
				String searchForWord = prts[0];
				int frequency = Integer.parseInt(prts[1]);
				frequencyOftheWord.put(searchForWord, frequency);
			}
		} catch (Exception handleError) {
			System.out.println("Got into Error while providing the word Frequency data from CSV file");
		}
		return frequencyOftheWord;
	}

// This method is created to save the frequency of the word to the CSV file name searchfrequency.csv to store the total frequency 
//    of a particluar word, so that we can get how many times that word is searched by maintaining the CSV file 

	private static void saveWordFrequency(Map<String, Integer> frequencyOftheWord) throws IOException {
		try (FileWriter fileWtr = new FileWriter(filePathOfCSV)) {
			for (Map.Entry<String, Integer> insert : frequencyOftheWord.entrySet()) {
				String searchedWord = insert.getKey();
				int searchedWordFreq = insert.getValue();
				fileWtr.append(searchedWord).append(",").append(Integer.toString(searchedWordFreq)).append("\n");
			}
		}
	}

}

package DISNY;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SpellChecking {

	private Set<String> maintainingRecord;

// Here the constructor initializes maintainingRecord by reading the words from cities.txt file and	adding them to the HashSet
	public SpellChecking() throws FileNotFoundException {
		// Initialize the HashSet
		maintainingRecord = new HashSet<>();
		Scanner takeInput = new Scanner(
				new File("C:/Users/Admin/eclipse-workspace/FlightPriceAnalysis/FlightPriceAnalysis/cities.txt"));
		while (takeInput.hasNext()) {
//        	Converting the taken word from the cities.txt file to lowerCase 
			String convertTakenWord = takeInput.nextLine().trim().toLowerCase();
//          Adding the word read from the cities.txt file to the HashSet maintainingRecord 
			maintainingRecord.add(convertTakenWord);
		}
		takeInput.close();
	}

	// This method checks if a given word exists in the HashSet -->
	// maintainingRecord .
	public boolean checkWhetherExist(String word) {
		return maintainingRecord.contains(word.toLowerCase());
	}

//   Created a method that suggest corrections for a misspelled word 
//   After checking if the word is wrong it provides the list of suggestions

	public List<String> suggestingWords(String EnteredWord) {
		List<String> recommendationArrayList = new ArrayList<>();

		if (maintainingRecord.contains(EnteredWord)) {
			return recommendationArrayList;
		}

// Declaring minimum Edit Distance threshold        
		int minDistance = 3;

		for (String correctWord : maintainingRecord) {
//     Calculating Edit Distane btw correctWord and EnteredWord
			int edtDist = calculateEdtDist(correctWord, EnteredWord);
			if (edtDist < minDistance) {
				minDistance = edtDist;
				recommendationArrayList.clear(); // Clear previous recommendations
				recommendationArrayList.add(correctWord);
			} else if (edtDist == minDistance) {
				recommendationArrayList.add(correctWord);
			}
		}

		return recommendationArrayList;
	}

//  This method calculates the Edit distance between two strings 
	private int calculateEdtDist(String c, String E) {
		int a = c.length();
		int b = E.length();
		int[][] mn = new int[a + 1][b + 1];
		for (int d = 0; d <= a; d++) {
			mn[d][0] = d;
		}
		for (int e = 0; e <= b; e++) {
			mn[0][e] = e;
		}
		for (int f = 1; f <= a; f++) {
			for (int h = 1; h <= b; h++) {
				if (Character.toLowerCase(c.charAt(f - 1)) == Character.toLowerCase(E.charAt(h - 1))) {
					mn[f][h] = mn[f - 1][h - 1];
				} else {
					mn[f][h] = 1 + Math.min(mn[f - 1][h], Math.min(mn[f][h - 1], mn[f - 1][h - 1]));
				}
			}
		}
//      Return Edit Distance
		return mn[a][b];
	}

	// This method performs spell checking on a given word.

	public boolean checkandSuggestWords(String checkWord) throws FileNotFoundException {
		SpellChecking spellchecker = new SpellChecking();
//       
//      Here it checks the word and if it doesnt have characters it will throw an message stating   
		if (!checkWord.matches("[a-zA-Z ]+")) {
//          Show error message for any non alphabetic input  
			System.out.print("\n<-------------------------------   Spell Checking    ------------------------------->");
			System.out.println("\nError: Please enter a valid city (containing only letters).\n");
			return false;
		}

//      Here it checks the word and 
//      if it finds that word in the maintainingRecord --> HashSet it will show The spelling of this city is correct    
		if (spellchecker.checkWhetherExist(checkWord)) {
			System.out.print("\n<-------------------------------   Spell Checking    ------------------------------->");
			System.out.println("\nThe spelling of this city is correct!");
			return true;
		} else {
//        	Here it will check the word and throws message to the console stating that the input word is not correctly spelled and there are not suggestions for this word 
			List<String> recommendationArrayList = spellchecker.suggestingWords(checkWord);
			if (recommendationArrayList.isEmpty()) {
				System.out.print(
						"\n<-------------------------------   Spell Checking    ------------------------------->");
				System.out.println(
						"\nThe entered word is not spelled correctly and there are no recommendations for correction.\n");
			}
//        	Here it will check the word and throws message to the console stating that the input word is not correctly spelled and there are suggestions for this word 

			else {
				System.out.print(
						"\n<-------------------------------   Spell Checking    ------------------------------->");
				System.out.println(
						"\nThe entered word is not spelled correctly. Here is a recommendation based on the search:\n");
				for (String suggestion : recommendationArrayList) {
					System.out.println("- " + suggestion);
				}
				System.out.println("");
			}
			return false;
		}
	}
}

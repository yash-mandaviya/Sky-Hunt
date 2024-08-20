package DISNY;

public class BoyerMooreAlgorithm {
	private final int radix; // Radix for ASCII characters
	private int[] rightAry; // Rightmost occurrence array for characters
	private String patrn; // Pattern string for searching

	// Constructor for BoyerMoore class, initializes radix, pattern, and rightAry
	public BoyerMooreAlgorithm(String pat) {
		this.radix = 256; // Assuming ASCII characters
		this.patrn = pat; // Assigning pattern string

		// Initializing rightmost occurrence array with -1 for all characters
		rightAry = new int[radix];
		for (int charc = 0; charc < radix; charc++)
			rightAry[charc] = -1;
		// Assigning rightmost occurrence index for characters in the pattern
		for (int j = 0; j < pat.length(); j++)
			rightAry[pat.charAt(j)] = j;
	}

	// Method to search for pattern occurrences in the text
	public int search(String txt, Integer newIndex) {
		int M = patrn.length(); // Length of the pattern
		int N = txt.length(); // Length of the text
		int skiip; // Amount to skip in case of mismatch
		// Loop through the text
		for (int i = 0 + newIndex; i <= N - M; i += skiip) {
			skiip = 0; // Reset skip amount
			// Compare characters from the end of the pattern
			for (int j = M - 1; j >= 0; j--) {
				// If characters mismatch
				if (patrn.charAt(j) != txt.charAt(i + j)) {
					// Calculate skip amount based on rightmost occurrence
					skiip = Math.max(1, (j - rightAry[txt.charAt(i + j)]));
					break;
				}
			}
			// If no skip is needed, return the current index as match position
			if (skiip == 0) {
				return i;
			}
		}
		// If no match found, return the length of the text
		return N;
	}

	// Main method to demonstrate Boyer-Moore pattern searching
	public static void main(String[] args) {

		String ptrn = "ab"; // Pattern string
		String text = "abhhhhahabbaaabaa"; // Text string for searching

		System.out.println(text); // Printing the text
		// Initializing variables for pattern and text length, and frequency counter
		int M = ptrn.length();
		int N = text.length();
		int frqncy = 0; // Counter for pattern occurrences
		int newIndx = 0; // Starting index for searching

		// Creating BoyerMoore object with the pattern
		BoyerMooreAlgorithm boyerMooore = new BoyerMooreAlgorithm(ptrn);
		int offSeet = boyerMooore.search(text, newIndx); // Searching for the pattern
		System.out.println("\n");
		// Loop to find all occurrences of the pattern in the text
		while (newIndx < (N - M)) {

			boyerMooore = new BoyerMooreAlgorithm(ptrn); // Creating BoyerMoore object
			offSeet = boyerMooore.search(text, newIndx); // Searching for the pattern
			newIndx = 1 + offSeet; // Moving to the next index

			// If pattern found at a valid index, print its position and increment frequency
			if (offSeet != N) {
				System.out.println("Position : " + offSeet);
				frqncy += 1; // Incrementing frequency
			}
		}
		// Printing the total frequency of pattern occurrences in the text
		System.out.println("Frequency : " + frqncy);
	}
}

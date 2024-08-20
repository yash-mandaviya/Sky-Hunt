package DISNY;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class WordCompletion {
	private TrieDSNode root = new TrieDSNode();

//  Here the method Word_Completor reads a file which has a list of words from the txt file cities.txt
//  After that it builds a trie Data Structure by inserting each word in trie
//  Once the trie is builded, it searches for words with a given prefix and prints the completions.  

	public void Word_Completor(String prfx) throws FileNotFoundException {
		try (Scanner input = new Scanner(new FileReader(
				"C:/Users/Admin/eclipse-workspace/FlightPriceAnalysis/FlightPriceAnalysis/cities.txt"))) {
			while (input.hasNext()) {
				String inputWord = input.nextLine().toLowerCase().replaceAll("\\s", ""); // Convert to lowercase and
																							// remove spaces
//            	Add Each word in Trie Data Structure
				addWordInTrie(inputWord);
			}
		} catch (FileNotFoundException catchError) {
			System.err.println("Got into Error While Reading File: " + catchError.getMessage());
			System.exit(1);
		}

//      Search for Prefix in Trie 
		TrieDSNode nodePrfx = searchPrefix(prfx.toLowerCase().replaceAll("\\s", ""));
		if (nodePrfx != null) {
			System.out
					.print("\n<-------------------------------   Word Completion    ------------------------------->");
			System.out.println("\nCompletions for the word \"" + prfx + "\" are...");
//         Printing the completion for the word
			printCompletions(nodePrfx, prfx);
		} else {
//        	No Completion found for the word 
			System.out
					.print("\n<-------------------------------   Word Completion    ------------------------------->");
			System.out.println("\n No completions found for \"" + prfx + "\" :( ");
		}
	}

//  Here addWordInTrie method adds a word in trie by creating nodes for each character of the word   
	private void addWordInTrie(String inputWord) {
		TrieDSNode node = root;

//     Here removeSpaceFromString stores the string with all the spaces removed from it
		String removeSpaceFromString = inputWord.replaceAll("\\s", "");
		for (char newCharacter : removeSpaceFromString.toCharArray()) {
			int pq = (newCharacter >= 'a' && newCharacter <= 'z') ? newCharacter - 'a'
					: (newCharacter >= 'A' && newCharacter <= 'Z') ? newCharacter - 'A' + 26 : -1;
			if (pq >= 0 && pq < 52) {
				if (node.children[pq] == null) {
					node.children[pq] = new TrieDSNode();
				}
				node = node.children[pq];
			}
		}
		node.markNodeAsEnd(true);
	}

	// Here this method looks through the trie for a specified prefix and returns
	// the node that corresponds to the prefix's final character.

	private TrieDSNode searchPrefix(String prfx) {
		TrieDSNode node = root;
		for (char newCharacter : prfx.toCharArray()) {
			int a = (newCharacter >= 'a' && newCharacter <= 'z') ? newCharacter - 'a'
					: (newCharacter >= 'A' && newCharacter <= 'Z') ? newCharacter - 'A' + 26 : -1;
			if (a >= 0 && a < 52 && node.children[a] != null) {
				node = node.children[a];
			} else {
				return null;
			}
		}
		return node;
	}

// This method recursively prints all words that can be formed from a given trie node.    
	private void printCompletions(TrieDSNode node, String prefix) {

		if (node == null) {
			return;
		}

		if (node.wordEndOrNot()) {
			System.out.println(prefix);
		}

		for (char newCharacter : node.getChildren()) {
			TrieDSNode child = node.childNodeGet(newCharacter);
			if (child != null) {
				printCompletions(child, prefix + newCharacter);
			}
		}
	}

	private static class TrieDSNode {
		private TrieDSNode[] children = new TrieDSNode[52];
		private boolean wordEndOrNot;

// Method created to get a child node corresponding to a character     
		public TrieDSNode childNodeGet(char newCharacter) {
			int jk = (newCharacter >= 'a' && newCharacter <= 'z') ? newCharacter - 'a'
					: (newCharacter >= 'A' && newCharacter <= 'Z') ? newCharacter - 'A' + 26 : -1;
			return (jk >= 0 && jk < 52) ? children[jk] : null;
		}

// Here the method is created to get an array of characters representing the children of the node
		public char[] getChildren() {
			StringBuilder newChar = new StringBuilder();
			for (int lm = 0; lm < 52; lm++) {
				if (children[lm] != null) {
					newChar.append((lm < 26) ? (char) ('a' + lm) : (char) ('A' + lm - 26));
				}
			}
			return newChar.toString().toCharArray();
		}

// Created a method to check if the node represents the end of a word
		public boolean wordEndOrNot() {
			return wordEndOrNot;
		}

// Created a method to mark the node as the end of the word 
		public void markNodeAsEnd(boolean wordEnd) {
			wordEndOrNot = wordEnd;
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		WordCompletion wordCompletion = new WordCompletion();
		wordCompletion.Word_Completor("lon");
	}
}

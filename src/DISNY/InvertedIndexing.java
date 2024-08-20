package DISNY;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InvertedIndexing {

	private static class IndexNode {
		String word;
		int wordFrequency;
		IndexNode leftNode, rightNode;
		List<TermOccurrence> timeOccurred;
		int height; // Height of the node

		// Constructor for IndexNode
		IndexNode(String word) {
			this.word = word;
			this.wordFrequency = 1;
			this.timeOccurred = new ArrayList<>();
			this.height = 1; // Initially height is 1 for new node
		}

		// Method to add occurrence of the word
		void addOccurrence(int position, String fileName) {
			timeOccurred.add(new TermOccurrence(position, fileName));
		}
	}

	// Define a nested static class named TermOccurrence
	private static class TermOccurrence {
		int position; // Position of occurrence
		String fileName; // Name of the file where the occurrence happened

		// Constructor for TermOccurrence
		TermOccurrence(int position, String fileName) {
			this.position = position;
			this.fileName = fileName;
		}
	}

	private IndexNode rootNode;// Root node of the inverted index tree

	// Method to create inverted index from a file
	public void createIndex(File newFile) {
		try (BufferedReader buffRead = new BufferedReader(new FileReader(newFile))) {
			String row;
			int pageNo = 0;
			int location = 0;
			while ((row = buffRead.readLine()) != null) {
				String[] wordOfRow = row.split("\\s+");
				for (String newWord : wordOfRow) {
					String convertWord = newWord.toLowerCase();
					addWord(convertWord, location++, newFile.getName());
				}
				pageNo++;
			}
		} catch (IOException handleError) {
			System.err.println("Got into Error while reading file: " + newFile.getAbsolutePath());
			handleError.printStackTrace();
		}
	}

	// Method to add a word to the inverted index
	private void addWord(String word, int location, String NameOfFile) {
		rootNode = insertRecursive(rootNode, word, location, NameOfFile);
	}

	// Recursive method to insert a word into the inverted index tree
	private IndexNode insertRecursive(IndexNode nodeForIndex, String word, int location, String NameOfFile) {
		if (nodeForIndex == null) {
			return new IndexNode(word);
		}

		int cmpRslt = word.compareTo(nodeForIndex.word);
		if (cmpRslt < 0) {
			nodeForIndex.leftNode = insertRecursive(nodeForIndex.leftNode, word, location, NameOfFile);
		} else if (cmpRslt > 0) {
			nodeForIndex.rightNode = insertRecursive(nodeForIndex.rightNode, word, location, NameOfFile);
		} else {
			nodeForIndex.wordFrequency++;
			nodeForIndex.addOccurrence(location, NameOfFile);
			return nodeForIndex; // No change in height, as the node already exists
		}

		// Update height of current node
		nodeForIndex.height = 1 + Math.max(height(nodeForIndex.leftNode), height(nodeForIndex.rightNode));

		// Check balance factor and perform rotations if necessary
		int balance = getBalance(nodeForIndex);

		// Left Left Case
		if (balance > 1 && word.compareTo(nodeForIndex.leftNode.word) < 0) {
			return rightRotate(nodeForIndex);
		}

		// Right Right Case
		if (balance < -1 && word.compareTo(nodeForIndex.rightNode.word) > 0) {
			return leftRotate(nodeForIndex);
		}

		// Left Right Case
		if (balance > 1 && word.compareTo(nodeForIndex.leftNode.word) > 0) {
			nodeForIndex.leftNode = leftRotate(nodeForIndex.leftNode);
			return rightRotate(nodeForIndex);
		}

		// Right Left Case
		if (balance < -1 && word.compareTo(nodeForIndex.rightNode.word) < 0) {
			nodeForIndex.rightNode = rightRotate(nodeForIndex.rightNode);
			return leftRotate(nodeForIndex);
		}

		return nodeForIndex;
	}

	// Get the height of a node
	private int height(IndexNode node) {
		return (node == null) ? 0 : node.height;
	}

	// Get the balance factor of a node
	private int getBalance(IndexNode node) {
		return (node == null) ? 0 : height(node.leftNode) - height(node.rightNode);
	}

	// Perform a right rotation
	private IndexNode rightRotate(IndexNode y) {
		IndexNode x = y.leftNode;
		IndexNode T2 = x.rightNode;

		// Perform rotation
		x.rightNode = y;
		y.leftNode = T2;

		// Update heights
		y.height = Math.max(height(y.leftNode), height(y.rightNode)) + 1;
		x.height = Math.max(height(x.leftNode), height(x.rightNode)) + 1;

		// Return new root
		return x;
	}

	// Perform a left rotation
	private IndexNode leftRotate(IndexNode x) {
		IndexNode y = x.rightNode;
		IndexNode T2 = y.leftNode;

		// Perform rotation
		y.leftNode = x;
		x.rightNode = T2;

		// Update heights
		x.height = Math.max(height(x.leftNode), height(x.rightNode)) + 1;
		y.height = Math.max(height(y.leftNode), height(y.rightNode)) + 1;

		// Return new root
		return y;
	}

	// Method to search for a word in the inverted index
	public void wordForSearching(String word) {
		IndexNode node = recurSearch(rootNode, word.toLowerCase());
		if (node == null) {
			System.out.println("Provided word not found: " + word);
			return;
		}
		wordObtained(node);
		System.out.println();
	}

	// Recursive method to search for a word in the inverted index tree
	private IndexNode recurSearch(IndexNode indNode, String word) {
		if (indNode == null) {
			return null;
		}

		int cmpRslt = word.compareTo(indNode.word);
		if (cmpRslt == 0) {
			return indNode;
		} else if (cmpRslt < 0) {
			return recurSearch(indNode.leftNode, word);
		} else {
			return recurSearch(indNode.rightNode, word);
		}
	}

	// Method to print occurrences of a word
	private void wordObtained(IndexNode node) {
		for (TermOccurrence occurrence : node.timeOccurred) {
			System.out
					.println("Word found at Position: " + occurrence.position + " in file --> " + occurrence.fileName);
		}
	}

	// Method to perform inverted indexing
	public static void performInvertedIndexing(String searchingWord) {
		String pathOfFolder = "C:/Users/Admin/eclipse-workspace/FlightPriceAnalysis/FlightPriceAnalysis/src/parsedFiles";
		InvertedIndexing index = new InvertedIndexing();

		File fldr = new File(pathOfFolder);
		if (!fldr.exists()) {
			System.err.println("Folder not found: " + pathOfFolder);
			return;
		}

		File[] fldrFiles = fldr.listFiles();
		if (fldrFiles == null) {
			System.err.println("Error listing files in folder: " + pathOfFolder);
			return;
		}

		for (File file : fldrFiles) {
			if (file.isFile() && file.getName().endsWith(".txt")) {
				System.out.println("Performing Inverted Indexing on " + file.getName() + " file");
				index.createIndex(file);
			}
		}

		System.out.println("\nPerforming Search for " + searchingWord + "\n");
		index.wordForSearching(searchingWord);
	}
}

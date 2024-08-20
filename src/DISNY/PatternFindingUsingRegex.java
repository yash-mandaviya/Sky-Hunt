package DISNY;

import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

// Method to find patterns in text files within a directory
public class PatternFindingUsingRegex {

	public static void patterns(String Pth_1, String Dirctry_pthh) throws IOException {
		String RgxPattrn = "C\\$[\\s]+[0-9]*";

		String TXT_2;

		Pattern regexPattern = Pattern.compile(RgxPattrn);
		// Access the directory containing text files
		File F_flder = new File(Dirctry_pthh);
		// Iteratiion for each file in the current mentioned Directory
		for (File f : F_flder.listFiles()) {
			// Reading the content of the current file mentioned below here
			TXT_2 = new String(Files.readAllBytes(Path.of(f.getAbsolutePath())));

			Matcher Final_mtcherPttrn = regexPattern.matcher(TXT_2);
			while (Final_mtcherPttrn.find()) {
				// Printing of the matched pattern
				System.out.println(Final_mtcherPttrn.group());

			}

		}

	}

	// Every functions are called in this Main method
	public static void main(String[] args) throws IOException {
		patterns("C\\$[\\s]+[0-9]*", "");
	}
}
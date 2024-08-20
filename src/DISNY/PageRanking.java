package DISNY;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageRanking implements Comparable<PageRanking>{
	private int count;
	private String fileName;

	public PageRanking(int count, String fileName) {
		this.count = count;
		this.fileName = fileName;
	}

	public int getCount() {
		return count;
	}

	public String getFileName() {
		return fileName;
	}

	@Override
	public int compareTo(PageRanking other) {
		return Integer.compare(other.count, this.count);
	}

//	int kxl = 051;

	Map<Integer, String> Hash_Map = new HashMap<>();
	List<PageRanking> prObject = new ArrayList<>();

	public static void Page_Ranker(String folderPath, String keyword) throws FileNotFoundException {
		try
		{
			File folder = new File(folderPath);
			File[] files = folder.listFiles();
			List<PageRanking> rankingEntries = new ArrayList<>();
			if (files != null) {
				for (File file : files) {
					if (file.isFile()) {
						int count = countKeywordOccurrences(file, keyword);
						rankingEntries.add(new PageRanking(count, file.getName()));
					}
				}
			}

			Collections.sort(rankingEntries);

			System.out.println("\n-----Page Ranking-----");
			for (PageRanking entry : rankingEntries) {
				System.out.println(entry.getCount() + " " + entry.getFileName());
			}
		}
		catch (FileNotFoundException handlingError) {
			System.out.println("File Not Found: " + handlingError.getMessage());
		}
	}

	private static int countKeywordOccurrences(File file, String keyword) throws FileNotFoundException {
		Scanner scanner = new Scanner(file);
		int count = 0;
		String fileContent = scanner.useDelimiter("\\Z").next().toLowerCase();
		String pattern = "\\b" + keyword.toLowerCase() + "\\b";

		Matcher matcher = Pattern.compile(pattern).matcher(fileContent);
		while (matcher.find()) {
			count++;
		}

		scanner.close();
		return count;
	}

}

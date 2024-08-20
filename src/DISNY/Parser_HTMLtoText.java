
package DISNY;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.nodes.Document;

public class Parser_HTMLtoText {

	public static void main(String args[]) throws IOException {

	}

	// This Method is used for parsing HTML files in the mentioned directory
	public static void htmlParsing(String fldrpth) {

		String Dirrectry = fldrpth;// this is to store the fldrpath

		File fldr_parse = new File(Dirrectry);

		for (File taken_files : fldr_parse.listFiles()) {
			// Inorder to check that the file has extension of .html
			if (taken_files.isFile() && taken_files.getName().endsWith(".html")) {

				File tknfle_input = new File(taken_files.getAbsolutePath());

				try {

					Document URLconnection = Jsoup.parse(tknfle_input, "UTF-8", "http://example.com/");

					String fileName = tknfle_input.getName();
					int pos = fileName.lastIndexOf(".");
					if (pos > 0 && pos < (fileName.length() - 1)) { // If '.' is not the first or last character.
						fileName = fileName.substring(0, pos);
					}

					String varName = "C:/Users/Admin/eclipse-workspace/FlightPriceAnalysis/FlightPriceAnalysis/src/parsedFiles/"
							+ fileName + ".txt";

					File tknfle2 = new File(varName);
					tknfle2.createNewFile();

					FileWriter Nowwritten = new FileWriter(varName);
					// boolean flag = true;

					int ink = 0;

					while (!URLconnection.getElementsByIndexEquals(ink).isEmpty()) {
						if (!URLconnection.getElementsByIndexEquals(ink).text().isEmpty()) {
							Nowwritten.write(URLconnection.getElementsByIndexEquals(ink).text());
							Nowwritten.write("\n");
						}

						ink++;
					}

					Nowwritten.close();

				} catch (IOException exptn) {

					exptn.printStackTrace();
				}

			}
		}
	}

}

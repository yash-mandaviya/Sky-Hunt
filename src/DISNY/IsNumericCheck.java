package DISNY;

//Class for checking if a given string represents a numeric value
public class IsNumericCheck {
	@SuppressWarnings("unused")
	public boolean isNumeric(String Numerc_strings) {
		int valuetkn;
		// The string which is being parsed is printed here
		System.out.println(String.format("Strings are been Parsed here: \"%s\"", Numerc_strings));

		if (Numerc_strings == null || Numerc_strings.equals("")) {
			System.out.println("EMPTY or NULL Numerc_strings value detected.");
			return false;
		}

		try {
			// Attempt to parse the string as an integer
			valuetkn = Integer.parseInt(Numerc_strings);
			return true;
		} catch (NumberFormatException exptn) {
			System.out.println("String dtected cannot be changed(parsed) to integer");
			exptn.printStackTrace();
		}
		return false;
	}

}

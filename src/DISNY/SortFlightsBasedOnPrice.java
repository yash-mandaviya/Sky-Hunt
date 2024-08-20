package DISNY;

import java.util.Arrays;
import java.util.Comparator;

public class SortFlightsBasedOnPrice {

	public FlightDetailVO[] sortFlights(FlightDetailVO[] detailsOfFlights) {

		Arrays.sort(detailsOfFlights, new Comparator<FlightDetailVO>() {

			@Override
			public int compare(FlightDetailVO Price_For_Flight_1, FlightDetailVO Price_For_Flight_2) {
				if (Price_For_Flight_1 == null || Price_For_Flight_2 == null) {
					return 0; // or handle the null case as needed
				}
				if (Price_For_Flight_1.getPrice() == Price_For_Flight_2.getPrice()) {
					return 0;
				} else if (Price_For_Flight_1.getPrice() > Price_For_Flight_2.getPrice()) {
					return 1;
				} else {
					return -1;
				}
			}

		});

		return detailsOfFlights;
	}

}

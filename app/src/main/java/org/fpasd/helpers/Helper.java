package org.fpasd.helpers;

public class Helper {

	public static String getOrdinal(int position) {
		String result = "";
		switch (position) {
			case 1 -> result = result + position + "st";
			case 2 -> result = result + position + "nd";
			case 3 -> result = result + position + "rd";
			default -> result = result + position + "th";
		}
		return result;
	}
}

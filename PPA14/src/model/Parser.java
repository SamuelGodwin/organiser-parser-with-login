package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This is our 'Parser' class in our model package. 
 * This class forms the prominent part of our model in our MVC structure.
 * This class and its method are public, so are accessible anywhere.
 * @author Samuel & Patrick
 *
 */
public class Parser {
	
	// Regex Patterns
	private static Pattern dateOrdinalRegex = Pattern.compile(
			"(the\\s)?(3(0(th)|1(st))|2?(1(st)|2(nd)|3(rd)|[4-9](th))|1[0-9](th))", Pattern.CASE_INSENSITIVE);
	
	private static Pattern dayRegex = Pattern.compile(
			"(mon|tue(s)?|wed(nes)?|thu(rs)?|fri|sat(ur)?|sun)(day)?", Pattern.CASE_INSENSITIVE);
	
	private static Pattern monthRegex = Pattern.compile(
			"(of\\s)?(jan(uary)?|feb(ruary)?|mar(ch)?|apr(il)?|may|jun(e)?|jul(y)?|aug(ust)?|sep(tember)?"
			+ "|oct(ober)?|nov(ember)?|dec(ember)?)", Pattern.CASE_INSENSITIVE);
	
	private static Pattern timeRegex = Pattern.compile(
			"(0[1-9]|1[0-9]|2[0-4])(:?[0-5][0-9])|(([1-9]|1[0-2])\\s?(am|pm))");
	
	private static Pattern impreciseTimeRegex = Pattern.compile(
			"morning|midday|afternoon|evening", Pattern.CASE_INSENSITIVE);
	
	private static Pattern dateRegex = Pattern.compile(
			"(0?[1-9]|1[0-9]|2[0-9]|3[01])(\\/)(0?[1-9]|1[0-2])(\\/)(20[1-9][1-9])?");
	
	public static InputType classifyInput(String string) {
		if (string.toLowerCase().contains("remind me to")) return InputType.REMINDER;
		
		for (String word : string.split(" ")) {
			if (matchesDate(word)) return InputType.CALENDAR;
		}
		
		return null;
		
	}
	
	/**
	 * 'formatReminder'
	 * @param string
	 * @return capitalised result of substring
	 */
	public static String formatReminder(String string) {
		return capitalise(string.substring(13, string.length()));
	}
	
	/**
	 * 'tokenizeInputToMap'
	 * @param string
	 * @return tokens
	 */
	public static HashMap<TokenType, String> tokenizeInputToMap(String string) {
		HashMap<TokenType, String> tokens = new HashMap<TokenType, String>();
		
		ArrayList<String> seperators = new ArrayList<>(Arrays.asList("the", "of"));
		ArrayList<String> prepositions = new ArrayList<>(Arrays.asList("at", "on"));
		String[] wordsList = string.split(" ");
		
		String previousWord = "";
		String dateString = "";
		String eventString = "";
		
		// For each word in wordsList, if a word preceded by "at" matches our timeRegex, it is time.
		// Otherwise, it is location.
		for (String word : wordsList) {
			
			if (word.toLowerCase().equals("next")) {
				dateString += word + " ";
			} else if (previousWord.toLowerCase().equals("at")) {
				if (timeRegex.matcher(word).matches()) {
					tokens.put(TokenType.TIME, word); 
					
				} else {
					tokens.put(TokenType.LOCATION, word);
				}
				
			} else if (matchesDate(word) || (matchesDate(previousWord) && seperators.contains(word))) {
				dateString += word + " ";
			} else if (impreciseTimeRegex.matcher(word).matches()) {
				tokens.put(TokenType.TIME, word);
			} else if (!prepositions.contains(word.toLowerCase())){
				eventString += word + " ";
			}
			
			previousWord = word;
			
		}
		
		eventString = eventString.replaceAll("in the", "");	
		
		tokens.put(TokenType.DATE, dateString);
		tokens.put(TokenType.EVENT, eventString);
		
		return tokens;
		
	}
	
	/**
	 * Tokens to Calendar.
	 * @param tokens
	 * @return a string representation of an object
	 */
	public static String tokensToCalendar(HashMap<TokenType, String> tokens) {	
		StringBuffer sb = new StringBuffer();
		String timeString = tokens.get(TokenType.TIME);
		String dateString = tokens.get(TokenType.DATE);
		String eventString = tokens.get(TokenType.EVENT);
		String locationString = tokens.get(TokenType.LOCATION);
		
		if (dateString == null) dateString = "-";
		if (timeString == null) timeString = "-";
		if (locationString == null) locationString = "-";
		
		sb.append("Event: " + capitalise(eventString));
		sb.append(" | Date: " + capitalise(parseDateToken(dateString)));
		sb.append(" | Time: " + capitalise(parseTimeToken(timeString)));
		sb.append(" | Location: " + capitalise(locationString));
		return sb.toString();	
	}
	
	/**
	 * Parses Date tokens
	 * @param string
	 * @return result of addTimeOrdinal()
	 */
	public static String parseDateToken(String string) {
		
		boolean next = false;
		
		String monthString = "";
		String dayOfMonthString = "";
		String dayOfWeekString = "";
		String numericalDate = "";
		
		String thisMonth;
		
		string = string.replaceAll(" the ", " ");
		string = string.replaceAll(" of ", " ");
		String[] wordsArr = string.split(" ");
		
		for (String word : wordsArr) {
			System.out.println(word);
			if (dateRegex.matcher(word).matches()) numericalDate = word;
			if (word.toLowerCase().equals("next")) next = true;
			if (monthRegex.matcher(word).matches()) monthString = word;
			if (dayRegex.matcher(word).matches()) dayOfWeekString = word;
			if (dateOrdinalRegex.matcher(word).matches()) dayOfMonthString = word;
		}
		
		System.out.println("Month: " + monthString);
		System.out.println("Day: " + dayOfWeekString);
		System.out.println("Day of Month: " + dayOfMonthString);
		
		if (!numericalDate.equals("")) {
			SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
	        Date date;
			try {
				date = parser.parse(numericalDate);
			} catch (ParseException e) {
				date = null;
				e.printStackTrace();
			}
	        SimpleDateFormat formatter = new SimpleDateFormat("EEEE d MMMM");
	        String ret = formatter.format(date);

			return addTimeOrdinal(ret);
		}
		if (monthString.equals("") || dayOfMonthString.equals("")) {
			if (next) {
				String ret = DateHandler.nextOccurenceOf(dayOfWeekString);
				return addTimeOrdinal(ret);
			}
			String ret = DateHandler.firstOccurenceOf(dayOfWeekString);
			return addTimeOrdinal(ret);
		}
		
		return addTimeOrdinal(string);
	}
	
	/**
	 * 'addTimeOrdinal' method.
	 * @param string
	 * @return result of replace() via string
	 */
	public static String addTimeOrdinal(String string) {
		
		String replaceWith = "";
		
		String dateString = string.replaceAll("\\D", "");
		System.out.println(dateString);
		int date = Integer.parseInt(dateString);
		if (date == 1) replaceWith = "1st";
		else if (date == 2) replaceWith = "2nd";
		else if (date == 3) replaceWith = "3rd";
		else if (3 < date && date < 21) replaceWith = String.valueOf(date) + "th";
		else if (date == 21) replaceWith = "21st";
		else if (date == 22) replaceWith = "22nd";
		else if (date == 23) replaceWith = "23rd";
		else if (23 < date && date < 31) replaceWith = String.valueOf(date) + "th";
		else if (date == 31) replaceWith = "31st";
		return string.replace(String.valueOf(date), replaceWith);
		
	}
	
	/**
	 * Parses Time tokens
	 * @param string
	 * @return ret
	 */
	public static String parseTimeToken(String string) {
		String ret = "";
		if (string.equals("-")) return string;
		String impreciseTime = "";
		String numericalTime = "";
		if (string == null) return null;
		String[] wordsArr = string.split(" ");
		for (String word : wordsArr) {
			if (impreciseTimeRegex.matcher(word).matches()) impreciseTime = word;
			if (timeRegex.matcher(word).matches()) numericalTime = word;
		}
		if (!impreciseTime.equals("")) {
			switch (impreciseTime.toLowerCase()) {
			case "morning":
				ret = "09:00";
				break;
			case "midday":
				ret = "12:00";
				break;
			case "afternoon":
				ret = "15:00";
				break;
			case "evening":
				ret = "20:00";
			default:
				break;
			}
		}
		
		if (!numericalTime.equals("")) {
			switch (numericalTime.substring(numericalTime.length() - 2)) {
			case "am":
				ret = numericalTime.substring(0, numericalTime.length() - 2);
				if (ret.length() == 1) ret = "0" + ret;
				ret += ":00";
				break;
			case "pm":
				ret = numericalTime.substring(0, numericalTime.length() - 2);
				int value = Integer.parseInt(ret);
				value += 12;
				ret = String.valueOf(value) + ":00";
				break;
			default:
				ret = numericalTime;
				break;
			}
			
		}
		return ret;
	}
	
	/**
	 * Method to capitalise first letter of a string.
	 * @param string
	 * @return string or ret
	 */
	public static String capitalise(String string) {	
		String ret = "";
		
		if (string == null || string.length() == 0) return string;
		
		ret += string.substring(0, 1).toUpperCase(); // Appends first letter as a capital letter
		ret += string.substring(1, string.length()); // Appends remainder of string
				
		return ret;
	}
	
	/**
	 * For each regex in a Pattern of regexes, if string matches (via matcher) then return true.
	 * @param string
	 * @return either true or false
	 */
	public static boolean matchesDate(String string) {
		
		Pattern[] dateRegexes = {dateOrdinalRegex, dayRegex, monthRegex, dateRegex};
		
		for (Pattern p : dateRegexes) {
			
			if (p.matcher(string).matches()) return true;
			
		}
		
		return false;
		
	}
	
}

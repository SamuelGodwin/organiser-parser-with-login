package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This is my 'DateHandler' class in model. 
 * This class forms part of the model of our MVC structure.
 * This class and its method are public, so are accessible anywhere.
 * @author Samuel
 *
 */
public class DateHandler {
	
	private static Pattern mondayRegex = Pattern.compile("mon(day)?", Pattern.CASE_INSENSITIVE);
	private static Pattern tuesdayRegex = Pattern.compile("tue(sday)?", Pattern.CASE_INSENSITIVE);
	private static Pattern wednesdayRegex = Pattern.compile("wed(nesday)?", Pattern.CASE_INSENSITIVE);
	private static Pattern thursdayRegex = Pattern.compile("thu(rsday)?", Pattern.CASE_INSENSITIVE);
	private static Pattern fridayRegex = Pattern.compile("fri(day)?", Pattern.CASE_INSENSITIVE);
	private static Pattern saturdayRegex = Pattern.compile("sat(urday)?", Pattern.CASE_INSENSITIVE);
	private static Pattern sundayRegex = Pattern.compile("sun(day)?", Pattern.CASE_INSENSITIVE);
	
	public DateHandler() {
		
	}
	public static void main(String[] a) {
		
		System.out.println(firstOccurenceOf("monday"));
		System.out.println(nextOccurenceOf("monday"));

	}
	
	/**
	 * 'firstOccurenceOf' method.
	 * @param passedDay
	 * @return formattedDate
	 */
	public static String firstOccurenceOf(String passedDay) {
		
		Calendar now = Calendar.getInstance();
		
		int dayToday = now.get(Calendar.DAY_OF_WEEK);
		dayToday = nicerFormat(dayToday);

		int parsedDay = -1;
		
		if (mondayRegex.matcher(passedDay).matches()) {
			parsedDay = Calendar.MONDAY;
		} else if (tuesdayRegex.matcher(passedDay).matches()) {
			parsedDay = Calendar.TUESDAY;
		} else if (wednesdayRegex.matcher(passedDay).matches()) {
			parsedDay = Calendar.WEDNESDAY;
		} else if (thursdayRegex.matcher(passedDay).matches()) {
			parsedDay = Calendar.THURSDAY;
		} else if (fridayRegex.matcher(passedDay).matches()) {
			parsedDay = Calendar.FRIDAY;
		} else if (saturdayRegex.matcher(passedDay).matches()) {
			parsedDay = Calendar.SATURDAY;
		} else if (sundayRegex.matcher(passedDay).matches()) {
			parsedDay = Calendar.SUNDAY;
		}
		
		parsedDay = nicerFormat(parsedDay);
		
		int difference = difference(dayToday, parsedDay);
		
		now.add(Calendar.DAY_OF_YEAR, difference);
		
		Date date = now.getTime();  
		String formattedDate = new SimpleDateFormat("EEEE d MMMM").format(date);
		return formattedDate;
		
	}
	
	/**
	 * 'nextOccurenceOf' method.
	 * @param passedDay
	 * @return formattedDate
	 */
	public static String nextOccurenceOf(String passedDay) {
		
		Calendar now = Calendar.getInstance();
		
		int dayToday = now.get(Calendar.DAY_OF_WEEK);
		dayToday = nicerFormat(dayToday);

		int parsedDay = -1;
		
		if (mondayRegex.matcher(passedDay).matches()) {
			parsedDay = Calendar.MONDAY;
		} else if (tuesdayRegex.matcher(passedDay).matches()) {
			parsedDay = Calendar.TUESDAY;
		} else if (wednesdayRegex.matcher(passedDay).matches()) {
			parsedDay = Calendar.WEDNESDAY;
		} else if (thursdayRegex.matcher(passedDay).matches()) {
			parsedDay = Calendar.THURSDAY;
		} else if (fridayRegex.matcher(passedDay).matches()) {
			parsedDay = Calendar.FRIDAY;
		} else if (saturdayRegex.matcher(passedDay).matches()) {
			parsedDay = Calendar.SATURDAY;
		} else if (sundayRegex.matcher(passedDay).matches()) {
			parsedDay = Calendar.SUNDAY;
		}
		
		parsedDay = nicerFormat(parsedDay);
		
		int difference = difference(dayToday, parsedDay);
		
		now.add(Calendar.DAY_OF_YEAR, difference + 7);
		
		Date date = now.getTime();  
		String formattedDate = new SimpleDateFormat("EEEE d MMMM").format(date);
		return formattedDate;
		
	}
	
	/**
	 * A method for a nicer format in terms of date.
	 * @param n
	 * @return n > 1 ? n - 1 : n + 6.
	 */
	public static int nicerFormat(int n) {
		
		return n > 1 ? n - 1 : n + 6;
		
	}
	
	/**
	 * 'difference' method.
	 * @param currentDay
	 * @param passedDay
	 * @return difference of days i.e. passedDay - currentDay
	 */
	private static int difference(int currentDay, int passedDay) {
		
		if (passedDay == currentDay) return 7;
		if (passedDay < currentDay) return 7 - (currentDay - passedDay);
		if (passedDay > currentDay) return passedDay - currentDay;
		return 0;
		
	}
}

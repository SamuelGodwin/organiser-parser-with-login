package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This is my 'DateTesting' class in model. 
 * This class forms part of my model of our MVC structure.
 * This class and its method are public, so are accessible anywhere.
 * @author Samuel
 *
 */
public class DateTesting {

	public static void main(String[] a) {
		
		Calendar now = Calendar.getInstance();
		
		int dayToday = now.get(Calendar.DAY_OF_WEEK);
		dayToday = nicerFormat(dayToday);
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		
		Pattern dayRegex = Pattern.compile(
				"(mon|tue(s)?|wed(nes)?|thu(rs)?|fri|sat(ur)?|sun)(day)?", Pattern.CASE_INSENSITIVE);
		
		Pattern mondayRegex = Pattern.compile("mon(day)?", Pattern.CASE_INSENSITIVE);
		Pattern tuesdayRegex = Pattern.compile("tue(sday)?", Pattern.CASE_INSENSITIVE);
		Pattern wednesdayRegex = Pattern.compile("wed(nesday)?", Pattern.CASE_INSENSITIVE);
		Pattern thursdayRegex = Pattern.compile("thu(rsday)?", Pattern.CASE_INSENSITIVE);
		Pattern fridayRegex = Pattern.compile("fri(day)?", Pattern.CASE_INSENSITIVE);
		Pattern saturdayRegex = Pattern.compile("sat(urday)?", Pattern.CASE_INSENSITIVE);
		Pattern sundayRegex = Pattern.compile("sun(day)?", Pattern.CASE_INSENSITIVE);
		
		int passedDay = -1;
		
		if (mondayRegex.matcher(input).matches()) {
			passedDay = Calendar.MONDAY;
		} else if (tuesdayRegex.matcher(input).matches()) {
			passedDay = Calendar.TUESDAY;
		} else if (wednesdayRegex.matcher(input).matches()) {
			passedDay = Calendar.WEDNESDAY;
		} else if (thursdayRegex.matcher(input).matches()) {
			passedDay = Calendar.THURSDAY;
		} else if (fridayRegex.matcher(input).matches()) {
			passedDay = Calendar.FRIDAY;
		} else if (saturdayRegex.matcher(input).matches()) {
			passedDay = Calendar.SATURDAY;
		} else if (sundayRegex.matcher(input).matches()) {
			passedDay = Calendar.SUNDAY;
		}
		
		passedDay = nicerFormat(passedDay);
		
		System.out.println(dayToday);
		
		System.out.println(passedDay);
		
		int difference = difference(dayToday, passedDay);
		now.add(Calendar.DAY_OF_YEAR, difference);
		
		Date date = now.getTime();  
		String format = new SimpleDateFormat("yyyy/MM/dd").format(date);
		System.out.println("Next occurence of passed day" + format);
		
	}
	
	/**
	 * A method for a nicer format in terms of date.
	 * @param n
	 * @return either n - 1 or n + 6.
	 */
	public static int nicerFormat(int n) {
		
		if (n > 1) return n - 1;
		return n + 6;
		
	}
	
	/**
	 * 'difference'.
	 * @param currentDay
	 * @param passedDay
	 * @return difference of days i.e. passedDay - currentDay
	 */
	public static int difference(int currentDay, int passedDay) {
		
		if (passedDay == currentDay) return 7;
		if (passedDay < currentDay) return 7 - (currentDay - passedDay);
		if (passedDay > currentDay) return passedDay - currentDay;
		return 0;
		
	}
}

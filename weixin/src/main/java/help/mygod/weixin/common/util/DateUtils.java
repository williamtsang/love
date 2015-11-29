// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DateUtils.java

package help.mygod.weixin.common.util;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {

	public DateUtils() {}

	public static String dateToString(Date date) {
		return dateToString(date, "{0}-{1}-{2}T{3}:{4}:{5}");
	}

	public static String toUTCDateFormat(Date date) {
		return dateToString(date, "{0}-{1}-{2}T{3}:{4}:{5}Z");
	}

	private static String dateToString(Date date, String format) {
		GregorianCalendar cal = new GregorianCalendar(UTC_TIME_ZONE);
		cal.setTime(date);
		String params[] = new String[6];
		params[0] = formatInteger(cal.get(1), 4);
		params[1] = formatInteger(cal.get(2) + 1, 2);
		params[2] = formatInteger(cal.get(5), 2);
		params[3] = formatInteger(cal.get(11), 2);
		params[4] = formatInteger(cal.get(12), 2);
		params[5] = formatInteger(cal.get(13), 2);
		return MessageFormat.format(format, (Object[]) params);
	}

	/**
	 * 将日期类型转换成指定格式的日期字符串
	 * 
	 * @param date 待转换的日期
	 * @param dateFormat 日期格式字符串
	 * @return String
	 */
	public static String convertDateToStr(Date date, String dateFormat) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	/**
	 * 将指定格式的字符串转换成日期类型
	 * 
	 * @param date 待转换的日期字符串
	 * @param dateFormat 日期格式字符串
	 * @return Date
	 */
	public static Date convertStrToDate(String dateStr, String dateFormat) {
		if (dateStr == null || dateStr.equals("")) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			return sdf.parse(dateStr);
		} catch (Exception e) {
			throw new RuntimeException("DateUtil.convertStrToDate():" + e.getMessage());
		}
	}

	private static String formatInteger(int value, int length) {
		String val = Integer.toString(value);
		int diff = length - val.length();
		for (int i = 0; i < diff; i++)
			val = (new StringBuilder()).append("0").append(val).toString();

		return val;
	}
	
	public static Date convertStrToDate(String str){
		if(str == null || "".equals(str))
			return null;
		
		try {
			str = str.replace("/", "-");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.parse(str);
		} catch (ParseException e) {
			throw new RuntimeException("Parse date failed", e);
		}
	}

	public static Date stringToDate(String strDate) throws ParseException {
		int diffTime[] = null;
		boolean plusTime = true;
		int idxT = strDate.indexOf('T');
		if (idxT == -1) throw new ParseException("Invalid Date Format", 0);
		int idxDiffUTC = strDate.indexOf('-', idxT);
		if (idxDiffUTC == -1) {
			idxDiffUTC = strDate.indexOf('+', idxT);
			plusTime = false;
		}
		if (idxDiffUTC != -1) {
			diffTime = getDiffTime(strDate, idxDiffUTC);
			strDate = strDate.substring(0, idxDiffUTC);
		}
		int idxMilliSec = strDate.indexOf('.');
		if (idxMilliSec != -1) {
			strDate = strDate.substring(0, idxMilliSec);
		} else {
			char lastChar = strDate.charAt(strDate.length() - 1);
			if (lastChar == 'z' || lastChar == 'Z') strDate = strDate.substring(0, strDate.length() - 1);
		}
		return createDate(strDate, diffTime, plusTime);
	}

	private static int[] getDiffTime(String strDate, int idx) throws ParseException {
		String strDiff = strDate.substring(idx + 1, strDate.length() - 1);
		int diffArray[] = new int[2];
		int colonIdx = strDiff.indexOf(':');
		if (colonIdx == -1) throw new ParseException("Invalid Date Format", 0);
		try {
			diffArray[0] = Integer.parseInt(strDiff.substring(0, colonIdx));
			diffArray[1] = Integer.parseInt(strDiff.substring(colonIdx + 1));
		} catch (NumberFormatException nfe) {
			throw new ParseException("Invalid Date Format", 0);
		}
		return diffArray;
	}

	private static Date createDate(String strDate, int timeDiff[], boolean plusDiff) throws ParseException {
		try {
			int year = Integer.parseInt(strDate.substring(0, 4));
			if (strDate.charAt(4) != '-') throw new ParseException("Invalid Date Format", 0);
			int month = Integer.parseInt(strDate.substring(5, 7)) - 1;
			if (strDate.charAt(7) != '-') throw new ParseException("Invalid Date Format", 0);
			int day = Integer.parseInt(strDate.substring(8, 10));
			if (strDate.charAt(10) != 'T') throw new ParseException("Invalid Date Format", 0);
			int hour = Integer.parseInt(strDate.substring(11, 13));
			if (strDate.charAt(13) != ':') throw new ParseException("Invalid Date Format", 0);
			int minute = Integer.parseInt(strDate.substring(14, 16));
			int second = 0;
			if (strDate.length() > 17) {
				if (strDate.charAt(16) != ':') throw new ParseException("Invalid Date Format", 0);
				second = Integer.parseInt(strDate.substring(17, 19));
			}
			GregorianCalendar cal = new GregorianCalendar(year, month, day, hour, minute, second);
			cal.setTimeZone(UTC_TIME_ZONE);
			if (timeDiff != null) {
				int hourDiff = plusDiff ? timeDiff[0] : -1 * timeDiff[0];
				int minuteDiff = plusDiff ? timeDiff[1] : -1 * timeDiff[1];
				cal.add(10, hourDiff);
				cal.add(12, minuteDiff);
			}
			return cal.getTime();
		} catch (NumberFormatException nfe) {
			throw new ParseException("Invalid Date Format", 0);
		}
	}

	private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");

	// private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	// private static SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static void main(String[] args) throws Exception {
//		System.out.println(strToDateTime("2008-11-25 9:9:9"));
		// System.out.println(addDays(new Date(),-1));
		//System.out.println(3 / 12);
		//System.out.println(25 / 12);
	}

	public static String formatYearAndMonth(Date time) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		if (time == null) return "";
		else
			return df.format(time);
	}

	public static String getCurrentDateStr() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(new java.util.Date());
	}
	
	public static String getCurrentTimeStr() {
		SimpleDateFormat df = new SimpleDateFormat("HHmmss");
		return df.format(new java.util.Date());
	}

	public static String getTodayStr() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new java.util.Date());
	}

	public static String formatDate(Date time) {
		if (time == null) return "";
		else {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(time);
		}
	}

	public static String formatMinuteTime(Date time) {
		if (time == null) return "";
		else {
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return df2.format(time);
		}
	}

	public static String formatTime(Date time) {

		if (time == null) return "";
		else {
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df2.format(time);
		}
	}

	public static Calendar getFirstDayOfCurrentDay(Calendar cal) {
		Calendar calFirst = (Calendar) cal.clone();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		calFirst.add(Calendar.DATE, -dayOfWeek + 1);
		return calFirst;
	}

	public static Calendar getLastDayOfCurrentDay(Calendar cal) {
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		Calendar calLast = (Calendar) cal.clone();
		calLast.add(Calendar.DATE, 7 - dayOfWeek);
		return calLast;
	}

	public static Date strToDate(String s) {
		try {
			if (s == null || "".equals(s)) return null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.parse(s);
		} catch (ParseException e) {
			throw new RuntimeException("pasrse date error", e);
		}
	}

	public static Date strToDateTime(String s) {
		try {
			if (s == null || "".equals(s)) return null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.parse(s);
		} catch (Exception e) {
			throw new RuntimeException("pasrse date error", e);
		}
	}

	public static long getCompareDate(String startDate, String endDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = formatter.parse(startDate);
		Date date2 = formatter.parse(endDate);
		long l = date2.getTime() - date1.getTime();
		long d = l / (24 * 60 * 60 * 1000);
		return d;
	}
	
	public static long getCompareDate(Date startDate, Date endDate) {
		long l = startDate.getTime() - endDate.getTime();
//		long d = l / (24 * 60 * 60 * 1000);
//		return d;
		return l;
	}

	public static Date convertLastTimeOfDate(Date date) {
		if (date == null) return null;
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(date.getTime());
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND, 59);
		ca.set(Calendar.MILLISECOND, 999);
		return ca.getTime();
	}
	
	public static Date convertStartTimeOfDate(Date date) {
		if (date == null) return null;
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(date.getTime());
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}

	public static Date addDays(Date date, int days) {
		if (date == null) return null;
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(date.getTime());
		ca.add(Calendar.DAY_OF_YEAR, days);
		return ca.getTime();
	}

	/**
	 * 根据指定年度和月份获取月初日期
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(int year, int month) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 根据指定年度和月份获取月末日期
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(int year, int month) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);

		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		calendar.set(Calendar.DAY_OF_MONTH, maxDay);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		return calendar.getTime();
	}

	public static Date getFirstDateOfThisYear() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.MONTH, 0);
		ca.set(Calendar.DATE, 1);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}

	/**
	 * 获取指定日期的年度
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getYearOfDate(Date date) {

		if (date == null) {
			throw new IllegalArgumentException("Invalid argument, date is null");
		}

		return date.getYear() + 1900;
	}

	/**
	 * 获取指定日期的月份
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getMonthOfDate(Date date) {

		if (date == null) {
			throw new IllegalArgumentException("Invalid argument, date is null");
		}

		return date.getMonth() + 1;
	}

	/**
	 * 获取指定日期所在月份的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {

		if (date == null) {
			throw new IllegalArgumentException("Invalid argument, date is null");
		}

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		int maxDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);

		calendar1.set(Calendar.DAY_OF_MONTH, maxDay);

		return calendar1.getTime();
	}
	
	public static String getLastDayOfMonth(Date date,String dateFormat) {
		Date tmp = getLastDayOfMonth(date);
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);	
		return sdf.format(tmp);
	}

	/**
	 * 获取指定日期所在月份的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {

		if (date == null) {
			throw new IllegalArgumentException("Invalid argument, date is null");
		}

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		calendar1.set(Calendar.DAY_OF_MONTH, 1);

		return calendar1.getTime();
	}

	/**
	 * 获取指定日期所在月份的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfMonth(Date date,String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);	
		Date tmp = getFirstDayOfMonth(date);
		return sdf.format(tmp);
	}
	
	public static Date getDateByFormatString(String dateString, String regex) throws Exception {
		try {
			String[] array = dateString.split(regex);
			Calendar ca = Calendar.getInstance();
			int year = Integer.valueOf(array[0]).intValue();
			int month = Integer.valueOf(array[1]).intValue() - 1;
			int date = Integer.valueOf(array[2]).intValue();
			ca.set(year, month, date, 0, 0, 0);
			ca.set(Calendar.MILLISECOND, 0);
			return ca.getTime();
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 将指定时间的月份字段加1
	 * 
	 * @param d 原始的时间
	 * @return 月份加1后的时间
	 */
	public static Date increateMonth(Date d){
		if(d==null) return null;
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(d.getTime());
		ca.add(Calendar.MONTH, 1);
		return ca.getTime();
	}
	
	/**
	 * 将指定时间的月份字段减1
	 * 
	 * @param d 原始的时间
	 * @return 月份加1后的时间
	 */
	public static Date decreateMonth(Date d){
		if(d==null)return null;
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(d.getTime());
		ca.add(Calendar.MONTH, -1);
		return ca.getTime();
	}
	
	public static Date increaseDate(Date d){
		if(d==null)
			return null;
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(d.getTime());
		ca.add(Calendar.DATE, 1);
		return ca.getTime();
	}
	
	public static Date decreaseDate(Date d){
		if(d==null)
			return null;
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(d.getTime());
		ca.add(Calendar.DATE, -1);
		return ca.getTime();
	}
	
	/**
	 * 获取两个日期间的月数差
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getDiffMonths(Date beginDate, Date endDate) {
		
		int curYear = DateUtils.getYearOfDate(beginDate);
		int endYear = DateUtils.getYearOfDate(endDate);
		int curMonths = DateUtils.getMonthOfDate(beginDate);
		int endMonths = DateUtils.getMonthOfDate(endDate);
		int uy = (endYear - curYear) * 12 + (endMonths - curMonths); 
		
		return uy;
	}

	/**
	 * 获取两个日期间的年数差
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getDiffYears(Date beginDate, Date endDate) {
		
		int curYear = DateUtils.getYearOfDate(beginDate);
		int endYear = DateUtils.getYearOfDate(endDate);
		int curMonths = DateUtils.getMonthOfDate(beginDate);
		int endMonths = DateUtils.getMonthOfDate(endDate);
		int uy = (endYear - curYear) * 12 + (endMonths - curMonths); 
		
		if (uy < 12) {
			return 1;
		} else {
			return uy / 12;
		}
	}
	
	/**
	 * 比较两个日期之间的天数差异，例如：如果left比right晚一天，返回1，如果相等返回0，
	 * 如果left比right早一天，返回-1
	 * @param left
	 * @param right
	 * @return int 差异天数
	 */
	public static int getDiffDays(Date left, Date right){
		GregorianCalendar leftCaldr = new GregorianCalendar();
		GregorianCalendar rightCaldr = new GregorianCalendar();
		leftCaldr.setTime(left);
		rightCaldr.setTime(right);
		
		leftCaldr.set(GregorianCalendar.HOUR_OF_DAY, 0);
		leftCaldr.set(GregorianCalendar.MINUTE, 0);
		leftCaldr.set(GregorianCalendar.SECOND, 0);
		leftCaldr.set(GregorianCalendar.MILLISECOND, 0);
		
		rightCaldr.set(GregorianCalendar.HOUR_OF_DAY, 0);
		rightCaldr.set(GregorianCalendar.MINUTE, 0);
		rightCaldr.set(GregorianCalendar.SECOND, 0);
		rightCaldr.set(GregorianCalendar.MILLISECOND, 0);
		
		long leftMilSec = leftCaldr.getTimeInMillis();
		long rightMilSec = rightCaldr.getTimeInMillis();
		
		long res = (leftMilSec - rightMilSec)/(24L * 60L *60L * 1000L);
		
		return (int)res;
	}
	
	/**
	 * 将时间置为一天中最早的时刻
	 * @param ori
	 * @return
	 */
	public static Date lower(Date ori){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(ori);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	
	/**
	 * 将时间置为一天最晚的时刻
	 * @param ori
	 * @return
	 */
	public static Date upper(Date ori){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(ori);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		
		return calendar.getTime();
	}
	

	public final static int WEEKEND_SATURDAY = 7;
	public final static int WEEKEND_SUNDAY = 1;


	
}

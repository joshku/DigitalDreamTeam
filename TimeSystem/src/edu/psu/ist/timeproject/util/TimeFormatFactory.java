package edu.psu.ist.timeproject.util;

import java.text.SimpleDateFormat;

public class TimeFormatFactory {
	public static SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm:ss");
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	public static SimpleDateFormat fileFormat = new SimpleDateFormat("yyyy-MM-dd_kkmmss"); 
}

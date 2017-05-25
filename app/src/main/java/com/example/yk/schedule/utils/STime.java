package com.example.yk.schedule.utils;

public class STime {
	private int hour;
	private int minutes;
	public STime(int hour, int minutes) {
		this.hour = hour;
		this.minutes = minutes;
	}
	public int getHour() {
		return hour;
	}
	public int getMinutes() {
		return minutes;
	}
	public static STime getTime(String t)
	{
		t = t.trim();
		return new STime(Integer.parseInt(t.substring(0, 2)), Integer.parseInt(t.substring(3, 5)));
	}
	public static String getTimeBack(STime stime)
	{
		String mytime ="";
		if(stime.getHour() >= 10)
		{
			mytime += String.valueOf(stime.getHour());
		}
		else
		{
			mytime += "0"+String.valueOf(stime.getHour());
		}
		mytime += ":";
		if(stime.getMinutes() >= 10)
		{
			mytime += String.valueOf(stime.getMinutes());
		}
		else
		{
			mytime += "0"+String.valueOf(stime.getMinutes());
		}
		return mytime;
	}
}

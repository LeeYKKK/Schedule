package com.example.yk.schedule.utils;

/**
 * lyk
 */
public class TipUtil {
	public static String getTipWithFormate(int p)
	{
		String tip="";
		switch(p)
		{
			case 0:
				tip="1-2节       ";
				break;
			case 1:
				tip="3-4节       ";
				break;
			case 2:
				tip="5-6节       ";
				break;
			case 3:
				tip="7-8节       ";
				break;
			case 4:
				tip="9-10节     ";
				break;
			case 5:
				tip="11-12节   ";
				break;
		}
		return tip;
	}
	public static String getTheClass(int c)
	{
		String s = "";
		switch(c)
		{
			case 0:
				s="1-2";
				break;
			case 1:
				s="3-4";
				break;
			case 2:
				s="5-6";
				break;
			case 3:
				s="7-8";
				break;
			case 4:
				s="9-10";
				break;
			case 5:
				s="11-12";
				break;
		}
		return s;
	}
	public static String getTheWeek(int c)
	{
		String s = "";
		switch(c)
		{
			case 1:
				s="Mon";
				break;
			case 2:
				s="Tue";
				break;
			case 3:
				s="Wed";
				break;
			case 4:
				s="Thu";
				break;
			case 5:
				s="Fri";
				break;
			case 6:
				s="Sar";
				break;
		}
		return s;
	}
}

package com.sjsu.cmpe226.mesonet.vo;

import java.sql.Date;

/** 
 * The DateMaster class is VO for the date master table.
 * @author krish
 *
 */
public class DateMasterVO {

	public int rowId = 0;
	public Date dateWid = null;
	public int year = 0;
	public int quarter = 0;
	public int month = 0;
	public int week = 0;
	public int weekOfYear = 0;
	public int dayOfMonth = 0;
	public int dayOfYear = 0;
	public String monthName = "";
	public String dayName = "";
	public String yearAndMonth = "";
	public String yearWeek = "";
	public String uiDate = "";
	public String firstLastDayOfWeek = "";
	public String shortDate = "";
	public String shortMonth = "";
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public Date getDateWid() {
		return dateWid;
	}
	public void setDateWid(Date dateWid) {
		this.dateWid = dateWid;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getWeekOfYear() {
		return weekOfYear;
	}
	public void setWeekOfYear(int weekOfYear) {
		this.weekOfYear = weekOfYear;
	}
	public int getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	public int getDayOfYear() {
		return dayOfYear;
	}
	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public String getDayName() {
		return dayName;
	}
	public void setDayName(String dayName) {
		this.dayName = dayName;
	}
	public String getYearAndMonth() {
		return yearAndMonth;
	}
	public void setYearAndMonth(String yearAndMonth) {
		this.yearAndMonth = yearAndMonth;
	}
	public String getYearWeek() {
		return yearWeek;
	}
	public void setYearWeek(String yearWeek) {
		this.yearWeek = yearWeek;
	}
	public String getUiDate() {
		return uiDate;
	}
	public void setUiDate(String uiDate) {
		this.uiDate = uiDate;
	}
	public String getFirstLastDayOfWeek() {
		return firstLastDayOfWeek;
	}
	public void setFirstLastDayOfWeek(String firstLastDayOfWeek) {
		this.firstLastDayOfWeek = firstLastDayOfWeek;
	}
	public String getShortDate() {
		return shortDate;
	}
	public void setShortDate(String shortDate) {
		this.shortDate = shortDate;
	}
	public String getShortMonth() {
		return shortMonth;
	}
	public void setShortMonth(String shortMonth) {
		this.shortMonth = shortMonth;
	}
	
	
	
	
}

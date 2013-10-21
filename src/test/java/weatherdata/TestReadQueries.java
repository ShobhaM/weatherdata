package weatherdata;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;

import com.sjsu.cmpe226.mesonet.constants.WeatherDataConstants;
import com.sjsu.cmpe226.mesonet.executor.BatchLoadDataWithHibernate;
import com.sjsu.cmpe226.mesonet.executor.LoadDataWithHibernate;
import com.sjsu.cmpe226.mesonet.executor.LoadDataWithJDBC;
import com.sjsu.cmpe226.mesonet.jdbc.dao.WeatherDataJDBCDao;

public class TestReadQueries {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	
	@Test
	public void testJDBCGetAvgTimeForStnWithDateRange(){
		WeatherDataJDBCDao weatherDataJDBCDao = new WeatherDataJDBCDao();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd/hhmm");
		java.util.Date dt1 = null;
        java.sql.Date fromDate = null;
        java.sql.Date toDate = null;
        try {
			dt1 = df.parse("20130101/0000");
			fromDate = new java.sql.Date(dt1.getTime());
			System.out.println(""+fromDate);
			dt1 = df.parse("20131031/0000");
			toDate = new java.sql.Date(dt1.getTime());
			System.out.println(""+toDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		weatherDataJDBCDao.getAvgTempForStnsWithinTimeRange(fromDate, toDate);
	}
	
	//@SuppressWarnings("deprecation")
	@Test
	public void testDateMasterDemo1(){
		WeatherDataJDBCDao weatherDataJDBCDao = new WeatherDataJDBCDao();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd/hhmm");
		java.util.Date dt1 = null;
        java.sql.Date fromDate = null;
        java.sql.Date toDate = null;
        try {
			dt1 = df.parse("20130830/0000");
			fromDate = new java.sql.Date(dt1.getTime());
			System.out.println(""+fromDate);
			dt1 = df.parse("20130905/0000");
			toDate = new java.sql.Date(dt1.getTime());
			System.out.println(""+toDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		weatherDataJDBCDao.demoWithDateMasterTableInSchema("BULLF", fromDate, toDate);
	}
	
	@Test
	public void testUtilDate(){
		Timestamp t1 = new Timestamp(System.currentTimeMillis());
		System.out.println(t1.getTime());
		System.out.println(t1.getTime());
	}
	
	/**
	 * The test case is to get the average temperature group by station id using latitude and longitude.
	 * This is by querying against the normalized schema or denormalized schema based on query types.
	 */
	@Test
	public void getAvgTempGroupByStationNorm1(){
		WeatherDataJDBCDao weatherDataJDBCDao = new WeatherDataJDBCDao();
		//Query types can be normalized with latitude and longitude or denormalized with latitude and longitude.
		//The values are normalizedOnlyLat, normalizedLatLon, denormalizedOnlyLat, denormalizedLatLon
		String queryType = "normalizedOnlyLat";
		System.out.println(""+(new Timestamp(System.currentTimeMillis())));
		weatherDataJDBCDao.getAvgTempGroupByStationNorm1(queryType);
		System.out.println(""+(new Timestamp(System.currentTimeMillis())));
	}
	
	/**
	 * The below test method serves to provide a sample off what additional feature or application we wanted to provide.
	 * The functional test method is to get the average temp for any quarter or year or week in an year or month in an year.
	 * This functionality supports providing report detailing about the weather trend.
	 * In the below method, we are getting the average temp for a station and the report would detail which week it was and for which year.
	 * So we are quirying for eg. Station ID = MU101 from date range 1/1/2008 to 31/12/2013
	 */
	@Test
	public void testWeatherReportAppUsingDateMaster(){
		WeatherDataJDBCDao weatherDataJDBCDao = new WeatherDataJDBCDao();
		System.out.println(""+(new Timestamp(System.currentTimeMillis())));
		weatherDataJDBCDao.testWeatherReportAppUsingDateMaster();
		System.out.println(""+(new Timestamp(System.currentTimeMillis())));
	}
	
}

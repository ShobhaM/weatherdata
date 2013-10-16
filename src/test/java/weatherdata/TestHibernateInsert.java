/**
 * 
 */
package weatherdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;

import com.sjsu.cmpe226.mesonet.constants.WeatherDataConstants;
import com.sjsu.cmpe226.mesonet.executor.LoadDataWithHibernate;
import com.sjsu.cmpe226.mesonet.executor.LoadDataWithJDBC;
import com.sjsu.cmpe226.mesonet.jdbc.dao.WeatherDataJDBCDao;

/**
 * @author bhargav_sjsu
 * 
 */
public class TestHibernateInsert {

	/*@Test
	public void testHibernateDataInsert() {
		
		LoadDataWithHibernate loadData = new LoadDataWithHibernate();
		Assert.assertTrue(loadData
				.loadDataFiles("/home/krish/Downloads/mesowest.out.20130904T1000","weatherData"));
	}*/
	
	/*@Test
	public void testHibernateMetaDataInsert() {

		LoadDataWithHibernate loadData = new LoadDataWithHibernate();
		Assert.assertTrue(loadData
				.loadDataFiles("/Users/bhargav_sjsu/Documents/weather_data/wd_meta/","metaData"));
	}*/
	
	/*@Test
	public void testJDBCWeatherDataInsert() {
		LoadDataWithJDBC loadData = new LoadDataWithJDBC();
		Assert.assertTrue(loadData
				.loadDataFiles("/home/krish/Downloads/TestData/", "weatherData"));
	}*/

	/*@Test
	public void testJDBCMetaDataInsert() {
		LoadDataWithJDBC loadData = new LoadDataWithJDBC();
		Assert.assertTrue(loadData
				.loadDataFiles("/Users/bhargav_sjsu/Documents/weather_data/wd_meta/", "metaData"));
	}*/
	
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
			dt1 = df.parse("20131031/0000");
			toDate = new java.sql.Date(dt1.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		weatherDataJDBCDao.getAvgTempForStnsWithinTimeRange(fromDate, toDate);
	}
}

/**
 * 
 */
package weatherdata;

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

/**
 * @author bhargav_sjsu
 * 
 */
public class TestHibernateInsert {

	@Test
	public void testHibernateDataInsert() {
		
		LoadDataWithHibernate loadData = new LoadDataWithHibernate();
		Assert.assertTrue(loadData
				.loadDataFiles("/home/krish/Documents/CMPE226/Project1/Test_Data/","weatherData"));
	}
	
	@Test
	public void testHibernateMetaDataInsert() {

		LoadDataWithHibernate loadData = new LoadDataWithHibernate();
		Assert.assertTrue(loadData
				.loadDataFiles("/home/krish/Documents/CMPE226/Project1/Test_MetaData/","metaData"));
	}
}

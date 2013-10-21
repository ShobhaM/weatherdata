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

public class TestHibernateBatchInsert {

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
	public void testBatchUploadHibernateMetaData(){
		BatchLoadDataWithHibernate loadData = new BatchLoadDataWithHibernate();
		Assert.assertTrue(loadData
				.loadDataFiles("/home/krish/Documents/CMPE226/Project1/Test_MetaData/","metaData"));
	}
	
	@Test
	public void testBatchUploadHibernateWeatherData(){
		BatchLoadDataWithHibernate loadData = new BatchLoadDataWithHibernate();
		Assert.assertTrue(loadData
				.loadDataFiles("/home/krish/Documents/CMPE226/Project1/Test_Data/27/","weatherData"));
	}

}

/**
 * 
 */
package weatherdata;

import org.junit.Assert;
import org.junit.Test;

import com.sjsu.cmpe226.mesonet.executor.LoadDataWithHibernate;

/**
 * @author bhargav_sjsu
 * 
 */
public class TestHibernateInsert {

	@Test
	public void testHibernateDataInsert() {
		
		LoadDataWithHibernate loadData = new LoadDataWithHibernate();
		Assert.assertTrue(loadData
				.loadDataFiles("/Users/bhargav_sjsu/Documents/weather_data/test_data/","weatherData"));
	}
	
	@Test
	public void testHibernateMetaDataInsert() {

		LoadDataWithHibernate loadData = new LoadDataWithHibernate();
		Assert.assertTrue(loadData
				.loadDataFiles("/Users/bhargav_sjsu/Documents/weather_data/wd_meta/","metaData"));
	}

}
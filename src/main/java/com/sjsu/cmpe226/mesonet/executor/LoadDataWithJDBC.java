package com.sjsu.cmpe226.mesonet.executor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

import com.sjsu.cmpe226.mesonet.jdbc.dao.WeatherDataJDBCDao;
import com.sjsu.cmpe226.mesonet.jdbc.dao.WeatherMetaDataJDBCDao;
import com.sjsu.cmpe226.mesonet.util.WriteToFile;

/**
 * This class is used to load data from the data files onto the database.
 * The methods would call the DAO class written using Java-JDBC to perform
 * transaction.
 * @author krish
 *
 */
public class LoadDataWithJDBC {

	private static Logger logger = Logger.getLogger(LoadDataWithJDBC.class
			.getName());
	//private StringBuilder data = null;
	private DataInputStream in = null;
	private BufferedReader br = null;
	
	/**
	 * The public empty constructor.
	 */
	public LoadDataWithJDBC(){
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//LoadDataWithJDBC obj = new LoadDataWithJDBC();
		//obj.readAndLoadMetaFromFileMethod("/home/krish/Documents/CMPE226/Project1/updateTrial.tbl");

		LoadDataWithJDBC obj = new LoadDataWithJDBC();
		obj.readAndLoadWeatherDataFromFileMethod("/home/krish/Downloads/mesowest.out.20130904T1000");

	}
	
	/**
	 * Method to real meta data file and load into hash map for further processing.
	 * @param fileName
	 */
	public void readAndLoadMetaFromFileMethod(String fileName){
		try {
			// Initialize the file input stream
			FileInputStream fstream = new FileInputStream(fileName);
			int count = 0;
			// Get DataInputStream object
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			String strLine = null;
			WeatherMetaDataJDBCDao weatherMetaDao = new WeatherMetaDataJDBCDao();

			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// append the content to StringBulder
				count++;

				if (count == 1)
					continue;

				HashMap<String, String> metaInfo = new HashMap<String, String>();
				String[] lineArray = strLine.split(",");
				if (lineArray.length == 18) {
					metaInfo.put("primary_id", lineArray[0]);
					metaInfo.put("secondary_id", lineArray[1]);
					metaInfo.put("station_name", lineArray[2]);
					metaInfo.put("state", lineArray[3]);
					metaInfo.put("country", lineArray[4]);
					metaInfo.put("latitude", lineArray[5]);
					metaInfo.put("longitude", lineArray[6]);
					metaInfo.put("elevation", lineArray[7]);
					metaInfo.put("mesowest_network_id", lineArray[8]);
					metaInfo.put("network_name", lineArray[9]);
					metaInfo.put("status", lineArray[10]);
					metaInfo.put("primary_provider_id", lineArray[11]);
					metaInfo.put("primary_provider", lineArray[12]);
					metaInfo.put("secondary_provider_id", lineArray[13]);
					metaInfo.put("secondary_provider", lineArray[14]);
					metaInfo.put("tertiary_provider_id", lineArray[15]);
					metaInfo.put("tertiary_provider", lineArray[16]);
					metaInfo.put("wims_id", lineArray[17]);
				}
				
				//Added code line to avoid records with Lat and Lon as null or blank --Krish
				if(metaInfo.get("latitude") != null || metaInfo.get("longitude") != null || 
						(!metaInfo.get("latitude").equals("")) || (!metaInfo.get("longitude").equals(""))){
					weatherMetaDao.saveMetaDataToDB(metaInfo);
				}
			}

			// Close the input stream
			in.close();

		} catch (Exception e) {
			logger.severe("Issue with reading file " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.severe("Issue with reading file " + e.getMessage());
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.severe("Issue with reading file " + e.getMessage());
				}
			}
		}
	}
	
	/**
	 * This method is to read weather data from the file and load to the data base.
	 */
	public synchronized void readAndLoadWeatherDataFromFileMethod(String fileName){
		try {
			FileInputStream fipstream = new FileInputStream(fileName);
			System.out.println("Total size of the file to read "
					+ fipstream.available());

			int count = 0;

			in = new DataInputStream(fipstream);
			br = new BufferedReader(new InputStreamReader(in));

			String strLine = null;

			WeatherDataJDBCDao weatherDataDao = new WeatherDataJDBCDao();

			while ((strLine = br.readLine()) != null) {
				count++;

				if (count <= 4)
					continue;

				HashMap<String, String> contentDataInfo = new HashMap<String, String>();
				/*String[] lineArray = strLine.split(" ");
				for(String s:lineArray){
					System.out.println("String is:" + s);
					
					System.out
						.println("length of the lineArray  " + lineArray.length);
				}*/
				Scanner sc = new Scanner(strLine);
				//String[] lineArray = new String[16];
				ArrayList<String> lineArray = new ArrayList<String>();
				
				while(sc.hasNext())
				{
					lineArray.add(sc.next());
				}
				
				
				System.out.println(lineArray.size());
				if (lineArray.size() == 16) {
					contentDataInfo.put("STN", lineArray.get(0));
					contentDataInfo.put("YYMMDD/HHMM", lineArray.get(1));
					contentDataInfo.put("MNET", lineArray.get(2));
					contentDataInfo.put("SLAT", lineArray.get(3));
					contentDataInfo.put("SLON", lineArray.get(4));
					contentDataInfo.put("SELV", lineArray.get(5));
					contentDataInfo.put("TMPF", lineArray.get(6));
					contentDataInfo.put("SKNT", lineArray.get(7));
					contentDataInfo.put("DRCT", lineArray.get(8));
					contentDataInfo.put("GUST", lineArray.get(9));
					contentDataInfo.put("PMSL", lineArray.get(10));
					contentDataInfo.put("ALTI", lineArray.get(11));
					contentDataInfo.put("DWPF", lineArray.get(12));
					contentDataInfo.put("RELH", lineArray.get(13));
					contentDataInfo.put("WTHR", lineArray.get(14));
					contentDataInfo.put("P24I", lineArray.get(15));
				}
				//System.out.println("completed forming hashmap successfully");
				
				
				//Added code line to avoid records with Lat and Lon as null or blank --Krish
				if(contentDataInfo.get("SLAT") != null || contentDataInfo.get("SLON") != null || 
						(!contentDataInfo.get("SLAT").equals("")) || (!contentDataInfo.get("SLON").equals(""))){
					weatherDataDao.saveWeatherDataToDB(contentDataInfo);
				}
				sc.close();
			}

			in.close();

		} catch (Exception e) {
			logger.severe("Issue with reading file " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.severe("Issue with reading file " + e.getMessage());
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.severe("Issue with reading file " + e.getMessage());
				}
			}
		}
	}
	
	/**
	 * The method is used while testing to load large number of records for either weatherData or metaData.
	 * @param filePath
	 * @param dataType
	 * @return
	 */
	public boolean loadDataFiles(String filePath, String dataType) {
		File folder = new File(filePath);
		LoadDataWithJDBC obj2 = new LoadDataWithJDBC();
		String filename = "";
		File[] listOfFiles = folder.listFiles();

		try {

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile() && listOfFiles[i].getName().toString() != ".DS_Store") {
					filename = listOfFiles[i].getName();
					Date date = new Date();
					
					//Start Time
					WriteToFile.appendToFileMethod(date.toString(), "/Users/bhargav_sjsu/Documents/logLoadTime.txt");
					
					if(dataType.equals("weatherData"))
						obj2.readAndLoadWeatherDataFromFileMethod(filePath+filename);
					else
						obj2.readAndLoadMetaFromFileMethod(filePath+filename);

					//End Time
					WriteToFile.appendToFileMethod(date.toString(), "/Users/bhargav_sjsu/Documents/logLoadTime.txt");
					
					File movefile = new File(filePath+filename);
					if (movefile.renameTo(new File(filePath + "archive/"
							+ movefile.getName()))) {
						System.out.println("File is moved successful!");
					} else {
						System.out.println("File is failed to move!");
					}

				}
			}
		} catch (Exception e) {
			System.out.println("File was not processed" + filename);
			e.printStackTrace();
		}

		return true;
	}
}

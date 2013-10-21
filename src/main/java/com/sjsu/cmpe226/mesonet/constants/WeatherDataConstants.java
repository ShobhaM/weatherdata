
package com.sjsu.cmpe226.mesonet.constants;

/**
 * @author Bhargav
 */
public class WeatherDataConstants {

    public static String  PROPERTY_FILE       = "properties.ini";
    public static String  NEW_LINE_CHAR       = "\n";
    public static String  EMAIL_PATTERN       = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //Database connection related constants.
    public static String DB_URL = "jdbc:postgresql://localhost:5432/weatherdata";
    public static String DB_DRIVER = "org.postgresql.Driver";
    public static String DB_USER_NAME = "postgres";
    public static String DB_PASSWORD = "postgres";
    
    //Metadata JDBC Queries.
    public static String INSERT_METADATA_QUERY = "INSERT INTO weatherdata_schema.wd_weather_station_metadata (primary_id, secondary_id,"
    		+ "station_name, state, country, latitude, longitude, last_update_date, insert_date) VALUES (?,?,?,?,?,?,?,?,?)";
    public static String s= "?,?,?,?,?,?,?,?,?,?,?,";
    public static String UPDATE_METADATA_QUERY = "UPDATE weatherdata_schema.wd_weather_station_metadata SET secondary_id = ?,"
    		+ "station_name = ?, state = ?, country = ?, latitude = ?, longitude = ?, last_update_date = ? WHERE primary_id = ?";
    
    //Waether data JDBC queries.
    public static String INSERT_WAETHERDATA_QUERY = "INSERT INTO weatherdata_schema.wd_weather_data (STN, CREATE_TIME, MNET, SLAT, SLON, "
    		+ "SELV, TMPF, SKNT, DRCT, GUST, PMSL, ALTI, DWPF, RELH, WTHR, P24I, LAST_UPDATE_DATE, INSERT_DATE) "
    		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static String UPDATE_WEATHERDATA_QUERY = "UPDATE weatherdata_schema.wd_weather_data SET MNET = ?, SLAT = ?, SLON = ?, SELV = ?,"
    		+ "TMPF = ?, SKNT = ?, DRCT = ?, GUST = ?, PMSL = ?, ALTI = ?, DWPF = ?, RELH = ?, WTHR = ?, P24I = ?, LAST_UPDATE_DATE = ? "
    		+ "WHERE STN = ? and CREATE_TIME = ?";
    
    public static String SELECT_AVGTEMP_FORSTN_WITHDATERANGE = "Select avg(tmpf), stn from weatherdata_schema.wd_weather_data where "
    		+ "create_time >= ? and create_time <= ? group by stn";
    
    public static String SELECT_DEMO_QUERY1 = "select avg(tmpf),dm.week_of_year,dm.year from weatherdata.weatherdata_schema.wd_weather_data wd,"
    		+ "weatherdata.weatherdata_schema.wd_weather_date_master dm where stn= ? "
    		+ "and wd.create_time between ? and ? and dm.date_wid between ? and ? group by dm.week_of_year,dm.year";
    
    public static String GET_AVGTEMP_NORM1 = "Select avg(wd.tmpf), wd.stn "
    		+ "from weatherdata_schema.wd_weather_data wd, weatherdata_schema.wd_weather_station_metadata md "
    		+ "where md.latitude between 27.00000 and 88.00000 and md.primary_id = wd.stn "
    		+ "group by wd.stn";
    
    public static String GET_AVGTEMP_NORM2 = "Select avg(wd.tmpf), wd.stn "
    		+ "from weatherdata_schema.wd_weather_data wd, weatherdata_schema.wd_weather_station_metadata md "
    		+ "where md.latitude between 27.00000 and 88.00000 and md.longitude between -131.00000 and -80.00000 and md.primary_id = wd.stn "
    		+ "group by wd.stn";
    
    public static String GET_AVGTEMP_NORM3 = "Select avg(tmpf), stn "
    		+ "from weatherdata_schema.wd_weather_data "
    		+ "where slat between 27.00000 and 88.00000 "
    		+ "group by stn";
    
    public static String GET_AVGTEMP_NORM4 = "Select avg(tmpf), stn from "
    		+ "weatherdata_schema.wd_weather_data "
    		+ "where slat between 27.00000 and 88.00000 and slon between -131.00000 and -80.00000 "
    		+ "group by stn";

    public static String GET_APPQUERY_DATEMASTER = "select avg(tmpf),dm.week_of_year,dm.year "
    		+ "from weatherdata.weatherdata_schema.wd_weather_data wd, weatherdata.weatherdata_schema.wd_weather_date_master dm "
    		+ "where stn='MU101' and dm.date_wid between '2008-01-01' and '2013-12-31'and dm.row_wid=wd.date_wid "
    		+ "group by dm.week_of_year,dm.year";
}

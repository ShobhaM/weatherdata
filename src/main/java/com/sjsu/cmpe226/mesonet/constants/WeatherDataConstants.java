
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
    public static String UPDATE_WEATHERDATA_QUERY = "UPDATE weatherdata_schema.wd_weather_data SET MNET = ?, SLAT = ?, SLAN = ?, SELV = ?,"
    		+ "TMPF = ?, SKNT = ?, DRCT = ?, GUST = ?, PMSL = ?, ALTI = ?, DWPF = ?, RELH = ?, WTHR = ?, P24I = ?, LAST_UPDATE_DATE = ? "
    		+ "WHERE STN = ? and CREATE_TIME = ?";

}

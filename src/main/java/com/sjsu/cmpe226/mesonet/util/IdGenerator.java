package com.sjsu.cmpe226.mesonet.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
public class IdGenerator implements IdentifierGenerator 
{
    static Integer id;
    static 
    {
        id = 0;
        String url = "jdbc:postgresql://localhost:5432/wdshard1";//jdbc:postgresql://localhost:5432
        String dbusr = "postgres";
        String dbpwd = "postgres";
        try  
        {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, dbusr, dbpwd);
            Statement stmt = con.createStatement();
            String query = "select max(id) from weatherdata_schema.wd_weather_data";
            ResultSet rset = stmt.executeQuery(query);
            if(rset.next()) {
                int tempid = rset.getInt(1);
                if(id<tempid)
                    id = tempid;
            }
            stmt.close();
            con.close();
            url = "jdbc:postgresql://localhost:5432/wdshard2";
            con = DriverManager.getConnection(url, dbusr, dbpwd);
            stmt = con.createStatement();
            query = "select max(id) from weatherdata_schema.wd_weather_data";
            rset = stmt.executeQuery(query);
            if(rset.next()) {
                int tempid = rset.getInt(1);
                if(id<tempid)
                    id = tempid;
            }
            System.out.println("Starting with Key id: " + id);
            stmt.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
  //  @Override
    public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
        id = id +1;
        return id;
    }
}
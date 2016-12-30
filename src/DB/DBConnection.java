/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

/**
 *
 * @author andre
 */
import java.sql.*;

public class DBConnection {
 
    private Connection connection;
    
    public DBConnection()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:parameters.db");
            
        }
        catch(Exception e)
        {
            System.err.println("Class: "+e.getClass()+" Message: "+e.getMessage());
            System.exit(0);
        }
    }
    
    public boolean createTable(String query)
    {
        Statement stm = null;
        boolean success = false;
        
        try
        {
            stm = this.connection.createStatement();
            stm.executeUpdate(query);
            stm.close();
            success = true;
        }
        catch(Exception e)
        {
            System.err.println("Class: "+e.getClass()+" Message: "+e.getMessage());           
        }
        
        return success;
    }
    
    public boolean insert(String query)
    {
        Statement stm = null;
        boolean success = false;
        try
        {
            this.connection.setAutoCommit(false); //Transaction
            stm = this.connection.createStatement();
            stm.executeUpdate(query);
            stm.close();
            success = true;
            this.connection.commit();
        }
        catch(Exception e)
        {
            System.err.println("Class: "+e.getClass()+" Message: "+e.getMessage());            
        }
        
        return success;
    }
    
    public void update(String query)
    {
        insert(query);
    }
    
    public ResultSet select(String query)
    {
        Statement stm = null;
        ResultSet rs = null;
        
        try
        {
            this.connection.setAutoCommit(false);
            
            stm = this.connection.createStatement();
            
            rs = stm.executeQuery(query);
            
        }
        catch(Exception e)
        {
            System.err.println("Class: "+e.getClass()+" Message: "+e.getMessage()); 
        }
        
        return rs;
    }
    
    public void closeConnection()
    {
        try
        {
            this.connection.close();
        }
        catch(Exception e)
        {
            System.err.println("Class: "+e.getClass()+" Message: "+e.getMessage());
            System.exit(0);
        }
    }
    
    public int isEmpty(String tableName)
    {
        boolean empty = false;
        Statement stm = null;
        int id = -1;
        
        try
        {
            this.connection.setAutoCommit(false);
            
            stm = this.connection.createStatement();
            
            ResultSet rs = stm.executeQuery("SELECT * FROM "+tableName);
            
            while(rs.next())
            {
                id = rs.getInt("ID");
            }
                        
         
            rs.close();
            stm.close();
        }
        catch(Exception e)
        {
            System.err.println("Class: "+e.getClass()+" Message: "+e.getMessage());   
        }
        
        return id;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wanzenberg.ulf.mymdi.database;

/**
 *
 * @author Ulf
 */
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.ResultSetMetaData;
import java.sql.SQLException; 
import java.sql.Statement; 
import java.util.Vector;

import net.wanzenberg.ulf.mymdi.common.Constants;

//********************************************************************************
//********************************************************************************
//Klasse DB_sqlite
//********************************************************************************
//********************************************************************************
public class  AccessSqliteDatabase { 

	// ================================================================================
	// database, database access & SQL 
	// ================================================================================
    private String myDatabase;
    private String mySQLStatement;
    private String mySQLStatementType;
    private static final AccessSqliteDatabase dbcontroller = new AccessSqliteDatabase(); 
    private static Connection connection; 
    Statement stmt = null;
    //private static final String DB_PATH = System.getProperty("user.home") + "/" + "mywork.db"; 
    //private static final String DB_PATH = "C:/UWDEV/" + "mywork2.db"; 
    public String DB_PATH = myDatabase; 
    


	// ================================================================================
	// SQLite
	// ================================================================================
    static { 
        try { 
            Class.forName("org.sqlite.JDBC"); 
        } catch (ClassNotFoundException e) { 
            System.err.println("Fehler beim Laden des JDBC-Treibers"); 
            e.printStackTrace(); 
        } 
    } 

    
    
	// ================================================================================
	// ... auxiliary variables 
	// ================================================================================
    public Vector<String> columnNames = new Vector<String>();
    public Vector<Vector> data = new Vector<Vector>();
    
    
    
    
	// =METHODE========================================================================
	// ================================================================================
	// !!! main
	// ================================================================================
	// ================================================================================
    // Der Interpreter nutzt diese Methode als Startpunkt f�r die Ausf�hrung
    // des Programms und beendet es wenn ihr Ende erreicht ist
    public static void main(String[] args) { 
        //System.out.println("AccessSqliteDatabase___public static void main");
        
 		// ================================================================================
		// ================================================================================
		// ... AccessSqliteDatabase
		// ================================================================================
		// ================================================================================
    	AccessSqliteDatabase dbc = AccessSqliteDatabase.getInstance(); 
        
    	
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// handleDBConnection
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        dbc.handleDBConnection(); 
        
        
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// populateVector
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        dbc.populateVector(); 
        
    }     
    
	// +KONSTRUKTOR++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Konstruktor ApplicationMain
    // 1st constructor
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private AccessSqliteDatabase()
    { 
    } 

    
	// +KONSTRUKTOR++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Konstruktor ApplicationMain
    // 2nd constructor
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public AccessSqliteDatabase(String parm_my_database, String parm_my_sql_statement)
    { 
      myDatabase = parm_my_database;
      DB_PATH = parm_my_database;
      System.out.println("DB_PATH: " + DB_PATH); 
      

      mySQLStatement = parm_my_sql_statement;
      
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// handleDBConnection
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        this.handleDBConnection(); 
        
        
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// populateVector
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        this.populateVector(); 
        
    } 

    
	// +KONSTRUKTOR++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Konstruktor ApplicationMain
    // 3rd constructor
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public AccessSqliteDatabase(String parm_my_database, String parm_my_sql_statement, String parm_sql_type)
    { 
		myDatabase = parm_my_database;
		mySQLStatement = parm_my_sql_statement;
		mySQLStatementType = parm_sql_type;
		System.out.println("DB_sqlite 3rd constructor"); 
      
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// handleDBConnection
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        this.handleDBConnection(); 
        
        try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
	        System.out.println("stmt FEHLER");
			e.printStackTrace();
		
		}
    	
        
        System.out.println("stmt KEIN FEHLER");        
        
        
        
        try {
        	System.out.println("temp________SQL-INSERT:" + mySQLStatement);        
			stmt.executeUpdate(mySQLStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
	        System.out.println("executeUpdate FEHLER");
			e.printStackTrace();
		}
        
        
        
        try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //connection.close();
        
        
        
        
        /* Connection c = null;
        Statement stmt = null;
        try {
          Class.forName("org.sqlite.JDBC");
          c = DriverManager.getConnection("jdbc:sqlite:" + "C:\\UWDEV\\mywork.db");
          System.out.println("Opened database successfully");

          stmt = c.createStatement();
          String sql = mySQLStatement; 
          stmt.executeUpdate(sql);
          stmt.close();
          c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        } */
    } 
    
    
	// +KONSTRUKTOR++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Konstruktor ApplicationMain
    // 4th constructor
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    AccessSqliteDatabase(String parm_my_database, String parm_my_sql_statement, String parm_my_expected_result_type, String XYZ)
    { 
      myDatabase = parm_my_database;
      mySQLStatement = parm_my_sql_statement;
      
        //DBController dbc = DB_sqlite.getInstance(); 
        //this.handleDBConnection(); 

      	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// singleDataset
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        this.getSingleDataset(); 
    } 
    
    
    
    
    /** ++++++++++++++++++++++++++++++++++++++++++++++
    *  accessor methods
    * +++++++++++++++++++++++++++++++++++++++++++++++
    */    
	// =METHODE========================================================================
	// ================================================================================
	// AccessSqliteDatabase getInstance
	// ================================================================================
	// ================================================================================
    public static AccessSqliteDatabase getInstance(){ 
        return dbcontroller; 
    } 
    
    

	// =METHODE========================================================================
	// ================================================================================
	// refresh
	// ================================================================================
	// ================================================================================
    public void refresh() { 
    	populateVector(); 
    }     
        
    
    
	// =METHODE========================================================================
	// ================================================================================
	// handleDBConnection
	// ================================================================================
	// ================================================================================
    void handleDBConnection() { 
        /** ----------------------------------------------
        *  ... 
        * -----------------------------------------------
        */
        try { 
            if (connection != null) 
                return; 
            System.out.println("Creating Connection to Database ..." + DB_PATH ); 
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH); 
            if (!connection.isClosed()) 
                System.out.println("...Connection established"); 
        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        } 

        /** ----------------------------------------------
        *  ... 
        * -----------------------------------------------
        */
        Runtime.getRuntime().addShutdownHook(new Thread() { 
            public void run() { 
                try { 
                    if (!connection.isClosed() && connection != null) { 
                        connection.close(); 
                        if (connection.isClosed()) 
                            System.out.println("Connection to Database closed"); 
                    } 
                } catch (SQLException e) { 
                    e.printStackTrace(); 
                } 
            } 
        }); 
    } 

    
    
	// =METHODE========================================================================
	// ================================================================================
	// getSingleDataset
	// ================================================================================
	// ================================================================================
    private void getSingleDataset() { 
        try { 
            Statement stmt = connection.createStatement(); 
            int columns1;

            //ResultSet rs1 = stmt.executeQuery("SELECT * FROM myobjects WHERE myobjects_id = 2"); 
            ResultSet rs1 = stmt.executeQuery("SELECT * FROM object_or_source_metadata WHERE myobjects_id = 2");

            
            /** ----------------------------------------------
            *  ... column names to vector
            * -----------------------------------------------
            */  
            ResultSetMetaData md = rs1.getMetaData();
            columns1 = md.getColumnCount();

            //  Get column names
            for (int i = 1; i <= columns1; i++) {
                //columnNames.addElement( md.getColumnName(i) );
                System.out.println(md.getColumnName(i));
            }
            
            
            /** ----------------------------------------------
            *  ... row values to vector
            * -----------------------------------------------
            */  
            while (rs1.next()) { 
                //Vector<Object> row = new Vector<Object>(columns);
                for (int i = 1; i <= columns1; i++) 
                {
                    //row.addElement( rs.getObject(i) );
                    System.out.println( rs1.getObject(i) );
                }
                //data.addElement( row );
                //System.out.println( row );
            } 
            
            rs1.close();
            
            connection.close(); 
            System.out.println("Connection closed");
            
        } catch (SQLException e) { 
            System.err.println("Couldn't handle DB-Query"); 
            e.printStackTrace(); 
        } 
    } 
    
    
    
    
    /** ++++++++++++++++++++++++++++++++++++++++++++++
     *  mutator methods
     * +++++++++++++++++++++++++++++++++++++++++++++++
     */
	// =METHODE========================================================================
	// ================================================================================
	// populateVector
	// ================================================================================
	// ================================================================================
    void populateVector() { 
        try { 
            Statement stmt = connection.createStatement(); 
            int columns;

            //ResultSet rs = stmt.executeQuery("SELECT * FROM myobjects"); 
            //ResultSet rs = stmt.executeQuery("SELECT * FROM object_or_source_metadata");
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + Constants.C_TABLE);
            
            /** ----------------------------------------------
            *  ... column names to vector
            * -----------------------------------------------
            */  
            ResultSetMetaData md = rs.getMetaData();
            columns = md.getColumnCount();

            //  Get column names
            for (int i = 1; i <= columns; i++) {
                columnNames.addElement( md.getColumnName(i) );
                //System.out.println(md.getColumnName(i));
            }
            
            
            /** ----------------------------------------------
            *  ... row values to vector
            * -----------------------------------------------
            */  
            while (rs.next()) { 
                Vector<Object> row = new Vector<Object>(columns);
                for (int i = 1; i <= columns; i++) 
                {
                    row.addElement( rs.getObject(i) );
                    System.out.println( rs.getObject(i) );
                }
                data.addElement( row );
                //System.out.println( row );
            } 
            
            rs.close();
            
            //connection.close(); 
            //System.out.println("Connection closed");
            
        } catch (SQLException e) { 
            System.err.println("Couldn't handle DB-Query"); 
            e.printStackTrace(); 
        } 
    } 

}
    


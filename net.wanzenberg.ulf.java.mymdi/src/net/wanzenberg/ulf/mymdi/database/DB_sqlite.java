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

public class  DB_sqlite { 

    /** ++++++++++++++++++++++++++++++++++++++++++++++
     * ...
     * +++++++++++++++++++++++++++++++++++++++++++++++
     */
    
    private String myDatabase;
    private String mySQLStatement;
    private String mySQLStatementType;
    
    
    public Vector<String> columnNames = new Vector<String>();
    public Vector<Vector> data = new Vector<Vector>();
    
    private static final DB_sqlite dbcontroller = new DB_sqlite(); 
    private static Connection connection; 
    
    Statement stmt = null;

    //private static final String DB_PATH = System.getProperty("user.home") + "/" + "mywork.db"; 
    //private static final String DB_PATH = "C:/UWDEV/" + "mywork2.db"; 
    public String DB_PATH = myDatabase; 
    

    static { 
        try { 
            Class.forName("org.sqlite.JDBC"); 
        } catch (ClassNotFoundException e) { 
            System.err.println("Fehler beim Laden des JDBC-Treibers"); 
            e.printStackTrace(); 
        } 
    } 

    
    /** ++++++++++++++++++++++++++++++++++++++++++++++
    *  constructor methods
    * +++++++++++++++++++++++++++++++++++++++++++++++
    */
    /** ----------------------------------------------
    *  1st constructor ...
    * -----------------------------------------------
    */    
    private DB_sqlite()
    { 
    } 
     
    /** ----------------------------------------------
    *  2nd constructor ...
    * -----------------------------------------------
    */    
    public DB_sqlite(String parm_my_database, String parm_my_sql_statement)
    { 
      myDatabase = parm_my_database;
      DB_PATH = parm_my_database;
      
      System.out.println("DB_PATH: " + DB_PATH); 

      mySQLStatement = parm_my_sql_statement;
      
        //DBController dbc = DB_sqlite.getInstance(); 
        this.handleDBConnection(); 
        
        
        this.populateVector(); 
    } 

    /** ----------------------------------------------
    *  3rd constructor ...
    * -----------------------------------------------
    */    
    public DB_sqlite(String parm_my_database, String parm_my_sql_statement, String parm_sql_type)
    { 
      myDatabase = parm_my_database;
      mySQLStatement = parm_my_sql_statement;
      mySQLStatementType = parm_sql_type;
      
      System.out.println("DB_sqlite 3rd constructor"); 
      
        //DBController dbc = DB_sqlite.getInstance(); 
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
    
    /** ----------------------------------------------
    *  4th constructor ...
    * -----------------------------------------------
    */    
    DB_sqlite(String parm_my_database, String parm_my_sql_statement, String parm_my_expected_result_type, String XYZ)
    { 
      myDatabase = parm_my_database;
      mySQLStatement = parm_my_sql_statement;
      
        //DBController dbc = DB_sqlite.getInstance(); 
        //this.handleDBConnection(); 
        this.singleDataset(); 
    } 
    
    /** ++++++++++++++++++++++++++++++++++++++++++++++
    *  accessor methods
    * +++++++++++++++++++++++++++++++++++++++++++++++
    */    
    /** ----------------------------------------------
    *  getInstance()
    *  ...
    * -----------------------------------------------
    */
    public static DB_sqlite getInstance(){ 
        return dbcontroller; 
    } 
     
    /** ----------------------------------------------
    * ... handleDBConnection
    * -----------------------------------------------
    */
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

    
    
    /** ++++++++++++++++++++++++++++++++++++++++++++++
     *  mutator methods
     * +++++++++++++++++++++++++++++++++++++++++++++++
     */
    /** ----------------------------------------------
     *  set ...
     * -----------------------------------------------
     */
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

    
    
    
    
    
    
    
    /** ++++++++++++++++++++++++++++++++++++++++++++++
     *  mutator methods
     * +++++++++++++++++++++++++++++++++++++++++++++++
     */
    /** ----------------------------------------------
     *  set ...
     * -----------------------------------------------
     */
    public void refresh() { 
    	populateVector(); 
    }     
    
    
    
    
    
    
    
    /** ----------------------------------------------
     *  NEE set ...
     * -----------------------------------------------
     */
    private void singleDataset() { 
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
    
    
    
    /** ##############################################
     *  main()
     * ###############################################
     */
    // main()
    // Der Interpreter nutzt diese Methode als Startpunkt für die Ausführung
    // des Programms und beendet es wenn ihr Ende erreicht ist
    public static void main(String[] args) { 
        DB_sqlite dbc = DB_sqlite.getInstance(); 
        dbc.handleDBConnection(); 
        System.out.println("DB_sqlite___public static void main");
        dbc.populateVector(); 
    } 
}
    


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
import java.sql.Array;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.ResultSetMetaData;
import java.sql.SQLException; 
import java.sql.Statement; 
import java.util.ArrayList;
import java.util.Vector;

import net.wanzenberg.ulf.mymdi.common.Constants;

//********************************************************************************
//********************************************************************************
//Klasse SqliteDatabaseToVector
//********************************************************************************
//********************************************************************************
public class SqliteDatabaseToVector { 

    /** ++++++++++++++++++++++++++++++++++++++++++++++
     * ...
     * +++++++++++++++++++++++++++++++++++++++++++++++
     */
    
    private String myDatabase;
    private String mySQLStatement;
    private String mySQLStatementType;
    
    public Vector<String> columnNames = new Vector<String>();
    public Vector<Vector> data = new Vector<Vector>();
    
    private static final SqliteDatabaseToVector dbcontroller = new SqliteDatabaseToVector(); 
    private static Connection connection; 

    // 20160328-1316-A 
    //private static final String DB_PATH = System.getProperty("user.home") + "/" + "mywork.db"; 
    private static final String DB_PATH = Constants.DB_PATH_MYWORK ;  
    // 20160328-1316-E 

    static { 
        try { 
            Class.forName("org.sqlite.JDBC"); 
        } catch (ClassNotFoundException e) { 
            System.err.println("Fehler beim Laden des JDBC-Treibers"); 
            e.printStackTrace(); 
        } 
    } 

	// +KONSTRUKTOR++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Konstruktor ApplicationMain
    // 1st constructor
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private SqliteDatabaseToVector()
    {
        System.out.println( "1st constructor");
    } 
     
	// +KONSTRUKTOR++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Konstruktor ApplicationMain
    // 2nd constuctor
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public SqliteDatabaseToVector(String parm_my_database, String parm_my_sql_statement, String parm_my_sql_statement_type) throws SQLException, ClassNotFoundException
    { 
        System.out.println( "2nd constructor");

        myDatabase = parm_my_database;
        mySQLStatement = parm_my_sql_statement;
        mySQLStatementType = parm_my_sql_statement_type;
      
        //DBController dbc = DB_sqlite.getInstance(); 
        this.handleDBConnection(); 
        
        if (parm_my_sql_statement_type == "DML"){
            this.executeSQL();
        }
        else
            if (parm_my_sql_statement_type == "TST")
                this.getResultArray();
            else
                this.populateVector(); 
    } 

	// +KONSTRUKTOR++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Konstruktor ApplicationMain
    // 3rd constuctor
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    SqliteDatabaseToVector(String parm_my_database, String parm_my_sql_statement, String parm_my_sql_statement_type, String parm_my_expected_result_type)
    { 
        System.out.println( "3rd constructor");
    	
      myDatabase = parm_my_database;
      mySQLStatement = parm_my_sql_statement;
      mySQLStatementType = parm_my_sql_statement_type;
      
        //DBController dbc = DB_sqlite.getInstance(); 
        //this.handleDBConnection(); 
        this.getSingleDataset(); 
    } 
    
   
    
    // ++++++++++++++++++++++++++++++++++++++++++++++
    //  accessor methods
    // +++++++++++++++++++++++++++++++++++++++++++++++
	// =METHODE========================================================================
	// ================================================================================
	// ... getInstance
	// ================================================================================
	// ================================================================================
    public static SqliteDatabaseToVector getInstance(){ 
        return dbcontroller; 
    } 
     
    
    
	// =METHODE========================================================================
	// ================================================================================
	// ... handleDBConnection
	// ================================================================================
	// ================================================================================
    private void handleDBConnection() { 
        /** ----------------------------------------------
        *  ... 
        * -----------------------------------------------
        */
        try { 
            if (connection != null) 
                return; 
            System.out.println("Creating Connection to Database ..."); 
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
	// ... getSQLResultArray
	// ================================================================================
	// ================================================================================
    public String[] getResultArray() throws SQLException, ClassNotFoundException{
        System.out.println("Klasse SqliteDatabaseToVector Methode: getSQLResultArray()");
	
		ArrayList<String> a = new ArrayList<String>();

		Statement stmt = connection.createStatement(); 
	
	    //Class.forName(sqlDriver);
	    //Connection con = DriverManager.getConnection(dtbSet, dtbUsername, dtbPassword);
	    //PreparedStatement ps = Connection.prepareStatement("SELECT room_name FROM "+ dtbTbl2);
	    //ResultSet rs = ps.executeQuery();
	    ResultSet rs = stmt.executeQuery(mySQLStatement); 
	       //System.out.println( "getSQLResultArray(): SQL: " + mySQLStatement);
	       
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columnsNumber = rsmd.getColumnCount();   
	       
	    while(rs.next())
	    {
	        //  Get columns
	        for (int i = 1; i <= columnsNumber; i++) {
	            a.add(rs.getString(i));
	        }        
	    }
	
	    return (String[]) a.toArray(new String[a.size()]);
    
    }

    
    
	// =METHODE========================================================================
	// ================================================================================
	// singleDataset
	// ================================================================================
	// ================================================================================
    private void getSingleDataset() { 
        try { 
            Statement stmt = connection.createStatement(); 
            int columns1;

            ResultSet rs1 = stmt.executeQuery("SELECT * FROM it_object WHERE object_id = 2"); 

            
            /** ----------------------------------------------
            *  ... column names to vector
            * -----------------------------------------------
            */  
            ResultSetMetaData md = rs1.getMetaData();
            columns1 = md.getColumnCount();

            //  Get column names
            for (int i = 1; i <= columns1; i++) {
                columnNames.addElement( md.getColumnName(i) );
                //System.out.println(md.getColumnName(i));
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
                    //System.out.println( rs1.getObject(i) );
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
	// ... populateVector
	// ================================================================================
	// ================================================================================
    private void populateVector() { 
        try { 
            Statement stmt = connection.createStatement(); 
            int columns;

            
    		System.out.println("populateVector");

            
            //ResultSet rs = stmt.executeQuery("SELECT object_id, object_name, object_type, object_description, library_type, library_path, file, member FROM it_object"); 
            ResultSet rs = stmt.executeQuery(mySQLStatement); 

            
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
                    //System.out.println( rs.getObject(i) );
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

    

    
    
     /** ----------------------------------------------
     *  set ...
     * -----------------------------------------------
     */
    private void executeSQL() { 
        try { 
            Statement stmt = connection.createStatement(); 

            System.out.println("executeSQL:" + mySQLStatement);
            
            stmt.executeUpdate(mySQLStatement);
            
            stmt.close();

            //connection.commit();

            
            //connection.close(); 
            //System.out.println("Connection closed");
            
        } catch (SQLException e) { 
            System.err.println("Couldn't handle DB-DML"); 
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
        AccessSqliteDatabase dbc = AccessSqliteDatabase.getInstance(); 
        dbc.handleDBConnection(); 
        dbc.populateVector(); 
    } 
}

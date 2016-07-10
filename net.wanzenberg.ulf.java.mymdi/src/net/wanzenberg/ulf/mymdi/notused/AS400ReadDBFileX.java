/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wanzenberg.ulf.mymdi.notused;



import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Exception;
import com.ibm.as400.access.AS400File;
import com.ibm.as400.access.AS400FileRecordDescription;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.Job;
import com.ibm.as400.access.JobList;
import com.ibm.as400.access.ObjectDoesNotExistException;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;
import com.ibm.as400.access.QSYSObjectPathName;
import com.ibm.as400.access.Record;
import com.ibm.as400.access.RecordFormat;
import com.ibm.as400.access.SequentialFile;
import java.awt.List;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Ulf
 */
public class AS400ReadDBFileX {

 public Vector<String> columnNames = new Vector<String>();
 public Vector<Vector> data = new Vector<Vector>();
 public ResultSet rs;
 private Properties props;


    /**
     */
    public AS400ReadDBFileX() throws PropertyVetoException, AS400SecurityException, AS400SecurityException, ErrorCompletingRequestException, IOException, InterruptedException, ObjectDoesNotExistException, ClassNotFoundException, SQLException
   {
        System.out.println("OKAY Anfang"); 
        
       // Declare variables to hold the system name and the command to run
        String systemString  = null;
        String commandString = null;

        
        // Created a reader to get input from the user
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in),1);


        // Get the system name and the command to run from the user
        String server="as400.holgerscherer.de";
        String user = "DREIU";
        String pass = "HLaxness";
        AS400 anAS400System = new AS400(server, user, pass); 
        String aSystemName = anAS400System.getSystemName();
        System.out.println("System: " + aSystemName);
     
        
        
        
        //anAS400System.connectService(AS400.FILE); 
        
        
        
        try 
        {
            anAS400System.connectService(anAS400System.COMMAND);
        } 
        catch (Throwable t) 
        {
            System.out.println("couldn't connect"); t.printStackTrace();
        }

        ProgramCall program = new ProgramCall(anAS400System);
        try 
        {
            program.setProgram("/QSYS.LIB/DREIU1.LIB/SNDMSG.PGM");
        } 
        catch (Throwable t) 
        {
            System.out.println("couldn't setProgram"); t.printStackTrace();
        }        
        try 
        {
            program.run();
            System.out.println("the program should have been executed"); 
        } 
        catch (Throwable t) 
        {
            System.out.println("couldn't run"); t.printStackTrace();
        }        
        
        
        
        
        String number="asdf <= Return value from Java Input";
        AS400Text txt80 = new AS400Text(80);
        ProgramParameter[] parmList = new ProgramParameter[1]; 
        parmList[0] = new ProgramParameter( txt80.toBytes(number),80);
        //ProgramCall pgm;
        //pgm = new ProgramCall(anAS400System,"/QSYS.LIB/DREIU1.LIB/SNDMSG.PGM", parmList);
        //pgm = new ProgramCall(anAS400System,"/QSYS.LIB/DREIU1.LIB/SNDMSG.PGM");
        
        
        


        
        
        
        // --------------------------------------------------- 
    	//
    	// --------------------------------------------------- 
        // props = new Properties();
        // props.load(new FileInputStream("properties/dataconnection.properties"));
 
        
        
    	// --------------------------------------------------- 
        // --------------------------------------------------- 
    	//
    	// --------------------------------------------------- 
    	// --------------------------------------------------- 
        String DRIVER = "com.ibm.as400.access.AS400JDBCDriver";        
        // String URL = "jdbc:as400://" + props.getProperty("local_system").trim() + ";naming=system;errors=full";
         String URL = "jdbc:as400://" + "as400.holgerscherer.de" + ";naming=system;errors=full";
            Connection conn = null; 
            String sql = null;
 
        //Connect to iSeries                                         
        Class.forName(DRIVER);                                       
        //conn = DriverManager.getConnection(URL,props.getProperty("userId").trim(),props.getProperty("password").trim());   
        conn = DriverManager.getConnection(URL,"DREIU","HLaxness");   
        sql = "SELECT * FROM dreiu1/glflgp" ;
        //System.out.println("SQL: " + sql);     
        PreparedStatement stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
 
        // It creates and displays the table
        //JTable table1 = new JTable(buildTableModel(rs));
        
        while (rs.next()) {
	        System.out.println("Flugnummer#: ----> " + rs.getString("FLGNR"));
	        System.out.println("Fluggebiet: " + rs.getString("FLGGES"));
	        System.out.println("Beschreibung: " + rs.getString("FLGBES"));
        }
 
        rs.close();
        stmt.close();
        conn.close();  
        
        
        System.out.println("OKAY Ende"); 
        
        
        
        
        
        

   }


    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

}




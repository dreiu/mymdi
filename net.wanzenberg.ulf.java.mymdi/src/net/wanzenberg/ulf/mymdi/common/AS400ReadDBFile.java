/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wanzenberg.ulf.mymdi.common;



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
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Ulf
 */
public class AS400ReadDBFile {

 public Vector<String> columnNames = new Vector<String>();
 public Vector<Vector> data = new Vector<Vector>();
    private Properties props;

    /**
     */
    public AS400ReadDBFile() throws PropertyVetoException, AS400SecurityException, AS400SecurityException, ErrorCompletingRequestException, IOException, InterruptedException, ObjectDoesNotExistException, ClassNotFoundException, SQLException
   {

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
        // --------------------------------------------------- 
    	//
    	// --------------------------------------------------- 
    	// --------------------------------------------------- 
        ProgramCall pgmcll = new ProgramCall(anAS400System);
        pgmcll.setProgram("/QSYS.LIB/DREIU1.LIB/DSPOBJD.PGM");

        
		AS400Text txt10 = new AS400Text(10);
    	// Create parameter array and populate.
    	ProgramParameter[] parmList1 = new ProgramParameter[1];
    	parmList1[0] = new ProgramParameter(txt10.toBytes("QCLSRC"),10);
        //pgmcll.setProgram("/QSYS.LIB/DREIU1.LIB/E#MBRINFO1.PGM", parmList1);

        if (pgmcll.run() != true)
        {
            // Report failure.
            System.out.println("Program failed!");
            // Show the messages.
            AS400Message[] messagelist = program.getMessageList();
            for (int i = 0; i < messagelist.length; ++i)
            {
                // Show each message.
                System.out.println(messagelist[i]);
            }
        }

        
        
        
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
        /* String URL = "jdbc:as400://" + "as400.holgerscherer.de" + ";naming=system;errors=full";
            Connection conn = null; 
            String sql = null;
 
        //Connect to iSeries                                         
        Class.forName(DRIVER);                                       
        //conn = DriverManager.getConnection(URL,props.getProperty("userId").trim(),props.getProperty("password").trim());   
        conn = DriverManager.getConnection(URL,"DREIU","HLaxness");   
        sql = "SELECT * FROM dreiu1/glflgp" ;
        //System.out.println("SQL: " + sql);     
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
 
        while (rs.next()) {
	        System.out.println("Flugnummer#: ----> " + rs.getString("FLGNR"));
	        System.out.println("Fluggebiet: " + rs.getString("FLGGES"));
	        System.out.println("Beschreibung: " + rs.getString("FLGBES"));
        }
 
        rs.close();
        stmt.close();
        conn.close();  
        */
        
        
        
        //System.out.println("previous signondate: " + anAS400System.getPreviousSignonDate().toString());
        System.out.println("userId: " + anAS400System.getUserId().toString());
        
        
        
        
        
        // xxxxxxxxxxxxxx-Anfang

    	// --------------------------------------------------- 
        // --------------------------------------------------- 
    	//
    	// --------------------------------------------------- 
    	// --------------------------------------------------- 
        // ...
        String aLibrary = "DREIU1";
        String aFile = "MBRINFLST";
        QSYSObjectPathName filePathName1;
        filePathName1 = new QSYSObjectPathName(aLibrary, aFile, "FILE");
        
        // ...
        SequentialFile theFile1 = new SequentialFile(anAS400System, filePathName1.getPath());       

        // Retrieve the record format for the file AS400FileRecordDescription 
        AS400FileRecordDescription theFile1RecordDescription1 = new AS400FileRecordDescription(anAS400System, filePathName1.getPath());
        System.out.println("Dateipfad: " + filePathName1.getPath());

        try
        {
            RecordFormat[] format =  theFile1RecordDescription1.retrieveRecordFormat();
            
            theFile1.setRecordFormat(format[0]); 
            theFile1.open(SequentialFile.READ_ONLY, 100,SequentialFile.COMMIT_LOCK_LEVEL_NONE);
            

            // ...
            System.out.println("Displaying file " + aLibrary.toUpperCase() + "/" + aFile.toUpperCase() + "(" + theFile1.getMemberName().trim() + "):");

            // ...
            Record record = theFile1.readNext(); 
            System.out.println("number of fields: " + record.getNumberOfFields());
            System.out.println("record format: " + record.getRecordFormat().getName());
            System.out.println("record number: " + record.getRecordNumber());
            System.out.println("record length: " + record.getRecordLength());
            System.out.println("record field 4: " + record.getField(4).toString());
            System.out.println("record field [MLMTXT]: " + record.getField("MLMTXT").toString());



            //  Get column names
            for (int i = 1; i < record.getNumberOfFields(); i++) {
                //columnNames.addElement( record.getField(i).toString() );
                System.out.println( "Feld Nummer " + i + " " + theFile1.getRecordFormat().getFieldDescription(i).getTEXT() );
                if (i == 3){
                    columnNames.addElement( theFile1.getRecordFormat().getFieldDescription(i).getTEXT());
                }
                if (i == 4){
                    columnNames.addElement( theFile1.getRecordFormat().getFieldDescription(i).getTEXT());
                }
                if (i == 13){
                    columnNames.addElement( theFile1.getRecordFormat().getFieldDescription(i).getTEXT());
                }
                if (i == 21){
                    //columnNames.addElement( theFile1.getRecordFormat().getFieldDescription(i).getTEXT());
                    columnNames.addElement( "Datum der letzten Änderung");
                    
                }
                if (i == 23){
                    columnNames.addElement( theFile1.getRecordFormat().getFieldDescription(i).getTEXT());
                }
            }

            // Loop while there are records in the file (while we have not 
            // reached end-of-file). 
            while (record != null)
            {
                Vector<Object> row = new Vector<Object>(500);

                for (int i = 1; i < record.getNumberOfFields(); i++) {
                    if (i == 3){
                        row.addElement( record.getField(i).toString() );
                    }
                    if (i == 4){
                        row.addElement( record.getField(i).toString() );
                    }
                    if (i == 13){
                        row.addElement( record.getField(i).toString() );
                    }
                    if (i == 21){
                        Date anInputDate = null ;
                        String pattern = "yyMMdd";
                        String OutputDate = null;
                        SimpleDateFormat aProcessFormat = new SimpleDateFormat(pattern);
                        try {
                          anInputDate = aProcessFormat.parse(record.getField(i).toString());
                          SimpleDateFormat anOutputDate = new SimpleDateFormat("dd.MM.yyyy");
                          OutputDate = anOutputDate.format(anInputDate);
                        } catch (ParseException e) {
                          e.printStackTrace();
                        }
                        row.addElement( OutputDate );
                    }
                    if (i == 23){
                        row.addElement( record.getField(i).toString() );
                    }

                }
                data.addElement( row );
                //System.out.println( row );

                record = theFile1.readNext();
            }


            System.out.println();


            theFile1.close();


            anAS400System.disconnectService(AS400.RECORDACCESS); 
        }
        catch (Exception e)
        {
          System.out.println("Error occurred attempting to display the file.");
          e.printStackTrace();

          try
          {
            // Close the file
            theFile1.close();
          }
          catch(Exception x)
          {
          }

          anAS400System.disconnectService(AS400.RECORDACCESS);
          System.exit(0);
        }  
        // xxxxxxxxxxxxxx-Ende
   }
    
    
    /**
     */
    public AS400ReadDBFile(String user, String server,  String pass) throws PropertyVetoException, AS400SecurityException, AS400SecurityException, ErrorCompletingRequestException, IOException, InterruptedException, ObjectDoesNotExistException, ClassNotFoundException, SQLException
   {

       // Declare variables to hold the system name and the command to run
        String systemString  = null;
        String commandString = null;

        
        // Created a reader to get input from the user
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in),1);


        // Get the system name and the command to run from the user
        //String server="as400.holgerscherer.de";
        //String user = "DREIU";
        //String pass = "HLaxness";
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
        
        
        
        
         
        
        
        //System.out.println("previous signondate: " + anAS400System.getPreviousSignonDate().toString());
        System.out.println("userId: " + anAS400System.getUserId().toString());
        
        
        
        
        
   }
    
    
    
}




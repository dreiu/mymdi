package net.wanzenberg.ulf.mymdi.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;

import net.wanzenberg.ulf.mymdi.main.ApplicationMain;

class RefreshListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {

		System.out.println("Test - Schaltfläche REFRESH gedrückt");

		// --------------------------------------------------------------------------------
		// Anmeldung/Verbindung zu AS400-System
		// --------------------------------------------------------------------------------
		String server = "as400.holgerscherer.de";
		String user = "DREIU";
		String pass = "HLaxness";
		AS400 anAS400System = new AS400(server, user, pass);
		String aSystemName = anAS400System.getSystemName();
		try {
			anAS400System.connectService(anAS400System.COMMAND);
		} catch (Throwable t) {
			System.out.println("Info - Verbindung zu AS400 konnte NICHT hergestellt werden.");
			t.printStackTrace();
		}

		
		// --------------------------------------------------------------------------------
		// Programmaufruf
		// --------------------------------------------------------------------------------
		ProgramCall aProgramCall = new ProgramCall(anAS400System);
		try {
			// program1.setProgram("/QSYS.LIB/DREIU1.LIB/SNDMSG.PGM");
			AS400Text txt10 = new AS400Text(10);
			// Create parameter array and populate.
			ProgramParameter[] aParameterList = new ProgramParameter[1];
			aParameterList[0] = new ProgramParameter(txt10.toBytes("QCBLSRC"), 10);
			aProgramCall.setProgram("/QSYS.LIB/DREIU1.LIB/E#MBRINFO1.PGM", aParameterList);

			
			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			// displayAS400Sources
			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			ApplicationMain.displayAS400Sources();
			

		} catch (Throwable t) {
			System.out.println("Fehler - Programm konnte nicht aufgerufen werden.");
			t.printStackTrace();
		}
		try {
			aProgramCall.run();
			System.out.println("Info - Das Programm wurde aufgerufen.");
		} catch (Throwable t) {
			System.out.println("Fehler - Programm konnte nicht aufgerufen werden.");
			t.printStackTrace();
		}

		
		// --------------------------------------------------------------------------------
		// Datum der letztmaligen Anmeldung 
		// --------------------------------------------------------------------------------
		GregorianCalendar myGregCal = null;
		int myDate = 0;
		try {
			myGregCal = anAS400System.getPreviousSignonDate();
			// String myDate = myGregCal.toString();
			myDate = myGregCal.get(Calendar.YEAR);

		} catch (AS400SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Info - Letzte Anmeldung: " + myDate);

		
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// displayAS400Sources
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		ApplicationMain.displayAS400Sources();

	}

}

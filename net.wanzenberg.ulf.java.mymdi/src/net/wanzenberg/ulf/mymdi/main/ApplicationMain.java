/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wanzenberg.ulf.mymdi.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Exception;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.ObjectDoesNotExistException;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;
import net.wanzenberg.ulf.mymdi.MetadataDetailJFrame;
import net.wanzenberg.ulf.mymdi.common.AS400ReadDBFile;
import net.wanzenberg.ulf.mymdi.common.Constants;
import net.wanzenberg.ulf.mymdi.common.MyOwnTableCellRenderer;
import net.wanzenberg.ulf.mymdi.common.MyOwnTableModel;
import net.wanzenberg.ulf.mymdi.database.AccessSqliteDatabase;
import net.wanzenberg.ulf.mymdi.common.DefaultValues;


/**
 *
 * @author Ulf Wanzenberg
 */
// ********************************************************************************
// ********************************************************************************
// Klasse ApplicationMain
// ********************************************************************************
// ********************************************************************************
public class ApplicationMain extends javax.swing.JFrame implements ActionListener {

	// ################################################################################
	// ... 
	// ################################################################################
	// ================================================================================
	// button btnClose 
	// ================================================================================
	private JButton btnClose;



	// ================================================================================
	// button btnChangePerspective 
	// ================================================================================
	private JButton btnChangePerspective;



	// ================================================================================
	// button btnRefresh 
	// ================================================================================
	private JButton btnRefresh;



	// ================================================================================
	// database, database access & SQL 
	// ================================================================================
	public static String DATABASE;
	static String myDatabase;
	static String mySqlStatement;
	static String mySqlStatementType;
	public ResultSet rs;



	// ================================================================================
	// Menu
	// ================================================================================
	JMenuBar menuMain;
	JMenuItem menuItemSQLite;
	JMenuItem menuItemClose;
	JMenu menuApplication;

	JMenu menuEnvironment;
	JMenuItem systemi;
	JMenuItem usersystemi;
	JMenuItem connectsystemi;

	JMenu menuSources;
	JMenuItem menuItemSourceLib;
	JMenuItem menuItemSourceFile;

	JMenu menuPerspective;
	JMenuItem menuItemSource;
	JMenuItem menuItemSourceMeta;

	JMenu menuInfo;
	JMenuItem about;



	// ================================================================================
	// ... label
	// ================================================================================
	private JLabel lblSystemi = new JLabel();
	private JLabel lblUserSystemi = new JLabel();
	private JLabel lblUserAndSystemi = new JLabel();
	
	
	
	// ================================================================================
	// ... auxiliary variables 
	// ================================================================================
	private Integer object_id;
	
	private String sUserAndSystemi;
	private String sUserSystemi;
	private String sSystemi;
	private String sSourceLib;
	private String sSourceFile;
	private String sMenuItemSystemi;
	

	private JTable jTableTest = new JTable();

	
	// private String filePathString = "C:\\UWDEV\\mywork.db";
	private String filePathString = Constants.DB_PATH_MYWORK_002;




	// ... SourceFiles
	// ################################################################################
	// ... internalFrameSourceFiles 
	// ================================================================================
	// -> JInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable)
	public static JInternalFrame internalFrameSourceFiles = new JInternalFrame("internalFrameSourceFiles", false, false, false,
			false);
			
	// ... 
	// ================================================================================
	// ...
	public static JDesktopPane sourceFilePane;
	// ...
	private static JTable jTableSourceList = new JTable();



	// ... Metadata
	// ################################################################################

	// ... internalFrameMetadata
	// ================================================================================
	// -> JInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable)
	public static JInternalFrame internalFrameMetadata = new JInternalFrame("internalFrame002", false, false, false,
			false);

	// ... 
	// ================================================================================
	//private JPanel panelMetadata = new JPanel();
	//private JLabel lblMetadataHeadline = new JLabel();
	// ...
	public static Integer swtchButtonSpecial = 0;
	// ...
	public JTable jTableMetadata = new JTable();






	

	// =METHODE========================================================================
	// ================================================================================
	// !!! main
	// ================================================================================
	// ================================================================================
	public static void main(String args[]) {

		
		// --------------------------------------------------------------------------------
		// ...
		// --------------------------------------------------------------------------------
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(ApplicationMain.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(ApplicationMain.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(ApplicationMain.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(ApplicationMain.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		}


		// iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
		// ...
		// iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
		ApplicationMain myMDI = new ApplicationMain();

	}	
	
	
	
	// +KONSTRUKTOR++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Konstruktor ApplicationMain
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public ApplicationMain() {

		// --------------------------------------------------------------------------------
		// initialize
		// --------------------------------------------------------------------------------
		initComponents();



		// ================================================================================
		// ================================================================================
		// Menu
		// ================================================================================
		// ================================================================================
		// Menüleiste erzeugen
		menuMain = new JMenuBar();


		// ================================================================================
		// Menu menuApplication
		// ================================================================================
		// Menüelemente erzeugen
		menuApplication = new JMenu("Anwendung");
		
		// Menüelement hinzufügen
		menuMain.add(menuApplication);

		// Untermenüelemente für Menü menuApplication erzeugen
		menuItemSQLite = new JMenuItem("SQLite-Datei ...");
		menuItemSQLite.addActionListener(this);
		// Untermenüelemente für Menü menuApplication hinzufügen
		menuApplication.add(menuItemSQLite);
		// Untermenüelemente für Menü menuApplication erzeugen
		menuItemClose = new JMenuItem("Beenden");
		menuItemClose.addActionListener(this);
		// Untermenüelemente für Menü menuApplication hinzufügen
		menuApplication.add(menuItemClose);



		// ================================================================================
		// Menu menuSources
		// ================================================================================
		// Menüelemente erzeugen
		menuSources = new JMenu("Quellen");

		// Menüelement hinzufügen
		menuMain.add(menuSources);

		// Untermenüelemente für Menü menuSources erzeugen
		menuItemSourceLib = new JMenuItem("Quellcodebibliothek auswählen");
		menuItemSourceLib.setBackground(Color.YELLOW);
		menuItemSourceLib.addActionListener(this);
		menuItemSourceFile = new JMenuItem("Quellcodedatei auswählen");
		menuItemSourceFile.setBackground(Color.YELLOW);
		menuItemSourceFile.addActionListener(this);

		// Untermenüelemente für Menü menuSources hinzufügen
		menuSources.add(menuItemSourceLib);
		menuSources.add(menuItemSourceFile);



		// ================================================================================
		// Menu menuPerspective
		// ================================================================================
		// Menüelemente erzeugen
		menuPerspective = new JMenu("Perspektive");

		// Menüelement hinzufügen
		menuMain.add(menuPerspective);
		
		// Untermenüelemente für Menü menuPerspective erzeugen
		menuItemSource = new JMenuItem("Quellcode(dateien)");
		menuItemSource.setBackground(Color.CYAN);
		menuItemSource.addActionListener(this);
		menuItemSourceMeta = new JMenuItem("Informationen zu Quellcode(dateien)");
		menuItemSourceMeta.addActionListener(this);
		// Untermenüelemente für Menü menuPerspective hinzufügen
		menuPerspective.add(menuItemSource);
		menuPerspective.add(menuItemSourceMeta);



		// ================================================================================
		// Menu menuEnvironment
		// ================================================================================
		// Menüelemente erzeugen
		menuEnvironment = new JMenu("Umgebung");

		// Menüelement hinzufügen
		menuMain.add(menuEnvironment);
		
		// Untermenüelemente für Menü menuEnvironment erzeugen
		systemi = new JMenuItem("???");
		systemi.setText("System i auswählen");
		systemi.addActionListener(this);
		usersystemi = new JMenuItem("(System i-)User/Benutzer auswählen");
		usersystemi.addActionListener(this);
		connectsystemi = new JMenuItem("Connect / Disconnect");
		connectsystemi.addActionListener(this);
		// Untermenüelemente für Menü menuEnvironment hinzufügen
		menuEnvironment.add(systemi);
		menuEnvironment.add(usersystemi);
		menuEnvironment.add(connectsystemi);



		// ================================================================================
		// Menu menuEnvironment
		// ================================================================================
		// Menüelemente erzeugen
		menuInfo = new JMenu("Info");

		// Menüelemente hinzufügen
		menuMain.add(menuInfo);

		// Untermenüelemente für Menü info erzeugen
		about = new JMenuItem("about");
		about.addActionListener(this);
		// Untermenüelemente für Menü info hinzufügen
		menuInfo.add(about);



		// ================================================================================
		// Panel menuPanel
		// ================================================================================
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new BorderLayout());
		menuPanel.setBackground(Color.green);
		menuPanel.setMaximumSize(new Dimension(10000, 50));
		menuPanel.setPreferredSize(new Dimension(100, 50));
		menuPanel.setMinimumSize(new Dimension(100, 50));

		menuPanel.add(menuMain, BorderLayout.CENTER);
		menuPanel.add(lblUserAndSystemi, BorderLayout.EAST);

		
		
		
		// ================================================================================
		// ================================================================================
		// Calendar
		// ================================================================================
		// ================================================================================
		// --------------------------------------------------------------------------------
		// ???
		// --------------------------------------------------------------------------------
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		System.out.println(sdf.format(cal.getTime()));



		// ================================================================================
		// ================================================================================
		// Metadata
		// ================================================================================
		// ================================================================================
		// --------------------------------------------------------------------------------
		// ???
		// --------------------------------------------------------------------------------
		String filePathString = Constants.DB_PATH_MYWORK_002;

		File f = new File(filePathString);
		if (f.exists() && !f.isDirectory()) {

			//System.out.println("Info - Datei " + filePathString + " gefunden.");

			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			// populateMetadatajTable
			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			populateMetadatajTable(jTableMetadata);

			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			// prepareInternalFrameMetadata
			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			prepareInternalFrameMetadata(jTableMetadata);

		} else {
			System.out.println("Warnung - Angegebene Datei nicht gefunden.");

			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			// prepareInternalFrameMetadata
			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			prepareInternalFrameMetadata(jTableMetadata);

			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			String choosertitle = null;
			chooser.setDialogTitle(choosertitle);

			// chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			//// disable the "All files" option.
			// chooser.setAcceptAllFileFilterUsed(false);
			//
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				System.out.println("Info - Ausgewähltes Verzeichnis: " + chooser.getCurrentDirectory());
				System.out.println("Info - Ausgewähltes Datei: " + chooser.getSelectedFile());

				filePathString = chooser.getSelectedFile().toString();

				// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
				// populateMetadatajTable
				// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
				populateMetadatajTable(jTableMetadata);

				
				// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
				// prepareInternalFrameMetadata
				// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
				prepareInternalFrameMetadata(jTableMetadata);

			} else {
				System.out.println("Warnung - Es wurde keine Datei ausgewählt.");

			}
		}

		
		
		
		// ================================================================================
		// ================================================================================
		// Sources
		// ================================================================================
		// ================================================================================
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// displayAS400Sources
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		ApplicationMain.displayAS400Sources();


		// --------------------------------------------------------------------------------
		// ... JDesktopPane
		// --------------------------------------------------------------------------------
		sourceFilePane = new JDesktopPane();
		// appDesktopPane.setBackground(Color.yellow);
		sourceFilePane.setSize(Toolkit.getDefaultToolkit().getScreenSize());

		// --------------------------------------------------------------------------------
		// ... JInternalFrame
		// --------------------------------------------------------------------------------
		// internalFrame001.setBounds(25, 25, 400, 200);
		internalFrameSourceFiles.setBorder(null);
		internalFrameSourceFiles.setIconifiable(false);
		internalFrameSourceFiles.setVisible(true);
		internalFrameListener internalListener = new internalFrameListener() {
		};
		internalFrameSourceFiles.addInternalFrameListener(internalListener);
		Border borderFrameInternalFrameSourceFiles = new BevelBorder(BevelBorder.RAISED);
		internalFrameSourceFiles.setBorder(borderFrameInternalFrameSourceFiles);
		internalFrameSourceFiles.setBackground(Color.yellow);

		sourceFilePane.add(internalFrameSourceFiles);
		// internalFrame001.setBounds(25, 25, 400, 200);
		//internalFrameSourceFiles.setSize(600, 300);
		try {
			internalFrameSourceFiles.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JPanel bottomButtonPanelDesktopPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
		// bottomButtonPanelDesktopPane.setBackground(Color.yellow);
		sourceFilePane.add(bottomButtonPanelDesktopPane);

		// ================================================================================
		// ================================================================================
		// bottomButtonPanel
		// ================================================================================
		// ================================================================================
		JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
		bottomButtonPanel.setMaximumSize(new Dimension(10000, 50));
		bottomButtonPanel.setPreferredSize(new Dimension(100, 50));
		bottomButtonPanel.setMinimumSize(new Dimension(10, 50));
		// bottomButtonPanel.setBackground(Color.yellow);

		// borderBottomPanel
		// --------------------------------------------------------------------------------
		Border borderBottomPanel = new BevelBorder(BevelBorder.RAISED);
		bottomButtonPanel.setBorder(borderBottomPanel);

		// --------------------------------------------------------------------------------
		// btnClose
		// --------------------------------------------------------------------------------
		btnClose = new JButton();
		btnClose.setText("BeENDEn");
		btnClose.setPreferredSize(new Dimension(120, 40));
		// ...
		// ... dieser Listener nimmt auf konkrete ... dieser Klasse bezug;
		// deshalb kann die Klasse ChangePerspectiveListener nicht aus
		// dieser java-Datei JFrameDialogueApplication ausgegliedert werden
		CloseApplicationListener listenAppClose = new CloseApplicationListener();
		btnClose.addActionListener(listenAppClose);

		bottomButtonPanel.add(btnClose);

		// --------------------------------------------------------------------------------
		// btnChangePerspective
		// --------------------------------------------------------------------------------
		btnChangePerspective = new JButton();
		btnChangePerspective.setText("-> Change InternalFrame <-");
		btnChangePerspective.setPreferredSize(new Dimension(120, 40));
		// ...
		// ... dieser Listener nimmt auf konkrete ... dieser Klasse bezug;
		// deshalb kann die Klasse ChangePerspectiveListener nicht aus
		// dieser java-Datei JFrameDialogueApplication ausgegliedert werden
		ChangePerspectiveListener listenAppPerspective = new ChangePerspectiveListener();
		btnChangePerspective.addActionListener(listenAppPerspective);

		bottomButtonPanel.add(btnChangePerspective);

		// --------------------------------------------------------------------------------
		// btnRefresh
		// --------------------------------------------------------------------------------
		btnRefresh = new JButton();
		btnRefresh.setText("-> REFRESH <-");
		btnRefresh.setPreferredSize(new Dimension(120, 40));
		// ...
		// ... dieser Listener nimmt auf konkrete ... dieser Klasse bezug;
		// deshalb kann die Klasse ChangePerspectiveListener nicht aus
		// dieser java-Datei JFrameDialogueApplication ausgegliedert werden
		RefreshListener listenRefresh = new RefreshListener();
		btnRefresh.addActionListener(listenRefresh);

		bottomButtonPanel.add(btnRefresh);

		// --------------------------------------------------------------------------------
		// --------------------------------------------------------------------------------
		// --------------------------------------------------------------------------------
		// ... add components to this jframe
		// --------------------------------------------------------------------------------
		// --------------------------------------------------------------------------------
		// --------------------------------------------------------------------------------
		this.setLayout(new BorderLayout());

		// --------------------------------------------------------------------------------
		// implement menuPanel
		// --------------------------------------------------------------------------------
		this.add(BorderLayout.NORTH, menuPanel);

		// --------------------------------------------------------------------------------
		// implement appDesktopPane
		// --------------------------------------------------------------------------------
		this.add(BorderLayout.CENTER, sourceFilePane);

		// --------------------------------------------------------------------------------
		// implement bottomButonPanel
		// --------------------------------------------------------------------------------
		this.add(BorderLayout.SOUTH, bottomButtonPanel);

		this.setSize(800, 600);
		// this.setPreferredSize(new Dimension (360, 400));
		this.setVisible(true);
		// this.pack();

	}







	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	
	
	
	
	


	
	
	
	// =METHODE========================================================================
	// ================================================================================
	// ... getAS400SourceData
	// ================================================================================
	// ================================================================================
	private void getAS400SourceData() {
		// Get the system name and the command to run from the user

		// --------------------------------------------------------------------------------
		// implement menuPanel
		// --------------------------------------------------------------------------------
		
		String server = "as400.holgerscherer.de";
		String user = "DREIU";
		String pass = "HLaxness";
		AS400 anAS400System = new AS400(server, user, pass);
		String aSystemName = anAS400System.getSystemName();
		System.out.println("System: " + aSystemName);

		// anAS400System.connectService(AS400.FILE);

		try {
			anAS400System.connectService(anAS400System.COMMAND);
		} catch (Throwable t) {
			System.out.println("couldn't connect");
			t.printStackTrace();
		}

		ProgramCall programToCall = new ProgramCall(anAS400System);
		try {

			AS400Text sourcefile = new AS400Text(10);
			// Create parameter array and populate.
			ProgramParameter[] parameter = new ProgramParameter[1];

			// parmList1[0] = new ProgramParameter(txt10.toBytes("QCLSRC"),10);
			parameter[0] = new ProgramParameter(sourcefile.toBytes(sSourceFile), 10);

			programToCall.setProgram("/QSYS.LIB/DREIU1.LIB/E#MBRINFO1.PGM", parameter);

		} catch (Throwable t) {
			System.out.println("Fehler beim Ausführen des Programms: " + sSourceFile + ".");
			t.printStackTrace();
		}
		try {
			programToCall.run();
			System.out.println("Das Programm " + sSourceFile + " sollte ausgeführt worden sein.");
		} catch (Throwable t) {
			System.out.println("Das Programm " + sSourceFile + " konnte nicht ausgeführt werden.");
			t.printStackTrace();
		}

		// Variante 2
		fillTableFromCertainDatabaseTableAndCertainUser(jTableTest, "SELECT * FROM dreiu1/cusmsp;");

		// internalFrame001.removeAll();
		// .repaint();
		// .remove(jScrollPaneSourceList);

		// ... AS400ReadDBFile
		// -------------------------------------------------------
		// 20150818-2124; Verwendung AS400ReadDBFile entfernt

		AS400ReadDBFile AS400ReadDBFile = null;
		try {
			AS400ReadDBFile = new AS400ReadDBFile();
		} catch (PropertyVetoException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (AS400SecurityException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ErrorCompletingRequestException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InterruptedException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ObjectDoesNotExistException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		}

		// 20150818-2124; Verwendung AS400ReadDBFile entfernt
		// DefaultTableModel tableModelAS400ReadDBFile = new
		// DefaultTableModel(AS400ReadDBFile.data, AS400ReadDBFile.columnNames);
		// DefaultTableModel tableModelAS400ReadDBFile = new
		// DefaultTableModel();
		MyOwnTableModel tableModelAS400ReadDBFile = new MyOwnTableModel(AS400ReadDBFile.data,
				AS400ReadDBFile.columnNames);

		// ... JTable
		// -------------------------------------------------------
		// JTable jTableSourceList = new JTable();
		jTableSourceList.setModel(tableModelAS400ReadDBFile);
		jTableSourceList.setAutoCreateRowSorter(true);
		jTableSourceList.setBackground(Color.red);
		// jTableSourceList.setPreferredScrollableViewportSize(jTableSourceList.getPreferredSize());
		// System.out.println("Size:" +
		// jTableSourceList.getPreferredSize().toString());
		Dimension d2 = new Dimension(375, 1100);
		jTableSourceList.setPreferredScrollableViewportSize(d2);
		

		

		// ... JTable
		// -------------------------------------------------------
		// JTable jTableSourceList = new JTable();
		/*
		 * jTableSourceList.setModel(tableModelAS400ReadDBFile);
		 * jTableSourceList.setAutoCreateRowSorter(true);
		 */
		jTableTest.setBackground(Color.yellow);
		// jTableSourceList.setPreferredScrollableViewportSize(jTableSourceList.getPreferredSize());
		// System.out.println("Size:" +
		// jTableSourceList.getPreferredSize().toString());
		Dimension d2test = new Dimension(375, 1100);
		jTableTest.setPreferredScrollableViewportSize(d2test);

		// ... JScrollPane
		// -------------------------------------------------------
		JScrollPane jScrollPaneSourceList = new JScrollPane();
		// jScrollPaneSourceList.getViewport().add(jTableSourceList);
		jScrollPaneSourceList.getViewport().add(jTableSourceList);
		jScrollPaneSourceList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPaneSourceList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// jScrollPaneSourceList.setLayout(null);
		jScrollPaneSourceList.getHorizontalScrollBar().setBackground(new Color(192, 192, 128));
		// jScrollPaneSourceList.setBounds(0, 0, 400, 400);

		// ... JScrollPane
		// -------------------------------------------------------
		JScrollPane jScrollPaneTest = new JScrollPane();
		jScrollPaneTest.getViewport().add(jTableTest);
		jScrollPaneTest.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPaneTest.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPaneTest.getHorizontalScrollBar().setBackground(new Color(192, 192, 128));

		// internalFrame001
		// -------------------------------------------------------
		// internalFrame001.setSize(700, 1200);
		// remove borders of internalframe
		internalFrameSourceFiles.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		// remove titlebar of internalframe
		((javax.swing.plaf.basic.BasicInternalFrameUI) internalFrameSourceFiles.getUI()).setNorthPane(null);

		// internalFrame001.setLayout(new GridLayout(2,0));
		internalFrameSourceFiles.setLayout(new GridBagLayout());

		// GridBagLayout object maintains a dynamic, rectangular grid of cells
		GridBagConstraints gBC = new GridBagConstraints();
		// gBC.fill = GridBagConstraints.RELATIVE;
		gBC.fill = GridBagConstraints.BOTH;
		// Specifies how to distribute extra horizontal space.
		// displayLayout.weightx = 2.0;
		// This field specifies the internal padding, that is, how much space to
		// add to the minimum height of the component.
		// displayLayout.ipady = 600;

		// ... JLabel001
		// -------------------------------------------------------
		JLabel jLabel001 = new JLabel();
		jLabel001.setText("Label: jLabel001");

		// Specifies the cell containing the leading edge of the component's
		// display area, where the first cell in a row has gridx=0.
		gBC.gridx = 0;
		// Specifies the cell at the top of the component's display area, where
		// the topmost cell has gridy=0.
		gBC.gridy = 0;

		gBC.weightx = 1.0;
		gBC.weighty = 0.9;

		// ...
		// internalFrame001.add(jLabel001);
		internalFrameSourceFiles.add(jLabel001, gBC);

		// Specifies the cell containing the leading edge of the component's
		// display area, where the first cell in a row has gridx=0.
		gBC.gridx = 0;
		// Specifies the cell at the top of the component's display area, where
		// the topmost cell has gridy=0.
		gBC.gridy = 1;
		// gBC.ipadx = 1000;
		// gBC.ipady = 1000;
		gBC.weightx = 1.0;
		gBC.weighty = 0.9;
		// ...
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// internalFrame001.add(jScrollPaneSourceList, gBC);

		internalFrameSourceFiles.add(jScrollPaneTest, gBC);

	}

	
	
	
	
	// ================================================================================
	// ================================================================================
	// ... userGetAS400SourceData
	// ================================================================================
	// ================================================================================
	/**

	 * @throws IOException
	 * @throws InterruptedException
	 * @throws AS400SecurityException
	 * @throws AS400Exception
	 */
	private void userGetAS400SourceData(String sUserSystemi, String sSystemi)
			throws AS400Exception, AS400SecurityException, InterruptedException, IOException {

		// if (aSystemName == null) {
		if (sSystemi == null) {
			// Get the system name and the command to run from the user
			String server = "1as400.holgerscherer.de";
			server = sSystemi;
			String user = "1DREIU";
			user = sUserSystemi;
			String pass = "HLaxness";
			AS400 anAS400System = new AS400(server, user, pass);
			String aSystemName = anAS400System.getSystemName();
			System.out.println("System: " + aSystemName);

			// anAS400System.connectService(AS400.FILE);

			try {
				anAS400System.connectService(anAS400System.COMMAND);
			} catch (Throwable t) {
				System.out.println("couldn't connect");
				t.printStackTrace();
			}

			ProgramCall program1 = new ProgramCall(anAS400System);
			try {
				// program1.setProgram("/QSYS.LIB/DREIU1.LIB/SNDMSG.PGM");
				AS400Text txt10 = new AS400Text(10);
				// Create parameter array and populate.
				ProgramParameter[] parmList1 = new ProgramParameter[1];

				// parmList1[0] = new
				// ProgramParameter(txt10.toBytes("QCLSRC"),10);
				parmList1[0] = new ProgramParameter(txt10.toBytes(sSourceFile), 10);
				program1.setProgram("/QSYS.LIB/DREIU1.LIB/E#MBRINFO1.PGM", parmList1);

			} catch (Throwable t) {
				System.out.println("couldn't setProgram");
				t.printStackTrace();
			}
			try {
				program1.run();
				System.out.println("the program should have been executed");
			} catch (Throwable t) {
				System.out.println("couldn't run");
				t.printStackTrace();
			}

		}

		// internalFrame001.removeAll();
		// .repaint();
		// .remove(jScrollPaneSourceList);

		// ... AS400ReadDBFile
		// -------------------------------------------------------
		// 20150818-2124; Verwendung AS400ReadDBFile entfernt

		AS400ReadDBFile AS400ReadDBFile = null;
		try {
			AS400ReadDBFile = new AS400ReadDBFile(sUserSystemi, sSystemi, "");
		} catch (PropertyVetoException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (AS400SecurityException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ErrorCompletingRequestException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InterruptedException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ObjectDoesNotExistException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		}

		// 20150818-2124; Verwendung AS400ReadDBFile entfernt
		// DefaultTableModel tableModelAS400ReadDBFile = new
		// DefaultTableModel(AS400ReadDBFile.data, AS400ReadDBFile.columnNames);
		// DefaultTableModel tableModelAS400ReadDBFile = new
		// DefaultTableModel();
		MyOwnTableModel tableModelAS400ReadDBFile = new MyOwnTableModel(AS400ReadDBFile.data,
				AS400ReadDBFile.columnNames);

		// ... JTable
		// -------------------------------------------------------
		// JTable jTableSourceList = new JTable();
		jTableSourceList.setModel(tableModelAS400ReadDBFile);
		jTableSourceList.setAutoCreateRowSorter(true);
		jTableSourceList.setBackground(Color.red);
		// jTableSourceList.setPreferredScrollableViewportSize(jTableSourceList.getPreferredSize());
		// System.out.println("Size:" +
		// jTableSourceList.getPreferredSize().toString());
		Dimension d2 = new Dimension(375, 1100);
		jTableSourceList.setPreferredScrollableViewportSize(d2);

		// ... JScrollPane
		// -------------------------------------------------------
		JScrollPane jScrollPaneSourceList = new JScrollPane();
		jScrollPaneSourceList.getViewport().add(jTableSourceList);
		jScrollPaneSourceList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPaneSourceList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// jScrollPaneSourceList.setLayout(null);
		jScrollPaneSourceList.getHorizontalScrollBar().setBackground(new Color(192, 192, 128));
		// jScrollPaneSourceList.setBounds(0, 0, 400, 400);

		// internalFrame001
		// -------------------------------------------------------
		// internalFrame001.setSize(700, 1200);
		// remove borders of internalframe
		internalFrameSourceFiles.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		// remove titlebar of internalframe
		((javax.swing.plaf.basic.BasicInternalFrameUI) internalFrameSourceFiles.getUI()).setNorthPane(null);

		// internalFrame001.setLayout(new GridLayout(2,0));
		internalFrameSourceFiles.setLayout(new GridBagLayout());

		// GridBagLayout object maintains a dynamic, rectangular grid of cells
		GridBagConstraints gBC = new GridBagConstraints();
		// gBC.fill = GridBagConstraints.RELATIVE;
		gBC.fill = GridBagConstraints.BOTH;
		// Specifies how to distribute extra horizontal space.
		// displayLayout.weightx = 2.0;
		// This field specifies the internal padding, that is, how much space to
		// add to the minimum height of the component.
		// displayLayout.ipady = 600;

		// Specifies the cell containing the leading edge of the component's
		// display area, where the first cell in a row has gridx=0.
		gBC.gridx = 0;
		// Specifies the cell at the top of the component's display area, where
		// the topmost cell has gridy=0.
		gBC.gridy = 1;
		// gBC.ipadx = 1000;
		// gBC.ipady = 1000;
		gBC.weightx = 1.0;
		gBC.weighty = 0.9;
		// ...
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++
		internalFrameSourceFiles.add(jScrollPaneSourceList, gBC);
		// internalFrame001.add(jScrollPaneSourceList);

		// ... JLabel001
		// -------------------------------------------------------
		/*
		 * JLabel jLabel001 = new JLabel(); jLabel001.setText("Label: jLabel001"
		 * );
		 * 
		 * 
		 * // Specifies the cell containing the leading edge of the component's
		 * display area, where the first cell in a row has gridx=0. gBC.gridx =
		 * 0; // Specifies the cell at the top of the component's display area,
		 * where the topmost cell has gridy=0. gBC.gridy = 0; gBC.weighty = 0.1;
		 * 
		 * // ... //internalFrame001.add(jLabel001);
		 * internalFrame001.add(jLabel001, gBC);
		 */

		/*
		 * SequentialFile file = new SequentialFile(anAS400System,
		 * "/QSYS.LIB/DREIU1.LIB/GLFLGP.FILE"); try { file.setRecordFormat(); }
		 * catch (AS400Exception | AS400SecurityException | InterruptedException
		 * | IOException | PropertyVetoException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } try {
		 * file.open(AS400File.READ_ONLY, 1, AS400File.COMMIT_LOCK_LEVEL_NONE );
		 * } catch (AS400Exception | AS400SecurityException |
		 * InterruptedException | IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } Record record = null; try { record =
		 * file.read(1); } catch (AS400Exception | AS400SecurityException |
		 * InterruptedException | IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } Object field = null; try { field =
		 * record.getField(0); } catch (UnsupportedEncodingException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 * System.out.println("Field is "+field); file.close();
		 */

	}

	/**
	 * ++++++++++++++++++++++++++++++++++++++++++++++ method ...
	 * ++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	public static void displayAS400Sources() {

		AS400ReadDBFile AS400ReadDBFile = null;
		try {
			AS400ReadDBFile = new AS400ReadDBFile();
		} catch (PropertyVetoException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (AS400SecurityException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ErrorCompletingRequestException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InterruptedException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ObjectDoesNotExistException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
		}
		// ... JTable
		// -------------------------------------------------------
		// internalFrame001.removeAll();

		MyOwnTableModel tableModelAS400ReadDBFile = new MyOwnTableModel(AS400ReadDBFile.data,
				AS400ReadDBFile.columnNames);

		// ... JTable
		// -------------------------------------------------------
		// JTable jTableSourceList = new JTable();
		jTableSourceList.setModel(tableModelAS400ReadDBFile);

		internalFrameSourceFiles.repaint();

		// ... JTable
		// -------------------------------------------------------
		// JTable jTableSourceList = new JTable();
		jTableSourceList.setModel(tableModelAS400ReadDBFile);
		jTableSourceList.setAutoCreateRowSorter(true);
		jTableSourceList.setBackground(Color.red);
		// jTableSourceList.setPreferredScrollableViewportSize(jTableSourceList.getPreferredSize());
		// System.out.println("Size:" +
		// jTableSourceList.getPreferredSize().toString());
		Dimension d2 = new Dimension(375, 1100);
		jTableSourceList.setPreferredScrollableViewportSize(d2);

		// ... JScrollPane
		// -------------------------------------------------------
		JScrollPane jScrollPaneSourceList = new JScrollPane();
		jScrollPaneSourceList.getViewport().add(jTableSourceList);
		jScrollPaneSourceList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPaneSourceList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// jScrollPaneSourceList.setLayout(null);
		jScrollPaneSourceList.getHorizontalScrollBar().setBackground(new Color(192, 192, 128));
		// jScrollPaneSourceList.setBounds(0, 0, 400, 400);

		// internalFrame001
		// -------------------------------------------------------
		// internalFrame001.setSize(700, 1200);
		// remove borders of internalframe
		internalFrameSourceFiles.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		// remove titlebar of internalframe
		((javax.swing.plaf.basic.BasicInternalFrameUI) internalFrameSourceFiles.getUI()).setNorthPane(null);

		// internalFrame001.setLayout(new GridLayout(2,0));
		internalFrameSourceFiles.setLayout(new GridBagLayout());

		// GridBagLayout object maintains a dynamic, rectangular grid of cells
		GridBagConstraints gBC = new GridBagConstraints();
		// gBC.fill = GridBagConstraints.RELATIVE;
		gBC.fill = GridBagConstraints.BOTH;
		// Specifies how to distribute extra horizontal space.
		// displayLayout.weightx = 2.0;
		// This field specifies the internal padding, that is, how much space to
		// add to the minimum height of the component.
		// displayLayout.ipady = 600;

		// Specifies the cell containing the leading edge of the component's
		// display area, where the first cell in a row has gridx=0.
		gBC.gridx = 0;
		// Specifies the cell at the top of the component's display area, where
		// the topmost cell has gridy=0.
		gBC.gridy = 1;
		// gBC.ipadx = 1000;
		// gBC.ipady = 1000;
		gBC.weightx = 1.0;
		gBC.weighty = 0.9;
		// ...
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++
		internalFrameSourceFiles.add(jScrollPaneSourceList, gBC);
		// internalFrame001.add(jScrollPaneSourceList);

		// ... JLabel001
		// -------------------------------------------------------
		/*
		 * JLabel jLabel001 = new JLabel(); jLabel001.setText("Label: jLabel001"
		 * );
		 * 
		 * 
		 * // Specifies the cell containing the leading edge of the component's
		 * display area, where the first cell in a row has gridx=0. gBC.gridx =
		 * 0; // Specifies the cell at the top of the component's display area,
		 * where the topmost cell has gridy=0. gBC.gridy = 0; gBC.weighty = 0.1;
		 * 
		 * // ... //internalFrame001.add(jLabel001);
		 * internalFrame001.add(jLabel001, gBC);
		 */

		/*
		 * SequentialFile file = new SequentialFile(anAS400System,
		 * "/QSYS.LIB/DREIU1.LIB/GLFLGP.FILE"); try { file.setRecordFormat(); }
		 * catch (AS400Exception | AS400SecurityException | InterruptedException
		 * | IOException | PropertyVetoException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } try {
		 * file.open(AS400File.READ_ONLY, 1, AS400File.COMMIT_LOCK_LEVEL_NONE );
		 * } catch (AS400Exception | AS400SecurityException |
		 * InterruptedException | IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } Record record = null; try { record =
		 * file.read(1); } catch (AS400Exception | AS400SecurityException |
		 * InterruptedException | IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } Object field = null; try { field =
		 * record.getField(0); } catch (UnsupportedEncodingException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 * System.out.println("Field is "+field); file.close();
		 */

	}

	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// changeDialogueMetadata
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Kommentar 20160710-1933
	// ----------------------------------------------------------
	// changeDialogueMetadata
	// ----------------------------------------------------------
	protected String[] changeDialogueMetadata() {

		// ... headline ...
		// ----------------------------------------------------------
		JPanel pnlChangeDialogueMetadataHeadline = new JPanel();
		JLabel lblChangeDialogueMetadataHeadline = new JLabel("Überschrift");
		pnlChangeDialogueMetadataHeadline.add(lblChangeDialogueMetadataHeadline);

		// ... array for data ...
		// ----------------------------------------------------------
		String[] dataChangeDialogueMetadata = new String[5];

		//
		// label, textFields ...
		// ----------------------------------------------------------
		JLabel lblName = new JLabel("Name:");
		JTextField txtName = new JTextField(12);
		JLabel lblLibraryType = new JLabel("Library Type:");
		JTextField txtLibraryType = new JTextField(12);
		JLabel lblLibrary = new JLabel("Library:");
		JTextField txtLibrary = new JTextField(12);
		JLabel lblShortDescription = new JLabel("Short Description:");
		JTextField txtShortDescription = new JTextField(12);
		JLabel lblLongDescription = new JLabel("Long Description:");
		JTextField txtLongDescription = new JTextField(12);

		//
		// set values in textfields ...
		// ----------------------------------------------------------
		txtName.setText(object_id.toString());
		txtLibrary.setText("...");
		String[] arrayItemsLibraryType = { "AS/400, IBM i/", "Windows" };
		final JComboBox cmbItemsLibraryType = new JComboBox(arrayItemsLibraryType);
		txtShortDescription.setText("...");
		txtLongDescription.setText("...");

		// ...
		// ----------------------------------------------------------
		JButton btnSaveChangeDialogueMetadata = new JButton("Speichern");

		//
		// ...dialogue...
		// ==========================================================
		Frame frmChangeDialogueMetadata = null;
		final JDialog dlgChangeDialogueMetadata = new JDialog(frmChangeDialogueMetadata, true);

		//
		// get values from textfields ...
		// ----------------------------------------------------------
		btnSaveChangeDialogueMetadata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataChangeDialogueMetadata[0] = txtName.getText();
				dataChangeDialogueMetadata[1] = (String) cmbItemsLibraryType.getSelectedItem();
				dataChangeDialogueMetadata[2] = txtLibrary.getText();
				dataChangeDialogueMetadata[3] = txtShortDescription.getText();
				dataChangeDialogueMetadata[4] = txtLongDescription.getText();

				// !!!
				retrieveDataFromChangeDialogueMetadata(dataChangeDialogueMetadata);

				// !!!
				// Daten schreiben
				System.out
						.println("!!!!!!!!!!!!!!!!! Pfad:" + filePathString + "   myDatabase:" + myDatabase.toString());
				mySqlStatement = "UPDATE object_or_source_metadata SET name = '" + txtName.getText()
						+ "', library_type = '" + (String) cmbItemsLibraryType.getSelectedItem() + "' , library = '"
						+ txtLibrary.getText() + "' , short_description = '" + txtShortDescription.getText()
						+ "' , long_description = '" + txtLongDescription.getText() + "' WHERE myobjects_id = "
						+ object_id;
				System.out.println("!!!!!!!!!!!!!!!!! SQL:" + mySqlStatement);
				mySqlStatementType = "UPDATE";
				AccessSqliteDatabase aSQLiteUpdateMetadata = new AccessSqliteDatabase(myDatabase, mySqlStatement, mySqlStatementType);

				// dlgChangeDialogueMetadata schließen
				dlgChangeDialogueMetadata.dispose();
			}
		});

		
		
		
		
		// ... lblMetadataHeadline
		// -------------------------------------------------------
		JLabel lblMetadataHeadline = new JLabel();
		lblMetadataHeadline.setText("Label: lblMetadataHeadline");

		// ...
		// ----------------------------------------------------------
		JPanel pnlChangeDialogueMetadata = new JPanel(new GridBagLayout());

		// ...
		// ----------------------------------------------------------
		GridBagConstraints gbcChangeDialogueMetadata = new GridBagConstraints();
		gbcChangeDialogueMetadata.fill = GridBagConstraints.HORIZONTAL;

		// ... 1 ...
		gbcChangeDialogueMetadata.weightx = 0.5;
		gbcChangeDialogueMetadata.gridx = 0;
		gbcChangeDialogueMetadata.gridy = 0;
		pnlChangeDialogueMetadata.add(lblName, gbcChangeDialogueMetadata);
		gbcChangeDialogueMetadata.weightx = 0.5;
		gbcChangeDialogueMetadata.gridx = 1;
		gbcChangeDialogueMetadata.gridy = 0;
		pnlChangeDialogueMetadata.add(txtName, gbcChangeDialogueMetadata);

		// ... 2 ...
		gbcChangeDialogueMetadata.weightx = 0.5;
		gbcChangeDialogueMetadata.gridx = 0;
		gbcChangeDialogueMetadata.gridy = 1;
		pnlChangeDialogueMetadata.add(lblLibraryType, gbcChangeDialogueMetadata);
		gbcChangeDialogueMetadata.weightx = 0.0;
		// c.gridwidth = 2; //2 columns wide
		gbcChangeDialogueMetadata.gridx = 1;
		gbcChangeDialogueMetadata.gridy = 1;
		// panel.add(txtLibraryType, c);
		pnlChangeDialogueMetadata.add(cmbItemsLibraryType, gbcChangeDialogueMetadata);

		// ... 3 ...
		gbcChangeDialogueMetadata.weightx = 0.5;
		gbcChangeDialogueMetadata.gridx = 0;
		gbcChangeDialogueMetadata.gridy = 2;
		pnlChangeDialogueMetadata.add(lblLibrary, gbcChangeDialogueMetadata);
		gbcChangeDialogueMetadata.weightx = 0.0;
		// c.gridwidth = 2; //2 columns wide
		gbcChangeDialogueMetadata.gridx = 1;
		gbcChangeDialogueMetadata.gridy = 2;
		pnlChangeDialogueMetadata.add(txtLibrary, gbcChangeDialogueMetadata);

		// ... 4 ...
		gbcChangeDialogueMetadata.weightx = 0.5;
		gbcChangeDialogueMetadata.gridx = 0;
		gbcChangeDialogueMetadata.gridy = 3;
		pnlChangeDialogueMetadata.add(lblShortDescription, gbcChangeDialogueMetadata);
		// c.ipady = 40; //make this component tall
		gbcChangeDialogueMetadata.weighty = 1.0; // request any extra vertical
													// space
		gbcChangeDialogueMetadata.gridwidth = 2; // 2 columns wide
		gbcChangeDialogueMetadata.gridx = 1; // aligned with
		gbcChangeDialogueMetadata.gridy = 3;
		pnlChangeDialogueMetadata.add(txtShortDescription, gbcChangeDialogueMetadata);

		// ... 5 ...
		gbcChangeDialogueMetadata.weightx = 0.5;
		gbcChangeDialogueMetadata.gridx = 0;
		gbcChangeDialogueMetadata.gridy = 4;
		pnlChangeDialogueMetadata.add(lblLongDescription, gbcChangeDialogueMetadata);
		// c.ipady = 40; //make this component tall
		gbcChangeDialogueMetadata.weighty = 1.0; // request any extra vertical
													// space
		gbcChangeDialogueMetadata.gridwidth = 2; // 2 columns wide
		gbcChangeDialogueMetadata.gridx = 1; // aligned with
		gbcChangeDialogueMetadata.gridy = 4;
		pnlChangeDialogueMetadata.add(txtLongDescription, gbcChangeDialogueMetadata);

		// ... 6 ...
		gbcChangeDialogueMetadata.weightx = 1.0;
		gbcChangeDialogueMetadata.gridx = 1;
		gbcChangeDialogueMetadata.gridy = 6;
		pnlChangeDialogueMetadata.add(btnSaveChangeDialogueMetadata, gbcChangeDialogueMetadata);

		//
		// ...
		// ----------------------------------------------------------

		dlgChangeDialogueMetadata.getContentPane().add(pnlChangeDialogueMetadataHeadline, "North");
		dlgChangeDialogueMetadata.getContentPane().add(pnlChangeDialogueMetadata);

		dlgChangeDialogueMetadata.pack();

		dlgChangeDialogueMetadata.setLocation(525, 200);

		dlgChangeDialogueMetadata.setVisible(true);

		return dataChangeDialogueMetadata;
	}

	
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// rtvDataFromChangeDialogueMetadata
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public void retrieveDataFromChangeDialogueMetadata(String[] data) {
		String stringDataChangeDialogueMetadata = "";
		for (int i = 0; i < data.length; i++)
			stringDataChangeDialogueMetadata += data[i] + "; ";

			// AbstractButton label = null;
			// label.setText(stringDataChangeDialogueMetadata);

		System.out.println("Test - Über Methode rtvDataChangeDialogueMetadata ausgelesene Daten:" + stringDataChangeDialogueMetadata);
	}


	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// populateMetadatajTable
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private void populateMetadatajTable(JTable jTableMetadata) {
		System.out.println("Test - ... populateMetadatajTable");
		
		
		String[] information1 = new String[5];

		// Zugriff auf Datenbank(tabelle) mittels DB_sqlite
		// ------------------------------------------------------------------
		myDatabase = Constants.DB_PATH_MYWORK_002;

		System.out.println("!!!!!!!!!!!!!!!!! Pfad:" + filePathString + "   myDatabase:" + myDatabase.toString());
		// mySqlStatement = "SELECT * FROM object_or_source_metadata";
		mySqlStatement = "SELECT * FROM " + Constants.C_TABLE;
		AccessSqliteDatabase aSQLite_access = new AccessSqliteDatabase(myDatabase, mySqlStatement);

		// ... TableModel ...
		// -----------------------------------------------
		MyOwnTableModel tableModelMetadata = new MyOwnTableModel(aSQLite_access.data, aSQLite_access.columnNames);

		// ...
		jTableMetadata.setPreferredScrollableViewportSize(jTableMetadata.getPreferredSize());

		// ... JTable ...
		// -----------------------------------------------
		jTableMetadata.setModel(tableModelMetadata);

		// ... JTable ...
		// -----------------------------------------------
		jTableMetadata.setAutoCreateRowSorter(true);

		// ... CellRenderer
		// -----------------------------------------------
		jTableMetadata.getColumnModel().getColumn(0).setCellRenderer(new MyOwnTableCellRenderer());

		// ... JTable ... MouseListener
		// -----------------------------------------------
		jTableMetadata.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = jTableMetadata.rowAtPoint(evt.getPoint());
				int col = jTableMetadata.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					System.out.println("--------------Zeile: " + row + " Spalte: " + col + " Wert: "
							+ jTableMetadata.getValueAt(row, col));

				}

				// --------------------------------------------------------------------------------
				// ... klickbares Icon in Spalte 1 ...
				// --------------------------------------------------------------------------------
				
				if (col == 0) {
					object_id = (Integer) jTableMetadata.getModel().getValueAt(row, 0);

					MetadataDetailJFrame aDatasetDetail = null;
					try {
						aDatasetDetail = new MetadataDetailJFrame(object_id);
					} catch (SQLException ex) {
						// Logger.getLogger(GUI_main.class.getName()).log(Level.SEVERE,
						// null, ex);
					} catch (ClassNotFoundException ex) {
						// Logger.getLogger(GUI_main.class.getName()).log(Level.SEVERE,
						// null, ex);
					}
					// HIER PASSIERTS!
					// WAS??
					// internalFrameMetadata.add(aDatasetDetail);
					// aDatasetDetail.setVisible(true);
					// default title and icon

					// DAS IST ES!
					// changeDialogue();
					String[] information1 = changeDialogueMetadata();

					for (int i = 0; i < information1.length; i++) {
						System.out.println(information1[i] + ",");
					}

					// MyDialog.Response dialogResponse = d.getDialogResponse();

					System.out.println("Test - Anzahl Datensätze in ...: " + aSQLite_access.data.size());
					int j = aSQLite_access.data.size();
					for (int i = 0; i < j; i++) {
						System.out.println("Test - i: " + i);
						tableModelMetadata.removeRow(0);

					}
					
				
					// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
					// aSQLite_access.refresh()
					// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
					aSQLite_access.refresh();

					
					// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
					// tableModelMetadata.fireTableDataChanged()
					// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
					tableModelMetadata.fireTableDataChanged();

				}
			}
		});
	}

	
	
		
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// prepareInternalFrameMetadata
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private void prepareInternalFrameMetadata(JTable jTableMetadata) {

		System.out.println("--- haupt ---");
		// System.out.println(" Wert(1,1): " + tableModelMetadata.getValueAt(0,
		// 1));

		// ... JScrollpane
		// -------------------------------------------------------
		JScrollPane jScrollPanelMetadata = new JScrollPane();
		jScrollPanelMetadata.getViewport().add(jTableMetadata);
		// jScrollPanelMetadata.add(jTableMetadata);

		jScrollPanelMetadata.setPreferredSize(new Dimension(500, 500));
		jScrollPanelMetadata.setBackground(Color.yellow);

		// ... JLabel
		// -------------------------------------------------------
		JLabel lblMetadataHeadline = new JLabel();
		lblMetadataHeadline.setText("lblMetadataHeadlinexxx");
		lblMetadataHeadline.setMaximumSize(new Dimension(50, 100));
		lblMetadataHeadline.setForeground(Color.red);

		// Font DEFAULT_SCHRIFTART = new Font("Arial", Font.BOLD + Font.ITALIC,
		// 16);
		lblMetadataHeadline.setFont(DefaultValues.defaultFont);

		lblMetadataHeadline.setMinimumSize(null);
		// jScrollPanelMetadata.getViewport().add(jLabel001);

		// jPanel002.setBounds(10, 20, 30, 40);
		// lblMetadataHeadline.setText("Label: lblMetadataHeadline");
		// jPanel002.add(lblMetadataHeadline);
		// jPanel002.setSize(100, 100);

		// remove borders of internalframe
		// -------------------------------------------------------
		internalFrameMetadata.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

		internalFrameMetadata.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// make the component wide enough to fill its display area horizontally,
		// but do not change its height
		// c.fill = GridBagConstraints.HORIZONTAL;
		// c.fill = GridBagConstraints.BOTH;
		c.ipady = 10;
		// c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		internalFrameMetadata.add(lblMetadataHeadline, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 9.5;
		c.weighty = 9.5;
		c.ipady = 150;
		c.gridx = 0;
		c.gridy = 1;
		// c.weightx = 5;
		c.anchor = GridBagConstraints.SOUTH;
		internalFrameMetadata.add(jScrollPanelMetadata, c);

		// internalFrame002.setBounds(125, 125, 400, 200);

		// remove titlebar of internalframe
		// -------------------------------------------------------
		((javax.swing.plaf.basic.BasicInternalFrameUI) internalFrameMetadata.getUI()).setNorthPane(null);

		// internalFrameMetadata.add(jPanel002);
		internalFrameMetadata.setVisible(true);

		Dimension dimensionInternalFrameMetadata;
		dimensionInternalFrameMetadata = internalFrameMetadata.getSize();
		dimensionInternalFrameMetadata.height = 7;
		// System.out.println("Höhe " + dimensionInternalFrameMetadata.height);

		// internalFrame002.setLayout(new GridLayout( 3, 1 ));
		// GridBagConstraints c2 = new GridBagConstraints();
		// c2.fill = GridBagConstraints.HORIZONTAL;
		// c2.weightx = 2.0;
		// c2.ipady = 200;
		// c2.gridx = 0;
		// c2.gridy = 0;
		// internalFrame001.add(jScrollPanelMetadata, c2);

	}

	// NEU 20160408
	// ----------------------------------------------------------
	// changeDialogue
	// ----------------------------------------------------------
	// private void changeDialogue()

	class jTableMetadataListener implements TableModelListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Test - actionPerformed für ...");

		}

		@Override
		public void tableChanged(TableModelEvent tme) {
			throw new UnsupportedOperationException("Not supported yet."); // To
																			// change
																			// body
																			// of
																			// generated
																			// methods,
																			// choose
																			// Tools
																			// |
																			// Templates.
		}

	}

	
	
	/**
	 * ############################################## class
	 * ################################################
	 */
	/*
	 class RefreshListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			System.out.println("Schaltfläche REFRESH gedrückt");

			String server = "as400.holgerscherer.de";
			String user = "DREIU";
			String pass = "HLaxness";
			AS400 anAS400System = new AS400(server, user, pass);
			String aSystemName = anAS400System.getSystemName();
			System.out.println("System: " + aSystemName);

			try {
				anAS400System.connectService(anAS400System.COMMAND);
			} catch (Throwable t) {
				System.out.println("couldn't connect");
				t.printStackTrace();
			}

			ProgramCall program1 = new ProgramCall(anAS400System);
			try {
				AS400Text txt10 = new AS400Text(10);
				ProgramParameter[] parmList1 = new ProgramParameter[1];
				parmList1[0] = new ProgramParameter(txt10.toBytes("QCBLSRC"), 10);
				program1.setProgram("/QSYS.LIB/DREIU1.LIB/E#MBRINFO1.PGM", parmList1);
				displayAS400Sources();

			} catch (Throwable t) {
				System.out.println("couldn't setProgram");
				t.printStackTrace();
			}
			try {
				program1.run();
				System.out.println("the program should have been executed");
			} catch (Throwable t) {
				System.out.println("couldn't run");
				t.printStackTrace();
			}

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
			System.out.println("letzte Anmeldung: " + myDate);

			displayAS400Sources();

		}

	}
/*	

	
	
	
	
	
	
	
	
	
	
	/**
	 * ############################################## class
	 * ################################################
	 */
	class internalFrameListener implements InternalFrameListener {
		public void actionPerformed(ActionEvent event) {
			// door.open();
			// repaintText();
			System.out.println("internalFrame");

		}

		@Override
		public void internalFrameOpened(InternalFrameEvent ife) {
			throw new UnsupportedOperationException("Not supported yet1."); // To
																			// change
																			// body
																			// of
																			// generated
																			// methods,
																			// choose
																			// Tools
																			// |
																			// Templates.
		}

		@Override
		public void internalFrameClosing(InternalFrameEvent ife) {
			throw new UnsupportedOperationException("Not supported yet2."); // To
																			// change
																			// body
																			// of
																			// generated
																			// methods,
																			// choose
																			// Tools
																			// |
																			// Templates.
		}

		@Override
		public void internalFrameClosed(InternalFrameEvent ife) {
			throw new UnsupportedOperationException("Not supported yet3."); // To
																			// change
																			// body
																			// of
																			// generated
																			// methods,
																			// choose
																			// Tools
																			// |
																			// Templates.
		}

		@Override
		public void internalFrameIconified(InternalFrameEvent ife) {
			throw new UnsupportedOperationException("Not supported yet4."); // To
																			// change
																			// body
																			// of
																			// generated
																			// methods,
																			// choose
																			// Tools
																			// |
																			// Templates.
		}

		@Override
		public void internalFrameDeiconified(InternalFrameEvent ife) {
			throw new UnsupportedOperationException("Not supported yet5."); // To
																			// change
																			// body
																			// of
																			// generated
																			// methods,
																			// choose
																			// Tools
																			// |
																			// Templates.
		}

		@Override
		public void internalFrameActivated(InternalFrameEvent ife) {
			// throw new UnsupportedOperationException("Not supported yet6 .");
			// //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public void internalFrameDeactivated(InternalFrameEvent ife) {
			// throw new UnsupportedOperationException("Not supported yet7.");
			// //To change body of generated methods, choose Tools | Templates.
			System.out.println("internalFrameDeactivated");

		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		/**
		 * ---------------------------------------------- ...
		 * -----------------------------------------------
		 */
		if (arg0.getSource() == menuItemSourceMeta) {
			System.out.println("... action_performed - source_meta wurde angeklickt");

			if (swtchButtonSpecial == 0) {
				swtchButtonSpecial = 1;

				// internalFrame002.setBackground(Color.yellow);
				// internalFrame002.setSize(300, 300);

				sourceFilePane.remove(internalFrameSourceFiles);

				sourceFilePane.add(internalFrameMetadata);
				try {
					internalFrameMetadata.setMaximum(true);
				} catch (PropertyVetoException ex) {
					Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
				}

				sourceFilePane.validate();
				sourceFilePane.repaint();
			}

		}

		/**
		 * ---------------------------------------------- ...
		 * -----------------------------------------------
		 */
		if (arg0.getSource() == menuItemSource) {
			System.out.println("... action_performed - source wurde angeklickt");
			if (swtchButtonSpecial == 1) {
				swtchButtonSpecial = 0;

				sourceFilePane.remove(internalFrameMetadata);

				sourceFilePane.add(internalFrameSourceFiles);
				try {
					internalFrameSourceFiles.setMaximum(true);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// internalFrame002 = null;
				sourceFilePane.validate();
				sourceFilePane.repaint();
			}

		}

		/**
		 * ---------------------------------------------- ...
		 * -----------------------------------------------
		 */
		if (arg0.getSource() == about) {
			System.out.println("about wurde angeklickt");

			// ---------------------------------------------------
			// ---------------------------------------------------
			//
			// ---------------------------------------------------
			// ---------------------------------------------------
			String DRIVER = "com.ibm.as400.access.AS400JDBCDriver";
			// String URL = "jdbc:as400://" +
			// props.getProperty("local_system").trim() +
			// ";naming=system;errors=full";
			String URL = "jdbc:as400://" + "as400.holgerscherer.de" + ";naming=system;errors=full";
			Connection conn = null;
			String sql = null;

			// Connect to iSeries
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// conn =
			// DriverManager.getConnection(URL,props.getProperty("userId").trim(),props.getProperty("password").trim());
			try {
				conn = DriverManager.getConnection(URL, "DREIU", "HLaxness");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql = "SELECT * FROM dreiu1/glflgp";
			// System.out.println("SQL: " + sql);
			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				rs = stmt.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// It creates and displays the table
			try {
				// Variante 1
				JTable table1 = new JTable(buildTableModel(rs));
				// Variante 2
				fillTableFromCertainDatabaseTableAndCertainUser(jTableTest, "SELECT * FROM dreiu1/cusmsp;");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			 * while (rs.next()) { System.out.println("Flugnummer#: ----> " +
			 * rs.getString("FLGNR")); System.out.println("Fluggebiet: " +
			 * rs.getString("FLGGES")); System.out.println("Beschreibung: " +
			 * rs.getString("FLGBES")); }
			 */

			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("OKAY Ende");

		}

		/**
		 * ---------------------------------------------- ...
		 * -----------------------------------------------
		 */
		if (arg0.getSource() == systemi) {
			System.out.println("systemi wurde angeklickt");

			// Dialog
			/*
			 * JDialog dialog = new JDialog(); dialog.setSize(316, 145);
			 * dialog.setLocation(300,200);
			 * dialog.getContentPane().setLayout(null);
			 */

			// Button
			/*
			 * JButton btnOk = new JButton("OK"); btnOk.setBounds(108, 54, 89,
			 * 23); dialog.getContentPane().add(btnOk); dialog.setVisible(true);
			 */

			Object[] possibilities = { "as400.holgerscherer.de", "dach041a.dach041.dachser.com",
					"dach041b.dach041.dachser.com", "ulf.wanzenberg.net" };
			Component frame = null;
			Icon icon = null;
			String sChosenSystem = (String) JOptionPane.showInputDialog(frame, "System:\n" + "\"system i\"",
					"Auswahl system i", JOptionPane.PLAIN_MESSAGE, icon, possibilities, "as400.holgerscherer.de");

			// If a string was returned, say so.
			if ((sChosenSystem != null) && (sChosenSystem.length() > 0)) {
				// setLabel("Green eggs and... " + s + "!");
				System.out.println("cool");
				sSystemi = sChosenSystem;
				if (sUserSystemi == null) {
					sUserSystemi = "";
				}
				sUserAndSystemi = sSystemi + "_" + sUserSystemi + "_" + sSourceLib + "_" + sSourceFile;
				lblUserAndSystemi.setText(sUserAndSystemi);

				systemi.setText("System i auswählen [" + sSystemi + "]");
				return;
			}
		}

		/**
		 * ---------------------------------------------- ...
		 * -----------------------------------------------
		 */
		if (arg0.getSource() == usersystemi) {
			System.out.println("usersystemi wurde angeklickt");

			Object[] possibilities = { "DREIU", "WANZENBERG" };
			Component frame = null;
			Icon icon = null;
			String s = (String) JOptionPane.showInputDialog(frame, "User:\n" + "\"system i\"", "Auswahl User System i",
					JOptionPane.PLAIN_MESSAGE, icon, possibilities, "DREIU");

			// If a string was returned, say so.
			if ((s != null) && (s.length() > 0)) {
				// setLabel("Green eggs and... " + s + "!");
				// System.out.println("cool");
				// lblUserSystemi.setText(s);
				sUserSystemi = s;
				sUserAndSystemi = sSystemi + "_" + sUserSystemi + "_" + sSourceLib + "_" + sSourceFile;
				lblUserAndSystemi.setText(sUserAndSystemi);

				usersystemi.setText("(System i-)User/Benutzer auswählen [" + sUserSystemi + "]");

				return;
			}
		}

		/**
		 * ---------------------------------------------- ...
		 * -----------------------------------------------
		 */
		if (arg0.getSource() == menuItemSourceLib) {
			System.out.println("menuItemSourceLib wurde angeklickt");

			Object[] possibilities = { "DREIU1", "DOUSALLV1", "DOUSFRAV1", };
			Component frame = null;
			Icon icon = null;
			String s = (String) JOptionPane.showInputDialog(frame, "Bibliothek:\n" + "\"Lib\"", "Auswahl Bibliothek",
					JOptionPane.PLAIN_MESSAGE, icon, possibilities, "DREIU1");

			// If a string was returned, say so.
			if ((s != null) && (s.length() > 0)) {
				sSourceLib = s;
				sUserAndSystemi = sSystemi + "_" + sUserSystemi + "_" + sSourceLib + "_" + sSourceFile;
				lblUserAndSystemi.setText(sUserAndSystemi);
				return;
			}
		}

		/**
		 * ---------------------------------------------- ...
		 * -----------------------------------------------
		 */
		if (arg0.getSource() == menuItemSourceFile) {
			System.out.println("menuItemSourceFile wurde angeklickt");

			Object[] possibilities = { "QCBLSRC", "QDDSSRC", "MSTXT", "QCLSRC", };
			Component frame = null;
			Icon icon = null;
			String s = (String) JOptionPane.showInputDialog(frame, "Datei:\n" + "\"File\"", "Auswahl Datei",
					JOptionPane.PLAIN_MESSAGE, icon, possibilities, "QCBLSRC");

			// If a string was returned, say so.
			if ((s != null) && (s.length() > 0)) {
				sSourceFile = s;
				sUserAndSystemi = sSystemi + "_" + sUserSystemi + "_" + sSourceLib + "_" + sSourceFile;
				lblUserAndSystemi.setText(sUserAndSystemi);
				return;
			}
		}

		/**
		 * ---------------------------------------------- ...
		 * -----------------------------------------------
		 */
		if (arg0.getSource() == connectsystemi) {
			System.out.println("connectsystemi wurde angeklickt; System:" + sSystemi + " User: " + sUserSystemi);
			if (sUserSystemi == null || sUserSystemi == " " || sSystemi == null || sSystemi == " ") {
				System.out.println("connectsystemi: System und User müssen angegeben werden");

			} else {
				try {
					/**
					 * ---------------------------------------------- ...
					 * -----------------------------------------------
					 */
					userGetAS400SourceData(sUserSystemi, sSystemi);

				} catch (AS400Exception | AS400SecurityException | InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		/**
		 * ---------------------------------------------- ...
		 * -----------------------------------------------
		 */
		/*
		 * if (arg0.getSource() == menuItemClose) { System.out.println(
		 * "BeENDEn wurde angeklickt"); System.exit(0); }
		 */

		/**
		 * ---------------------------------------------- ...
		 * -----------------------------------------------
		 */
		if (arg0.getSource() == menuItemSQLite) {
			System.out.println("menuItemSQLite");

			int result;

			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			String choosertitle = null;
			chooser.setDialogTitle(choosertitle);

			// chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			//
			// disable the "All files" option.
			//
			// chooser.setAcceptAllFileFilterUsed(false);
			//
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

				

				
				
				
				
				System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
				System.out.println("getSelectedFile() : " + chooser.getSelectedFile());

				filePathString = chooser.getSelectedFile().toString();

				populateMetadatajTable(jTableMetadata);
				prepareInternalFrameMetadata(jTableMetadata);
			} else {
				System.out.println("No Selection ");
				
				// 20160623
				JTable.PrintMode mode = JTable.PrintMode.FIT_WIDTH;
				try {
					System.out.println("try (print(mode))");
					jTableMetadata.print(mode);
				} catch (PrinterException e) {
					// TODO Auto-generated catch block
					System.out.println("catch (print(mode))");
					e.printStackTrace();
				}
				
				
			}
		}

	}

	/**
	 * ---------------------------------------------- ...
	 * -----------------------------------------------
	 */
	private void setLabel(String string) {
		// TODO Auto-generated method stub

	}

	/**
	 * ---------------------------------------------- ...
	 * -----------------------------------------------
	 */
	// für Variante 1

	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

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

	// für Variante 2
	public void fillTableFromCertainDatabaseTableAndCertainUser(JTable table, String Query) {

        // --------------------------------------------------- 
    	// ... Define Connection ...
    	// --------------------------------------------------- 
		String DRIVER = "com.ibm.as400.access.AS400JDBCDriver";
		// String URL = "jdbc:as400://" +
		// props.getProperty("local_system").trim() +
		// ";naming=system;errors=full";
		String URL = "jdbc:as400://" + "as400.holgerscherer.de" + ";naming=system;errors=full";
		Connection conn = null;
		String sql = null;

		
        // --------------------------------------------------- 
        // Connect to iSeries                                         
    	// --------------------------------------------------- 
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
        
        // --------------------------------------------------- 
    	// ... SQL-statement and execution
    	// --------------------------------------------------- 
		try {
            //conn = DriverManager.getConnection(URL,props.getProperty("userId").trim(),props.getProperty("password").trim());   
			conn = DriverManager.getConnection(URL, "DREIU", "HLaxness");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql = "SELECT * FROM dreiu1/cusmsp";
		sql = "SELECT * FROM dreiu1/dbadrp";
		sql = "SELECT * FROM dreiu1/glflgp";

		// System.out.println("SQL: " + sql);
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
        // --------------------------------------------------- 
    	// ... remove previously added rows from DefaultTableModel
    	// --------------------------------------------------- 
		while (table.getRowCount() > 0) {
			((DefaultTableModel) table.getModel()).removeRow(0);
		}
		
		
        
        // --------------------------------------------------- 
    	// ... add/insert rows into DefaultTableModel
    	// --------------------------------------------------- 
		int columns = 0;
		try {
			columns = rs.getMetaData().getColumnCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				Object[] row = new Object[columns];
				for (int i = 1; i <= columns; i++) {
					row[i - 1] = rs.getObject(i);
					System.out.println("Wert:" + row[i - 1].toString());

				}
				((DefaultTableModel) table.getModel()).insertRow(rs.getRow() - 1, row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// Variables declaration - do not modify//GEN-BEGIN:variables
	// End of variables declaration//GEN-END:variables
}

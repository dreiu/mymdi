/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wanzenberg.ulf.mymdi;

import java.sql.SQLException;

import net.wanzenberg.ulf.mymdi.database.DBSqliteToVector;
import net.wanzenberg.ulf.mymdi.common.Constants;


/**
 *
 * @author Ulf
 */
public class MetadataDetailJFrame extends javax.swing.JInternalFrame {

	static String mySqlStatement;
	static String myDatabase;
	static String myExpectedResultType;
	private String mySqlStatement_type;
	private Integer object_id;
	private String[] resultarray;

	/**
	 * Creates new form DatenbankzugriffMaske
	 */
	public MetadataDetailJFrame(Integer parm_Object_id) throws SQLException, ClassNotFoundException {

		/**
		 *  ...
		 * -----------------------------------------------
		 */
		//initComponents();

		object_id = parm_Object_id;
		System.out.println("GENAU_v" );
		/**
		 *  ...
		 * -----------------------------------------------
		 */
		// Zugriff auf Datenbank(tabelle) mittels DB_sqlite
		// ------------------------------------------------------------------
		//myDatabase = "mywork.db";
		myDatabase = Constants.DATABASE;
		mySqlStatement = "SELECT object_id, object_name, object_type, object_description, object_library_type, object_library_or_path, source_file, source_member FROM it_object WHERE object_id = "
				+ object_id;
		mySqlStatement = "SELECT myobjects_id, name, library_type, library, short_description, short_description FROM object_or_source_metadata WHERE myobjects_id = "
				+ object_id;		

		
		System.out.println("SQL: " + mySqlStatement);
		mySqlStatement_type = "TST";
		DBSqliteToVector objectDetail = new DBSqliteToVector(myDatabase, mySqlStatement, myExpectedResultType);
		// System.out.println( "Array: " +
		// objectDetail.getSQLResultArray().toString());

		resultarray = objectDetail.getSQLResultArray();
		int size = resultarray.length;

		System.out.println("size resultarray: " + size);
		// System.out.println( "Anzahl Felder: " +
		// objectDetail.getSQLResultArray().length );
		for (int i = 0; i < size; i++) {
			 System.out.println("resultarray: " + resultarray[i]);
		}
		// jTextField_object_name.setText(resultarray[1]);
		// jTextField_object_type.setText(resultarray[2]);
		// jTextArea_object_description.setText(resultarray[3]);
		// jTextField_library_type.setText(resultarray[4]);
		// jTextField_library_path.setText(resultarray[5]);
		 //jTextField_file.setText(resultarray[2]);
		// jTextField_member.setText(resultarray[7]);



		/**
		 * ...
		 * -----------------------------------------------
		 */

		
		/**
		 * jScrollPane design
		 * -----------------------------------------------
		 */
		 //jScrollPane1.setSize(1000, 600);

		
		/**
		 * this ... design
		 * -----------------------------------------------
		 */
		System.out.println("GENAU_n" );
		this.setSize(1000, 600);

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

		
		// XXX YYY ZZZ habe ich die Felder einfach hier reingepackt!!!
		jTextField_member = new javax.swing.JTextField();
		jTextField_file = new javax.swing.JTextField();
		jTextField_library_path = new javax.swing.JTextField("HUHU");
		jTextField_library_type = new javax.swing.JTextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea_object_description = new javax.swing.JTextArea();
		jTextField_object_type = new javax.swing.JTextField();
		jTextField_object_name = new javax.swing.JTextField();

		jTextField_member.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField_memberActionPerformed(evt);
			}
		});

		jTextField_file.setToolTipText("");

		jTextArea_object_description.setColumns(20);
		jTextArea_object_description.setRows(5);
		jScrollPane1.setViewportView(jTextArea_object_description);

		jTextField_object_name.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField_object_nameActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 485,
						Short.MAX_VALUE)
				.addGroup(
						layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(
										layout.createSequentialGroup().addContainerGap()
												.addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(jTextField_object_name)
														.addComponent(jTextField_object_type)
														.addComponent(jScrollPane1,
																javax.swing.GroupLayout.DEFAULT_SIZE, 465,
																Short.MAX_VALUE)
														.addComponent(jTextField_library_type)
														.addComponent(jTextField_library_path)
														.addComponent(jTextField_file).addComponent(jTextField_member))
												.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 274, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(20, 20, 20)
								.addComponent(jTextField_object_name, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jTextField_object_type, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jTextField_library_type, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jTextField_library_path, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jTextField_file, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(9, 9, 9)
								.addComponent(jTextField_member, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(21, 21, 21))));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jTextField_memberActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField_memberActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jTextField_memberActionPerformed

	private void jTextField_object_nameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField_object_nameActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jTextField_object_nameActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea jTextArea_object_description;
	private javax.swing.JTextField jTextField_file;
	private javax.swing.JTextField jTextField_library_path;
	private javax.swing.JTextField jTextField_library_type;
	private javax.swing.JTextField jTextField_member;
	private javax.swing.JTextField jTextField_object_name;
	private javax.swing.JTextField jTextField_object_type;
	// End of variables declaration//GEN-END:variables
}
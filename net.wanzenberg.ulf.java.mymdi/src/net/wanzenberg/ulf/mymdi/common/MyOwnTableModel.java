/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wanzenberg.ulf.mymdi.common;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.xml.crypto.Data;

/** *********************************************
*  MyOwnTableModel ... 
* ***********************************************
*/
  public class MyOwnTableModel extends AbstractTableModel {

    Vector m_parmDataVector;
    Vector m_parmColumnVector;
    
    //private TableColumnModel tcm;

    
    Object data[][];
    String columnNames[] = { "", "Message ID", "Group ID", "DateSent", "Message"};    
    
    /** ++++++++++++++++++++++++++++++++++++++++++++++
    *  constructor methods
    * +++++++++++++++++++++++++++++++++++++++++++++++
    */
    /** ----------------------------------------------
    *  1st constructor ...
    * -----------------------------------------------
    */      
    public MyOwnTableModel(Vector parmDataVector, Vector parmColumnVector ) {
    	 
      super();
      //System.out.println("MyOwnTableModel");
      m_parmDataVector = parmDataVector;
      m_parmColumnVector = parmColumnVector;
    }
    
    /** ----------------------------------------------
    *  2nd constructor ...
    * -----------------------------------------------
    */          
    public MyOwnTableModel(Object[][] data) {
        this.data = new Object[data.length][columnNames.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i][0] = Boolean.TRUE;
            for (int j = 0; j < columnNames.length - 1; j++)
                this.data[i][j + 1] = data[i][j];
    }
}
    
    
    /** ++++++++++++++++++++++++++++++++++++++++++++++
    *  accessor methods
    * +++++++++++++++++++++++++++++++++++++++++++++++
    */    
    /** ----------------------------------------------
    *  getColumnCount()
    * -----------------------------------------------
    */    
    @Override
    public int getColumnCount() {
      //return m_colNames.length;
      return m_parmColumnVector.size();
    }
    
    /** ----------------------------------------------
    *  getRowCount()
    * -----------------------------------------------
    */    
    @Override
    public int getRowCount() {
      return m_parmDataVector.size();
    }
    
    /** ----------------------------------------------
    *  getColumnName(int col)
    * -----------------------------------------------
    */    
    @Override
    public String getColumnName(int col) {
      //return m_colNames[col];
      return m_parmColumnVector.elementAt(col).toString();
    }


    
    /** ----------------------------------------------
    *  getValueAt(int row, int col)
    * -----------------------------------------------
    */    
    @Override
    public Object getValueAt(int row, int col) {
      //Data macData = (Data) (m_parmDataVector.elementAt(row));
      return ((Vector) m_parmDataVector.get(row)).get(col);
      //return new String();
    }
    
    
    
    
    /** ++++++++++++++++++++++++++++++++++++++++++++++
     *  mutator methods
     * +++++++++++++++++++++++++++++++++++++++++++++++
     */
    /** ----------------------------------------------
     *  setValueAt(Object value, int row, int col)
     * -----------------------------------------------
     */
    public void setValueAt(Object value, int row, int col) {
      Data macData = (Data) (m_parmDataVector.elementAt(row));


    }
    
    
    


    
    /** ----------------------------------------------
     *  ...
     * -----------------------------------------------
     */
    /**
   * Removes a row from the table and sends a {@link TableModelEvent} to
   * all registered listeners.
   * 
   * @param row the row index.
   */
    public void removeRow(int row) {
      // Removes the element at the specified position in this Vector.
      m_parmDataVector.remove(row);
      // Notifies all listeners that rows in the range [firstRow, lastRow], inclusive, have been deleted.
      fireTableRowsDeleted(row,row);
    }

    
    /** ----------------------------------------------
     *  ...
     * -----------------------------------------------
     */
    public void addRow(Vector row) {
//      m_parmDataVector.add(row);
//      fireTableStructureChanged();
      
      
      // Adds the element at the specified position in this Vector.
      m_parmDataVector.addElement(row);  
      // Notifies all listeners that ...
      this.fireTableDataChanged();  
      
      
    }    


  }




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wanzenberg.ulf.mymdi.common;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


/** *********************************************
*  class MyRenderer ... 
* ***********************************************
*/
public class MyOwnTableCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer
{
    
    // constructor method to create an instance
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) 
    {
        //setIcon(new ImageIcon(getClass().getResource("../icon_note_small.jpg")));
        setIcon(new ImageIcon(getClass().getResource("icon_note_small.jpg")));
        //***
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
    
}
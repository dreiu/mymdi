package net.wanzenberg.ulf.mymdi.notused;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ChangeMetadata{
	public static void main(String[] bla){

		// Erstellung Array vom Datentyp Object, Hinzufügen der Komponenten		
		JTextField name = new JTextField();
		JTextField vorname = new JTextField();
                Object[] message = {"Name", name, 
        		"Vorname", vorname};

                JOptionPane pane = new JOptionPane( message, 
                                                JOptionPane.PLAIN_MESSAGE, 
                                                JOptionPane.OK_CANCEL_OPTION);
                pane.createDialog(null, "Titelmusik").setVisible(true);

                System.out.println("Eingabe: " + name.getText() + ", " + vorname.getText());
		System.exit(0);
	}
}
package net.wanzenberg.ulf.mymdi.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangePerspectiveListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
		// door.open();
		// repaintText();
		System.out.println("Test - ChangePerspectiveListener hat reagiert");

		if (ApplicationMain.swtchButtonSpecial == 1) {
			ApplicationMain.swtchButtonSpecial = 0;

			ApplicationMain.sourceFilePane.remove(ApplicationMain.internalFrameMetadata);
			ApplicationMain.sourceFilePane.add(ApplicationMain.internalFrameSourceFiles);
			// internalFrame002 = null;
			ApplicationMain.sourceFilePane.validate();
			ApplicationMain.sourceFilePane.repaint();
			
			
		} else {
			ApplicationMain.swtchButtonSpecial = 1;

			
			// internalFrame002.setBackground(Color.yellow);
			// internalFrame002.setSize(300, 300);

			ApplicationMain.sourceFilePane.remove(ApplicationMain.internalFrameSourceFiles);

			ApplicationMain.sourceFilePane.add(ApplicationMain.internalFrameMetadata);
			ApplicationMain.internalFrameMetadata.reshape(100, 100, 500, 250);
			/*try {
				ApplicationMain.internalFrameMetadata.setMaximum(true);
			} catch (PropertyVetoException ex) {
				Logger.getLogger(ApplicationMain.class.getName()).log(Level.SEVERE, null, ex);
			}*/ 

			// 20160603
			//ApplicationMain.appDesktopPane.add(ApplicationMain.internalFrameMetadata);

			ApplicationMain.sourceFilePane.validate();
			ApplicationMain.sourceFilePane.repaint();
		}

	}

}

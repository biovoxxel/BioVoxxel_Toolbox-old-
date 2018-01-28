package threshold.check;


import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.SwingUtilities;

import org.scijava.command.Command;
import org.scijava.plugin.Plugin;

import ij.IJ;
import ij.WindowManager;
import net.imagej.ImageJ;

@Plugin(type = Command.class, menuPath = "BioVoxxel>Threshold Check Test")
public class Threshold_Check implements Command {

	public void run() {
		SwingUtilities.invokeLater(new Runnable() {
			
			 public void run() {
				 try {
					 if(WindowManager.getFrame("BioVoxxels' Threshold Check")==null) {
						 ThresholdCheckGui btc = new ThresholdCheckGui();
						 btc.setVisible(true);
						 btc.addWindowListener(new WindowListener() {
							
							@Override
							public void windowOpened(WindowEvent e) {
								WindowManager.addWindow(btc);
								
							}
							
							@Override
							public void windowIconified(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void windowDeiconified(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void windowDeactivated(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void windowClosing(WindowEvent e) {
								WindowManager.removeWindow(btc);
								
							}
							
							@Override
							public void windowClosed(WindowEvent e) {
								// TODO Auto-generated method stub
								WindowManager.removeWindow(btc);
							}
							
							@Override
							public void windowActivated(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}
						});
					} else {
						WindowManager.getFrame("BioVoxxels' Threshold Check").toFront();
						//JOptionPane.showMessageDialog(null, "Image Integrity Analyzer is already open");
					}
					 
				 } catch (Exception e) {
					 e.printStackTrace();
				 }
			 }
		 });
	}
	
	/** Tests our command. */
	public static void main(final String... args) throws Exception {
		// Launch ImageJ as usual.
		final ImageJ ij = net.imagej.Main.launch(args);
		IJ.open("http://imagej.nih.gov/ij/images/boats.gif");
		// Launch the example
		//ij.command().run(Example1c.class, true);
	}

}


package threshold.check;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ij.IJ;
import ij.WindowManager;
import net.imagej.ImageJ;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JCheckBox;
import java.awt.Insets;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ThresholdCheckGui extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThresholdCheckGui frame = new ThresholdCheckGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ThresholdCheckGui() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ThresholdCheckGui.class.getResource("/resources/Voxxelgreen.png")));
		setTitle("BioVoxxels' Threshold Check");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 331, 538);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JCheckBox chckbxBrightObjects = new JCheckBox("Bright objects on a dark background");
		GridBagConstraints gbc_chckbxBrightObjects = new GridBagConstraints();
		gbc_chckbxBrightObjects.gridwidth = 2;
		gbc_chckbxBrightObjects.anchor = GridBagConstraints.WEST;
		gbc_chckbxBrightObjects.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxBrightObjects.gridx = 1;
		gbc_chckbxBrightObjects.gridy = 1;
		contentPane.add(chckbxBrightObjects, gbc_chckbxBrightObjects);
		
		JCheckBox chckbxIgnoreBlackPixels = new JCheckBox("ignore black pixels (default = off)");
		GridBagConstraints gbc_chckbxIgnoreBlackPixels = new GridBagConstraints();
		gbc_chckbxIgnoreBlackPixels.gridwidth = 2;
		gbc_chckbxIgnoreBlackPixels.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxIgnoreBlackPixels.anchor = GridBagConstraints.WEST;
		gbc_chckbxIgnoreBlackPixels.gridx = 1;
		gbc_chckbxIgnoreBlackPixels.gridy = 2;
		contentPane.add(chckbxIgnoreBlackPixels, gbc_chckbxIgnoreBlackPixels);
		
		JCheckBox chckbxIgnorewhitePixelsdefault = new JCheckBox("ignorewhite pixels (default = off)");
		GridBagConstraints gbc_chckbxIgnorewhitePixelsdefault = new GridBagConstraints();
		gbc_chckbxIgnorewhitePixelsdefault.gridwidth = 2;
		gbc_chckbxIgnorewhitePixelsdefault.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxIgnorewhitePixelsdefault.anchor = GridBagConstraints.WEST;
		gbc_chckbxIgnorewhitePixelsdefault.gridx = 1;
		gbc_chckbxIgnorewhitePixelsdefault.gridy = 3;
		contentPane.add(chckbxIgnorewhitePixelsdefault, gbc_chckbxIgnorewhitePixelsdefault);
		
		JSeparator separator1 = new JSeparator();
		GridBagConstraints gbc_separator1 = new GridBagConstraints();
		gbc_separator1.insets = new Insets(0, 0, 5, 0);
		gbc_separator1.fill = GridBagConstraints.BOTH;
		gbc_separator1.gridwidth = 5;
		gbc_separator1.gridx = 0;
		gbc_separator1.gridy = 5;
		contentPane.add(separator1, gbc_separator1);
		
		JCheckBox chckbxIncludeLocal = new JCheckBox("include local auto thresholds");
		GridBagConstraints gbc_chckbxIncludeLocal = new GridBagConstraints();
		gbc_chckbxIncludeLocal.anchor = GridBagConstraints.WEST;
		gbc_chckbxIncludeLocal.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxIncludeLocal.gridx = 1;
		gbc_chckbxIncludeLocal.gridy = 7;
		contentPane.add(chckbxIncludeLocal, gbc_chckbxIncludeLocal);
		
		JLabel lblRadius = new JLabel("radius (default = 15)");
		GridBagConstraints gbc_lblRadius = new GridBagConstraints();
		gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
		gbc_lblRadius.gridx = 1;
		gbc_lblRadius.gridy = 8;
		contentPane.add(lblRadius, gbc_lblRadius);
		
		JSpinner spinnerRadius = new JSpinner();
		spinnerRadius.setModel(new SpinnerNumberModel(new Integer(15), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinnerRadius = new GridBagConstraints();
		gbc_spinnerRadius.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerRadius.gridwidth = 2;
		gbc_spinnerRadius.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerRadius.gridx = 2;
		gbc_spinnerRadius.gridy = 8;
		contentPane.add(spinnerRadius, gbc_spinnerRadius);
		
		JLabel lblParameter1 = new JLabel("parameter 1 (default = 0)");
		GridBagConstraints gbc_lblParameter1 = new GridBagConstraints();
		gbc_lblParameter1.insets = new Insets(0, 0, 5, 5);
		gbc_lblParameter1.gridx = 1;
		gbc_lblParameter1.gridy = 9;
		contentPane.add(lblParameter1, gbc_lblParameter1);
		
		JSpinner spinnerParam1 = new JSpinner();
		GridBagConstraints gbc_spinnerParam1 = new GridBagConstraints();
		gbc_spinnerParam1.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerParam1.gridwidth = 2;
		gbc_spinnerParam1.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerParam1.gridx = 2;
		gbc_spinnerParam1.gridy = 9;
		contentPane.add(spinnerParam1, gbc_spinnerParam1);
		
		JLabel lblParameter2 = new JLabel("parameter 2 (default = 0)");
		GridBagConstraints gbc_lblParameter2 = new GridBagConstraints();
		gbc_lblParameter2.insets = new Insets(0, 0, 5, 5);
		gbc_lblParameter2.gridx = 1;
		gbc_lblParameter2.gridy = 10;
		contentPane.add(lblParameter2, gbc_lblParameter2);
		
		JSpinner spinnerParam2 = new JSpinner();
		GridBagConstraints gbc_spinnerParam2 = new GridBagConstraints();
		gbc_spinnerParam2.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerParam2.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerParam2.gridwidth = 2;
		gbc_spinnerParam2.gridx = 2;
		gbc_spinnerParam2.gridy = 10;
		contentPane.add(spinnerParam2, gbc_spinnerParam2);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.gridwidth = 5;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 12;
		contentPane.add(separator, gbc_separator);
		
		JCheckBox chckbxTestWatershed = new JCheckBox("Test Watershed-ability");
		GridBagConstraints gbc_chckbxTestWatershed = new GridBagConstraints();
		gbc_chckbxTestWatershed.anchor = GridBagConstraints.WEST;
		gbc_chckbxTestWatershed.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxTestWatershed.gridx = 1;
		gbc_chckbxTestWatershed.gridy = 13;
		contentPane.add(chckbxTestWatershed, gbc_chckbxTestWatershed);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(0)));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.gridwidth = 2;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 2;
		gbc_spinner.gridy = 13;
		contentPane.add(spinner, gbc_spinner);
		
		JCheckBox chckbxQuantificationrelativeQuality = new JCheckBox("Quantification (relative quality)");
		GridBagConstraints gbc_chckbxQuantificationrelativeQuality = new GridBagConstraints();
		gbc_chckbxQuantificationrelativeQuality.anchor = GridBagConstraints.WEST;
		gbc_chckbxQuantificationrelativeQuality.gridwidth = 2;
		gbc_chckbxQuantificationrelativeQuality.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxQuantificationrelativeQuality.gridx = 1;
		gbc_chckbxQuantificationrelativeQuality.gridy = 14;
		contentPane.add(chckbxQuantificationrelativeQuality, gbc_chckbxQuantificationrelativeQuality);
		
		JCheckBox chckbxExtendedQualityMeasures = new JCheckBox("Extended quality measures");
		GridBagConstraints gbc_chckbxExtendedQualityMeasures = new GridBagConstraints();
		gbc_chckbxExtendedQualityMeasures.anchor = GridBagConstraints.WEST;
		gbc_chckbxExtendedQualityMeasures.gridwidth = 2;
		gbc_chckbxExtendedQualityMeasures.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxExtendedQualityMeasures.gridx = 1;
		gbc_chckbxExtendedQualityMeasures.gridy = 15;
		contentPane.add(chckbxExtendedQualityMeasures, gbc_chckbxExtendedQualityMeasures);
		
		JCheckBox chckbxOutputAsMontage = new JCheckBox("Output as Montage");
		GridBagConstraints gbc_chckbxOutputAsMontage = new GridBagConstraints();
		gbc_chckbxOutputAsMontage.gridwidth = 2;
		gbc_chckbxOutputAsMontage.anchor = GridBagConstraints.WEST;
		gbc_chckbxOutputAsMontage.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxOutputAsMontage.gridx = 1;
		gbc_chckbxOutputAsMontage.gridy = 16;
		contentPane.add(chckbxOutputAsMontage, gbc_chckbxOutputAsMontage);
		
		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.fill = GridBagConstraints.BOTH;
		gbc_separator_1.gridwidth = 5;
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 17;
		contentPane.add(separator_1, gbc_separator_1);
		
		JButton btnTestCurrentImage = new JButton("Test current image");
		btnTestCurrentImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String method = IJ.getString("Method", "Default");
				ThresholdCheck tc = new ThresholdCheck();
				tc.runAutoLocalThreshold(WindowManager.getCurrentImage(), method, true);
			}
		});
		GridBagConstraints gbc_btnTestCurrentImage = new GridBagConstraints();
		gbc_btnTestCurrentImage.insets = new Insets(0, 0, 5, 5);
		gbc_btnTestCurrentImage.gridx = 1;
		gbc_btnTestCurrentImage.gridy = 19;
		contentPane.add(btnTestCurrentImage, gbc_btnTestCurrentImage);
		
		JButton btnApplySelected = new JButton("Apply selected");
		GridBagConstraints gbc_btnApplySelected = new GridBagConstraints();
		gbc_btnApplySelected.insets = new Insets(0, 0, 5, 5);
		gbc_btnApplySelected.gridx = 2;
		gbc_btnApplySelected.gridy = 19;
		contentPane.add(btnApplySelected, gbc_btnApplySelected);
	}

}

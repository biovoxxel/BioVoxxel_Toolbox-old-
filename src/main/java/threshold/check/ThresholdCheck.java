package threshold.check;

import java.awt.Color;
import java.awt.image.IndexColorModel;
import java.io.IOException;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.plugin.ContrastEnhancer;
import ij.plugin.LutLoader;
import ij.plugin.filter.EDM;
import ij.plugin.filter.MaximumFinder;
import ij.process.AutoThresholder;
import ij.process.ByteProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import ij.process.LUT;


public class ThresholdCheck {

	/*	Copyright (C), Jan Brocher / BioVoxxel. All rights reserved.
	 *
	 *	All Macros/Plugins were written by Jan Brocher/BioVoxxel.
	 *
	 *	Redistribution and use in source and binary forms of all plugins and macros, with or without modification, 
	 *	are permitted provided that the following conditions are met:
	 *
	 *	1.) Redistributions of source code must retain the above copyright notice, 
	 *	this list of conditions and the following disclaimer.
	 *	2.) Redistributions in binary form must reproduce the above copyright notice, this list of conditions 
	 *	and the following disclaimer in the documentation and/or other materials provided with the distribution.
	 *  3.) Neither the name of BioVoxxel nor the names of its contributors may be used to endorse or promote 
	 *  products derived from this software without specific prior written permission.
	 *	
	 *	DISCLAIMER:
	 *
	 *	THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ?AS IS? AND ANY EXPRESS OR IMPLIED WARRANTIES, 
	 *	INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
	 *	DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
	 *	EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
	 *	SERVICES;  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
	 *	WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE 
	 *	USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
	 *
	 */

	
	/**
	 * @author BioVoxxel
	 * @version 2.0.0
	 *
	 */
	
	public ThresholdCheck() {
		
	}
	
	byte[] reds = new byte[256];
	byte[] greens = {(byte) 0, (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8, (byte)9, (byte)10, (byte)11, (byte)12, (byte)13, (byte)14, (byte)15, (byte)16, (byte)17, (byte)18, (byte)19, (byte)20, (byte)21, (byte)22, (byte)23, (byte)24, (byte)25, (byte)26, (byte)27, (byte)28, (byte)29, (byte)30, (byte)31, (byte)32, (byte)33, (byte)34, (byte)35, (byte)36, (byte)37, (byte)38, (byte)39, (byte)40, (byte)41, (byte)42, (byte)43, (byte)44, (byte)45, (byte)46, (byte)47, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)58, (byte)59, (byte)60, (byte)61, (byte)62, (byte)63, (byte)64, (byte)65, (byte)66, (byte)67, (byte)68, (byte)69, (byte)70, (byte)71, (byte)72, (byte)73, (byte)74, (byte)75, (byte)76, (byte)77, (byte)78, (byte)79, (byte)80, (byte)81, (byte)82, (byte)83, (byte)84, (byte)85, (byte)86, (byte)87, (byte)88, (byte)89, (byte)90, (byte)91, (byte)92, (byte)93, (byte)94, (byte)95, (byte)96, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102, (byte)103, (byte)104, (byte)105, (byte)106, (byte)107, (byte)108, (byte)109, (byte)110, (byte)111, (byte)112, (byte)113, (byte)114, (byte)115, (byte)116, (byte)117, (byte)118, (byte)119, (byte)120, (byte)121, (byte)122, (byte)123, (byte)124, (byte)125, (byte)126, (byte)127, (byte)128, (byte)129, (byte)130, (byte)131, (byte)132, (byte)133, (byte)134, (byte)135, (byte)136, (byte)137, (byte)138, (byte)139, (byte)140, (byte)141, (byte)142, (byte)143, (byte)144, (byte)145, (byte)146, (byte)147, (byte)148, (byte)149, (byte)150, (byte)151, (byte)152, (byte)153, (byte)154, (byte)155, (byte)156, (byte)157, (byte)158, (byte)159, (byte)160, (byte)161, (byte)162, (byte)163, (byte)164, (byte)165, (byte)166, (byte)167, (byte)168, (byte)169, (byte)170, (byte)171, (byte)172, (byte)173, (byte)174, (byte)175, (byte)176, (byte)177, (byte)178, (byte)179, (byte)180, (byte)181, (byte)182, (byte)183, (byte)184, (byte)185, (byte)186, (byte)187, (byte)188, (byte)189, (byte)190, (byte)191, (byte)192, (byte)193, (byte)194, (byte)195, (byte)196, (byte)197, (byte)198, (byte)199, (byte)200, (byte)201, (byte)202, (byte)203, (byte)204, (byte)205, (byte)206, (byte)207, (byte)208, (byte)209, (byte)210, (byte)211, (byte)212, (byte)213, (byte)214, (byte)215, (byte)216, (byte)217, (byte)218, (byte)219, (byte)220, (byte)221, (byte)222, (byte)223, (byte)224, (byte)225, (byte)226, (byte)227, (byte)228, (byte)229, (byte)230, (byte)231, (byte)232, (byte)233, (byte)234, (byte)235, (byte)236, (byte)237, (byte)238, (byte)239, (byte)240, (byte)241, (byte)242, (byte)243, (byte)244, (byte)245, (byte)246, (byte)247, (byte)248, (byte)249, (byte)250, (byte)251, (byte)252, (byte)253, (byte)254, (byte)255};
	byte[] blues = new byte[256];
	
	//LUT btcLut = new LUT(reds, greens, blues);
	
	public static String[] autoThresholdMethods = AutoThresholder.getMethods();
	public static String[] autoLocalThresholdMethods = new String[]{"Bernsen", "Contrast", "Mean", "Median", "MidGrey", "Niblack","Otsu", "Phansalkar", "Sauvola"};
	
	
	
		
	public ImageStack runAutoThreshold(ImagePlus imp, boolean darkBackground) {
		ImageStack outputStack = new ImageStack(imp.getWidth(), imp.getHeight(), autoThresholdMethods.length);
		for(int t=0; t<autoThresholdMethods.length; t++) {
			
			outputStack.setProcessor(thresholdImageProcessor(imp.getProcessor().duplicate(), autoThresholdMethods[t], darkBackground), t);
		}
		
		return outputStack;
	}
	
	public void runAutoLocalThreshold(ImagePlus imp, String threshold, boolean darkBackground) {
		
		AutoThresholder at = new AutoThresholder();
		
		int thresholdValue = at.getThreshold(threshold, imp.getProcessor().getHistogram());
		System.out.println(thresholdValue);
		
		for(int i=0; i<reds.length; i++) {
			if(i<thresholdValue) {
				reds[i] = 0;
				blues[i] = (byte)255;
			} else {
				reds[i] = (byte)255;
				blues[i] = 0;
			}
		}
		
		LUT newLUT = new LUT(reds, greens, blues);
		
		imp.getProcessor().setLut(newLUT);
		imp.updateAndDraw();
		
	}
	
	
	public void thresholdImage(ImagePlus imp, String method, boolean darkBackground) {
		imp.setProcessor(thresholdImageProcessor(imp.getProcessor(), method, darkBackground));
	}
	
	public ImageProcessor thresholdImageProcessor(ImageProcessor ip, String method, boolean darkBackground) {
		ImageProcessor duplicateIP = ip.duplicate();
		duplicateIP.setAutoThreshold(method, darkBackground, ImageProcessor.BLACK_AND_WHITE_LUT);
		return duplicateIP;
	}
	
	public void applyHiLoLut(ByteProcessor bp) {
		try {
			IndexColorModel cm = LutLoader.open("/HiLo.lut");
			LUT hiloLUT = new LUT(cm, 0, 255);
			
			bp.setLut(hiloLUT);
			bp.applyLut();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void applyYellowLut(ByteProcessor bp) {
		LUT yellow = LUT.createLutFromColor(Color.yellow);
		bp.setLut(yellow);
		bp.applyLut();
	}
	
	
	public void mergeImageWithMask(ImagePlus image, ImagePlus mask) {
		
		IJ.setPasteMode("Add");
		ContrastEnhancer ce = new ContrastEnhancer();
		
		ImagePlus duplicateImage = image.duplicate();
		ce.setNormalize(true);
		ce.stretchHistogram(duplicateImage, 1.0);
		
		duplicateImage.copy();
		mask.paste();
	}
	
	
	
	
		
	/* This ImageJ plugin does Watershed segmentation of the EDM, similar to
	 * Process>Binary>Watershed but with adjustable sensitivity.
	 * The ImageJ Process>Binary>Watershed algorithm has a tolerance of 0.5.
	 *
	 * 2012-May-08 Michael Schmid
	 */
	private void runAdjustableWatershed(ImageProcessor ip, double tolerance) {
		MaximumFinder maxFinder = new MaximumFinder(); 
		int backgroundValue = 0;
        FloatProcessor floatEdm = new EDM().makeFloatEDM(ip, backgroundValue, false);
        ByteProcessor newIp = maxFinder.findMaxima(floatEdm, tolerance, ImageProcessor.NO_THRESHOLD, MaximumFinder.SEGMENTED, false, true);
        
        drawSegmentationLines(ip, backgroundValue, newIp);
        ip.setBinaryThreshold();
	}
	
	private void drawSegmentationLines(ImageProcessor ip, int backgroundValue, ByteProcessor segmentedIp) {
        byte[] origPixels = (byte[])ip.getPixels();
        byte[] segmPixels = (byte[])segmentedIp.getPixels();
        for (int p=0; p<origPixels.length; p++)
            if (segmPixels[p] == 0) {
            	origPixels[p] = (byte)backgroundValue;
            }
    }

	
}

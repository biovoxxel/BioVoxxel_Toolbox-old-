package deprecated;

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import java.awt.event.*;
import ij.plugin.filter.*;
import ij.measure.Calibration;
import ij.measure.CurveFitter;
import org.apache.commons.math3.stat.correlation.*;
import org.apache.commons.math3.stat.inference.*;

/*
 *	Copyright (C), Jan Brocher / BioVoxxel. All rights reserved.
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

public class Interactive_Correlation_Plot implements PlugInFilter, MouseListener, ImageListener {
	
	ImagePlus imp, plotIMP;
	ImageProcessor ip, imageRoi;
	Object snapshotBuffer;
	Plot correlationPlot;
	ImageCanvas imageCanvas;
	int startX, startY, w, h;
	int flags = DOES_RGB|SNAPSHOT|SUPPORTS_MASKING;
	
	private double[] ch1;
	private double[] ch2;
	
	
	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		ip = imp.getProcessor();
		Prefs.keepUndoBuffers = true;
		ip.snapshot();
		return flags;
	}
	
	public void run(ImageProcessor ip) {
		Rectangle roi = ip.getRoi();
		imageRoi = ip.getMask();
	    startX = roi.x;
		startY = roi.y;
		w = roi.width;
		h = roi.height;
		ImagePlus copyOfRoi = imp.duplicate();
		
		
		correlationPlot = new Plot("Plot of " + imp.getTitle(), "red", "green", Plot.X_TICKS|Plot.Y_TICKS|Plot.X_NUMBERS|Plot.Y_NUMBERS);
		plotIMP = createPlot(copyOfRoi, imageRoi).getImagePlus();
		plotIMP.show();
		plotIMP.addImageListener(this);
		ImageWindow win = plotIMP.getWindow();
		imageCanvas = win.getCanvas();
		imageCanvas.addMouseListener(this);
	}
	
	public Plot createPlot(ImagePlus inputImage, ImageProcessor inputSelection) {
		int inputImageSize = inputImage.getWidth() * inputImage.getHeight();
		ch1 = new double[inputImageSize];
		ch2 = new double[inputImageSize];
		double sumRed = 0;
		double sumGreen = 0;
		
		ImageProcessor inputProcessor = inputImage.getProcessor();
		ColorProcessor colorIP = inputProcessor.convertToColorProcessor();
		//double[] dR = new double[inputImageSize];
		//double[] dG = new double[inputImageSize];
		int index = 0;
		for(int y=0; y<inputImage.getHeight(); y++) {
			for(int x=0; x<inputImage.getWidth(); x++) {
				if(inputSelection==null || inputSelection.getPixel(x,y)!=0) {
					ch1[index] = (double) ((colorIP.getPixel(x,y)>>16)&0xff);
					ch2[index] = (double) ((colorIP.getPixel(x,y)>>8)&0xff);
					sumRed = sumRed + ch1[index];
					sumGreen = sumGreen + ch2[index];
					index++;
				} else {
					ch1[index] = 0.0;
					ch2[index] = 0.0;
					sumRed = sumRed + ch1[index];
					sumGreen = sumGreen + ch2[index];
					index++;
				}
			}
		}
		
		CurveFitter cf = new CurveFitter(ch1, ch2);
		cf.doFit(CurveFitter.STRAIGHT_LINE);
		double[] param = cf.getParams();
		double R = cf.getRSquared();
		//IJ.log("" + cf.getStatusString());
		double[] cfX = new double[2];
		double[] cfY = new double[2];
		cfX[0] = 0.0;
		cfY[0] = param[0] + (param[1] * 0);
		cfX[1] = 255.0;
		cfY[1] = param[0] + (param[1] * 255.0);
		
		
		//KolmogorovSmirnovTest KST = new KolmogorovSmirnovTest();
		//double KoSmirTest = KST.kolmogorovSmirnovStatistic(dR, dG);
		
		PearsonsCorrelation pearsonCorr = new PearsonsCorrelation();
		double pcc = pearsonCorr.correlation(ch1, ch2);
		SpearmansCorrelation spearmanCorr = new SpearmansCorrelation();
		double spc = spearmanCorr.correlation(ch1, ch2);
		
		//after addition of apache.commons.math3 version 3.4.1
		KendallsCorrelation kendallsCorr = new KendallsCorrelation();
		double tau = kendallsCorr.correlation(ch1, ch2);
		
		double averageRed = sumRed / index;
		double averageGreen = sumGreen / index;
		
		//create the plot from the R and G value arrays
		correlationPlot.setSize(600, 580);
		correlationPlot.setFrameSize(510, 510);
		correlationPlot.setLimits(0.0, 255.0, 0.0, 255.0);
		correlationPlot.addPoints(ch1, ch2, Plot.DOT);
		correlationPlot.setColor(java.awt.Color.red);
		correlationPlot.drawLine(averageRed, 0.0, averageRed, 255.0);
		correlationPlot.setColor(java.awt.Color.green);
		correlationPlot.drawLine(0.0, averageGreen, 255.0, averageGreen);
		correlationPlot.setColor(java.awt.Color.blue);
		correlationPlot.drawLine(cfX[0], cfY[0], cfX[1], cfY[1]);
		/*
		if(pcc>0.0 && averageRed>averageGreen) {
			correlationPlot.drawLine(0.0, 0.0, 255.0, (255.0 * pcc));
		} else if(pcc>0.0 && averageRed<averageGreen) {
			correlationPlot.drawLine(0.0, 0.0, (255.0 * pcc), 255.0);
		} else if(pcc<0.0) {
			correlationPlot.drawLine(0.0, 255.0, 255.0, (255 + 255*pcc));
		}
		*/
		/*
		int decimalPlacesInKSTValue = String.valueOf(KoSmirTest).length();
		if(decimalPlacesInKSTValue>7) {
			correlationPlot.addLabel(0.0, 0.0, "p (K-S) = " + String.valueOf(KoSmirTest).substring(0,7));
		} else {
			correlationPlot.addLabel(0.0, 0.0, "p (K-S) = " + String.valueOf(KoSmirTest));
		}
		*/
		correlationPlot.addLabel(0.0, 0.0, "R^2 = " + String.valueOf(R).substring(0,6));
		correlationPlot.addLabel(0.25, 0.0, "pcc = " + String.valueOf(pcc).substring(0,6));
		correlationPlot.addLabel(0.5, 0.0, "spc = " + String.valueOf(spc).substring(0,6));
		correlationPlot.addLabel(0.75, 0.0, "tau = " + String.valueOf(tau).substring(0,6));
		
		return correlationPlot;
	}
	
	public void mouseReleased(MouseEvent e) {
		ip.reset();
		//IJ.log("mouseReleased");
		Roi currentSelection = plotIMP.getRoi();
		Calibration cal = plotIMP.getCalibration();
		ImageProcessor selectedPlotArea = null; 
		try {
			selectedPlotArea = currentSelection.getMask();
		} catch(NullPointerException npe) {
			return;
		}
							//test output
							//ImagePlus maskIMP = new ImagePlus("Mask", selectedPlotArea);
							//maskIMP.show();
		Rectangle boundingRectangle = currentSelection.getBounds();
		//IJ.log("Roi: "+currentSelection + " --> Mask: " + selectedPlotArea);
		if(currentSelection!=null) {
			if(!currentSelection.isArea()) {
				IJ.showMessage("selection needs to be an area");
			} else {
				double[] storedValueCh1 = new double[boundingRectangle.height * boundingRectangle.width];
				double[] storedValueCh2 = new double[boundingRectangle.height * boundingRectangle.width];
				int count = 0;
				double xOrigin = (boundingRectangle.x - 60) * cal.pixelWidth;
				double yOrigin = (plotIMP.getHeight() - boundingRectangle.y - 40) * cal.pixelHeight;
					//IJ.log("" + xOrigin + "/" + yOrigin);
				int imagePixelCount = w * h;
				int outputIndex = 0;
				int x = 0;
				int y = 0;
				
				//If user selects a non-rectangular in the plot window runs this part
				if(selectedPlotArea!=null) {
					for(int v=0; v<boundingRectangle.height; v++) {
						for(int u=0; u<boundingRectangle.width; u++) {
							if(selectedPlotArea.getPixel(u, v)!=0) {
								storedValueCh1[count] = xOrigin + (u * cal.pixelWidth);
								storedValueCh2[count] = yOrigin - (v * cal.pixelHeight);
								//IJ.log("" + (xOrigin + (u * cal.pixelWidth)) + "-->" + (yOrigin - (v * cal.pixelHeight)));
								count++;
							}
						}
					}
					
					for(int i=0; i<imagePixelCount; i++) {
						if((ch1[i]>=xOrigin && ch1[i]<=(xOrigin+boundingRectangle.width)) && (ch2[i]<=yOrigin && ch2[i]>=(yOrigin-boundingRectangle.height)) && (imageRoi==null || imageRoi.getPixel((i % w),(i / w))!=0)) {
							for(int r=0; r<count; r++) {
								if(ch1[i]==storedValueCh1[r] && ch2[i]==storedValueCh2[r]) {
									y = (i / w);
									x = (i % w);
									ip.putPixelValue((x + startX), (y + startY), 255.0);
								} else {
									//do nothing
								}
							}
						}
					}
					imp.setProcessor(ip);
				
				//If user selects a rectangular in the plot window runs this part
				} else {
					//get rectangular ROI in the plot window and read in containing pixel values
					for(int v=0; v<boundingRectangle.height; v++) {
						for(int u=0; u<boundingRectangle.width; u++) {
								storedValueCh1[count] = xOrigin + (u * cal.pixelWidth);
								storedValueCh2[count] = yOrigin - (v * cal.pixelHeight);
								//IJ.log("" + (xOrigin + (u * cal.pixelWidth)) + "-->" + (yOrigin - (v * cal.pixelHeight)));
								count++;
						}
					}
					
					for(int i=0; i<imagePixelCount; i++) {
						if((ch1[i]>=xOrigin && ch1[i]<=(xOrigin+boundingRectangle.width)) && (ch2[i]<=yOrigin && ch2[i]>=(yOrigin-boundingRectangle.height)) && (imageRoi==null || imageRoi.getPixel((i % w),(i / w))!=0)) {
							for(int r=0; r<count; r++) {
								if(ch1[i]==storedValueCh1[r] && ch2[i]==storedValueCh2[r]) {
									y = (i / w);
									x = (i % w);
									//IJ.log(""+x +" / " + y);
									ip.putPixelValue((x + startX), (y + startY), 255.0);
								} else {
									//do nothing
								}
							}
						}
					}
				}
			}
		} else {
			ip.reset();
			imp.setProcessor(ip);
		}
		imp.updateAndDraw();
	}
	
	
	public void mousePressed(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}	
	public void mouseEntered(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	
	public void imageClosed(ImagePlus plot) {
		ImagePlus.removeImageListener(this);
		ip.reset();
		imp.setProcessor(ip);
		imp.updateAndDraw();
	}
	
	public void imageOpened(ImagePlus imp) {}
	public void imageUpdated(ImagePlus imp) {}
	
}
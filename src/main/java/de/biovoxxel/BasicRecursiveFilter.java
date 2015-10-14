/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package de.biovoxxel;
import net.imagej.Dataset;
import net.imagej.DefaultDataset;
import net.imagej.ImageJ;
import net.imagej.display.DatasetView;
import net.imagej.display.DefaultDatasetView;
import net.imagej.display.ImageDisplay;

import org.scijava.ItemIO;
import org.scijava.ItemVisibility;
import org.scijava.command.Command;
import org.scijava.command.Previewable;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * An ImageJ2 command with preview that is triggered only check the Preview
 * checkbox is active.
 */
@Plugin(type = Command.class,
	menuPath = "BioVoxxel>Basic Recursive Filter")
public class BasicRecursiveFilter implements Command {

	// -- Parameters --

	//@Parameter
	//private DatasetService datasetService;
	
	@Parameter //(type = ItemIO.INPUT)
	private Dataset ds;
	
	@Parameter(type = ItemIO.OUTPUT)
	private Dataset duplicate;
	
	@Parameter
	DatasetView dsv;
	
/*
	@Parameter(persist = true, label = "filter radius", description = "choose a filter radius", choices = {"1", "2", "3", "4", "5"})
	private String radius = "1";
	
	@Parameter (style = "slider", min = "1", max = "500", stepSize = "1", persist = true, label = "max. iterations", description = "will stop filtering if reaching this iteration number")
	private int iterations = 300;

	
	@Parameter(visibility = ItemVisibility.INVISIBLE, persist = false,
		callback = "previewChanged")
	private boolean preview;
*/
	// -- Other fields --

	

	// -- Command methods --

	public void run() {
		
		//duplicate = ds.duplicate();
		
		
	}

/*
	// -- Previewable methods --

	public void preview() {
		if (preview) run();
	}

	public void cancel() {
		// Set the image's title back to the original value.
		
	}

	// -- Callback methods --

	// Called when the {@link #preview} parameter value changes. 
	protected void previewChanged() {
		// When preview box is unchecked, reset the image title back to original.
		if (!preview) cancel();
	}

*/

	// -- Main method --

	/** Tests our command. */
	public static void main(final String... args) throws Exception {
		// Launch ImageJ as usual.
		final ImageJ ij = net.imagej.Main.launch(args);
		
		final Dataset img = ij.scifio().datasetIO().open("C:\\Users\\BioVoxxel\\Desktop\\lena-std.tif");
		ij.ui().show(img);
		
		// Launch the "PreviewCheckbox" command.
		//ij.command().run(BasicRecursiveFilter.class, true);
	}

	

}

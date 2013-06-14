package nl.tue.fingerpaint.client.storage;

/**
 * Utility class that can be used to export files.
 * 
 * @author Group Fingerpaint
 */
public class FileExporter {

	/**
	 * Exports the line chart that is currently shown on screen as an svg
	 * image. " xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\"" is 
	 * automatically added to the resulting svg, so it should not already
	 * be present.
	 * 
	 * @param svg SVG image as a string.
	 */
	public static void exportSvgImage(String svg) {
		// Add tags to indicate that this should be an svg image
		svg = svg.substring(0, 4)
				+ " xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\""
				+ svg.substring(4, svg.length());
		
		promptSvgDownload(svg);
	}
	
	/**
	 * Saves a string representing an svg file to disk by showing a file download dialog.
	 * @param svg File to save, in string representation.
	 */
	private static native void promptSvgDownload(String svg) /*-{
		var blob = new $wnd.Blob([svg], {type: "image/svg+xml;charset=utf-8"});
		$wnd.saveAs(blob, "graph.svg");
	}-*/;
}

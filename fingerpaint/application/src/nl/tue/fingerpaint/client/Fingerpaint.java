package nl.tue.fingerpaint.client;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Tessa Belder
 */
public class Fingerpaint implements EntryPoint {

	// Button to toggle between black and white drawing color
	private ToggleButton toggleColor;

	// Rectangular geometry to draw on
	private Geometry geom;

	// Horizontal panel to contain drawing canvas and menubar
	private HorizontalPanel panel = new HorizontalPanel();

	// Vertical panel to contain all menu items
	private VerticalPanel menuPanel = new VerticalPanel();

	// Width of the menu in which buttons are displayed
	// on the right side of the window in pixels
	private static final int menuWidth = 125;

	// Height of address-bar / tabs / menu-bar in the
	// browser in pixels. If this is not taken into account,
	// a vertical scrollbar appears.
	private static final int topBarHeight = 50;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Initialise geometry
		geom = new RectangleGeometry(Window.getClientHeight() - topBarHeight,
				Window.getClientWidth() - menuWidth);

		// Initialise toggleButton and add to menuPanel
		createToggleButton();
		menuPanel.add(toggleColor);

		// TODO: Initialise other menu items and add them to menuPanel

		// Add canvas and menuPanel to the panel
		// Make the canvas the entire width of the screen except for the
		// menuWidth
		panel.setWidth("100%");
		panel.add(geom.getCanvas());
		panel.add(menuPanel);
		panel.setCellWidth(menuPanel, Integer.toString(menuWidth));

		// Add panel to RootPanel
		RootPanel.get().add(panel);
	}

	/*
	 * Initialises the toggleColor button. TODO: Use pictures instead of text on
	 * the button.
	 * 
	 * Note: If the button shows "black" it means the current drawing color is
	 * black. Not 'toggle to black'.
	 */
	private void createToggleButton() {
		toggleColor = new ToggleButton("black", "white");
		toggleColor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				toggleColor();

			}
		});
	}

	/*
	 * Changes the current drawing color from black to white, and from white to
	 * black.
	 */
	private void toggleColor() {
		if (toggleColor.isDown()) {
			geom.setColor(CssColor.make("white"));
		} else {
			geom.setColor(CssColor.make("black"));
		}
	}
}
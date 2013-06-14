package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.gui.menu.MenuLevelSwitcher;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to change the drawing tool.
 * 
 * @author Group Fingerpaint
 */
public class ToolSelectButton extends Button implements ClickHandler {

	/**
	 * Construct the {@link ToolSelectButton}.
	 */
	public ToolSelectButton() {
		super(FingerpaintConstants.INSTANCE.btnSelectTool());
		addClickHandler(this);
		ensureDebugId("toolSelectButton");
	}

	/**
	 * Shows the popup in which to select the drawing tool.
	 * @param event The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		MenuLevelSwitcher.showSub1MenuToolSelector();
	}
}

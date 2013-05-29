package nl.tue.fingerpaint.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.tue.fingerpaint.client.Geometry.StepAddedListener;
import nl.tue.fingerpaint.client.json.ProtocolMap;
import nl.tue.fingerpaint.client.json.ProtocolStorage;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.resources.FingerpaintResources;
import nl.tue.fingerpaint.client.serverdata.ServerDataCache;
import nl.tue.fingerpaint.client.simulator.SimulatorService;
import nl.tue.fingerpaint.client.simulator.SimulatorServiceAsync;
import nl.tue.fingerpaint.shared.GeometryNames;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.storage.client.StorageMap;
import com.google.gwt.user.cellview.client.CellBrowser;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Group Fingerpaint
 */
public class Fingerpaint implements EntryPoint {
	// Class to keep track of everything the user has selected
	protected ApplicationState as;

	// Label that displays the userChoice values
	private Label mixingDetails = new Label();

	// Button to toggle between black and white drawing colour
	private ToggleButton toggleColor;

	// Button to load predefined distribution half black, half white
	// Needed for testing purposes for story 32
	private Button loadDistButton;

	// Button to reset the distribution to all white
	private Button resetDistButton;

	// Button to save the current results
	private Button saveResultsButton;

	// Button to load an initial distribution
	private Button loadInitDistButton;

	// --------------------------------------------------------------------------------------
	// Popup Panel to handle the loading of an initial distribution or mixing
	// protocol

	// Vertical Panel to hold the textbar and the cancel button in the load
	// popuppanel
	private VerticalPanel loadVerticalPanel;

	// close Button inside the save popup menu
	private Button closeLoadButton;

	// --------------------------------------------------------------------------------------

	// Button to remove previously saved results

	// --------------------------------------------------------------------------------------

	// Button to adapt the drawing tool
	// TODO: Change this to a button on which the current tool is drawn

	// Panel that covers the entire application and blocks the user from
	// accessing other features
	private static FlowPanel loadingPanel = new FlowPanel();

	// Popup Panel to handle the saving of the current results
	private PopupPanel saveResultsPanel;

	// Vertical Panel to hold the textbar and the save button in the save
	// popuppanel
	private VerticalPanel saveResultsVerticalPanel;

	// Horizontal Panel to hold the Save and Cancel buttons in the popup panel
	private HorizontalPanel saveButtonsPanel;

	// Textbox to input the name in to name the file
	private TextBox saveNameTextBox;

	// Save Button inside the save popup menu
	private Button saveResultsPanelButton;

	// Cancel Button inside the save popup menu
	private Button cancelSaveResultsButton;

	// Popup Panel that appears after the Save button in the save popup panel
	// has been pressed
	private PopupPanel confirmSavePanel;

	// Vertical Panel to hold the save message and the ok/overwrite button in
	// the confirm save popup panel
	private VerticalPanel confirmSaveVerticalPanel;

	// Label to hold the save message
	private Label saveMessageLabel;

	// Horizontal Panel to hold the ok or overwrite/cancel button(s) in the
	// confirm save popup panel
	private HorizontalPanel confirmButtonsPanel;

	// Ok / Cancel button to close the save results popup panel
	private Button closeSaveButton;

	// Overwrite button to confirm the save if an already used name has been
	// chosen
	private Button confirmSaveResultsButton;

	// --------------------------------------------------------------------------------------

	// Button to remove previously saved results
	private Button removeSavedResultsButton;

	// Popup Panel to handle the removing of results
	private PopupPanel removeResultsPanel;

	// Vertical panel to hold the flex panel and close button
	private VerticalPanel removeResultsVerticalPanel;

	// FlexTable to hold all the result entries
	private FlexTable resultsFlexTable;

	// Button to close the remove results popup panel
	private Button closeResultsButton;

	// --------------------------------------------------------------------------------------

	private Button saveProtocolButton;

	private Button saveProtocolPanelButton;

	private Button confirmSaveProtocolButton;

	private Button saveDistributionButton;

	private Button saveDistributionPanelButton;

	private Button confirmSaveDistributionButton;

	// Button to adapt the drawing tool
	// TODO: Change this to a button on which the current tool is drawn
	private Button toolSelectButton;

	// PopupPanel which contains options for selecting a different drawing tool
	private PopupPanel toolSelector;

	// The panel in the popup panel to seperate the toolSelector from the
	// toolSizer
	private HorizontalPanel popupPanelPanel;

	// The panel in the popup panel that contains the different drawing tools
	private VerticalPanel popupPanelMenu;

	// Button to select the square drawing tool
	// TODO: Change this to a button on which a square is drawn
	private ToggleButton squareDrawingTool;

	// Button to select the circle drawing tool
	// TODO: Change this to a button on which a circle is drawn
	private ToggleButton circleDrawingTool;

	// Horizontal panel to contain drawing canvas and menu bar
	private HorizontalPanel panel = new HorizontalPanel();

	// Vertical panel to contain all menu items
	private VerticalPanel menuPanel = new VerticalPanel();

	// Panel that covers the entire application and blocks the user from
	// accessing other features
	private static PopupPanel loadPanel = new PopupPanel();
	private Label loadPanelMessage;

	// The NumberSpinner and label to define the step size
	// TODO: The text 'Step size' should be translated later on
	private Label sizeLabel = new Label("Step size");
	private NumberSpinner sizeSpinner;

	// Checkbox that needs to be checked to define a protocol. If it isn't
	// checked, steps are executed directly.
	private CheckBox defineProtocolCheckBox;

	// The NumberSpinner and label to define how many times the mixing protocol
	// is executed
	// TODO: The text '#steps' should be translated later on
	private Label nrStepsLabel = new Label("#steps");
	private NumberSpinner nrStepsSpinner;

	/**
	 * Shows the textual representation of the mixing protocol.
	 */
	// Button that executes the current mixing run when it is pressed
	private Button mixNowButton;

	// Button that resets the protocol when it is pressed
	private Button resetProtocolButton;

	/*
	 * The NumberSpinner to set the #steps parameter. Its settings are described
	 * via the following parameters.
	 */
	private final double NRSTEPS_DEFAULT = 1.0;
	private final double NRSTEPS_RATE = 1.0;
	private final double NRSTEPS_MIN = 1.0;
	private final double NRSTEPS_MAX = 50.0;

	private static final String LOADPANEL_ID = "loading-overlay";
	private static final String LOADPANEL_MESSAGE_ID = "loading-overlay-message";

	// Width of the menu in which buttons are displayed
	// on the right side of the window in pixels
	private final int menuWidth = 200;

	// Height of address-bar / tabs / menu-bar in the
	// browser in pixels. If this is not taken into account,
	// a vertical scroll bar appears.
	private final int topBarHeight = 65;

	/**
	 * Shows the textual representation of the mixing protocol.
	 */
	private TextArea taProtocolRepresentation = new TextArea();

	private Storage storage;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Load CSS
		FingerpaintResources.INSTANCE.css().ensureInjected();

		// Initialise the loading panel
		// Add animation image
		Image loadImage = new Image(FingerpaintResources.INSTANCE.loadImage()
				.getSafeUri());
		loadingPanel.add(loadImage);

		// Add label that may contain explanatory text
		loadPanelMessage = new Label(
				FingerpaintConstants.INSTANCE.loadingGeometries(), false);
		loadPanelMessage.getElement().setId(LOADPANEL_MESSAGE_ID);
		loadingPanel.add(loadPanelMessage);
		loadingPanel.getElement().setId(LOADPANEL_ID);

		// initialise the underlying model of the application
		as = new ApplicationState();
		as.setNrSteps(1.0);
		setLoadPanelVisible(true);
		ServerDataCache.initialise(new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				setLoadPanelVisible(false);
				loadMenu();
			}

			@Override
			public void onFailure(Throwable caught) {
				setLoadPanelVisible(false);
				if (caught instanceof RequestTimeoutException) {
					showError("The simulation server did not respond in"
							+ " time. Try again later");
				} else {
					showError(caught.getMessage());
				}
				setLoadPanelVisible(false);
				showError(caught.getMessage());
			}
		});

		try {
			initLocalStorage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Build and show the main menu.
	 */
	protected void loadMenu() {
		// Create a model for the cellbrowser.
		TreeViewModel model = new CustomTreeModel();

		/*
		 * Create the browser using the model. We specify the default value of
		 * the hidden root node as "null".
		 */
		CellBrowser tree = (new CellBrowser.Builder<Object>(model, null))
				.build();

		// Add the tree to the root layout panel.
		RootLayoutPanel.get().add(tree);
	}

	/**
	 * Tries to initialise local storage.
	 * 
	 * @throws Exception
	 *             If HTML5 Local Storage is not supported in this browser.
	 */
	private void initLocalStorage() throws Exception {
		storage = Storage.getLocalStorageIfSupported();

		if (storage == null) {
			throw new Exception(
					"HTML5 Local Storage is not supported in this browser.");
		}
	}

	/**
	 * Saves the state of the application to the HTML5 local storage under key
	 * {@code name}.
	 * 
	 * @param name
	 *            The name to save the state under.
	 */
	private void saveState(String name) {
		String asJson = as.jsonize();

		storage.setItem(name, asJson);
	}

	private void saveProtocol(String name) {
		// JSONObject jsonProtocols;
		String jsonProtocols;
		// try {
		// jsonProtocols = new JSONObject(storage.getItem("PROT"));
		jsonProtocols = storage.getItem("PROT");
		// } catch (JSONException e) {
		// System.err.println(e.toString());
		// jsonProtocols = null;
		// }
		ProtocolStorage saveProtObject = null;
		if (jsonProtocols == null) {
			saveProtObject = new ProtocolStorage();
		} else {
			saveProtObject = ProtocolStorage.unJsonize(jsonProtocols);
			// TODO: extract
		}

		String shortName = GeometryNames.getShortName(as.getGeometryChoice());
		ProtocolMap saveProt = saveProtObject.getProtocols().get(shortName);
		if (saveProt == null) {
			saveProt = new ProtocolMap();
			saveProtObject.addProtocol(shortName, saveProt);
		}
		saveProt.addProtocol(name, as.getProtocol());
		storage.setItem("PROT", saveProtObject.jsonize());
	}

	/**
	 * Returns whether a saved state with key {@code name} exists in local
	 * storage.
	 * 
	 * @param name
	 *            The key to check.
	 * @return whether a saved state with key {@code name} exists in local
	 *         storage.
	 */
	private boolean isNameInUse(String name) {
		StorageMap storageMap = new StorageMap(storage);
		return storageMap.containsKey(name);
	}

	/**
	 * Loads the JSON object from HTML storage. Has no effect if no information
	 * is stored under {@code saveName}.
	 * 
	 * @param saveName
	 */
	private void loadState(String saveName) {
		String jsonObject = storage.getItem(saveName);

		if (jsonObject != null && jsonObject != "") {
			as.unJsonize(jsonObject);
			as.drawDistribution();
		}

		refreshWidgets();
	}

	private void refreshWidgets() {
		nrStepsSpinner.setValue(as.getNrSteps());
		sizeSpinner.setValue(as.getStepSize());

		for (MixingStep step : as.getProtocol().getProgram()) {
			updateProtocolLabel(step);
		}
	}

	/**
	 * Show a pop-up with given message that indicates an error has occurred.
	 * 
	 * @param message
	 *            A message that explains the error.
	 */
	protected void showError(String message) {
		final PopupPanel errorPopup = new PopupPanel(false, true);
		errorPopup.setAnimationEnabled(true);
		VerticalPanel verPanel = new VerticalPanel();
		verPanel.add(new Label("An error occurred!"));
		if (message != null) {
			verPanel.add(new Label(message));
		}
		verPanel.add(new Button("Close", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				errorPopup.hide();
			}
		}));
		errorPopup.add(verPanel);
		errorPopup.center();
	}

	/**
	 * Change the message that is displayed in the load panel below the loading
	 * animation.
	 * 
	 * @param message
	 *            The message to show below the animation. When {@code null},
	 *            the message will be deleted.
	 */
	protected void setLoadPanelMessage(String message) {
		if (message == null) {
			message = "";
		}

		loadPanelMessage.setText(message);
	}

	/**
	 * The model that defines the nodes in the tree.
	 */
	private class CustomTreeModel implements TreeViewModel {

		/**
		 * Number of levels in the tree. Is used to determine when the browser
		 * should be closed.
		 */
		private final static int NUM_LEVELS = 2;

		/** A selection model that is shared along all levels. */
		private final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();

		/** Updater on the highest level. */
		private final ValueUpdater<String> valueGeometryUpdater = new ValueUpdater<String>() {
			@Override
			public void update(String value) {
				as.setGeometry(value);
				lastClickedLevel = 0;
				GWT.log("Update geometry = \"" + as.getGeometryChoice() + "\"!");
			}
		};

		/** Updater on level 1. */
		private final ValueUpdater<String> valueMixerUpdater = new ValueUpdater<String>() {
			@Override
			public void update(String value) {
				as.setMixer(value);
				lastClickedLevel = 1;
				GWT.log("Update mixer = \"" + as.getMixerChoice() + "\"!");
			}
		};

		/** Indicate which level was clicked the last. */
		private int lastClickedLevel = -1;

		private void setUserChoiceValues(String selectedMixer) {
			// TODO: Actually create a different geometry depending on the
			// chosen geometry...
			as.setGegeom(new RectangleGeometry(Window.getClientHeight()
					- topBarHeight, Window.getClientWidth() - menuWidth));
		}

		public CustomTreeModel() {

			selectionModel
					.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
						@Override
						public void onSelectionChange(SelectionChangeEvent event) {
							String selected = selectionModel
									.getSelectedObject();

							if (selected != null
									&& lastClickedLevel == NUM_LEVELS - 1) {
								setUserChoiceValues(selected);

								// "closes" Cellbrowser widget (clears whole
								// rootpanel)
								// TODO: Make decent close-code
								RootPanel.get().clear();

								if (as.getGeometryChoice() != null
										&& as.getMixerChoice() != null) {
									mixingDetails.setText("Geometry: "
											+ as.getGeometryChoice().toString()
											+ ", Mixer: "
											+ as.getMixerChoice().toString());
								} else {// This should never happen. Just to be
										// safe i made this msg so fails are
										// visible
									mixingDetails
											.setText("Geometry and/or Mixer was not selected succesfully");
								}

								RootPanel.get().add(mixingDetails);

								createMixingWidgets();
							}
						}
					});
		}

		/**
		 * Helper method that initialises the widgets for the mixing interface
		 */
		private void createMixingWidgets() {

			// Initialise a listener for when a new step is entered to the
			// protocol
			StepAddedListener l = new StepAddedListener() {
				@Override
				public void onStepAdded(MixingStep step) {
					addStep(step);
				}
			};
			as.getGeometry().addStepAddedListener(l);

			// Initialise the loadPanel
			createLoadPanel();

			// Initialise the toolSelectButton and add to menuPanel
			createToolSelector();
			menuPanel.add(toolSelectButton);

			// Initialise toggleButton and add to
			// menuPanel
			createToggleButton();
			menuPanel.add(toggleColor);

			// Initialise the loadDistButton and add to
			// menuPanel
			createLoadDistButton();
			menuPanel.add(loadDistButton);

			// Initialise the resetDistButton and add to menuPanel
			createResetDistButton();
			menuPanel.add(resetDistButton);

			// Initialise the loadInitDistButton and add it to the menuPanel
			createLoadInitDistButton();
			menuPanel.add(loadInitDistButton);

			// Initialise the saveResultsButton and add it to the menuPanel
			createSaveResultsButton();
			menuPanel.add(saveResultsButton);

			// Initialise the removeSavedResultsButton and add it to the
			// menuPanel
			createRemoveSavedResultsButton();
			menuPanel.add(removeSavedResultsButton);

			// Initialise the saveProtocolButton and add it to the menuPanel
			createSaveDistributionButton();
			menuPanel.add(saveDistributionButton);

			// Initialise a spinner for changing the length of a mixing protocol
			// step and add to menuPanel.
			createStepSizeSpinner();
			menuPanel.add(sizeLabel);
			menuPanel.add(sizeSpinner);

			// Initialise the checkbox that indicates whether a protocol is
			// being defined, or single steps have to be executed and add to
			// menu panel
			createDefineProtocolCheckBox();
			menuPanel.add(defineProtocolCheckBox);

			// Initialise a spinner for #steps
			createNrStepsSpinner();

			// Initialise the resetProtocol button
			createResetProtocolButton();

			// Initialise the saveProtocolButton and add it to the menuPanel
			createSaveProtocolButton();

			createProtocolRepresentationTextArea();

			// Initialise the mixNow button
			createMixNowButton();

			// TODO: Initialise other menu items and add them to menuPanel
			// Add all the protocolwidgets to the menuPanel and hide them
			// initially.
			menuPanel.add(nrStepsLabel);
			menuPanel.add(nrStepsSpinner);
			menuPanel.add(taProtocolRepresentation);
			menuPanel.add(mixNowButton);
			menuPanel.add(saveProtocolButton);
			menuPanel.add(resetProtocolButton);
			toggleProtocolWidgets(false);

			// Add canvas and menuPanel to the panel
			// Make the canvas the entire width of the
			// screen except for the
			// menuWidth
			panel.setWidth("100%");
			panel.add(as.getGeometry().getCanvas());
			panel.add(menuPanel);
			panel.setCellWidth(menuPanel, Integer.toString(menuWidth) + "px");

			// Add panel to RootPanel
			RootPanel.get().add(panel);
		}

		/**
		 * Get the {@link NodeInfo} that provides the children of the specified
		 * value.
		 */
		public <T> NodeInfo<?> getNodeInfo(T value) {
			// When the Tree is being initialised, the last clicked level will
			// be -1,
			// in other cases, we need to load the level after the currently
			// clicked one.
			if (lastClickedLevel < 0) {
				// LEVEL 0. - Geometry
				// We passed null as the root value. Return the Geometries.

				// Create a data provider that contains the list of Geometries.
				ListDataProvider<String> dataProvider = new ListDataProvider<String>(
						Arrays.asList(ServerDataCache.getGeometries()));

				// Return a node info that pairs the data provider and the cell.
				return new DefaultNodeInfo<String>(dataProvider,
						new ClickableTextCell(), selectionModel,
						valueGeometryUpdater);
			} else if (lastClickedLevel == 0) {
				// LEVEL 1 - Mixer (leaf)

				// We want the children of the Geometry. Return the mixers.
				ListDataProvider<String> dataProvider = new ListDataProvider<String>(
						Arrays.asList(ServerDataCache
								.getMixersForGeometry((String) value)));

				// Use the shared selection model.
				return new DefaultNodeInfo<String>(dataProvider,
						new ClickableTextCell(), selectionModel,
						valueMixerUpdater);

			}
			return null;
		}

		/**
		 * Check if the specified value represents a leaf node. Leaf nodes
		 * cannot be opened.
		 */
		// You can define your own definition of leaf-node here.
		public boolean isLeaf(Object value) {
			return lastClickedLevel == NUM_LEVELS - 1;
		}
	}

	/*
	 * Initialises the spinner for the stepSize
	 */
	private void createStepSizeSpinner() {
		// initial initialisation of the spinner
		sizeSpinner = new NumberSpinner(MixingStep.STEP_DEFAULT,
				MixingStep.STEP_UNIT, MixingStep.STEP_MIN, MixingStep.STEP_MAX,
				true);
		as.editStepSize(MixingStep.STEP_DEFAULT);

		// set a listener for the spinner
		sizeSpinner.setSpinnerListener(new NumberSpinnerListener() {

			@Override
			public void onValueChange(double value) {
				// change the current mixing step
				as.editStepSize(value);
			}

		});
	}

	/*
	 * Initialises the reset Distribution Button. When this button is pressed,
	 * the current canvas is reset to all white
	 */
	private void createResetDistButton() {
		resetDistButton = new Button("Reset Dist");
		resetDistButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				as.getGeometry().resetDistribution();
			}

		});
	}

	/*
	 * Initialises the loadPanel
	 */
	private void createLoadPanel() {
		// Initialise the loading panel
		// Add animation image
		loadingPanel = new FlowPanel();
		Image loadImage = new Image("/img/loading_animation.gif");
		loadingPanel.add(loadImage);
		// Add label that may contain explanatory text
		loadPanelMessage = new Label("Loading geometries and mixers...", false);
		loadPanelMessage.getElement().setId(LOADPANEL_MESSAGE_ID);
		loadingPanel.add(loadPanelMessage);
		loadingPanel.getElement().setId(LOADPANEL_ID);
	}

	/*
	 * Toggles the visibility and availability of all the protocol widgets.
	 */
	private void toggleProtocolWidgets(boolean value) {
		// TODO: make a setEnabled for the numberspinner
		nrStepsLabel.setVisible(value);
		nrStepsSpinner.setVisible(value);
		taProtocolRepresentation.setVisible(value);
		mixNowButton.setVisible(value);
		saveProtocolButton.setVisible(value);
		saveProtocolButton.setEnabled(value);
		resetProtocolButton.setVisible(value);
		resetProtocolButton.setEnabled(value);
	}

	/*
	 * Initialises the spinner for the nrSteps.
	 */
	private void createNrStepsSpinner() {
		// Initialise the spinner with the required settings.
		nrStepsSpinner = new NumberSpinner(NRSTEPS_DEFAULT, NRSTEPS_RATE,
				NRSTEPS_MIN, NRSTEPS_MAX, true);
		// Also initialise the initial value in the ApplicationState class.
		as.setNrSteps(NRSTEPS_DEFAULT);

		// The spinner for #steps should update the nrSteps variable whenever
		// the value is changed.
		nrStepsSpinner.setSpinnerListener(new NumberSpinnerListener() {

			@Override
			public void onValueChange(double value) {
				as.setNrSteps(value);
			}
		});
	}

	/*
	 * Initialises the toggleColor button. TODO: Use pictures instead of text on
	 * the button.
	 * 
	 * Note: If the button shows "black" it means the current drawing colour is
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
		toggleColor.setWidth("100px");
	}

	public ArrayList<String> getStoredNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (int i = 0; i < storage.getLength(); i++) {
			names.add(storage.key(i));
		}
		return names;
	}

	/**
	 * Removes an item from local storage.
	 * 
	 * @param key
	 *            Item to remove.
	 */
	public void removeStoredItem(String key) {
		storage.removeItem(key);
	}

	/*
	 * Initialises the protocol representation text area. TODO: this code has to
	 * be removed!
	 */
	private void createProtocolRepresentationTextArea() {
		taProtocolRepresentation.setText("");
		taProtocolRepresentation.setWidth(String.valueOf(menuWidth) + "px");
		taProtocolRepresentation
				.setWidth(String.valueOf(menuWidth - 10) + "px");
		taProtocolRepresentation.setEnabled(false);
	}

	/*
	 * Changes the current drawing colour from black to white, and from white to
	 * black.
	 */
	private void toggleColor() {
		if (toggleColor.isDown()) {
			as.getGeometry().setColor(CssColor.make("white"));
		} else {
			as.getGeometry().setColor(CssColor.make("black"));
		}
	}

	/*
	 * Initialises the tool selector, including buttons to select the shape of
	 * the tool, and the slider to select the size of the tool
	 */
	private void createToolSelector() {
		// --Initialise all elements--------------------------------
		toolSelector = new PopupPanel(true);
		popupPanelPanel = new HorizontalPanel();
		popupPanelMenu = new VerticalPanel();
		squareDrawingTool = new ToggleButton("square", "square");
		circleDrawingTool = new ToggleButton("circle", "circle");

		squareDrawingTool.addClickHandler(new ClickHandler() {

			/*
			 * Select the square drawing tool when this button is clicked
			 */
			@Override
			public void onClick(ClickEvent event) {

				if (!squareDrawingTool.isDown()) {
					squareDrawingTool.setDown(true);
				} else {
					// TODO Change hard-coded 3 to 'size-slider.getValue()' or
					// something
					as.getGeometry().setDrawingTool(new SquareDrawingTool(3));

					circleDrawingTool.setDown(false);
				}
			}
		});
		// Initial drawing tool is square
		squareDrawingTool.setDown(true);

		circleDrawingTool.addClickHandler(new ClickHandler() {

			/*
			 * Select the square drawing tool when this button is clicked
			 */
			@Override
			public void onClick(ClickEvent event) {

				if (!circleDrawingTool.isDown()) {
					circleDrawingTool.setDown(true);
				} else {
					// TODO Change hard-coded 3 to 'size-slider.getValue()' or
					// something
					as.getGeometry().setDrawingTool(new CircleDrawingTool(3));

					squareDrawingTool.setDown(false);
				}
			}
		});

		// -- Add all Drawings Tools below ---------------------
		popupPanelMenu.add(squareDrawingTool);
		popupPanelMenu.add(circleDrawingTool);

		// --TODO: Add DrawingTool Size slider below ----------------
		popupPanelPanel.add(popupPanelMenu);

		// Add everything to the popup panel
		toolSelector.add(popupPanelPanel);

		// Create the button the triggers the popup panel
		// TODO: The text 'Select Tool' should be translated later on
		toolSelectButton = new Button("Select Tool");
		toolSelectButton.addClickHandler(new ClickHandler() {

			/*
			 * Show the popupPanel when this button is clicked
			 */
			@Override
			public void onClick(ClickEvent event) {
				toolSelector
						.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
							public void setPosition(int offsetWidth,
									int offsetHeight) {
								int left = (Window.getClientWidth()
										- offsetWidth - 75);
								int top = 40;
								toolSelector.setPopupPosition(left, top);
							}
						});
			}

		});
	}

	/*
	 * Initialises the resetProtocol button. When pressed, this button sets a
	 * new (and empty) protocol in the application state, and it clear the
	 * protocol representation text area.
	 */
	private void createResetProtocolButton() {
		// TODO: The text 'Reset Protocol' should be translated later on
		resetProtocolButton = new Button("Reset Protocol");
		resetProtocolButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				resetProtocol();
				// When reset protocol is pressed, the save results button is
				// also disabled.
				saveResultsButton.setEnabled(false);
			}

		});
	}

	// /*
	// * Initialises the createSaveResultsButton. When pressed, this button
	// allows
	// * a user to save a mixing run
	// */
	// private void createSaveResultsButton() {
	// // TODO: The text 'Save Results' should be translated later on
	// saveResultsButton = new Button("Save Results");
	// saveResultsPanel = new PopupPanel();
	// saveResultsPanel.setModal(true);
	//
	// // Initially, the save button is disabled; it will become available if
	// // "Mix Now" is pressed.
	// saveResultsButton.setEnabled(true); // TODO: Set to false. True is for
	// // testing only
	//
	// saveResultsVerticalPanel = new VerticalPanel();
	// saveButtonsPanel = new HorizontalPanel();
	// saveNameTextBox = new TextBox();
	// saveNameTextBox.setMaxLength(30);
	//
	// saveResultsPanelButton = new Button("Save");
	// saveResultsPanelButton.setEnabled(false);
	// cancelSaveResultsButton = new Button("Cancel");
	// confirmSavePanel = new PopupPanel();
	// confirmSavePanel.setModal(true);
	//
	// confirmSaveVerticalPanel = new VerticalPanel();
	// saveMessageLabel = new Label();
	// confirmButtonsPanel = new HorizontalPanel();
	// closeSaveResultsButton = new Button();
	// confirmSaveButton = new Button("Overwrite");
	//
	// // Display the first popuppanel when the save button is pressed

	private void createSaveProtocolButton() {
		// TODO: The text 'Save Results' should be translated later on
		saveProtocolButton = new Button("Save Protocol");
		saveProtocolButton.setEnabled(true);
		saveProtocolPanelButton = new Button("Save");
		saveProtocolPanelButton.setEnabled(false);
		confirmSaveProtocolButton = new Button("Overwrite");

		saveProtocolButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				createSavePanel(saveProtocolButton, saveProtocolPanelButton,
						confirmSaveProtocolButton);
				saveResultsPanel
						.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
							public void setPosition(int offsetWidth,
									int offsetHeight) {
								int left = (Window.getClientWidth() - offsetWidth) / 2;
								int top = (Window.getClientHeight() - offsetHeight) / 2;
								saveResultsPanel.setPopupPosition(left, top);
							}
						});
				saveNameTextBox.setFocus(true);
			}

		});

		confirmSaveProtocolButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String name = saveNameTextBox.getText();
				saveProtocol(name);
				saveMessageLabel.setText("Save has been succesful");
				closeSaveButton.setText("OK");
				confirmSaveProtocolButton.removeFromParent();
				confirmSavePanel.center();
			}
		});

		saveProtocolPanelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String name = saveNameTextBox.getText();
				if (isNameInUse(name)) {
					saveMessageLabel.setText("This name is already in use. "
							+ "Choose whether to overwrite existing file "
							+ "or to cancel.");
					closeSaveButton.setText("Cancel");

					confirmButtonsPanel.remove(closeSaveButton);
					confirmButtonsPanel.add(confirmSaveProtocolButton);
					confirmButtonsPanel.add(closeSaveButton);
				} else {
					saveProtocol(name);
					saveMessageLabel.setText("Save has been succesful");
					closeSaveButton.setText("OK");
					if (confirmSaveProtocolButton.isAttached()) {
						confirmButtonsPanel.remove(confirmSaveProtocolButton);
					}
				}
				confirmSavePanel
						.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
							public void setPosition(int offsetWidth,
									int offsetHeight) {
								int left = (Window.getClientWidth() - offsetWidth) / 2;
								int top = (Window.getClientHeight() - offsetHeight) / 2;
								confirmSavePanel.setPopupPosition(left, top);
							}
						});
				saveResultsPanel.hide();
			}
		});

		createSavePanel(saveProtocolButton, saveProtocolPanelButton,
				confirmSaveProtocolButton);
	}

	/*
	 * Initialises the createSaveResultsButton. When pressed, this button allows
	 * a user to save a mixing run
	 */
	private void createSaveResultsButton() {
		// TODO: The text 'Save Results' should be translated later on
		saveResultsButton = new Button("Save Results");
		saveResultsButton.setEnabled(true);
		saveResultsPanelButton = new Button("Save");
		saveResultsPanelButton.setEnabled(false);
		confirmSaveResultsButton = new Button("Overwrite");

		saveResultsButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				createSavePanel(saveResultsButton, saveResultsPanelButton,
						confirmSaveResultsButton);
				saveResultsPanel
						.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
							public void setPosition(int offsetWidth,
									int offsetHeight) {
								int left = (Window.getClientWidth() - offsetWidth) / 2;
								int top = (Window.getClientHeight() - offsetHeight) / 2;
								saveResultsPanel.setPopupPosition(left, top);
							}
						});
				saveNameTextBox.setFocus(true);
			}

		});

		confirmSaveResultsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String name = saveNameTextBox.getText();
				saveState(name);
				saveMessageLabel.setText("Save has been succesful");
				closeSaveButton.setText("OK");
				confirmSaveResultsButton.removeFromParent();
				confirmSavePanel.center();
			}
		});

		saveResultsPanelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String name = saveNameTextBox.getText();
				if (isNameInUse(name)) {
					saveMessageLabel.setText("This name is already in use. "
							+ "Choose whether to overwrite existing file "
							+ "or to cancel.");
					closeSaveButton.setText("Cancel");

					confirmButtonsPanel.remove(closeSaveButton);
					confirmButtonsPanel.add(confirmSaveResultsButton);
					confirmButtonsPanel.add(closeSaveButton);
				} else {
					saveState(name);
					saveMessageLabel.setText("Save has been succesful");
					closeSaveButton.setText("OK");
					if (confirmSaveResultsButton.isAttached()) {
						confirmButtonsPanel.remove(confirmSaveResultsButton);
					}
				}
				confirmSavePanel
						.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
							public void setPosition(int offsetWidth,
									int offsetHeight) {
								int left = (Window.getClientWidth() - offsetWidth) / 2;
								int top = (Window.getClientHeight() - offsetHeight) / 2;
								confirmSavePanel.setPopupPosition(left, top);
							}
						});
				saveResultsPanel.hide();
			}
		});

	}

	/*
	 * Initialises the createSaveButton. When pressed, this button allows a user
	 * to save a mixing run
	 */
	private void createSaveDistributionButton() {
		// TODO: The text 'Save Distribution' should be translated later on
		saveDistributionButton = new Button("Save Distribution");
		saveDistributionButton.setEnabled(true);
		saveDistributionPanelButton = new Button("Save");
		saveDistributionPanelButton.setEnabled(false);
		confirmSaveDistributionButton = new Button("Overwrite");

		saveDistributionButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				createSavePanel(saveDistributionButton,
						saveDistributionPanelButton,
						confirmSaveDistributionButton);
				saveResultsPanel
						.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
							public void setPosition(int offsetWidth,
									int offsetHeight) {
								int left = (Window.getClientWidth() - offsetWidth) / 2;
								int top = (Window.getClientHeight() - offsetHeight) / 2;
								saveResultsPanel.setPopupPosition(left, top);
							}
						});
				saveNameTextBox.setFocus(true);
			}

		});

		confirmSaveDistributionButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String name = saveNameTextBox.getText();
				saveDistribution(name);
				saveMessageLabel.setText("Save has been succesful");
				closeSaveButton.setText("OK");
				confirmSaveDistributionButton.removeFromParent();
				confirmSavePanel.center();
			}
		});

		saveDistributionPanelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String name = saveNameTextBox.getText();
				if (isNameInUse(name)) {
					saveMessageLabel.setText("This name is already in use. "
							+ "Choose whether to overwrite existing file "
							+ "or to cancel.");
					closeSaveButton.setText("Cancel");

					confirmButtonsPanel.remove(closeSaveButton);
					confirmButtonsPanel.add(confirmSaveDistributionButton);
					confirmButtonsPanel.add(closeSaveButton);
				} else {
					saveDistribution(name);
					saveMessageLabel.setText("Save has been succesful");
					closeSaveButton.setText("OK");
					if (confirmSaveDistributionButton.isAttached()) {
						confirmButtonsPanel
								.remove(confirmSaveDistributionButton);
					}
				}
				confirmSavePanel
						.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
							public void setPosition(int offsetWidth,
									int offsetHeight) {
								int left = (Window.getClientWidth() - offsetWidth) / 2;
								int top = (Window.getClientHeight() - offsetHeight) / 2;
								confirmSavePanel.setPopupPosition(left, top);
							}
						});
				saveResultsPanel.hide();
			}

		});

	}

	private void saveDistribution(String name) {
		// TODO Auto-generated method stub

	}

	public void createSavePanel(Button save1, final Button save2,
			final Button overwrite) {

		saveResultsPanel = new PopupPanel();
		saveResultsPanel.setModal(true);

		// Initially, the save button is disabled; it will become available if
		// "Mix Now" is pressed.

		saveResultsVerticalPanel = new VerticalPanel();
		saveButtonsPanel = new HorizontalPanel();
		saveNameTextBox = new TextBox();
		saveNameTextBox.setMaxLength(30);

		cancelSaveResultsButton = new Button("Cancel");
		confirmSavePanel = new PopupPanel();
		confirmSavePanel.setModal(true);

		confirmSaveVerticalPanel = new VerticalPanel();
		saveMessageLabel = new Label();
		confirmButtonsPanel = new HorizontalPanel();
		closeSaveButton = new Button();

		// Display the first popuppanel when the save button is pressed

		// add all components to first popuppanel
		saveResultsPanel.add(saveResultsVerticalPanel);
		saveResultsVerticalPanel.add(saveNameTextBox);
		saveResultsVerticalPanel.add(saveButtonsPanel);
		saveButtonsPanel.add(save2);
		saveButtonsPanel.add(cancelSaveResultsButton);

		// add all components to second popup panel
		confirmSavePanel.add(confirmSaveVerticalPanel);
		confirmSaveVerticalPanel.add(saveMessageLabel);
		confirmSaveVerticalPanel.add(confirmButtonsPanel);
		confirmButtonsPanel.add(closeSaveButton);

		// display the second popup panel when the second save button is pressed

		// Hide the first popup panel when the first cancel button is pressed
		cancelSaveResultsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				saveResultsPanel.hide();
				saveNameTextBox.setText("");
				save2.setEnabled(false);
			}
		});

		// Determine whether user input is valid. Enable/disable the save
		// button. Execute save when ENTER is pressed.
		saveNameTextBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				String text = saveNameTextBox.getText();
				String inputCharacter = Character.toString(event.getCharCode());
				int textlength = text.length();
				if (inputCharacter
						.matches("[~`!@#$%^&*()+={}\\[\\]:;\"|\'\\\\<>?,./\\s]")) {
					saveNameTextBox.cancelKey();
				}
				if (inputCharacter.matches("[A-Za-z0-9]")) {
					textlength++;
				}
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE) {
					textlength--;
				}
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					save2.click();
				}
				save2.setEnabled(textlength > 0);
			}
		});

		// Hide both popup panels if the OK button was pressed. Hide only the
		// second panel if the cancel button was pressed.
		closeSaveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				confirmSavePanel.hide();
				if (!closeSaveButton.getText().equals("OK")) {
					confirmSavePanel.remove(overwrite);
					saveResultsPanel.show();
					saveNameTextBox.setSelectionRange(0, saveNameTextBox
							.getText().length());
					saveNameTextBox.setFocus(true);
				} else {
					saveNameTextBox.setText("");
					save2.setEnabled(false);
				}
			}
		});
	}

	/*
	 * Initialises the loadInitDistButton. When pressed, this button allows a
	 * user to load an initial distribution to the canvas.
	 */
	private void createLoadInitDistButton() {
		loadInitDistButton = new Button("Load Initial Distribution");
		loadVerticalPanel = new VerticalPanel();
		loadPanel = new PopupPanel();
		loadPanel.setModal(true);
		loadPanel.add(loadVerticalPanel);
		closeLoadButton = new Button("Close");

		closeLoadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadPanel.removeFromParent();
			}
		});

		// loadFlexTable = new FlexTable();

		loadInitDistButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				// TODO: un-comment when StorageManager is implemented.
				// Get all initial distributions for current geometry
				// List<String> geometryDistributions = StorageManager.INSTANCE
				// .getDistributions(as.getGeometryChoice());

				// TODO: Replace with geometryDistributions after StorageManager
				// is implemented.
				final List<String> NAMES = Arrays.asList("Sunday", "Monday",
						"Tuesday", "Wednesday", "Thursday", "Friday",
						"Saturday");

				// Create a cell to render each value.
				TextCell textCell = new TextCell();

				// Create a CellList that uses the cell.
				CellList<String> cellList = new CellList<String>(textCell);
				cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

				// Add a selection model to handle user selection.
				final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
				cellList.setSelectionModel(selectionModel);
				selectionModel
						.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
							public void onSelectionChange(
									SelectionChangeEvent event) {
								String selected = selectionModel
										.getSelectedObject();

								// TODO: un-comment after StorageManager
								// is implemented.
								// get the selected initial distribution, and
								// set it in the AS
								// as.setInitialDistribution(StorageManager.INSTANCE.getDistributions(selected));

								// TODO: Remove this substitute functionality
								Window.alert("Look, it works! You selected "
										+ selected);
								loadPanel.removeFromParent();
							}
						});

				// Set the total row count. This isn't strictly necessary, but
				// it affects
				// paging calculations, so its good habit to keep the row count
				// up to date.
				cellList.setRowCount(NAMES.size(), true);

				// Push the data into the widget.
				cellList.setRowData(0, NAMES);

				loadVerticalPanel.add(cellList);
				loadVerticalPanel.add(closeLoadButton);
				loadPanel.center();
			}
		});

	}

	/*
	 * Initialises the removeSavedResultsButton. When pressed, this button
	 * allows a user to remove a previously saved mixing run
	 */
	private void createRemoveSavedResultsButton() {
		// TODO: The text 'Remove Saved Results' should be translated later on
		removeSavedResultsButton = new Button("Remove Saved Results");
		removeResultsVerticalPanel = new VerticalPanel();
		removeResultsPanel = new PopupPanel();
		removeResultsPanel.setModal(true);
		removeResultsPanel.add(removeResultsVerticalPanel);

		closeResultsButton = new Button("Close");
		resultsFlexTable = new FlexTable();

		removeSavedResultsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				resultsFlexTable.removeFromParent();
				closeResultsButton.removeFromParent();
				resultsFlexTable = new FlexTable();
				removeResultsVerticalPanel.add(resultsFlexTable);
				removeResultsVerticalPanel.add(closeResultsButton);

				resultsFlexTable.setText(0, 0, "File name");
				resultsFlexTable.setText(0, 1, "Remove");

				resultsFlexTable.getRowFormatter().addStyleName(0,
						"removeListHeader");
				resultsFlexTable.addStyleName("removeList");

				final ArrayList<String> names = getStoredNames();
				for (int i = 0; i < names.size(); i++) {
					final int row = i + 1;
					final String name = names.get(i);
					resultsFlexTable.setText(row, 0, name);
					Button removeStockButton = new Button("x");
					removeStockButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							int removedIndex = names.indexOf(name);
							names.remove(removedIndex);
							removeStoredItem(name);
							resultsFlexTable.removeRow(removedIndex + 1);
						}
					});
					resultsFlexTable.setWidget(row, 1, removeStockButton);
				}

				removeResultsPanel
						.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
							public void setPosition(int offsetWidth,
									int offsetHeight) {
								int left = (Window.getClientWidth() - offsetWidth) / 2;
								int top = (Window.getClientHeight() - offsetHeight) / 2;
								removeResultsPanel.setPopupPosition(left, top);
							}
						});
			}
		});

		closeResultsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				removeResultsPanel.hide();
			}
		});
	}

	/**
	 * Updates the protocol label to show the textual representation of
	 * {@code step} and adds this to the existing steps in the protocol.
	 * 
	 * @param step
	 *            The new {@code Step} of which the textual representation
	 *            should be added.
	 */
	private void updateProtocolLabel(MixingStep step) {
		String oldProtocol = taProtocolRepresentation.getText();
		String stepString;

		if (step.isTopWall() && step.movesForward()) {
			stepString = "T";
		} else if (step.isTopWall() && !step.movesForward()) {
			stepString = "-T";
		} else if (!step.isTopWall() && step.movesForward()) {
			stepString = "B";
		} else { // (!step.isTopWall() && !step.movesForward()) {
			stepString = "-B";
		}

		stepString += "[" + step.getStepSize() + "]";

		taProtocolRepresentation.setText(oldProtocol + stepString + " ");
	}

	// --Methods for testing purposes only---------------------------------
	/*
	 * Initialises the Load Distribution button. This button only exists for
	 * testing purposes. When it is pressed, the distribution of the geometry is
	 * set to a colour bar from black to white, from left to right. This
	 * distribution is then drawn on the canvas, to demonstrate we can load an
	 * arbitrary distribution, with 256 gray scale colours. TODO: Can (and
	 * should) be removed when the communication is functional
	 */
	private void createLoadDistButton() {
		loadDistButton = new Button("Load Dist");
		loadDistButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// RectangleDistribution dist = new RectangleDistribution();
				double[] dist = new double[96000];
				for (int x = 0; x < 400; x++) {
					for (int y = 0; y < 240; y++) {
						// dist.setValue(x, y, (double) x / 400);
						dist[x + 400 * (239 - y)] = (double) x / 400;
					}
				}
				as.getGeometry().drawDistribution(dist);
			}
		});
	}

	/**
	 * <p>
	 * Show or hide an overlay with a loading animation in the centre. Making
	 * this panel visible will make it impossible for the user to give input.
	 * </p>
	 * 
	 * <p>
	 * When hiding the panel, the message will also be reset. Change it with
	 * {@link #setLoadPanelMessage}.
	 * </p>
	 * 
	 * @param visible
	 *            If the panel should be hidden or shown.
	 */
	protected void setLoadPanelVisible(boolean visible) {
		if (visible) {
			if (RootPanel.get(LOADPANEL_ID) == null) {
				RootPanel.get().add(loadingPanel);
			}
		} else {
			if (RootPanel.get(LOADPANEL_ID) != null) {
				loadingPanel.removeFromParent();
				setLoadPanelMessage(null);
			}
		}
	}

	/*
	 * Initialises the define Protocol checkbox. When this button is pressed,
	 * the current protocol is reset, and the protocol widgets are shown/hidden.
	 */
	private void createDefineProtocolCheckBox() {
		// TODO: The text 'Define Protocol' should be translated later on
		defineProtocolCheckBox = new CheckBox("Define Protocol");
		defineProtocolCheckBox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (defineProtocolCheckBox.getValue()) {
					toggleProtocolWidgets(true);
				} else {
					resetProtocol();
					toggleProtocolWidgets(false);
				}
			}
		});
	}

	/*
	 * resets the current protocol and the protocol widgets
	 */
	private void resetProtocol() {
		as.setProtocol(new MixingProtocol());
		taProtocolRepresentation.setText("");
		as.setNrSteps(NRSTEPS_DEFAULT);
		nrStepsSpinner.setValue(NRSTEPS_DEFAULT);
		mixNowButton.setEnabled(false);
	}

	/*
	 * Initialises the mixNow button. When pressed, the current protocol is
	 * executed. TODO: When this button is disabled, hovering it should not make
	 * it appear 'active'
	 */
	private void createMixNowButton() {
		// TODO: The text 'Mix Now' should be translated later on
		mixNowButton = new Button("Mix Now");
		mixNowButton.setEnabled(false);
		mixNowButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				executeMixingRun(as.getProtocol());
			}

		});
	}

	/**
	 * If the {@code Define Protocol} checkbox is ticked, this method adds a new
	 * {@code MixingStep} to the mixing protocol, and updates the text area
	 * {@code taProtocolRepresentation} accordingly.
	 * 
	 * @param step
	 *            The {@code MixingStep} to be added.
	 */
	private void addStep(MixingStep step) {
		if (defineProtocolCheckBox.getValue()) {
			step.setStepSize(as.getStepSize());
			as.addMixingStep(step);

			updateProtocolLabel(step);
			mixNowButton.setEnabled(true);
		} else {
			MixingProtocol protocol = new MixingProtocol();
			step.setStepSize(as.getStepSize());
			protocol.addStep(step);
			executeMixingRun(protocol);
		}
	}

	/**
	 * Saves the initial distribution. Sends all current information about the
	 * protocol and the distribution to the server. Displays the results on
	 * screen.
	 */
	private void executeMixingRun(MixingProtocol protocol) {

		as.setInitialDistribution(as.getGeometry().getDistribution());

		Simulation simulation = new Simulation(as.getMixChoice(), protocol,
				as.getInitialDistribution(), as.getNrSteps(), false);

		TimeoutRpcRequestBuilder timeoutRpcRequestBuilder = new TimeoutRpcRequestBuilder(
				10000);
		SimulatorServiceAsync service = GWT.create(SimulatorService.class);
		((ServiceDefTarget) service)
				.setRpcRequestBuilder(timeoutRpcRequestBuilder);
		AsyncCallback<SimulationResult> callback = new AsyncCallback<SimulationResult>() {
			@Override
			public void onSuccess(SimulationResult result) {
				as.getGeometry().drawDistribution(
						result.getConcentrationVectors()[0]);
				setLoadPanelVisible(false);
			}

			@Override
			public void onFailure(Throwable caught) {
				setLoadPanelVisible(false);
				if (caught instanceof RequestTimeoutException) {
					showError("The simulation server did not respond in"
							+ " time. Try again later");
				} else {
					showError(caught.getMessage());
				}
			}
		};

		service.simulate(simulation, callback);
		setLoadPanelMessage("Running the simulation. Please wait...");
		setLoadPanelVisible(true);

		saveResultsButton.setEnabled(true);
	}

}
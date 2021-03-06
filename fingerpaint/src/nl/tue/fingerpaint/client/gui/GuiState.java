package nl.tue.fingerpaint.client.gui;

import nl.tue.fingerpaint.client.gui.buttons.BackMenuButton;
import nl.tue.fingerpaint.client.gui.buttons.BackStopDefiningProtocolButton;
import nl.tue.fingerpaint.client.gui.buttons.CancelCompareButton;
import nl.tue.fingerpaint.client.gui.buttons.CancelSaveResultsButton;
import nl.tue.fingerpaint.client.gui.buttons.CircleDrawingToolToggleButton;
import nl.tue.fingerpaint.client.gui.buttons.CloseCompareButton;
import nl.tue.fingerpaint.client.gui.buttons.CloseLoadButton;
import nl.tue.fingerpaint.client.gui.buttons.CloseResultsButton;
import nl.tue.fingerpaint.client.gui.buttons.CloseSaveButton;
import nl.tue.fingerpaint.client.gui.buttons.CloseSingleGraphViewButton;
import nl.tue.fingerpaint.client.gui.buttons.CompareButton;
import nl.tue.fingerpaint.client.gui.buttons.ComparePerformanceButton;
import nl.tue.fingerpaint.client.gui.buttons.DistributionsButton;
import nl.tue.fingerpaint.client.gui.buttons.ExportDistributionButton;
import nl.tue.fingerpaint.client.gui.buttons.ExportMultipleGraphsButton;
import nl.tue.fingerpaint.client.gui.buttons.ExportSingleGraphButton;
import nl.tue.fingerpaint.client.gui.buttons.LoadInitDistButton;
import nl.tue.fingerpaint.client.gui.buttons.LoadProtocolButton;
import nl.tue.fingerpaint.client.gui.buttons.LoadResultsButton;
import nl.tue.fingerpaint.client.gui.buttons.MenuToggleButton;
import nl.tue.fingerpaint.client.gui.buttons.MixNowButton;
import nl.tue.fingerpaint.client.gui.buttons.NewCompareButton;
import nl.tue.fingerpaint.client.gui.buttons.OverwriteSaveButton;
import nl.tue.fingerpaint.client.gui.buttons.ProtocolsButton;
import nl.tue.fingerpaint.client.gui.buttons.RemoveInitDistButton;
import nl.tue.fingerpaint.client.gui.buttons.RemoveSavedProtButton;
import nl.tue.fingerpaint.client.gui.buttons.RemoveSavedResultsButton;
import nl.tue.fingerpaint.client.gui.buttons.ResetDistButton;
import nl.tue.fingerpaint.client.gui.buttons.ResetProtocolButton;
import nl.tue.fingerpaint.client.gui.buttons.ResultsButton;
import nl.tue.fingerpaint.client.gui.buttons.SaveDistributionButton;
import nl.tue.fingerpaint.client.gui.buttons.SaveItemPanelButton;
import nl.tue.fingerpaint.client.gui.buttons.SaveProtocolButton;
import nl.tue.fingerpaint.client.gui.buttons.SaveResultsButton;
import nl.tue.fingerpaint.client.gui.buttons.SquareDrawingToolToggleButton;
import nl.tue.fingerpaint.client.gui.buttons.ToggleColourButton;
import nl.tue.fingerpaint.client.gui.buttons.ToggleDefineProtocol;
import nl.tue.fingerpaint.client.gui.buttons.ToolSelectButton;
import nl.tue.fingerpaint.client.gui.buttons.ViewSingleGraphButton;
import nl.tue.fingerpaint.client.gui.celllists.CompareSelectPopupCellList;
import nl.tue.fingerpaint.client.gui.celllists.LoadInitDistCellList;
import nl.tue.fingerpaint.client.gui.celllists.LoadProtocolCellList;
import nl.tue.fingerpaint.client.gui.celllists.LoadResultsCellList;
import nl.tue.fingerpaint.client.gui.flextables.InitDistFlexTable;
import nl.tue.fingerpaint.client.gui.flextables.ProtocolFlexTable;
import nl.tue.fingerpaint.client.gui.flextables.ResultsFlexTable;
import nl.tue.fingerpaint.client.gui.labels.DistributionsLabel;
import nl.tue.fingerpaint.client.gui.labels.DrawingToolLabel;
import nl.tue.fingerpaint.client.gui.labels.NoFilesFoundLabel;
import nl.tue.fingerpaint.client.gui.labels.ProtocolLabel;
import nl.tue.fingerpaint.client.gui.labels.ProtocolRepresentationLabel;
import nl.tue.fingerpaint.client.gui.labels.ProtocolsLabel;
import nl.tue.fingerpaint.client.gui.labels.ResultsLabel;
import nl.tue.fingerpaint.client.gui.labels.SaveMessageLabel;
import nl.tue.fingerpaint.client.gui.panels.ComparePopupPanel;
import nl.tue.fingerpaint.client.gui.panels.CompareSelectPopupPanel;
import nl.tue.fingerpaint.client.gui.panels.LoadPopupPanel;
import nl.tue.fingerpaint.client.gui.panels.LoadVerticalPanel;
import nl.tue.fingerpaint.client.gui.panels.OverwriteSavePopupPanel;
import nl.tue.fingerpaint.client.gui.panels.RemoveResultsPopupPanel;
import nl.tue.fingerpaint.client.gui.panels.RemoveResultsVerticalPanel;
import nl.tue.fingerpaint.client.gui.panels.SaveItemPopupPanel;
import nl.tue.fingerpaint.client.gui.panels.ViewSingleGraphPopupPanel;
import nl.tue.fingerpaint.client.gui.spinners.CursorSizeSpinner;
import nl.tue.fingerpaint.client.gui.spinners.NrStepsSpinner;
import nl.tue.fingerpaint.client.gui.spinners.StepSizeSpinner;
import nl.tue.fingerpaint.client.gui.textboxes.SaveNameTextBox;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * <p>
 * A class that contains references to all the GUI elements used in the
 * Fingerpaint application. This class is used as an "element manager", for easy
 * element referencing.
 * </p>
 * 
 * <p>
 * All widgets that can be initialised, must be initialised in this class.
 * </p>
 * 
 * @author Group Fingerpaint
 */
public class GuiState {
	// --- PUBLIC GLOBALS -----------------------------------------------------
	/** The ID for the loading panel. */
	public static final String LOADINGPANEL_ID = "loading-overlay";

	/** The ID for the message to be displayed in the loading panel. */
	public static final String LOADINGPANEL_MESSAGE_ID = "loading-overlay-message";

	/**
	 * Stores how long in milliseconds a SAVE_SUCCESS_MESSAGE should be shown in
	 * a NotificationPanel.
	 */
	public static final int DEFAULT_TIMEOUT = 2000;

	/**
	 * Timeout for the somewhat longer message that appears when a geometry
	 * is unsupported.
	 */
	public static final int UNSUPPORTED_GEOM_TIMEOUT = 5000;

	// --- LOADING APPLICATION WIDGETS ----------------------------------------
	/**
	 * Panel that covers the entire application and blocks the user from
	 * accessing other features.
	 */
	public static FlowPanel loadingPanel = new FlowPanel();

	/**
	 * The message to be shown below the loading animation of the loading panel.
	 * This may be empty, but can be used to inform the user about <i>what</i>
	 * is loading exactly.
	 */
	public static Label loadingPanelMessage = new Label();

	// --- MENU WIDGETS -------------------------------------------------------
	/** Vertical panel to contain all menu items. */
	public static VerticalPanel[] menuPanels = new VerticalPanel[] {
		new VerticalPanel(), // main menu
		new VerticalPanel(), // submenu level 1
		new VerticalPanel()  // submenu level 2
	};
	
	/** Wrapper for the main menu ({@link #menuPanels}[0]), used in animation of hiding menu. */
	public static FlowPanel menuPanelOuterWrapper = new FlowPanel();
	
	/** Wrapper for the {@link #menuPanelOuterWrapper}, used in animation of submenus. */
	public static FlowPanel menuPanelInnerWrapper = new FlowPanel();

	/** Button to toggle whether the menu is visible. */
	public static MenuToggleButton menuToggleButton = new MenuToggleButton(
			menuPanelOuterWrapper);
	
	/** Button that is used in the first menu level to go up one level. */
	public static BackMenuButton backMenu1Button = new BackMenuButton("backMenu1Button");
	
	/** Button that is used in the second menu level to go up one level. */
	public static BackMenuButton backMenu2Button = new BackMenuButton("backMenu2Button");

	// --- DRAWING TOOL WIDGETS -----------------------------------------------
	/**
	 * Label to be displayed above the drawing tool related submenu.
	 */
	public static DrawingToolLabel drawingToolLabel = new DrawingToolLabel();
	
	/** Numberspinner to change the size of the drawing tool. */
	public static CursorSizeSpinner cursorSizeSpinner;

	/** Button to toggle between black and white drawing colour. */
	public static ToggleColourButton toggleColour;
	
	/** Button to toggle between black and white drawing colour in the tool submenu. */
	public static ToggleColourButton toolMenuToggleColour;

	/** Button to change the shape of the selected drawing tool. */
	public static ToolSelectButton toolSelectButton = new ToolSelectButton();

	/** Button to select the square-shaped drawing tool. */
	public static SquareDrawingToolToggleButton squareDrawingTool;

	/** Button to select the circle-shaped drawing tool. */
	public static CircleDrawingToolToggleButton circleDrawingTool;

	// --- INITIAL DISTRIBUTION WIDGETS ---------------------------------------
	/**
	 * Label to be displayed above the distributions related submenu.
	 */
	public static DistributionsLabel distributionsLabel = new DistributionsLabel();
	
	/**
	 * CellList used to select and load one previously saved concentration
	 * distribution.
	 */
	public static LoadInitDistCellList loadInitDistCellList;

	/** Button to enter the submenu with distribution related actions. */
	public static DistributionsButton distributionsButton = new DistributionsButton();
	
	/** Button to save an initial concentration distribution. */
	public static SaveDistributionButton saveDistributionButton;

	/** Button to load an initial concentration distribution. */
	public static LoadInitDistButton loadInitDistButton;

	/** Button to reset the current distribution to completely white. */
	public static ResetDistButton resetDistButton;

	// -- RESULTS WIDGETS -----------------------------------------
	/**
	 * Label to be displayed above the results related submenu.
	 */
	public static ResultsLabel resultsLabel = new ResultsLabel();
	
	/** Button to enter the submenu with results related actions. */
	public static ResultsButton resultsButton = new ResultsButton();
	
	/** Button to save the current mixing result. */
	public static SaveResultsButton saveResultsButton;

	/** Button to load a previously saved mixing result */
	public static LoadResultsButton loadResultsButton = new LoadResultsButton();
	
	/** CellList to list the to be loaded results */
	public static LoadResultsCellList LoadResultsCellList;
	
	/** Pop-up panel to handle the removal of results. */
	public static RemoveResultsPopupPanel removeResultsPanel = new RemoveResultsPopupPanel();

	/** Vertical panel to hold the flextable and close button. */
	public static RemoveResultsVerticalPanel removeResultsVerticalPanel = new RemoveResultsVerticalPanel();

	/** Flextable to hold all the result entries. */
	public static ResultsFlexTable resultsFlexTable = new ResultsFlexTable();
	
	/** Button to remove previously saved mixing results. */
	public static RemoveSavedResultsButton removeSavedResultsButton = new RemoveSavedResultsButton();

	/** Button to close the remove results pop-up panel. */
	public static CloseResultsButton closeResultsButton = new CloseResultsButton();

	// --- REMOVE SAVED PROTOCOL WIDGETS---------------------
	/** button to remove previously saved protocols */
	public static RemoveSavedProtButton removeSavedProtButton;
	// Note: This button makes use of the same panels and flextable as
	// removeSavedResultsButton
	
	/** Flextable to hold all the protocol entries. */
	public static ProtocolFlexTable protocolFlexTable = new ProtocolFlexTable();

	// --- REMOVE SAVED INITIAL DISTRIBUTION WIDGETS---------------------
	/** button to remove previously saved protocols */
	public static RemoveInitDistButton removeInitDistButton;
	// Note: This button makes use of the same panels and flextable as
	// removeSavedResultsButton
	
	/** Flextable to hold all the protocol entries. */
	public static InitDistFlexTable initDistFlexTable = new InitDistFlexTable();
	
	// --- MIXING PROTOCOL WIDGETS --------------------------------------------
	/**
	 * Label to be displayed above the protocols related submenu.
	 */
	public static ProtocolsLabel protocolsLabel = new ProtocolsLabel();
	
	/**
	 * Button that can be used to access the protocols related submenu.
	 */
	public static ProtocolsButton protocolsButton = new ProtocolsButton();
	
	/**
	 * CellList that can be used to load a previously saved mixing protocol.
	 */
	public static LoadProtocolCellList loadProtocolCellList;

	/**
	 * The numberspinner to define how many times the mixing protocol is
	 * executed that is located in the main menu.
	 */
	public static NrStepsSpinner nrStepsSpinner;

	/** Button to save the current mixing protocol. */
	public static SaveProtocolButton saveProtocolButton;

	/** Button to load a mixing protocol. */
	public static LoadProtocolButton loadProtocolButton;

	/** Button that resets the current mixing protocol when pressed. */
	public static ResetProtocolButton resetProtocolButton;

	/**
	 * Button that executes the current mixing run (initial distribution and
	 * mixing protocol) when pressed.
	 */
	public static MixNowButton mixNowButton;

	/**
	 * Label to be displayed above the {@link #nrStepsSpinner}, to explain its
	 * purpose.
	 */
	public static Label nrStepsLabel = new Label(
			FingerpaintConstants.INSTANCE.lblNrSteps());

	/**
	 * Label that shows the textual representation of the current mixing
	 * protocol.
	 */
	public static ProtocolRepresentationLabel labelProtocolRepresentation = new ProtocolRepresentationLabel();

	/**
	 * Label to be displayed above the protocol-related buttons, to explain
	 * their purpose.
	 */
	public static ProtocolLabel labelProtocolLabel = new ProtocolLabel();

	/**
	 * Toggle button that needs to be clicked to define a protocol. If it isn't
	 * in its default state, steps 9wall movements) are executed directly.
	 */
	public static ToggleDefineProtocol toggleDefineProtocol;
	
	/**
	 * Button that is used in the submenu with protocol related stuff to go
	 * back to the main menu.
	 */
	public static BackStopDefiningProtocolButton backStopDefiningProtocol;

	// --- SAVE POP-UP MENU WIDGETS -------------------------------------------
	/** Pop-up panel to handle the saving of the current results. */
	public static SaveItemPopupPanel saveItemPanel = new SaveItemPopupPanel();

	/**
	 * Horizontal panel to hold the Save and Cancel buttons in the pop-up panel.
	 */
	public static HorizontalPanel saveButtonsPanel = new HorizontalPanel();

	/**
	 * Vertical panel to hold the textbox and the save button in the save pop-up
	 * panel.
	 */
	public static VerticalPanel saveItemVerticalPanel = new VerticalPanel();

	/**
	 * Button to save the item under the specified name in the
	 * {@link #saveNameTextBox}.
	 */
	public static SaveItemPanelButton saveItemPanelButton;

	/** Cancel button inside the save pop-up menu. */
	public static CancelSaveResultsButton cancelSaveResultsButton = new CancelSaveResultsButton();

	/** Ok / Cancel button to close the save results or overwrite pop-up panel. */
	public static CloseSaveButton closeSaveButton = new CloseSaveButton();

	/** Textbox to input the name in to name the file. */
	public static SaveNameTextBox saveNameTextBox = new SaveNameTextBox();

	/** Label to indicate that the chosen name is already in use. */
	public static SaveMessageLabel saveMessageLabel = new SaveMessageLabel();

	// --- OVERWRITE POP-UP MENU WIDGETS --------------------------------------
	/**
	 * Pop-up panel that appears after the Save button in the save pop-up panel
	 * has been pressed.
	 */
	public static OverwriteSavePopupPanel overwriteSavePanel = new OverwriteSavePopupPanel();

	/**
	 * Horizontal panel to hold the OK or Overwrite/Cancel button(s) in the
	 * confirm save pop-up panel.
	 */
	public static HorizontalPanel overwriteButtonsPanel = new HorizontalPanel();

	/**
	 * Vertical panel to hold the save message and the OK/Overwrite button in
	 * the confirm save pop-up panel.
	 */
	public static VerticalPanel overwriteSaveVerticalPanel = new VerticalPanel();

	/** Button to overwrite the item that is currently being saved. */
	public static OverwriteSaveButton overwriteSaveButton;

	// --- LOAD POP-UP MENU WIDGETS -------------------------------------------
	/**
	 * Vertical panel to hold the textbox and the cancel button in the load
	 * pop-up panel.
	 */
	public static LoadVerticalPanel loadVerticalPanel = new LoadVerticalPanel();
	
	/** Scroll panel to hold the lists in the the loadVerticalPanel and removeResultsVerticalPanel */
	public static ScrollPanel listScrollPanel = new ScrollPanel();

	/** Pop-up panel to handle the loading of previously saved items. */
	public static LoadPopupPanel loadPanel = new LoadPopupPanel();
	
	/** Label that can be used to indicate that some action is taking place. */
	public static Label loadLabel = new Label(FingerpaintConstants.INSTANCE.lblLoad());

	/** Button to close the load pop-up menu. */
	public static CloseLoadButton closeLoadButton = new CloseLoadButton();
	
	/** Label to indicate there are no saves found for the selected loading option */
	public static NoFilesFoundLabel noFilesFoundLabel = new NoFilesFoundLabel();

	// --- STEP SIZE WIDGETS --------------------------------------------------
	/**
	 * The numberspinner to define the step size of a single wall movement,
	 * located in the main menu.
	 */
	public static StepSizeSpinner sizeSpinner;
	
	/**
	 * The numberspinner to define the step size of a single wall movement,
	 * located in the protocol submenu.
	 */
	public static StepSizeSpinner sizeProtocolMenuSpinner;

	/**
	 * Label to be displayed above the {@link #sizeSpinner}, to explain its
	 * purpose.
	 */
	public static Label sizeLabel = new Label(
			FingerpaintConstants.INSTANCE.lblStepSize());
	
	/**
	 * Label to be displayed above the {@link #sizeProtocolMenuSpinner}, to explain its
	 * purpose.
	 */
	public static Label sizeProtocolMenuLabel = new Label(
			FingerpaintConstants.INSTANCE.lblStepSize());
	
	// ---EXPORT CANVAS IMAGE WIDGET------------------------------------------

	/** Button to export the image of the current distribution. */
	public static ExportDistributionButton exportDistributionButton;

	// --- VIEW SINGLE GRAPH WIDGETS ------------------------------------------
	/**
	 * Pop-up menu to display the performance of a single graph. It is opened
	 * when {@link #viewSingleGraphButton} is clicked. It contains a vertical
	 * panel.
	 */
	public static ViewSingleGraphPopupPanel viewSingleGraphPopupPanel = new ViewSingleGraphPopupPanel();

	/**
	 * Horizontal panel to contain the Close and Export buttons.
	 */
	public static HorizontalPanel viewSingleGraphHorizontalPanel = new HorizontalPanel();

	/**
	 * Vertical panel to contain the horizontal panel and simple panel of the
	 * single graph pop-up.
	 */
	public static VerticalPanel viewSingleGraphVerticalPanel = new VerticalPanel();

	/**
	 * Simple panel to display the graph of the previously executed mixing run.
	 */
	public static SimplePanel viewSingleGraphGraphPanel = new SimplePanel();

	/** Button to view the performance of the previously executed mixing run. */
	public static ViewSingleGraphButton viewSingleGraphButton;

	/** Button to close the performance pop-up. */
	public static CloseSingleGraphViewButton closeSingleGraphViewButton = new CloseSingleGraphViewButton();

	/** Button to export the image of the current mixing performance. */
	public static ExportSingleGraphButton exportSingleGraphButton;

	/** Button to export the image of multiple mixing performances. */
	public static ExportMultipleGraphsButton exportMultipleGraphButton;

	// --- COMPARE PERFORMANCE WIDGETS ----------------------------------------
	/**
	 * CellList that can be used to select multiple mixing runs from all
	 * available saved mixing runs.
	 */
	public static CompareSelectPopupCellList compareSelectPopupCellList = new CompareSelectPopupCellList();

	/**
	 * Pop-up panel to display all the previously stored mixing runs with
	 * performance. It also contains the Compare and Close buttons.
	 */
	public static CompareSelectPopupPanel compareSelectPopupPanel = new CompareSelectPopupPanel();

	/**
	 * Pop-up panel that displays the simple panel with the performance graph
	 * and New Comparison and Close buttons.
	 */
	public static ComparePopupPanel comparePopupPanel = new ComparePopupPanel();

	/**
	 * Simple panel that displays a graph with the mixing performance of the
	 * selected mixing runs.
	 */
	public static SimplePanel compareGraphPanel = new SimplePanel();

	/**
	 * Button to compare the performance of previously saved mixing runs. When
	 * clicked, it opens the {@link #compareSelectPopupPanel} pop-up.
	 */
	public static ComparePerformanceButton comparePerformanceButton;

	/**
	 * Button to compare the performance of the selected mixing runs.
	 */
	public static CompareButton compareButton;

	/** Cancel button inside the compare performance pop-up. */
	// Note: this button NEEDS to be initialised AFTER the compareSelectPopupCellList
	public static CancelCompareButton cancelCompareButton = new CancelCompareButton(
			GuiState.compareSelectPopupCellList.getSelectionModel());

	/** Close button inside the compare performance pop-up. */
	// Note: this button NEEDS to be initialised AFTER the compareSelectPopupCellList
	public static CloseCompareButton closeCompareButton = new CloseCompareButton(
			GuiState.compareSelectPopupCellList.getSelectionModel());

	/**
	 * Button inside the compare performance pop-up to start a new comparison.
	 * When clicked, it closes the {@link #comparePopupPanel} pop-up and opens
	 * the {@link #compareSelectPopupPanel}pop-up
	 */
	public static NewCompareButton newCompareButton = new NewCompareButton();

	/**
	 * Sets the IDs for all widgets in this class (except the CellBrowser,
	 * ToggleColourButton and MenuToggleButton). The ID is either used in the
	 * CSS file or for debugging purposes. A widget may <b>never</b> have an
	 * ordinary ID and a debug ID at the same time.
	 */
	public static void setIDs() {
		loadingPanel.getElement().setId(GuiState.LOADINGPANEL_ID);
		loadingPanelMessage.getElement()
				.setId(GuiState.LOADINGPANEL_MESSAGE_ID);

		menuPanels[0].getElement().setId("menuPanel");
		menuPanels[1].getElement().setId("menuSub1Panel");
		menuPanels[2].getElement().setId("menuSub2Panel");
		menuPanelInnerWrapper.getElement().setId("menuPanelInnerWrapper");
		menuPanelOuterWrapper.getElement().setId("menuPanelWrapper");

		removeResultsVerticalPanel.ensureDebugId("removeResultsVerticalPanel");

		nrStepsLabel.ensureDebugId("nrStepsLabel");

		saveButtonsPanel.ensureDebugId("saveButtonsPanel");
		saveItemVerticalPanel.ensureDebugId("saveItemVerticalPanel");

		overwriteSavePanel.ensureDebugId("overwriteSavePanel");
		overwriteButtonsPanel.ensureDebugId("overwriteButtonsPanel");
		overwriteSaveVerticalPanel.ensureDebugId("overwriteSaveVerticalPanel");
		loadVerticalPanel.ensureDebugId("loadVerticalPanel");
		listScrollPanel.ensureDebugId("listScrollPanel");

		sizeLabel.ensureDebugId("sizeLabel");
		sizeProtocolMenuLabel.ensureDebugId("sizeProtocolMenuLabel");

		viewSingleGraphHorizontalPanel
				.ensureDebugId("viewSingleGraphHorizontalPanel");
		viewSingleGraphVerticalPanel
				.ensureDebugId("viewSingleGraphVerticalPanel");
		viewSingleGraphGraphPanel.getElement().setId(
				"viewSingleGraphGraphPanel");

		compareSelectPopupPanel.ensureDebugId("compareSelectPopupPanel");
		comparePopupPanel.ensureDebugId("comparePopupPanel");
		compareGraphPanel.getElement().setId("compareGraphPanel");
	}

}

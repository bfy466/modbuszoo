package fi.bfy466.view;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import fi.bfy466.MainApp;

import fi.bfy466.model.Register;
import fi.bfy466.util.DateConvertUtil;


public class RegisterOverviewController {

	@FXML
    private TableView<Register> registerTable;
    @FXML
    private TableColumn<Register, String> registerDescriptionColumn;
    @FXML
    private TableColumn<Register, String> registerTypeColumn;
    @FXML
    private TableColumn<Register, Integer> registerStartColumn;

    @FXML
    private Label registerPortLabel;
    @FXML
    private Label registerDescriptionLabel;
    @FXML
    private Label registerStartLabel;
    @FXML
    private Label registerSeriesLabel;
    @FXML
    private Label registerTypeLabel;
    @FXML
    private Label registerTypeTextLabel;
    @FXML
    private Label registerMultiplierLabel;
    @FXML
    private Label versionDateLabel;
    @FXML
    private Label registerStartHexLabel;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public RegisterOverviewController() {
    }

    /**
     * Initialises the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	// Initialising the register table with the three columns.

    	registerDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().registerDescriptionProperty());

    	//registerTypeColumn.setCellValueFactory(cellData -> cellData.getValue().registerTypeProperty().asObject());
    	registerTypeColumn.setCellValueFactory(cellData -> cellData.getValue().registerTypeTextProperty());

    	registerStartColumn.setCellValueFactory(cellData -> cellData.getValue().registerStartProperty().asObject());

    	// Clearing register data
    	showRegisterDetails(null);

    	// Listening to selection changes and show register details when changed
    	registerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showRegisterDetails(newValue));


    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Adding observable list data to the table
        registerTable.setItems(mainApp.getRegisterData());
    }

    /**
     * Filling all fields to show details about the register.
     * If the specified register is null, all text fields are cleared.
     *
     * @param register the register or null
     */
    private void showRegisterDetails(Register register) {
        String registerStartHexValue;

    	if (register != null) {
            // Filling the labels with info from the register object.
        	registerPortLabel.setText(String.valueOf(register.getRegisterPort()));
        	registerStartLabel.setText(String.valueOf(register.getRegisterStart()));


        	/**
        	 * Setting the hexadecimal value to corresponding label
        	 *
        	 */

        	registerStartHexValue = Integer.toString(Integer.parseInt(registerStartLabel.getText()), 16);
        	if (registerStartHexValue != null)
        		registerStartHexLabel.setText("0x" + registerStartHexValue.toUpperCase());
        	else
        		registerStartHexLabel.setText("0x0000");

        	registerTypeLabel.setText(String.valueOf(register.getRegisterType()));
        	registerTypeTextLabel.setText(register.getRegisterTypeText());
        	registerDescriptionLabel.setText(register.getRegisterDescription());
        	//registerSeriesLabel.setText(Integer.toString(register.getRegisterSeries()));
        	registerMultiplierLabel.setText(Double.toString(register.getRegisterMultiplier()));
        	versionDateLabel.setText(DateConvertUtil.format(register.getVersionDate()));

        } else {
            // Register is null, remove all the text.
        	registerPortLabel.setText("");
        	registerStartLabel.setText("");
        	registerTypeLabel.setText("");
        	registerDescriptionLabel.setText("");
        	//registerSeriesLabel.setText("");
        	registerMultiplierLabel.setText("");
        	versionDateLabel.setText("");
        }
    }


    /**
     * Calling when user clicks on the "Delete" button.
     */
    @FXML
    private void handleDeleteRegister() {
        int selectedIndex = registerTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	registerTable.getItems().remove(selectedIndex);
        } else {
        	// Nothing is selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No register Selected");
            alert.setContentText("Please select a register in the table.");

            alert.showAndWait();
        }
    }


    /**
     * Calling when user clicks the "New" button. Opens a dialog to edit
     * details for a new register.
     */
    @FXML
    private void handleNewRegister() {
        Register tempRegister = new Register(1, 3);
        boolean okClicked = mainApp.showRegisterEditDialog(tempRegister);
        if (okClicked) {
            mainApp.getRegisterData().add(tempRegister);
        }
    }

    /**
     * Calling when the user clicks the "Edit" button. Opens a dialog to edit
     * details for the selected register.
     */
    @FXML
    private void handleEditRegister() {
        Register selectedRegister = registerTable.getSelectionModel().getSelectedItem();
        if (selectedRegister != null) {
            boolean okClicked = mainApp.showRegisterEditDialog(selectedRegister);
            if (okClicked) {
                showRegisterDetails(selectedRegister);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No register selected");
            alert.setContentText("Please select a register in the table.");

            alert.showAndWait();
        }
    }




}
package fi.bfy466.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import fi.bfy466.model.Register;
import fi.bfy466.util.DateConvertUtil;
import fi.bfy466.util.isNumber;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegisterEditDialogController {

	@FXML
    private TextField registerPortField;
	@FXML
    private TextField registerStartField;
	@FXML
	private TextField registerDescriptionField;
    @FXML
    private TextField registerSeriesField;
    @FXML
    private TextField registerTypeField;
    @FXML
    private TextField registerMultiplierField;
    @FXML
    private TextField versionDateField;
    @FXML
    private Label registerStartHexLabel;


    private Stage dialogStage;
    private Register register;
    private boolean okClicked = false;
    LocalDate todaySystem = LocalDate.now();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

     /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    /**
     * Sets the register to be edited in the dialog.
     *
     * @param register
     */
    public void setRegister(Register register) {
        this.register = register;

        String registerStartHexValue;

        registerPortField.setText(Integer.toString(register.getRegisterPort()));
        registerStartField.setText(Integer.toString(register.getRegisterStart()));

        registerStartHexValue = Integer.toString(Integer.parseInt(registerStartField.getText()), 16);
    	registerStartHexLabel.setText("0x" + registerStartHexValue.toUpperCase());

        registerDescriptionField.setText(register.getRegisterDescription());
        registerSeriesField.setText(Integer.toString(register.getRegisterSeries()));
        registerTypeField.setText(Integer.toString(register.getRegisterType()));
        registerMultiplierField.setText(Double.toString(register.getRegisterMultiplier()));
        versionDateField.setText(DateConvertUtil.format(register.getVersionDate()));
        versionDateField.setPromptText("dd.mm.yyyy");
    }


    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }



    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";


        /**
         * Checking if input values have correct format and inside dedicated ranges
         *
         */
        if (registerPortField.getText() == null || Integer.parseInt(registerPortField.getText()) < 3 || Integer.parseInt(registerPortField.getText()) > 10
        		|| !isNumber.CheckIsNumber(registerPortField.getText()))
        {
            errorMessage += "Not valid COM port number\nValid range: 1 .. 10";
            registerPortField.setText("6");
            register.setRegisterPort(Integer.parseInt(registerPortField.getText()));
        }

        if (registerStartField.getText() == null || registerStartField.getText().length() == 0 || Integer.parseInt(registerStartField.getText()) < 0
        		|| Integer.parseInt(registerStartField.getText()) > 49999 || isNumber.CheckIsNumber(registerPortField.getText()) == false)
        {
            errorMessage += "Not valid start register!\nValid range: 0 .. 49 999";
            registerStartField.setText("1");
            register.setRegisterStart(Integer.parseInt(registerStartField.getText()));

            /**
             * Setting the hexadecimal value of a register to corresponding label
             *
             */

            registerStartHexLabel.setText(Integer.toString(Integer.parseInt(registerStartField.getText()), 16));
        }

        if (registerDescriptionField.getText() == null || registerDescriptionField.getText().length() == 0)
        {
            errorMessage += "Default description\n";
            registerStartField.setText("Default description. Register number; " + registerStartField.getText());
            register.setRegisterDescription(registerStartField.getText());

        }
        if (registerSeriesField.getText() == null || registerSeriesField.getText().length() == 0 || Integer.parseInt(registerSeriesField.getText()) < 1
        		|| Integer.parseInt(registerSeriesField.getText()) > 64 || isNumber.CheckIsNumber(registerPortField.getText()) == false)
        {
            errorMessage += "Not valid number of registers!\nValid range: 1 .. 64\nApplying default value ( 1 )";
            registerSeriesField.setText("1");
            register.setRegisterSeries(Integer.parseInt(registerSeriesField.getText()));
        }

        if (registerTypeField.getText() == null || registerTypeField.getText().length() == 0 || Integer.parseInt(registerTypeField.getText()) < 1
        		|| Integer.parseInt(registerTypeField.getText()) > 16 || isNumber.CheckIsNumber(registerPortField.getText()) == false)
        {
            errorMessage += "Not valid register type\nApplying default value ( 3 )";
            registerTypeField.setText("3");
            register.setRegisterType(Integer.parseInt(registerTypeField.getText()));
        }

        if (registerMultiplierField.getText() == null || registerMultiplierField.getText().length() == 0 || Double.parseDouble(registerMultiplierField.getText()) < 0.0
        		|| isNumber.CheckIsNumber(registerPortField.getText()) == false)

        {
            errorMessage += "Not valid multiplier\nApplying default value ( 1.0 )";
            registerMultiplierField.setText("1.0");
            register.setRegisterMultiplier(Double.parseDouble(registerMultiplierField.getText()));
        }

        if (versionDateField.getText() == null || versionDateField.getText().length() == 0) {
            errorMessage += "No valid version date\nApplying today's system date";
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            LocalDate localDateToday = LocalDate.now();
            String todayFormatted = todaySystem.format(dateFormat);
            versionDateField.setText(todayFormatted);

            register.setVersionDate(localDateToday);

        } else {
            if (!DateConvertUtil.validDate(versionDateField.getText())) {
                errorMessage += "No valid version date. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please check fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }


    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
        	register.setRegisterPort(Integer.parseInt(registerPortField.getText()));
        	register.setRegisterStart(Integer.parseInt(registerStartField.getText()));
            register.setRegisterDescription(registerDescriptionField.getText());
            register.setRegisterSeries(Integer.parseInt(registerSeriesField.getText()));
            register.setRegisterType(Integer.parseInt(registerTypeField.getText()));
            register.setRegisterMultiplier(Double.parseDouble(registerMultiplierField.getText()));
            register.setVersionDate(DateConvertUtil.parse(versionDateField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }


}

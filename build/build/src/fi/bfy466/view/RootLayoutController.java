package fi.bfy466.view;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.JAXBException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import fi.bfy466.MainApp;

/**
 * Controller for the root layout. Basic functionality: menu bar and frame for other elements.
 * @author bfy466
 *
 */


public class RootLayoutController {


    // Reference to the main application
    private MainApp mainApp;

    /**
     * Calling by main application to give a reference.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Creating an empty list of registers.
     */
    @FXML
    private void handleNew() {
        mainApp.getRegisterData().clear();
        mainApp.setRegisterFilePath(null);
    }

    /**
     * Opening a FileChooser to select a list to load.
     */
    @FXML
    private void handleOpen() throws JAXBException {

    	File registerFile = mainApp.getRegisterFilePath();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(registerFile);
        fileChooser.setTitle("Open registers XML file");

        // Setting extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Showing open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        try {
	        if (file != null) {

				mainApp.loadRegisterDataFromFile(file);

	        }
	        else
	        {
	        	mainApp.getRegisterData().clear();
	            mainApp.setRegisterFilePath(null);
	        }
        } catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
    }

    /**
     * Saving opened file with the list. If there is no open file, the "save as" dialog will be shown.
     */
    @FXML
    private void handleSave() {
        File registerFile = mainApp.getRegisterFilePath();
        if (registerFile != null) {
            try {
				mainApp.saveRegisterDataToFile(registerFile);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e);
			}
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opening FileChooser to let the user select the file to save to.
     */
    @FXML
    private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();

		// Setting extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Showing save file dialog
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		if (file != null) {
			// Making sure that file has the correct extension
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			try {
				mainApp.saveRegisterDataToFile(file);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e);
			}
		}
	}


    /**
     * Calling when user clicks the "Create Function block" button.
     */
    @FXML
    private void handleCreateFunctionBlockIEC() {


    	String currentDirectory = System.getProperty("user.dir");

    	LocalDate localDateForSave = LocalDate.now();
    	LocalTime localTimeForSave = LocalTime.now();

    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    	String fileNameTimestampFB = localDateForSave.toString() + "_" + localTimeForSave.format(formatter).replace(":", "");

    	// Integer rand = (int)(Math.random() * ((99999 - 10000) + 1)) + 10000;
    	File fileFB = new File(currentDirectory + "\\" + fileNameTimestampFB + "_FB.ST");
    	File file = new File(currentDirectory + "\\" + fileNameTimestampFB + "_PRG.ST");

    	try {
			mainApp.saveRegisterDataToIECFB(fileFB);
			mainApp.saveRegisterDataToIECcall(file, fileFB);
			System.out.println(fileFB.getPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}

    }


    /**
     * Calling when user clicks the "Create Function block" button.
     */
    @FXML
    private void handleCreateFunctionBlockIECAs() {
	    FileChooser fileChooser = new FileChooser();

		// Setting extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ST files (*.st)", "*.st");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setInitialDirectory(mainApp.getRegisterFilePath());
		fileChooser.setTitle("Saving Function block and Call program files");

		// Showing save file dialog
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

			try {
				File fileFB = new File(file.getPath().substring(0, file.getPath().length() - 3) + "_FB.st");
				File filePRG = new File(file.getPath().substring(0, file.getPath().length() - 3) + "_PRG.st");
				mainApp.saveRegisterDataToIECFB(fileFB);
				System.out.println( filePRG.getPath());
				System.out.println( fileFB.getPath());
				mainApp.saveRegisterDataToIECcall(filePRG,fileFB);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e);
			}

    }



    /**
     * Opening about dialog.
     */
    @FXML
    private void handleAbout() {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("ModbusZoo");
    	alert.setHeaderText("version 0.1");
    	alert.setContentText("Author: Nikolai Zhukov\n bfy466");

    	alert.showAndWait();
    }

    /**
     * Closing the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

}

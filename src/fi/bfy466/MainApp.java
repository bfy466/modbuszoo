package fi.bfy466;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import java.util.List;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import fi.bfy466.model.Register;
import fi.bfy466.model.RegisterListWrapper;
import fi.bfy466.view.RegisterEditDialogController;
import fi.bfy466.view.RegisterOverviewController;
import fi.bfy466.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
	 * The data as an observable list of Registers.
	 */
	private ObservableList<Register> registerData = FXCollections.observableArrayList();
	public List<Register> registerListData = registerData;

	/**
	 * Constructor
	 */
	public MainApp() {
		// Adding some sample data
		/**
		*registerData.add(new Register(16, 3));
		*registerData.add(new Register(4, 17));
		*registerData.add(new Register(5, 64));
		*registerData.add(new Register(5, 65));
		*registerData.add(new Register(6, 128));
		*registerData.add(new Register(6, 129));
		*/


	}

	/**
	 * Returns file preference state.
	 * The preference is read from the operation system's specific registry. If no such preference can be found, null is returned.
	 *
	 * @return
	 */
	public File getRegisterFilePath() {

		String filePath = System.getProperty("user.dir");

	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	    /*
		String currentPath1 = System.getProperty("user.dir");
		System.out.println("Method 1: Current path is:: " + currentPath1);



		File currFile = new File("");
		String currentPath2 = currFile.getAbsolutePath();
		System.out.println("Method 2: Current path is:: " + currentPath2);

		Path currentDirectoryPath = Paths.get("").toAbsolutePath();
		String currentPath3 = currentDirectoryPath.toString();
		System.out.println("Method 3: Current directory path:: " + currentPath3);


		FileSystem fileSystem = FileSystems.getDefault();
		Path path = fileSystem.getPath("").toAbsolutePath();
		String currentDirectoryPath4 = path.toString();
		System.out.println("Method 4: Current directory path:: " + currentDirectoryPath4);

		return new File(currentPath1);
		*/
	}

	/**
	 * Sets the path of the current loaded file. The path is requested from operating system registry.
	 *
	 * @param file the file or null to remove the path
	 */
	public void setRegisterFilePath(File file) {

		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        primaryStage.setTitle("ModbusZoo - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        primaryStage.setTitle("ModbusZoo");
	    }

	}




	 /**
     * Initialising the root layout.
     */
    public void initRootLayout() {
    	try {
            // Loading root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Showing the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Giving controller access to the main application MainApp.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Trying to load last opened file.
/*
    	try {
			File file = getRegisterFilePath();

			if (file != null) {
			    loadRegisterDataFromFile(file);
			}

        } catch (Exception e) {
			// Catching any exception
			e.printStackTrace();
		}
*/
    }


	/**
	 * Opens a dialog to edit details for the specified register. If the user
	 * clicks OK, the changes are saved into the provided register object and true
	 * is returned.
	 *
	 * @param  register object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showRegisterEditDialog(Register register) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/RegisterEditDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Edit register parameters");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the register into the controller.
	        RegisterEditDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setRegister(register);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


	 /**
     * Shows the registers overview inside the root layout.
     */
    public void showRegisterOverview() {
        try {
            // Load register overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RegisterOverview.fxml"));
            AnchorPane registerOverview = (AnchorPane) loader.load();

            // Set register overview into the centre of root layout.
            rootLayout.setCenter(registerOverview);

            // Give the controller access to the main app.
            RegisterOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	/**
	 * Returns the data as an observable list of Registers.
	 * @return
	 */
	public ObservableList<Register> getRegisterData() {
		return registerData;
	}

	public List<Register> getListRegisterData() {
		return registerData;
	}


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ModbusZoo");
        this.primaryStage.getIcons().add(new Image("file:resources/images/zoo_icon_1.png"));
        initRootLayout();
        showRegisterOverview();

    }





	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}





	/**
	 * Loads register data from the file. Current register data will be replaced.
	 *
	 * @param file
	 */
	public void loadRegisterDataFromFile(File file) throws JAXBException {
	    try {


	    	JAXBContext context = JAXBContext.newInstance(RegisterListWrapper.class);
	        Unmarshaller unmarshaller = context.createUnmarshaller();

	        // Reading XML from the file and unmarshalling.
	        RegisterListWrapper wrapper = (RegisterListWrapper) unmarshaller.unmarshal(file);
	        System.out.println(wrapper);

	        registerData.clear();
	        registerData.addAll(wrapper.getRegisters());

	        // Saving the file path to the registry.
	        setRegisterFilePath(file);

	    } catch (Exception e) { // catches exception
	    	System.out.println(e);
	    	Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error message");
	        alert.setHeaderText("Cannot load data");
	        alert.setContentText("Cannot load data from file:\n" + file.getPath() + "\n\n" + e.toString());
	        alert.showAndWait();
	    }
	}

	/**
	 * Saves current register data to the specified file.
	 *
	 * @param file
	 */
	public void saveRegisterDataToFile(File file) throws JAXBException {
	    try {
	        JAXBContext context = JAXBContext.newInstance(RegisterListWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping register data.
	        RegisterListWrapper wrapper = new RegisterListWrapper();
	        wrapper.setRegisters(registerData);

	        // Marshalling and saving XML to file.
	        m.marshal(wrapper, file);
	        m.marshal(wrapper, System.out);

	        // Save the file path to the registry.
	        setRegisterFilePath(file);

	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Cannot save data");
	        alert.setContentText("Cannot save data to file:\n" + file.getPath());

	        alert.showAndWait();
	    }
	}


	/**
	 * Saves current register data to the function block IEC.
	 *
	 * @param file
	 */
	public void saveRegisterDataToIECFB(File file) throws IOException {

	if (!registerData.isEmpty())
		{
		try {
	    		// Trying to create new function block file *.ST
	    		// Filename is creating in the RootLayoutController



			      if (file.createNewFile()) {
			        System.out.println("File created: " + file.getName());

			        Alert alert = new Alert(AlertType.INFORMATION);
			    	alert.setTitle("ModbusZoo");
			    	alert.setHeaderText("File created: ");
			    	alert.setContentText(file.getPath());
			    	alert.showAndWait();
			    	// Save the file path to the registry.
			        setRegisterFilePath(file);

			      } else {
			        System.out.println("File already exists.");

			        Alert alert = new Alert(AlertType.INFORMATION);
			    	alert.setTitle("ModbusZoo");
			    	alert.setHeaderText("File allready exists: ");
			    	alert.setContentText(file.getPath());
			    	alert.showAndWait();
			    	// Save the file path to the registry.
			        setRegisterFilePath(file);

			      }

			    } catch (IOException e) {
			      System.out.println("An error occurred.");

			      Alert alert = new Alert(AlertType.WARNING);
		          alert.setTitle("ModbusZoo");
		          alert.setHeaderText("An error occured.");
		          alert.setContentText(e.toString());
		          alert.showAndWait();
			      e.printStackTrace();

			    }


				try {

					/*
					 * Creating a file writer to save data to the function block *.ST file
					 */

					FileWriter myWriter = new FileWriter(file.getName());


					// Header of function block
					myWriter.write("FUNCTION_BLOCK " + (file.getName()).substring(0, file.getName().length() - 3) + "\r\n");
			  	    myWriter.write("VAR_EXTERNAL\n\n");
			  	    myWriter.write("END_VAR\n\n");

			  	    myWriter.write("VAR_INPUT\r\n");
			  	    myWriter.write("iModuleAddress :INT;\r\n");
			  	    myWriter.write("iPort t:INT;\r\n");

			  	    // Creating function block input string variables (parameters) according to the register type
			  	    for (Register r : registerListData) {

			  	    	switch (r.getRegisterType()) {
			  	    		case 1:
			  	    			myWriter.write("coil_"+ r.getRegisterStart() + "\t\t\t:STRING(30);\r\n");
			  	    			break;
			  	    		case 5:
			  	    			myWriter.write("coil_"+ r.getRegisterStart() + "\t\t\t:STRING(30);\r\n");
			  	    			break;
			  	    		case 15:
			  	    			myWriter.write("coil_"+ r.getRegisterStart() + "\t\t\t:STRING(30);\r\n");
			  	    			break;

			  	    		case 2:
			  	    			myWriter.write("discrete_"+ r.getRegisterStart() + "\t\t\t:STRING(30);\r\n");
			  	    			break;

			  	    		case 3:
			  	    			myWriter.write("holding_"+ r.getRegisterStart() + "\t\t\t:STRING(30);\r\n");
			  	    			break;
			  	    		case 6:
			  	    			myWriter.write("holding_"+ r.getRegisterStart() + "\t\t\t:STRING(30);\r\n");
			  	    			break;
			  	    		case 16:
			  	    			myWriter.write("holding_"+ r.getRegisterStart() + "\t\t\t:STRING(30);\r\n");
			  	    			break;

			  	    		case 4:
			  	    			myWriter.write("inputreg_"+ r.getRegisterStart() + "\t\t\t:STRING(30);\r\n");
			  	    			break;

			  	    		default:
			  	    			myWriter.write("unknown_"+ r.getRegisterStart() + "\t\t\t:STRING(30);\r\n");
			  	    	}
			  	    }

			  	    myWriter.write("id_ModuleAlarm : STRING(30):='';\r\n");
			  	    myWriter.write("END_VAR\n\n");
			  	    myWriter.write("VAR_OUTPUT\n\n");
			  	    myWriter.write("END_VAR\n\n");

			  	    // Common function block variables
			  	    myWriter.write("VAR\r\n");
			  	    myWriter.write("iResult :INT;\r\n");
			  	    myWriter.write("wResult :WORD;\r\n");
			  	    myWriter.write("udintResult :UDINT;\r\n");

			  	    // Creating standard Modbus function block calls for every register
			  	    for (Register r : registerListData) {

			  	    	myWriter.write("fbReg_x"+ r.getRegisterType() + "_" + r.getRegisterStart() + " :GenericModbus642FB;\r\n");

			  	    }

			  	    myWriter.write("\nEND_VAR\r\n");

			  	    for (Register r : registerListData){

			  	    	String genericModbusName = "fbReg_x"+ r.getRegisterType() + "_" + r.getRegisterStart();

			  	    	/*
			  	    	 * In all IEC register functions is a limitation for INTEGER variable for start register: if more then 32767 (e.g. decimal 32768 = hexadecimal 8000), it shall be in a format 16#8000
			  	    	 */
			  	    	String regStart = Integer.toString(r.getRegisterStart());
			  	    	if (r.getRegisterStart() >= 32767) {
		  	    			regStart = "16#" + Integer.toString(r.getRegisterStart(), 16);
		  	    		};

			  	    	/*
			  	    	* Selection of the proper register function according to the register type
						*/

			  	    	switch (r.getRegisterType()) {

			  	    	// COIL
			  	    	case 1:

			  	    		myWriter.write(
		  	    			genericModbusName + "(Port:= " + r.getRegisterPort() +", Send:=0, Module:=iModuleAddress, StartRegister := " +
		  	    			regStart + ", RegisterType:=" + r.getRegisterType() +");\r\nIF " + genericModbusName + ".DataValid = 1 THEN\r\n" +
		  	    			genericModbusName + ".Reg0 := CoilF_3(id_String := coil_" + r.getRegisterStart() + ", RegValue := " + genericModbusName +
		  	    			".Reg0,  BitMask := 16#0001, Force := FALSE, ReleaseOverride := TRUE);\r\n" + genericModbusName +"(Send := 2);\r\nEND_IF;\r\n");

		  	    			break;
		  	    		case 5:
		  	    			myWriter.write(
				  	    	genericModbusName + "(Port:= " + r.getRegisterPort() +", Send:=0, Module:=iModuleAddress, StartRegister := " +
				  	    	regStart + ", RegisterType:=" + r.getRegisterType() +");\r\nIF " + genericModbusName + ".DataValid = 1 THEN\r\n" +
				  	    	genericModbusName + ".Reg0 := CoilF_3(id_String := coil_" + r.getRegisterStart() + ", RegValue := " + genericModbusName +
				  	    	".Reg0,  BitMask := 16#0001, Force := FALSE, ReleaseOverride := TRUE);\r\n" + genericModbusName +"(Send := 2);\r\nEND_IF;\r\n");

		  	    			break;
		  	    		case 15:
		  	    			myWriter.write(
				  	    	genericModbusName + "(Port:= " + r.getRegisterPort() +", Send:=0, Module:=iModuleAddress, StartRegister := " +
				  	    	regStart + ", RegisterType:=" + r.getRegisterType() +");\r\nIF " + genericModbusName + ".DataValid = 1 THEN\r\n" +
				  	    	genericModbusName + ".Reg0 := CoilF_3(id_String := coil_" + r.getRegisterStart() + ", RegValue := " + genericModbusName +
				  	    	".Reg0,  BitMask := 16#0001, Force := FALSE, ReleaseOverride := TRUE);\r\n" + genericModbusName +"(Send := 2);\r\nEND_IF;\r\n");

		  	    			break;

		  	    		// DISCRETE INPUT
		  	    		case 2:
		  	    			myWriter.write(
				  	    	genericModbusName + "(Port:= " + r.getRegisterPort() +", Send:=0, Module:=iModuleAddress, StartRegister := " +
				  	    	regStart + ", RegisterType:=" + r.getRegisterType() +");\r\nIF " + genericModbusName + ".DataValid = 1 THEN\r\n" +
				  	    	"iResult := DiscreteInputF(id_String := discrete_" + r.getRegisterStart() + ", RegValue := " + genericModbusName +
				  	    	".Reg0,  BitMask := 16#0001);\r\nEND_IF;\r\n");
		  	    			break;

		  	    		// HOLDING REGISTER
		  	    		case 3:
		  	    			myWriter.write(
						  	genericModbusName + "(Port:= " + r.getRegisterPort() +", Send:=0, Module:=iModuleAddress, StartRegister := " +
						  	regStart + ", RegisterType:=" + r.getRegisterType() +");\r\nIF " + genericModbusName + ".DataValid = 1 THEN\r\n" +
						  	genericModbusName + ".Reg0 := HoldingRegF_3(id_String := holding_" + r.getRegisterStart() + ", RegValue := " + genericModbusName +
						  	".Reg0,  rMultiplier := "+ r.getRegisterMultiplier() + ", Force := FALSE, ReleaseOverride := TRUE);\r\n" + genericModbusName +"(Send := 2);\r\nEND_IF;\r\n");

		  	    			break;
		  	    		case 6:
		  	    			myWriter.write(
		  	    			genericModbusName + "(Port:= " + r.getRegisterPort() +", Send:=0, Module:=iModuleAddress, StartRegister := " +
						  	regStart + ", RegisterType:=" + r.getRegisterType() +");\r\nIF " + genericModbusName + ".DataValid = 1 THEN\r\n" +
						  	genericModbusName + ".Reg0 := HoldingRegF_3(id_String := holding_" + r.getRegisterStart() + ", RegValue := " + genericModbusName +
						  	".Reg0,  rMultiplier := "+ r.getRegisterMultiplier() + ", Force := FALSE, ReleaseOverride := TRUE);\r\n" + genericModbusName +"(Send := 2);\r\nEND_IF;\r\n");

		  	    			break;
		  	    		case 16:
		  	    			myWriter.write(
		  	    			genericModbusName + "(Port:= " + r.getRegisterPort() +", Send:=0, Module:=iModuleAddress, StartRegister := " +
							regStart + ", RegisterType:=" + r.getRegisterType() +");\r\nIF " + genericModbusName + ".DataValid = 1 THEN\r\n" +
							genericModbusName + ".Reg0 := HoldingRegF_3(id_String := holding_" + r.getRegisterStart() + ", RegValue := " + genericModbusName +
							".Reg0,  rMultiplier := "+ r.getRegisterMultiplier() + ", Force := FALSE, ReleaseOverride := TRUE);\r\n" + genericModbusName +"(Send := 2);\r\nEND_IF;\r\n");

		  	    			break;

		  	    		// INPUT REGISTER
		  	    		case 4:
		  	    			myWriter.write(
							genericModbusName + "(Port:= " + r.getRegisterPort() +", Send:=0, Module:=iModuleAddress, StartRegister := " +
							regStart + ", RegisterType:=" + r.getRegisterType() +");\r\nIF " + genericModbusName + ".DataValid = 1 THEN\r\n" +
							genericModbusName + ".Reg0 := InputRegF(id_String := inputreg_" + r.getRegisterStart() + ", RegValue := " + genericModbusName +
							".Reg0,  rMultiplier := "+ r.getRegisterMultiplier() + ");\r\n" + "END_IF;\r\n");

		  	    			break;

		  	    		default:
		  	    			myWriter.write("(*** ERROR !!!  UNKNOWN REGISTER TYPE FOR REGISTER STARTED BY"+ r.getRegisterStart() + "\t\t, REGISTER TYPE AS " + r.getRegisterType() + "***);\r\n");
		  	    	}


			  	    }

			  	    myWriter.write("END_FUNCTION_BLOCK");
			  	    myWriter.close();

			  	    System.out.println("Successfully wrote to the file.");

			  	    } catch (IOException e) {
			  	      System.out.println("An error occurred.");
			  	      e.printStackTrace();
			  	      System.out.println(e);


	    	for (Register tempR : registerListData) {

	    		String description = tempR.getRegisterDescription();
	    		int start = tempR.getRegisterStart();
	    		int port = tempR.getRegisterPort();
	    		int type = tempR.getRegisterType();
	    		String typeText = tempR.getRegisterTypeText();
	    		Double multiplier = tempR.getRegisterMultiplier();
	    		LocalDate versionDate = tempR.getVersionDate();

	            System.out.println("port : " + port + "---  start : " + start + "---  typeText : " + typeText + "---  type : " + type + "---  multiplier : " + multiplier + "---  description : " + description + "---  versionDate : " + versionDate);

	    	}
		}

	}
	else
	{
			System.out.println("An empty Register list.");
			Alert alert = new Alert(AlertType.WARNING);
	          alert.setTitle("ModbusZoo");
	          alert.setHeaderText("An empty Register list.");
	          alert.showAndWait();

	}

	}


	public void saveRegisterDataToIECcall(File file, File fileFB) throws IOException {

		if ( !registerData.isEmpty() )
		{
		try
			{
		    		// Trying to create new call program file *.ST
		    		// Filename is creating in the RootLayoutController

				      if (file.createNewFile()) {
				        System.out.println("File created: " + file.getName());

				        Alert alert = new Alert(AlertType.INFORMATION);
				    	alert.setTitle("ModbusZoo");
				    	alert.setHeaderText("File created: ");
				    	alert.setContentText(file.getPath());
				    	alert.showAndWait();
				    	// Save the file path to the registry.
				        setRegisterFilePath(file);

				      } else {
				        System.out.println("File already exists.");

				        Alert alert = new Alert(AlertType.INFORMATION);
				    	alert.setTitle("ModbusZoo");
				    	alert.setHeaderText("File allready exists: ");
				    	alert.setContentText(file.getPath());
				    	alert.showAndWait();
				    	// Save the file path to the registry.
				        setRegisterFilePath(file);

				      }

				    } catch (IOException e) {
				      System.out.println("An error occurred.");

				      Alert alert = new Alert(AlertType.WARNING);
			          alert.setTitle("ModbusZoo");
			          alert.setHeaderText("An error occured.");
			          alert.setContentText(e.toString());
			          alert.showAndWait();
				      e.printStackTrace();

				    }


					try {

						/*
						 * Creating a file writer to save data to the function block *.ST file
						 */

							FileWriter myWriter = new FileWriter(file.getName());
							Register tempR = registerListData.get(0);



							// Header of function block
							myWriter.write("PROGRAM " + (file.getName()).substring(0, file.getName().length() - 3) + "\r\n");

					  	    // Common function block variables
					  	    myWriter.write("VAR\r\n");
					  	    myWriter.write("iResult\t:INT;\r\n");

					  	    myWriter.write("fbMB\t: "+ (fileFB.getName()).substring(0, fileFB.getName().length() - 3) +";\r\n");
					  	    myWriter.write("\nEND_VAR\r\n");

					  	    myWriter.write("fbMB(\r\niModuleAddress	:= 1,\r\niPort := " + tempR.getRegisterPort() + ",\r\n");

					  	    for (Register r : registerListData)
					  	    {

					  	    	/*
					  	    	* Selection of the proper register function according to the register type
								*/

					  	    	switch (r.getRegisterType())
					  	    	{

					  	    	// COIL
					  	    	case 1:
					  	    		myWriter.write("coil_" + r.getRegisterStart() + " := '',\t(*** " + r.getRegisterDescription() + " ***)\r\n");

				  	    			break;
				  	    		case 5:
				  	    			myWriter.write("coil_" + r.getRegisterStart() + " := '',\t(*** " + r.getRegisterDescription() + " ***)\r\n");

				  	    			break;
				  	    		case 15:
				  	    			myWriter.write("coil_" + r.getRegisterStart() + " := '',\t(*** " + r.getRegisterDescription() + " ***)\r\n");

				  	    			break;

				  	    		// DISCRETE INPUT
				  	    		case 2:
				  	    			myWriter.write("discrete_" + r.getRegisterStart() + " := '',\t(*** " + r.getRegisterDescription() + " ***)\r\n");

				  	    			break;

				  	    		// HOLDING REGISTER
				  	    		case 3:
				  	    			myWriter.write("holding_" + r.getRegisterStart() + " := '',\t(*** " + r.getRegisterDescription() + " ***)\r\n");

				  	    			break;
				  	    		case 6:
				  	    			myWriter.write("holding_" + r.getRegisterStart() + " := '',\t(*** " + r.getRegisterDescription() + " ***)\r\n");

				  	    			break;
				  	    		case 16:
				  	    			myWriter.write("holding_" + r.getRegisterStart() + " := '',\t(*** " + r.getRegisterDescription() + " ***)\r\n");

				  	    			break;

				  	    		// INPUT REGISTER
				  	    		case 4:
				  	    			myWriter.write("inputreg_" + r.getRegisterStart() + " := '',\t(*** " + r.getRegisterDescription() + " ***)\r\n");

				  	    			break;

				  	    		default:
				  	    			myWriter.write("(*** ERROR !!!  UNKNOWN REGISTER TYPE FOR REGISTER STARTED BY"+ r.getRegisterStart() + "\t\t, REGISTER TYPE AS " + r.getRegisterType() + "***);\r\n");
					  	    	}


					  	    }

					  	    myWriter.write("id_ModuleAlarm := '');\r\n");
					  	    myWriter.write("END_PROGRAM");
					  	    myWriter.close();

					  	    System.out.println("Successfully wrote to the file.");

					}

						catch (IOException e)
					  	    {
					  	      System.out.println("An error occurred.");
					  	      e.printStackTrace();
					  	      System.out.println(e);


			}
		}else{

			System.out.println("An empty Register list.");
			Alert alert = new Alert(AlertType.WARNING);
	          alert.setTitle("ModbusZoo");
	          alert.setHeaderText("An empty Register list.");
	          alert.showAndWait();
		}

	}





    public static void main(String[] args) {
        launch(args);
    }
}
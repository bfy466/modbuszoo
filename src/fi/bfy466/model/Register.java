package fi.bfy466.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import fi.bfy466.util.LocalDateAdapter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Register {

	private final StringProperty registerDescription;
//	private final StringProperty versionDescription;

	private final IntegerProperty registerPort;
	private final IntegerProperty registerStart;
	private final IntegerProperty registerSeries;
	private final IntegerProperty registerType;
	private final StringProperty registerTypeText;
	private final DoubleProperty registerMultiplier;
	private final ObjectProperty<LocalDate> versionDate;

	/**
	 * Default constructor
	 */
	public Register() {
		// this(null, null);
		this.registerStart = new SimpleIntegerProperty(1);
		this.registerDescription = new SimpleStringProperty("Default description");
		this.registerPort = new SimpleIntegerProperty(6);

		this.registerType = new SimpleIntegerProperty(3);
		this.registerTypeText = new SimpleStringProperty("undefined register type");

		this.registerSeries = new SimpleIntegerProperty(1);
		this.registerMultiplier = new SimpleDoubleProperty(1.0);
		this.versionDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
	}

	/**
	 * Constructor with some initial data.
	 *
	 * @param registerStart
	 * @param registerType
	 */
	public Register(Integer registerStart, Integer registerType) {

		this.registerStart = new SimpleIntegerProperty(registerStart);
		this.registerDescription = new SimpleStringProperty("Default description");
		this.registerPort = new SimpleIntegerProperty(6);

		this.registerType = new SimpleIntegerProperty(registerType);
		this.registerTypeText = new SimpleStringProperty("undefined register type");

		this.registerSeries = new SimpleIntegerProperty(1);
		this.registerMultiplier = new SimpleDoubleProperty(1.0);
		this.versionDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());

	}

// Register port methods

	public int getRegisterPort() {
		return registerPort.get();
	}

	public void setRegisterPort(int registerPort) {
		this.registerPort.set(registerPort);
	}

	public IntegerProperty registerPortProperty() {
		return registerPort;
	}


// Start Register methods

	public int getRegisterStart() {
		return registerStart.get();
	}

	public void setRegisterStart(int registerStart) {
		this.registerStart.set(registerStart);
	}

	public IntegerProperty registerStartProperty() {
		return registerStart;
	}


// Description methods

	public String getRegisterDescription() {
		return registerDescription.get();
	}

	public void setRegisterDescription(String registerDescription) {
		this.registerDescription.set(registerDescription);
	}

	public StringProperty registerDescriptionProperty() {
		return registerDescription;
	}

// Register Type methods

	public int getRegisterType() {
		return registerType.get();
	}

	public void setRegisterType(int registerType) {
		this.registerType.set(registerType);
	}

	public IntegerProperty registerTypeProperty() {
		return registerType;
	}

// Register Type text methods

	public String getRegisterTypeText() {
		return registerTypeText.get();
	}

	public void setRegisterTypeText(String registerTypeText) {
		this.registerTypeText.set(registerTypeText);
	}

	public StringProperty registerTypeTextProperty() {

		/**
		 * Creating additional String property for different types of registers
		 */
		int type = this.getRegisterType();

		SimpleStringProperty ssp = new SimpleStringProperty();

		switch(type) {
			case 1:
				ssp.setValue("Coil (Function 1)");
				break;
			case 2:
				ssp.setValue("Descrete Input (Function 2)");
				break;
			case 3:
				ssp.setValue("Holding Register (Function 3)");
				break;
			case 4:
				ssp.setValue("Input Register (Function 4)");
				break;
			case 5:
				ssp.setValue("Coil (Function 5)");
				break;
			case 6:
				ssp.setValue("Holding Register (Function 6)");
				break;
			case 15:
				ssp.setValue("Coil (Function 15)");
				break;
			case 16:
				ssp.setValue("Holding Register (Function 16)");
				break;
			default:
				ssp.setValue("Undefined register type");
		}

		//this.registerTypeText = ssp;
		return ssp;
	}

// Register Series methods

	public int getRegisterSeries() {
		return registerSeries.get();
	}

	public void setRegisterSeries(int registerSeries) {
		this.registerSeries.set(registerSeries);
	}

	public IntegerProperty registerSeriesProperty() {
		return registerSeries;
	}

// Register Multiplier methods

	public double getRegisterMultiplier() {
		return registerMultiplier.get();
	}

	public void setRegisterMultiplier(double registerMultiplier) {
		this.registerMultiplier.set(registerMultiplier);
	}

	public DoubleProperty registerMultiplierProperty() {
		return registerMultiplier;
	}

// Version date methods

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getVersionDate() {
	    return versionDate.get();
	}


	public void setVersionDate(LocalDate versionDate) {
		this.versionDate.set(versionDate);
	}

	public ObjectProperty<LocalDate> versionDateProperty() {
		return versionDate;
	}

}
package fi.bfy466.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper class for list of registers. This is used for saving the list to XML.
 *
 */

@XmlRootElement(name = "registers")
//@XmlAccessorType (XmlAccessType.FIELD)

public class RegisterListWrapper {

	private List<Register> registers;

	@XmlElement(name = "register")
	public List<Register> getRegisters() {
		return registers;
	}

	public void setRegisters(List<Register> registers) {
		this.registers = registers;
	}

}

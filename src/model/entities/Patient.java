package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Patient implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String telephone;

	public Patient(String name, String telephone) {
		this.name = name;
		this.telephone = telephone;
	}

	public String getName() {
		return name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, telephone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		return Objects.equals(name, other.name) && Objects.equals(telephone, other.telephone);
	}
	
	

}


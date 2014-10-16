package curso.and02.alumni.models;

import java.io.Serializable;

/**
 * 
 * @author pablo
 *
 */
public class Alumno implements Serializable {

	private static final long serialVersionUID = -5216523430903833503L;
	
	String name = "";
	String lastname = "";
	String groupo = "";
	String area = "";
	boolean is_present = false;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getGroupo() {
		return groupo;
	}
	
	public void setGroupo(String groupo) {
		this.groupo = groupo;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public boolean isIs_present() {
		return is_present;
	}
	
	public void setIs_present(boolean is_present) {
		this.is_present = is_present;
	}
	
	public String toString() {
		return "Alumno: " + this.name + " " + this.lastname + "\n Grupo: " + this.groupo;
	}
}

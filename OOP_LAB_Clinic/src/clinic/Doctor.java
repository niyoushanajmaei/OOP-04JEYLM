package clinic;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Doctor {
	private String first;
	private String last;
	private String ssn;
	private int id;
	private String specialization;
	private Collection<Patient> patients;
	
	public Doctor(String first, String last, String ssn, int id, String specialization) {
		this.first = first;
		this.last = last;
		this.ssn = ssn;
		this.id = id;
		this.specialization = specialization;
		patients = new LinkedList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	public void addPatient(Patient patient) {
		patients.add(patient);
	}
	
	public Collection<Patient> getPatients() {
		return patients;
	}
	
	public String desc() {
		return getId() + " " + getLast() + " " + getFirst();
	}
}

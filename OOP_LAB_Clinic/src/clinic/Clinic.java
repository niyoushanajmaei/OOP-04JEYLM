package clinic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {
	Map<String,Patient> patients = new LinkedHashMap<>();
	Map<Integer,Doctor> doctors = new LinkedHashMap<>();

	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		patients.put(ssn,new Patient(first,last,ssn));
	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		Patient p;
		if (patients.containsKey(ssn)) {
			p = patients.get(ssn);
			return p.getLast() + " " + p.getFirst() + " (" + p.getSsn() +")";
		}else{
			throw (new NoSuchPatient());
		}
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		doctors.put(docID, new Doctor(first,last,ssn,docID,specialization));
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		Doctor d;
		if (doctors.containsKey(docID)) {
			d = doctors.get(docID);
			return d.getLast() + " " + d.getFirst() + " (" + d.getSsn() +") [" + d.getId() + "]: "+d.getSpecialization();
		}else{
			throw (new NoSuchDoctor());
		}
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		Patient p;
		Doctor d;
		
		if (patients.containsKey(ssn)) {
			p = patients.get(ssn);
		}else {
			throw new NoSuchPatient();
		}
		if(doctors.containsKey(docID)) {
			d = doctors.get(docID);
		}else {
			throw new NoSuchDoctor();
		}
		
		p.setDoctor(d);
		d.addPatient(p);
	}
	
	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		Patient p;
		
		if (patients.containsKey(ssn)) {
			p = patients.get(ssn);
		}else {
			throw new NoSuchPatient();
		}
		if(p.getDoctor()!=null) {
			return p.getDoctor().getId();
		}else {
			throw new NoSuchDoctor();
		}
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		Doctor d;
		
		if(doctors.containsKey(id)) {
			d = doctors.get(id);
		}else {
			throw new NoSuchDoctor();
		}
		
		return d.getPatients().stream().collect(Collectors.mapping(Patient::getSsn, Collectors.toList()));
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and specialization.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param readed linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader) throws IOException {
		List<String> line = new ArrayList<>();
		String[] data;
		try {
			BufferedReader buffReader= new BufferedReader(reader);
			while (line.add(buffReader.readLine()) && line.get(line.size()-1)!=null) {
				//System.out.println(line.get(line.size()-1));
				try {
					data = line.get(line.size()-1).split(";");
					//System.out.println(data);
					if (data[0].trim().equals("P")) {
						addPatient(data[1].trim(),data[2].trim(),data[3].trim());
						//System.out.println("added patient");
					}else if (data[0].trim().equals("M")) {
						addDoctor(data[2].trim(),data[3].trim(),data[4].trim(),Integer.parseInt(data[1].trim()),data[5].trim());
						//System.out.println("added doctor");
					}
				}catch (Exception e) {
					System.out.println("Error on line: " + line.get(line.size()-1));
				}
			}
		}catch (IOException e) {
			System.out.println("IOException");
		}finally {
			reader.close();
		}
		return line.size()-1;
	}



	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method calls the
	 * {@link ErrorListener#offending} method passing the line itself,
	 * ignores the row, and skip to the next one.<br>

	 * 
	 * @param reader reader linked to the file to be read
	 * @param listener listener used for wrong line notifications
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader, ErrorListener listener) throws IOException {
		List<String> line = new ArrayList<>();
		String[] data;
		try {
			BufferedReader buffReader= new BufferedReader(reader);
			while (line.add(buffReader.readLine()) && line.get(line.size()-1)!=null) {
				//System.out.println(line.get(line.size()-1));
				try {
					data = line.get(line.size()-1).split(";");
					//System.out.println(data);
					if (data[0].trim().equals("P")) {
						addPatient(data[1].trim(),data[2].trim(),data[3].trim());
						//System.out.println("added patient");
					}else if (data[0].trim().equals("M")) {
						addDoctor(data[2].trim(),data[3].trim(),data[4].trim(),Integer.parseInt(data[1].trim()),data[5].trim());
						//System.out.println("added doctor");
					}
				}catch (Exception e) {
					listener.offending(line.get(line.size()- 1));
					System.out.println("Error on line: " + line.get(line.size()-1));
				}
			}
		}catch (IOException e) {
			System.out.println("IOException");
		}finally {
			reader.close();
		}
		return line.size()-1;
	}

		
	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		List<Integer> res = doctors.values().stream().filter(doctor -> doctor.getPatients() == null)
				.sorted(Comparator.comparing(Doctor::getLast).thenComparing(Doctor::getFirst))
				.collect(Collectors.mapping(doctor -> doctor.getId(),Collectors.toList()));
		return res;
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		double avg = findAveragePatients();
		List<Integer> res = doctors.values().stream().filter(doctor ->doctor.getPatients().size() > avg)
				.collect(Collectors.mapping(doctor -> doctor.getId(), Collectors.toList()));
		return res;
	}

	private double findAveragePatients() {
		int sum = doctors.values().stream()
				.collect(Collectors.summingInt(doctor -> doctor.getPatients().size()));
		double res = sum/doctors.size();
		//System.out.println(res);
		return res;
	}
	
	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		/*
		List<String> res = doctors.values().stream()
				.sorted(Comparator.comparing(doctor -> doctor.getPatients().size()).reversed())
				.collect(Collectors.toMap(doctor -> String.format("%3d", doctor.getPatients().size()),doctor -> doctor.Desc()))
				.entries().stream()
				.collect(Collectors.mapping(entry -> entry.getKey() + " : " + entry.getValue(),Collectors.toList()));
		return res;
		*/
		return null;
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
		// TODO Complete method
		return null;
	}
	
}

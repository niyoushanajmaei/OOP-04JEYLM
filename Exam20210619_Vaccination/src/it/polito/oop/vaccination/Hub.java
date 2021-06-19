package it.polito.oop.vaccination;

public class Hub {

	String name;
	int doc,nurse,other;

	public Hub(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDoc() {
		return doc;
	}

	public void setDoc(int doc) {
		this.doc = doc;
	}

	public int getNurse() {
		return nurse;
	}

	public void setNurse(int nurse) {
		this.nurse = nurse;
	}

	public int getOther() {
		return other;
	}

	public void setOther(int other) {
		this.other = other;
	}

	public int getCapacity() {
		int res = 10 *doc + 12 *nurse + 20*other; 
		return res;
	}
	
	
	
}

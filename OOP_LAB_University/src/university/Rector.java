package university;

class Rector {
	
	private String first_name;
	private String last_name;
	
	void setName(String first, String last) {
		first_name = first;
		last_name = last;
	}
	
	public String toString() {
		return first_name + " " +  last_name;
	}
}

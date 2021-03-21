package university;

class Course {
	private int code;
	private Teacher teacher;
	private String title;
	private Student[] attendees;
	private int nAttendees = 0;
	
	public Course() {
		teacher = new Teacher();
	}
	
	public Course(int code, String title, String teacher) {
		this.teacher = new Teacher();
		this.code =code;
		this.title = title;
		this.teacher.setName(teacher);
	}
	
	void setCode(int code) {
		this.code = code;
	}
	
	void setTeacher(String name) {
		this.teacher.setName(name);
	}
	
	void setTitle(String title) {
		this.title = title;
	}
	
	public String toString() {
		return code + "," +title+","+teacher.getName();
	}

	public void addAttendee(Student student) {
		int exists = 0;
		if (nAttendees==0) {
			attendees = new Student[100];
		}
		for (int i = 0;i<nAttendees && exists == 0;i++) {
			if(attendees[i].getID()==student.getID()) {
				exists =1 ;
			}
		}
		if (exists == 0) {
			attendees[nAttendees] = new Student(student.getID(),student.getFirstName(),student.getLastName());
			nAttendees++;
		}
	}

	public int getCode() {
		return code;
	}

	public int getNAttendees() {
		return nAttendees;
	}

	public Student[] getAttendees() {
		return attendees;
	}
	
	String getTitle() {
		return title;
	}
	
	String getTeacher() {
		return teacher.getName();
	}
	
}

package university;

class Course {
	private int code;
	private Teacher teacher;
	private String title;
	private Student[] attendees;
	private int nAttendees = 0;
	private int[] grades;
	int takenExams = 0;
	
	public Course() {
		teacher = new Teacher();
		attendees = new Student[100];
		grades = new int[100];
		for (int i = 0;i<100;i++) {
			grades[i] = -1;
		}
	}
	
	public Course(int code, String title, String teacher) {
		this.teacher = new Teacher();
		this.code =code;
		this.title = title;
		this.teacher.setName(teacher);
		attendees = new Student[100];
		grades = new int[100];
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

	public String average() {
		float sum = 0 ;
		String res = new String();
		for (int i =0; i<takenExams ;i++) {
			sum += grades[i];
		}
		if (takenExams == 0) {
			res = "No student has taken the exam in " + title;
		}else {
			res = "The average for the course " + title + " is: " + sum / takenExams;
		}
		return res;
	}

	public void setGrade(int grade) {
		grades[takenExams] = grade;
		takenExams++;
	}
	
}

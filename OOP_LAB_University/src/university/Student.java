package university;

class Student {
	
	private String first_name;
	private String last_name;
	private int id;
	private Course[] courses;
	private int nCourses = 0;
	private int[] grades;
	int takenExams = 0;
	float score = 0;
	
	public Student(int id,String first, String last) {
		this.id = id;
		first_name = first;
		last_name = last;
		courses = new Course[25];
		grades = new int[25];
		for (int i = 0;i<25;i++) {
			grades[i] = -1;
		}
	}
	
	public Student() {
		courses = new Course[25];
		grades = new int[25];
	}

	void setName(String first, String last) {
		first_name = first;
		last_name = last;
	}

	void setid(int id) {
		this.id = id;
	}
	
	int getID() {
		return id;
	}
	
	String getFirstName() {
		return first_name;
	}
	
	String getLastName() {
		return last_name;
	}
	
	public String toString() {
		return id + " " + first_name + " " + last_name;
	}

	public void addCourse(Course course) {
		int exists = 0;
		for (int i = 0;i<nCourses && exists == 0;i++) {
			if(courses[i].getCode()==course.getCode()) {
				exists =1 ;
			}
		}
		if (exists == 0) {
			courses[nCourses] = new Course(course.getCode(),course.getTitle(),course.getTeacher());
			nCourses++;
		}
		
	}

	public int getNCourses() {
		return nCourses;
	}

	public Course[] getCourses() {
		return courses;
	}

	public void setGrade(int grade) {
		grades[takenExams] = grade;
		takenExams++;
	}

	public String average() {
		float sum = 0 ;
		String res = new String();
		for (int i =0; i<takenExams ;i++) {
			sum+= grades[i];
		}
		if (takenExams == 0) {
			res = "Student " + id + " hasn't taken any exams.";
		}else {
			res = "Student " + id + " : " + sum / takenExams;
		}
		return res;
	}

	public float getScore() {
		return score;
	}

	public void setScore() {
		for (int i = 0;i<takenExams;i++) {
			score += grades[i];
		}
		score = (score/takenExams) + (takenExams/nCourses * 10);
	}
	
}

package university;

class Student {
	
	private String first_name;
	private String last_name;
	private int id;
	private Course[] courses;
	private int nCourses = 0;
	
	public Student(int id,String first, String last) {
		this.id = id;
		first_name = first;
		last_name = last;
	}
	
	public Student() {
	
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
		if (nCourses==0) {
			courses = new Course[25];
		}
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


}

package university;

import java.util.logging.Logger;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	
	private String name;
	private Rector rector;
	private Student[] students;
	private Course[] courses;
	private int enrolled = 0;
	private int nCourses = 0;
	private static Logger logger;

	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		this.name = name;
		rector = new Rector();
		students = new Student[1000];
	}
	
	/**
	 * Getter for the name of the university
	 * 
	 * @return name of university
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first
	 * @param last
	 */
	
	Student[] getStudents() {
		return students;
	}

	int getEnrolled() {
		return enrolled;
	}
	
	public void setRector(String first, String last){
		rector.setName(first,last);
	}
	
	/**
	 * Retrieves the rector of the university
	 * 
	 * @return name of the rector
	 */
	public String getRector(){
		return rector.toString();
	}
	
	/**
	 * Enrol a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * 
	 * @return unique ID of the newly enrolled student
	 */
	public int enroll(String first, String last){
		int id = 10000 + enrolled;
		students[enrolled] = new Student();
		students[enrolled].setName(first,last);
		students[enrolled].setid(id);
		enrolled ++;
		logger.info("New student enrolled: "+id+", "+first+" "+last);
		return id;
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the ID of the student
	 * 
	 * @return information about the student
	 */	
	public String student(int id){
		return findStudent(id).toString();
	}
	
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		int code = 10 + nCourses;
		if (nCourses == 0) {
			courses = new Course[50];
		}
		courses[nCourses] = new Course();
		courses[nCourses].setTitle(title);
		courses[nCourses].setTeacher(teacher);
		courses[nCourses].setCode(code);
		nCourses ++;
		logger.info("New course activated: "+code+", "+title+ " " +teacher);
		return code;
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code){
		return findCourse(code).toString();
	}
	
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		findStudent(studentID).addCourse(findCourse(courseCode));
		findCourse(courseCode).addAttendee(findStudent(studentID));
		logger.info("Student "+studentID +" signed up for course "+courseCode);
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		String res = new String("");
		Course course = findCourse(courseCode);
		for(int i=0;i<course.getNAttendees();i++) {
			res += course.getAttendees()[i].toString();
			res += '\n';
		}
		return res;
	}

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		String res = new String("");
		Student student = findStudent(studentID);
		for(int i=0;i<student.getNCourses();i++) {
			res += student.getCourses()[i].toString();
			res += '\n';
		}
		return res;
	}
	
	public Student findStudent(int id) {
		return students[id - 10000];
	}
	
	public Course findCourse(int code) {
		return courses[code-10];
	}

	public void useLogger(Logger logger) {
		this.logger = logger;
	}

	
}

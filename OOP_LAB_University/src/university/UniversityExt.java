package university;

import java.util.logging.Logger;

/**
 * This class is an extended version of the {@Link University} class.
 * 
 *
 */
public class UniversityExt extends University {

	private final static Logger logger = Logger.getLogger("University");

	public UniversityExt(String name) {
		super(name);
		// Example of logging
		logger.info("Creating extended university object");
	}

	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseID	course code 
	 * @param grade		grade ( 0-30)
	 */
	public void exam(int studentId, int courseID, int grade) {
		super.findStudent(studentId).setGrade(grade);
		super.findCourse(courseID).setGrade(grade);
		logger.info("Student "+studentId+" took an exam in course "+courseID+" with grade "+grade);
	}

	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 * 
	 * @param studentId the ID of the student
	 * @return the average grade formatted as a string.
	 */
	public String studentAvg(int studentId) {
		return super.findStudent(studentId).average();
	}

	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 * @param courseId	course code 
	 * @return the course average formatted as a string
	 */
	public String courseAvg(int courseId) {
		return super.findCourse(courseId).average();
	}

	/**
	 * Retrieve information for the best students to award a price.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info of the best three students.
	 */
	public String topThreeStudents() {
		Student[] students = super.getStudents();
		int enrolled = super.getEnrolled();
		Student tmp;
		String res = new String("");
		for (int i =0;i<enrolled;i++) {
			students[i].setScore();
		}
		for(int i = 0;i<enrolled ;i++) {
			for (int j = 0 ; j<i;j++) {
				if (students[j].getScore()>students[i].getScore()) {
					tmp = students[i];
					students[i] = students[j];
					students[j] = tmp;
				}
			}
		}
		if (enrolled >= 3) {
			for (int i = enrolled-1;i>=enrolled-3;i--) {
				if (students[i].getScore()!=-1) {
					res +=  students[i].getFirstName() +" "+ students[i].getLastName() + " : " + students[i].getScore() + "\n";
				}
			}
		}else {
			for (int i = enrolled-1;i>=0;i--) {
				if (students[i].getScore()!=-1)	
					res +=  students[i].getFirstName() +" "+ students[i].getLastName() + " : " + students[i].getScore() + "\n";
			}
		}
		return res;
	}

	@Override
	public int enroll(String first, String last){
		int id = 10000 + getEnrolled();
		getStudents()[getEnrolled()] = new Student();
		getStudents()[getEnrolled()].setName(first,last);
		getStudents()[getEnrolled()].setid(id);
		setEnrolled(getEnrolled()+1) ;
		logger.info("New student enrolled: "+id+", "+first+" "+last);
		return id;
	}

	@Override
	public int activate(String title, String teacher){
		int code = 10 + getnCourses();
		if (getnCourses() == 0) {
			setCourses(new Course[50]);
		}
		getCourses()[getnCourses()] = new Course();
		getCourses()[getnCourses()].setTitle(title);
		getCourses()[getnCourses()].setTeacher(teacher);
		getCourses()[getnCourses()].setCode(code);
		setnCourses(getnCourses() + 1);
		logger.info("New course activated: "+code+", "+title+ " " +teacher);
		return code;
	}

	@Override
	public void register(int studentID, int courseCode){
		findStudent(studentID).addCourse(findCourse(courseCode));
		findCourse(courseCode).addAttendee(findStudent(studentID));
		logger.info("Student "+studentID +" signed up for course "+courseCode);
	}

}

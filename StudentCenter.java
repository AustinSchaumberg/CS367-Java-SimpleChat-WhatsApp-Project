///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Student Center
// Files:            StudentCenter.java
// Semester:         Spring 2016
//
// Author:           Austin Schaumberg
// Email:            schaumberg2@wisc.edu
// CS Login:         schaumberg
// Lecturer's Name:  Deb Deppeler
// Lab Section:      367-002 (lecture)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
//                  CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     (name of your pair programming partner)
// Email:            (email address of your programming partner)
// CS Login:         (partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
//			NOT APPLICABLE
//
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * Student Center abstraction for our simulation. Execution starts here.
 * 
 * @author CS367
 *
 */
public class StudentCenter
{
	private static int DEFAULT_POINTS = 100;
	// Global lists of all courses and students
	private static List<Course> courseList = new ArrayList<Course>();
	private static List<Student> studentList = new ArrayList<Student>();

	public static void main(String[] args)
	{
		if(args.length != 3)
		{
			System.err.println("Bad invocation! Correct usage: " + 
					"java StudentCentre <StudentCourseData file>" + 
					"<CourseRosters File> "
					+ "+ <StudentCourseAssignments File>");
			System.exit(1);
		}
		boolean didInitialize = readData(args[0]);
		if(!didInitialize)
		{
			System.err.println("Failed to initialize the application!");
			System.exit(1);
		}
		generateAndWriteResults(args[1], args[2]);
	}

	/**
	 * 
	 * @param fileName
	 *            - input file. Has 3 sections - #Points/Student, #Courses, and
	 *            multiple #Student sections. See P3 page for more details.
	 * @return true on success, false on failure.
	 * 
	 */
	public static boolean readData(String fileName)
	{
		// create instance of the input file to be read over. 
		File studentCourseData = new File(fileName);
		try
		{
			// TODO parse the input file as described in the P3 specification.
			// make sure to call course.addStudent() appropriately.
			// initializes a scanner which will be used to parse through 
			// the input file's data.
			Scanner scnr = new Scanner(studentCourseData);
			// string variable will be used to capture header information, if 
			// parsed over by the scanner. 
			String pastScnrInfo="";
			// start of while-loop used to parse through the input file
			while(scnr.hasNextLine())
			{
				// clears the scanner info variable if necessary.
				String scnrInfo = "";
				// this makes way for the pastScnrInfo to be transfered over
				// to this variable if necessary.
				if(pastScnrInfo.contains("#"))
				{
					scnrInfo = pastScnrInfo;
				}
				// otherwise the scnrInfo uses the nextLine in the scanner
				// which is parsing the file.
				// these two conditionals are essentially used to catch/stop
				// the scanner from parsing over a header and continuing on 
				// reading the file's information. Instead the program now
				// stops and awaits the header data to be verified before 
				// continuing on reading the input file.
				else
				{
					scnrInfo = scnr.nextLine();
				}
				// clears pastScnrInfo.
				pastScnrInfo="";
				// determines if the header of the input file contains 
				// the student points attributed to each student.
				// if this is true, the scanner parses/trims the necessary
				// data from the file and sets the student coin amount.
				if(scnrInfo.contains("#Points/Student"))
				{
					String coins = scnr.nextLine();
					int coinAmnt = Integer.parseInt(coins.trim());
					DEFAULT_POINTS = coinAmnt;
				}
				// determines if the header of the input file contains 
				// the courses which will be available to register for.
				// if this is true, the scanner parses/trims the necessary
				// data (course name, course code, course max)
				// from the file and sets the course information, which
				// contains the course limit maximum amount for students 
				// who can attend the class, as well as the name of the course
				// and the course number. Finally adds the course to the 
				// list of courses.
				else if(scnrInfo.contains("#Courses"))
				{
					while(scnr.hasNextLine())
					{
						String coursesInfo = scnr.nextLine();
						// catches if header is the nextLine in scanner,
						// breaks loop, returns to top for re-evaluation
						if(coursesInfo.contains("#"))
						{
							pastScnrInfo = coursesInfo;
							break;
						}
						// parses info for courses, course name, courseMax
						// and course code.
						String coursesInfoTmp = coursesInfo.trim();
						String[] array = coursesInfoTmp.split(" ");
						int courseMax = Integer.parseInt(array[2]);
						Course course = new Course(array[0],array[1],courseMax);
						courseList.add(course);
					}
				}
				// determines if the header of the input file contains 
				// the student information which will be used to create
				// the course rosters, registrationQueues, etc.
				// if this is true, the scanner parses/trims the necessary
				// data (name, studentID, and given the default value of 
				// coins alloted to students who are registering for courses)
				// from there the file and creates a student, who 
				// has a set amount of course coins attributed to the 
				// necessary courses each would like to take.
				// the student is then added to the course's registration queue
				else if(scnrInfo.contains("#Student"))
				{
					// parses and trims data for the student name and ID
					String studentName = scnr.nextLine();
					String studentNameTmp = studentName.trim();
					String studentID = scnr.nextLine();
					String studentIDTmp = studentID.trim();
					Student student = new Student(studentNameTmp, studentIDTmp,
							DEFAULT_POINTS);
					studentList.add(student);
					// this while loop is kicked off after the student's info
					// is captured.
					// this while loop continues reading through the student's
					// data to register them for their desired courses.
					// in order of which they are processed from the file.
					while(scnr.hasNextLine())
					{						
						String courseInfo = scnr.nextLine();
						// catches if header is the nextLine in scanner,
						// breaks loop, returns to top for re-evaluation
						if(courseInfo.contains("#"))
						{
							pastScnrInfo = courseInfo;
							break;
						}
						// parse and trim data from input file, 
						// contains the desired courseCode, amount of coins
						// used as a priority basis
						String courseInfoTmp = courseInfo.trim();
						String tarray[] = courseInfoTmp.split(" ");
						String classCode = tarray[0];
						String courseCoinsStrg = tarray[1];
						int courseCoins = Integer.parseInt(courseCoinsStrg);
						getCourseFromCourseList(classCode).addStudent
						(student, courseCoins);
					}
				}
			}
			// closes scanner
			scnr.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("File Parse Error");
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param fileName1
	 *            - output file with a line for each course
	 * @param fileName2
	 *            - output file with a line for each student
	 */
	public static void generateAndWriteResults(String fileName1,
			String fileName2)
	{
		try
		{
			// List Students enrolled in each course
			PrintWriter writer = new PrintWriter(new File(fileName1));
			for (Course c : courseList)
			{
				writer.println("-----" + c.getCourseCode() + " " 
						+ c.getCourseName() + "-----");

				// Core functionality
				c.processRegistrationList();

				// List students enrolled in each course
				int count = 1;
				for (Student s : c.getCourseRegister())
				{
					writer.println(count + ". " + s.getid() + "\t" +
							s.getName());
					s.enrollCourse(c);
					count++;
				}
				writer.println();
			}
			writer.close();

			// List courses each student gets
			writer = new PrintWriter(new File(fileName2));
			for (Student s : studentList)
			{
				writer.println("-----" + s.getid() + " " + s.getName() +
						"-----");
				int count = 1;
				for (Course c : s.getEnrolledCourses())
				{
					writer.println(count + ". " + c.getCourseCode() + "\t"
							+ c.getCourseName());
					count++;
				}
				writer.println();
			}
			writer.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Look up Course from classCode
	 * 
	 * @param courseCode
	 * @return Course object
	 */
	private static Course getCourseFromCourseList(String courseCode)
	{
		for (Course c : courseList)
		{
			if(c.getCourseCode().equals(courseCode))
			{
				return c;
			}
		}
		return null;
	}
}

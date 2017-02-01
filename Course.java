///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Student Center
// Files:            Course.java
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

import java.util.*;

/**
 * Class to represent every Course in our Course Registration environment
 * 
 * @author CS367
 *
 */

public class Course
{
	// course code
	private String courseCode;
	// course name
	private String name;
	// Number of students allowed in the course
	private int maxCapacity;
	// Number of students enrolled in the course
	private int classCount;
	// The PriorityQueue structure
	private PriorityQueue<Student> registrationQueue;
	// List of students who are finally enrolled in the course
	private ArrayList<Student> courseRoster;

	public Course(String classCode, String name, int maxCapacity)
	{
		// TODO initialize all parameters
		this.courseCode = classCode;
		this.name = name;
		this.maxCapacity = maxCapacity;
		classCount=0;
		registrationQueue = new PriorityQueue<Student>();
		courseRoster = new ArrayList<Student>();
	}

	/**
	 * Creates a new PriorityqueueItem - with appropriate priority(coins) and
	 * this student in the item's queue. Add this item to the registrationQueue.
	 * 
	 * @param student
	 *            the student
	 * @param coins
	 *            the number of coins the student has
	 */
	public void addStudent(Student student, int coins)
	{
		// Enqueue a newly created PQItem.
		// Checking if a PriorityQueueItem with the same priority already exists
		// is done in the enqueue method.
		// if the student has enough points to spend on the course...
		if(student.getPoints()>=coins)
		{
			// then the student is deducted that coin amount
			student.deductCoins(coins);
			// the student then has the course added to their cart.
			student.addToCart(this);
			// new PQItem created with the coin amount setting their priority.
			PriorityQueueItem<Student> newStudent = 
					new PriorityQueueItem<Student>(coins);
			// puts the student's name/info into the PQItem
			newStudent.getList().enqueue(student);
			// adds the student to the registrationQueue
			registrationQueue.enqueue(newStudent);	
		}
	}

	/**
	 * Populates the courseRoster from the registration list.
	 * Use the PriorityQueueIterator for this task.
	 */
	public void processRegistrationList()
	{
		// TODO : populate courseRoster from registrationQueue
		// Use the PriorityQueueIterator for this task.
		Iterator<PriorityQueueItem<Student>> iter=registrationQueue.iterator();
		// beginning of while loop that will parse through the registration list
		// and enroll students into the course.
		while(iter.hasNext())
		{
			PriorityQueueItem<Student> newStud = iter.next();
			PriorityQueueItem<Student> newStudTemp = newStud;
			// dequeues the PriorityQueueItem's queue, enqueueing all of the 
			// students in order of time and priority value.
			while((!newStudTemp.getList().isEmpty())&&(classCount<maxCapacity))
			{
				Student dequeueItem = newStudTemp.getList().dequeue();
				// enrolls student into the course
				courseRoster.add(dequeueItem);
				// adds to class count of students
				classCount++;
			}
		}
	}

	/**
	 * returns course name
	 */
	public String getCourseName()
	{
		// TODO
		return this.name;
	}

	/**
	 * returns course code
	 */
	public String getCourseCode()
	{
		// TODO
		return this.courseCode;
	}

	/**
	 * returns course roster of students
	 */
	public List<Student> getCourseRegister()
	{
		// TODO
		return this.courseRoster;
	}
}

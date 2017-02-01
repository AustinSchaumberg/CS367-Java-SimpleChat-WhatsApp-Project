///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Student Center
// Files:            PriorityQueueItem.java
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
/**
 * 
 * Class to represent object stored at every entry in the PriorityQueue. ie, The
 * internal node structure of {@link PriorityQueue}
 * 
 * @author CS367
 *
 * @param <E>
 *            the generic type of the data content stored in the list
 */
public class PriorityQueueItem<E> implements Comparable<PriorityQueueItem<E>>
{

	private int priority;
	private Queue<E> queue;

	public PriorityQueueItem(int priority)
	{
		// TODO initialize variables
		queue = new Queue<E>();
		this.priority = priority;
	}

	public PriorityQueueItem() {
		// TODO Auto-generated constructor stub
		queue = this.queue;
		priority = this.priority;
	}

	public int getPriority()
	{
		// TODO
		return priority;
	}

	public Queue<E> getList()
	{
		// TODO
		return queue;
	}

	/**
	 * Add an item to the queue of this PriorityQueueItem object
	 * 
	 * @param item
	 *            item to add to the queue
	 */
	public void add(E item)
	{
		// TODO
		queue.enqueue(item);
	}

	/**
	 * Compares this Node to another node on basis of priority
	 * 
	 * @param o
	 *            other node to compare to
	 * @return -1 if this node's priority is lesser, +1 if this nodes priority
	 *         is higher after, else 0 if priorities are the same.
	 */
	@Override
	public int compareTo(PriorityQueueItem<E> o)
	{
		// TODO
		//		if(this == null||o==null)
		//		{
		//			throw new NullPointerException();
		//		}
		if(this == null||o==null)
		{
			if(this == null)
			{
				if(o == null)
					return 0; //equal
				else
					return -1; // null is before other strings
			}
			else
			{ // o != null
				if(o == null)
					return 1;  // all other strings are after null
				else
					return this.compareTo(o);
			}
		}
		if(this.priority<o.priority)
		{
			return -1;
		}
		else if(this.priority>o.priority)
		{
			return +1;
		}
		else
			return 0;
	}
}

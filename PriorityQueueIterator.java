///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Student Center
// Files:            PriorityQueueIterator.java
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
import java.lang.reflect.Array;
import java.util.*;

public class PriorityQueueIterator<T> implements Iterator<PriorityQueueItem<T>>
{
	private PriorityQueue<T> priorityQueue;
	
	/**
	 * Constructs a PriorityQueueIterator by utilizing a copy of the
	 * PriorityQueue. Hint : The local priorityQueue object need not be
	 * preserved, and hence you can use the dequeue() operation.
	 * 
	 * @param pq
	 */
	public PriorityQueueIterator(PriorityQueue<T> pq)
	{
		// TODO
		// This copies the contents of the passed parameter to the local object.
		// Hint : see copy constructor in PriorityQueue
		this.priorityQueue = new PriorityQueue(pq);
	}

	/**
	 * Returns true if the iteration has more elements.
	 * 
	 * @return true/false
	 */
	@Override
	public boolean hasNext()
	{
		// TODO
		if(this.priorityQueue.size()>0)
		{
			return true;	
		}
		else
			return false;
	}

	/**
	 * Returns the next element in the iteration. The iterator should return the
	 * PriorityQueueItems in order of decreasing priority.
	 * 
	 * @return the next element in the iteration
	 * @throws NoSuchElementException
	 *             if the iteration has no more elements
	 */
	@Override
	public PriorityQueueItem<T> next()
	{
		// TODO
		if(!this.hasNext())
		{
			// returns blank PQItem if there are no items left to return
			return new PriorityQueueItem<T>();
		}
		// dequeue's the top item from the PQ copied by the iterator
		// and returns the item removed from the PQ.
		return this.priorityQueue.dequeue();
	}

	/**
	 * Unsupported in this iterator for this assignment
	 */
	@Override
	public void remove()
	{
		// Do not implement
		throw new UnsupportedOperationException();
	}
}

///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Student Center
// Files:            Queue.java
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
 * An ordered collection of items, where items are added to the rear and removed
 * from the front.
 */
public class Queue<E> implements QueueADT<E>
{

	// TODO
	// You may use a naive expandable circular array or a chain of listnodes.
	// You may NOT use Java's predefined classes such as ArrayList or
	// LinkedList.
	private E[] items;
	private int numItems;
	private int rearIndex;
	private int frontIndex;
	private static final int INITSIZE = 100;

	public Queue()
	{
		items=(E[]) (new Object[INITSIZE]);
		numItems=0;
		rearIndex=0;
		frontIndex=1;
	}

	/**
	 * Adds an item to the rear of the queue.
	 * 
	 * @param item
	 *            the item to add to the queue.
	 * @throws IllegalArgumentException
	 *             if item is null.
	 */
	public void enqueue(E item)
	{
		// check for full array and expand if necessary
		if(items.length==numItems)
		{
			expandCapacity();
		}
		// check for null item, throw exception
		if(item == null)
		{
			throw new IllegalArgumentException();
		}

		//use auxilary method to increment rear index with wrap around
		rearIndex = incrementIndex(rearIndex);
		//insert new item at rear of queue
		items[rearIndex] = item;
		// increment numItems
		numItems++;
	}



	/**
	 * Removes an item from the front of the Queue and returns it.
	 * 
	 * @return the front item in the queue.
	 * @throws EmptyQueueException
	 *             if the queue is empty.
	 */
	public E dequeue()
	{
		// checks if empty queue
		if(numItems<=0)
		{
			throw new EmptyQueueException();
		}
		// creates temp object with item to be dequeued
		E tmp = items[frontIndex];
		// increment frontIndex
		frontIndex = incrementIndex(frontIndex);
		// decrement numItems
		numItems--;
		// return item dequeued
		return tmp;
	}

	/**
	 * Returns the item at front of the Queue without removing it.
	 * 
	 * @return the front item in the queue.
	 * @throws EmptyQueueException
	 *             if the queue is empty.
	 */
	public E peek()
	{
		// checks for empty queue, throws exception
		if(numItems ==0)
		{
		throw new EmptyQueueException();	
		}
		// returns item at the front of the queue
		else
		return items[frontIndex];
	}

	/**
	 * Returns true iff the Queue is empty.
	 * 
	 * @return true if queue is empty; otherwise false.
	 */
	public boolean isEmpty()
	{
		if(numItems == 0)
		{
			return true;
		}
		else
		return false;
	}

	/**
	 * Removes all items in the queue leaving an empty queue.
	 */
	public void clear()
	{
		while(!(items.length==0))
		{
			dequeue();
		}
		frontIndex = 1;
		rearIndex = 0;
	}

	/**
	 * Returns the number of items in the Queue.
	 * 
	 * @return the size of the queue.
	 */
	public int size()
	{	
		return numItems;
	}

	private void expandCapacity()
	{
		//expanding should be done using the naive copy-all-elements approach
		// create temporary array
		E[]tmp = (E[])(new Object[items.length*2]);
		// copy array items to tmp array
		System.arraycopy(items, frontIndex, tmp, frontIndex,
				items.length-frontIndex);
		// checks if front index is not 0, re-performs copy
		if(frontIndex!=0){
			System.arraycopy(items, 0, tmp, items.length, frontIndex);
		}
		items = tmp;
		rearIndex = frontIndex+numItems-1;
	}
	
	private int incrementIndex(int index)
	{
		//		return (inx + index.length) % index.length;
		if(index==items.length-1)
		{
			return 1;
		}
		else
			return index+1;

	}
}

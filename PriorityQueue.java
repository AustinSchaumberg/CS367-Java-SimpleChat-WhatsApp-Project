///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Student Center
// Files:            PriorityQueue.java
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
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * PriorityQueue implemented as a Binary Heap backed by an array. Implements
 * QueueADT. Each entry in the PriorityQueue is of type PriorityQueueNode<E>.
 * First element is array[1]
 * 
 * @author CS367
 *
 * @param <E>
 */
public class PriorityQueue<E> implements QueueADT<PriorityQueueItem<E>>
{
	//sets default array capacity
	private final int DEFAULT_CAPACITY = 100;
	//used for declaring top of the PQHeap
	private static final int TOP = 1;
	// Number of elements in heap
	private int currentSize;

	/**
	 * The heap array. First element is array[1]
	 */
	private PriorityQueueItem<E>[] array;

	/**
	 * Construct an empty PriorityQueue.
	 */
	public PriorityQueue()
	{
		// initialize currentSize to 0
		currentSize = 0;
		// the below code initializes the array.. similar code used for
		// expanding
		array = (PriorityQueueItem<E>[]) Array
				.newInstance(PriorityQueueItem.class, DEFAULT_CAPACITY + 1);
	}

	/**
	 * Copy constructor
	 * 
	 * @param pq
	 *            PriorityQueue object to be copied
	 */
	public PriorityQueue(PriorityQueue<E> pq)
	{
		//creates a copy of this PriorityQueue's size  
		this.currentSize = pq.currentSize;
		// creates copy of this PriorityQueue's array of PriorityQueueItems
		this.array = Arrays.copyOf(pq.array, currentSize + 1);
	}

	/**
	 * Adds an item to this PriorityQueue. If array is full, double the array
	 * size.
	 * 
	 * @param item
	 *            object of type PriorityQueueItem<E>.
	 */
	@Override
	public void enqueue(PriorityQueueItem<E> item)
	{
		//throws an illegal argument exception if the item input to the queue
		//method is null.
		if(item==null)
		{
			throw new IllegalArgumentException();
		}
		// TODO write appropriate code
		// Check if array is full - double if necessary
		if(currentSize==array.length)
		{
			doubleArray();
		}
		// Check all nodes and find if one with equal priority exists.
		// Add to the existing node's list if it does
		// boolean value 'exists' used to find out whether or not the item
		// being enqueued into this PQ currently exists in the PQ
		boolean exists = false;
		// itemDequeue is item of generic type used to store an object which
		// will eventually be dequeued from the input 'item' PQItem if the
		// item being enqueued is found to currently exist within the PQ.
		E itemDequeue = null;
		if(!exists)
		{
			// sets object iter to iterate through this PriorityQueue
			Iterator<PriorityQueueItem<E>> iter = iterator();
			// while the iterator has next
			while(iter.hasNext())
			{
				// iterator's next object is set and compared to the 
				// current item attempting to be enqueued in the statement
				// below.
				PriorityQueueItem<E> item2 = iter.next();
				// boolean value used as a trigger for unloading related 
				// objects from the current PQItem to the existing PQItem
				// with equivalent Priorities.
				boolean itemEmpty = item.getList().isEmpty();
				if(!itemEmpty)
				{
					if((item.getPriority()==item2.getPriority()))
					{
						itemDequeue = item.getList().dequeue();
						item2.getList().enqueue(itemDequeue);
						// sets exists to true, which would effectively 
						// skip the if statement after this.
						exists=true;
						// breaks out of while-loop
						break;
					}
				}
			}
		}
		// if the boolean value from above is still false, meaning an item with
		// equivalent priority was not found in the Queue. The program
		// continues to this conditional statement.
		// Create news node with value added to list and percolate
		// it up
		if(!exists)
		{
			// percolate up and add item
			add(item);
		}
		buildHeap();
	}

	/**
	 * Returns the number of items in this PriorityQueue.
	 * 
	 * @return the number of items in this PriorityQueue.
	 */
	public int size()
	{
		// TODO write appropriate code
		// returns size of heap
		return currentSize;
	}

	/**
	 * Returns a PriorityQueueIterator. The iterator should return the
	 * PriorityQueueItems in order of decreasing priority.
	 * 
	 * @return iterator over the elements in this PriorityQueue
	 */
	public Iterator<PriorityQueueItem<E>> iterator()
	{
		// TODO write appropriate code - see PriortyQueueIterator constructor
		// returns an iterator with a copy of this exact PriorityQueue.
		return new PriorityQueueIterator(this);
	}

	/**
	 * Returns the largest item in the priority queue.
	 * 
	 * @return the largest priority item.
	 * @throws NoSuchElementException
	 *             if empty.
	 */
	@Override
	public PriorityQueueItem<E> peek()
	{
		// TODO fill in appropriate code, replace default return statement
		// checks if PQ is empty
		if(this.isEmpty())
		{
			throw new NoSuchElementException();
		}
		// else returns the top item of the priority queue
		else
			return array[TOP];
	}

	/**
	 * Removes and returns the largest item in the priority queue. Switch last
	 * element with removed element, and percolate down.
	 * 
	 * @return the largest item.
	 * @throws NoSuchElementException
	 *             if empty.
	 */
	@Override
	public PriorityQueueItem<E> dequeue()
	{
		// TODO
		// if size is 0, the dequeue returns a new PQItem, since no others
		// are available or exist in PQ.
		if(currentSize==0)
		{
			throw new NoSuchElementException();
		}
		// create an PQItem which saves the top most PQItem of the PQ. 
		PriorityQueueItem<E> popGoesTheItem = array[TOP];
		// Remove first element
		// Replace with last element, percolate down
		// decrement currentSize
		array[TOP] = array[currentSize--]; 
		buildHeap();
		// return item removed
		return popGoesTheItem;
	}

	/**
	 * Heapify Establish heap order property from an arbitrary arrangement of
	 * items. ie, initial array that does not satisfy heap property. Runs in
	 * linear time.
	 */
	private void buildHeap()
	{
		for (int i = currentSize / 2;i > 0;i--)
		{
			percolateDown(i);
		}

		if(currentSize>=2 && array[TOP].compareTo(array[2])<0)
		{
			swap(TOP, 2);
		}
	}

	/**
	 * Make this PriorityQueue empty.
	 */
	public void clear()
	{
		// TODO write appropriate code
		// while the PQ is not empty, dequeue item in the PQ
		while(!this.isEmpty())
		{
			dequeue();
		}
	}

	/**
	 * Internal method to percolate down in the heap. <a
	 * href="https://en.wikipedia.org/wiki/Binary_heap#Extract">Wiki</a>}
	 * 
	 * @param hole
	 *            the index at which the percolate begins.
	 */
	private void percolateDown(int hole)
	{
		// TODO
		// boolean variable used to see if the first condition was met,
		// if not moves to the next condition
		boolean done = false;
		// checks if the position of hole is a branch of the binary tree being
		// created for the heap.
		if (!isBranch(hole))
		{ 
			// if the position of 'hole' is not a branch, next checks the
			// if the conditions: if the array item's priority
			// at the hole position is less than the item's left or right 
			// child priority status.
			if ( array[hole].compareTo(array[lChild(hole)]) <0||
					array[hole].compareTo(array[rChild(hole)])<0)
			{
				// next the condition checks if the item in the left child
				// has a higher priority than the right child
				if (array[lChild(hole)].getPriority()>array[rChild(hole)]
						.getPriority())
				{
					// if true, swap item at hole position in array of PQItems
					// with item's left child (which swaps the two items in the
					// tree)
					swap(hole, lChild(hole));
					// percolate the left to make sure all children of this
					// child follow the same hierarchy. 
					percolateDown(lChild(hole));
				}
				else
				{
					// if true, swap item at hole position in array of PQItems
					// with item's right child (which swaps the two items in the
					// tree)
					swap(hole, rChild(hole));
					// percolate the right child to make sure all children of 
					// this child follow the same hierarchy. 
					percolateDown(rChild(hole));
				}
				// done = true; moved out to the final if statement to see if
				// prevents shifting.
				done = true;
			}
		}
		// if the value above is a branch, then this portion occurs. 
		else if(!done)
		{
			int child = currentSize;
			while(!done)
			{
				//inside loop, changes for swaps
				// parent method performs simple arithmetic stated below in
				// this class.
				int parent = parent(child);
				//checks for errors such as 2/2 or 1/2
				if(parent==0)
				{
					//breaks out of while loop, by setting to true.
					done = true;
				}
				// if parent value is larger than the child's priority 
				// value stop loop
				else if(array[parent].compareTo(array[lChild(parent)])>0
						||array[parent].compareTo(array[rChild(parent)])>0)
				{
					done=true;
				}
				else
				{
					if (array[lChild(parent)].getPriority()>
					array[rChild(parent)].getPriority())
					{
						// if true, swap item at hole position 
						// in array of PQItems with item's left child
						// (which swaps the two items in the tree)
						swap(parent, lChild(parent));
						// percolate the left to make sure all children of this
						// child follow the same hierarchy. 
						percolateDown(lChild(parent));
						//increment child to parent val
					}
					else
					{
						// if true, swap item at hole position 
						// in array of PQItems with item's left child
						// (which swaps the two items in the tree)
						swap(parent, rChild(parent));
						// percolate the right to make sure all children of this
						// child follow the same hierarchy. 
						percolateDown(rChild(parent));
						//increment child to parent value
					}
					child=parent;
				}
			}
		}

	}

	/**
	 * Internal method to expand array.
	 */
	private void doubleArray()
	{
		PriorityQueueItem<E>[] newArray;

		newArray = (PriorityQueueItem<E>[])
				Array.newInstance(PriorityQueueItem.class, array.length * 2);
		for (int i = 0;i < array.length;i++)
			newArray[i] = array[i];
		array = newArray;
	}

	@Override
	public boolean isEmpty()
	{
		if(currentSize == 0)
			return true;
		return false;
	}

	/**
	 * Internal method for obtaining parent value.
	 */
	private int parent(int pos)
	{
		return pos / 2;
	}

	/**
	 * Internal method for obtaining left child value for BTS.
	 */
	private int lChild(int pos)
	{
		return (2 * pos);
	}

	/**
	 * Internal method for obtaining right child value for BTS.
	 */
	private int rChild(int pos)
	{
		return (2 * pos) + 1;
	}

	/**
	 * Internal boolean method for checking if item is branch
	 */
	private boolean isBranch(int pos)
	{
		if (pos>=(currentSize/2)  &&  pos<=currentSize)
		{
			return true;
		}
		return false;
	}

	/**
	 * Internal method for swapping 2 items in a PriorityQueue 
	 */
	private void swap(int firstpos,int nextpos)
	{
		PriorityQueueItem<E> tmp;
		tmp = array[firstpos];
		array[firstpos] = array[nextpos];
		array[nextpos] = tmp;
	}

	/**
	 * Internal method for percolating up
	 */
	private int percolateUp(int hole, PriorityQueueItem<E> item)
	{
		while (hole != 1 && array[hole/2].compareTo(item) > 0) 
		{
			array[hole] = array[hole/2];         // shift parent down
			hole = hole/2;
		}
		return hole;
	}

	/**
	 * Internal method for percolate up/add to queue 
	 */
	private boolean add(PriorityQueueItem<E> item)
	{
		int hole = percolateUp( ++currentSize, item );
		array[hole] = item;
		return true;
	}
}
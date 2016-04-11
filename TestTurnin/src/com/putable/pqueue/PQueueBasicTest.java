package com.putable.pqueue;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class PQueueBasicTest {

	PQueue basicPQ;
	int currentSize;
	
	public class Thing extends AbstractPQAble{

		private int whenToExecute;
		
		public Thing() {
			
		}
		public Thing(int whenToExecute) {
			this.whenToExecute = whenToExecute;
		}
		
		@Override
		public int compareTo(PQAble arg0) {
			if (arg0 == null) 
				throw new NullPointerException();
			Thing comparator = (Thing) arg0;
			if (this.whenToExecute == comparator.whenToExecute)
				return 0;
			else if (this.whenToExecute < comparator.whenToExecute)
				return 1;
			
			return -1;
		}
		
		public int getWhenToExecute() {
			return whenToExecute;
		}
		public void setWhenToExecute(int number) {
			this.whenToExecute = number;
		}

	}
	
	@Before
	public void setUp() throws Exception {
		this.basicPQ = new PQueueBasic();
	}
	
	/**
	 * Makes a list of valid (fully implemented) PQAble 
	 * objects of length size.
	 * @param size The number of objects in the list.
	 * @return  the List<PQAble> that was created.
	 */
	
	private List<PQAble> makeValidList(int size,int random) {
		List<PQAble> items = new ArrayList<PQAble>(size);
		Random rand = new Random();
		rand.setSeed(System.nanoTime());
		
		for (int i = 0; i < size; i++) {
			if (random == 0)
				items.add(new Thing(10));
			else
				items.add(new Thing(rand.nextInt(100)));
		}
		
		return items;
	}
	
	/**
	 * Takes a list of PQAble objects and adds them to the 
	 * PQueue being tested.
	 * @param items
	 * 			List<PQAble> of items to be added to the PQueue.
	 */
	private void loadPQ (List<PQAble> items) {
		PQueueBasic basePQ = (PQueueBasic) basicPQ;
		PQAble[] PQArray = basePQ.getHeapArray();
		if (basePQ.getCurrentSize() == 1) {
			PQArray[0] = new Thing(-1);
			PQArray[0].setPQueue(basicPQ);
		}
		for (int i = 0; i < items.size(); i++) {
			
			basicPQ.insert(items.get(i));
		}
	}
	/**
	 * Checks if the given array follows the heap order property.
	 * Specifically with array[0] being high priority but not data.
	 * the first entry is array[1]
	 * @param array the array of PQAble objects to be tested 
	 * @return true if the array follows the heap order property.
	 */
	private boolean isHeap(PQAble[] array) {
		if (array == null)
			return false;
		
		return isHeapTree(array, 1);
	}
	private boolean isHeapTree(PQAble[] array, int i) {
		if (i > currentSize-1 || i*2+1 >=currentSize)
			return true;
		else if (array[i].compareTo(array[i*2])>=0 && array[i].compareTo(array[i*2+1])>=0)
            return (isHeapTree(array, 2*i ) && isHeapTree(array, 2*i + 1));
        else
            return false;
	}
	private PQAble[] getHeapArray(PQueue PQ) {
		PQueueBasic basePQ = (PQueueBasic) PQ;
		PQAble[] heapArray = basePQ.getHeapArray();
		currentSize = basePQ.getCurrentSize();
		return heapArray;
	}

	/**
	 * This will test loading a PQ with the indicated size 100 times.
	 * The number random controls whether or not the priorities are all
	 * going to be the same or random numbers.
	 * @param size
	 * @param random
	 * @throws Exception
	 */
	private void loadAndTestForHeapProperty(int size, int random) throws Exception {
		for (int i = 0; i < 100; i++) {
			setUp();
			loadPQ(makeValidList(size,random));
			boolean isHeap = isHeap(getHeapArray(basicPQ));
			assertTrue(isHeap);
		}
	}
	/**
	 * Loads a PQ with the size number of PQAbles. Then removes one and tests
	 * if the removed is the same as the top and if the new heapArray still 
	 * follows the heap order property.
	 * @param size  The number of PQAbles to be added to the PQueue.
	 * @param random  Determines if the priority is random or all the same.
	 * @throws Exception
	 */
	private void loadAndRemoveTest (int size, int random) throws Exception {
		for (int i = 0; i < 100; i++) {
			setUp();
			loadPQ(makeValidList(size,random));
			assertTrue(isHeap(getHeapArray(basicPQ)));
			PQAble topPrio = basicPQ.top();
			PQAble removed = basicPQ.remove();
			assertEquals(topPrio,removed);
			assertTrue(isHeap(getHeapArray(basicPQ)));
			if (size == 0) // nothing is removed. null is returned for both
				assertTrue(currentSize == size +1);
			else 
				assertTrue(currentSize == size);
		}
	}
	/**
	 * Loads a basic PQueue with size number of PQAble objects. Then removes one
	 * and adds a new one. Testing for proper removal and insertion.
	 * @param size
	 * @param random
	 * @throws Exception
	 */
	private void loadRemoveReload(int size, int random) throws Exception {
		for (int i = 0; i < 100; i++) {
			setUp();
			loadPQ(makeValidList(size,random));
			basicPQ.remove();
			PQAble toAdd = new Thing(10);
			basicPQ.insert(toAdd);
			assertTrue(isHeap(getHeapArray(basicPQ)));
			if (size ==0)
				assertTrue(currentSize == size+2);
			else
				assertTrue(currentSize == size+1);
		}
	}
	/**
	 * Loads a PQueue with size PQAble objects then removes all of them.
	 * Testing for heap property and proper removal every time.
	 * @param size
	 * @param random
	 * @throws Exception
	 */
	private void removeAll(int size, int random) throws Exception{
		setUp();
		loadPQ(makeValidList(size,random));
		for (int i = 0; i < size; i++) {
			PQAble topPrio = basicPQ.top();
			PQAble removed = basicPQ.remove();
			assertEquals(topPrio,removed);
			assertTrue(isHeap(getHeapArray(basicPQ)));
		}
		getHeapArray(basicPQ);
		assertTrue(currentSize == 1);
	}
	
	@Test
	public void testCorrectInsert() throws Exception {
		loadAndTestForHeapProperty(10,0);
		loadAndTestForHeapProperty(121,0);
		loadAndTestForHeapProperty(10,1);
		loadAndTestForHeapProperty(121,1);
		
		loadAndTestForHeapProperty(0,0);
		loadAndTestForHeapProperty(0,1);
	}
	
	@Test
	public void testBasicRemove() throws Exception {
		loadAndRemoveTest(10,1);
		loadAndRemoveTest(120,1);
		loadAndRemoveTest(10000,1);
		loadAndRemoveTest(10,0);
		loadAndRemoveTest(120,0);
		//test for some odd sizes also.
		loadAndRemoveTest(117,0);
		loadAndRemoveTest(421,1);
		loadAndRemoveTest(81,1);
	}
	
	@Test
	public void testRandomSizes() throws Exception {
		//Try to catch any special size that could be a problem.
		for (int i = 0; i<200; i++) {
			loadAndRemoveTest(i,1);
		}
	}
	
	@Test
	public void testLoadRemoveReload() throws Exception {
		for (int i = 0; i < 110; i++ ) {
			loadRemoveReload(i,1);
		}
	}
	
	@Test
	public void testRemoveAll() throws Exception{
		for (int i =0; i < 200; i++) {
			removeAll(i,1);
		}
	}
	
	@Test
	public void testInsertMillionRandomPrio() throws Exception {
		//loadAndTestForHeapProperty(1000000,1);
	}
	
}

package com.putable.pqueue;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class PQueueBasicImplTest {

	PQueue basicPQ;
	int currentSize;
	
	@Before
	public void setUp() throws Exception {
		this.basicPQ = new PQueueBasicImpl();
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
	private boolean isHeap(Thing[] array) {
		if (array == null)
			return false;
		
		return isHeapTree(array, 1);
	}
	private boolean isHeapTree(Thing[] array, int i) {
		if (i >= currentSize-1 || i*2 >=currentSize || i*2 +1 >=currentSize)
			return true;
		else if (array[i].compareTo(array[2*i]) == 0 && array[i].compareTo(array[2*i + 1]) == 0 ||
				array[i].compareTo(array[2*i]) == 2 && array[i].compareTo(array[2*i + 1]) == 2 ||
				array[i].compareTo(array[2*i]) == 0 && array[i].compareTo(array[2*i + 1]) == 2 ||
				array[i].compareTo(array[2*i]) == 2 && array[i].compareTo(array[2*i + 1]) == 0)
            return (isHeapTree(array, 2*i ) && isHeapTree(array, 2*i + 1));
        else
            return false;
	}
	private Thing[] getHeapArray(PQueue PQ) {
		PQueueBasicImpl basePQ = (PQueueBasicImpl) PQ;
		Thing[] heapArray = (Thing[]) basePQ.heapArray;
		currentSize = basePQ.currentSize;
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
	private void loadAndRemoveTest (int size, int random) throws Exception {
		for (int i = 0; i < 100; i++) {
			setUp();
			loadPQ(makeValidList(size,random));
			getHeapArray(basicPQ);
			PQAble topPrio = basicPQ.top();
			PQAble removed = basicPQ.remove();
			assertEquals(topPrio,removed);
			assertTrue(isHeap(getHeapArray(basicPQ)));
			assertTrue(currentSize == size);
		}
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
	}
	
	@Test
	public void testInsertMillionRandomPrio() throws Exception {
		//loadAndTestForHeapProperty(1000000,1);
	}
	
}

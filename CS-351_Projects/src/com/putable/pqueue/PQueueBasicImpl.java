package com.putable.pqueue;

public class PQueueBasicImpl implements PQueue{

	private int arraySize = 100;
	public PQAble[] heapArray = new Thing[arraySize];
	public int currentSize = 1;
	 
	public PQueueBasicImpl() {
		this.heapArray[0] = new Thing(-1);
	}
	
	@Override
	public PQAble remove() {
		PQAble highestPriority = null;
		if (heapArray[1] != null) {
			highestPriority = heapArray[1];
			heapArray[1] = heapArray[currentSize-1];
			heapArray[1].setIndex(1);
			heapArray[currentSize-1] = null;
			currentSize--;
			heapifyDown(1);
		}
		
		return highestPriority;
	}

	@Override
	public PQAble top() {
		return heapArray[1];
	}

	@Override
	public void insert(PQAble p) {
		if (p.getPQueue() != null)
			throw new IllegalStateException();
		else 
			p.setPQueue(this);
		//If the size has reached the full size
		//of the heapArray double the size of the 
		//heap array.
		if (currentSize == arraySize)
			increaseArraySize();
		
		p.setIndex(currentSize);
		heapArray[currentSize] = p;
		heapifyUp(p);
		currentSize++;
	}

	@Override
	public void delete(PQAble p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int size() {
		return currentSize - 1;
	}

	@Override
	public boolean isAdvanced() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Takes the new node and compares it to 
	 * its parent recursively until it is at its
	 * proper place in the heap based on its priority
	 */
	private void heapifyUp(PQAble newNode) {
		PQAble parent = heapArray[newNode.getIndex()/2];
		
		if (newNode.compareTo(parent) == 1)
			return;
		if (newNode.compareTo(parent) == 0) {
			swapPQAble(newNode,parent);
			heapifyUp(newNode);
		}
		
	}
	private void heapifyDown(int index) {
		if (index*2 >= currentSize)
			return;
		
		PQAble highestPriorityChild = checkChildren(heapArray[index*2],
				heapArray[index*2+1]);
		
		if (highestPriorityChild == null || heapArray[index].compareTo(highestPriorityChild)==0) {
			return;
		}
		int highIndex = highestPriorityChild.getIndex();
		swapPQAble(highestPriorityChild, heapArray[index]);
		
		heapifyDown(highIndex);
	}
	private PQAble checkChildren(PQAble lChild, PQAble rChild) {
		if (lChild == null && rChild == null) 
			return null;
		else if (lChild != null && rChild == null)
			return lChild;
		if (lChild.compareTo(rChild) == 2) {
			return lChild;
		}else if (lChild.compareTo(rChild) == 0){
			return lChild;
		}else
			return rChild;	
	}
	private void swapPQAble(PQAble highP, PQAble lowP) {
		int highIn = highP.getIndex();
		int lowIn  = lowP.getIndex();
		PQAble temp = highP;
		heapArray[highIn] = lowP;
		lowP.setIndex(highIn);
		temp.setIndex(lowIn);
		heapArray[lowIn] = temp;
		
	}
	
	/**
	 * This method takes the old array and makes a new one
	 *  double its size and copies the old array into the new
	 *  bigger one.
	 */
	private void increaseArraySize() {
		arraySize = arraySize * 2;
		PQAble[] biggerArray = new Thing[arraySize];
		
		for (int i = 0; i < arraySize/2; i++) {
			biggerArray[i] = heapArray[i];
		}
		heapArray = biggerArray;
	}
	
}

package com.putable.pqueue;

/**
 * This is a basic PQueue. It will insert and remove the highest priority item
 * in amortized log n time (where n is the current size).
 * 
 * @author michaelsmith
 * 
 */

public class PQueueAdvanced implements PQueue {

	private int arraySize = 100;
	private PQAble[] heapArray = new PQAble[arraySize];
	private int currentSize = 1;

	public PQueueAdvanced() {

	}

	@Override
	public PQAble remove() {
		PQAble highestPriority = null;
		if (heapArray[1] != null) {
			highestPriority = heapArray[1];
			highestPriority.setPQueue(null);
			highestPriority.setIndex(0);
			heapArray[1] = heapArray[currentSize - 1];
			heapArray[1].setIndex(1);
			heapArray[currentSize - 1] = null;
			heapifyDown(1);
			currentSize--;
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
		// If the size has reached the full size
		// of the heapArray double the size of the
		// heap array.
		if (currentSize == arraySize)
			increaseArraySize();

		p.setIndex(currentSize);
		heapArray[currentSize] = p;
		heapifyUp(p);
		currentSize++;
	}

	@Override
	public void delete(PQAble p) {
		if (p.getPQueue() != this)
			throw new IllegalStateException();
		if (heapArray[p.getIndex()] != p)
			throw new IllegalStateException();
		int index = p.getIndex();

		heapArray[index].setPQueue(null);
		if (index == currentSize - 1) {
			heapArray[index] = null;
			currentSize--;
			return;
		}
		// takeOutOfPq(index);
		heapArray[index] = heapArray[currentSize - 1];
		heapArray[index].setIndex(index);
		heapArray[currentSize - 1] = null;
		heapifyUp(heapArray[index]);
		heapifyDown(index);
		currentSize--;
	}

	@Override
	public int size() {
		return currentSize - 1;
	}

	@Override
	public boolean isAdvanced() {
		return true;
	}

	/**
	 * Takes the new node and compares it to its parent recursively until it is
	 * at its proper place in the heap based on its priority
	 */
	private void heapifyUp(PQAble newNode) {
		if (newNode.getIndex() / 2 == 0)
			return;
		PQAble parent = heapArray[newNode.getIndex() / 2];

		if (newNode.compareTo(parent) < 0) {
			swapPQAble(newNode, parent);
			heapifyUp(newNode);
		}

	}

	private void heapifyDown(int index) {
		if (index * 2 >= currentSize)
			return;

		PQAble highestPriorityChild = checkChildren(heapArray[index * 2],
				heapArray[index * 2 + 1]);

		if (highestPriorityChild == null
				|| heapArray[index].compareTo(highestPriorityChild) <= 0) {
			return;
		}
		int highIndex = highestPriorityChild.getIndex();
		swapPQAble(highestPriorityChild, heapArray[index]);

		heapifyDown(highIndex);
	}

	/**
	 * Takes the children of a node and returns the highest priority child.
	 * 
	 * @param lChild
	 * @param rChild
	 * @return
	 */
	private PQAble checkChildren(PQAble lChild, PQAble rChild) {
		if (lChild == null && rChild == null)
			return null;
		else if (lChild != null && rChild == null)
			return lChild;
		if (lChild.compareTo(rChild) <= 0) {
			return lChild;
		} else
			return rChild;
	}

	/**
	 * Swaps the high priority PQAble with the low priority PQAble.
	 * 
	 * @param highP
	 * @param lowP
	 */
	private void swapPQAble(PQAble highP, PQAble lowP) {
		int highIn = highP.getIndex();
		int lowIn = lowP.getIndex();
		PQAble temp = highP;
		heapArray[highIn] = lowP;
		lowP.setIndex(highIn);
		temp.setIndex(lowIn);
		heapArray[lowIn] = temp;

	}

	/**
	 * This method takes the old array and makes a new one double its size and
	 * copies the old array into the new bigger one.
	 */
	private void increaseArraySize() {
		arraySize = arraySize * 2;
		PQAble[] biggerArray = new PQAble[arraySize];

		for (int i = 0; i < arraySize / 2; i++) {
			biggerArray[i] = heapArray[i];
		}
		heapArray = biggerArray;
	}

	@Override
	public String toString() {
		StringBuilder strArray = new StringBuilder();
		for (int i = 1; i < currentSize; i++) {
			strArray.append(heapArray[i].toString());
		}
		return strArray.toString();
	}

}

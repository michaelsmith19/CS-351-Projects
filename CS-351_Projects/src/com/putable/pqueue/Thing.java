package com.putable.pqueue;

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
			return 0;
		Thing comparator = (Thing) arg0;
		if (this.whenToExecute < comparator.whenToExecute)
			return 0;
		else if (this.whenToExecute == comparator.whenToExecute)
			return 2;

		return 1;
	}
	
	public int getWhenToExecute() {
		return whenToExecute;
	}
	public void setWhenToExecute(int number) {
		this.whenToExecute = number;
	}

}

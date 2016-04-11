package com.putable.siteriter.server;

/**
 * This interface is for basic thread concurrency. Basically, I am trying to
 * follow an analogy which encapsulates the behavior that I need for threads
 * using the SDLParser in SDLServer:
 * 
 * A ballroom can hold any number of people. They enter and leave, but have to
 * check in and check out. Sometimes, a janitor will come through and clean the
 * ballroom, but he must lock the front door and wait for everyone to leave
 * first. Once he finished cleaning, he can unlock the front door and let
 * everyone back in.
 * 
 * The methods described in this interface follow this analogy.
 * 
 * @author Trent Small
 * 
 */
public interface Ballroom
{
	/**
	 * Checks a Thread into the ballroom. If the ballroom is locked, the Thread
	 * must wait for the ballroom to unlock in order for it this method to
	 * finish.
	 */
	void checkIn() throws InterruptedException;

	/**
	 * Checks a Thread out of the ballroom. This also notifies any thread
	 * attempting to lock the ballroom to let him know that a Thread has left.
	 */
	void checkOut();

	/**
	 * Waits for all threads which called {@link #checkIn()} to call
	 * {@link #checkOut()}, then locks the ballroom. After this is called, the
	 * ballroom must be {@link #unlock()}ed in order to let Threads check back in. Any
	 * locking threads <em><b>**** MUST NOT ****</em></b> be checked in.
	 */
	void lock() throws InterruptedException;

	/**
	 * Unlocks the ballroom, notifying all threads which are waiting to Check
	 * In.
	 */
	void unlock();
}

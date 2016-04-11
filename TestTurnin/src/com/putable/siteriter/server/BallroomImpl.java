package com.putable.siteriter.server;

/**
 * An implementation of a Ballroom. This is an attempt to write non-trivial code
 * in order to decrease the likelihood of thread concurrency bugs.
 * 
 * @author Trent Small
 * 
 */
public class BallroomImpl implements Ballroom
{
	/**
	 * An object which serves no purpose other than to be used for
	 * {@link Object#wait()} and {@link Object#notifyAll()}.
	 */
	private Object				waitLock		= new Object();

	/**
	 * Another lock object used to synchronize reading and writing to
	 * {@link #checkedInCount}.
	 */
	private Object				countLock		= new Object();

	/**
	 * Another lock object used to synchronize reading and writing to
	 * {@link locked}.
	 */
	private Object				lockedLock		= new Object();

	/**
	 * Keeps track of how many Threads are currently checked in. This is to be
	 * accessed <em><b>only</b></em> by {@link #addToCheckedCount(int)} and
	 * {@link #getCheckedCount()} to keep threads concurrent.
	 */
	private volatile int		checkedInCount	= 0;

	/**
	 * Keeps track of whether or not the Ballroom is locked. This is to be
	 * accessed <em><b>only</b></em> by {@link #setLocked(boolean)} and
	 * {@link #isLocked()}.
	 */
	private volatile boolean	locked			= false;

	/**
	 * Increments {@link #checkedInCount} by the specified argument. The
	 * argument can be negative, which results in a decrement. This is
	 * synchronized, so only one thread may modify this value at a time.
	 * 
	 * @param n
	 *            The number to add to {@link #checkedInCount}.
	 */
	private void addToCheckedCount(int n)
	{
		synchronized (countLock)
		{
			checkedInCount += n;
		}
	}

	/**
	 * Checks to see how many Threads are currently between calling
	 * {@link #checkIn()} and {@link #checkOut()}.
	 * 
	 * @return The number of threads that are checked in.
	 */
	private int getCheckedCount()
	{
		synchronized (countLock)
		{
			return checkedInCount;
		}
	}

	/**
	 * Sets the value of {@link #locked}. This is synchronized, so only one
	 * Thread may edit this value at a time.
	 * 
	 * @param value
	 *            The value to set {@link #locked} to.
	 */
	private void setLocked(boolean value)
	{
		synchronized (lockedLock)
		{
			locked = value;
		}
	}

	/**
	 * Checks to see whether or not the Ballroom is locked.
	 * 
	 * @return {@link true} if the Ballroom is locked, else {@link false}.
	 */
	private synchronized boolean isLocked()
	{
		synchronized (lockedLock)
		{
			return locked;
		}
	}

	@Override
	public void checkIn() throws InterruptedException
	{
		/*
		 * If the lock is activated, wait for someone to notify that it is
		 * deactivated, Then enter.
		 */
		while (isLocked())
		{
			synchronized (waitLock)
			{
				waitLock.wait();
			}
		}
		addToCheckedCount(1);
	}

	@Override
	public void checkOut()
	{
		/*
		 * Leave, then notify anyone waiting to lock that a Thread has left
		 */
		addToCheckedCount(-1);
		synchronized (waitLock)
		{
			waitLock.notifyAll();
		}
	}

	@Override
	public synchronized void lock() throws InterruptedException
	{
		setLocked(true);

		/*
		 * Wait for everyone to leave after the lock is activated
		 */
		while (getCheckedCount() > 0)
		{
			synchronized (waitLock)
			{
				waitLock.wait();
			}
		}
	}

	@Override
	public void unlock()
	{
		/*
		 * Deactivate the lock and notify anyone waiting to enter
		 */
		setLocked(false);
		synchronized (waitLock)
		{
			waitLock.notifyAll();
		}
	}
}

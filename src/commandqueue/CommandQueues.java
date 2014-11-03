/*
 This file is part of theunibot.

 theunibot is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 theunibot is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with theunibot.  If not, see <http://www.gnu.org/licenses/>.

 Copyright (c) 2014 Unidesk Corporation
 */
package commandqueue;

import commands.CommandInterface;
import enums.CommandStatus;
import java.util.concurrent.Semaphore;
import utils.Result;

/**
 *
 */
public class CommandQueues {

	private static CommandQueues cw = null;

	private CommandQueue[] queues = new CommandQueue[3];
	private CommandQueue statusQueue = new CommandQueue();
	private Semaphore queueSemaphore = new Semaphore(0);
	private boolean killThread = false;
	private int roundRobin = 1;

	public static CommandQueues getInstance() {
		if (cw == null)
			cw = new CommandQueues();
		return cw;
	}

	private CommandQueues() {
		for (int i = 0; i < queues.length; i++)
			queues[i] = new CommandQueue();
	}

	/**
	 * Clear the command queue of the given num
	 *
	 * @param int with the queue index to clear
	 */
	public void clear(int num) {
		System.out.println("Clearing queue " + num);
		synchronized (this) {
			// loop while anything is in this queue
			while (true) {
				// remove top element
				CommandInterface cmd = queues[num].pop();
				if (cmd == null)
					break;
				System.out.println("Clearing unexecuted command: " + cmd.details());

				// and decrement semaphore counter accordingly
				queueSemaphore.tryAcquire();
			}

			// now do it again, this time for the status queue
			while (true) {
				// remove top element
				CommandInterface cmd = statusQueue.pop();
				if (cmd == null)
					break;
				System.out.println("Clearing status command: " + cmd.details());
			}
		}
	}

	/**
	 * Adds an element to the given queue. Standard command not requiring a sign
	 * or desktop.
	 *
	 * @param queueIndex index of the queue to add to
	 * @param cmd        command to add to the queue
	 * @param checkable  if checkable with status
	 */
	public void add(int queueIndex, CommandInterface cmd, boolean checkable) {
		synchronized (this) {
			cmd.setQueueIndex(queueIndex);
			queues[queueIndex].add(cmd);
			if (checkable)
				statusQueue.add(cmd);
			// signal that we have something to do
			queueSemaphore.release();
		}
	}

	/**
	 * Determine number of commands currently enqueued
	 *
	 * @return count of commands waiting for execution
	 */
	public int queueDepth() {
		synchronized (this) {
			// scan all queues, counting elements
			int count = 0;
			for (int scan = 0; scan < 3; ++scan)
				count += queues[scan].queueDepth();

			return count;
		}
	}

	/**
	 * Gets the next command in the list (blocking until command available);
	 * leaves item on queue (see pop)
	 *
	 * @return Next CommandInterface from the queues, following priority rules.
	 *         Returns null if the thread should be killed
	 */
	public CommandInterface pop() {
		// safety valve ... don't sleep if we know a kill is waiting
		if (killThread)
			return null;

		// now wait on the semaphore
		try {
			queueSemaphore.acquire();
		} catch (InterruptedException e) {
			return null;
		}

		synchronized (this) {
			// first, see if we should kill
			if (killThread)
				return null;

			// check if anything in the high priority queue
			CommandInterface cmd;
			cmd = queues[0].pop();
			if (cmd != null)
				return cmd;

			// now try the next queue based on round-robin
			for (int scan = 0; scan < 2; ++scan) {
				// switch robin
				roundRobin++;
				// does this one have something for us?
				int queueIndex = roundRobin % 2 + 1;
				cmd = queues[queueIndex].pop();
				if (cmd != null)
					return cmd;
			}
			// we got nothing.  
			System.out.println("WARNING: CommandQueueWrapper.pop found nothing; THIS SHOULD NEVER HAPPEN!");
			return null;
		}
	}

	/**
	 * Pushes the specified command to the head of the queue; used to re-enqueue
	 * an item when it has not completed
	 */
	public void push(CommandInterface cmd) {
		synchronized (this) {
			//inserts the command at the head of the queue
			queues[cmd.getQueueIndex()].insert(cmd, 0);
			// signal that we have something to do
			queueSemaphore.release();
		}
	}

	/**
	 * Gets status of the specific queued element; removes from list when
	 * complete
	 *
	 * @param id Id of the queued item to check
	 *
	 * @return Status of the command
	 */
	public CommandStatus getStatus(String id) {
		synchronized (this) {
			long l = Long.parseLong(id);
			CommandInterface cmd = statusQueue.getById(l);
			if (cmd == null)
				return CommandStatus.UNKNOWN;

			CommandStatus status = cmd.getStatus();
			// if complete, remove it from memory
			if ((status == CommandStatus.COMPLETE) || (status == CommandStatus.ERROR))
				statusQueue.remove(cmd);
			return cmd.getStatus();
		}
	}

	/**
	 * Gets result of the specific queued element
	 *
	 * @param id Id of the queued item to check
	 *
	 * @return Status of the command
	 */
	public Result getResult(String id) {
		synchronized (this) {
			long l = Long.parseLong(id);
			CommandInterface cmd = statusQueue.getById(l);
			if (cmd == null)
				return null;
			return cmd.getResult();
		}
	}

	/**
	 * Sends a kill message back to the controlling thread, via the pop call
	 */
	public void kill() {
		killThread = true;
		// signal that we have something to do
		queueSemaphore.release();
	}

	/**
	 * Determines if a shutdown request is pending
	 *
	 * @return true if killed, false if not
	 */
	public boolean killed() {
		return killThread;
	}
}

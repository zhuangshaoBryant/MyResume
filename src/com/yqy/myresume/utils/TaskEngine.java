/**
 * $Revision: 4005 $
 * $Date: 2006-06-16 08:58:27 -0700 (Fri, 16 Jun 2006) $
 *
 * Copyright (C) 2006 Jive Software. All rights reserved.
 *
 * This software is published under the terms of the GNU Public License (GPL),
 * a copy of which is included in this distribution.
 */

package com.yqy.myresume.utils;

import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Performs tasks using worker threads. It also allows tasks to be scheduled to be
 * run at future dates. This class mimics relevant methods in both
 * {@link ExecutorService} and {@link Timer}. Any {@link TimerTask} that's
 * scheduled to be run in the future will automatically be run using the thread
 * executor's thread pool. This means that the standard restriction that TimerTasks
 * should run quickly does not apply.
 *
 * @author Matt Tucker
 */
public class TaskEngine {

    private static TaskEngine instance = null;

    /**
     * Returns a task engine instance (singleton).
     *
     * @return a task engine.
     */
    public static TaskEngine getInstance() {
    	synchronized (TaskEngine.class) {
    		if(instance == null){
        		instance = new TaskEngine();
        	}
		}
    	
        return instance;
    }
    public void close(){
    	instance = null;
    }
    private Timer timer;
    private ExecutorService executor;
    private Map<TimerTask, TimerTaskWrapper> wrappedTasks = new ConcurrentHashMap<TimerTask, TimerTaskWrapper>();

    /**
     * Constructs a new task engine.
     */
    private TaskEngine() {
        timer = new Timer("timer-spark", true);
        executor = Executors.newCachedThreadPool(new ThreadFactory() {

            final AtomicInteger threadNumber = new AtomicInteger(1);

            public Thread newThread(Runnable runnable) {
                // Use our own naming scheme for the threads.
                Thread thread = new Thread(Thread.currentThread().getThreadGroup(), runnable,
                        "pool-spark" + threadNumber.getAndIncrement(), 0);
                // Make workers daemon threads.
                thread.setDaemon(true);
                if (thread.getPriority() != Thread.NORM_PRIORITY) {
                    thread.setPriority(Thread.NORM_PRIORITY);
                }
                return thread;
            }
        });
    }

    /**
     * Submits a Runnable task for execution and returns a Future
     * representing that task.
     *
     * @param task the task to submit.
     * @return a Future representing pending completion of the task,
     *         and whose <tt>get()</tt> method will return <tt>null</tt>
     *         upon completion.
     * @throws java.util.concurrent.RejectedExecutionException
     *                              if task cannot be scheduled
     *                              for execution.
     * @throws NullPointerException if task null.
     */
    public Future<?> submit(Runnable task) {
        return executor.submit(task);
    }


    /**
     * Schedules the specified task for execution after the specified delay.
     *
     * @param task  task to be scheduled.
     * @param delay delay in milliseconds before task is to be executed.
     * @throws IllegalArgumentException if <tt>delay</tt> is negative, or
     *                                  <tt>delay + System.currentTimeMillis()</tt> is negative.
     * @throws IllegalStateException    if task was already scheduled or
     *                                  cancelled, or timer was cancelled.
     */
    public void schedule(TimerTask task, long delay) {
        timer.schedule(new TimerTaskWrapper(task), delay);
    }

    /**
     * Schedules the specified task for execution at the specified time.  If
     * the time is in the past, the task is scheduled for immediate execution.
     *
     * @param task task to be scheduled.
     * @param time time at which task is to be executed.
     * @throws IllegalArgumentException if <tt>time.getTime()</tt> is negative.
     * @throws IllegalStateException    if task was already scheduled or
     *                                  cancelled, timer was cancelled, or timer thread terminated.
     */
    public void schedule(TimerTask task, Date time) {
        timer.schedule(new TimerTaskWrapper(task), time);
    }

   
    public void schedule(TimerTask task, long delay, long period) {
        TimerTaskWrapper taskWrapper = new TimerTaskWrapper(task);
        wrappedTasks.put(task, taskWrapper);
        timer.schedule(taskWrapper, delay, period);
    }

    /**
     * Cancels the execution of a scheduled task. {@link java.util.TimerTask#cancel()}
     *
     * @param task the scheduled task to cancel.
     */
    public void cancelScheduledTask(TimerTask task) {
    	TimerTask taskWrapper = wrappedTasks.remove(task);
        if (taskWrapper != null) {
            taskWrapper.cancel();
        }
    }
    
    /**
     * Shuts down the task engine service.
     */
    public void shutdown() {
        if (executor != null) {
            executor.shutdownNow();
            executor = null;
        }

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Wrapper class for a standard TimerTask. It simply executes the TimerTask
     * using the executor's thread pool.
     */
    private class TimerTaskWrapper extends TimerTask {

        private TimerTask task;

        public TimerTaskWrapper(TimerTask task) {
            this.task = task;
        }

        public void run() {
            executor.submit(task);
        }
    }
}
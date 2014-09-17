package com.rd.quartz.notice;

import org.apache.log4j.Logger;

public class AbstractLoanTask implements LoanTask {
	private Logger logger = Logger.getLogger(AbstractLoanTask.class);
	public Thread task = new Thread("Task") {
		private boolean isRun;

		public void run() {
			while (this.isRun) {
				AbstractLoanTask.this.logger.debug(getName() + " Running!");
				AbstractLoanTask.this.doLoan();
				AbstractLoanTask.this.doWait();
			}
		}

		public void stopThread() {
			this.isRun = false;
		}
	};

	public AbstractLoanTask() {
		this.task.start();
		this.logger.debug("线程启动！");
	}

	public void execute() {
		this.logger.debug("task.execute()");
		Object lock = getLock();
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	public void doLoan() {
		this.logger.debug("AbstractTask开始");
	}

	public void doWait() {
		Object lock = getLock();
		synchronized (getLock()) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {
	}

	public Object getLock() {
		return "false";
	}
}
package com.liuyun.site.quartz.notice;

import com.liuyun.site.domain.Notice;
import com.liuyun.site.service.NoticeService;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.beans.factory.annotation.Autowired;

public class NoticeJobQueue<T> {
	private Queue<T> queue = new ConcurrentLinkedQueue<T>();
	private LoanTask task;

	@Autowired
	private static NoticeService noticeService;
	public static NoticeJobQueue<Notice> NOTICE = null;
	public static NoticeJobQueue<Notice> MESSAGE = null;

	public NoticeJobQueue(LoanTask task) {
		this.task = task;
	}

	public synchronized void offer(T model) {
		if (!this.queue.contains(model)) {
			this.queue.offer(model);
			synchronized (this.task.getLock()) {
				this.task.execute();
			}
		}
	}

	public synchronized void offer(List<T> ts) {
		for (int i = 0; i < ts.size(); ++i) {
			T t = ts.get(i);
			if (!this.queue.contains(t)) {
				this.queue.offer(t);
			}
		}
		synchronized (this.task.getLock()) {
			this.task.execute();
		}
	}

	public synchronized T poll() {
		return this.queue.poll();
	}

	public synchronized T peek() {
		return this.queue.peek();
	}

	public synchronized void remove(T model) {
		this.queue.remove(model);
	}

	public int size() {
		return this.queue.size();
	}

	public static void init(NoticeService noticeService) {
		NOTICE = new NoticeJobQueue<Notice>(new SmsTask(noticeService));
		MESSAGE = new NoticeJobQueue<Notice>(new MessageTask(noticeService));
	}

	public static void stop() {
		NOTICE.task.stop();
	}

	public void doAutoQueue() {
	}
}
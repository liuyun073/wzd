package com.rd.quartz;

import com.rd.domain.Repayment;
import com.rd.model.DetailCollection;
import com.rd.model.borrow.BorrowModel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： JobQueue   
 * 类描述： job 队列   
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 26, 2013 9:00:42 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 26, 2013 9:00:42 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class JobQueue<T> {
	private Queue<T> queue = new ConcurrentLinkedQueue<T>();

	public static JobQueue<BorrowModel> FULL_SUCCESS = new JobQueue<BorrowModel>();
	public static JobQueue<BorrowModel> FULL_FAIL = new JobQueue<BorrowModel>();
	public static JobQueue<BorrowModel> CANCEL = new JobQueue<BorrowModel>();
	public static JobQueue<Repayment> REPAY = new JobQueue<Repayment>();
	public static JobQueue<DetailCollection> FLOW_REPAY = new JobQueue<DetailCollection>();
	public static JobQueue<BorrowModel> SECOND_REPAY = new JobQueue<BorrowModel>();
	public static JobQueue<BorrowModel> AUTO_TENDER = new JobQueue<BorrowModel>();

	public synchronized void offer(T model) {
		if (!this.queue.contains(model))
			this.queue.offer(model);
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
}
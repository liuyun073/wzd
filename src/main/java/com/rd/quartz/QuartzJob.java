package com.rd.quartz;

import com.rd.context.Constant;
import com.rd.domain.Repayment;
import com.rd.model.DetailCollection;
import com.rd.model.borrow.BorrowHelper;
import com.rd.model.borrow.BorrowModel;
import com.rd.service.AutoBorrowService;
import com.rd.service.BorrowService;
import com.rd.service.NoticePayBorrowService;
import java.util.List;
import org.apache.log4j.Logger;


/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： QuartzJob   
 * 类描述： 任务调度管理器   
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Nov 3, 2013 11:17:09 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Nov 3, 2013 11:17:09 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class QuartzJob {
	private Logger logger = Logger.getLogger(QuartzJob.class);
	
	private BorrowService borrowService;
	private AutoBorrowService autoBorrowService;
	private NoticePayBorrowService noticePayBorrowService;
	private int sleepTime = 5;

	/**
	 * **************************************************************************************
	 * 方法名: doAutoQueue 
	 * 功能: 自动任务添加队列 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数:  入参   
	 * 返回类型: void     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public void doAutoQueue() {
		List<BorrowModel> slist = this.borrowService.getBorrowListByStatus(3);
		for (BorrowModel b : slist) {
			JobQueue.FULL_SUCCESS.offer(b);
		}

		List<BorrowModel> flist = this.borrowService.getBorrowListByStatus(4);
		for (BorrowModel b : flist) {
			JobQueue.FULL_FAIL.offer(b);
		}

		List<BorrowModel> clist = this.borrowService.getBorrowListByStatus(5);
		for (BorrowModel b : clist) {
			JobQueue.CANCEL.offer(b);
		}

		List<Repayment> rlist = this.borrowService.getAllRepaymentList(1);
		for (Repayment r : rlist) {
			JobQueue.REPAY.offer(r);
		}

		List<DetailCollection> dclist = this.borrowService.getAllFlowCollectList(0);
		for (DetailCollection c : dclist) {
			if (c.getStatus() == 0) {
				JobQueue.FLOW_REPAY.offer(c);
			}
		}

		List<BorrowModel> blist = this.borrowService.getBorrowListByStatus(19);
		for (BorrowModel b : blist)
			JobQueue.AUTO_TENDER.offer(b);
	}

	
	/**
	 * **************************************************************************************
	 * 方法名: doTask 
	 * 功能: 任务处理 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数:  入参   
	 * 返回类型: void     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public void doTask() {
		
		Thread fullSuccessTask = new Thread() {
			public void run() {
				QuartzJob.this.doAutoVerifyFullSuccess();
			}
		};
		fullSuccessTask.setName("doAutoVerifyFullSuccess-Thread");
		fullSuccessTask.start();

		Thread fullFailTask = new Thread() {
			public void run() {
				QuartzJob.this.doAutoVerifyFullFail();
			}
		};
		fullFailTask.setName("doAutoVerifyFullFail-Thread");
		fullFailTask.start();

		Thread cancelTask = new Thread() {
			public void run() {
				QuartzJob.this.doAutoCancel();
			}
		};
		cancelTask.setName("doAutoCancel-Thread");
		cancelTask.start();

		Thread repayTask = new Thread() {
			public void run() {
				QuartzJob.this.doAutoRepay();
			}
		};
		repayTask.setName("doAutoRepay-Thread");
		repayTask.start();

		Thread flowRepayTask = new Thread() {
			public void run() {
				QuartzJob.this.doAutoFlowRepay();
			}
		};
		flowRepayTask.setName("doAutoFlowRepay-Thread");
		flowRepayTask.start();

		Thread autoTenderTask = new Thread() {
			public void run() {
				QuartzJob.this.doAutoTender();
			}
		};
		autoTenderTask.setName("doAutoTender-Thread");
		autoTenderTask.start();
	}

	
	/**
	 * **************************************************************************************
	 * 方法名: doAutoVerifyFullSuccess 
	 * 功能: 处理复审成功的借款 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数:  入参   
	 * 返回类型: void     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public void doAutoVerifyFullSuccess() {
		while (true) {
			BorrowModel b = (BorrowModel) JobQueue.FULL_SUCCESS.peek();
			if (b != null) {
				BorrowModel wrapBorrrow = BorrowHelper.getHelper(b);
				try {
					if (wrapBorrrow.getBorrowType() == Constant.TYPE_DONATION) {
						this.logger.info("这是爱心捐助标");
						this.autoBorrowService.autoVerifyFullSuccessForDonation(wrapBorrrow);
					} else {
						this.autoBorrowService.autoVerifyFullSuccess(wrapBorrrow);
					}
				} catch (Exception e) {
					this.logger.error(e.getMessage());
					e.printStackTrace();
				} finally {
					JobQueue.FULL_SUCCESS.remove(b);
				}
			}
			sleep(this.sleepTime);
		}
	}

	/**
	 * **************************************************************************************
	 * 方法名: doAutoVerifyFullFail 
	 * 功能: 处理复审失败的借款 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数:  入参   
	 * 返回类型: void     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public void doAutoVerifyFullFail() {
		while (true) {
			BorrowModel b = (BorrowModel) JobQueue.FULL_FAIL.peek();
			if (b != null) {
				BorrowModel wrapBorrrow = BorrowHelper.getHelper(b);
				try {
					this.autoBorrowService.autoVerifyFullFail(wrapBorrrow);
				} catch (Exception e) {
					this.logger.error(e.getMessage());
					e.printStackTrace();
				} finally {
					JobQueue.FULL_FAIL.remove(b);
				}
			}
			sleep(this.sleepTime);
		}
	}

	/**
	 * **************************************************************************************
	 * 方法名: doAutoCancel 
	 * 功能: 处理撤回的借款 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数:  入参   
	 * 返回类型: void     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public void doAutoCancel() {
		while (true) {
			BorrowModel b = (BorrowModel) JobQueue.CANCEL.peek();
			if (b != null) {
				BorrowModel wrapBorrrow = BorrowHelper.getHelper(b);
				try {
					this.autoBorrowService.autoCancel(wrapBorrrow);
				} catch (Exception e) {
					this.logger.error(e.getMessage());
					e.printStackTrace();
				} finally {
					JobQueue.CANCEL.remove(b);
				}
			}
			sleep(this.sleepTime);
		}
	}

	/**
	 * **************************************************************************************
	 * 方法名: doAutoRepay 
	 * 功能: 处理还款 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数:  入参   
	 * 返回类型: void     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public void doAutoRepay() {
		while (true) {
			Repayment r = (Repayment) JobQueue.REPAY.peek();
			if (r != null) {
				try {
					this.autoBorrowService.autoRepay(r);
				} catch (Exception e) {
					this.logger.error(e.getMessage());
					e.printStackTrace();
				} finally {
					JobQueue.REPAY.remove(r);
				}
			}
			sleep(this.sleepTime);
		}
	}

	
	public void doAutoFlowRepay() {
		while (true) {
			DetailCollection dc = (DetailCollection) JobQueue.FLOW_REPAY.peek();
			if ((dc != null) && (dc.getStatus() == 0)) {
				try {
					this.autoBorrowService.autoFlowRepay(dc);
				} catch (Exception e) {
					this.logger.error(e.getMessage());
					e.printStackTrace();
				} finally {
					JobQueue.FLOW_REPAY.remove(dc);
				}
			}
			sleep(this.sleepTime);
		}
	}

	/**
	 * **************************************************************************************
	 * 方法名: doAutoTender 
	 * 功能: 处理投标
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数:  入参   
	 * 返回类型: void     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public void doAutoTender() {
		while (true) {
			BorrowModel borrow = (BorrowModel) JobQueue.AUTO_TENDER.peek();
			if ((borrow != null) && (borrow.getStatus() == 19)) {
				try {
					this.autoBorrowService.autoDealTender(borrow);
				} catch (Exception e) {
					this.logger.error(e.getMessage());
					e.printStackTrace();
				} finally {
					JobQueue.AUTO_TENDER.remove(borrow);
				}
			}
			sleep(this.sleepTime);
		}
	}

	
	private void sleep(int sec) {
		try {
			Thread.sleep(1000 * sec);
		} catch (InterruptedException localInterruptedException) {
		}
	}

	public void doMsgTimer() {
	}

	public BorrowService getBorrowService() {
		return this.borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public AutoBorrowService getAutoBorrowService() {
		return this.autoBorrowService;
	}

	public void setAutoBorrowService(AutoBorrowService autoBorrowService) {
		this.autoBorrowService = autoBorrowService;
	}

	/**
	 * **************************************************************************************
	 * 方法名: doAutoNoticePayBorrow 
	 * 功能: 还款通知  一周为期 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数:  入参   
	 * 返回类型: void     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public void doAutoNoticePayBorrow() {
		this.logger.debug("doAutoNoticeToPayBorrow job start....");
		this.noticePayBorrowService.autoNoticePayBorrow();
	}

	/**
	 * **************************************************************************************
	 * 方法名: doAutoCalcuLateInterest 
	 * 功能: 定时计算逾期的天数和利息 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数:  入参   
	 * 返回类型: void     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public void doAutoCalcuLateInterest() {
		this.logger.debug("计算逾期的天数和利息go....");
		this.noticePayBorrowService.CalcLateInterest();
	}

	public NoticePayBorrowService getNoticePayBorrowService() {
		return this.noticePayBorrowService;
	}

	public void setNoticePayBorrowService(
			NoticePayBorrowService noticePayBorrowService) {
		this.noticePayBorrowService = noticePayBorrowService;
	}
}
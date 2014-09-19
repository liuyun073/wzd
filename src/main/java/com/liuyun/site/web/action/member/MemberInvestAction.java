package com.liuyun.site.web.action.member;

import com.liuyun.site.context.Constant;
import com.liuyun.site.domain.User;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.invest.CollectionList;
import com.liuyun.site.model.invest.InvestBorrowList;
import com.liuyun.site.service.BorrowService;
import com.liuyun.site.service.UserService;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.web.action.BaseAction;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;

public class MemberInvestAction extends BaseAction {
	private static Logger logger = Logger.getLogger(MemberInvestAction.class);
	private UserService userService;
	private BorrowService borrowService;

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public BorrowService getBorrowService() {
		return this.borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public String list() {
		logger.info("成功投资列表");
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		String type = this.request.getParameter("type");
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		InvestBorrowList list = this.borrowService.getSuccessListByUserid(user_id, type, page, new SearchParam());
		this.request.setAttribute("borrow", list.getList());
		Map<String, Object> map = param.toMap();
		map.put("type", type);
		this.request.setAttribute("param", map);
		this.request.setAttribute("page", list.getPage());
		this.request.setAttribute("type", type);
		return SUCCESS;
	}

	public String collect() {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		int status = NumberUtils.getInt(this.request.getParameter("status"));
		SearchParam param = new SearchParam();
		CollectionList cList = this.borrowService.getCollectionList(user_id, status, page, param);

		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.request.setAttribute("currentTime", sdf.format(date));

		this.request.setAttribute("collect", cList.getList());
		this.request.setAttribute("page", cList.getPage());

		param.setStatus("" + status);
		this.request.setAttribute("param", param.toMap());
		this.request.setAttribute("status", Integer.valueOf(status));
		return SUCCESS;
	}

	public String tender() {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		PageDataList pList = this.borrowService.getInvestTenderListByUserid(user_id, page, param);
		this.request.setAttribute(Constant.TENDER, pList.getList());
		this.request.setAttribute("page", pList.getPage());
		this.request.setAttribute("param", param.toMap());
		return SUCCESS;
	}

	public String bid() {
		User user = getSessionUser();
		long user_id = user.getUser_id();
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		PageDataList pList = this.borrowService.getInvestTenderingListByUserid(user_id, page, param);
		this.request.setAttribute(Constant.TENDER, pList.getList());
		this.request.setAttribute("page", pList.getPage());
		this.request.setAttribute("param", param.toMap());
		return SUCCESS;
	}
}
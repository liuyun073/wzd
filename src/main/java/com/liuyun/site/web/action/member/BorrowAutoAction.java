package com.liuyun.site.web.action.member;

import com.opensymphony.xwork2.ModelDriven;
import com.liuyun.site.context.Constant;
import com.liuyun.site.domain.BorrowAuto;
import com.liuyun.site.domain.User;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.service.AutoBorrowService;
import com.liuyun.site.service.BorrowService;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import com.liuyun.site.web.action.BaseAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

public class BorrowAutoAction extends BaseAction implements SessionAware,
		ServletRequestAware, ModelDriven<BorrowAuto> {
	private static Logger logger = Logger.getLogger(BorrowAutoAction.class);
	private BorrowService borrowService;
	private AutoBorrowService autoBorrowService;
	private Map session;
	private HttpServletRequest request;
	private BorrowAuto auto = new BorrowAuto();

	public AutoBorrowService getAutoBorrowService() {
		return this.autoBorrowService;
	}

	public void setAutoBorrowService(AutoBorrowService autoBorrowService) {
		this.autoBorrowService = autoBorrowService;
	}

	public String auto() {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		String type = this.request.getParameter("type");
		type = StringUtils.isNull(type);
		List list = new ArrayList();
		String errormsg = null;
		if ((type.equals("list")) || (type.equals(""))) {
			list = this.borrowService.getBorrowAutoList(user_id);
			this.request.setAttribute("auto", list);
			this.request.setAttribute("type", type);
			return SUCCESS;
		}
		if (type.equals("add")) {
			list = this.borrowService.getBorrowAutoList(user_id);
			if (list.size() > 0) {
				errormsg = "您已经添加了1条自动投标，最多只能添加1条，您可以删除或者修改 ！";
			} else {
				this.auto.setUser_id(user_id);
				this.auto.setAddtime(NumberUtils.getInt(getTimeStr()));
				this.borrowService.addBorrowAuto(this.auto);
				list = this.borrowService.getBorrowAutoList(user_id);
			}
			this.request.setAttribute("errormsg", errormsg);
			this.request.setAttribute("auto", list);
			return SUCCESS;
		}
		if (type.equals("modify")) {
			String idStr = this.request.getParameter("id");
			long id = NumberUtils.getLong(idStr);
			if (id < 1L) {
				errormsg = "修改自动投标失败！";
				this.request.setAttribute("errormsg", errormsg);
			}

			List autolist = this.borrowService.getBorrowAutoList(user_id);
			BorrowAuto newauto = null;
			if (autolist.size() > 0) {
				newauto = (BorrowAuto) autolist.get(0);
				this.auto.setId(newauto.getId());
				this.auto.setUser_id(user_id);
				this.auto.setAddtime(NumberUtils.getInt(getTimeStr()));
				logger.debug("auto============" + this.auto);
				this.borrowService.modifyBorrowAuto(this.auto);
			}

			list = this.borrowService.getBorrowAutoList(user_id);
			this.request.setAttribute("auto", list);
			return SUCCESS;
		}
		if (type.equals("showAuto")) {
			list = this.borrowService.getBorrowAutoList(user_id);
			if (list.size() < 1) {
				return NOTFOUND;
			}
			BorrowAuto ba = (BorrowAuto) list.get(0);
			this.request.setAttribute("auto", ba);

			this.request.setAttribute("ba", ba);
			this.request.setAttribute("type", type);
			SearchParam param = new SearchParam();
			param.setTimelimit_day_first(ba.getTimelimit_day_first());
			param.setTimelimit_day_last(ba.getTimelimit_day_last());
			param.setTimelimit_month_first(ba.getTimelimit_month_first());
			param.setTimelimit_month_last(ba.getTimelimit_month_last());
			param.setApr_first(ba.getApr_first());
			param.setApr_last(ba.getApr_last());
			param.setAward_first(ba.getAward_first());
			this.request.setAttribute("map", param.toMap());
			return "query";
		}
		if (type.equals("delete")) {
			this.borrowService.deleteBorrowAuto(user_id);
			list = this.borrowService.getBorrowAutoList(user_id);
			this.request.setAttribute("auto", list);
			return SUCCESS;
		}
		return NOTFOUND;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public BorrowAuto getModel() {
		return this.auto;
	}

	public BorrowService getBorrowService() {
		return this.borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}
}
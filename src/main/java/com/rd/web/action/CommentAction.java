package com.rd.web.action;

import com.opensymphony.xwork2.ModelDriven;
import com.rd.domain.User;
import com.rd.model.BorrowComments;
import com.rd.model.DetailUser;
import com.rd.service.CommentService;
import com.rd.service.UserService;
import com.rd.util.StringUtils;
import java.io.PrintStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class CommentAction extends BaseAction implements
		ModelDriven<BorrowComments> {
	private static Logger logger = Logger.getLogger(CommentAction.class);
	private String valicode;
	private String comments;
	private long borrowid;
	private CommentService commentservice;
	private UserService userService;
	private BorrowComments commentinfo = new BorrowComments();
	private String rsmsg;
	private String backurl;

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String add() {
		User user = getSessionUser();
		if (user != null) {
			DetailUser detailUser = this.userService.getDetailUser(user
					.getUser_id());
			if (detailUser != null) {
				int vip_status = detailUser.getVip_status();
				String real_status = detailUser.getReal_status();
				if (!StringUtils.isNull(real_status).equals("1")) {
					message("请先进行实名认证", "/member/identify/realname.html",
							"返回上一页");
					return FAIL;
				}
				if (vip_status != 1) {
					message("请先进行vip认证", "/member/vip.html", "返回上一页");
					return FAIL;
				}
			}
		}
		if (checkValidImg(this.valicode)) {
			System.out.println(this.commentinfo);
			this.commentinfo.setAddip(getRequestIp());
			this.commentinfo.setAddtime(getTimeStr());
			this.commentinfo.setUser_id(getSessionUser().getUser_id());
			this.commentinfo.setArticle_id(this.borrowid);
			this.commentinfo.setComment(this.comments);
			if (this.commentservice.addComment(this.commentinfo) > 0)
				this.rsmsg = "评论添加成功";
		} else {
			this.rsmsg = "验证码不正确";
		}

		this.backurl = ("<a href='" + this.request.getHeader("Referer") + "'>点击返回</a>");
		return SUCCESS;
	}

	public String list() {
		Map map = this.commentservice.getCommentListByBorrowid(this.borrowid);
		this.request.setAttribute("commentList", map.get("List"));
		this.request.setAttribute("count", map.get("count"));
		return SUCCESS;
	}

	public String getValicode() {
		return this.valicode;
	}

	public void setValicode(String valicode) {
		this.valicode = valicode;
	}

	public CommentService getCommentservice() {
		return this.commentservice;
	}

	public void setCommentservice(CommentService commentservice) {
		this.commentservice = commentservice;
	}

	public String getRsmsg() {
		return this.rsmsg;
	}

	public void setRsmsg(String rsmsg) {
		this.rsmsg = rsmsg;
	}

	public String getBackurl() {
		return this.backurl;
	}

	public void setBackurl(String backurl) {
		this.backurl = backurl;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public BorrowComments getModel() {
		return this.commentinfo;
	}

	public long getBorrowid() {
		return this.borrowid;
	}

	public void setBorrowid(long borrowid) {
		this.borrowid = borrowid;
	}
}
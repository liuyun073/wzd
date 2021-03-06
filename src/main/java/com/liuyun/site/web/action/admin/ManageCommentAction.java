package com.liuyun.site.web.action.admin;

import com.liuyun.site.domain.Comments;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.service.CommentService;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import com.liuyun.site.web.action.BaseAction;
import org.apache.log4j.Logger;

public class ManageCommentAction extends BaseAction {
	private Logger logger = Logger.getLogger(ManageBorrowAction.class);
	private CommentService commentService;

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public String showAllComment() throws Exception {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		SearchParam param = new SearchParam();
		param.setUsername(username);
		PageDataList plist = this.commentService.getComment(page, param);
		setPageAttribute(plist, param);
		setMsgUrl("/admin/comment/comment.html");
		return SUCCESS;
	}

	public String updateComment() throws Exception {
		long status = NumberUtils.getLong(this.request.getParameter("status"));
		int id = NumberUtils.getInt(this.request.getParameter("id"));
		Comments c = this.commentService.getCommentById(id);
		this.request.setAttribute("comment", c);
		c.setId(id);
		if (status == 1L) {
			c.setStatus(1);
			this.commentService.updateCommentByUser_Id(c);
			message("审核通过！", "/admin/comment/showAllComment.html");
			return ADMINMSG;
		}
		if (status == -1L) {
			c.setStatus(-1);
			this.commentService.updateCommentByUser_Id(c);
			message("审核不通过", "/admin/comment/showAllComment.html");
			return ADMINMSG;
		}
		return SUCCESS;
	}

	public String deleteComment() throws Exception {
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		this.commentService.deleteCommentByid(id);
		message("评论删除成功", "/admin/comment/showAllComment.html");
		return ADMINMSG;
	}
}
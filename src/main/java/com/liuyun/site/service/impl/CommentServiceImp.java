package com.liuyun.site.service.impl;

import com.liuyun.site.dao.CommentDao;
import com.liuyun.site.domain.Comments;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.service.CommentService;
import com.liuyun.site.tool.Page;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentServiceImp implements CommentService {
	private CommentDao commentDao;

	public CommentDao getCommentDao() {
		return this.commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public Map<String, Object> getCommentListByBorrowid(long id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("List", this.commentDao.getCommentList(id));
		map.put("count", Integer.valueOf(this.commentDao.getCommentCount(id)));
		return map;
	}

	public int addComment(Comments c) {
		c.setModule_code("borrow");
		c.setFlag("1");
		c.setStatus(0);
		this.commentDao.addComment(c);
		return 1;
	}

	public void updateCommentByUser_Id(Comments c) {
		this.commentDao.updateCommentByID(c);
	}

	public void deleteCommentByid(long id) {
		this.commentDao.deleteCommentID(id);
	}

	public Comments getCommentById(long id) {
		return this.commentDao.getCommentByid(id);
	}

	public PageDataList getComment(int page, SearchParam param) {
		int total = this.commentDao.getCommentCount(param);
		Page p = new Page(total, page);
		List list = this.commentDao.getCommentCounts(p.getStart(), p
				.getPernum(), param);
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}
}
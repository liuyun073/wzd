package com.liuyun.site.service;

import com.liuyun.site.domain.Comments;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import java.util.Map;

public abstract interface CommentService {
	public abstract Map<String, Object> getCommentListByBorrowid(long paramLong);

	public abstract int addComment(Comments paramComments);

	public abstract void updateCommentByUser_Id(Comments paramComments);

	public abstract void deleteCommentByid(long paramLong);

	public abstract Comments getCommentById(long paramLong);

	public abstract PageDataList getComment(int paramInt,
			SearchParam paramSearchParam);
}
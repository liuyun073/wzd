package com.rd.dao;

import com.rd.domain.Comments;
import com.rd.model.BorrowComments;
import com.rd.model.SearchParam;
import java.util.List;

public abstract interface CommentDao {
	public abstract List<BorrowComments> getCommentList(long paramLong);

	public abstract int getCommentCount(long paramLong);

	public abstract int addComment(Comments paramComments);

	public abstract int deleteCommentByCommentId(long paramLong);

	public abstract int deleteCommentByBorrowId(long paramLong);

	public abstract int deleteCommentByUserId(long paramLong);

	public abstract void updateCommentByID(Comments paramComments);

	public abstract void deleteCommentID(long paramLong);

	public abstract Comments getCommentByid(long paramLong);

	public abstract int getCommentCount(SearchParam paramSearchParam);

	public abstract List<BorrowComments> getCommentCounts(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);
}
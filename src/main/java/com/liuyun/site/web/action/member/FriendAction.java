package com.liuyun.site.web.action.member;

import com.opensymphony.xwork2.ModelDriven;
import com.liuyun.site.context.Constant;
import com.liuyun.site.domain.Friend;
import com.liuyun.site.domain.User;
import com.liuyun.site.model.DetailUser;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.service.FriendService;
import com.liuyun.site.service.UserService;
import com.liuyun.site.tool.Page;
import com.liuyun.site.tool.coder.BASE64Encoder;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import com.liuyun.site.web.action.BaseAction;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class FriendAction extends BaseAction implements ModelDriven<Friend> {
	private static Logger logger = Logger.getLogger(FriendAction.class);
	private FriendService friendService;
	private UserService userService;
	private Friend friend = new Friend();

	public FriendService getFriendService() {
		return this.friendService;
	}

	public void setFriendService(FriendService friendService) {
		this.friendService = friendService;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Friend getModel() {
		return this.friend;
	}

	public String invite() throws Exception {
		User user = getSessionUser();
		long user_id = user.getUser_id();
		List list = this.userService.getInvitedUserByUserid(user_id);
		BASE64Encoder encoder = new BASE64Encoder();
		String s = encoder.encode(("" + user.getUser_id()).getBytes());
		this.request.setAttribute("userid", s);
		this.request.setAttribute("friendList", list);
		return SUCCESS;
	}

	public String request() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		List list = this.friendService.getFriendsRequest(user_id);
		this.request.setAttribute("list", list);
		return SUCCESS;
	}

	public String addfriend() throws Exception {
		Friend f = wrapFriend();
		f.setFriends_userid(this.friend.getFriends_userid());
		f.setUser_id(getSessionUser().getUser_id());
		this.friendService.addFriend(f);
		message("添加好友成功! ", "/member/friend/myfriend.html");
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: addfriendrequest 
	 * 功能: 申请添加好友 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String addfriendrequest() throws Exception {
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
		Friend f = wrapFriend();
		f.setFriends_userid(this.friend.getFriends_userid());
		f.setUser_id(getSessionUser().getUser_id());
		this.friendService.addFriendRequest(f);
		message("添加好友成功，请等待好友的审核 ", "/member/friend/myfriend.html");
		return SUCCESS;
	}

	private Friend wrapFriend() {
		this.friend.setStatus(0);

		this.friend.setAddtime(getTimeStr());
		this.friend.setAddip(getRequestIp());
		return this.friend;
	}

	private Friend wrapFriend(int status) {
		wrapFriend();
		this.friend.setStatus(status);
		return this.friend;
	}

	public String myfriend() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		int total = this.friendService.countFriendsByUserId(user_id);
		int page = 1;
		Page pageInfo = new Page(total, page, Page.ROWS);
		this.request.setAttribute("pageInfo", pageInfo);
		this.request.setAttribute("getPageStr", getPageStr(pageInfo));

		List list = this.friendService.getFriendsByUserId(user_id);
		this.request.setAttribute("list", list);
		return SUCCESS;
	}

	public String black() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		List list = this.friendService.getBlackList(user_id);
		this.request.setAttribute("list", list);
		this.request.setAttribute("query_type", "black");
		return SUCCESS;
	}

	public String blackfriend() throws Exception {
		String username = StringUtils.isNull(this.request
				.getParameter("username"));

		Friend f = wrapFriend(2);
		f.setUser_id(getSessionUser().getUser_id());
		f.setFriends_username(username);

		this.friendService.addBlackFriend(f);
		message("成功加入黑名单!", "/member/friend/black.html");
		return MSG;
	}

	public String delfriend() throws Exception {
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		this.friendService.delFriend(getSessionUser().getUser_id(), username);
		message("成功删除好友!", "/member/friend/myfriend.html");
		return MSG;
	}

	public String readdfriend() throws Exception {
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		this.friendService.readdFriend(getSessionUser().getUser_id(), username);
		message("重新加为好友!", "/member/friend/myfriend.html");
		return MSG;
	}

	public String tc() throws Exception {
		User user = getSessionUser();
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		if (page == 0) {
			page = 1;
		}
		String username = user.getUsername();
		SearchParam param = new SearchParam();
		param.setUsername(username);
		PageDataList plist = this.friendService.getFriendTiChengAcount(page,
				param);
		setPageAttribute(plist, param);
		setMsgUrl("/member/friend/tc.html");
		return SUCCESS;
	}

	private String getPageStr(Page p) {
		StringBuffer sb = new StringBuffer();
		int currentPage = p.getCurrentPage();
		int[] dispayPage = new int[5];
		if (p.getPages() < 5) {
			dispayPage = new int[p.getPages()];
			for (int i = 0; i < dispayPage.length; ++i) {
				dispayPage[i] = (i + 1);
			}
		} else if (currentPage < 3) {
			for (int i = 0; i < 5; ++i)
				dispayPage[i] = (i + 1);
		} else if (currentPage > p.getPages() - 2) {
			for (int i = 0; i < 5; ++i)
				dispayPage[i] = (p.getPages() - 4 + i);
		} else {
			for (int i = 0; i < 5; ++i) {
				dispayPage[i] = (currentPage - 2 + i);
			}
		}

		String typestr = " ";

		for (int i = 0; i < dispayPage.length; ++i) {
			if (dispayPage[i] == currentPage)
				sb
						.append(" <span class='this_page'>" + currentPage
								+ "</span>");
			else {
				sb.append(" <a href='myfriend.html?page=" + dispayPage[i]
						+ typestr + "'>" + dispayPage[i] + "</a>");
			}
		}
		return sb.toString();
	}
}
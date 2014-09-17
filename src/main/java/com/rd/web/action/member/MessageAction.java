package com.rd.web.action.member;

import com.opensymphony.xwork2.ModelDriven;
import com.rd.context.Constant;
import com.rd.domain.Message;
import com.rd.domain.User;
import com.rd.model.DetailUser;
import com.rd.model.PageDataList;
import com.rd.service.MessageService;
import com.rd.service.UserService;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import com.rd.web.action.BaseAction;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MessageAction extends BaseAction implements ModelDriven<Message> {
	private Message message = new Message();
	private MessageService messageService;
	private UserService userService;

	public Message getModel() {
		return this.message;
	}

	public MessageService getMessageService() {
		return this.messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String box() throws Exception {
		User sessionUser = getSessionUser();
		long userid = sessionUser.getUser_id();
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		PageDataList plist = this.messageService.getReceiveMessgeByUserid(
				userid, page);

		int Utypeid = this.userService.getUserById(userid).getType_id();
		int displaySend = 0;
		if ((Utypeid == 1) || (Utypeid == 3) || (Utypeid == 15)) {
			displaySend = 1;
		}
		HttpSession session = this.request.getSession();
		session.setAttribute("displaySend", Integer.valueOf(displaySend));

		this.request.setAttribute("msgList", plist.getList());
		this.request.setAttribute("page", plist.getPage());
		this.request.setAttribute("param", new HashMap());
		return SUCCESS;
	}

	public String sent() throws Exception {
		User sessionUser = getSessionUser();
		long userid = sessionUser.getUser_id();
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		PageDataList plist = this.messageService.getSentMessgeByUserid(userid,
				page);
		this.request.setAttribute("msgList", plist.getList());
		this.request.setAttribute("page", plist.getPage());
		this.request.setAttribute("param", new HashMap());
		return SUCCESS;
	}

	public String send() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		String sendType = StringUtils.isNull(this.request
				.getParameter("sendType"));
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		Message msg = this.messageService.getMessageById(id);
		User sent_user = getSessionUser();
		DetailUser detailUser = this.userService.getDetailUser(sent_user
				.getUser_id());
		if (detailUser != null) {
			int vip_status = detailUser.getVip_status();
			String real_status = detailUser.getReal_status();
			if (!StringUtils.isNull(real_status).equals("1")) {
				this.request.setAttribute("errormsg", "请先进行实名认证");
				return FAIL;
			}
			if (vip_status != 1) {
				this.request.setAttribute("errormsg", "请先进行vip认证");
				return FAIL;
			}
		}
		String checked = this.request.getParameter("sendAll");
		if (type.equals("add")) {
			String errormsg = checkMessage();
			if (!errormsg.equals("")) {
				this.request.setAttribute("errormsg", errormsg);
				return FAIL;
			}
			User receive_user = this.userService.getUserByName(this.message
					.getReceive_username());
			if ((receive_user == null) && (checked == null)) {
				errormsg = "收件人不存在";
				this.request.setAttribute("errormsg", errormsg);
				return FAIL;
			}

			if (checked != null) {
				List list = this.userService.getAllUser(2);
				for (int i = 0; i < list.size(); ++i) {
					this.message.setReceive_user(((User) list.get(i))
							.getUser_id());
					this.message.setSent_user(sent_user.getUser_id());
					this.message.setStatus(0);
					this.message.setType(Constant.SYSTEM);
					this.message.setAddip(getRequestIp());
					this.message.setAddtime(getTimeStr());
					this.messageService.addMessage(this.message);
				}
				message("发送消息成功！", "/member/message/sent.html");
				return MSG;
			}

			this.message.setSent_user(sent_user.getUser_id());
			this.message.setReceive_user(receive_user.getUser_id());
			this.message.setStatus(0);
			this.message.setType(Constant.SYSTEM);
			this.message.setAddip(getRequestIp());
			this.message.setAddtime(getTimeStr());

			this.messageService.addMessage(this.message);

			message("发送消息成功！", "/member/message/sent.html");
			return MSG;
		}
		if (type.equals("reply")) {
			this.message.setSent_user(msg.getReceive_user());
			this.message.setReceive_user(msg.getSent_user());
			this.message.setStatus(0);
			this.message.setType(Constant.SYSTEM);
			this.message.setAddip(getRequestIp());
			this.message.setAddtime(getTimeStr());
			this.message.setName("Re:" + msg.getName());
			this.message.setContent(this.message.getContent()
					+ "</br>------------------ 原始信息 ------------------</br>"
					+ msg.getContent());

			this.messageService.addMessage(this.message);

			message("回复消息成功！", "/member/message/sent.html");
			return MSG;
		}
		this.request.setAttribute("msg_type", "send");

		this.request.setAttribute("sendType", sendType);
		this.request.setAttribute(MSG, msg);
		return SUCCESS;
	}

	private String checkMessage() {
		String errormsg = "";
		String checked = this.request.getParameter("sendAll");
		String validcode = StringUtils.isNull(this.request
				.getParameter("valicode"));
		System.out.println("name: " + this.message.getName());
		if ((StringUtils.isNull(this.message.getReceive_username()).equals(""))
				&& (checked == null)) {
			errormsg = "收件人不能为空！";
			return errormsg;
		}
		if (StringUtils.isNull(this.message.getName().trim()).equals("")) {
			errormsg = "标题不能为空！";
			return errormsg;
		}
		if (StringUtils.isNull(this.message.getContent()).equals("")) {
			errormsg = "内容不能为空！";
			return errormsg;
		}
		if (!checkValidImg(validcode)) {
			errormsg = "验证码错误！";
			return errormsg;
		}
		return errormsg;
	}

	public String set() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		Long[] ids = NumberUtils.getLongs(this.request.getParameterValues("id"));
		if (ids.length < 1) {
			message("您操作有误，请勿乱操作！", "/member/message/box.html");
			return MSG;
		}
		String tip = "";
		if (type.equals("1")) {
			tip = "删除信息成功！";
			this.messageService.deleteReceiveMessage(ids);
		} else if (type.equals("2")) {
			tip = "删除信息成功！";
			this.messageService.deleteSentMessage(ids);
		} else if (type.equals("4")) {
			tip = "已标记已读 ！";
			this.messageService.setReadMessage(ids);
		} else if (type.equals("3")) {
			tip = "已标记未读 ！";
			this.messageService.setUnreadMessage(ids);
		}
		message(tip, "/member/message/box.html");
		return MSG;
	}

	public String view() throws Exception {
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		String type = StringUtils.isNull(this.request.getParameter("type"));
		if (id < 1L) {
			message("您操作有误，请勿乱操作！", "/member/message/box.html");
			return MSG;
		}

		Message msg = this.messageService.getMessageById(id);
		if (msg == null) {
			message("您操作有误，请勿乱操作！", "/member/message/box.html");
			return MSG;
		}
		User friend = this.userService.getUserById(msg.getSent_user());
		msg.setStatus(1);
		this.messageService.modifyMessge(msg);
		this.request.setAttribute(MSG, msg);
		this.request.setAttribute("friend", friend);
		this.request.setAttribute("type", type);
		return SUCCESS;
	}
}
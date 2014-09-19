package com.liuyun.site.web.action;

import com.alibaba.fastjson.JSON;
import com.baidu.ueditor.upload.Uploader;
import com.liuyun.site.context.Constant;
import com.liuyun.site.domain.User;
import com.liuyun.site.tool.interest.EndInterestCalculator;
import com.liuyun.site.tool.interest.InterestCalculator;
import com.liuyun.site.tool.interest.MonthEqualCalculator;
import com.liuyun.site.tool.interest.MonthInterestCalculator;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;


public class ToolAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ToolAction.class);
	private String userid;
	private String size;
	private double account;
	private double lilv;
	private int times;
	private int time_limit_day;
	private String type;
	private File upload;
	private File localUrl;
	private String uploadFileName;
	private String sep = File.separator;
	private double cropX;
	private double cropY;
	private double cropW;
	private double cropH;
	private String plugintype;

	public String imgurl() throws Exception {
		if (NumberUtils.getInt(this.userid) < 1) {
			this.userid = "" + NumberUtils.getInt(this.userid);
		}
		this.userid = (((this.userid == null) || (this.userid.equals(""))) ? ""
				: this.userid);
		this.size = (((this.size == null) || (this.size.equals(""))) ? ""
				: this.size);
		String[] sizes = { "big", "middle", "small" };
		List<String> sizelist = Arrays.asList(sizes);
		this.size = ((sizelist.contains(this.size)) ? this.size : "big");

		String url = "data" + this.sep + "avatar" + this.sep + this.userid
				+ "_avatar_" + this.size + ".jpg";
		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		url = contextPath + url;
		File avatarFile = new File(url);
		if (!avatarFile.exists()) {
			url = contextPath + "data" + this.sep + "images" + this.sep
					+ "avatar" + this.sep + "noavatar_" + this.size + ".gif";
		}
		logger.debug("IMG_URL:" + url);
		cteateImg(url);
		return null;
	}

	public String editorUploadImg() throws Exception {
		String newFileName = generateUploadFilename();
		String retMsg = "";
		if (this.upload == null) {
			retMsg = getRetMsg(1, "上传图片失败！", "");
		}
		if (retMsg.isEmpty()) {
			String imgUrl = upload(this.upload, this.uploadFileName,
					"/data/upload", newFileName + ".jpg");
			retMsg = getRetMsg(0, "上传图片成功！", imgUrl);
		}

		this.response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = this.response.getWriter();
		out.print(retMsg);
		out.flush();
		out.close();
		return null;
	}

	public String ueditorUploadImg() throws Exception {
//		Uploader up = new Uploader(this.request);
//		up.setSavePath("/data/upfiles");
//		String[] fileType = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
//		up.setAllowFiles(fileType);
//		up.setMaxSize(10000);
//		up.upload();
//		String ret = "{'original':'" + up.getOriginalName() + "','url':'"
//				+ up.getUrl() + "','title':'" + up.getTitle() + "','state':'"
//				+ up.getState() + "'}";
//		logger.info(ret);
//		this.response.getWriter().print(ret);
		return null;
	}

	private String getRetMsg(int flag, String msg, String imgUrl) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ERROR, Integer.valueOf(flag));
		map.put("message", msg);
		map.put("url", this.request.getContextPath() + imgUrl);
		return map.toString();
	}

	public String upload() throws Exception {
		Map<String, Object> map = ServletActionContext.getContext().getSession();
		User user = (User) map.get(Constant.SESSION_USER);
		logger.info("文件：" + this.upload);
		logger.info("文件名：" + this.uploadFileName);
		Date d = new Date();

		String destfilename2 = ServletActionContext.getServletContext().getRealPath("/data")
				+ this.sep + "temp" + this.sep + user.getUser_id() + ".jpg";

		logger.info(destfilename2);
		File imageFile = null;
		File imageFile2 = null;
		try {
			imageFile2 = new File(destfilename2);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		FileUtils.copyFile(this.upload, imageFile2);
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String retMsg = "";
		try {
			BufferedImage bi = ImageIO.read(imageFile2);
			if (bi == null) {
				logger.info("你输入的文件为无效图片，即图片文件不合法");
				retMsg = getRetMsg(1, "你输入的文件为无效图片，即图片文件不合法！", "");
				out.print(retMsg);
				out.flush();
				out.close();
				return null;
			}
			logger.info("你输入的文件为有效图片");
		} catch (IOException e) {
			e.printStackTrace();
		}

		String src = request.getContextPath() + "/data/temp/"
				+ user.getUser_id() + ".jpg";
		BufferedImage srcImage = null;
		BufferedImage destImage = null;
		File file = new File(destfilename2);
		int newWi = 0;
		int newHi = 0;
		try {
			srcImage = ImageIO.read(file);
			int w = srcImage.getWidth();
			int h = srcImage.getHeight();
			int minW = (w > h) ? w : h;
			double newWd = 300.0D / minW * w;
			double newHd = 300.0D / minW * h;
			newWi = (int) newWd;
			newHi = (int) newHd;
			destImage = new BufferedImage(newWi, newHi, 5);
			destImage.getGraphics().drawImage(
					srcImage.getScaledInstance(newWi, newHi, 4), 0, 0, null);
			ImageIO.write(destImage, "jpg", new File(destfilename2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("Print img:" + src);

		out.print("{");
		out.print("error: '',\n");
		out.print("msg: '" + src + "',\n");
		out.print("width: " + newWi + ",\n");
		out.print("height: " + newHi + "\n");
		out.print("}");
		out.flush();
		out.close();
		return null;
	}

	public String cropimg() throws Exception {
		try {
			Map<String, Object> map = ServletActionContext.getContext().getSession();
			User user = (User) map.get(Constant.SESSION_USER);
			String destfilename = ServletActionContext.getServletContext()
					.getRealPath("/data")
					+ this.sep + "temp" + this.sep + user.getUser_id() + ".jpg";
			logger.info(destfilename);
			cteateImg(destfilename);
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String saveAvatar() throws Exception {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/json;charset=UTF-8");
			int x = 0;
			int y = 0;
			int w = 0;
			int h = 0;
			if (this.cropX >= 0.0D)
				x = (int) this.cropX;
			if (this.cropY >= 0.0D)
				y = (int) this.cropY;
			if (this.cropW >= 0.0D)
				w = (int) this.cropW;
			if (this.cropH >= 0.0D)
				h = (int) this.cropH;

			logger.debug("X=" + x + ",Y=" + y + ",W=" + w + ",H=" + h);

			boolean re = operateImg(x, y, w, h);
			PrintWriter out = response.getWriter();
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("flag", Integer.valueOf(1));
			jsonMap.put(MSG, SUCCESS);
			jsonMap.put("useravatar", "");
			printJson(JSON.toJSONString(jsonMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean operateImg(int x, int y, int w, int h) throws IOException {
		Map<String, Object> map = ServletActionContext.getContext().getSession();
		User user = (User) map.get(Constant.SESSION_USER);
		String dataPath = ServletActionContext.getServletContext().getRealPath(
				"/data");
		String avatarDir = dataPath + this.sep + "avatar";
		String dest = avatarDir + this.sep + user.getUser_id()
				+ "_avatar_middle.jpg";
		String smalldest = avatarDir + this.sep + user.getUser_id()
				+ "_avatar_small.jpg";
		String src = ServletActionContext.getServletContext().getRealPath(
				"/data")
				+ this.sep + "temp" + this.sep + user.getUser_id() + ".jpg";
		BufferedImage srcImage = null;
		try {
			srcImage = ImageIO.read(new File(src));
			BufferedImage destImage = srcImage.getSubimage(x, y, w, h);
			BufferedImage lastImage = new BufferedImage(w, h, 5);
			lastImage.getGraphics().drawImage(destImage, 0, 0, null);
			BufferedImage compressImage = new BufferedImage(98, 98, 5);
			BufferedImage compressImage48 = new BufferedImage(48, 48, 5);
			compressImage.getGraphics().drawImage(
					lastImage.getScaledInstance(98, 98, 4), 0, 0, null);
			compressImage48.getGraphics().drawImage(
					lastImage.getScaledInstance(48, 48, 4), 0, 0, null);
			File avatarDirFile = new File(avatarDir);
			if (!avatarDirFile.exists()) {
				avatarDirFile.mkdir();
			}
			File newFile = new File(dest);
			File smallNewFile = new File(smalldest);
			logger.info("Avatar dest:" + dest);
			ImageIO.write(compressImage, "jpg", newFile);
			ImageIO.write(compressImage48, "jpg", smallNewFile);
			FileUtils.forceDelete(new File(src));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	private void cteateImg(String url) throws IOException {
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0L);
		OutputStream output = res.getOutputStream();
		if (url.toLowerCase().endsWith(".jpg")) {
			res.setContentType("image/jpeg");
		} else if (url.toLowerCase().endsWith(".gif")) {
			res.setContentType("image/gif");
		}
		InputStream imageIn = new FileInputStream(new File(url));
		BufferedInputStream bis = new BufferedInputStream(imageIn);
		BufferedOutputStream bos = new BufferedOutputStream(output);
		byte[] data = new byte[4096];
		int size = 0;
		size = bis.read(data);
		while (size != -1) {
			bos.write(data, 0, size);
			size = bis.read(data);
		}
		bis.close();
		bos.flush();
		bos.close();
		output.flush();
		output.close();
	}

	public String interest() throws Exception {
		String toolType = StringUtils.isNull(this.type);
		if (toolType.equals("month")) {
			InterestCalculator ic = new MonthEqualCalculator(this.account,
					this.lilv / 100.0D, this.times);

			ic.each();
			this.request.setAttribute("ic", ic);
			System.out.println(ic);
		} else if (toolType.equals("day")) {
			InterestCalculator ic = new MonthEqualCalculator(this.account,
					this.lilv / 100.0D, this.times);
			String tblixi = ic.eachDay();
			this.request.setAttribute("tblixi", tblixi);
		} else if (toolType.equals("monthInterest")) {
			InterestCalculator ic = new MonthInterestCalculator(this.account,
					this.lilv / 100.0D, this.times);

			ic.each();
			this.request.setAttribute("ic", ic);
		} else if (toolType.equals("monthEndInterest")) {
			InterestCalculator ic = new EndInterestCalculator(this.account,
					this.lilv / 100.0D, this.times * 30);

			ic.each();
			this.request.setAttribute("ic", ic);
		} else if (toolType.equals("dayEndInterest")) {
			InterestCalculator ic = new EndInterestCalculator(this.account,
					this.lilv / 100.0D, this.time_limit_day);

			ic.each();
			this.request.setAttribute("ic", ic);
		} else if ((!toolType.equals("phone")) && (toolType.equals("ip"))) {
			String area = getAreaByIp(StringUtils.isNull(this.request
					.getParameter("ip")));
			this.request.setAttribute("ip", area);
		}
		this.request.setAttribute("type", toolType);
		return SUCCESS;
	}

	public String plugin() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		return null;
	}

	public String validimg() throws Exception {
		genernateCaptchaImage();
		return null;
	}

	public String actionNotFound() throws Exception {
		return SUCCESS;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public double getAccount() {
		return this.account;
	}

	public void setAccount(double account) {
		this.account = account;
	}

	public double getLilv() {
		return this.lilv;
	}

	public void setLilv(double lilv) {
		this.lilv = lilv;
	}

	public int getTimes() {
		return this.times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getTime_limit_day() {
		return this.time_limit_day;
	}

	public void setTime_limit_day(int time_limit_day) {
		this.time_limit_day = time_limit_day;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public File getUpload() {
		return this.upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return this.uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public double getCropX() {
		return this.cropX;
	}

	public void setCropX(double cropX) {
		this.cropX = cropX;
	}

	public double getCropY() {
		return this.cropY;
	}

	public void setCropY(double cropY) {
		this.cropY = cropY;
	}

	public double getCropW() {
		return this.cropW;
	}

	public void setCropW(double cropW) {
		this.cropW = cropW;
	}

	public double getCropH() {
		return this.cropH;
	}

	public void setCropH(double cropH) {
		this.cropH = cropH;
	}

	public String getPlugintype() {
		return this.plugintype;
	}

	public void setPlugintype(String plugintype) {
		this.plugintype = plugintype;
	}

	public File getLocalUrl() {
		return this.localUrl;
	}

	public void setLocalUrl(File localUrl) {
		this.localUrl = localUrl;
	}
}
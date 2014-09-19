package com.liuyun.site.service.impl;

import com.liuyun.site.context.Global;
import com.liuyun.site.dao.LotteryDao;
import com.liuyun.site.dao.TenderDao;
import com.liuyun.site.domain.LotteryRule;
import com.liuyun.site.domain.User;
import com.liuyun.site.domain.WinningInformation;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.service.LotteryService;
import com.liuyun.site.tool.Page;
import com.liuyun.site.tool.lottery.LotteryMain;
import com.liuyun.site.util.DateUtils;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import java.util.List;
import org.apache.log4j.Logger;

public class LotteryServiceImpl implements LotteryService {
	private static Logger logger = Logger.getLogger(LotteryServiceImpl.class);
	private LotteryDao lotteryDao;
	private TenderDao tenderDao;

	public TenderDao getTenderDao() {
		return this.tenderDao;
	}

	public void setTenderDao(TenderDao tenderDao) {
		this.tenderDao = tenderDao;
	}

	public LotteryDao getLotteryDao() {
		return this.lotteryDao;
	}

	public void setLotteryDao(LotteryDao lotteryDao) {
		this.lotteryDao = lotteryDao;
	}

	public List<LotteryRule> lotteryList() {
		return this.lotteryDao.getList();
	}

	public void modify_lottery(LotteryRule lotteryRule) {
		this.lotteryDao.update(lotteryRule);
	}

	public void add_lottery(LotteryRule lotteryRule) {
		this.lotteryDao.add(lotteryRule);
	}

	public LotteryRule getLotteryById(long id) {
		return this.lotteryDao.getLotteryById(id);
	}

	public void winning_information_add(WinningInformation winningInformation) {
		this.lotteryDao.winning_information_add(winningInformation);
	}

	public List winningInformationList() {
		return this.lotteryDao.getWinningInformationList();
	}

	public int getWinningInformationByUseridCount(long userid,
			long winning_money) {
		return this.lotteryDao.getWinningInformationByUseridCount(userid,
				winning_money);
	}

	public double getWinningInformationByAwardSum(long status) {
		return this.lotteryDao.getWinningInformationByAwardSum(status);
	}

	public String initLottery(String type, String result, User user,
			int usertype) {
		List list = this.lotteryDao.getList();
		if (list.size() > 0) {
			LotteryRule lotteryRule = (LotteryRule) list.get(0);
			String start_date = StringUtils.isNull(lotteryRule.getStart_date());
			String end_date = StringUtils.isNull(lotteryRule.getEnd_date());
			if ((StringUtils.isBlank(start_date))
					&& (StringUtils.isBlank(start_date))) {
				return null;
			}
		}
		String data = result;

		int tenderTimes = this.tenderDao.getTotalTenderTimesByUserid(user
				.getUser_id());

		int times = this.lotteryDao.getWinningInformationByUseridCount(user
				.getUser_id(), 0L);

		if (tenderTimes > 0) {
			usertype = 1;
		}

		if (times > 0) {
			usertype = 1;

			long every_user_lottery_max_times = NumberUtils.getLong(Global
					.getValue("every_user_lottery_max_times"));
			every_user_lottery_max_times = (every_user_lottery_max_times < 1L) ? 3L
					: every_user_lottery_max_times;

			int tenderAddLotteryTimes = this.lotteryDao
					.getTenderAddLotteryTimes(user.getUser_id());
			if (tenderAddLotteryTimes + every_user_lottery_max_times > times) {
				every_user_lottery_max_times += tenderAddLotteryTimes;
			}
			if (times >= every_user_lottery_max_times) {
				data = "你当日抽奖次数已有" + times + "次,请明日继续";
				return data;
			}
		}

		if (StringUtils.isBlank(type)) {
			LotteryMain main = new LotteryMain();
			int choujia_mix_unit = NumberUtils.getInt(StringUtils.isNull(Global
					.getValue("choujia_mix_unit")));

			int n = LotteryMain.secondChoujiaMain(choujia_mix_unit, usertype);
			data = String.valueOf(n);

			double today_winning_award_sum = this.lotteryDao
					.getWinningInformationByAwardSum(1L);
			if (NumberUtils.getDouble(Global.getValue("lottery_max_award")) <= today_winning_award_sum) {
				n = 0;
				data = String.valueOf(n);
				return data;
			}

			String winning_moneys = Global.getValue("winning_moneys");
			String[] moneys = new String[0];
			if (winning_moneys != null) {
				moneys = winning_moneys.split(",");
			}
			if ((moneys != null) && (moneys.length > 0)) {
				for (int i = 0; i < moneys.length; ++i) {
					if (i == 0) {
						times = this.lotteryDao
								.getWinningInformationByUseridCount(user
										.getUser_id(), Integer
										.parseInt(moneys[i]));
						if (times >= 2) {
							n = 0;
							data = String.valueOf(n);
							return data;
						}
					}
					if (i == 1) {
						times = this.lotteryDao
								.getWinningInformationByUseridCount(user
										.getUser_id(), Integer
										.parseInt(moneys[i]));
					}
					if (i != 2)
						continue;
					times = times
							+ this.lotteryDao
									.getWinningInformationByUseridCount(user
											.getUser_id(), Integer
											.parseInt(moneys[i]));
					if (times >= 3) {
						n = 0;
						data = String.valueOf(n);
						return data;
					}
				}
			}

		} else {
			WinningInformation winningInformation = new WinningInformation();
			winningInformation.setUser_id(user.getUser_id());
			winningInformation.setUsername(user.getUsername());
			int status = checkLotteryStatus(result);
			winningInformation.setStatus(status);
			if (status == 1) {
				String lottertname = getLotteryName(result);
				winningInformation.setAward_name(lottertname);
			}
			winningInformation.setGmt_create(DateUtils.getNowTimeStr());
			this.lotteryDao.winning_information_add(winningInformation);
			return data;
		}

		return data;
	}

	private int checkLotteryStatus(String result) {
		if (result.equals("0")) {
			return 0;
		}
		return 1;
	}

	private String getLotteryName(String result) {
		String winning_money = "";
		String winning_moneys = Global.getValue("winning_moneys");
		String[] moneys = new String[0];
		if (winning_moneys != null) {
			moneys = winning_moneys.split(",");
			for (int i = 0; i < moneys.length; ++i) {
				if (result.equals(Integer.valueOf(i + 1))) {
					winning_money = moneys[i];
					break;
				}
			}
			return winning_money;
		}
		return winning_money;
	}

	public PageDataList getWinningInfortionList(int page, SearchParam param) {
		int total = this.lotteryDao.getWinningInformationCount(param);
		Page p = new Page(total, page);
		List list = this.lotteryDao.getWinningInformationList(p.getStart(), p
				.getPernum(), param);
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}
}
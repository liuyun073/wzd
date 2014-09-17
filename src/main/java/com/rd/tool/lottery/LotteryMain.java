package com.rd.tool.lottery;

import com.rd.context.Global;
import com.rd.util.NumberUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： LotteryMain   
 * 类描述： 抽奖 主类   
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 26, 2013 9:44:22 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 26, 2013 9:44:22 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class LotteryMain {
	public static int firstChoujiaMain(int usertype) {
		List<Reward> rlist = initRewards();

		Lottery lottery = new Lottery();

		List<Reward> rewards = null;
		int count = 0;
		if (usertype == 1) {
			rewards = lottery.getKeys(rlist, 5);
			if ((isWinner(rewards)) && (!rewards.isEmpty()) && (rewards.size() == 5)) {
				return 1;
			}

			rewards = lottery.getKeys(rlist, 4);
			if ((isWinner(rewards)) && (!rewards.isEmpty()) && (rewards.size() == 4)) {
				for (Reward r : rewards) {
					if (rewards.size() > 0) {
						if (r.getIndex() <= 1)
							return 2;
						if ((r.getIndex() > 1) && (r.getIndex() <= 3))
							return 3;
						if ((r.getIndex() > 3) && (r.getIndex() <= 6))
							return 4;
						if ((r.getIndex() > 6) && (r.getIndex() <= 10)) {
							return 5;
						}
					}
				}
			}
		}

		rewards = lottery.getKeys(rlist, 3);
		if ((isWinner(rewards)) && (!rewards.isEmpty())
				&& (rewards.size() == 3)) {
			for (Reward r : rewards) {
				if (rewards.size() > 0) {
					if (r.getIndex() <= 1)
						return 6;
					if ((r.getIndex() > 1) && (r.getIndex() <= 3))
						return 7;
					if ((r.getIndex() > 3) && (r.getIndex() <= 6))
						return 8;
					if ((r.getIndex() > 6) && (r.getIndex() <= 10)) {
						return 9;
					}
				}
			}
		}

		rewards = lottery.getKeys(rlist, 2);
		if ((isWinner(rewards)) && (!rewards.isEmpty())
				&& (rewards.size() == 2)) {
			for (Reward r : rewards) {
				if (rewards.size() > 0) {
					if (r.getIndex() <= 4) {
						return 10;
					}
					return 11;
				}
			}

		}

		return 0;
	}

	public static boolean isWinner(List<Reward> list) {
		boolean isWinner = false;

		for (int i = 0; i < list.size(); ++i) {
			for (int j = i + 1; j < list.size(); ++j) {
				if (((Reward) list.get(i)).index != ((Reward) list.get(j)).index) {
					return false;
				}

				isWinner = true;
			}

		}

		return isWinner;
	}

	public static List<Reward> initRewards() {
		List<Reward> rlist = new ArrayList<Reward>();

		rlist.add(new Reward(1, "1000", 1));

		rlist.add(new Reward(2, "500", 2));

		rlist.add(new Reward(3, "300", 5));

		rlist.add(new Reward(4, "200", 8));

		rlist.add(new Reward(5, "100", 11));

		rlist.add(new Reward(6, "50", 14));

		rlist.add(new Reward(7, "25", 10));

		rlist.add(new Reward(8, "20", 11));

		rlist.add(new Reward(9, "15", 8));

		rlist.add(new Reward(10, "10", 11));

		rlist.add(new Reward(11, "5", 20));
		return rlist;
	}

	public static int secondChoujiaMain(int i, int usertype) {
		String new_user_choujia_probability = Global.getValue("new_user_choujia_probability");
		String old_user_choujia_probability = Global.getValue("old_user_choujia_probability");
		String[] new_user_choujia_probability_array = new_user_choujia_probability.split(",");
		String[] old_user_choujia_probability_array = old_user_choujia_probability.split(",");
		int poor = old_user_choujia_probability_array.length - new_user_choujia_probability_array.length;
		Random r = new Random();
		int j = r.nextInt(i);

		if (usertype == 1) {
			for (int a = 0; a < old_user_choujia_probability_array.length; ++a) {
				double choujia_probability_number = NumberUtils.getDouble(old_user_choujia_probability_array[a]);
				if (j < i * choujia_probability_number)
					return 0 + a;
			}
		} else {
			for (int a = 0; a < new_user_choujia_probability_array.length; ++a) {
				double choujia_probability_number = NumberUtils.getDouble(new_user_choujia_probability_array[a]);
				if (j < i * choujia_probability_number) {
					return poor + a;
				}
			}
		}
		return 0;
	}
}
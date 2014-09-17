package com.rd.tool.lottery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： Lottery   
 * 类描述： 抽奖   
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 26, 2013 9:43:33 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 26, 2013 9:43:33 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class Lottery {
	public static List<Reward> randomList;

	public List<Reward> getKeys(List<Reward> rlist, int keyLength) {
		List<Reward> list = new ArrayList<Reward>();

		for (int i = 0; i < keyLength; ++i) {
			list.add(getKey(rlist));
		}

		if (list.size() < 0) {
			for (int i = 0; i < keyLength - 1; ++i) {
				list.add(getKey(rlist));
			}

		}

		return list;
	}

	private Reward getKey(List<Reward> rlist) {
		List<Reward> randomList = getRandomList(rlist);

		List<Integer> percentSteps = getPercentSteps(rlist);

		int maxPercentStep = ((Integer) percentSteps.get(percentSteps.size() - 1)).intValue();

		int randomStep = new Random().nextInt(maxPercentStep);

		int keyIndex = 0;

		int begin = 0;

		int end = 0;

		for (int i = 0; i < percentSteps.size(); ++i) {
			if (i == 0) {
				begin = 0;
			} else {
				begin = ((Integer) percentSteps.get(i - 1)).intValue();
			}

			end = ((Integer) percentSteps.get(i)).intValue();

			if ((randomStep <= begin) || (randomStep > end))
				continue;
			keyIndex = i;

			break;
		}

		return (Reward) randomList.get(keyIndex);
	}

	private List<Integer> getPercentSteps(List<Reward> rlist) {
		List<Integer> percentSteps = new ArrayList<Integer>();

		int percent = 0;

		for (Reward r : rlist) {
			percent += r.succPercent;

			percentSteps.add(Integer.valueOf(percent));
		}

		return percentSteps;
	}

	private List<Reward> getRandomList(List<Reward> rlist) {
		List<Reward> oldList = new ArrayList<Reward>(rlist);

		List<Reward> newList = new ArrayList<Reward>();

		int randomIndex = 0;

		int randomLength = 0;

		for (int i = 0; i < rlist.size(); ++i) {
			randomLength = oldList.size() - 1;

			if (randomLength != 0) {
				randomIndex = new Random().nextInt(randomLength);
			} else {
				randomIndex = 0;
			}

			newList.add(oldList.remove(randomIndex));
		}

		return newList;
	}
}
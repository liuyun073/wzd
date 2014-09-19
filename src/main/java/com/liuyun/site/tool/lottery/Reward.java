package com.liuyun.site.tool.lottery;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： Reward   
 * 类描述： 奖励bean
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 26, 2013 9:44:51 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 26, 2013 9:44:51 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class Reward {
	public int index;
	public String name;
	public int succPercent;

	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSuccPercent() {
		return this.succPercent;
	}

	public void setSuccPercent(int succPercent) {
		this.succPercent = succPercent;
	}

	public Reward(int index, String name, int succPercent) {
		this.index = index;

		this.name = name;

		this.succPercent = succPercent;
	}

	public String toString() {
		return "Reward [index=" + this.index + ", name=" + this.name
				+ ", succPercent=" + this.succPercent + "]";
	}
}
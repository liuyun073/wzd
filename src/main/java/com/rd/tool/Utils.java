package com.rd.tool;

import com.rd.tool.interest.InterestCalculator;
import com.rd.tool.interest.MonthInterestCalculator;

public class Utils {
	public static void main(String[] args) {
		InterestCalculator ic = new MonthInterestCalculator(100.0D, 0.12D, 3);
		ic.each();
		System.out.println(ic.toString());
	}

	public static double Mrpi(double p, double r, int mn) {
		double mr = r / 12.0D;

		double aprPow = Math.pow(1.0D + mr, mn);

		double monPay = p * mr * aprPow / (aprPow - 1.0D);

		return monPay;
	}

	public static double GetCashFee(double x, double y, double r, double maxCash) {
		if ((x <= 1500.0D) || (y <= 1500.0D))
			return r * x;
		if (y >= x) {
			if ((x > 1500.0D) && (x <= 30000.0D)) {
				return 3.0D;
			}
			return 5.0D;
		}

		if (y <= 30000.0D) {
			return 3.0D + (x - y) * r;
		}
		return 5.0D + (x - y) * r;
	}

	public static double GetCashFeeForAidai(double x, double y, double r,
			double maxCash) {
		if (y <= 0.0D)
			return 0.0D;
		if (y <= x) {
			return x * r;
		}
		return (x - y) * r;
	}

	public static double cashFeeForZsd(double x, double y, double r,
			double maxCash) {
		if (y <= 0.0D) {
			return 0.0D;
		}
		return r * x;
	}

	public static double GetZsdCashFee(double x, double y, double r,
			double maxCash) {
		if (y <= 0.0D)
			return r * x;
		if (y >= x) {
			return 0.0D;
		}
		return (x - y) * r;
	}

	public static double getCashFeeForlhd(double x, double y, double r,
			double maxCash) {
		if (y >= x) {
			return 0.0D;
		}
		if (y > 0.0D) {
			return (x - y) * r;
		}
		return x * r;
	}

	public static double getCashFeeForZRZB(double x, double y, double r,
			double maxCash) {
		if ((x <= 1500.0D) || (y <= 1500.0D))
			return r * x;
		if (y >= x) {
			if ((x > 1500.0D) && (x <= 30000.0D)) {
				return 3.0D;
			}
			return 5.0D;
		}

		if (y <= 30000.0D) {
			return 3.0D + (x - y) * r;
		}
		return 5.0D + (x - y) * r;
	}

	public static double getCashFeeForJJY(double x, double y, double r,
			double maxCash) {
		if (y >= x) {
			if ((x > 0.0D) && (x <= 30000.0D)) {
				return 3.0D;
			}
			return 5.0D;
		}

		if (y <= 30000.0D) {
			return 3.0D + (x - y) * r;
		}
		return 5.0D + (x - y) * r;
	}

	public static double getCashFeeForSSJB(double x, double y, double r,
			double maxCash, double tender_award) {
		if ((y >= x) || (tender_award >= x)) {
			return 0.0D;
		}
		if (y > 0.0D) {
			if ((tender_award > 0.0D) && (tender_award < x)
					&& (x - y - tender_award > 0.0D)) {
				return (x - y - tender_award) * r;
			}
			return (x - y) * r;
		}

		return x * r;
	}

	public static double getCashFeeForRYD(double x, double r, double money) {
		return money * r;
	}

	public static double getCashFeeForZrzbZero(double x, double y, double r,
			double maxCash) {
		if (y >= x) {
			if ((x > 1500.0D) && (x <= 30000.0D)) {
				return 0.0D;
			}
			return 0.0D;
		}

		if (y <= 30000.0D) {
			return 3.0D + (x - y) * r;
		}
		return 5.0D + (x - y) * r;
	}

	public static double getCashFeeForZrzbZero(double x, double r, double money) {
		if (x < money) {
			if (x >= 0.0D) {
				return (money - x) * r;
			}
			return money * r;
		}

		return 0.0D;
	}

	public static double GetLargeCashFee(double x, double y, double r,
			double maxCash) {
		if (y >= 500000.0D)
			return x / 10000.0D;
		if (y < 20000.0D) {
			if (y < 0.0D) {
				y = 0.0D;
			}
			return y / 10000.0D + (x - y) * r;
		}
		if (y >= x) {
			return x / 10000.0D;
		}
		return y / 10000.0D + (x - y) * r;
	}

	public static double getCashFeeForHuidai(double money, double x, double r) {
		if (x < money) {
			if (x >= 0.0D) {
				return (money - x) * r;
			}
			return money * r;
		}

		return 0.0D;
	}

	public static double getCashFeeForHndai(double x, double y, double r,
			double maxCash, double t, double o, double or) {
		double todayCashFee = 0.0D;
		if (t > 0.0D) {
			if (o > t) {
				todayCashFee = 0.0D;
			} else if (x > t) {
				todayCashFee = (t - o) * or;
			} else if (x > t - o)
				todayCashFee = (t - o) * or;
			else {
				todayCashFee = x * or;
			}

		} else {
			todayCashFee = 0.0D;
		}

		if ((y >= x) && (todayCashFee == 0.0D))
			return 3.0D;
		if ((y >= x) && (todayCashFee > 0.0D)) {
			return todayCashFee;
		}
		if (y > 0.0D) {
			return (x - y) * r + todayCashFee;
		}
		return x * r + todayCashFee;
	}

	public static double getCashFeeForJlct(double x, double r, double money,
			double lastsum) {
		double y = money - x;

		double fee = 0.0D;
		if (x < money) {
			if (x >= 0.0D) {
				if (lastsum > 0.0D) {
					if (y <= lastsum)
						fee = y * r;
					else if (y > lastsum) {
						fee = lastsum * r;
					}
				}
				return fee;
			}
			if (lastsum > 0.0D) {
				if (money <= lastsum)
					fee = money * r;
				else if (money > lastsum) {
					fee = lastsum * r;
				}
			}
			return fee;
		}

		return 0.0D;
	}
}
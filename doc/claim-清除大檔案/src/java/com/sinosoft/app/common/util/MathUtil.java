package com.sinosoft.app.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数学运算工具类 包含精确浮点数运算，加减乘除，四舍五入
 * @Company: sinosig
 * @author 中科软
 * @Date: 2011-07-28
 */

public class MathUtil extends org.apache.commons.lang.math.NumberUtils {
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 提供精确的加法运算。
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以後10位，以後的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以後的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以後几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点後保留几位
	 * @return 四舍五入後的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点後保留几位
	 * @return 四舍五入後的结果
	 */
	public static String roundReturnString(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		double d = b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		String s = preventScientificCounting(d, scale);
		return s;
	}

	public static String preventScientificCounting(double d) {
		// 16位整数位，两小数位
		DecimalFormat df = new DecimalFormat("###############0.00");
		String str = df.format(d);
		return str;
	}

	public static String preventScientificCounting(double d, int scale) {
		// 16位整数位，两小数位
		DecimalFormat df = null;
		StringBuffer zeronum = new StringBuffer();
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		} else if (scale > 0) {
			zeronum.append(".");
			for (int i = 1; i <= scale; i++) {
				zeronum.append("0");
			}
		}
		df = new DecimalFormat("###############0" + zeronum.toString());
		String str = df.format(d);
		return str;
	}

	public static int getRound(double dSource) {
		int iRound;
		BigDecimal deSource = new BigDecimal(dSource);
		iRound = deSource.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
		return iRound;
	}

	// 截断小数点後几位
	public static BigDecimal cutOff(BigDecimal bigDecimal, int scale) {
		// 16位整数位，两小数位
		DecimalFormat df = null;
		StringBuffer zeronum = new StringBuffer();
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		} else if (scale > 0) {
			zeronum.append(".");
			for (int i = 1; i <= scale; i++) {
				zeronum.append("0");
			}
		}
		df = new DecimalFormat("###############0" + zeronum.toString());
		bigDecimal = new BigDecimal(df.format(bigDecimal));
		return bigDecimal;
	}

	/**
	 * Double对象转double
	 * @param Double doubleObj
	 * @return double
	 * @author 中科软
	 */
	public static double transDoubleTodouble(Double doubleObj) {
		double doubleValue = Double.NaN;
		if (doubleObj != null && !doubleObj.isNaN()) {
			doubleValue = doubleObj.doubleValue();
		}
		return doubleValue;
	}

	/**
	 * Double对象转double,null则转为零
	 * @param Double doubleObj
	 * @return double
	 * @author 中科软
	 */
	public static double transDoubleNullToZero(Double doubleObj) {
		double doubleValue = transDoubleTodouble(doubleObj);
		if (Double.isNaN(doubleValue)) {
			doubleValue = 0.0;
		}
		return doubleValue;
	}
}

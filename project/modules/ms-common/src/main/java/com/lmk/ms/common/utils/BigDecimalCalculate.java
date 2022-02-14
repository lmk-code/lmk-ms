package com.lmk.ms.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 高精度的计算器
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
public class BigDecimalCalculate {
	
	/** 默认的小数位精度 */
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 提供精确的加法运算
	 * @param v1	被加数
	 * @param v2	加数
	 * @return		两个数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算
	 * @param v1	被减数
	 * @param v2	减数
	 * @return		两个数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 
	 * 提供精确的乘法运算
	 * @param v1	被乘数
	 * @param v2	乘数
	 * @return	两个数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算,当发生除不尽的情况时,
	 * 精确到小数点以后10位, 以后的数字四舍五入.
	 * 
	 * @param v1	被除数
	 * @param v2	除数
	 * @return	两个数的商
	 */
	public static double div(double v1, double v2) {
		return divide(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算.
	 * 当发生除不尽的情况时,由scale参数指 定精度,以后的数字四舍五入.
	 * @param v1	被除数
	 * @param v2	除数
	 * @param scale	小数位精度
	 * @return	两个参数的商
	 */
	public static double divide(double v1, double v2, int scale) {
		if (scale < 0) 
			scale = DEF_DIV_SCALE;

		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理
	 * @param v		需要四舍五入的数字
	 * @param scale	小数点后保留几位
	 * @return		四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) 
			scale = DEF_DIV_SCALE;

		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
	}
}
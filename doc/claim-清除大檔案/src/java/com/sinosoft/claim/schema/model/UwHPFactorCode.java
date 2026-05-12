package com.sinosoft.claim.schema.model;

import java.io.Serializable;

public class UwHPFactorCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static class Common {
		public static String RealPayFlag = "RealPayFlag";
	}

	public static class Car extends Common {
		public static String KindCarSumpaid = "KindCarSumpaid";
	}

	public static class NonCar extends Common {
	}

	public static class Acci extends Common {
		/** 意健险 分险别核赔金额 */
		public static String KindSumRealPay = "KindSumRealPay";
		/** 意健险 预赔金额 */
		public static String SumPrepaid = "SumPrepaid";
	}
}

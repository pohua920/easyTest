package com.tlg.db2.entity;

import java.math.BigDecimal;

import com.tlg.iBatis.IBatisBaseEntity;

public class Com580WK extends IBatisBaseEntity<BigDecimal> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 信用卡號
	 */
	private String wk01;
	/**
	 * 管控起日
	 */
	private Integer wk02;
	/**
	 * 管控起時
	 */
	private Integer wk05;
	/**
	 * 管控迄日
	 */
	private Integer wk04;
	/**
	 * 管控迄時
	 */
	private Integer wk06;
	/**
	 * 備註
	 */
	private String wk03;
	/**
	 * 輸入者
	 */
	private String wk91;
	/**
	 * 輸入日期
	 */
	private Integer wk92;
	/**
	 * 輸入時間
	 */
	private Integer wk95;
	/**
	 * 修改者
	 */
	private String wk93;
	/**
	 * 修改日期
	 */
	private Integer wk94;
	/**
	 * 修改時間
	 */
	private Integer wk96;
	/**
	 * 來源
	 */
	private String wk07;

	public String getWk01() {
		return wk01;
	}

	public void setWk01(String wk01) {
		this.wk01 = wk01;
	}

	public Integer getWk02() {
		return wk02;
	}

	public void setWk02(Integer wk02) {
		this.wk02 = wk02;
	}

	public Integer getWk05() {
		return wk05;
	}

	public void setWk05(Integer wk05) {
		this.wk05 = wk05;
	}

	public Integer getWk04() {
		return wk04;
	}

	public void setWk04(Integer wk04) {
		this.wk04 = wk04;
	}

	public Integer getWk06() {
		return wk06;
	}

	public void setWk06(Integer wk06) {
		this.wk06 = wk06;
	}

	public String getWk03() {
		return wk03;
	}

	public void setWk03(String wk03) {
		this.wk03 = wk03;
	}

	public String getWk91() {
		return wk91;
	}

	public void setWk91(String wk91) {
		this.wk91 = wk91;
	}

	public Integer getWk92() {
		return wk92;
	}

	public void setWk92(Integer wk92) {
		this.wk92 = wk92;
	}

	public Integer getWk95() {
		return wk95;
	}

	public void setWk95(Integer wk95) {
		this.wk95 = wk95;
	}

	public String getWk93() {
		return wk93;
	}

	public void setWk93(String wk93) {
		this.wk93 = wk93;
	}

	public Integer getWk94() {
		return wk94;
	}

	public void setWk94(Integer wk94) {
		this.wk94 = wk94;
	}

	public Integer getWk96() {
		return wk96;
	}

	public void setWk96(Integer wk96) {
		this.wk96 = wk96;
	}

	public String getWk07() {
		return wk07;
	}

	public void setWk07(String wk07) {
		this.wk07 = wk07;
	}
}
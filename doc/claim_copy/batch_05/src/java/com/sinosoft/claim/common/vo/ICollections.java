package com.sinosoft.claim.common.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * 连接的工具类
 * @author 中科软
 */
public class ICollections {
	/**
	 * 加上选项"所有"後返回
	 * @param old 原始集合
	 * @return 加上选项"所有"後的集合
	 */
	public static Collection<LabelValueBean> getCollectionWithAll(Collection<LabelValueBean> old) {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(old.size() + 1);
		result.add(new LabelValueBean("所有", ""));
		result.addAll(old);
		return result;
	}

	/**
	 * 得到有效状态列表
	 * @return 有效状态列表
	 */
	public static Collection<LabelValueBean> getValidStatusList() {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(2);
		result.add(new LabelValueBean("有效", "1"));
		result.add(new LabelValueBean("失效", "0"));
		return result;
	}

	/**
	 * 得到回访业务类型列表
	 * @return 启用回访业务类型列表
	 */
	public static Collection<LabelValueBean> getBackVisitTypeList() {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(2);
		result.add(new LabelValueBean("查勘回訪", "1"));
		result.add(new LabelValueBean("定損回訪", "2"));
		return result;
	}

	/**
	 * 得到回访客户满意度列表
	 * @return 启用回访客户满意度列表
	 */
	public static Collection<LabelValueBean> getCustomOpinionList() {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(5);
		result.add(new LabelValueBean("很不滿意", "1"));
		result.add(new LabelValueBean("不滿意", "2"));
		result.add(new LabelValueBean("一般", "3"));
		result.add(new LabelValueBean("滿意", "4"));
		result.add(new LabelValueBean("很滿意", "5"));

		return result;
	}

	/**
	 * 得到是与否列表
	 * @return 是与否列表
	 */
	public static Collection<LabelValueBean> getYesNoList() {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(2);
		result.add(new LabelValueBean("是", "1"));
		result.add(new LabelValueBean("否", "0"));
		return result;
	}

	/**
	 * 得到出险原因代码列表
	 * @return 列表
	 */
	public static Collection<LabelValueBean> getDamageCodeList() {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(29);
		result.add(new LabelValueBean("碰撞", "456"));
		result.add(new LabelValueBean("車與車碰撞", "621"));
		result.add(new LabelValueBean("車與物碰撞", "622"));
		result.add(new LabelValueBean("車與人碰撞", "623"));
		result.add(new LabelValueBean("玻璃破碎", "417"));
		result.add(new LabelValueBean("傾覆", "403"));
		result.add(new LabelValueBean("墜落", "411"));
		result.add(new LabelValueBean("盜搶", "409"));
		result.add(new LabelValueBean("自燃", "410"));
		result.add(new LabelValueBean("外界物體倒塌", "404"));
		result.add(new LabelValueBean("外界物體墜落", "405"));
		result.add(new LabelValueBean("火災", "401"));
		result.add(new LabelValueBean("爆炸", "402"));
		result.add(new LabelValueBean("暴雨", "001"));
		result.add(new LabelValueBean("暴風", "002"));
		result.add(new LabelValueBean("龍卷風", "003"));
		result.add(new LabelValueBean("雷擊", "004"));
		result.add(new LabelValueBean("洪水", "005"));
		result.add(new LabelValueBean("海嘯", "007"));
		result.add(new LabelValueBean("地陷", "008"));
		result.add(new LabelValueBean("冰陷", "011"));
		result.add(new LabelValueBean("雪崩", "012"));
		result.add(new LabelValueBean("崖崩", "022"));
		result.add(new LabelValueBean("雹災", "013"));
		result.add(new LabelValueBean("泥石流", "014"));
		result.add(new LabelValueBean("滑坡", "015"));
		result.add(new LabelValueBean("載運保險車輛的渡船遭受自然災害", "024"));
		result.add(new LabelValueBean("資金周轉不靈", "618"));
		result.add(new LabelValueBean("其他", "999"));
		return result;
	}

	/**
	 * 得到事故原因代码列表
	 * @return 列表
	 */
	public static Collection<LabelValueBean> getDamageTypeCodeList() {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(12);
		result.add(new LabelValueBean("疏忽大意、措施不當", "009"));
		result.add(new LabelValueBean("安全間距不夠", "001"));
		result.add(new LabelValueBean("超速行駛", "002"));
		result.add(new LabelValueBean("逆向行駛", "003"));
		result.add(new LabelValueBean("疲勞駕駛", "004"));
		result.add(new LabelValueBean("違章裝載", "006"));
		result.add(new LabelValueBean("制動失靈", "007"));
		result.add(new LabelValueBean("轉向失靈", "008"));
		result.add(new LabelValueBean("違章並線", "010"));
		result.add(new LabelValueBean("其他機械故障", "995"));
		result.add(new LabelValueBean("其他違章行駛", "996"));
		result.add(new LabelValueBean("其他", "999"));
		return result;
	}

	/**
	 * 得到事故原因代码列表
	 * @return 列表
	 */
	public static Collection<LabelValueBean> getIndemnityDutyList() {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(12);
		result.add(new LabelValueBean("全責", "0"));
		result.add(new LabelValueBean("主責", "1"));
		result.add(new LabelValueBean("同責", "2"));
		result.add(new LabelValueBean("次責", "3"));
		result.add(new LabelValueBean("無責", "4"));
		result.add(new LabelValueBean("其它", "9"));
		return result;
	}

	/**
	 * 得到损失部件代码列表
	 * @return 损失部件列表
	 */
	public static Collection<LabelValueBean> getPartCodeList() {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(4);
		result.add(new LabelValueBean("前部", "1"));
		// result.add(new LabelValueBean("左车身", "2"));
		// result.add(new LabelValueBean("右车身", "3"));
		result.add(new LabelValueBean("中部", "2"));
		result.add(new LabelValueBean("後部", "4"));

		// 原因：损失部件代码中增加玻璃一项
		// result.add(new LabelValueBean("玻璃", "5"));

		return result;
	}

	/**
	 * 得到核价意见代码列表
	 * @return 核价意见列表
	 */
	public static Collection<LabelValueBean> getVerifyPriceOpinionList() {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(6);
		result.add(new LabelValueBean("同意報價", "01"));
		result.add(new LabelValueBean("補充信息", "02"));
		result.add(new LabelValueBean("價格異議發回", "03"));
		result.add(new LabelValueBean("價格已修正", "04"));
		result.add(new LabelValueBean("向外詢價", "05"));
		result.add(new LabelValueBean("其它", "99"));
		return result;
	}

	public static Collection<LabelValueBean> getVerifyPriceVerpoOpinionList() {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(5);
		result.add(new LabelValueBean("同意報價", "01"));
		result.add(new LabelValueBean("補充信息", "02"));
		result.add(new LabelValueBean("價格異議發回", "03"));
		result.add(new LabelValueBean("價格已修正", "04"));
		result.add(new LabelValueBean("其它", "99"));
		return result;
	}

	/**
	 * 得到核损意见代码列表
	 * @return 核损意见列表
	 */
	public static Collection<LabelValueBean> getVerifyOpinionList() {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(5);
		result.add(new LabelValueBean("同意定損", "01"));
		result.add(new LabelValueBean("價格異議", "02"));
		result.add(new LabelValueBean("信息不充分", "03"));
		result.add(new LabelValueBean("出具檢驗報告", "04"));
		result.add(new LabelValueBean("其它", "99"));
		return result;
	}

	/**
	 * 得到修理厂类型代码列表
	 * @return 修理厂类型列表
	 */
	public static Collection<LabelValueBean> getRepairFactoryType() {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(3);
		result.add(new LabelValueBean("4S店", "01"));
		result.add(new LabelValueBean("一類廠", "02"));
		result.add(new LabelValueBean("二類廠以下", "03"));
		return result;
	}

	// begin以下为客户资料管理
	public static Collection<LabelValueBean> getCustomerKind() {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(2);
		result.add(new LabelValueBean("0-專業代理人", "0"));
		result.add(new LabelValueBean("1-兼業代理人", "1"));
		result.add(new LabelValueBean("2-經紀人", "2"));
		return result;
	}

	public static Collection<LabelValueBean> getCustomerFlag() {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(2);
		result.add(new LabelValueBean("0-準客戶", "0"));
		result.add(new LabelValueBean("1-正式客戶", "1"));
		return result;
	}

	public static Collection<LabelValueBean> getShareHolder() {
		Collection<LabelValueBean> result = new ArrayList<LabelValueBean>(2);
		result.add(new LabelValueBean("否", "0"));
		result.add(new LabelValueBean("是", "1"));
		return result;
	}

	/**
	 * 获得理赔结论下拉列表选项
	 * @return
	 */
	public static Collection<LabelValueBean> getCompensateResultList() {
		Collection<LabelValueBean> result = new LinkedList<LabelValueBean>();
		result.add(new LabelValueBean("給付", "1"));
		result.add(new LabelValueBean("拒付", "0"));
		return result;
	}

	/**
	 * 获得理算退回信息下拉列表选项
	 * @return
	 */
	public static Collection<LabelValueBean> getCompensateBackList() {
		Collection<LabelValueBean> result = new LinkedList<LabelValueBean>();
		result.add(new LabelValueBean("補充材料", "01"));
		result.add(new LabelValueBean("拒賠條件", "02"));
		result.add(new LabelValueBean("增補項目[修改按增補項目處理]", "03"));
		return result;
	}

	// end

	// add by start zhaohui 2007-6-18
	// 逐步将静态变量从平台jar包中放入此类维护
	public static final String TASK_CLAIM_CERTAINLOSS_UPDATE = "claim.certainloss.update";
	// add by end zhaohui

	/**
	 * 改派任务中处理人员选择时，人员权限判断对照 add by liping start Data 2007-10-12
	 */
	// 立案任务
	public static final String TASK_CLAIM_CLAIM_INSERT = "claim.claim.insert";
	// 查勘任务
	public static final String TASK_CLAIM_CHECK_INSERT = "claim.check.insert";
	// 车辆定损任务
	public static final String TASK_CLAIM_CERTA_INSERT = "claim.certaincarloss.insert";
	// 财产定损任务
	public static final String TASK_CLAIM_PROPC_INSERT = "claim.certainloss.insert";
	// 人伤定损任务
	public static final String TASK_CLAIM_WOUND_INSERT = "claim.certainpersonloss.insert";
	// 核损任务
	public static final String TASK_CLAIM_VERIF_INSERT = "claim.verifycarloss.insert";
	// 单证任务
	public static final String TASK_CLAIM_CERTI_INSERT = "claim.certify.insert";
	// 理算任务
	public static final String TASK_CLAIM_COMPP_INSERT = "claim.compensate.insert";
	// 核赔任务
	public static final String TASK_CLAIM_VERIC_INSERT = "claim.undwrt.deal";
	// 结案任务
	public static final String TASK_CLAIM_ENDCA_INSERT = "claim.endcase.insert";

	// add by liping end

}

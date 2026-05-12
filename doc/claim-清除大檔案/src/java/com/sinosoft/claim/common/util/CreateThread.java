package com.sinosoft.claim.common.util;

import com.sinosoft.claim.dto.domain.PrpDagentDto;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.services.sms.SmsSend;

/**
 * 功能:发短信新增一个线程 
 */

public class CreateThread implements Runnable {

	/**业务号码*/
	private String businessNo = "";
	/**业务人员*/
	private String nextHandlerCode = "";
	/**类型*/
	private String strType = "";
	private PrpLregist prpLregist = null;
	private PrpDagentDto prpDagentDto = null;
	/**
	 * 构造方法
	 * @param registNo 报案号码
	 * @param nextHandlerCode 处理人员
	 * @param strType 节点类型
	 */
	public CreateThread(String registNo, String nextHandlerCode, String strType) {
		this.businessNo = registNo;
		this.nextHandlerCode = nextHandlerCode;
		this.strType = strType;
		// 开始线程
		new Thread(this).start();

	}

	/**
	 * 构造方法
	 * @param prpLregistDto 报案对象
	 * @param prpDagentDto  代理人代码对象
	 * @param nextHandlerCode 处理人员
	 * @param strType 节点类型
	 */
	public CreateThread(PrpLregist prpLregistDto, PrpDagentDto prpDagentDto, String nextHandlerCode, String strType) {
		this.prpLregist = prpLregistDto;
		this.prpDagentDto = prpDagentDto;
		this.nextHandlerCode = nextHandlerCode;
		this.strType = strType;
		// 开始线程
		new Thread(this).start();

	}

	/** 
	 * 启动一个线程
	 */
	@Override
	public void run() {

		if ("ALLS".equals(strType)) {// 调度提交，需要現場處理發信息
			try {
				SmsSend.smsForSchedule(businessNo, nextHandlerCode);
			} catch (Exception e) {
				System.out.println("--------------------调度发送信息失败---------------------");
				e.printStackTrace();
			}
		} else if ("NOCK".equals(strType)) {// 调度提交，不需要現場處理發信息
			try {
				SmsSend.smsForSchedule1(businessNo, nextHandlerCode);
			} catch (Exception e) {
				System.out.println("--------------------调度发送信息失败---------------------");
				e.printStackTrace();
			}
		} else if ("agent".equals(strType)) {// 调度提交，代理业务发送短信
			try {
			} catch (Exception e) {
				System.out.println("--------------------代理来务调度发送信息失败---------------------");
				e.printStackTrace();
			}
		} else if ("regis".equals(strType)) {// 报案提交,个险非车发送短信
			try {
				SmsSend.smsForRegist(businessNo);
			} catch (Exception e) {
				System.out.println("--------------------报案发送信息失败---------------------");
				e.printStackTrace();
			}
		} else if ("claim".equals(strType)) {// 报案提交,个险非车发送短信
			try {
				SmsSend.smsForClaim(businessNo);
			} catch (Exception e) {
				System.out.println("--------------------立案发送信息失败---------------------");
				e.printStackTrace();
			}
		}
	}
}

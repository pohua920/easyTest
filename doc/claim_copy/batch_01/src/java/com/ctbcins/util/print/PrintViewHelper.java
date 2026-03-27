package com.ctbcins.util.print;

import org.springframework.beans.factory.annotation.Autowired;

import ins.framework.common.Page;

import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.sysframework.common.util.StringUtils;
/**
 * 公用的取值
 * 因為邏輯一致不用都分開寫這樣不好維護
 * mantis：CLM0072 ，處理人員：BK007 蘇哲，需求單編號：CLM0072.工程險追償理算書
 * @author bk007
 *
 */
public class PrintViewHelper {

	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private CompensateService compensateService;

	/**
	 * 取得追償經辦
	 * 因追償作業經辦代入異常故改用流程資料來帶入經辦人資料
	 * @param report
	 * @param compensateNo
	 */
	protected void getReplevyHandler(Report report,String compensateNo){
		Page handlerSwfLogList;
		try {
			handlerSwfLogList = workFlowService.findViewSwfLogAll("BUSINESSNO = '" + StringUtils.rightTrim(compensateNo) + "' AND NODETYPE = 'Broker' ORDER BY LOGNO DESC ", 1, 1);
			if(!handlerSwfLogList.getResult().isEmpty()){
				SwfLog swfLog = (SwfLog)handlerSwfLogList.getResult().get(0);
				if("00".equals(swfLog.getHandleDept())){
					report.setHandlerCode(swfLog.getHandlerCode());
					report.setHandlerName(swfLog.getHandlerName());
				}else {
					report.setSubHandlerCode(swfLog.getHandlerCode());
					report.setSubHandlerName(swfLog.getHandlerName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * mantis：CLM0028 ，處理人員：BK007 蘇哲，需求單編號：CLM0028 追償處理賠付對象管控、追償計算書增加追償說明
	 * 取出計算書備註
	 * @param compensateNo 計算書號
	 * @return 計算書備註
	 */
	protected void getContextByCompensateNo(Report report,String compensateNo) {
		report.setContext(compensateService.getContextByCompensateNo(compensateNo));
	}

}

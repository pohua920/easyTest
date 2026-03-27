package com.sinosoft.undwrt.undwrtBase.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.prpall.dbsvr.cb.DBPrpCPmain;
import com.sinosoft.prpall.dbsvr.pg.DBPrpPhead;
import com.sinosoft.prpall.dbsvr.tb.DBPrpTmain;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.undwrt.common.model.PrpDuser;
import com.sinosoft.undwrt.undwrtBase.model.UwNotion;
import com.sinosoft.undwrt.undwrtBase.model.UwNotionId;
import com.sinosoft.undwrt.undwrtBase.model.WfGrade;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfGradeService;

/**
 * 核保處理意見實現類.
 */
public class UwNotionServiceSpringImpl extends
		GenericDaoHibernate<UwNotion, UwNotionId> implements UwNotionService {

	/** 屬性rule字段的長度. */
	public static final int RULE_LENGTH = 70; // rule字段的长度

	/** 屬性定級信息接口. */
	private WfGradeService wfGradeService;

	/**
	 * 批量插入.
	 * 
	 * @param uwNotionList
	 *            核保意見列表
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService#insertAll(java.util.List)
	 */
	@Override
	public void insertAll(List<UwNotion> uwNotionList) throws Exception {

		super.saveAll(uwNotionList);
	}

	/**
	 * 保存審核意見.
	 * 
	 * @param uwNotionDto
	 *            核保意見類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService#saveNotion(com.sinosoft.undwrt.undwrtBase.model.UwNotion)
	 */
	@Override
	public void saveNotion(UwNotion uwNotionDto) throws Exception {

		super.save(uwNotionDto);
	}

	/**
	 * 將制單員的說明，插入到UwNotion表中。如果是出單員，則插入出單員意見.
	 * 
	 * @param wfLog
	 *            工作流日誌類
	 * @param iCertiType
	 *            業務類型
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService#insertUwNotionByMakeUser(com.sinosoft.undwrt.undwrtBase.model.WfLog,
	 *      java.lang.String)
	 */
	public void insertUwNotionByMakeUser(WfLog wfLog, String iCertiType)
			throws Exception {

		if (wfLog == null) {
			return;
		}

		UwNotion uwNotionDto = new UwNotion();
		UwNotionId uwNotionId = new UwNotionId();
		uwNotionDto.setId(uwNotionId);
		DBPrpTmain dbPrpTmain = new DBPrpTmain();
		DBPrpCPmain dbPrpCPmain = new DBPrpCPmain();
		DBPrpPhead dbPrpPhead = new DBPrpPhead();

		int intResult = -1;

		if (iCertiType.equals("T")) {// 判断业务类型
			intResult = dbPrpTmain.getInfo(wfLog.getBusinessNo());
			uwNotionDto.setHandleText(dbPrpTmain.getRemark());// 出单员的意见
		}

		if (iCertiType.equals("E")) {
			dbPrpPhead.getInfo(wfLog.getBusinessNo());

			intResult = dbPrpCPmain.getInfo(dbPrpPhead.getPolicyNo());
			//mantis： HAS0147，處理人員：Sam，需求單編號：HAS0147，旅平險WS變更保期修復
			//mantis： HAS0157，處理人員：Sam，需求單編號：HAS0157，TA旅平險WS調整
			if(!"3".equals(dbPrpCPmain.getUnderWriteFlag())){//3 自動核保通過
				uwNotionDto.setHandleText(dbPrpCPmain.getRemark());// 出单员的意见	
			}else{
				uwNotionDto.setHandleText("通過");// 出单员的意见
			}
			////mantis： HAS0147，處理人員：Sam，需求單編號：HAS0147，旅平險WS變更保期修復 End
		}

		if (intResult != -1) {
			uwNotionDto.getId().setFlowId(wfLog.getId().getFlowId());
			uwNotionDto.getId().setLogNo(wfLog.getId().getLogNo());

			if (!uwNotionDto.getHandleText().equals("")
					&& uwNotionDto.getHandleText() != null) {
				// 向UwNotion表插入出单员意见
				super.saveAll((List) ungroup(uwNotionDto));
			}
		}

	}

	/**
	 * 查詢處理意見.
	 * 
	 * @param flowID
	 *            工作流號
	 * @return 處理意見
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService#getPreHandleText(java.lang.String)
	 */
	@Override
	public String getPreHandleText(String flowID) throws Exception {
		String strSql = "";
		String strPreHandleText = "";// 前次核保人意见
		Iterator iterator = null;
		WfGrade wfGradeDto = null;
		UwNotion uwNotionDto = null;
		int intLogNo = 0;

		if (flowID != null) {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.flowId", flowID);
			queryRule.addNotEqual("id.gradeMode", "1");
			queryRule.addAscOrder("id.logNo");
			Collection colWfLog = wfGradeService.findListByQueryRule(queryRule);
			iterator = colWfLog.iterator();
			while (iterator.hasNext()) {
				wfGradeDto = (WfGrade) iterator.next();
				intLogNo = wfGradeDto.getId().getLogNo();
			}
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.flowId", flowID);
			queryRule.addEqual("id.logNo", intLogNo);
			queryRule.addAscOrder("id.logNo");
			queryRule.addAscOrder("id.lineNo");
			Collection colUwNotion = this.find(queryRule);
			iterator = colUwNotion.iterator();
			while (iterator.hasNext()) {
				uwNotionDto = (UwNotion) iterator.next();
				strPreHandleText += uwNotionDto.getHandleText();
			}
		}
		return strPreHandleText;
	}

	/**
	 * 按條件查詢多條數據.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的記錄集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService#findByConditions(ins.framework.common.QueryRule)
	 */
	@Override
	public List<UwNotion> findByConditions(QueryRule queryRule)
			throws Exception {

		List<UwNotion> list = super.find(queryRule);
		return list;
	}

	/**
	 * 刪除多條記錄.
	 * 
	 * @param list
	 *            要刪除的集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService#deleteList(java.util.List)
	 */
	@Override
	public void deleteList(List list) throws Exception {
		super.deleteAll(list);
		// TODO Auto-generated method stub

	}

	/**
	 * 拆分審批意見.
	 * 
	 * @param uwNotionDto
	 *            處理意見類
	 * @return 拆分后的集合
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService#ungroup(com.sinosoft.undwrt.undwrtBase.model.UwNotion)
	 */
	public List<UwNotion> ungroup(UwNotion uwNotionDto) {
		List<UwNotion> col = new ArrayList();
		UwNotion uwNotionDtoNew = null;
		UwNotionId uwNotionId = null;
		String[] arrHandleText = {}; // 审批意见拆分后的数组
		int i = 0;
		if (uwNotionDto.getHandleText() == null
				|| uwNotionDto.getHandleText().equals("")) {
			arrHandleText = new String[1];
			arrHandleText[0] = "";
		} else {
			// 拆分审批意见
			arrHandleText = StringUtils.split(uwNotionDto.getHandleText(),
					RULE_LENGTH);
		}
		for (i = 0; i < arrHandleText.length; i++) {
			uwNotionDtoNew = new UwNotion();
			uwNotionId = new UwNotionId();
			uwNotionId.setFlowId(uwNotionDto.getId().getFlowId());
			uwNotionId.setLineNo(i + 1);
			uwNotionId.setLogNo(uwNotionDto.getId().getLogNo());
			uwNotionDtoNew.setId(uwNotionId);
			uwNotionDtoNew.setHandleText(arrHandleText[i]);
			col.add(uwNotionDtoNew);
		}
		return col;
	}

	/**
	 * 獲取屬性定級信息接口.
	 * 
	 * @return 屬性定級信息接口的值
	 */
	public WfGradeService getWfGradeService() {
		return wfGradeService;
	}

	/**
	 * 設置屬性定級信息接口.
	 * 
	 * @param wfGradeService
	 *            待設置的定級信息接口的值
	 */
	public void setWfGradeService(WfGradeService wfGradeService) {
		this.wfGradeService = wfGradeService;
	}

}

package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.common.QueryRule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.prpall.blsvr.tb.BLPrpTmainSub;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtBase.model.UwNotion;
import com.sinosoft.undwrt.undwrtBase.model.UwNotionId;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDealTaskService;

/**
 * 核保系統提交任務服務實現類.
 */
public class CommonDealTaskServiceSpringImpl implements CommonDealTaskService {

	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;

	/** 屬性核保處理意見接口. */
	private UwNotionService uwNotionService;

	// add by wangjun 20130130
	/**
	 * 保存任務.
	 * 
	 * @param uwNotionDto
	 *            核保意見類
	 * @param prpDuserDto
	 *            用戶信息類
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDealTaskService#saveTask(com.sinosoft.undwrt.undwrtBase.model.UwNotion,
	 *      com.sinosoft.platform.dto.domain.PrpDuserDto)
	 */
	public void saveTask(UwNotion uwNotionDto, PrpDuserDto prpDuserDto)
			throws SQLException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		try {
			String strBusinessNoCI = "";
			String strWhere = "";
			String strFlowIDCI = "";
			WfLog wfLogDtoNew = null;
			BLPrpTmainSub BLPrpTmainSub = new BLPrpTmainSub();
			UwNotion uwNotionDtoNew = new UwNotion();
			Collection wfLogDtoList = new ArrayList();
			DateTime dateTime = new DateTime(new java.util.Date());

			WfLog wfLogDto = new WfLog();
			// 保存审批意见
			List<UwNotion> notionList = (List<UwNotion>) (uwNotionService
					.ungroup(uwNotionDto));
			uwNotionService.insertAll(notionList);

			// 更新工作流日志状态,报异常临时替换成下面的方式
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.flowId", uwNotionDto.getId().getFlowId());
			queryRule.addEqual("id.logNo", uwNotionDto.getId().getLogNo());
			wfLogDto = wfLogService.findByPrimaryKey(queryRule);
			// add by zhangruifeng 20071213 begin reason :
			// 修改由于使用后退按钮后重复提交导致投保单状态改变的bug
			// 当当前节点的状态为关闭0或已处理已提交状态时不允许再进行操作
			if ("0".equals(wfLogDto.getNodeStatus())
					|| "4".equals(wfLogDto.getNodeStatus())) {
				throw new Exception(
						internal.getText("undwrt.service.commonDealTask.workflowCheckPass")
								+ wfLogDto.getNodeName()
								+ internal
										.getText("undwrt.service.commonDealTask.dealFinish"));
			}
			// add by zhangruifeng 20071213 end
			// 更改车险提交时同时提交
			if ("T".equals(wfLogDto.getBusinessType())) {
				if ("A01".equals(wfLogDto.getRiskCode())
						|| "0502".equals(wfLogDto.getRiskCode())
						|| "0503".equals(wfLogDto.getRiskCode())
						|| "0510".equals(wfLogDto.getRiskCode())) {
					BLPrpTmainSub.getData(wfLogDto.getBusinessNo());
					if (BLPrpTmainSub.getSize() > 0
							&& "111".equals(BLPrpTmainSub.getArr(0).getFlag())) {
						strBusinessNoCI = BLPrpTmainSub.getArr(0)
								.getMainPolicyNo();
						QueryRule queryRule1 = QueryRule.getInstance();
						queryRule1.addEqual("businessNo", strBusinessNoCI);

						wfLogDtoList = wfLogService
								.findByQueryRuleList(queryRule1);

						Iterator itwflog = wfLogDtoList.iterator();

						if (itwflog.hasNext()) {
							wfLogDtoNew = (WfLog) itwflog.next();

							strFlowIDCI = wfLogDtoNew.getId().getFlowId();

							UwNotionId uwNotionId = new UwNotionId();

							uwNotionId.setFlowId(strFlowIDCI);
							queryRule = QueryRule.getInstance();
							queryRule.addEqual("id.flowId", strFlowIDCI);
							queryRule.addDescOrder("id.logNo");
							uwNotionId.setLogNo(wfLogService
									.getMaxLogNo(queryRule));

							uwNotionDtoNew.setId(uwNotionId);

							uwNotionDtoNew.setHandleText(uwNotionDto
									.getHandleText());
							List<UwNotion> notionList2 = (List<UwNotion>) (uwNotionService
									.ungroup(uwNotionDtoNew));
							uwNotionService.insertAll(notionList2);

						}
					}
				}
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// add by wangjun20130124
	/**
	 * 批量保存任務.
	 * 
	 * @param uwNotionList
	 *            核保意見類集合
	 * @param prpDuserDto
	 *            用戶信息類
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDealTaskService#saveBatchTask(java.util.Collection,
	 *      com.sinosoft.platform.dto.domain.PrpDuserDto)
	 */
	public void saveBatchTask(Collection uwNotionList, PrpDuserDto prpDuserDto)
			throws SQLException, Exception {
		try {
			DateTime dateTime = new DateTime(new java.util.Date());
			WfLog wfLogDto = new WfLog();
			for (Iterator i = uwNotionList.iterator(); i.hasNext();) {
				UwNotion uwNotionDto = (UwNotion) i.next();
				// 保存审批意见
				List<UwNotion> notionList = (List<UwNotion>) (uwNotionService
						.ungroup(uwNotionDto));
				uwNotionService.insertAll(notionList);
				// 更新工作流日志状态
				QueryRule queryRule = QueryRule.getInstance();
				queryRule
						.addEqual("id.flowId", uwNotionDto.getId().getFlowId());
				queryRule.addEqual("id.logNo", uwNotionDto.getId().getLogNo());
				wfLogDto = wfLogService.findByPrimaryKey(queryRule);
				wfLogDto.setDeptCode(prpDuserDto.getComCode());
				wfLogDto.setOperatorCode(prpDuserDto.getUserCode());
				wfLogDto.setOperatorName(prpDuserDto.getUserName());
				wfLogDto.setHandleTime(dateTime.current().toString()
						.substring(0, 19));
				wfLogDto.setNodeStatus("3");
				wfLogService.update(wfLogDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 獲取屬性工作流日誌接口.
	 * 
	 * @return 屬性工作流日誌接口的值
	 */
	public WfLogService getWfLogService() {
		return wfLogService;
	}

	/**
	 * 設置屬性工作流日誌接口.
	 * 
	 * @param wfLogService
	 *            待設置的工作流日誌接口的值
	 */
	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	/**
	 * 獲取屬性核保處理意見接口.
	 * 
	 * @return 屬性核保處理意見接口的值
	 */
	public UwNotionService getUwNotionService() {
		return uwNotionService;
	}

	/**
	 * 設置屬性核保處理意見接口.
	 * 
	 * @param uwNotionService
	 *            待設置的核保處理意見接口的值
	 */
	public void setUwNotionService(UwNotionService uwNotionService) {
		this.uwNotionService = uwNotionService;
	}

}

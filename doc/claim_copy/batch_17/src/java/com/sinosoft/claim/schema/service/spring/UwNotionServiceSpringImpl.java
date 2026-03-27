package com.sinosoft.claim.schema.service.spring;
/**
 * 核保核赔处理意见表接口
 * @author 中科软
 */
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.UwNotion;
import com.sinosoft.claim.schema.model.UwNotionId;
import com.sinosoft.claim.schema.model.WfGrade;
import com.sinosoft.claim.schema.model.WfLog;
import com.sinosoft.claim.schema.service.facade.UwNotionService;
import com.sinosoft.claim.schema.service.facade.WfGradeService;
import com.sinosoft.sysframework.common.util.StringUtils;

public class UwNotionServiceSpringImpl extends
		GenericDaoHibernate<UwNotion, UwNotionId> implements UwNotionService {	

	public static final int RULE_LENGTH = 70; // rule字段的长度
	
	private WfGradeService wfGradeService;
	

	@Override
	public void insertAll(List<UwNotion> uwNotionList) throws Exception {

		super.saveAll(uwNotionList);
	}

	@Override
	public void saveNotion(UwNotion uwNotionDto) throws Exception {

		super.save(uwNotionDto);
	}



	public void insertUwNotionByMakeUser(WfLog wfLog, String iCertiType) throws Exception {
		if (wfLog == null) {
			return;
		}

		UwNotion uwNotionDto = new UwNotion();
		UwNotionId uwNotionId = new UwNotionId();
		uwNotionDto.setId(uwNotionId);
		// DBPrpTmain dbPrpTmain = new DBPrpTmain();
		// DBPrpCPmain dbPrpCPmain = new DBPrpCPmain();
		// DBPrpPhead dbPrpPhead = new DBPrpPhead();

		int intResult = -1;

		// if(iCertiType.equals("T")){//判断业务类型
		// intResult = dbPrpTmain.getInfo(wfLog.getBusinessNo());
		// uwNotionDto.setHandleText(dbPrpTmain.getRemark());//出单员的意见
		// }
		//        
		// if(iCertiType.equals("E")){
		// dbPrpPhead.getInfo(wfLog.getBusinessNo());
		//            
		// intResult = dbPrpCPmain.getInfo(dbPrpPhead.getPolicyNo());
		// uwNotionDto.setHandleText(dbPrpCPmain.getRemark());//出单员的意见
		// }

		if (intResult != -1) {
			uwNotionDto.getId().setFlowId(wfLog.getId().getFlowId());
			uwNotionDto.getId().setLogNo(wfLog.getId().getLogNo());

			if (!uwNotionDto.getHandleText().equals("") && uwNotionDto.getHandleText() != null) {
				// 向UwNotion表插入出单员意见
				List<UwNotion> list = ungroup(uwNotionDto);
				if (list.size() > 0) {
					UwNotion uwNotion = list.get(0);
					String sql = "Delete From UwNotion Where FlowId = '" + uwNotion.getId().getFlowId() + "'" + "And LogNo = '" + uwNotion.getId().getLogNo() + "'";
					HibernateUtils.executeSql(super.getSession(), sql);
					/*
					 * //重新设置空格，後发现空格不能存到oracle for (Iterator i =
					 * collection.iterator(); i.hasNext(); ) { uwNotionDto =
					 * (UwNotionDto) i.next(); if (uwNotionDto.getHandleText()
					 * == null || uwNotionDto.getHandleText().equals("")) {
					 * uwNotionDto.setHandleText(" "); }
					 * dbUwNotion.insert(uwNotionDto); }
					 */
					super.saveAll(list);
				}
			}
		}
	}
	

	@Override
	public String getPreHandleText(String flowID) throws Exception {
//		String strSql = "";
	    String strPreHandleText = "";//前次核保人意见
	    Iterator<?> iterator = null;
	    WfGrade wfGradeDto = null;
	    UwNotion uwNotionDto = null;
	    int intLogNo = 0;
	    
	    if(flowID!=null){
	    	QueryRule queryRule=QueryRule.getInstance();
	    	queryRule.addEqual("id.flowId", flowID);
	    	queryRule.addNotEqual("id.gradeMode", "1");
	    	queryRule.addAscOrder("id.logNo");
	    	List<WfGrade> colWfLog = wfGradeService.findListByQueryRule(queryRule);
	    	iterator = colWfLog.iterator();
	    	while(iterator.hasNext()){
	    		wfGradeDto = (WfGrade)iterator.next();
	    		intLogNo = wfGradeDto.getId().getLogNo();
	    	}
	    	queryRule=QueryRule.getInstance();
	    	queryRule.addEqual("id.flowId", flowID);
	    	queryRule.addEqual("id.logNo", intLogNo);
	    	queryRule.addAscOrder("id.logNo");
	    	queryRule.addAscOrder("id.lineNo");
	    	List<UwNotion> colUwNotion = this.find(queryRule);
	    	iterator = colUwNotion.iterator();
	    	while(iterator.hasNext()){
	    		uwNotionDto = (UwNotion)iterator.next();
	    		strPreHandleText += uwNotionDto.getHandleText();
	    	}
	    }
	    return strPreHandleText;
	}

	@Override
	public List<UwNotion> findByConditions(QueryRule queryRule)
			throws Exception {
		List<UwNotion> list = super.find(queryRule);
		return list;
	}
	public List<UwNotion> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
		List<UwNotion> list = super.find(queryRule);
		return list;
	}
	@Override
    public void deleteList(List<?> list) throws Exception {
        super.deleteAll(list);
        
    }
	

	public List<UwNotion> ungroup(UwNotion uwNotionDto) {
		List<UwNotion> col = new ArrayList<UwNotion>();
		UwNotion uwNotionDtoNew = null;
		UwNotionId uwNotionId = null;
		String[] arrHandleText = {}; // 审批意见拆分後的数组
		int i = 0;
		if (uwNotionDto.getHandleText() == null || uwNotionDto.getHandleText().equals("")) {
			arrHandleText = new String[1];
			arrHandleText[0] = "";
		} else {
			// 拆分审批意见
			arrHandleText = StringUtils.split(uwNotionDto.getHandleText(), RULE_LENGTH);
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

	public WfGradeService getWfGradeService() {
		return wfGradeService;
	}

	public void setWfGradeService(WfGradeService wfGradeService) {
		this.wfGradeService = wfGradeService;
	}
	
	
}

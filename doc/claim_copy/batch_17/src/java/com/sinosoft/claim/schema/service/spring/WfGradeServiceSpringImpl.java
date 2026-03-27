package com.sinosoft.claim.schema.service.spring;
/**
 * 双核的业务级别修改轨迹接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.Collection;
import java.util.List;

import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.WfGrade;
import com.sinosoft.claim.schema.model.WfGradeId;
import com.sinosoft.claim.schema.model.WfLog;
import com.sinosoft.claim.schema.service.facade.WfGradeService;
import com.sinosoft.claim.schema.service.facade.WfLogService;

public class WfGradeServiceSpringImpl extends GenericDaoHibernate<WfGrade, WfGradeId>
		implements WfGradeService {

	private WfLogService wfLogService;
	
    public void saveWfGrade(String iFlowId, int iModelNo, int iNodeNo,String iBusinessType, String iBusinessNo, 
    		String iUserCode, String iOpertorCode ,String iGradeCode ,String iGradeValue ,String iMaxUsableRate ,String iBrokerRate ,
    		String iAgentRate ,String iOrgRate ,String iBreakevenRate ,String iExtRate1 ,String iExtRate2 ,String iExtRate3) throws Exception {
    	
		try {
			//保存定级轨迹信息
			this.saveWfGrade2(iFlowId ,iModelNo ,iNodeNo ,iBusinessType ,iBusinessNo ,iUserCode ,iOpertorCode ,iGradeCode ,
					iGradeValue ,iMaxUsableRate ,iBrokerRate ,iAgentRate ,iOrgRate ,iBreakevenRate ,iExtRate1 ,iExtRate2 ,iExtRate3);
			//核保核批通过的对定级信息的後续处理
			//blWfGradeAction.echoGrade(iDBManager,iBusinessType,iBusinessNo);
		}catch(Exception exception){
            throw exception;
        }
    }
    private PrpDuserService prpDuserService;
    private void saveWfGrade2(String iFlowId,
			int iModelNo, int iNodeNo, String iBusinessType,
			String iBusinessNo, String iUserCode, String iOpertorCode,
			String iGradeCode, String iGradeValue, String iMaxUsableRate,
			String iBrokerRate, String iAgentRate, String iOrgRate,
			String iBreakevenRate, String iExtRate1, String iExtRate2,
			String iExtRate3) {

		WfLog wfLogPreDto = new WfLog();
		WfLog wfLogCurrDto = new WfLog();
		//WfLogDto wfLogNextDto = new WfLogDto();//获取下一条Wflog数据(核保员处理後的下一条Wflog数据)
		//WfLogDto wfLogDto = new WfLogDto();
		//SWfNodeDto wfNodeDto = new SWfNodeDto();
		WfGrade wfGradeDto = null;//定级信息
//		BLPrpDuserFacade blPrpDuserFacade = new BLPrpDuserFacade();
		PrpDuser prpDuser = null;
		
//		ChgDate chgDate = new ChgDate();
		String strWherePart = "FlowID='" + iFlowId.trim() + "'";
		int intCount = 0;
		String strOpertorName = "";
		try {
			if(!iOpertorCode.equals("")){
				prpDuser = prpDuserService.findPrpDuser(iOpertorCode);
				if(prpDuser!=null){
					strOpertorName = prpDuser.getUserName();
				}
			}
			
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(strWherePart);
			intCount =  this.getCount(queryRule);
			
			//获取当前Wflog数据(核保员处理时的当前Wflog数据)
//			queryRule = QueryRule.getInstance();
//			queryRule.addEqual("id.flowId", iFlowId).addEqual("id.logNo", intCount-1);
			
			wfLogCurrDto = wfLogService.findByPrimaryKey(iFlowId,intCount-1);
			
			//获取上一条Wflog数据(核保员处理时的上一条Wflog数据)
//			queryRule = QueryRule.getInstance();
//			queryRule.addEqual("id.flowId", iFlowId).addEqual("id.logNo", intCount-2);
			wfLogPreDto = wfLogService.findByPrimaryKey(iFlowId,intCount-2);
			//wfLogNextDto = wfLogDto;
			//wfNodeDto = dbWfNode.findByPrimaryKey(wfLogNextDto.getModelNo(), wfLogNextDto.getNodeNo());
			//如果上一条Wflog的NodeNo为1则是从核心提交上来的业务，要记录手工和自动定级信息，否则只记录手工定级信息
		
			if(wfLogPreDto.getNodeNo()==1){
				//自动定级
				
					wfGradeDto = getAutoGrade(iFlowId,wfLogCurrDto.getId().getLogNo(),iModelNo,wfLogCurrDto.getNodeNo(),iOpertorCode,strOpertorName,iBusinessType,iBusinessNo);
	
				if(!wfGradeDto.getGradeCode().equals("")){
					insert(wfGradeDto);
				}
				//手工定级
				wfGradeDto = getManualGrade(iFlowId,wfLogCurrDto.getId().getLogNo(),iModelNo,wfLogCurrDto.getNodeNo(),iOpertorCode,strOpertorName,iBusinessType,iBusinessNo,
						iGradeCode,iGradeValue,iMaxUsableRate,iBrokerRate,iAgentRate,iOrgRate,iBreakevenRate,iExtRate1,iExtRate2,iExtRate3);
				insert(wfGradeDto);
			}else{
	
				wfGradeDto = getManualGrade(iFlowId,wfLogCurrDto.getId().getLogNo(),iModelNo,wfLogCurrDto.getNodeNo(),iOpertorCode,strOpertorName,iBusinessType,iBusinessNo,
						iGradeCode,iGradeValue,iMaxUsableRate,iBrokerRate,iAgentRate,iOrgRate,iBreakevenRate,iExtRate1,iExtRate2,iExtRate3);
				insert(wfGradeDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
     * 
     * @param flowID
     * @return
     * @throws Exception
     */
	@Override
    public String getPreGradeCode(String flowID)throws Exception {
//    	String strSql = "";
        String strPreGradeCode = "";//前次分级
        
        WfGrade wfGradeDto = null;
        
        if(flowID!=null){
        	
	    	QueryRule queryRule=QueryRule.getInstance();
	    	queryRule.addEqual("id.flowId", flowID);
	    	queryRule.addNotEqual("id.gradeMode", "1");
	    	queryRule.addAscOrder("id.logNo");
	    	List<WfGrade> colWfGrade = this.findListByQueryRule(queryRule);
           if(colWfGrade.size()>0){
          	  	wfGradeDto = (WfGrade)colWfGrade.get(0);
          	  	strPreGradeCode = wfGradeDto.getGradeCode();
            }
        }
        
        return strPreGradeCode;
    }
    
    /**
     * 插入一条数据
     * @param wfGradeDto wfGradeDto
     * @throws Exception
     */
	@Override
    public void insert(WfGrade wfGradeDto)
        throws Exception{
//        DBManager dbManager = new DBManager();
//        BLWfGradeAction blWfGradeAction = new BLWfGradeAction();
//        try{
//            dbManager.open("undwrtDataSource");
//            dbManager.beginTransaction();
//            //插入记录
//            blWfGradeAction.insert(dbManager,wfGradeDto);
//            dbManager.commitTransaction();
//        }catch(Exception exception){
//            dbManager.rollbackTransaction();
//            throw exception;
//        }finally{
//            dbManager.close();
//        }
    }

    /**
     * 按主键删除一条数据
     * @param flowId FlowId
     * @param logNo LogNo
     * @param gradeMode GradeMode
     * @throws Exception
     */
	@Override
    public void delete(String flowId,int logNo,String gradeMode)
        throws Exception{
//        DBManager dbManager = new DBManager();
//        BLWfGradeAction blWfGradeAction = new BLWfGradeAction();
//        try{
//            dbManager.open("undwrtDataSource");
//            dbManager.beginTransaction();
//            //删除记录
//            blWfGradeAction.delete(dbManager,flowId, logNo, gradeMode);
//            dbManager.commitTransaction();
//        }catch(Exception exception){
//            dbManager.rollbackTransaction();
//            throw exception;
//        }finally{
//            dbManager.close();
//        }
    }

    /**
     * 按条件删除数据
     * @param conditions 删除条件
     * @throws Exception
     */
	@Override
    public void deleteByConditions(String conditions)
        throws Exception{
//        DBManager dbManager = new DBManager();
//        BLWfGradeAction blWfGradeAction = new BLWfGradeAction();
//        try{
//            dbManager.open("undwrtDataSource");
//            dbManager.beginTransaction();
//            //按条件删除记录
//            blWfGradeAction.deleteByConditions(dbManager,conditions);
//            dbManager.commitTransaction();
//        }catch(Exception exception){
//            dbManager.rollbackTransaction();
//            throw exception;
//        }finally{
//            dbManager.close();
//        }
    }

    /**
     * 按主键更新一条数据(主键本身无法变更)
     * @param wfGradeDto wfGradeDto
     * @throws Exception
     */
	@Override
    public void update(WfGrade wfGradeDto) {
//        DBManager dbManager = new DBManager();
//        BLWfGradeAction blWfGradeAction = new BLWfGradeAction();
//        try{
//            dbManager.open("undwrtDataSource");
//            dbManager.beginTransaction();
//            //更新记录
//            blWfGradeAction.update(dbManager,wfGradeDto);
//            dbManager.commitTransaction();
//        }catch(Exception exception){
//            dbManager.rollbackTransaction();
//            throw exception;
//        }finally{
//            dbManager.close();
//        }
    }

    /**
     * 按主键查找一条数据
     * @param flowId FlowId
     * @param logNo LogNo
     * @param gradeMode GradeMode
     * @return wfGradeDto wfGradeDto
     * @throws Exception
     */
	@Override
    public WfGrade findByPrimaryKey(String flowId,int logNo,String gradeMode)
        throws Exception{
//        DBManager dbManager = new DBManager();
//        BLWfGradeAction blWfGradeAction = new BLWfGradeAction();
        //声明DTO
        WfGrade wfGradeDto = null;
//        try{
//            dbManager.open("undwrtDataSource");
//            //查询数据,赋值给DTO
//            wfGradeDto = blWfGradeAction.findByPrimaryKey(dbManager,flowId, logNo, gradeMode);
//        }catch(Exception exception){
//            throw exception;
//        }finally{
//            dbManager.close();
//        }
        return wfGradeDto;
    }


    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return PageRecord 查询的一页的结果
     * @throws Exception
     */
    public Page findByConditions(String conditions,int pageNo,int rowsPerPage)
        throws Exception{
    	Page page = new Page();

//        if(conditions.trim().length()==0){
//            conditions = "1=1";
//        }
//
//        DBManager dbManager = new DBManager();
//        BLWfGradeAction blWfGradeAction = new BLWfGradeAction();
//        try{
//            dbManager.open("undwrtDataSource");
//            pageRecord = blWfGradeAction.findByConditions(dbManager,conditions,pageNo,rowsPerPage);
//        }catch(Exception exception){
//            throw exception;
//        }finally{
//            dbManager.close();
//        }
        return page;
    }
    /**
     * 按条件查询多条数据
     * @param DBManager dbManager
     * @param conditions 查询条件
     * @return Collection 包含wfGradeDto的集合
     * @throws Exception
     */
	@Override
    public List<WfGrade> findByConditions(QueryRule queryRule) throws Exception{

        List<WfGrade> list = null;
        list = this.find(queryRule);

        return list;
    }


	@Override
	public WfGrade getManualGrade(String iFlowId, int iLogNo, int iModelNo,
			int iNodeNo, String iOperatorCode, String iOperatorName,
			String iBusinessType, String iBusinessNo, String iGradeCode,
			String iGradeValue, String iMaxUsableRate, String iBrokerRate,
			String iAgentRate, String iOrgRate, String iBreakevenRate,
			String iExtRate1, String iExtRate2, String iExtRate3)
			throws Exception {
		return null;
	}

//	public void echoGrade(String iBusinessType,
//			String iBusinessNo, String iClassCode, String strRiskCode,
//			String iFlowId) throws Exception {
//
//		WfGrade wfGradeDto = null;
//		DBPrpTmain dbPrpTmain = new DBPrpTmain(dbManager);
//		DBPrpCmain dbPrpCmain = new DBPrpCmain(dbManager);
//		DBPrpPhead dbPrpPhead = new DBPrpPhead(dbManager);
//		DBPrpTgrade dbPrpTgrade = new DBPrpTgrade(dbManager);
//		DBPrpCgrade dbPrpCgrade = new DBPrpCgrade(dbManager);
//		DBPrpCPgrade dbPrpCPgrade = new DBPrpCPgrade(dbManager);
//		PrpCmainDto prpCmainDto = null;
//		PrpPheadDto prpPheadDto = null;
//		PrpTgradeDto prpTgradeDto = null;
//		PrpCgradeDto prpCgradeDto = null;
//		PrpCPgradeDto prpCPgradeDto = null;
//		Collection col = null;
//		Iterator iterator = null;
//		String strSql = "";
//		strSql = "";
//		String strPolicyNo = "";
//		boolean isUnderWrite = false;//是否核保通过
//		boolean isAutoUnderWrite = false;//是否自动核保通过
//		String strUnderWriteFlag = "";
//		if(!"D".equals(ConstantCodes.carClassMap.get(iClassCode)) && (!strRiskCode.equals("9997") && !strRiskCode.equals("9998") && !strRiskCode.equals("9999"))){
//			if(iBusinessType.equals("T")){
//				strSql = "ProposalNo='" + iBusinessNo + "'";
//				strUnderWriteFlag = dbPrpTmain.findByPrimaryKey(iBusinessNo).getUnderWriteFlag();
//				if(strUnderWriteFlag.equals("1")||strUnderWriteFlag.equals("5")){
//					isUnderWrite = true;
//				}else if(strUnderWriteFlag.equals("3")||strUnderWriteFlag.equals("6")){
//					isAutoUnderWrite = true;
//				}
//			}else if(iBusinessType.equals("P")){
//				strSql = "PolicyNo='" + iBusinessNo + "'";
//				strUnderWriteFlag = dbPrpCmain.findByPrimaryKey(iBusinessNo).getUnderWriteFlag();
//				if(strUnderWriteFlag.equals("1")||strUnderWriteFlag.equals("5")){
//					isUnderWrite = true;
//				}else if(strUnderWriteFlag.equals("3")||strUnderWriteFlag.equals("6")){
//					isAutoUnderWrite = true;
//				}
//			}else if(iBusinessType.equals("E")){
//				strSql = "EndorseNo='" + iBusinessNo + "'";
//				strUnderWriteFlag = dbPrpPhead.findByPrimaryKey(iBusinessNo).getUnderWriteFlag();
//				if(strUnderWriteFlag.equals("1")||strUnderWriteFlag.equals("5")){
//					isUnderWrite = true;
//				}else if(strUnderWriteFlag.equals("3")||strUnderWriteFlag.equals("6")){
//					isAutoUnderWrite = true;
//				}
//			}
//			if(isUnderWrite){
//				//获取定级轨迹信息 begin
//				QueryRule queryRule = QueryRule.getInstance();
//				if(iBusinessType.equals("T")){
//					queryRule.addEqual("id.flowId", iFlowId).addEqual("id.gradeMode", "0").addAscOrder("id.logNo");
//				}else if(iBusinessType.equals("P")){
//					queryRule.addEqual("id.flowId", iFlowId).addEqual("id.gradeMode", "0").addAscOrder("id.logNo");
//				}else if(iBusinessType.equals("E")){
//					queryRule.addEqual("id.flowId", iFlowId).addEqual("id.gradeMode", "0").addAscOrder("id.logNo");
//
//				}
//
//				col = this.findByConditions(queryRule);
//				iterator = col.iterator();
//				while(iterator.hasNext()){
//					wfGradeDto = (WfGrade)iterator.next();
//				}
//				//获取定级轨迹信息 end
//				//获取保单号 begin
//				if(iBusinessType.equals("T")){
//					strSql = "ProposalNo='" + iBusinessNo + "'";
//					col = null;
//					iterator = null;
//					col = dbPrpCmain.findByConditions(strSql);
//					iterator = col.iterator();
//					while(iterator.hasNext()){
//						prpCmainDto = (PrpCmainDto)iterator.next();
//						strPolicyNo = prpCmainDto.getPolicyNo();
//					}
//				}else if(iBusinessType.equals("P")){
//					strPolicyNo = iBusinessNo;
//				}else if(iBusinessType.equals("E")){
//					strSql = "EndorseNo='" + iBusinessNo + "'";
//					col = null;
//					iterator = null;
//					col = dbPrpPhead.findByConditions(strSql);
//					iterator = col.iterator();
//					while(iterator.hasNext()){
//						prpPheadDto = (PrpPheadDto)iterator.next();
//						strPolicyNo = prpPheadDto.getPolicyNo();
//					}
//				}
//				//获取保单号 end
//				if(wfGradeDto!=null){
//					if(iBusinessType.equals("T")||iBusinessType.equals("P")){
//						prpTgradeDto = dbPrpTgrade.findByPrimaryKey(iBusinessNo);
//						prpCgradeDto = dbPrpCgrade.findByPrimaryKey(strPolicyNo);
//						if(prpTgradeDto!=null){
//							prpTgradeDto.setManualGradeCode(wfGradeDto.getGradeCode());
//							prpTgradeDto.setManualGradeValue(wfGradeDto.getGradeValue());
//							prpTgradeDto.setManualMaxUsableRate(wfGradeDto.getMaxUsableRate());
//							prpTgradeDto.setManualOrgRate(wfGradeDto.getOrgRate());
//							prpTgradeDto.setManualAgentRate(wfGradeDto.getAgentRate());
//							prpTgradeDto.setManualBreakevenRate(wfGradeDto.getBreakevenRate());
//							prpTgradeDto.setManualBrokerRate(wfGradeDto.getBrokerRate());
//							prpTgradeDto.setManualExt1Rate(wfGradeDto.getExtRate1());
//							prpTgradeDto.setManualExt2Rate(wfGradeDto.getExtRate2());
//							prpTgradeDto.setManualExt3Rate(wfGradeDto.getExtRate3());
//							dbPrpTgrade.update(prpTgradeDto);
//						}
//						if(prpCgradeDto!=null){
//							prpCgradeDto.setManualGradeCode(wfGradeDto.getGradeCode());
//							prpCgradeDto.setManualGradeValue(wfGradeDto.getGradeValue());
//							prpCgradeDto.setManualMaxUsableRate(wfGradeDto.getMaxUsableRate());
//							prpCgradeDto.setManualOrgRate(wfGradeDto.getOrgRate());
//							prpCgradeDto.setManualAgentRate(wfGradeDto.getAgentRate());
//							prpCgradeDto.setManualBreakevenRate(wfGradeDto.getBreakevenRate());
//							prpCgradeDto.setManualBrokerRate(wfGradeDto.getBrokerRate());
//							prpCgradeDto.setManualExt1Rate(wfGradeDto.getExtRate1());
//							prpCgradeDto.setManualExt2Rate(wfGradeDto.getExtRate2());
//							prpCgradeDto.setManualExt3Rate(wfGradeDto.getExtRate3());
//							dbPrpCgrade.update(prpCgradeDto);
//						}
//					}else if(iBusinessType.equals("E")){
//						prpCPgradeDto = dbPrpCPgrade.findByPrimaryKey(strPolicyNo);
//						if(prpCPgradeDto!=null){
//							prpCPgradeDto.setManualGradeCode(wfGradeDto.getGradeCode());
//							prpCPgradeDto.setManualGradeValue(wfGradeDto.getGradeValue());
//							prpCPgradeDto.setManualMaxUsableRate(wfGradeDto.getMaxUsableRate());
//							prpCPgradeDto.setManualOrgRate(wfGradeDto.getOrgRate());
//							prpCPgradeDto.setManualAgentRate(wfGradeDto.getAgentRate());
//							prpCPgradeDto.setManualBreakevenRate(wfGradeDto.getBreakevenRate());
//							prpCPgradeDto.setManualBrokerRate(wfGradeDto.getBrokerRate());
//							prpCPgradeDto.setManualExt1Rate(wfGradeDto.getExtRate1());
//							prpCPgradeDto.setManualExt2Rate(wfGradeDto.getExtRate2());
//							prpCPgradeDto.setManualExt3Rate(wfGradeDto.getExtRate3());
//							dbPrpCPgrade.update(prpCPgradeDto);
//						}
//						prpCgradeDto = dbPrpCgrade.findByPrimaryKey(strPolicyNo);
//						if(prpCgradeDto!=null){
//							prpCgradeDto.setManualGradeCode(wfGradeDto.getGradeCode());
//							prpCgradeDto.setManualGradeValue(wfGradeDto.getGradeValue());
//							prpCgradeDto.setManualMaxUsableRate(wfGradeDto.getMaxUsableRate());
//							prpCgradeDto.setManualOrgRate(wfGradeDto.getOrgRate());
//							prpCgradeDto.setManualAgentRate(wfGradeDto.getAgentRate());
//							prpCgradeDto.setManualBreakevenRate(wfGradeDto.getBreakevenRate());
//							prpCgradeDto.setManualBrokerRate(wfGradeDto.getBrokerRate());
//							prpCgradeDto.setManualExt1Rate(wfGradeDto.getExtRate1());
//							prpCgradeDto.setManualExt2Rate(wfGradeDto.getExtRate2());
//							prpCgradeDto.setManualExt3Rate(wfGradeDto.getExtRate3());
//							dbPrpCgrade.update(prpCgradeDto);
//						}
//					}
//				}
//			}
//			if(isAutoUnderWrite){
//				//获取保单号 begin
//				if(iBusinessType.equals("T")){
//					strSql = "ProposalNo='" + iBusinessNo + "'";
//					col = null;
//					iterator = null;
//					col = dbPrpCmain.findByConditions(strSql);
//					iterator = col.iterator();
//					while(iterator.hasNext()){
//						prpCmainDto = (PrpCmainDto)iterator.next();
//						strPolicyNo = prpCmainDto.getPolicyNo();
//					}
//				}else if(iBusinessType.equals("P")){
//					strPolicyNo = iBusinessNo;
//				}else if(iBusinessType.equals("E")){
//					strSql = "EndorseNo='" + iBusinessNo + "'";
//					col = null;
//					iterator = null;
//					col = dbPrpPhead.findByConditions(strSql);
//					iterator = col.iterator();
//					while(iterator.hasNext()){
//						prpPheadDto = (PrpPheadDto)iterator.next();
//						strPolicyNo = prpPheadDto.getPolicyNo();
//					}
//				}
//				//获取保单号 end
//				if(iBusinessType.equals("T")||iBusinessType.equals("P")){
//					prpTgradeDto = dbPrpTgrade.findByPrimaryKey(iBusinessNo);
//					prpCgradeDto = dbPrpCgrade.findByPrimaryKey(strPolicyNo);
//					if(prpTgradeDto!=null){
//						prpTgradeDto.setManualGradeCode(prpTgradeDto.getAutoGradeCode());
//						prpTgradeDto.setManualGradeValue(prpTgradeDto.getAutoGradeValue());
//						prpTgradeDto.setManualMaxUsableRate(prpTgradeDto.getAutoMaxUsableRate());
//						prpTgradeDto.setManualOrgRate(prpTgradeDto.getAutoOrgRate());
//						prpTgradeDto.setManualAgentRate(prpTgradeDto.getAutoAgentRate());
//						prpTgradeDto.setManualBreakevenRate(prpTgradeDto.getAutoBreakevenRate());
//						prpTgradeDto.setManualBrokerRate(prpTgradeDto.getAutoBrokerRate());
//						prpTgradeDto.setManualExt1Rate(prpTgradeDto.getAutoExt1Rate());
//						prpTgradeDto.setManualExt2Rate(prpTgradeDto.getAutoExt2Rate());
//						prpTgradeDto.setManualExt3Rate(prpTgradeDto.getAutoExt3Rate());
//						dbPrpTgrade.update(prpTgradeDto);
//					}
//					if(prpCgradeDto!=null){
//						prpCgradeDto.setManualGradeCode(prpCgradeDto.getAutoGradeCode());
//						prpCgradeDto.setManualGradeValue(prpCgradeDto.getAutoGradeValue());
//						prpCgradeDto.setManualMaxUsableRate(prpCgradeDto.getAutoMaxUsableRate());
//						prpCgradeDto.setManualOrgRate(prpCgradeDto.getAutoOrgRate());
//						prpCgradeDto.setManualAgentRate(prpCgradeDto.getAutoAgentRate());
//						prpCgradeDto.setManualBreakevenRate(prpCgradeDto.getAutoBreakevenRate());
//						prpCgradeDto.setManualBrokerRate(prpCgradeDto.getAutoBrokerRate());
//						prpCgradeDto.setManualExt1Rate(prpCgradeDto.getAutoExt1Rate());
//						prpCgradeDto.setManualExt2Rate(prpCgradeDto.getAutoExt2Rate());
//						prpCgradeDto.setManualExt3Rate(prpCgradeDto.getAutoExt3Rate());
//						dbPrpCgrade.update(prpCgradeDto);
//					}
//				}else if(iBusinessType.equals("E")){
//					prpCPgradeDto = dbPrpCPgrade.findByPrimaryKey(strPolicyNo);
//					if(prpCPgradeDto!=null){
//						prpCPgradeDto.setManualGradeCode(prpCPgradeDto.getAutoGradeCode());
//						prpCPgradeDto.setManualGradeValue(prpCPgradeDto.getAutoGradeValue());
//						prpCPgradeDto.setManualMaxUsableRate(prpCPgradeDto.getAutoMaxUsableRate());
//						prpCPgradeDto.setManualOrgRate(prpCPgradeDto.getAutoOrgRate());
//						prpCPgradeDto.setManualAgentRate(prpCPgradeDto.getAutoAgentRate());
//						prpCPgradeDto.setManualBreakevenRate(prpCPgradeDto.getAutoBreakevenRate());
//						prpCPgradeDto.setManualBrokerRate(prpCPgradeDto.getAutoBrokerRate());
//						prpCPgradeDto.setManualExt1Rate(prpCPgradeDto.getAutoExt1Rate());
//						prpCPgradeDto.setManualExt2Rate(prpCPgradeDto.getAutoExt2Rate());
//						prpCPgradeDto.setManualExt3Rate(prpCPgradeDto.getAutoExt3Rate());
//						dbPrpCPgrade.update(prpCPgradeDto);
//					}
//					prpCgradeDto = dbPrpCgrade.findByPrimaryKey(strPolicyNo);
//					if(prpCgradeDto!=null){
//						prpCgradeDto.setManualGradeCode(prpCgradeDto.getAutoGradeCode());
//						prpCgradeDto.setManualGradeValue(prpCgradeDto.getAutoGradeValue());
//						prpCgradeDto.setManualMaxUsableRate(prpCgradeDto.getAutoMaxUsableRate());
//						prpCgradeDto.setManualOrgRate(prpCgradeDto.getAutoOrgRate());
//						prpCgradeDto.setManualAgentRate(prpCgradeDto.getAutoAgentRate());
//						prpCgradeDto.setManualBreakevenRate(prpCgradeDto.getAutoBreakevenRate());
//						prpCgradeDto.setManualBrokerRate(prpCgradeDto.getAutoBrokerRate());
//						prpCgradeDto.setManualExt1Rate(prpCgradeDto.getAutoExt1Rate());
//						prpCgradeDto.setManualExt2Rate(prpCgradeDto.getAutoExt2Rate());
//						prpCgradeDto.setManualExt3Rate(prpCgradeDto.getAutoExt3Rate());
//						dbPrpCgrade.update(prpCgradeDto);
//					}
//				}
//			}
//		}
//	}


    /**
     * 查询满足模糊查询条件的记录数
     * @param conditions conditions
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
	@Override
	public int getCount(QueryRule queryRule) throws Exception {
		return super.find(queryRule).size();
	}


	@Override
	public List<WfGrade> findListByQueryRule(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	@Override
	public Collection<?> findByConditions(String conditions) throws Exception {
		return null;
	}

	public WfGrade getAutoGrade(String iFlowId, int iLogNo, int iModelNo,
			int iNodeNo, String iOperatorCode, String iOperatorName,
			String iBusinessType, String iBusinessNo) throws Exception {
		return null;
	}

	public WfLogService getWfLogService() {
		return wfLogService;
	}

	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}
	
}

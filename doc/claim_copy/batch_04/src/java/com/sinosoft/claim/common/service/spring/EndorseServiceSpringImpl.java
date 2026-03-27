package com.sinosoft.claim.common.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.common.service.facade.EndorseService;
import com.sinosoft.claim.common.service.facade.PrpPengageService;
import com.sinosoft.claim.common.service.facade.PrpPfeeService;
import com.sinosoft.claim.common.service.facade.PrpPheadService;
import com.sinosoft.claim.common.service.facade.PrpPitemCarService;
import com.sinosoft.claim.common.service.facade.PrpPitemKindService;
import com.sinosoft.claim.common.service.facade.PrpPmainService;
import com.sinosoft.claim.common.service.facade.PrpPprofitService;
import com.sinosoft.claim.common.service.facade.PrpPtextService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.common.vo.EndorseDto;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.sysframework.exceptionlog.UserException;

public class EndorseServiceSpringImpl extends GenericDaoHibernate<EndorseDto, String> implements EndorseService {
	/** 批改主表Service*/
	private PrpPheadService prpPheadService;
	/** 批改保单Service*/
	private PrpPmainService prpPmainService;
	/** 批改险别Service*/
	private PrpPitemKindService prpPitemKindService;
	/** 批改内容信息Service*/
	private PrpPtextService prpPtextService;
	/** 批改车辆Service*/
	private PrpPitemCarService prpPitemCarService;
	/** 批改费用信息Service*/
	private PrpPfeeService prpPfeeService;
	/** 批改折扣Service*/
	private PrpPprofitService prpPprofitService;
	/** 批改条款Service*/
	private PrpPengageService prpPengageService;
	/**
	 * 交验此保单是否处於批改状态
	 * @param policyNo 保单号码
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkStatus(String policyNo) throws Exception {
		int  checkFlag=-1;
		//mantis：CLM0215，處理人員：DP0713，需求單編號：新核心-車險保批審核判定條件修改增加自動核保註記
	    String statement = "Select count(*) from PrpPmain Where policyNo= '"+policyNo + "' and underwriteFlag NOT IN ('1','3') ";
		logger.debug(statement);
		checkFlag = (int) HibernateUtils.getCountbyCountSql(this.getSession(), statement);
		logger.info("EndorseServiceSpringImpl.checkStatus() success!");
		return checkFlag;
	}
	/**
	 * 获得批单
	 * @param policyNo 保单号码
	 * @return 批单对象
	 * @throws Exception
	 */
	@Override
	public EndorseDto findByConditions(String policyNo) throws SQLException, UserException, Exception {
		EndorseDto endorseDto = new EndorseDto();
		String conditions = " policyNo = '" + policyNo + "'";
		endorseDto.setPrpPheadList(this.prpPheadService.findByConditions(conditions, 0, 0));
		endorseDto.setPrpPmainList(this.prpPmainService.findByConditions(conditions, 0, 0));
		endorseDto.setPrpPitemKindList(this.prpPitemKindService.findByConditions(conditions, 0, 0));
		endorseDto.setPrpPtextList(this.prpPtextService.findByConditions(conditions, 0, 0));
		if (policyNo == null)
		{
			throw new UserException(-98,-1000,this.getClass().getName()+".findByPrimaryKey("+policyNo+")");
		}
		return endorseDto;
	}
	/**
	 * 获得批单
	 * @param endorseNo 批单号
	 * @return 批单对象
	 * @throws Exception
	 */
	@Override
	public EndorseDto findByPrimaryKey(String endorseNo) throws SQLException, UserException, Exception {
	     EndorseDto endorseDto = new EndorseDto();
	     //取得涉案车辆
	     endorseDto.setPrpPhead(this.prpPheadService.findByPrimaryKey(endorseNo));
	     endorseDto.setPrpPmain(this.prpPmainService.findByPrimaryKey(endorseNo));
	     String conditions = " endorseNo = '" + endorseNo + "'";
	     endorseDto.setPrpPitemKindList(this.prpPitemKindService.findByConditions(conditions, 0, 0));
	     endorseDto.setPrpPtextList(this.prpPtextService.findByConditions(conditions, 0, 0));
	     endorseDto.setPrpPitemcarList(this.prpPitemCarService.findByConditions(conditions, 0, 0));
	     endorseDto.setPrpPfeeList(this.prpPfeeService.findByConditions(conditions, 0, 0));
	     endorseDto.setPrpPprofitList(this.prpPprofitService.findByConditions(conditions, 0, 0));
	     endorseDto.setPrpPengageList(this.prpPengageService.findByConditions(conditions, 0, 0));
		if (endorseNo == null)
		{
			throw new UserException(-98,-1000,this.getClass().getName()+".findByPrimaryKey("+endorseNo+")");
		}
		
		return endorseDto;
	}
	/**
	 * 获得批单
	 * @param conditions 查询条件
	 * @return 批单对象
	 * @throws Exception
	 */
	@Override
	public List<PrpPhead> findByPrpPheadConditions(String conditions) throws SQLException, UserException, Exception {
		List<PrpPhead> collection = new ArrayList<PrpPhead>();
		if (conditions == null)
		{
			throw new UserException(-98,-1000,this.getClass().getName()+".findByPrpPheadConditions("+conditions+")");
		}
	    if(conditions.trim().length()==0){
	        conditions = "1=1";
	    }
		collection=this.prpPheadService.findByConditions(conditions, 0, 0);
		return collection;
	}

	public PrpPheadService getPrpPheadService() {
		return prpPheadService;
	}

	public void setPrpPheadService(PrpPheadService prpPheadService) {
		this.prpPheadService = prpPheadService;
	}

	public PrpPmainService getPrpPmainService() {
		return prpPmainService;
	}

	public void setPrpPmainService(PrpPmainService prpPmainService) {
		this.prpPmainService = prpPmainService;
	}

	public PrpPitemKindService getPrpPitemKindService() {
		return prpPitemKindService;
	}

	public void setPrpPitemKindService(PrpPitemKindService prpPitemKindService) {
		this.prpPitemKindService = prpPitemKindService;
	}

	public PrpPtextService getPrpPtextService() {
		return prpPtextService;
	}

	public void setPrpPtextService(PrpPtextService prpPtextService) {
		this.prpPtextService = prpPtextService;
	}
	public PrpPitemCarService getPrpPitemCarService() {
		return prpPitemCarService;
	}
	public void setPrpPitemCarService(PrpPitemCarService prpPitemCarService) {
		this.prpPitemCarService = prpPitemCarService;
	}
	public PrpPfeeService getPrpPfeeService() {
		return prpPfeeService;
	}
	public void setPrpPfeeService(PrpPfeeService prpPfeeService) {
		this.prpPfeeService = prpPfeeService;
	}
	public PrpPprofitService getPrpPprofitService() {
		return prpPprofitService;
	}
	public void setPrpPprofitService(PrpPprofitService prpPprofitService) {
		this.prpPprofitService = prpPprofitService;
	}
	public PrpPengageService getPrpPengageService() {
		return prpPengageService;
	}
	public void setPrpPengageService(PrpPengageService prpPengageService) {
		this.prpPengageService = prpPengageService;
	}
	
}

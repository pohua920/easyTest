package com.sinosoft.claim.reins.service.spring;

import java.util.ArrayList;
import java.util.Collection;

import com.sinosoft.claim.reins.service.facade.ReinsService;
import com.sinosoft.claim.reins.vo.ReinsCaseStatus;
import com.sinosoft.claim.reins.vo.ReinsClaimMain;
import com.sinosoft.claim.reins.vo.ReinsClaimSummary;
import com.sinosoft.claim.reins.vo.ReinsDangerUnit;
import com.sinosoft.claim.reins.vo.ReinsLargeCase;
import com.sinosoft.claim.reins.vo.ReinsRepayCalResult;
import com.sinosoft.sysframework.common.datatype.DateTime;

public class ReinsServiceImplTest implements ReinsService {

	public void changeCaseStatus(ReinsCaseStatus reinsCaseStatus) throws Exception {
		System.out.println("结案，重开赔案，註銷/拒赔成功！");

	}

//	public Collection getDangerUnit(String policyNo, DateTime damageDate)
//			throws Exception {
//		DBManager dbManager = new DBManager();    
//		try{
//			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
//			return getDangerUnit(dbManager, policyNo, damageDate);
//		}catch(Exception exception){        	 
//			throw exception;
//		}finally{
//			dbManager.close();
//		}
//		
//	}

	public Collection<ReinsDangerUnit> getDangerUnit(String policyNo,
			DateTime DamageDate) throws Exception {
		Collection<ReinsDangerUnit> collection =new ArrayList<ReinsDangerUnit>();
		ReinsDangerUnit reinsDangerUnit=new ReinsDangerUnit();
		reinsDangerUnit.setAddressName("jlsaddjfaslf");
		reinsDangerUnit.setDangerDesc("jelfea");
		reinsDangerUnit.setDangerNo(new Integer(1));
		collection.add(reinsDangerUnit);
		return collection;
	
	}

//	public Collection getLargeCashLoss(ReinsClaimSummary reinsClaimSummary)
//			throws Exception {
//		DBManager dbManager = new DBManager();    
//		try{
//			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
//			return getLargeCashLoss(dbManager, reinsClaimSummary);
//		}catch(Exception exception){        	 
//			throw exception;
//		}finally{
//			dbManager.close();
//		}
//	}

	public Collection<ReinsLargeCase> getLargeCashLoss(
			ReinsClaimSummary reinsClaimSummary) throws Exception {
		Collection<ReinsLargeCase> reinsLargeCaseCollection=new ArrayList<ReinsLargeCase>();
		ReinsLargeCase reinsLargeCase=new ReinsLargeCase();
		reinsLargeCase.setDangerNo(new Integer(1));
		reinsLargeCase.setLargeLoss(Boolean.TRUE);
		reinsLargeCase.setCashLoss(Boolean.TRUE);
		reinsLargeCaseCollection.add(reinsLargeCase);
		return reinsLargeCaseCollection;
		
	}

//	public double getSumFacShare(String policyNo, DateTime DamageDate)
//			throws Exception {
//		DBManager dbManager = new DBManager();    
//		try{
//			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
//			return getSumFacShare(dbManager, policyNo, DamageDate);
//		}catch(Exception exception){        	 
//			throw exception;
//		}finally{
//			dbManager.close();
//		}
//		
//	}

	public double getSumFacShare(String policyNo,
			DateTime DamageDate) throws Exception {
		return 1;
	}

	public void repayCal(ReinsClaimMain reinsClaimMain)
			throws Exception {
		System.out.println("模拟再保送分赔成功！");

	}

//	public Collection repaySimulate(ReinsClaimSummary reinsClaimSummary)
//			throws Exception {
//		DBManager dbManager = new DBManager();   
//		try{
//			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
//			return repaySimulate(dbManager, reinsClaimSummary);
//		}catch(Exception exception){        	 
//			throw exception;
//		}finally{
//			dbManager.close();
//		}
//		
//	}

	public Collection<ReinsRepayCalResult> repaySimulate(
			ReinsClaimSummary reinsClaimSummary) throws Exception {
		Collection<ReinsRepayCalResult> collection =new ArrayList<ReinsRepayCalResult>();
		ReinsRepayCalResult reinsRepayCalResult=new ReinsRepayCalResult();
		reinsRepayCalResult.setReinsModeName("自留");
		reinsRepayCalResult.setShareRate(new Double(20));
		reinsRepayCalResult.setSumPaid(new Double(1000));
		collection.add(reinsRepayCalResult);
		return collection;
	}

}

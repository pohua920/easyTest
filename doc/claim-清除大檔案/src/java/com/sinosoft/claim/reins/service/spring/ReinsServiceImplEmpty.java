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

/**
 * 再保为空的接口实现类
 * @author 中科软
 *
 */
public class ReinsServiceImplEmpty implements ReinsService{

	public void changeCaseStatus(ReinsCaseStatus reinsCaseStatus) throws Exception {
		System.out.println("该再保的实现是空的，请重载实现方法，並在配置文件中重指向");
	}

//	public Collection getDangerUnit(DBManager dbManager, String policyNo, DateTime DamageDate) throws Exception {
//		System.out.println("该再保的实现是空的，请重载实现方法，並在配置文件中重指向");
//		return new ArrayList();
//	}

	public Collection<ReinsDangerUnit> getDangerUnit(String policyNo, DateTime DamageDate) throws Exception {
		System.out.println("该再保的实现是空的，请重载实现方法，並在配置文件中重指向");
		return new ArrayList<ReinsDangerUnit>();
	}

//	public Collection getLargeCashLoss(DBManager dbManager, ReinsClaimSummary reinsClaimSummary) throws Exception {
//		System.out.println("该再保的实现是空的，请重载实现方法，並在配置文件中重指向");
//		return new ArrayList();
//	}

	public Collection<ReinsLargeCase> getLargeCashLoss(ReinsClaimSummary reinsClaimSummary) throws Exception {
		System.out.println("该再保的实现是空的，请重载实现方法，並在配置文件中重指向");
		return new ArrayList<ReinsLargeCase>();
	}

//	public double getSumFacShare(DBManager dbManager, String policyNo, DateTime DamageDate) throws Exception {
//		System.out.println("该再保的实现是空的，请重载实现方法，並在配置文件中重指向");
//		return 0;
//	}

	public double getSumFacShare(String policyNo, DateTime DamageDate) throws Exception {
		System.out.println("该再保的实现是空的，请重载实现方法，並在配置文件中重指向");
		return 0;
	}

	public void repayCal(ReinsClaimMain reinsClaimMain) throws Exception {
		System.out.println("该再保的实现是空的，请重载实现方法，並在配置文件中重指向");
	}

//	public Collection repaySimulate(DBManager dbManager, ReinsClaimSummary reinsClaimSummary) throws Exception {
//		System.out.println("该再保的实现是空的，请重载实现方法，並在配置文件中重指向");
//		return new ArrayList();
//	}

	public Collection<ReinsRepayCalResult> repaySimulate(ReinsClaimSummary reinsClaimSummary) throws Exception {
		System.out.println("该再保的实现是空的，请重载实现方法，並在配置文件中重指向");
		return new ArrayList<ReinsRepayCalResult>();
	}

}

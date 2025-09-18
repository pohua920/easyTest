package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps044DetailVo;
import com.tlg.aps.vo.FirRenewListForFileVo;
import com.tlg.aps.vo.FirRenewListForPremVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirRenewListDtl;
import com.tlg.util.PageInfo;

/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
public interface FirRenewListDtlDao extends IBatisBaseDao<FirRenewListDtl, BigDecimal> {
	
	public List<FirRenewListForPremVo> selectRenewListForPrem(Map params) throws Exception;
	
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業 start*/
	public List<Aps044DetailVo> selectForAps044Detail(PageInfo pageInfo) throws Exception;
	
	public int countForAps044Detail(Map<String, String> params) throws Exception;
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業 end*/
	
	//mantis：FIR0570_1，處理人員：DP0706，需求單編號：FIR0570_1 住火_APS每月應續件產生排程
	public List<FirRenewListForPremVo> selectRenewListForMail(Map params) throws Exception;
	
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 start*/
	public List<FirRenewListForFileVo> selectRenewListForOtherFile(Map params) throws Exception;
	
	public List<FirRenewListForFileVo> selectRenewListForCoreFile(Map params) throws Exception;
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 end*/
}
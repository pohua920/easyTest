package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/* mantis：FIR0495，處理人員：CC009，需求單編號：FIR0495_住火-APS板信回饋檔-排程查詢作業 */
import com.tlg.aps.vo.Aps038DetailVo;
import com.tlg.aps.vo.Aps041DetailVo1;
import com.tlg.aps.vo.Aps041DetailVo2;
import com.tlg.aps.vo.FirUbProcessVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirAgtBatchDtl;
import com.tlg.prpins.entity.FirAgtUb02;
import com.tlg.util.PageInfo;

public interface FirAgtBatchDtlDao extends IBatisBaseDao<FirAgtBatchDtl, BigDecimal> {
	/*mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 start */

	public List<FirAgtBatchDtl> selectForAps009Detail01(PageInfo pageInfo)throws Exception;
	
	public int countForAps009Detail01(Map<String,String> params)throws Exception;

	public List<FirAgtBatchDtl> selectForAps009Detail03(PageInfo pageInfo)throws Exception;
	
	public int countForAps009Detail03(Map<String,String> params)throws Exception;
	
	/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 */
	public Integer updateByParams(Map<String,Object> params) throws Exception;
	
	/* mantis：FIR0495，處理人員：CC009，需求單編號：FIR0495_住火-APS板信回饋檔-排程查詢作業 start */
	public List<Aps038DetailVo> selectForAps038Detail01(PageInfo pageInfo)throws Exception;
	
	public int countForAps038Detail01(Map<String,String> params)throws Exception;

	public List<Aps038DetailVo> selectForAps038Detail03(PageInfo pageInfo)throws Exception;
	
	public int countForAps038Detail03(Map<String,String> params)throws Exception;
	/* mantis：FIR0495，處理人員：CC009，需求單編號：FIR0495_住火-APS板信回饋檔-排程查詢作業 end */
	
	/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 start */
	public List<Aps041DetailVo1> selectForAps041Dtl1(PageInfo pageInfo)throws Exception;
	
	public int countForAps041Dtl1(Map<String,String> params)throws Exception;
	
	public List<Aps041DetailVo2> selectForAps041Dtl2(PageInfo pageInfo)throws Exception;
	
	public int countForAps041Dtl2(Map<String,String> params)throws Exception;
	/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 end */
	
	//mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程
	public List<FirUbProcessVo> selectForUbProposalEmail(Map params) throws Exception;

	/*mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 start*/
	public List<FirAgtUb02> selectForUbBackFile(Map params) throws Exception;
	
	public List<FirAgtBatchDtl> selectForUbBackFileEmail(Map params) throws Exception;
	/*mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 end*/
}
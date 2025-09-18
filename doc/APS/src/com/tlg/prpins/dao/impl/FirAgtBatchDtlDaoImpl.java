package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/* mantis：FIR0495，處理人員：CC009，需求單編號：FIR0495_住火-APS板信回饋檔-排程查詢作業 */
import com.tlg.aps.vo.Aps038DetailVo;
import com.tlg.aps.vo.Aps041DetailVo1;
import com.tlg.aps.vo.Aps041DetailVo2;
import com.tlg.aps.vo.FirUbProcessVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtBatchDtlDao;
import com.tlg.prpins.entity.FirAgtBatchDtl;
import com.tlg.prpins.entity.FirAgtUb02;
import com.tlg.util.PageInfo;

public class FirAgtBatchDtlDaoImpl extends IBatisBaseDaoImpl<FirAgtBatchDtl, BigDecimal> implements FirAgtBatchDtlDao {
	/* mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 start */
	@Override
	public String getNameSpace() {
		return "FirAgtBatchDtl";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FirAgtBatchDtl> selectForAps009Detail01(PageInfo pageInfo) throws Exception {
		List<FirAgtBatchDtl> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps009Detail01",pageInfo.getFilter());
		return queryForList;
	}

	@Override
	public int countForAps009Detail01(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps009Detail01", params);
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FirAgtBatchDtl> selectForAps009Detail03(PageInfo pageInfo) throws Exception {
		List<FirAgtBatchDtl> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps009Detail03",pageInfo.getFilter());
		return queryForList;
	}
	
	@Override
	public int countForAps009Detail03(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps009Detail03", params);
		return count;
	}
	
	/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 START */
	@Override
	public Integer updateByParams(Map<String, Object> params) throws Exception {
		return (Integer) getSqlMapClientTemplate().update(getNameSpace() + ".updateByParams", params);
	}
	/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 END */
	
	/* mantis：FIR0495，處理人員：CC009，需求單編號：FIR0495_住火-APS板信回饋檔-排程查詢作業 start */
	@Override
	public List<Aps038DetailVo> selectForAps038Detail01(PageInfo pageInfo) throws Exception {
		List<Aps038DetailVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps038Detail01",pageInfo.getFilter());
		return queryForList;
	}

	@Override
	public int countForAps038Detail01(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps038Detail01", params);
		return count;
	}

	@Override
	public List<Aps038DetailVo> selectForAps038Detail03(PageInfo pageInfo) throws Exception {
		List<Aps038DetailVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps038Detail03",pageInfo.getFilter());
		return queryForList;
	}

	@Override
	public int countForAps038Detail03(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps038Detail03", params);
		return count;
	}
	/* mantis：FIR0495，處理人員：CC009，需求單編號：FIR0495_住火-APS板信回饋檔-排程查詢作業 end */
	
	/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 start */
	@Override
	public List<Aps041DetailVo1> selectForAps041Dtl1(PageInfo pageInfo) throws Exception {
		List<Aps041DetailVo1> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps041Dtl1",pageInfo.getFilter());
		return queryForList;
	}

	@Override
	public int countForAps041Dtl1(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps041Dtl1", params);
		return count;
	}

	@Override
	public List<Aps041DetailVo2> selectForAps041Dtl2(PageInfo pageInfo) throws Exception {
		List<Aps041DetailVo2> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps041Dtl2",pageInfo.getFilter());
		return queryForList;
	}

	@Override
	public int countForAps041Dtl2(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps041Dtl2", params);
		return count;
	}
	/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 end */
	
	/*mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 start*/
	@Override
	public List<FirUbProcessVo> selectForUbProposalEmail(Map params) throws Exception {
		List<FirUbProcessVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForUbProposalEmail",params);
		return queryForList;
	}
	/*mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 end*/
	
	/*mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 start */
	@Override
	public List<FirAgtUb02> selectForUbBackFile(Map params) throws Exception {
		List<FirAgtUb02> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForUbBackFile",params);
		return queryForList;
	}
	
	@Override
	public List<FirAgtBatchDtl> selectForUbBackFileEmail(Map params) throws Exception {
		List<FirAgtBatchDtl> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForUbBackFileEmail",params);
		return queryForList;
	}
	/*mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 end */
}
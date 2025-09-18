package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps016DetailVo;
import com.tlg.aps.vo.Aps034FbDetailVo;
import com.tlg.aps.vo.Aps034genFileVo;
import com.tlg.aps.vo.Aps055BotDetailVo;
import com.tlg.aps.vo.Aps055BotGenFileVo;
import com.tlg.aps.vo.Aps057ResultVo;
import com.tlg.aps.vo.Aps060YcbDetailVo;
import com.tlg.aps.vo.Aps060YcbGenFileVo;
import com.tlg.aps.vo.FirBopRenewalDataVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtrnBatchDtlDao;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.util.PageInfo;

public class FirAgtrnBatchDtlDaoImpl extends IBatisBaseDaoImpl<FirAgtrnBatchDtl, BigDecimal> implements FirAgtrnBatchDtlDao {
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程  */
	@Override
	public String getNameSpace() {
		return "FirAgtrnBatchDtl";
	}

	/* mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業  start */
	@Override
	public Aps016DetailVo findInsuredDataJoinTocoreMain(Map params) throws Exception {
		Aps016DetailVo aps016DetailVo = (Aps016DetailVo) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectInsuredDataJoinTocoreMain",params);
		return aps016DetailVo;
	}

	@Override
	public List<Aps016DetailVo> findFirAgtrnBatchDtlForExcel(String batchNo) throws Exception {
		List<Aps016DetailVo> searchResult =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectFirAgtrnBatchDtlForExcel",batchNo);
		return searchResult;
	}
	
	@Override
	public List<Aps016DetailVo> findFirAgtrnBatchDtlForDetail(PageInfo pageInfo) throws Exception {
		List<Aps016DetailVo> queryForList =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForDetail",pageInfo.getFilter());
		return queryForList;
	}
	
	@Override
	public int countFirAgtrnBatchDtlForDetail(Map params) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForDetail", params);
		return count;
	}
	/* mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業  end */
	
	/* mantis：FIR0349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業 start*/
	@Override
	public List<FirBopRenewalDataVo> findRenewalDataByBatchNo(PageInfo pageInfo) throws Exception {
		List<FirBopRenewalDataVo> queryForList =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectRenewalDataByBatchNo",pageInfo.getFilter());
		return queryForList;
	}
	
	@Override
	public int countRenewalDataByBatchNo(Map params) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countRenewalDataByBatchNo", params);
		return count;
	}
	
	@Override
	public FirBopRenewalDataVo selectCountPolicyByBatchNo(Map params) throws Exception {
		FirBopRenewalDataVo searchResult =  (FirBopRenewalDataVo) getSqlMapClientTemplate().queryForObject(getNameSpace()+".countPolicyByBatchNo",params);
		return searchResult;
	}
	
	@Override
	public List<FirBopRenewalDataVo> selectBopRnDataForExcelByBatchNo(Map params) throws Exception {
		List<FirBopRenewalDataVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectBopRnDataForExcel",params);
		return queryForList;
	}
	
	public List<FirBopRenewalDataVo> findRenewalDataForExcelByBatchNo(String batchNo) throws Exception {
		List<FirBopRenewalDataVo> queryForList =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectRenewalDataForExcelByBatchNo",batchNo);
		return queryForList;
	}
	/* mantis：FIR0349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業 end*/
	
	/* mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 start*/
	@Override
	public List<Aps034FbDetailVo> selectForFbrnDetail(PageInfo pageInfo) throws Exception {
		List<Aps034FbDetailVo> queryForList =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForFbrnDetail",pageInfo.getFilter());
		return queryForList;
	}
	
	@Override
	public int countForFbrnDetail(Map params) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForFbrnDetail", params);
		return count;
	}
	
	@Override
	public Aps034FbDetailVo selectForFbrnInsuredData(Map params) throws Exception {
		Aps034FbDetailVo aps034FbDetailVo =  (Aps034FbDetailVo) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectFbrnInsuredData",params);
		return aps034FbDetailVo;
	}
	
	@Override
	public List<Aps034genFileVo> selectForFbRejectFile(Map params) throws Exception {
		List<Aps034genFileVo> queryForList =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectFbRejectFile", params);
		return queryForList;
	}
	
	@Override
	public List<Aps034genFileVo> selectForFbRenewalData(Map params) throws Exception {
		List<Aps034genFileVo> queryForList =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectFbRenewalData", params);
		return queryForList;
	}
	
	@Override
	public int countCoreInsured(Map params) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countCoreInsured", params);
		return count;
	}
	
	@Override
	public Aps034genFileVo selectCoreNotInsuredData(Map params) throws Exception {
		Aps034genFileVo aps034genFileVo =  (Aps034genFileVo) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectCoreNotInsuredData",params);
		return aps034genFileVo;
	}
	
	@Override
	public List<Aps034genFileVo> selectForFbFkind(Map params) throws Exception {
		List<Aps034genFileVo> queryForList =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectFbRenewalFkind", params);
		return queryForList;
	}
	/* mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 end*/
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public List<Aps055BotDetailVo> selectForBotrnDetail(PageInfo pageInfo) throws Exception {
		List<Aps055BotDetailVo> queryForList =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForBotrnDetail",pageInfo.getFilter());
		return queryForList;
	}
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public int countForBotrnDetail(Map params) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForBotrnDetail", params);
		return count;
	}
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public List<Aps055BotGenFileVo> selectForBotrnXlsByParams(PageInfo pageInfo) throws Exception {
		List<Aps055BotGenFileVo> queryForList =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForBotrnXls",pageInfo.getFilter());
		return queryForList;
	}
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public Aps055BotDetailVo selectForBotrnInsuredData(Map params) throws Exception {
		Aps055BotDetailVo aps055BotDetailVo =  (Aps055BotDetailVo) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectBotrnInsuredData",params);
		return aps055BotDetailVo;
	}
	
	/* mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業 start */
	@Override
	public List<Aps057ResultVo> selectForAps057(PageInfo pageInfo) throws Exception {
		List<Aps057ResultVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps057",pageInfo.getFilter());
		return queryForList;
	}

	@Override
	public int countForAps057(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps057", params);
		return count;
	}
	/* mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業 end */
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public int countForYcbrnDetail(Map params) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForYcbrnDetail", params);
		return count;
	}
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public List<Aps060YcbGenFileVo> selectForYcbrnXlsByParams(PageInfo pageInfo) throws Exception {
		List<Aps060YcbGenFileVo> queryForList =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForYcbrnXls",pageInfo.getFilter());
		return queryForList;
	}
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public Aps060YcbDetailVo selectForYcbrnInsuredData(Map params) throws Exception {
		Aps060YcbDetailVo aps060YcbDetailVo =  (Aps060YcbDetailVo) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectYcbrnInsuredData",params);
		return aps060YcbDetailVo;
	}

	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public List<Aps060YcbDetailVo> selectForYcbrnDetail(PageInfo pageInfo) throws Exception {
		List<Aps060YcbDetailVo> queryForList =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForYcbrnDetail",pageInfo.getFilter());
		return queryForList;
	}
}
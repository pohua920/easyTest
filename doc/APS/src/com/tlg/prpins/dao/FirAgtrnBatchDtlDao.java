package com.tlg.prpins.dao;

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
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.util.PageInfo;

/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 */
public interface FirAgtrnBatchDtlDao extends IBatisBaseDao<FirAgtrnBatchDtl, BigDecimal> {
	
	/* mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業  start */
	public Aps016DetailVo findInsuredDataJoinTocoreMain(Map params)throws Exception;
	
	public List<Aps016DetailVo> findFirAgtrnBatchDtlForExcel(String batchNo)throws Exception;
	
	public List<Aps016DetailVo> findFirAgtrnBatchDtlForDetail(PageInfo pageInfo) throws Exception;
	
	public int countFirAgtrnBatchDtlForDetail(Map params);
	/* mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業  end */
	
	/* mantis：FIR0349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業 start*/
	public List<FirBopRenewalDataVo> findRenewalDataByBatchNo(PageInfo pageInfo) throws Exception;
	
	public int countRenewalDataByBatchNo(Map params);
	
	public FirBopRenewalDataVo selectCountPolicyByBatchNo(Map params) throws Exception;

	public List<FirBopRenewalDataVo> selectBopRnDataForExcelByBatchNo(Map params) throws Exception;
	
	public List<FirBopRenewalDataVo> findRenewalDataForExcelByBatchNo(String batchNo) throws Exception;
	/* mantis：FIR0349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業 end*/
	
	/* mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 start*/
	public List<Aps034FbDetailVo> selectForFbrnDetail(PageInfo pageInfo) throws Exception;
	
	public int countForFbrnDetail(Map params);
	
	public Aps034FbDetailVo selectForFbrnInsuredData(Map params) throws Exception;
	
	public List<Aps034genFileVo> selectForFbRejectFile(Map params) throws Exception;
	
	public List<Aps034genFileVo> selectForFbRenewalData(Map params) throws Exception;
	
	public int countCoreInsured(Map params);
	
	public Aps034genFileVo selectCoreNotInsuredData(Map params) throws Exception;
	
	public List<Aps034genFileVo> selectForFbFkind(Map params) throws Exception;
	/* mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 end*/
	
	/* mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 start*/
	public List<Aps055BotDetailVo> selectForBotrnDetail(PageInfo pageInfo) throws Exception;
	
	public int countForBotrnDetail(Map params);
	
	public List<Aps055BotGenFileVo> selectForBotrnXlsByParams(PageInfo pageInfo) throws Exception;
	
	public Aps055BotDetailVo selectForBotrnInsuredData(Map params) throws Exception;
	/* mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 end*/
	
	/* mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業 start */
	public List<Aps057ResultVo> selectForAps057(PageInfo pageInfo) throws Exception;
	
	public int countForAps057(Map<String, String> params) throws Exception;
	/* mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業 end */
	
	/* mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 start*/
	public List<Aps060YcbDetailVo> selectForYcbrnDetail(PageInfo pageInfo) throws Exception;
	
	public int countForYcbrnDetail(Map params);
	
	public List<Aps060YcbGenFileVo> selectForYcbrnXlsByParams(PageInfo pageInfo) throws Exception;
	
	public Aps060YcbDetailVo selectForYcbrnInsuredData(Map params) throws Exception;
	/* mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 end*/
}
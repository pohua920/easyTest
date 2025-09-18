package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps034DownloadlVo;
import com.tlg.aps.vo.Aps055BotDetailVo;
import com.tlg.aps.vo.Aps055DownloadlVo;
import com.tlg.aps.vo.Aps060DownloadlVo;
import com.tlg.aps.vo.Aps060YcbDetailVo;
import com.tlg.aps.vo.FirPahsinRenewalVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirAgtrnBatchMain;
import com.tlg.util.PageInfo;

/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
public interface FirAgtrnBatchMainDao extends IBatisBaseDao<FirAgtrnBatchMain, BigDecimal> {
	
	// mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 
	public Aps034DownloadlVo findForFbrnDownloadData(Map params) throws Exception;
	
	/* mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
	public List<FirPahsinRenewalVo> selectBatchMainForBoprn(PageInfo pageInfo) throws Exception;
	public int countBatchMainForBoprn(Map<String, Object> params) throws Exception;
	/* mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/
	
	/* mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 start*/
	public Aps055DownloadlVo findForBotrnDownloadData(Map params) throws Exception;
	public List<Aps055BotDetailVo> selectBatchMainForBotrn(PageInfo pageInfo) throws Exception;
	public int countBatchMainForBotrn(Map<String, Object> params) throws Exception;
	/* mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 end*/
	
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
	public List<Aps055BotDetailVo> selectBatchMainForBotrnIntoCore() throws Exception;
	
	/* mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 start*/
	public Aps060DownloadlVo findForYcbrnDownloadData(Map params) throws Exception;
	public int countBatchMainForYcbrn(Map<String, Object> params) throws Exception;
	public List<Aps060YcbDetailVo> selectBatchMainForYcbrn(PageInfo pageInfo) throws Exception;
	/* mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 end*/
	
}
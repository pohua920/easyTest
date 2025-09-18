package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps034DownloadlVo;
import com.tlg.aps.vo.Aps055BotDetailVo;
import com.tlg.aps.vo.Aps055DownloadlVo;
import com.tlg.aps.vo.Aps060DownloadlVo;
import com.tlg.aps.vo.Aps060YcbDetailVo;
import com.tlg.aps.vo.FirPahsinRenewalVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtrnBatchMainDao;
import com.tlg.prpins.entity.FirAgtrnBatchMain;
import com.tlg.util.PageInfo;

public class FirAgtrnBatchMainDaoImpl extends IBatisBaseDaoImpl<FirAgtrnBatchMain, BigDecimal> implements FirAgtrnBatchMainDao {
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	@Override
	public String getNameSpace() {
		return "FirAgtrnBatchMain";
	}
	
	/* mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 */
	@Override
	public Aps034DownloadlVo findForFbrnDownloadData(Map params) throws Exception {
		Aps034DownloadlVo aps034FbDetailVo =  (Aps034DownloadlVo) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectFbrnDownloadData",params);
		return aps034FbDetailVo;
	}
	
	/* mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start */
	@Override
	public List<FirPahsinRenewalVo> selectBatchMainForBoprn(PageInfo pageInfo) throws Exception {
		return getSqlMapClientTemplate().queryForList(getNameSpace()+".selectBatchMainForBoprn",pageInfo.getFilter());
	}

	@Override
	public int countBatchMainForBoprn(Map<String, Object> params) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countBatchMainForBoprn", params);
	}
	/* mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end */
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public Aps055DownloadlVo findForBotrnDownloadData(Map params) throws Exception {
		Aps055DownloadlVo aps055DownloadlVo =  (Aps055DownloadlVo) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectBotrnDownloadData",params);
		return aps055DownloadlVo;
	}
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public List<Aps055BotDetailVo> selectBatchMainForBotrn(PageInfo pageInfo) throws Exception {
		return getSqlMapClientTemplate().queryForList(getNameSpace()+".selectBatchMainForBotrn",pageInfo.getFilter());
	}
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public int countBatchMainForBotrn(Map<String, Object> params) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countBatchMainForBotrn", params);
	}
	
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
	@Override
	public List<Aps055BotDetailVo> selectBatchMainForBotrnIntoCore() throws Exception {
		return  getSqlMapClientTemplate().queryForList(getNameSpace() + ".selectBatchMainForBotrnIntoCore" );
	}
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public Aps060DownloadlVo findForYcbrnDownloadData(Map params) throws Exception {
		Aps060DownloadlVo aps060DownloadlVo =  (Aps060DownloadlVo) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectYcbrnDownloadData",params);
		return aps060DownloadlVo;
	}
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public int countBatchMainForYcbrn(Map<String, Object> params) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countBatchMainForYcbrn", params);
	}
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public List<Aps060YcbDetailVo> selectBatchMainForYcbrn(PageInfo pageInfo) throws Exception {
		return getSqlMapClientTemplate().queryForList(getNameSpace()+".selectBatchMainForYcbrn",pageInfo.getFilter());
	}
}
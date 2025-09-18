package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps046ResultVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FetclaimmainDao;
import com.tlg.prpins.entity.Fetclaimmain;
import com.tlg.util.PageInfo;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
public class FetclaimmainDaoImpl extends IBatisBaseDaoImpl<Fetclaimmain, BigDecimal> implements FetclaimmainDao {
	
	@Override
	public String getNameSpace() {
		return "Fetclaimmain";
	}

	@Override
	public List<Aps046ResultVo> selectForMainData(PageInfo pageInfo) throws Exception {
		List<Aps046ResultVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForMainData",pageInfo.getFilter());
		return queryForList;
	}

	@Override
	public int countForMainData(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForMainData", params);
		return count;
	}

	@Override
	public List<Aps046ResultVo> selectForClaimList(Map<String, String> params) throws Exception {
		List<Aps046ResultVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForClaimList",params);
		return queryForList;
	}
	
	/**mantis：MOB0019，處理人員：BJ085，需求單編號：MOB0019 理賠審核確認作業 */
	@Override
	public Aps046ResultVo selectForReviewNum(Map<String, String> params) throws Exception {
		Aps046ResultVo aps046ResultVo = (Aps046ResultVo) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectForReviewNum", params);
		return aps046ResultVo;
	}

}
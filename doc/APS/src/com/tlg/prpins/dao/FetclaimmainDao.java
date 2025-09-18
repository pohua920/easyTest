package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps046ResultVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.Fetclaimmain;
import com.tlg.util.PageInfo;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
public interface FetclaimmainDao extends IBatisBaseDao<Fetclaimmain, BigDecimal> {
	
	public List<Aps046ResultVo> selectForMainData(PageInfo pageInfo) throws Exception;
	
	public int countForMainData(Map<String, String> params) throws Exception;
	
	public List<Aps046ResultVo> selectForClaimList(Map<String, String> params) throws Exception;
	
	//mantis：MOB0019，處理人員：BJ085，需求單編號：MOB0019 理賠審核確認作業
	public Aps046ResultVo selectForReviewNum(Map<String, String> params) throws Exception;
}
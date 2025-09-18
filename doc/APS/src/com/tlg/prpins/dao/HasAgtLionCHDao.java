package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.HasAgtLionCH;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public interface HasAgtLionCHDao extends IBatisBaseDao<HasAgtLionCH, BigDecimal> {
	public List<HasAgtLionCH> selectForGenFile(Map<String,String> params) throws Exception;
}
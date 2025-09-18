package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.HasAgtLionCM;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public interface HasAgtLionCMDao extends IBatisBaseDao<HasAgtLionCM, BigDecimal> {
	public List<HasAgtLionCM> selectForGenFile(Map<String,String> params) throws Exception;
}
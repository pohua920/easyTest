package com.sinosoft.claim.schema.service.spring;
/**
 * 追偿损余文字说明信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLrtext;
import com.sinosoft.claim.schema.model.PrpLrtextId;
import com.sinosoft.claim.schema.service.facade.PrpLrtextService;

public class PrpLrtextServiceSpringImpl extends
GenericDaoHibernate<PrpLrtext, PrpLrtextId> implements PrpLrtextService{

	@Override
	public void save(PrpLrtext prpLrtext) throws Exception {
		logger.info("保存追偿损余文字说明信息");
		super.save(prpLrtext);
		
	}

	@Override
	public void save(List<PrpLrtext> list) throws Exception {
		logger.info("保存追偿损余文字说明信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLrtextId prpLrtextId) throws Exception {
		logger.info("删除追偿损余文字说明信息编号为" + prpLrtextId + "的追偿损余文字说明信息");
		super.deleteByPK(PrpLrtext.class, prpLrtextId);
	}

	@Override
	public PrpLrtext findPrpLrtext(PrpLrtextId prpLrtextId) throws Exception {
		logger.info("查询追偿损余文字说明信息编号为" + prpLrtextId + "的追偿损余文字说明信息");
		return super.get(PrpLrtext.class, prpLrtextId);
	}

	@Override
	public Page findPrpLrtext(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取追偿损余文字说明信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLrtext> findPrpLrtext(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据追偿损余文字说明编号查询出追偿损余文字说明信息
	 * @param certiNo ：传入的追偿损余文字说明编号
	 * @return 返回追偿损余文字说明
	 */
	public PrpLrtext findPrpLrtext(String certiNo) throws Exception{
		PrpLrtext prpLrtext = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLrtext> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLrtext = resultList.get(0);
		}
		return prpLrtext;
	}

}

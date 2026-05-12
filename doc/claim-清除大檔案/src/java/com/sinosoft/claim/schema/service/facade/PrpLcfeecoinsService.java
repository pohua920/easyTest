package com.sinosoft.claim.schema.service.facade;
/**
 * 联共保赔付金额分摊接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLcfeecoins;
import com.sinosoft.claim.schema.model.PrpLcfeecoinsId;

public interface PrpLcfeecoinsService {
	
	/**
	 * 保存联共保赔付金额分摊信息
	 * @param prpLcfeecoins ：传入的联共保赔付金额分摊
	 */
	public void save(PrpLcfeecoins prpLcfeecoins) throws Exception;
	
	/**
	 * 联共保赔付金额分摊信息
	 * @param list  :传入的联共保赔付金额分摊信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLcfeecoins> list) throws Exception;
	
	/**
	 * 删除联共保赔付金额分摊信息
	 * @param prpLcfeecoinsId ：传入的联共保赔付金额分摊编号
	 */
	public void delete(PrpLcfeecoinsId prpLcfeecoinsId) throws Exception;

	/**
	 * 更新联共保赔付金额分摊信息
	 * @param prpLcfeecoins :传入需要更新的联共保赔付金额分摊
	 */
	public void update(PrpLcfeecoins prpLcfeecoins) throws Exception;

	/**
	 * 根据联共保赔付金额分摊编号查询出联共保赔付金额分摊信息
	 * @param prpLcfeecoinsId ：传入的联共保赔付金额分摊编号
	 * @return 返回联共保赔付金额分摊
	 */
	public PrpLcfeecoins findPrpLcfeecoins(PrpLcfeecoinsId prpLcfeecoinsId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的联共保赔付金额分摊页面信息
	 */
	public Page findPrpLcfeecoins(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取联共保赔付金额分摊信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的联共保赔付金额分摊信息   的列表
	 */
	public List<PrpLcfeecoins> findPrpLcfeecoins(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据联共保赔付金额分摊编号查询出联共保赔付金额分摊信息
	 * @param certiNo ：传入的联共保赔付金额分摊编号
	 * @return 返回联共保赔付金额分摊
	 */
	public PrpLcfeecoins findPrpLcfeecoins(String certiNo) throws Exception;
}

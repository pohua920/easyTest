package com.sinosoft.claim.common.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDclass;
import com.sinosoft.claim.schema.model.PrpDrisk;
import com.sinosoft.claim.schema.service.facade.PrpDclassService;
import com.sinosoft.claim.undwrt.vo.RiskCategoryCodeDto;

public class PrpDriskServiceSpringImpl extends GenericDaoHibernate<PrpDrisk, String> implements PrpDriskService {

	/** 险类service*/
	private PrpDclassService prpDclassService;
	/**
	 * 按条件查询多条数据
	 * @param conditions 查询条件
	 * @return List 包含prpDrisk的集合
	 * @throws Exception
	 */
	@Override
	public List<PrpDrisk> findByConditions(String conditions) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}

	/**
	 * 根据险种编号查询出险种信息
	 * @param riskCode ：传入的险种编号
	 * @return 返回险种
	 */
	@Override
	public PrpDrisk findPrpDrisk(String riskCode){
		logger.info("查询险种代码为" + riskCode + "的险种信息");
		return super.get(PrpDrisk.class, riskCode);
	}
	
	/**
	 * 保存险种信息
	 * @param prpDrisk ：传入的险种
	 */
	@Override
	public void save(PrpDrisk prpDrisk) throws Exception {
		logger.info("保存险种信息");
		super.save(prpDrisk);
	}

	/**
	 * 保存或修改
	 * @param prpDrisk 险类
	 * @throws Exception
	 */
	public void saveOrUpdate(PrpDrisk prpDrisk) throws Exception {
		super.getSession().merge(prpDrisk);
	}

	/**
	 * 删除险种信息
	 * @param riskCode ：传入的险种编号
	 */
	@Override
	public void delete(String riskCode) throws Exception {
		super.deleteByPK(PrpDrisk.class, riskCode);
		logger.info("删除险种编号为" + riskCode + "的险种信息");
	}

	/**
	 * 保存险种信息
	 * @param list:保存险种信息
	 */
	@Override
	public void save(List<PrpDrisk> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**险种修改
	 * @description: 险种修改
	 * @param PrpDrisk 险类对象
	 * @throws Exception
	 */
	@Override
	public void update(PrpDrisk prpDrisk) {
		logger.info("修改险种信息开始");
		super.update(prpDrisk);
		logger.info("修改险种信息结束");
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的险种页面信息
	 */
	@Override
	public Page findPrpDrisk(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取险种列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param conditions 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的险种页面信息
	 */
	@Override
	public Page findPrpDrisk(String conditions, int pageNo, int pageSize) throws Exception{
		logger.info("获取险种列表信息");
		String sql = "select * from PrpDrisk where "+conditions;
		Page page = HibernateUtils.findPagebySql(this.getSession(), sql, pageNo, pageSize, PrpDrisk.class);
		return page;
	}
	/**
	 * 按条件查询多条数据
	 * @param queryRule 查询条件
	 * @return Collection 包含prpDrisk的集合
	 * @throws Exception
	 */
	@Override
	public List<PrpDrisk> findPrpDrisk(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	
	/**
     * 通过险种大类从PrpDclass表查出该大类的所有ClassCode，
     * 再从PrpDrisk表查出这些ClassCode的所有RiskCode，展现在页面上。
     * @return
	 * @throws Exception
     */
	@SuppressWarnings("unchecked")
	@Override
	public List findRiskCodeByRiskCategory() throws Exception{
        /*
         * 通过险种大类从PrpDclass表查出该大类的所有ClassCode，
         * 再从PrpDrisk表查出这些ClassCode的所有RiskCode，展现在页面上。
         */
		String[] riskCategory = { "D", "Y", "Q", "E", "G", "Z" };
        List riskCodeCollection = new ArrayList();
        List<PrpDrisk> riskCollection = null;
        PrpDclass prpDclass = null;
        PrpDrisk prpDrisk = null;
        RiskCategoryCodeDto riskCategoryCodeDto = null;
		for (int i = 0; i < riskCategory.length; i++) {
			riskCollection = this.findByConditions(" riskcode in ( select outercode from uticodetransfer where risktype = '"+riskCategory[i]+"' ) and ValidStatus='1' ORDER BY RISKCODE ");
			for (int k = 0; k < riskCollection.size(); k++) {
				prpDrisk = (PrpDrisk) riskCollection.get(k);
				riskCategoryCodeDto = new RiskCategoryCodeDto();
				riskCategoryCodeDto.setRiskCategory(riskCategory[i]);
				riskCategoryCodeDto.setRiskCode(prpDrisk.getRiskCode());
				riskCategoryCodeDto.setRiskName(prpDrisk.getRiskCName());
				riskCodeCollection.add(riskCategoryCodeDto);
			}
		}
        return riskCodeCollection;
    }

	public PrpDclassService getPrpDclassService() {
		return prpDclassService;
	}

	public void setPrpDclassService(PrpDclassService prpDclassService) {
		this.prpDclassService = prpDclassService;
	}

}

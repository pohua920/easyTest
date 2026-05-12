package com.sinosoft.claim.schema.service.spring;

/**
 * 代码信息接口实现类
 * @author 中科软
 *
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDcodeId;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;

public class PrpDcodeServiceSpringImpl extends GenericDaoHibernate<PrpDcode, PrpDcodeId> implements PrpDcodeService {

	/**
	 * 根据查询条件获取通用代码的列表
	 * @param condition 查询条件
	 * @return 包含的 通用代码 的列表
	 */
	@Override
	public List<PrpDcode> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的通用代码页面信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrpDcode> findPrpDcode(QueryRule queryRule, int pageNo, int pageSize) {
		List<PrpDcode> list = null;
		Page page = super.find(queryRule, pageNo, pageSize);
		list = page.getResult();
		return list;
	}

	/**
	 * 根据查询语句获取通用代码 的列表
	 * @param sql 查询语句(完整sql)
	 * @return 包含的 通用代码 的列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrpDcode> findPrpDcodeBySql(String sql) {
		List<PrpDcode> list = null;
		list = super.getSession().createSQLQuery(sql).addEntity(PrpDcode.class).list();
		return list;
	}

	/**
	 * 根据查询对象获取 通用代码 的列表
	 * @param queryRule 查询对象
	 * @return 包含的通用代码 的列表
	 */
	@Override
	public List<PrpDcode> findPrpDcode(QueryRule queryRule) throws Exception {
		try {
			List<PrpDcode> list = super.find(queryRule);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 分页查询通用代码表的数据
	 */
	@Override
	public Page findByConditions(String conditions, int pageNo, int pageSize) {
		if (DataUtils.emptyToNull(conditions) == null) {
			conditions = " 1=1 ";
		}
		String sql = "select * from PrpDcode where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize, PrpDcode.class);
	}
	/**
	 * 分页查询通用代码表的数据
	 */
	public Page findByConditionBySql(String conditions, int pageNo, int pageSize) {
		if (DataUtils.emptyToNull(conditions) == null) {
			conditions = " 1=1 ";
		}
		String sql = "select * from PrpDcode where " + conditions;
		Page page = HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize);
		Object[] obj = null;
		PrpDcode prpDcode = null;
		PrpDcodeId id = null;
		List<PrpDcode> prpDcodeList = new ArrayList<PrpDcode>();
		for(int i =0;i<page.getResult().size();i++){
			obj = (Object[]) page.getResult().get(i);
			prpDcode  = new PrpDcode();
			id = prpDcode.getId();
			id.setCodeType(DataUtils.getString(obj[0]));
			id.setCodeCode(DataUtils.getString(obj[1]));
			prpDcode.setCodeCName(DataUtils.getString(obj[2]));
			prpDcode.setCodeEName(DataUtils.getString(obj[3]));
			prpDcode.setNewCodeCode(DataUtils.getString(obj[4]));
			prpDcode.setValidStatus(DataUtils.getString(obj[5]));
			prpDcode.setFlag(DataUtils.getString(obj[6]));
			prpDcode.setUpperCode(DataUtils.getString(obj[7]));
			prpDcodeList.add(prpDcode);
		}
		page = new Page(page.getStart(),page.getTotalCount(),page.getPageSize(),prpDcodeList);
		return  page;
//		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize, PrpDcode.class);
	}

	/**
	 * 根据主键查询通用代码数据
	 * @param registNo ：传入的代码表主键
	 * @return 返回代码
	 */
	@Override
	public PrpDcode findPrpDcode(PrpDcodeId prpDcodeId) {
		logger.info("查询主键编号为" + prpDcodeId + "的主键信息");
		return super.get(PrpDcode.class, prpDcodeId);
	}

	/**
	 * 根据类型和代码获取通用代码
	 * @param codeType 类型
	 * @param codeCode 代码
	 * @return 包含的 通用代码
	 */
	@Override
	public PrpDcode findByPrimaryKey(String codeType, String codeCode) {
		logger.info("查询主键编号为" + codeType + "-" + codeCode + "的主键信息");
		PrpDcodeId prpDcodeId = new PrpDcodeId(codeType, codeCode);
		return findPrpDcode(prpDcodeId);
	}
	/**
	 * 根据类型和代码获取通用代码,根据newCodeCode关联表PrpDcodeRisk查询
	 * @param codeType 类型
	 * @param codeCode 代码
	 * @return 包含的 通用代码
	 */
	public PrpDcode findByPrimaryKey(String codeType, String codeCode,String riskCode)throws Exception {
		logger.info("查询主键编号为" + codeType + "-" + codeCode + "的主键信息");
		String sql = "select * from PrpDcode where  codetype='"+codeType+"' and codeCode='"+codeCode
		+"' and newCodeCode in (select codecode from PrpdCodeRisk where Codetype='"+codeType+"' and riskCode in ('"+riskCode+"','0000'))";
		List<PrpDcode> list = (List<PrpDcode>) HibernateUtils.findbySql(super.getSession(), sql, PrpDcode.class);
		PrpDcode prpDcode = null;
		if(list.size()>0){
			prpDcode = list.get(0);
		}
		return prpDcode;
	}
	/**
	 * 删除通用代码数据信息
	 * @param prpDcodeId ：传入的通用代码数据信息编号
	 */
	@Override
	public void delete(PrpDcodeId prpDcodeId) throws Exception {
		logger.info("删除通用代码数据为" + prpDcodeId + "的通用代码数据信息");
		super.deleteByPK(prpDcodeId);
	}

	/**
	 * 保存通用代码数据信息
	 * @param PrpDcode ：传入的通用代码数据
	 */
	@Override
	public void save(PrpDcode prpDcode) throws Exception {
		logger.info("保存通用代码数据信息");
		super.save(prpDcode);
	}

	/**
	 * 保存通用代码数据信息
	 * @param list:保存通用代码数据信息
	 */
	@Override
	public void save(List<PrpDcode> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

}

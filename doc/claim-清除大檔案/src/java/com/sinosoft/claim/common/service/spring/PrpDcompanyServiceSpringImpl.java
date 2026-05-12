package com.sinosoft.claim.common.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.sysframework.common.Constants;

public class PrpDcompanyServiceSpringImpl extends GenericDaoHibernate<PrpDcompany, String> implements PrpDcompanyService {

	/**
	 * 保存机构信息
	 * @param prpDcompany 传入的机构
	 * @throws Exception
	 */
	@Override
	public void save(PrpDcompany prpDcompany) throws Exception {
		logger.info("保存机构信息");
		super.save(prpDcompany);
	}

	/**
	 * @param prpDcompany
	 * @throws Exception 保存或修改，
	 */
	@Override
	public void saveOrUpdate(PrpDcompany prpDcompany) throws Exception {
		super.getSession().merge(prpDcompany);
	}

	/**
	 * 删除机构信息
	 * @param comCode ：传入的机构代码
	 */
	@Override
	public void delete(String comCode) throws Exception {
		super.deleteByPK(PrpDcompany.class, comCode);
		logger.info("删除机构代码为" + comCode + "的机构信息");
	}

	/**
	 * 保存机构信息
	 * @param list:保存机构信息
	 */
	@Override
	public void save(List<PrpDcompany> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * @description: 机构修改
	 * @param PrpDcompany prpDcompany
	 * @throws Exception
	 */
	@Override
	public void update(PrpDcompany prpDcompany) {
		logger.info("修改机构信息开始");
		super.update(prpDcompany);
		logger.info("修改机构信息结束");
	}

	/**
	 * 根据sql语句条件查询
	 * @param conditions 查询条件
	 * @return 返回部门结果
	 * @throws Exception 
	 */
	@Override
	public List<PrpDcompany> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}

	/**
	 * 根据机构编号查询出机构信息
	 * @param comCode ：传入的机构编号
	 * @return 返回机构
	 * @throws Exception 
	 */
	@Override
	public PrpDcompany findPrpDcompany(String comCode) throws Exception {
		logger.info("查询机构编号为" + comCode + "的机构信息");
		return super.get(PrpDcompany.class, comCode);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的机构页面信息
	 * @throws Exception 
	 */
	@Override
	public Page findPrpDcompany(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取机构列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的机构页面信息
	 * @throws Exception 
	 */
	@Override
	public Page findPrpDcompany(String conditions, int pageNo, int pageSize) throws Exception {
		logger.info("获取机构列表信息");
		String sql = "select * from PrpDcompany where " + conditions;
		Page page = HibernateUtils.findPagebySql(this.getSession(), sql, pageNo, pageSize, PrpDcompany.class);
		return page;
	}

	/**
	 * 查询部门的结果集
	 * @param queryRule 查询条件
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PrpDcompany> findPrpDcompany(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 是否存在
	 * @param comCode 机构代码
	 * @return
	 * @throws Exception 
	 */
	@Override
	public boolean isExist(String comCode) throws Exception {
		String hql = "from PrpDcompany where comCode=?";
		long count = super.getCount(hql, comCode);
		if (count < 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 查询满足模糊查询条件的记录数
	 * @param conditions conditions
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 */
	@Override
	public int getCount(String conditions) throws Exception {
		int count = -1;
		StringBuffer buffer = new StringBuffer(100);
		buffer.append("SELECT count(*) FROM (SELECT * FROM PrpDcompany WHERE ");
		buffer.append(conditions);
		buffer.append(")");
		Session session = super.getSession();
		count = (int) HibernateUtils.getCountbyCountSql(session, buffer.toString());
		return count;
	}

	/**
	 * 根据机构号查询出机构信息
	 * @param comCode ：传入的机构代码
	 * @return 返回机构信息
	 * @throws Exception 
	 */
	@Override
	public PrpDcompany query(String comCode) {
		logger.info("查询机构代码为" + comCode + "的机构信息");
		return super.get(PrpDcompany.class, comCode);
	}
	/**
	 * 查询机构
	 * @param userCode 用户代码
	 * @param taskCode 用户
	 * @param rule 查询条件
	 * @param pageNo 起始页
	 * @param pageSize 中页数
	 * @return
	 */
	@Override
	public Page findCompanyByRule(String userCode, String taskCode, QueryRule rule, int pageNo, int pageSize) {
		return super.find(rule, pageNo, pageSize);
	}

	/**
	 * 查询下级机构的数量
	 * @param prpDComCode 机构代码
	 * @param i 下几级（3或者4级）
	 * @return 返回多少个
	 */
	@Override
	public int getCompanyAmount(String prpDComCode, int i) {
		String hql = "";
		if (i == 3) {
			hql = " from PrpDcompany where upperComCode= ? and comLevel='3'";
		} else if (i == 4) {
			hql = " from PrpDcompany where upperComCode in (select comCode from PrpDcompany where upperComCode= ? and comLevel='3')";
			// hql+=" and comLevel='4' ";
		}
		int amount = (int) super.getCount(hql, prpDComCode);
		return amount;
	}

	/**
	 * 查询部门名称
	 * @param comCode 编码代码
	 * @param isChinese true 获取中午名称，false 获取英文名称
	 * @return 根据部门代码，获取部门名称
	 */
	@Override
	public String getComName(String comCode, boolean isChinese) throws Exception {
		String comName = "";
		if (comCode != null && !"".equals(comCode)) {
			PrpDcompany prpDcompany = super.get(comCode);
			if (prpDcompany != null) {
				if (isChinese) {
					comName = prpDcompany.getComCName();
				} else {
					comName = prpDcompany.getComEName();
				}
			}
		}
		return comName;
	}

	/**
	 * 根据部门编号，获取部门中午名称
	 * @param comCode 编码代码
	 * @return 返回部门名称
	 */
	@Override
	public String getComName(String comCode) throws Exception {
		return this.getComName(comCode, true);
	}

	/**
	 * 获得某个用户所有分配的机构
	 * @Description: 
	 * @author 中科软
	 * @param userCode 用户代码
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrpDcompany> findUserGradeCompanyListByUserCode(String userCode) throws Exception {
		String sql = "select * from PrpDcompany prpdCompany where  exists (Select comCode From utiusergrade Where userCode=? and comCode = prpdCompany.comCode)";
		return super.findBySql(sql, userCode);
	}
	

	/**
	 * 查询机构
	 * @param conditions 查询条件
	 * @param pageNo 起始页
	 * @param pageSize 每页显示的条数
	 * @return 返回page对象
	 * @throws Exception
	 */
	public Page findByPage(String conditions, int pageNo, int pageSize) throws Exception {
		if (DataUtils.emptyToNull(conditions) == null) {
			conditions = " 1=1 ";
		}
		String sql = "select * from PrpDcompany where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize, PrpDcompany.class);
	}
	/**
	 * 翻译代码
	 * @param userCode 用户代码
	 * @param isChinese 中文，英文
	 * @throws Exception
	 * @return String 返回名称
	 */
	@Override
	public String translateCode(String comCode, boolean isChinese) throws Exception {
		String codeName = "";
		if (comCode == null || comCode.equals("")) {
			return codeName;
		}
		PrpDcompany prpDcompany = super.get(PrpDcompany.class, comCode);
		if (prpDcompany != null) {
			if (isChinese) {
				codeName = prpDcompany.getComCName();
			} else {
				codeName = prpDcompany.getComEName();
			}
		}
		return codeName;
	}

	 /**
	   * 获得部门信息
	   * @param  comCode
	   * @return prpDcompany对象
	   * @throws Exception
	   */
	@Override
	public PrpDcompany findByPrimaryKey(String comCode) {
		return super.get(PrpDcompany.class, comCode);
	}

	 /**
     * 按comCode查询
     * @param comCode 部门代码
     * @param withSubCompany 是否包含下级
     * @param conditions 附加条件
     * @return 包含prpDcompanyDto的集合
     * @throws Exception
     */
	@Override
	public Collection<PrpDcompany> findByComCode(String comCode,
			boolean withSubCompany, String conditions) throws Exception {
		Collection<PrpDcompany> collection = new ArrayList<PrpDcompany>();
        if (conditions.trim().length() == 0 && withSubCompany) {
            collection = findByComCode( comCode, "",
                    Constants.SUB_COMPANY);
        } else {
        	//modify by xuning gpic 20071126
            //String allConditions = "ComCode = '" + comCode + "'";
        	String allConditions = "";
            // 包含下级
            if (withSubCompany) {
                allConditions = " (comCode in"
                        + " (Select ComCode from prpdCompany Start With ComCode  = '"
                        + comCode
                        + "' Connect By Prior comCode = uppercomCode  and  "
                        + ""
                        + " prior ComCode != ComCode  and validstatus='1'))";

            }
            else{
            	allConditions = "ComCode = '" + comCode + "'";
            }
            allConditions += conditions;
            allConditions += " ORDER BY ComCode";
            collection = this.findByConditions(allConditions);
        }
        return collection;
	}
	
	 /**
     * 按comCode查询
     * @param comCode 部门代码
     * @param exceptComCode 是否包含下级
     * @param queryComType 附加条件
     * @return 包含prpDcompanyDto的集合
     * @throws Exception
     */
	public Collection<PrpDcompany> findByComCode( String comCode,
	            String exceptComCode, int queryComType) throws Exception {
	    	return findByComCode( comCode, true, " and 1=1");
	 }
	
}

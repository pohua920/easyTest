package com.sinosoft.claim.schema.service.spring;

/**
 * 代查勘接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.dto.domain.PrpDuserDto;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpLgeneralClaimTaskLog;
import com.sinosoft.claim.schema.service.facade.PrpLgeneralClaimTaskLogService;

public class PrpLgeneralClaimTaskLogServiceSpringImpl extends GenericDaoHibernate<PrpLgeneralClaimTaskLog, String> implements PrpLgeneralClaimTaskLogService {

    /**
     * 保存代查勘信息
     * @param prpLgeneralClaimTaskLog ：传入的代查勘
     */
    @Override
    public void save(PrpLgeneralClaimTaskLog prpLgeneralClaimTaskLog) throws Exception {
        logger.info("保存代查勘信息");
        super.save(prpLgeneralClaimTaskLog);
    }

    /**
     * @param prpLgeneralClaimTaskLog
     * @throws Exception 保存或修改，
     */
    public void saveOrUpdate(PrpLgeneralClaimTaskLog prpLgeneralClaimTaskLog) throws Exception {
        super.getSession().merge(prpLgeneralClaimTaskLog);
    }

    /**
     * @description: 代查勘修改
     * @param PrpLgeneralClaimTaskLog prpLgeneralClaimTaskLog
     * @throws Exception
     */
    @Override
    public void update(PrpLgeneralClaimTaskLog prpLgeneralClaimTaskLog) {
        logger.info("修改代查勘信息开始");
        super.update(prpLgeneralClaimTaskLog);
        logger.info("修改代查勘信息结束");
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
        buffer.append("SELECT count(*) FROM (SELECT * FROM PrpLgeneralClaimTaskLog WHERE ");
        buffer.append(conditions);
        buffer.append(")");
        Session session = super.getSession();
        count = (int) HibernateUtils.getCountbyCountSql(session, buffer.toString());
        return count;
    }

    /**
     * @param conditions
     * @param pageNo
     * @param rowsPerPage
     * @return
     * @throws Exception 代查勘的查询
     */
    @Override
    public Page findByConditions(String conditions, int pageNo, int pageSize) throws Exception {
		String hql = "select * from PrpLgeneralClaimTaskLog where " + conditions;
		Page page = HibernateUtils.findPagebySql(super.getSession(), hql, pageNo, pageSize, PrpLgeneralClaimTaskLog.class);
    	return page;
    }
    
    @Override
    public List<PrpLgeneralClaimTaskLog> findPrpLgeneralClaimTaskLog(QueryRule queryRule) {
        return super.find(queryRule);
    }
    /**
	 * 查询能够处理某一机构下拥有某项权限的操作员
	 * @throws Exception
	 * @return Page
	 * @author 中科软
	 */
	public Page queryUserHaveRights(String conditions, int pageNo, int pageSize) throws Exception {
		List<PrpDuserDto> list = new ArrayList<PrpDuserDto>(pageSize);
		List<Object[]> resultSet = (List<Object[]>) super.getSession().createSQLQuery(conditions).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
		PrpDuserDto prpDuserDto = null;
		for (int i = 0; i < resultSet.size(); i++) {
			prpDuserDto = new PrpDuserDto();
			Object[] obj = resultSet.get(i);
			if (obj[0] != null) {
				prpDuserDto.setUserCode(obj[0].toString());
			}
			if (obj[1] != null) {
				prpDuserDto.setUserName(obj[1].toString());
			}
			list.add(prpDuserDto);
		}
		conditions = "select count(*) from (" + conditions + ")";
		long count = HibernateUtils.getCountbyCountSql(super.getSession(), conditions);
		Page page = new Page((pageNo - 1) * pageSize, count, pageSize, list);
		return page;
	}

	/**
	 * 根据当前机构取得该机构的二级机构
	 * @param workFlowDto 理赔工作流流程处理处理任务取消的对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getLevelTwoComCode(PrpDcompany prpDcompany) throws SQLException, Exception {
		String levelTwoComCode = "";//北部業管中心
		String comCode = prpDcompany.getComCode();
		if(prpDcompany!=null) {
			String comLevel = prpDcompany.getComLevel();
			if ("1".equals(comLevel)) {
				levelTwoComCode = "00";//北部業管中心
			} else if ("2".equals(comLevel)) {
				levelTwoComCode = comCode;
			} else {
				StringBuffer buffer = new StringBuffer(200);
				buffer.append("SELECT COMCODE FROM (");
				buffer.append("SELECT COMCODE,COMLEVEL ");
				buffer.append("FROM PRPDCOMPANY ");
				buffer.append("WHERE 1=1 ");
				buffer.append("START WITH COMCODE = '");
				buffer.append(comCode);
				buffer.append("' CONNECT BY PRIOR UPPERCOMCODE = COMCODE ");
				buffer.append("AND PRIOR COMCODE <> UPPERCOMCODE ");
				buffer.append(") WHERE COMLEVEL = '2'");
				Object obj = super.getSession().createSQLQuery(buffer.toString()).uniqueResult();
				if (obj != null) {
					levelTwoComCode = obj.toString();
				}
			}
		}
		return levelTwoComCode;
	}
}
package cn.com.sinosoft.dms.service.spring;

import java.util.List;

import ins.framework.common.Page;
import ins.framework.dao.GenericDaoHibernate;

import cn.com.sinosoft.dms.service.facade.PrpDdriverService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sinosoft.dms.model.PrpDdriver;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDdriverServiceSpringImpl extends
		GenericDaoHibernate<PrpDdriver, String> implements PrpDdriverService {


	    public void deletePrpDdriver(PrpDdriver prpDdriver) {
			super.delete(prpDdriver);
		}

		public PrpDdriver findByPrimaryKey(String driverCode) {
			PrpDdriver prpDdriver = super.get(driverCode);
			return prpDdriver;
		}
		

		public void insertPrpDdriver(PrpDdriver prpDdriver) {
			super.save(prpDdriver);
		}

		public void updatePrpDdriver(PrpDdriver prpDdriver) {
			super.update(prpDdriver);
		}
		
		public Page getPrpDdriverList(PrpDdriver prpDdriver, int pageNo, int pageSize) {
			 StringBuffer hql = new StringBuffer();
		        hql.append(" from PrpDdriver prpDdriver where 1=1");
		        HqlRulesUtil hqlRules = new HqlRulesUtil();
		        hqlRules.addLike("drivingLicenseNo", prpDdriver.getDrivingLicenseNo());
		        hqlRules.addLike("driverName", prpDdriver.getDriverName());
		        if(hqlRules.getHql().trim()!=null&&!hqlRules.getHql().trim().equals("")){
		            hql.append("and "+hqlRules.getHql());
		        }
		        logger.debug("HQL is :"+hql.toString());
		        Page page = findByHql(hql.toString(), pageNo, pageSize);
		        return page;
		}
		public void deleteByPK(String PK){
			super.deleteByPK(PK);
		}
		public void deleteAll(List list){
			if(list!=null&&list.size()!=0){
				super.deleteAll(list);
			}
		}
}

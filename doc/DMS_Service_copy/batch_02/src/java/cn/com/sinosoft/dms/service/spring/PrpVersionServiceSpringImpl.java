package cn.com.sinosoft.dms.service.spring;

import ins.framework.common.Page;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpVersion;
import cn.com.sinosoft.dms.service.facade.PrpVersionService;

public class PrpVersionServiceSpringImpl extends
		GenericDaoHibernate<PrpVersion, String> implements
		PrpVersionService {
	/**
	 * 分页查询(PrpVersion表的所有记录)
	 */
	public Page getPrpVersionList(PrpVersion prpVersion,int pageNo, int pageSize) {
		String hql = "";
		hql = " from PrpVersion prpVersion  where 1=1";
		Page page = super.findByHql(hql, pageNo, pageSize);
		return page;
	}
	
    public PrpVersion findByPrimaryKey(String productId){
		String hql="from PrpVersion prpVersion where prpVersion.id.productId=?";
		List list = new ArrayList();
		list = super.findByHql(hql, productId);
		if(list.size()!=0){
			return (PrpVersion)list.get(0);
		}else{
			return null;
		}
    }
}

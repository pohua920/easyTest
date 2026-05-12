package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.DateTime;
import ins.framework.dao.GenericDaoHibernate;

import java.util.Date;
import java.util.List;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDexch;
import com.sinosoft.claim.schema.model.PrpDexchId;
import com.sinosoft.claim.schema.service.facade.PrpDexchService;
import com.sinosoft.sysframework.reference.AppConfig;
@SuppressWarnings("unchecked")
public class PrpDexchServiceSpringImpl extends GenericDaoHibernate<PrpDexch, PrpDexchId> implements PrpDexchService {
	
    @Override
    public PrpDexch findPrpDexch(Date exchDate, String baseCurrency, String exchCurrency){
    	if (exchDate == null) {
    		exchDate = new Date();
    	}
    	Date d = new DateTime(exchDate, DateTime.YEAR_TO_DAY);
		String sql = "select exchDate,baseCurrency,exchCurrency,base,exchRate,validStatus,flag from "+this.getDmsUser()+"prpdexch where exchDate = to_date('" + d.toString() + "','yyyy-mm-dd') and baseCurrency = '" + baseCurrency + "' and exchCurrency = '" + exchCurrency + "' and validStatus='1' ";
		List<PrpDexch> list =  (List<PrpDexch>) HibernateUtils.findbySql(super.getSession(), sql, PrpDexch.class);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
    }

    @Override
    public List<PrpDexch> findBasePrpDexch(Date exchDate, String baseCurrency) {
        if (exchDate == null) {
            exchDate = new Date();
        }
        Date d = new DateTime(exchDate, DateTime.YEAR_TO_DAY);
        String sql = "select exchDate,baseCurrency,exchCurrency,base,exchRate,validStatus,flag from "+this.getDmsUser()+"prpdexch where  exchDate = to_date('" + d.toString() + "','yyyy-mm-dd') and baseCurrency = '" + baseCurrency + "' and validStatus='1' ";
        List<PrpDexch> list = (List<PrpDexch>) HibernateUtils.findbySql(super.getSession(), sql,PrpDexch.class);
        return list;
    }

	@Override
    public List<PrpDexch> findExchPrpDexch(Date exchDate, String exchCurrency) {
        if (exchDate == null) {
            exchDate = new Date();
        }
        Date d = new DateTime(exchDate, DateTime.YEAR_TO_DAY);
        String sql = "select exchDate,baseCurrency,exchCurrency,base,exchRate,validStatus,flag from "+this.getDmsUser()+"prpdexch where exchDate = to_date('" + d.toString() + "','yyyy-mm-dd') and exchCurrency = '" + exchCurrency + "' and validStatus='1' ";
        List<PrpDexch> list = (List<PrpDexch>) HibernateUtils.findbySql(super.getSession(), sql,PrpDexch.class);
        return list;
    }
    /**
     * 获取dms系统用户
     * @return
     */
    private String getDmsUser(){
    	String dmsUser;
		try {
			dmsUser = AppConfig.get("sysconst.DMS_USER");
			if(!CommonUtils.isEmpty(dmsUser)){
				dmsUser = dmsUser+".";
			}else{
				dmsUser = "";
			}
			return dmsUser;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
    }
}

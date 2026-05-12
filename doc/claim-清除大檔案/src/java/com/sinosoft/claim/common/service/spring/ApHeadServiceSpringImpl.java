package com.sinosoft.claim.common.service.spring;

import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.List;

import com.sinosoft.claim.common.service.facade.ApHeadService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.compensate.vo.PayRefRecDto;

public class ApHeadServiceSpringImpl extends GenericDaoHibernate implements ApHeadService {

	@Override
	public PayRefRecDto findByQueryConditions(String conditions) throws Exception {
		StringBuffer buffer = new StringBuffer(200);
		// Æ´SQLÓï¾ä
		buffer.append("SELECT ");
		buffer.append("Wb_paydate,");
		buffer.append("Wb_obj_name,");
		buffer.append("Wb_pay_med,");
		buffer.append("Wb_pay_cry,");
		buffer.append("Wb_pay_amt ");
		buffer.append("FROM Ap_head WHERE ");
		buffer.append(conditions);
		List<?> list = HibernateUtils.findbySql(super.getSession(), buffer.toString());
		PayRefRecDto payRefRecDto = null;
		if (list != null && !list.isEmpty()) {
			Object[] object = (Object[]) list.get(0);
			payRefRecDto = new PayRefRecDto();
			String payDate = String.valueOf(object[0]);
			if (DataUtils.emptyToNull(payDate)!= null ) {
				payRefRecDto.setPayDate(payDate.substring(0, 10));
			}
			payRefRecDto.setPayName(String.valueOf(object[1]));
			payRefRecDto.setPayMethod(String.valueOf(object[2]));
			payRefRecDto.setCurrency(String.valueOf(object[3]));
			payRefRecDto.setAmount(((Number) object[4]).doubleValue());
		}
		return payRefRecDto;
	}

}

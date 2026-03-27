package com.sinosoft.undwrt.common.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.Collection;

import com.sinosoft.platform.dto.domain.UtiUserGradeDto;
import com.sinosoft.platform.ui.model.UtiUserGradeFindByConditionsCommand;
import com.sinosoft.undwrt.common.service.facade.ProcessCodeInputService;
import com.sinosoft.undwrt.common.util.Constants;

/**
 * 輸入員工代碼自動帶出機構代碼實現類.
 */
@SuppressWarnings("rawtypes")
public class ProcessCodeInputServiceSpringImpl extends GenericDaoHibernate implements ProcessCodeInputService {

	/**
	 * 得到可選代碼結構的集合.
	 * 
	 * @param userCode
	 *            人員工號
	 * @return 可選的機構代碼
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.ProcessCodeInputService#getComCodeOptionsText(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Collection<UtiUserGradeDto> getComCodeOptionsText(String userCode) throws Exception {

		String conditions = "UserCode ='" + userCode + "' AND GradeCode = '"+ Constants.GRADECODE_121 +"' Order By ComCode";
		UtiUserGradeFindByConditionsCommand command = new UtiUserGradeFindByConditionsCommand(conditions);
		return (Collection<UtiUserGradeDto>) command.execute();
	}

}

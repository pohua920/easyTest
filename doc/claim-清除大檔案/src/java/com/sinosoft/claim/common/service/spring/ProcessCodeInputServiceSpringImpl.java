package com.sinosoft.claim.common.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.Collection;

import com.sinosoft.claim.common.service.facade.ProcessCodeInputService;
import com.sinosoft.platform.dto.domain.UtiUserGradeDto;
import com.sinosoft.platform.ui.model.UtiUserGradeFindByConditionsCommand;

@SuppressWarnings("unchecked")
public class ProcessCodeInputServiceSpringImpl extends GenericDaoHibernate implements ProcessCodeInputService{
	private static final String SystemCode = "claim";
	
	/***
	 * 获取岗位机构信息
	 * @param userCode：用户代码
	 * @throws Exception
	 */
	public Collection<UtiUserGradeDto> getComCodeOptionsText(String userCode) throws Exception{
		String conditions = "UserCode ='"+ userCode +"' And gradecode in(Select distinct(GradeCode) From UtiGradeTask Where TaskCode Like'"+SystemCode+"%') Order By ComCode ";
        UtiUserGradeFindByConditionsCommand command = new UtiUserGradeFindByConditionsCommand(conditions);
        return (Collection<UtiUserGradeDto>) command.execute();
	}

}

package com.sinosoft.claim.schema.service.spring;

/**
 * 機構員工崗位差異功能權限信息接口实现类
 * @author 陈朋
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.UtiUserGradeTask;
import com.sinosoft.claim.schema.model.UtiUserGradeTaskId;
import com.sinosoft.claim.schema.service.facade.UtiUserGradeTaskService;

public class UtiUserGradeTaskServiceSpringImpl extends GenericDaoHibernate<UtiUserGradeTask, UtiUserGradeTaskId> implements UtiUserGradeTaskService {

	@Override
	public void save(UtiUserGradeTask utiUserGradeTask) throws Exception {
		logger.info("保存機構員工崗位差異功能權限信息信息");
		super.save(utiUserGradeTask);

	}

	@Override
	public void save(List<UtiUserGradeTask> list) throws Exception {
		logger.info("保存機構員工崗位差異功能權限信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(UtiUserGradeTaskId utiUserGradeTaskId) throws Exception {
		logger.info("删除機構員工崗位差異功能權限信息编号为" + utiUserGradeTaskId + "的機構員工崗位差異功能權限信息");
		super.deleteByPK(UtiUserGradeTask.class, utiUserGradeTaskId);
	}

	@Override
	public UtiUserGradeTask findUtiUserGradeTask(UtiUserGradeTaskId utiUserGradeTaskId) throws Exception {
		logger.info("查询機構員工崗位差異功能權限信息编号为" + utiUserGradeTaskId + "的機構員工崗位差異功能權限信息");
		return super.get(UtiUserGradeTask.class, utiUserGradeTaskId);
	}

	@Override
	public Page findUtiUserGradeTask(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取機構員工崗位差異功能權限信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<UtiUserGradeTask> findUtiUserGradeTask(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	/**
	 * 检查这个用户是否配置了这个权限,true有这个权限，false没有这个权限
	 * @param userDto
	 * @param taskCode
	 * @return
	 * @throws Exception
	 */
	public boolean checkPower(UserDto userDto,String taskCode)throws Exception{
		//mantis： CLM0067 ，處理人員： BK007 蘇哲 ，需求單編號： CLM0067.理賠系統-備案修改權限修正   開始
//		boolean flag = false;
//		if(userDto!= null&&taskCode!=null){
//			String hql = " from UtiUserGradeTask task where task.id.userCode = '"+userDto.getUserCode()+"' and task.id.taskCode = '"+taskCode+"'";
//			Long count = super.getCount(hql);
//			if(count>0){
//				flag = true;
//			}
//		}
//		return flag;
		long permValue = 0;
		try {
			List<?> resultList = new ArrayList<Object>();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT NVL(MAX(VALUE),0) VALUE FROM ( ");
			sb.append("  SELECT GRADECODE,MIN(TO_NUMBER(VALUE)) VALUE FROM ( ");
			sb.append("   SELECT UG.COMCODE,UG.USERCODE,GT.GRADECODE, GT.TASKCODE, null GRANTLEVEL,null GRANTVALUE,GT.VALUE, GT.REMARK, GT.FLAG FROM UTIUSERGRADE UG ");
			sb.append("   LEFT JOIN UTIGRADETASK GT ON GT.GRADECODE = UG.GRADECODE ");
			sb.append("   WHERE UG.USERCODE = '"+userDto.getUserCode()+"' AND UG.COMCODE = '"+userDto.getComCode()+"' AND TASKCODE = '"+taskCode+"' ");
			sb.append("   UNION ALL ");
			sb.append("   SELECT COMCODE, USERCODE, GRADECODE, TASKCODE, GRANTLEVEL, GRANTVALUE, VALUE, REMARK, FLAG FROM UTIUSERGRADETASK ");
			sb.append("   WHERE USERCODE = '"+userDto.getUserCode()+"' AND COMCODE = '"+userDto.getComCode()+"' AND TASKCODE = '"+taskCode+"' ");
			sb.append("  ) ");
			sb.append("  GROUP BY GRADECODE ");
			sb.append(" ) ");
			Query query = getSession().createSQLQuery(sb.toString());
			resultList = query.list();
			if (resultList.size() > 0) {
				Object obj = resultList.get(0);
				if(obj != null && obj instanceof BigDecimal){
					BigDecimal object = (BigDecimal) obj;
					permValue = object.longValue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return permValue>0;
		//mantis： CLM0067 ，處理人員： BK007 蘇哲 ，需求單編號： CLM0067.理賠系統-備案修改權限修正  結束
	}
}

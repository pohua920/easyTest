package com.sinosoft.one.bpm.service.spring;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.sinosoft.one.bpm.model.TaskParamInfo;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.sinosoft.one.bpm.model.ProcessInstanceBOInfo;
import com.sinosoft.one.bpm.service.facade.ProcessInstanceBOService;

public class ProcessInstanceBOServiceSupport implements ProcessInstanceBOService{
	private EntityManager em;
	private boolean useJTA;
    private static Logger logger = LoggerFactory.getLogger(ProcessInstanceBOServiceSupport.class);
	
	public ProcessInstanceBOServiceSupport(EntityManager em, boolean useJTA) {
		this.em = em;
		this.useJTA = useJTA;
	}
	
	
	public ProcessInstanceBOInfo getProcessInstanceBOInfo(
			String processId, String businessId) {
		ProcessInstanceBOInfo result = null;
		try {
	        Query query = em.createNamedQuery("ProcessInstanceBOInfoForProcessIdAndBusinessId");
	        query.setParameter("processId", processId);
	        query.setParameter("businessId", businessId);
	        result = (ProcessInstanceBOInfo) query.getSingleResult();
		} catch(NoResultException exception) {
			logger.warn("businessId : " + businessId + ", processId : " + processId);
			logger.warn(exception.getLocalizedMessage());
		} 
		return result;
	}
	
	

	public void createProcessInstanceBOInfo(final ProcessInstanceBOInfo info) {
		if(useJTA && TransactionSynchronizationManager.isActualTransactionActive()) {
			em.joinTransaction();
		}
		em.persist(info); 
	}

    public void insertTaskParamInfo(TaskParamInfo taskParamInfo){
    	
        if(useJTA && TransactionSynchronizationManager.isActualTransactionActive()) {
            em.joinTransaction();
        }
        em.merge(taskParamInfo);
//        em.persist(taskParamInfo);
    }

    @Override
    public void deleteTaskParamInfo(TaskParamInfo taskParamInfo) {
       em.remove(taskParamInfo);
    }
    @Override
    public void deleteTaskParamInfo(long taskId, String userId, String processId, String businessId, long processInstanceId) {
        String hqlString = "delete from TaskParamInfo t " +
                "            where t.taskId=? " +
                "              and t.userId=? " +
                "              and t.processId=? " +
                "              and t.businessId=?" +
                "              and t.processInstanceId=?";
        Query query = em.createQuery(hqlString);
        query.setParameter(0, taskId);
        query.setParameter(1, userId);
        query.setParameter(2, processId);
        query.setParameter(3, businessId);
        query.setParameter(4, processInstanceId);
        query.executeUpdate();
    }

    @Override
    public void deleteProcesssInstanceBOInfo(String processId, long processInstanceId) {
        String hqlString = "delete from ProcessInstanceBOInfo t " +
                "            where t.processId=? and t.processInstanceId=?";
        Query query = em.createQuery(hqlString);
        query.setParameter(0, processId);
        query.setParameter(1, processInstanceId);
        query.executeUpdate();
    }

    @Override
    public void deleteProcesssInstanceBOInfo(ProcessInstanceBOInfo processsInstanceBOInfo) {
        em.remove(processsInstanceBOInfo);
    }

    @Override
    public TaskParamInfo findTaskParamInfo(String processId, String businessId, long processInstanceId){
        TaskParamInfo result = null;
        Query query = null;
        try {
            query = em.createNamedQuery("TaskParamInfoForProcessInstanceId");
            query.setParameter("processInstanceId", processInstanceId);
            query.setParameter("businessId",businessId);
            query.setParameter("processId",processId);
            result = (TaskParamInfo)  query.getSingleResult();
        } catch(NoResultException exception) {
            logger.warn(exception.getLocalizedMessage());
        } catch (NonUniqueResultException e) {
            logger.warn(e.getLocalizedMessage());
            result = (TaskParamInfo) query.getResultList().get(0);
        }
        return result;
    }

    @Override
    public TaskParamInfo findTaskParamInfo(String userId, String processId, String businessId, long processInstanceId,Long taskId) {
        TaskParamInfo result = null;
        Query query = null;
        try {
            query = em.createNamedQuery("TaskParamInfoForTaskId");
            query.setParameter("taskId", taskId);
            query.setParameter("userId",userId);
            query.setParameter("processInstanceId", processInstanceId);
            query.setParameter("businessId",businessId);
            query.setParameter("processId",processId);
            result = (TaskParamInfo)  query.getSingleResult();
        } catch(NoResultException exception) {
            logger.warn(exception.getLocalizedMessage());
        } catch (NonUniqueResultException e) {
            logger.warn(e.getLocalizedMessage());
            result = (TaskParamInfo) query.getResultList().get(0);
        }
        return result;
    }

    @Override
    public List<TaskParamInfo> listTaskParamInfo(String processId, String businessId, long processInstanceId) {
        List<TaskParamInfo> result = null;
        Query query = null;
        try {
            query = em.createNamedQuery("TaskParamInfoListForBusinessId");
            query.setParameter("processInstanceId",processInstanceId);
            query.setParameter("businessId",businessId);
            query.setParameter("processId",processId);
            result = (List<TaskParamInfo>) query.getResultList();
        } catch(NoResultException exception) {
            logger.warn(exception.getLocalizedMessage());
        } catch (NonUniqueResultException e) {
            logger.warn(e.getLocalizedMessage());
        }
        return result==null? Collections.EMPTY_LIST:result;
    }

    public ProcessInstanceBOInfo getProcessInstanceBOInfo(long processInstanceId) {
		ProcessInstanceBOInfo result = null;
		Query query = null;
		try {
	        query = em.createNamedQuery("ProcessInstanceBOInfoForProcessInstanceId");
	        query.setParameter("processInstanceId", processInstanceId);
	        result = (ProcessInstanceBOInfo)  query.getSingleResult();
		} catch(NoResultException exception) {
			logger.warn(exception.getLocalizedMessage());
		} catch (NonUniqueResultException e) {
			logger.warn(e.getLocalizedMessage());
			result = (ProcessInstanceBOInfo) query.getResultList().get(0);
		}
		return result;
	}

	public void removeProcessInstanceBOInfo(final ProcessInstanceBOInfo info) {
		info.setModifyTime(new Date());
		info.setStatus(String.valueOf(ProcessInstanceBOInfo.Status.REMOVE.ordinal()));
		em.merge(info);
	}
	
	public void removeProcessInstanceBOInfo(final long piId) {
//		String hqlString = "update ProcessInstanceBOInfo p set p.modifyTime=:modifyTime, p.status=:status where p.processInstanceId=:processInstanceId";
//		Query query = em.createQuery(hqlString);
//		query.setParameter("1", new Date());
//		query.setParameter("2", String.valueOf(ProcessInstanceBOInfo.Status.REMOVE.ordinal()));
//		query.setParameter("3", piId);
//		query.executeUpdate();
        ProcessInstanceBOInfo processInstanceBOInfo = getProcessInstanceBOInfo(piId);
        processInstanceBOInfo.setModifyTime(new Date());
        processInstanceBOInfo.setStatus(String.valueOf(ProcessInstanceBOInfo.Status.REMOVE.ordinal()));
        processInstanceBOInfo.setProcessInstanceId(piId);
        em.persist(processInstanceBOInfo);
	}

	@SuppressWarnings("unchecked")
	public List<ProcessInstanceBOInfo> getAllNormalProcessInstanceBOInfo() {
		List<ProcessInstanceBOInfo> result = null;
		try {
	        Query query = em.createNamedQuery("AllNormalProcessInstanceBOInfoes");
	        result = query.getResultList();
		} catch(NoResultException exception) {
			logger.warn(exception.getLocalizedMessage());
		}
		return result;
	}


	public BigDecimal queryProcessInstanceIdByTaskId(long taskId) {
		String sqlString = "SELECT processInstanceId FROM task WHERE id=?";
		Query query = em.createNativeQuery(sqlString);
		query.setParameter(1, taskId);
		return (BigDecimal) query.getSingleResult();
	}
}

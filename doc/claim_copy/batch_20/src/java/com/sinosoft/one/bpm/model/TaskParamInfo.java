package com.sinosoft.one.bpm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class TaskParamInfo {
    private String id;
    private Long taskId;
    private String userId;
    private String businessId;
    private String paramData;
    private String processId;
    private Date recordTime;
    private Long processInstanceId;
    /**
     * 0任务信息，1流程信息
     */
    private String paramType;

    public TaskParamInfo(String processId, long processInstanceId, String businessId, String paramData) {
        this.processId = processId;
        this.processInstanceId = processInstanceId;
        this.businessId = businessId;
        this.paramData = paramData;
        this.paramType = "1";
        this.recordTime = new Date();
    }
    public TaskParamInfo(Long taskId,String userId,String businessId,String paramData,long processInstanceId,String processId){
        this.processId = processId;
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.userId = userId;
        this.businessId = businessId;
        this.paramData = paramData;
        this.paramType = "0";
        this.recordTime = new Date();
    }
    @Column(name = "paramType")
    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    @Column(name = "processInstanceId")
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Column(name = "processId")
    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    @Column(name = "record_time")
    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public TaskParamInfo(){}
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(name = "taskId")
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    @Column(name = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @Column(name = "businessId")
    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
    @Column(name = "paramData")
    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData;
    }
}

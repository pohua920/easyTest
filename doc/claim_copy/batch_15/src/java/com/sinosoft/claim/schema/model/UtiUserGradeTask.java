package com.sinosoft.claim.schema.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * POJO类機構員工崗位差異功能權限表
 */
@Entity
@Table(name="UTIUSERGRADETASK"
)
public class UtiUserGradeTask  implements java.io.Serializable {
    private static final long serialVersionUID = 1L;


   
 /** 属性id */
     private UtiUserGradeTaskId id;
   
 /** 属性權限任務表 */
     private UtiTask utiTask;
   
 /** 属性授权级别 */
     private String granTlevel;
   
 /** 属性授权值 */
     private String grantValue;
   
 /** 属性權限值 */
     private String value;
   
 /** 属性備注 */
     private String remark;
   
 /** 属性標志位 */
     private String flag;

	/**
	 * 类Utiusergradetask的默认构造方法
	 */
    public UtiUserGradeTask() {
    }

   
    /**
     * 属性id的getter方法
     */      @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="comcode", column=@Column(name="COMCODE") ), 
        @AttributeOverride(name="usercode", column=@Column(name="USERCODE") ), 
        @AttributeOverride(name="gradecode", column=@Column(name="GRADECODE") ), 
        @AttributeOverride(name="taskcode", column=@Column(name="TASKCODE") ) } )

    public UtiUserGradeTaskId getId() {
        return this.id;
    }
    /**
     * 属性id的setter方法
     */
    public void setId(UtiUserGradeTaskId id) {
        this.id = id;
    }
    /**
     * 属性權限任務表的getter方法
     */ 
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TASKCODE", nullable=false, insertable=false, updatable=false)

    public UtiTask getUtiTask() {
        return this.utiTask;
    }
    /**
     * 属性權限任務表的setter方法
     */
    public void setUtiTask(UtiTask utiTask) {
        this.utiTask = utiTask;
    }
    /**
     * 属性授权级别的getter方法
     */ 
    
    @Column(name="granTlevel")

    public String getgranTlevel() {
        return this.granTlevel;
    }
    /**
     * 属性授权级别的setter方法
     */
    public void setgranTlevel(String granTlevel) {
        this.granTlevel = granTlevel;
    }
    /**
     * 属性授权值的getter方法
     */ 
    
    @Column(name="GrantValue")

    public String getGrantValue() {
        return this.grantValue;
    }
    /**
     * 属性授权值的setter方法
     */
    public void setGrantValue(String grantValue) {
        this.grantValue = grantValue;
    }
    /**
     * 属性權限值的getter方法
     */ 
    
    @Column(name="VALUE")

    public String getValue() {
        return this.value;
    }
    /**
     * 属性權限值的setter方法
     */
    public void setValue(String value) {
        this.value = value;
    }
    /**
     * 属性備注的getter方法
     */ 
    
    @Column(name="REMARK")

    public String getRemark() {
        return this.remark;
    }
    /**
     * 属性備注的setter方法
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * 属性標志位的getter方法
     */ 
    
    @Column(name="FLAG")

    public String getFlag() {
        return this.flag;
    }
    /**
     * 属性標志位的setter方法
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }




}



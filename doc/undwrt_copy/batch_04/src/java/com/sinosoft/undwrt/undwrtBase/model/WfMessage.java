package com.sinosoft.undwrt.undwrtBase.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * 这是WfMessage_核保核赔流转留言表的数据传输对象基类<br>
 * 创建于 2005-01-04 16:32:03.593<br>
 * JToolpad(1.3.3) Vendor:zhouxianli1978@hotmail.com
 */
@Entity(name = "WFMESSAGE_UNDWRT")
@Table(name = "WFMESSAGE")
public class WfMessage implements Serializable{
	
	/** 屬性id. */
	private WfMessageId wfMessageId;
    
    /** 属性業務號碼. */
    private String businessNo = "";
    
    /** 属性留言內容. */
    private String context = "";
    
    /** 属性留言時間. */
    private String operateTime = "";
    
    /** 属性操作員代碼. */
    private String operatorCode = "";

    /** 属性操作員名稱. */
    private String operatorName = "";

    /** 属性標誌. */
    private String flag = "";

    /**
	 * 默认构造方法,构造一个默认的WfMessageDtoBase对象.
	 */
    public WfMessage(){
    }



    /**
	 * 设置属性业务号码.
	 * 
	 * @param businessNo
	 *            待设置的属性业务号码的值
	 */
    
    public void setBusinessNo(String businessNo){
        this.businessNo = StringUtils.rightTrim(businessNo);
    }

    /**
	 * 获取属性业务号码.
	 * 
	 * @return 属性业务号码的值
	 */
    @Column(name = "BUSINESSNO", length = 25)
    public String getBusinessNo(){
        return businessNo;
    }


    /**
	 * 设置属性留言内容.
	 * 
	 * @param context
	 *            待设置的属性留言内容的值
	 */
    public void setContext(String context){
        this.context = StringUtils.rightTrim(context);
    }

    /**
	 * 获取属性留言内容.
	 * 
	 * @return 属性留言内容的值
	 */
    @Column(name = "CONTEXT", length = 70)
    public String getContext(){
        return context;
    }

    /**
	 * 设置属性留言时间.
	 * 
	 * @param operateTime
	 *            the new 属性留言时间
	 */
    public void setOperateTime(String operateTime){
        this.operateTime = StringUtils.rightTrim(operateTime);
    }

    /**
	 * 获取属性留言时间.
	 * 
	 * @return 属性留言时间的值
	 */
    @Column(name = "OPERATETIME", length = 19)
    public String getOperateTime(){
        return operateTime;
    }

    /**
	 * 设置属性操作员代码.
	 * 
	 * @param operatorCode
	 *            待设置的属性操作员代码的值
	 */
    public void setOperatorCode(String operatorCode){
        this.operatorCode = StringUtils.rightTrim(operatorCode);
    }

    /**
	 * 获取属性操作员代码.
	 * 
	 * @return 属性操作员代码的值
	 */
    @Column(name = "OPERATORCODE", length = 10)
    public String getOperatorCode(){
        return operatorCode;
    }

    /**
	 * 设置属性操作员名称.
	 * 
	 * @param operatorName
	 *            the new 属性操作员名称
	 */
   public void setOperatorName(String operatorName){
       this.operatorName = StringUtils.rightTrim(operatorName);
   }

   /**
	 * 获取属性操作员名称.
	 * 
	 * @return 属性操作员代码的值
	 */
   @Column(name = "OPERATORNAME", length = 30)
   public String getOperatorName(){
       return operatorName;
    }


    /**
	 * 设置属性标志.
	 * 
	 * @param flag
	 *            待设置的属性标志的值
	 */
    public void setFlag(String flag){
        this.flag = StringUtils.rightTrim(flag);
    }

    /**
	 * 获取属性标志.
	 * 
	 * @return 属性标志的值
	 */
    @Column(name = "FLAG", length = 2)
	public String getFlag() {
		return flag;
	}
	
	/**
	 * 獲取屬性id.
	 * 
	 * @return 屬性id的值
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "messageId", column = @Column(name = "MESSAGEID", nullable = false, length = 25)),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "lineNo", column = @Column(name = "LINENO", nullable = false, precision = 22, scale = 0))		
			}
	)
	public WfMessageId getWfMessageId() {
		return wfMessageId;
	}

	/**
	 * 設置屬性id.
	 * 
	 * @param wfMessageId
	 *            待設置的id的值
	 */
	public void setWfMessageId(WfMessageId wfMessageId) {
		this.wfMessageId = wfMessageId;
	}
    
}

package com.sinosoft.undwrt.common.vo;

import java.io.Serializable;

import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;

/**
 * 这是WfLog_工作流日志表的数据传输对象类<br>
 * 创建于 2004-12-24 10:33:35.147<br>
 * JToolpad(1.2.14) Vendor:zhouxianli@sinosoft.com.cn
 * added by gengxiaobo 增加总保额和总保费字段。
 */
public class WfLogVo extends WfLog implements Serializable{
    
    /**
	 * 默认构造方法,构造一个默认的WfLogDto对象.
	 */
    public WfLogVo(){
    }
    
    /** 属性被保险人名称. */
    private String insuredName = "";
    
    /** 属性任务状态. */
    private String nodeStatusName = "";
    
    /** 属性任务状态. */
    private String flowStatusName = "";
    
    /** 审批意见. */
    private String handleText = "";
    
    /** 用户代码. */
    private String userCode = "";
    
    /** 报价单任务状态. */
    private String qtaNodeStatusName = "";
    
    /** 用于工作流状态查询字段 *. */
    //工作流起始时间
    private String startTime = "";
    //工作流终止时间
    /** 屬性The sinosoft end time. */
    private String endTime = "";
    //流程图展现的Title
    /** 屬性標題. */
    private String title = "";
    //增加画矩形的Y轴坐标
    /** 屬性The sinosoft pos y1. */
    private int posY1 = 0;
    
    /** 屬性The sinosoft pos y2. */
    private int posY2 = 0;
    //增加画线的坐标
    /** 屬性The sinosoft start pos x. */
    private int startPosX = 0;
    
    /** 屬性The sinosoft start pos y. */
    private int startPosY = 0;
    
    /** 屬性The sinosoft end pos x. */
    private int endPosX = 0;
    
    /** 屬性The sinosoft end pos y. */
    private int endPosY = 0;
    //节点背景色
    /** 屬性The sinosoft node color. */
    private String nodeColor = "";
    //超时状态
    /** 屬性The sinosoft over time. */
    private String overTime = "";
    
    /** 屬性The sinosoft license no. */
    private String licenseNo = null; //车牌号
    
    /** 屬性The sinosoft relate contract no. */
    private String relateContractNo = null; //关联预约协议号
    
    /** 屬性險種大類. */
    private String riskCategory = null; //险种大类代码
    
    /** 屬性The sinosoft identify type. */
    private String identifyType = null;
    
    /** 屬性The sinosoft identify number. */
    private String identifyNumber = null;
    
    /** 屬性The sinosoft reins status. */
    private String reinsStatus = null;
    
    /** 屬性The sinosoft policy no. */
    private String policyNo = null;
    
    /** 屬性立案號. */
    private String claimNo = null;
    
    /** 屬性機構名稱. */
    private String comName = null;
    
    /** 屬性The sinosoft next node no. */
    private int nextNodeNo = 0;
    
    /** 屬性The sinosoft next node name. */
    private String nextNodeName = null;
    
    //added by gengxiaobo begin 20080704 增加总保额和总保费字段。
    /** 属性总保险金额(折算为人民币总保额). */
    private double sumAmount = 0D;
    
    /** 属性总保险费(折算为人民币总保费). */
    private double sumPremium = 0D;    
    //added by gengxiaobo end 20080704 增加总保额和总保费字段。
    //属性审核通过级别的名称
    /** 屬性The sinosoft pass level name. */
    private String passLevelName = "";
    
    /** 屬性The sinosoft is i log. */
    private String isILog = "";
    
    /** 屬性The sinosoft message. */
    private String message = "";
    
    /** 屬性The sinosoft prior type. */
    private String priorType="";
    
    //add by yangfang begin 20110527 增加出单员代码和出单员名称字段
    /** 屬性The sinosoft single code. */
    private String singleCode = "";
    
    /** 屬性The sinosoft single member. */
    private String singleMember = ""; 
    //add by yangfang end 20110527 增加出单员代码和出单员名称字段
    
    /**
	 * Gets the 屬性The sinosoft prior type.
	 * 
	 * @return the 屬性The sinosoft prior type
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getPriorType()
	 */
    public String getPriorType() {
		return priorType;
	}

	/**
	 * Sets the 屬性The sinosoft prior type.
	 * 
	 * @param priorType
	 *            the new 屬性The sinosoft prior type
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setPriorType(java.lang.String)
	 */
	public void setPriorType(String priorType) {
		this.priorType = priorType;
	}

	/**
	 * Sets the 属性被保险人名称.
	 * 
	 * @param insuredName
	 *            the new 属性被保险人名称
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setInsuredName(java.lang.String)
	 */
	public void setInsuredName(String insuredName)
    {
        this.insuredName = StringUtils.rightTrim(insuredName);
    }
    
    /**
	 * Gets the 属性被保险人名称.
	 * 
	 * @return the 属性被保险人名称
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getInsuredName()
	 */
    public String getInsuredName()
    {
        return insuredName;
    }
    
    /**
	 * Sets the 属性任务状态.
	 * 
	 * @param nodeStatusName
	 *            the new 属性任务状态
	 */
    public void setNodeStatusName(String nodeStatusName)
    {
        this.nodeStatusName = StringUtils.rightTrim(nodeStatusName);
    }
    
    /**
	 * Gets the 属性任务状态.
	 * 
	 * @return the 属性任务状态
	 */
    public String getNodeStatusName()
    {
        String strNodeStatus = this.getNodeStatus();
        InternationalizationUtil internal = new InternationalizationUtil();
        if (strNodeStatus.equals("1"))
            nodeStatusName = internal.getText("undwrt.HebaoTaskDealQuery.waitDeal");
        if (strNodeStatus.equals("2"))
            nodeStatusName = internal.getText("undwrt.HebaoTaskDealQuery.playingDeal");
        if (strNodeStatus.equals("3"))
            nodeStatusName = internal.getText("undwrt.HebaoTaskShowQuery.alreadyDealNoFlow");
        if (strNodeStatus.equals("4"))
            nodeStatusName = internal.getText("undwrt.HebaoTaskDealQuery.alreadyDealFlow");
        if (strNodeStatus.equals("0"))
            nodeStatusName = internal.getText("undwrt.HebaoTaskDealQuery.alreadyFinish");
        if (strNodeStatus.equals("5"))
            nodeStatusName = internal.getText("undwrt.commonTraceInfo.requestRejected");
        return nodeStatusName;
    }
    
    /**
	 * Sets the 报价单任务状态.
	 * 
	 * @param qtaNodeStatusName
	 *            the new 报价单任务状态
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setQtaNodeStatusName(java.lang.String)
	 */
    public void setQtaNodeStatusName(String qtaNodeStatusName)
    {
        this.qtaNodeStatusName = StringUtils.rightTrim(qtaNodeStatusName);
    }
    
    /**
	 * Gets the 报价单任务状态.
	 * 
	 * @return the 报价单任务状态
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getQtaNodeStatusName()
	 */
    public String getQtaNodeStatusName()
    {
        String strNodeStatus = this.getNodeStatus();
        InternationalizationUtil internal = new InternationalizationUtil();
        if (strNodeStatus.equals("0"))
        	qtaNodeStatusName = internal.getText("undwrt.HebaoQueryQtaStats.tempSave");
        if (strNodeStatus.equals("1"))
        	qtaNodeStatusName = internal.getText("undwrt.HebaoQueryQtaStats.waitAudit");
        if (strNodeStatus.equals("2"))
        	qtaNodeStatusName = internal.getText("undwrt.HebaoQueryQtaStats.auditHitBack");
        if (strNodeStatus.equals("3"))
        	qtaNodeStatusName = internal.getText("undwrt.HebaoQueryQtaStats.auditPass");
        if (strNodeStatus.equals("4"))
        	qtaNodeStatusName = internal.getText("undwrt.HebaoQueryQtaStats.backAlter");
        if (strNodeStatus.equals("5"))
        	qtaNodeStatusName = internal.getText("undwrt.HebaoQueryQtaStats.alreadyPrint");
        if (strNodeStatus.equals("6"))
        	qtaNodeStatusName = internal.getText("undwrt.HebaoQueryQtaStats.policyConfirm");
        if (strNodeStatus.equals("7"))
        	qtaNodeStatusName = internal.getText("undwrt.HebaoQueryQtaStats.produceThrowPolicy");
        if (strNodeStatus.equals("8"))
        	qtaNodeStatusName = internal.getText("undwrt.HebaoQueryQtaStats.throwPolicyUndwortPass");
        if (strNodeStatus.equals("9"))
        	qtaNodeStatusName = internal.getText("undwrt.HebaoQueryQtaStats.producePolicy");
        return qtaNodeStatusName;
    }
    
    /**
	 * Sets the 属性任务状态.
	 * 
	 * @param flowStatusName
	 *            the new 属性任务状态
	 */
    public void setFlowStatusName(String flowStatusName)
    {
        this.flowStatusName = StringUtils.rightTrim(flowStatusName);
    }
    
    /**
	 * Gets the 属性任务状态.
	 * 
	 * @return the 属性任务状态
	 */
    public String getFlowStatusName()
    {
        String strFlowStatus = this.getFlowStatus();
        InternationalizationUtil internal = new InternationalizationUtil();
        if (strFlowStatus.equals("0"))
        	flowStatusName = internal.getText("undwrt.pages.undwrtDeal.normalFlow");
        if (strFlowStatus.equals("1"))
            flowStatusName = internal.getText("undwrt.pages.undwrtDeal.rollback");
        return flowStatusName;
    }
    
    /**
	 * Sets the 审批意见.
	 * 
	 * @param handleText
	 *            the new 审批意见
	 */
    public void setHandleText(String handleText)
    {
        this.handleText = StringUtils.rightTrim(handleText);
    }
    
    /**
	 * Gets the 审批意见.
	 * 
	 * @return the 审批意见
	 */
    public String getHandleText()
    {
        return handleText;
    }
    
    /**
	 * Sets the 用户代码.
	 * 
	 * @param userCode
	 *            the new 用户代码
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setUserCode(java.lang.String)
	 */
    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }
    
    /**
	 * Gets the 用户代码.
	 * 
	 * @return the 用户代码
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getUserCode()
	 */
    public String getUserCode()
    {
        return userCode;
    }
    
    /**
	 * Sets the 用于工作流状态查询字段 *.
	 * 
	 * @param startTime
	 *            the new 用于工作流状态查询字段 *
	 */
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    
    /**
	 * 獲取屬性the sinosoft star time.
	 * 
	 * @return 屬性the sinosoft star time的值
	 */
    public String getStarTime()
    {
        return startTime;
    }
    
    /**
	 * 設置屬性the sinosoft end time.
	 * 
	 * @param endTime
	 *            待設置的the sinosoft end time的值
	 */
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    
    /**
	 * 獲取屬性the sinosoft end time.
	 * 
	 * @return 屬性the sinosoft end time的值
	 */
    public String getEndTime()
    {
        return endTime;
    }
    
    /**
	 * 設置屬性標題.
	 * 
	 * @param title
	 *            待設置的標題的值
	 */
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    /**
	 * 獲取屬性標題.
	 * 
	 * @return 屬性標題的值
	 */
    public String getTitle()
    {
        return title;
    }
    
    /**
	 * 設置屬性the sinosoft pos y1.
	 * 
	 * @param posY1
	 *            待設置的the sinosoft pos y1的值
	 */
    public void setPosY1(int posY1)
    {
        this.posY1 = posY1;
    }
    
    /**
	 * 獲取屬性the sinosoft pos y1.
	 * 
	 * @return 屬性the sinosoft pos y1的值
	 */
    public int getPosY1 ()
    {
        return posY1;
    }
    
    /**
	 * 設置屬性the sinosoft pos y2.
	 * 
	 * @param posY2
	 *            待設置的the sinosoft pos y2的值
	 */
    public void setPosY2(int posY2)
    {
        this.posY2 = posY2;
    }
    
    /**
	 * 獲取屬性the sinosoft pos y2.
	 * 
	 * @return 屬性the sinosoft pos y2的值
	 */
    public int getPosY2()
    {
        return posY2;
    }
    
    /**
	 * 設置屬性the sinosoft start pos x.
	 * 
	 * @param startPosX
	 *            待設置的the sinosoft start pos x的值
	 */
    public void setStartPosX(int startPosX)
    {
        this.startPosX = startPosX;
    }
    
    /**
	 * 獲取屬性the sinosoft start pos x.
	 * 
	 * @return 屬性the sinosoft start pos x的值
	 */
    public int getStartPosX()
    {
        return startPosX;
    }
    
    /**
	 * 設置屬性the sinosoft start pos y.
	 * 
	 * @param startPosY
	 *            待設置的the sinosoft start pos y的值
	 */
    public void setStartPosY(int startPosY)
    {
        this.startPosY = startPosY;
    }
    
    /**
	 * 獲取屬性the sinosoft start pos y.
	 * 
	 * @return 屬性the sinosoft start pos y的值
	 */
    public int getStartPosY()
    {
        return startPosY;
    }
    
    /**
	 * 設置屬性the sinosoft end pos x.
	 * 
	 * @param endPosX
	 *            待設置的the sinosoft end pos x的值
	 */
    public void setEndPosX(int endPosX)
    {
        this.endPosX = endPosX;
    }
    
    /**
	 * 獲取屬性the sinosoft end pos x.
	 * 
	 * @return 屬性the sinosoft end pos x的值
	 */
    public int getEndPosX()
    {
        return endPosX;
    }
    
    /**
	 * 設置屬性the sinosoft end pos y.
	 * 
	 * @param endPosY
	 *            待設置的the sinosoft end pos y的值
	 */
    public void setEndPosY(int endPosY)
    {
        this.endPosY = endPosY;
    }
    
    /**
	 * 獲取屬性the sinosoft end pos y.
	 * 
	 * @return 屬性the sinosoft end pos y的值
	 */
    public int getEndPosY()
    {
        return endPosY;
    }
    
    /**
	 * 設置屬性the sinosoft node color.
	 * 
	 * @param nodeColor
	 *            待設置的the sinosoft node color的值
	 */
    public void setNodeColor(String nodeColor)
    {
        this.nodeColor = nodeColor;
    }
    
    /**
	 * 獲取屬性the sinosoft node color.
	 * 
	 * @return 屬性the sinosoft node color的值
	 */
    public String getNodeColor()
    {
        if(this.getNodeStatus().equals("1"))  //待处理
        {
            nodeColor = "#FFBBBB";
        }
        else if(this.getNodeStatus().equals("2"))  //正在处理
        {
            nodeColor = "#65B1B1";
        }
        else if(this.getNodeStatus().equals("3"))  //已处理未提交
        {
            nodeColor = "#b0ffb0";
        }
        else if(this.getNodeStatus().equals("4"))  //已提交
        {
            nodeColor = "#acc7ff";
        }
        else //已关闭
        {
            nodeColor = "#e4caff";
        }
        return nodeColor;
    }
    
    /**
	 * 設置屬性the sinosoft over time.
	 * 
	 * @param overTime
	 *            待設置的the sinosoft over time的值
	 */
    public void setOverTime(String overTime)
    {
        this.overTime = overTime;
    }
    
    /**
	 * 獲取屬性the sinosoft over time.
	 * 
	 * @return 屬性the sinosoft over time的值
	 */
    public String getOverTime()
    {
        return overTime;
    }
    
    /**
	 * Gets the 屬性The sinosoft license no.
	 * 
	 * @return the 屬性The sinosoft license no
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getLicenseNo()
	 */
    public String getLicenseNo()
    {
        return licenseNo;
    }
    
    /**
	 * Sets the 屬性The sinosoft license no.
	 * 
	 * @param licenseNo
	 *            the new 屬性The sinosoft license no
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setLicenseNo(java.lang.String)
	 */
    public void setLicenseNo(String licenseNo)
    {
        this.licenseNo = licenseNo;
    }
    
    /**
	 * Gets the 屬性The sinosoft relate contract no.
	 * 
	 * @return the 屬性The sinosoft relate contract no
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getRelateContractNo()
	 */
    public String getRelateContractNo()
    {
        return relateContractNo;
    }
    
    /**
	 * Sets the 屬性The sinosoft relate contract no.
	 * 
	 * @param relateContractNo
	 *            the new 屬性The sinosoft relate contract no
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setRelateContractNo(java.lang.String)
	 */
    public void setRelateContractNo(String relateContractNo)
    {
        this.relateContractNo = relateContractNo;
    }
    
    /**
	 * Gets the 屬性險種大類.
	 * 
	 * @return the 屬性險種大類
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getRiskCategory()
	 */
    public String getRiskCategory()
    {
        return riskCategory;
    }
    
    /**
	 * Sets the 屬性險種大類.
	 * 
	 * @param riskCategory
	 *            the new 屬性險種大類
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setRiskCategory(java.lang.String)
	 */
    public void setRiskCategory(String riskCategory)
    {
        this.riskCategory = riskCategory;
    }
    
    /**
	 * Gets the 屬性立案號.
	 * 
	 * @return the 屬性立案號
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getClaimNo()
	 */
    public String getClaimNo()
    {
        return claimNo;
    }
    
    /**
	 * Sets the 屬性立案號.
	 * 
	 * @param claimNo
	 *            the new 屬性立案號
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setClaimNo(java.lang.String)
	 */
    public void setClaimNo(String claimNo)
    {
        this.claimNo = claimNo;
    }
    
    /**
	 * Gets the 屬性The sinosoft identify number.
	 * 
	 * @return the 屬性The sinosoft identify number
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getIdentifyNumber()
	 */
    public String getIdentifyNumber()
    {
        return identifyNumber;
    }
    
    /**
	 * Sets the 屬性The sinosoft identify number.
	 * 
	 * @param identifyNumber
	 *            the new 屬性The sinosoft identify number
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setIdentifyNumber(java.lang.String)
	 */
    public void setIdentifyNumber(String identifyNumber)
    {
        this.identifyNumber = identifyNumber;
    }
    
    /**
	 * Gets the 屬性The sinosoft identify type.
	 * 
	 * @return the 屬性The sinosoft identify type
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getIdentifyType()
	 */
    public String getIdentifyType()
    {
        return identifyType;
    }
    
    /**
	 * Sets the 屬性The sinosoft identify type.
	 * 
	 * @param identifyType
	 *            the new 屬性The sinosoft identify type
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setIdentifyType(java.lang.String)
	 */
    public void setIdentifyType(String identifyType)
    {
        this.identifyType = identifyType;
    }
    
    /**
	 * Gets the 屬性The sinosoft policy no.
	 * 
	 * @return the 屬性The sinosoft policy no
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getPolicyNo()
	 */
    public String getPolicyNo()
    {
        return policyNo;
    }
    
    /**
	 * Sets the 屬性The sinosoft policy no.
	 * 
	 * @param policyNo
	 *            the new 屬性The sinosoft policy no
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setPolicyNo(java.lang.String)
	 */
    public void setPolicyNo(String policyNo)
    {
        this.policyNo = policyNo;
    }
    
    /**
	 * Gets the 屬性The sinosoft reins status.
	 * 
	 * @return the 屬性The sinosoft reins status
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getReinsStatus()
	 */
    public String getReinsStatus()
    {
        return reinsStatus;
    }
    
    /**
	 * Sets the 屬性The sinosoft reins status.
	 * 
	 * @param reinsStatus
	 *            the new 屬性The sinosoft reins status
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setReinsStatus(java.lang.String)
	 */
    public void setReinsStatus(String reinsStatus)
    {
        this.reinsStatus = reinsStatus;
    }
    
    /**
	 * Gets the 用于工作流状态查询字段 *.
	 * 
	 * @return the 用于工作流状态查询字段 *
	 */
    public String getStartTime()
    {
        return startTime;
    }
    
    /**
	 * 獲取屬性the sinosoft next node name.
	 * 
	 * @return 屬性the sinosoft next node name的值
	 */
    public String getNextNodeName()
    {
        return nextNodeName;
    }
    
    /**
	 * 設置屬性the sinosoft next node name.
	 * 
	 * @param nextNodeName
	 *            待設置的the sinosoft next node name的值
	 */
    public void setNextNodeName(String nextNodeName)
    {
        this.nextNodeName = nextNodeName;
    }
    
    /**
	 * 獲取屬性the sinosoft next node no.
	 * 
	 * @return 屬性the sinosoft next node no的值
	 */
    public int getNextNodeNo()
    {
        return nextNodeNo;
    }
    
    /**
	 * 設置屬性the sinosoft next node no.
	 * 
	 * @param nextNodeNo
	 *            待設置的the sinosoft next node no的值
	 */
    public void setNextNodeNo(int nextNodeNo)
    {
        this.nextNodeNo = nextNodeNo;
    }
    
    /**
	 * Gets the 屬性機構名稱.
	 * 
	 * @return the 屬性機構名稱
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getComName()
	 */
    public String getComName()
    {
        return comName;
    }
    
    /**
	 * Sets the 屬性機構名稱.
	 * 
	 * @param comName
	 *            the new 屬性機構名稱
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setComName(java.lang.String)
	 */
    public void setComName(String comName)
    {
        this.comName = comName;
    }

	/**
	 * Gets the 属性总保险金额(折算为人民币总保额).
	 * 
	 * @return the 属性总保险金额(折算为人民币总保额)
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getSumAmount()
	 */
	public double getSumAmount() {
		return sumAmount;
	}

	/**
	 * Sets the 属性总保险金额(折算为人民币总保额).
	 * 
	 * @param sumAmount
	 *            the new 属性总保险金额(折算为人民币总保额)
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setSumAmount(double)
	 */
	public void setSumAmount(double sumAmount) {
		this.sumAmount = sumAmount;
	}

	/**
	 * Gets the 属性总保险费(折算为人民币总保费).
	 * 
	 * @return the 属性总保险费(折算为人民币总保费)
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getSumPremium()
	 */
	public double getSumPremium() {
		return sumPremium;
	}

	/**
	 * Sets the 属性总保险费(折算为人民币总保费).
	 * 
	 * @param sumPremium
	 *            the new 属性总保险费(折算为人民币总保费)
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setSumPremium(double)
	 */
	public void setSumPremium(double sumPremium) {
		this.sumPremium = sumPremium;
	}

	/**
	 * 獲取屬性the sinosoft pass level name.
	 * 
	 * @return 屬性the sinosoft pass level name的值
	 */
	public String getPassLevelName() {
		return passLevelName;
	}

	/**
	 * 設置屬性the sinosoft pass level name.
	 * 
	 * @param passLevelName
	 *            待設置的the sinosoft pass level name的值
	 */
	public void setPassLevelName(String passLevelName) {
		this.passLevelName = passLevelName;
	}

	/**
	 * 獲取屬性the sinosoft checks if is i log.
	 * 
	 * @return 屬性the sinosoft checks if is i log的值
	 */
	public String getIsILog() {
		return isILog;
	}

	/**
	 * 設置屬性the sinosoft checks if is i log.
	 * 
	 * @param isILog
	 *            待設置的the sinosoft checks if is i log的值
	 */
	public void setIsILog(String isILog) {
		this.isILog = isILog;
	}

	/**
	 * 獲取屬性the sinosoft message.
	 * 
	 * @return 屬性the sinosoft message的值
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 設置屬性the sinosoft message.
	 * 
	 * @param message
	 *            待設置的the sinosoft message的值
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the 屬性The sinosoft single member.
	 * 
	 * @return the 屬性The sinosoft single member
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getSingleMember()
	 */
	public String getSingleMember() {
		return singleMember;
	}

	/**
	 * Sets the 屬性The sinosoft single member.
	 * 
	 * @param singleMember
	 *            the new 屬性The sinosoft single member
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setSingleMember(java.lang.String)
	 */
	public void setSingleMember(String singleMember) {
		this.singleMember = singleMember;
	}

	/**
	 * Gets the 屬性The sinosoft single code.
	 * 
	 * @return the 屬性The sinosoft single code
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#getSingleCode()
	 */
	public String getSingleCode() {
		return singleCode;
	}

	/**
	 * Sets the 屬性The sinosoft single code.
	 * 
	 * @param singleCode
	 *            the new 屬性The sinosoft single code
	 * @see com.sinosoft.undwrt.undwrtBase.model.WfLog#setSingleCode(java.lang.String)
	 */
	public void setSingleCode(String singleCode) {
		this.singleCode = singleCode;
	}


}

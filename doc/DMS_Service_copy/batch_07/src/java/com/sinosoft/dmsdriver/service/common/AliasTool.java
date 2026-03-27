/*     */ package com.sinosoft.dmsdriver.service.common;
/*     */ 
/*     */ 

import com.sinosoft.dmsdriver.domain.agentSYN.AgentSYNObj;
/*     */ import com.sinosoft.dmsdriver.domain.common.PageResPacket;
/*     */ import com.sinosoft.dmsdriver.domain.common.RequestHeadSchema;
/*     */ import com.sinosoft.dmsdriver.domain.common.ResponseHeadSchema;
/*     */ import com.sinosoft.dmsdriver.domain.getRisk.GetRiskReqBody;
/*     */ import com.sinosoft.dmsdriver.domain.getRisk.GetRiskReqPacket;
/*     */ import com.sinosoft.dmsdriver.domain.getRiskEngage.GetRiskEngageReqBody;
/*     */ import com.sinosoft.dmsdriver.domain.getRiskEngage.GetRiskEngageReqPacket;
/*     */ import com.sinosoft.dmsdriver.domain.productSYN.ClassObj;
/*     */ import com.sinosoft.dmsdriver.domain.productSYN.ClauseReportObj;
/*     */ import com.sinosoft.dmsdriver.domain.productSYN.FrameObj;
		  import com.sinosoft.dmsdriver.domain.productSYN.ProductSetObj;
/*     */ import com.sinosoft.dmsdriver.domain.productSYN.RationObj;
/*     */ import com.sinosoft.dmsdriver.domain.productSYN.RiskObj;
/*     */ import com.sinosoft.dmsdriver.domain.productSYN.SYNReqBody;
/*     */ import com.sinosoft.dmsdriver.domain.productSYN.SYNReqPacket;
/*     */ import com.sinosoft.dmsdriver.domain.productSYN.SYNResPacket;
/*     */ import com.sinosoft.dmsdriver.model.PrpDRCKRateLower;
/*     */ import com.sinosoft.dmsdriver.model.PrpDRationEngage;
/*     */ import com.sinosoft.dmsdriver.model.PrpDaccountInfo;
/*     */ import com.sinosoft.dmsdriver.model.PrpDagent;
/*     */ import com.sinosoft.dmsdriver.model.PrpDagentExt;
/*     */ import com.sinosoft.dmsdriver.model.PrpDagentExtId;
/*     */ import com.sinosoft.dmsdriver.model.PrpDagentId;
/*     */ import com.sinosoft.dmsdriver.model.PrpDarea;
/*     */ import com.sinosoft.dmsdriver.model.PrpDareaId;
/*     */ import com.sinosoft.dmsdriver.model.PrpDclass;
/*     */ import com.sinosoft.dmsdriver.model.PrpDclauseReport;
/*     */ import com.sinosoft.dmsdriver.model.PrpDclauseReportId;
/*     */ import com.sinosoft.dmsdriver.model.PrpDcontractManage;
/*     */ import com.sinosoft.dmsdriver.model.PrpDcontractManageId;
/*     */ import com.sinosoft.dmsdriver.model.PrpDframe;
/*     */ import com.sinosoft.dmsdriver.model.PrpDnewCodeRisk;
/*     */ import com.sinosoft.dmsdriver.model.PrpDration;
/*     */ import com.sinosoft.dmsdriver.model.PrpDrationClauseKind;
import com.sinosoft.dmsdriver.model.PrpDrationClauseKindId;
import com.sinosoft.dmsdriver.model.PrpDrationCondition;
import com.sinosoft.dmsdriver.model.PrpDrationConditionId;
/*     */ import com.sinosoft.dmsdriver.model.PrpDrationLimit;
/*     */ import com.sinosoft.dmsdriver.model.PrpDrationRelation;
/*     */ import com.sinosoft.dmsdriver.model.PrpDrationRelationId;
/*     */ import com.sinosoft.dmsdriver.model.PrpDrationShortrate;
/*     */ import com.sinosoft.dmsdriver.model.PrpDrisk;
/*     */ import com.sinosoft.dmsdriver.model.PrpDriskClause;
/*     */ import com.sinosoft.dmsdriver.model.PrpDriskClauseId;
/*     */ import com.sinosoft.dmsdriver.model.PrpDriskClauseKind;
/*     */ import com.sinosoft.dmsdriver.model.PrpDriskClauseKindId;
/*     */ import com.sinosoft.dmsdriver.model.PrpDriskClauseKindRelation;
/*     */ import com.sinosoft.dmsdriver.model.PrpDriskClauseKindRelationId;
/*     */ import com.sinosoft.dmsdriver.model.PrpDriskEngage;
/*     */ import com.sinosoft.dmsdriver.model.PrpDriskEngageId;
/*     */ import com.sinosoft.dmsdriver.model.PrpDriskItem;
/*     */ import com.sinosoft.dmsdriver.model.PrpDriskItemId;
/*     */ import com.sinosoft.dmsdriver.model.PrpDriskLimit;
/*     */ import com.sinosoft.dmsdriver.model.PrpDriskLimitId;
/*     */ import com.sinosoft.dmsdriver.model.PrpDriskShortRate;
/*     */ import com.sinosoft.dmsdriver.model.PrpDriskShortRateId;
import com.sinosoft.dmsdriver.model.PrpDset;
import com.sinosoft.dmsdriver.model.PrpDsetChannel;
import com.sinosoft.dmsdriver.model.PrpDsetChannelId;
import com.sinosoft.dmsdriver.model.PrpDsetRationrelation;
import com.sinosoft.dmsdriver.model.PrpDsetRationrelationId;
import com.sinosoft.dmsdriver.model.PrpDsetRenewal;
import com.sinosoft.dmsdriver.model.PrpDsetRenewalId;
/*     */ import com.sinosoft.dmsdriver.model.PrpDtaxAuthorities;
import com.sinosoft.dmsdriver.model.PrpYDDagent;
import com.thoughtworks.xstream.XStream;
/*     */ 
/*     */ public class AliasTool
/*     */ {
/*     */   public static void alias(XStream xs)
/*     */   {
/*  63 */     xs.alias("PageResPacket", PageResPacket.class);
/*  64 */     xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
/*  65 */     xs.alias("DictPage", DictPage.class);
/*     */ 
/*  67 */     xs.alias("RequestHeadSchema", RequestHeadSchema.class);
/*     */ 
/*  69 */     xs.alias("GetRiskEngageReqPacket", GetRiskEngageReqPacket.class);
/*  70 */     xs.alias("GetRiskEngageReqBody", GetRiskEngageReqBody.class);
/*  71 */     xs.alias("PrpDtaxAuthorities", PrpDtaxAuthorities.class);
/*  72 */     xs.alias("PrpDrisk", PrpDrisk.class);
/*  73 */     xs.alias("PrpDclass", PrpDclass.class);
/*  74 */     xs.alias("GetRiskReqPacket", GetRiskReqPacket.class);
/*  75 */     xs.alias("GetRiskReqBody", GetRiskReqBody.class);
/*     */ 
/*  77 */     xs.alias("ClassObj", ClassObj.class);
/*  78 */     xs.alias("FrameObj", FrameObj.class);
/*  79 */     xs.alias("RationObj", RationObj.class);
/*  80 */     xs.alias("RiskObj", RiskObj.class);
/*     */ 
/*  82 */     xs.alias("PrpDagent", PrpDagent.class);
              xs.alias("PrpYDDagent", PrpYDDagent.class);
/*  83 */     xs.alias("PrpDagentId", PrpDagentId.class);
/*  84 */     xs.alias("AgentSYNObj", AgentSYNObj.class);
/*  85 */     xs.alias("PrpDagentExt", PrpDagentExt.class);
/*  86 */     xs.alias("PrpDagentExtId", PrpDagentExtId.class);
/*  87 */     xs.alias("PrpDcontractManage", PrpDcontractManage.class);
/*  88 */     xs.alias("PrpDcontractManageId", PrpDcontractManageId.class);
/*     */ 
/*  90 */     xs.alias("SYNReqBody", SYNReqBody.class);
/*  91 */     xs.alias("SYNReqPacket", SYNReqPacket.class);
/*  92 */     xs.alias("SYNResPacket", SYNResPacket.class);
/*     */ 
/*  94 */     xs.alias("PrpDaccountInfo", PrpDaccountInfo.class);
/*  95 */     xs.alias("PrpDarea", PrpDarea.class);
/*  96 */     xs.alias("PrpDareaId", PrpDareaId.class);
/*  97 */     xs.alias("PrpDframe", PrpDframe.class);
/*  98 */     xs.alias("PrpDration", PrpDration.class);
/*  99 */     xs.alias("PrpDrationClauseKind", PrpDrationClauseKind.class);
/* 100 */     xs.alias("PrpDrationLimit", PrpDrationLimit.class);
/* 101 */     xs.alias("PrpDRationEngage", PrpDRationEngage.class);
/* 102 */     xs.alias("PrpDrationShortrate", PrpDrationShortrate.class);
/* 103 */     xs.alias("PrpDriskClauseId", PrpDriskClauseId.class);
/* 104 */     xs.alias("PrpDriskClause", PrpDriskClause.class);
/* 105 */     xs.alias("PrpDriskClauseKind", PrpDriskClauseKind.class);
/* 106 */     xs.alias("PrpDriskClauseKindId", PrpDriskClauseKindId.class);
/*     */ 
/* 108 */     xs.alias("PrpDriskShortRate", PrpDriskShortRate.class);
/* 109 */     xs.alias("PrpDriskShortRateId", PrpDriskShortRateId.class);
/*     */ 
/* 111 */     xs.alias("PrpDriskItem", PrpDriskItem.class);
/* 112 */     xs.alias("PrpDriskItemId", PrpDriskItemId.class);
/* 113 */     xs.alias("PrpDnewCodeRisk", PrpDnewCodeRisk.class);
/*     */ 
/* 115 */     xs.alias("PrpDriskLimit", PrpDriskLimit.class);
/* 116 */     xs.alias("PrpDriskLimitId", PrpDriskLimitId.class);
/*     */ 
/* 118 */     xs.alias("PrpDriskEngage", PrpDriskEngage.class);
/* 119 */     xs.alias("PrpDriskEngageId", PrpDriskEngageId.class);
/*     */ 
/* 121 */     xs.alias("PrpDriskClauseKindRelation", PrpDriskClauseKindRelation.class);
/* 122 */     xs.alias("PrpDriskClauseKindRelationId", PrpDriskClauseKindRelationId.class);
/* 123 */     xs.alias("ClauseReportObj", ClauseReportObj.class);
/* 124 */     xs.alias("PrpDclauseReport", PrpDclauseReport.class);
/* 125 */     xs.alias("PrpDclauseReportId", PrpDclauseReportId.class);
/*     */ 
/* 127 */     xs.alias("PrpDrationRelation", PrpDrationRelation.class);
/* 128 */     xs.alias("PrpDrationRelationId", PrpDrationRelationId.class);
/*     */ 
/* 130 */     xs.alias("PrpDRCKRateLower", PrpDRCKRateLower.class);
/*     */     xs.alias("PrpDrationCondition", PrpDrationCondition.class);
			  xs.alias("PrpDrationConditionId", PrpDrationConditionId.class);
/* 132 */     xs.alias("ProductSetObj", ProductSetObj.class);
/* 133 */     xs.alias("PrpDset", PrpDset.class);
/* 134 */     xs.alias("PrpDsetRationrelation", PrpDsetRationrelation.class);
/*     */     xs.alias("PrpDsetRationrelationId", PrpDsetRationrelationId.class);
/* 136 */     xs.alias("PrpDsetRenewal", PrpDsetRenewal.class);
/* 137 */     xs.alias("PrpDsetRenewalId", PrpDsetRenewalId.class);
/*     */     xs.alias("PrpDsetChannel", PrpDsetChannel.class);
/*     */     xs.alias("PrpDsetChannelId", PrpDsetChannelId.class);
              xs.alias("PrpDrationClauseKindId", PrpDrationClauseKindId.class);
/*     */   }
/*     */ }

/* Location:           C:\Users\ADMINI~1\AppData\Local\Temp\Rar$DR53.248\
 * Qualified Name:     com.sinosoft.dmsdriver.service.common.AliasTool
 * JD-Core Version:    0.6.0
 */
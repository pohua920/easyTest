package com.tlg.commons.util.api.soap.regist;
//
//import cn.com.sinosoft.webservice.cxf.CalculateResultVo;
//import cn.com.sinosoft.webservice.cxf.KindInfoVo;
//import cn.com.sinosoft.webservice.cxf.PolicyGenInfoVo;
//import cn.com.sinosoft.webservice.cxf.PolicyInfoResultVo;
//import cn.com.sinosoft.webservice.cxf.PolicyInfoVo;
//import cn.com.sinosoft.webservice.cxf.PremiumInfoVo;
//import cn.com.sinosoft.webservice.cxf.PrpCcommission;
//import cn.com.sinosoft.webservice.cxf.PrpCcommissionDetail;
//import cn.com.sinosoft.webservice.cxf.PrpCcommissionDetailId;
//import cn.com.sinosoft.webservice.cxf.PrpCcommissionId;
//import cn.com.sinosoft.webservice.cxf.PrpCfee;
//import cn.com.sinosoft.webservice.cxf.PrpCfeeId;
//import cn.com.sinosoft.webservice.cxf.PrpCinsured;
//import cn.com.sinosoft.webservice.cxf.PrpCinsuredArtif;
//import cn.com.sinosoft.webservice.cxf.PrpCinsuredArtifId;
//import cn.com.sinosoft.webservice.cxf.PrpCinsuredId;
//import cn.com.sinosoft.webservice.cxf.PrpCinsuredNature;
//import cn.com.sinosoft.webservice.cxf.PrpCinsuredNatureId;
//import cn.com.sinosoft.webservice.cxf.PrpCitemCar;
//import cn.com.sinosoft.webservice.cxf.PrpCitemCarExt;
//import cn.com.sinosoft.webservice.cxf.PrpCitemCarExtId;
//import cn.com.sinosoft.webservice.cxf.PrpCitemCarId;
//import cn.com.sinosoft.webservice.cxf.PrpCitemKind;
//import cn.com.sinosoft.webservice.cxf.PrpCitemKindId;
//import cn.com.sinosoft.webservice.cxf.PrpClimit;
//import cn.com.sinosoft.webservice.cxf.PrpClimitId;
//import cn.com.sinosoft.webservice.cxf.PrpCmain;
//import cn.com.sinosoft.webservice.cxf.PrpCplan;
//import cn.com.sinosoft.webservice.cxf.PrpCplanId;
//import cn.com.sinosoft.webservice.cxf.QueryInfoVo;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
 */
@XmlRegistry
public class ObjectFactory {
	private static final QName _QueryRequest_QNAME = new QName(
			"http://sinosoft.com.cn", "queryRequest");
	private static final QName _RenewalResponse_QNAME = new QName(
			"http://sinosoft.com.cn", "renewalResponse");
	private static final QName _QueryResponse_QNAME = new QName(
			"http://sinosoft.com.cn", "queryResponse");
	private static final QName _RenewalRequest_QNAME = new QName(
			"http://sinosoft.com.cn", "renewalRequest");
	private static final QName _CalculateRequest_QNAME = new QName(
			"http://sinosoft.com.cn", "calculateRequest");
	private static final QName _CalculateResponse_QNAME = new QName(
			"http://sinosoft.com.cn", "calculateResponse");

//	public PolicyInfoResultVo createPolicyInfoResultVo() {
//		return new PolicyInfoResultVo();
//	}
//
//	public QueryInfoVo createQueryInfoVo() {
//		return new QueryInfoVo();
//	}
//
//	public PolicyInfoVo createPolicyInfoVo() {
//		return new PolicyInfoVo();
//	}
//
//	public PolicyGenInfoVo createPolicyGenInfoVo() {
//		return new PolicyGenInfoVo();
//	}
//
//	public CalculateResultVo createCalculateResultVo() {
//		return new CalculateResultVo();
//	}
//
//	public PrpCitemCarId createPrpCitemCarId() {
//		return new PrpCitemCarId();
//	}
//
//	public PrpCitemKindId createPrpCitemKindId() {
//		return new PrpCitemKindId();
//	}
//
//	public PrpClimit createPrpClimit() {
//		return new PrpClimit();
//	}
//
//	public PrpCcommission createPrpCcommission() {
//		return new PrpCcommission();
//	}
//
//	public PrpCitemCarExt createPrpCitemCarExt() {
//		return new PrpCitemCarExt();
//	}
//
//	public PrpCinsuredArtif createPrpCinsuredArtif() {
//		return new PrpCinsuredArtif();
//	}
//
//	public PrpCinsuredId createPrpCinsuredId() {
//		return new PrpCinsuredId();
//	}
//
//	public PrpCfee createPrpCfee() {
//		return new PrpCfee();
//	}
//
//	public PrpCinsured createPrpCinsured() {
//		return new PrpCinsured();
//	}
//
//	public PrpCcommissionDetailId createPrpCcommissionDetailId() {
//		return new PrpCcommissionDetailId();
//	}
//
//	public PrpCcommissionDetail createPrpCcommissionDetail() {
//		return new PrpCcommissionDetail();
//	}
//
//	public PrpCfeeId createPrpCfeeId() {
//		return new PrpCfeeId();
//	}
//
//	public PrpCitemCar createPrpCitemCar() {
//		return new PrpCitemCar();
//	}
//
//	public PrpCmain createPrpCmain() {
//		return new PrpCmain();
//	}
//
//	public PrpCitemCarExtId createPrpCitemCarExtId() {
//		return new PrpCitemCarExtId();
//	}
//
//	public PrpCcommissionId createPrpCcommissionId() {
//		return new PrpCcommissionId();
//	}
//
//	public PrpCinsuredNatureId createPrpCinsuredNatureId() {
//		return new PrpCinsuredNatureId();
//	}
//
//	public PremiumInfoVo createPremiumInfoVo() {
//		return new PremiumInfoVo();
//	}
//
//	public PrpCplan createPrpCplan() {
//		return new PrpCplan();
//	}
//
//	public PrpCplanId createPrpCplanId() {
//		return new PrpCplanId();
//	}
//
//	public PrpCitemKind createPrpCitemKind() {
//		return new PrpCitemKind();
//	}
//
//	public PrpCinsuredNature createPrpCinsuredNature() {
//		return new PrpCinsuredNature();
//	}
//
//	public PrpCinsuredArtifId createPrpCinsuredArtifId() {
//		return new PrpCinsuredArtifId();
//	}
//
//	public PrpClimitId createPrpClimitId() {
//		return new PrpClimitId();
//	}
//
//	public KindInfoVo createKindInfoVo() {
//		return new KindInfoVo();
//	}
//
//	@XmlElementDecl(namespace = "http://sinosoft.com.cn", name = "queryRequest")
//	public JAXBElement<QueryInfoVo> createQueryRequest(QueryInfoVo value) {
//		return new JAXBElement(_QueryRequest_QNAME, QueryInfoVo.class,
//				(Class) null, value);
//	}
//
//	@XmlElementDecl(namespace = "http://sinosoft.com.cn", name = "renewalResponse")
//	public JAXBElement<PolicyInfoResultVo> createRenewalResponse(
//			PolicyInfoResultVo value) {
//		return new JAXBElement(_RenewalResponse_QNAME,
//				PolicyInfoResultVo.class, (Class) null, value);
//	}
//
//	@XmlElementDecl(namespace = "http://sinosoft.com.cn", name = "queryResponse")
//	public JAXBElement<PolicyInfoResultVo> createQueryResponse(
//			PolicyInfoResultVo value) {
//		return new JAXBElement(_QueryResponse_QNAME, PolicyInfoResultVo.class,
//				(Class) null, value);
//	}
//
//	@XmlElementDecl(namespace = "http://sinosoft.com.cn", name = "renewalRequest")
//	public JAXBElement<PolicyGenInfoVo> createRenewalRequest(
//			PolicyGenInfoVo value) {
//		return new JAXBElement(_RenewalRequest_QNAME, PolicyGenInfoVo.class,
//				(Class) null, value);
//	}
//
//	@XmlElementDecl(namespace = "http://sinosoft.com.cn", name = "calculateRequest")
//	public JAXBElement<PolicyInfoVo> createCalculateRequest(PolicyInfoVo value) {
//		return new JAXBElement(_CalculateRequest_QNAME, PolicyInfoVo.class,
//				(Class) null, value);
//	}
//
//	@XmlElementDecl(namespace = "http://sinosoft.com.cn", name = "calculateResponse")
//	public JAXBElement<CalculateResultVo> createCalculateResponse(
//			CalculateResultVo value) {
//		return new JAXBElement(_CalculateResponse_QNAME,
//				CalculateResultVo.class, (Class) null, value);
//	}
}
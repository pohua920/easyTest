D:\workspace\workSpaceBranch\claim\src\java\com\sinosoft\claim\schema\service\spring\PrpLplanKindServiceSpringImpl.java
196行
下面SQL去抓時間
任一條不再USER指定的區間 就會跳錯
mantis：CLM0223 ，處理人員：DP0713，需求單編號：新核心-旅平險案件審核異常 做過一次調整

select l.Compensateno,l.Policyno,m.Itemkindno,m.kindname,l.exchRate,
l.Currency,l.Riskcode,l.Kindcode,l.PayObjectSerialNo,ROUND(l.Sumrealpay*l.exchRate,0) Sumrealpay
,l.Exceptdeductiblepay Exceptdeductiblepay,c.Comcode,m.STARTDATE,m.ENDDATE,l.PERSONNO 
from BUSINESS.Prplpersonloss@PROD l ,BUSINESS.Prpcitemkind@PROD m,BUSINESS.Prplcompensate@PROD c 
 where  m.Kindcode=l.Kindcode and m.Policyno = l.Policyno and l.Compensateno= c.compensateNo and l.compensateNo='C185026GZL2500201'  
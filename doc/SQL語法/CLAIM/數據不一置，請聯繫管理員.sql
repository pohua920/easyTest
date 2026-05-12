--TA GA GZ 查是否在區間內 START
SELECT p.DAMAGESTARTDATE AS '事故發生',p.* FROM PRPLCLAIM p WHERE p.POLICYNO = '180623TA0000390'
上面=>2024-03-03

下面=>2023-10-05  2024-02-08

select '保險期間=>',m.STARTDATE,m.ENDDATE,l.Compensateno,l.Policyno,m.Itemkindno,l.exchRate,l.Currency,l.Riskcode,l.Kindcode,l.PayObjectSerialNo,
ROUND(l.Sumrealpay*l.exchRate,0) Sumrealpay,l.Exceptdeductiblepay Exceptdeductiblepay,c.Comcode,l.PERSONNO 
from BUSINESS.Prplpersonloss@PROD l join BUSINESS.Prplcompensate@PROD c on l.Compensateno= c.compensateNo  
left join BUSINESS.Prpcitemkind@PROD m on l.FAMILYNO = m.FAMILYNO 
and l.KINDCODE = m.KINDCODE  
and m.Policyno = l.Policyno  and l.ITEMKINDNO= m.ITEMKINDNO  where l.compensateNo='C180624TAL0500201' 
--TA GA GZ 查是否在區間內  END

--FLOWID 放入 WFFLOWMAIN  (補wfflowmain)
--RELATEFLOWID 找swflog
SELECT wf.FLOWID,wf.RELATEFLOWID,wf.* FROM BUSINESS.WFLOG wf WHERE wf.BUSINESSNO in ('C185026GZL2500201')
SELECT * FROM SWFLOG s WHERE FLOWID = 'LGA00040000026000002'

INSERT INTO BUSINESS.WFLOG
SELECT wf.* FROM BUSINESS.WFLOG@PROD wf WHERE wf.BUSINESSNO in ('C185026GZL2500201')
INSERT INTO BUSINESS.WFLOG
SELECT wf.* FROM BUSINESS.WFLOG@PROD wf WHERE wf.policyNo like '185025GZ2000001%' 

--補wfflowmain
INSERT INTO BUSINESS.WFFLOWMAIN
select * from BUSINESS.WFFLOWMAIN@PROD wfm WHERE wfm.FLOWID in ('4026041417033306420')
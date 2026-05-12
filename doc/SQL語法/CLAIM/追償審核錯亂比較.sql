     SELECT * FROM BUSINESS.SWFLOG s WHERE s.BUSINESSNO = 'R188824PBL0503301'
     SELECT * FROM BUSINESS.SWFLOG@PROD s WHERE s.BUSINESSNO = 'R188824PBL0503301'
 
	 SELECT * FROM BUSINESS.SWFPATHLOG s WHERE s.FLOWID ='LPB00000000025000110'
	 SELECT * FROM BUSINESS.SWFPATHLOG@PROD s WHERE s.FLOWID ='LPB00000000025000110'
	 
	 select * from BUSINESS.SWFCONFIG this_ where  processId = 'claim_audit' order by nodeNo ASC
	 select * from BUSINESS.SWFCONFIG@PROD this_ where  processId = 'claim_audit' order by nodeNo ASC
	 
	 SELECT s.* FROM BUSINESS.PrpLcompensate s WHERE s.compensateNo = 'R188824PBL0503301'
	 SELECT s.* FROM BUSINESS.PrpLcompensate@PROD s WHERE s.compensateNo = 'R188824PBL0503301'
	 
	 
	 
--¿òº|¸É¦^
SELECT * FROM PrpLcompensate s WHERE s.compensateNo = 'R188824PBL0503301'
select distinct(registno) from prplregistrpolicy where 1=1  and  claimNo='188824PBL05033' 

UPDATE BUSINESS.TASK x
	SET x.STATUS='Reserved',x.ACTUALOWNER_ID='ClaimsManager'
	WHERE x.ID=1644881;

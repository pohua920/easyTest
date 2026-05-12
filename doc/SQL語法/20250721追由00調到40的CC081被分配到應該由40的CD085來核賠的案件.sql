SELECT wflog.COMCODE,wflog.* FROM wflog WHERE  businessno = 'C180225TALA528401'

SELECT *
        FROM   (
        SELECT DISTINCT wflog.*
                FROM   wflog
                WHERE  (( wflog.nodeno <= 2
                          AND ( wflog.comcode IN (SELECT comcode
                                                  FROM   prpdcompany
                                                  START WITH comcode IN ('40')
                                                  CONNECT BY PRIOR
                                                  comcode = uppercomcode
                                                  AND PRIOR comcode != comcode
                                                            AND validstatus =
                                                                '1')
                                AND ( wflog.classcode IN ( 'C1' )
                                       OR wflog.riskcode IN (
                                          'GA', 'PA', 'TR', 'TA' )
                                    ) ) ))
                       AND businessno = 'C180225TALA528401'
                       AND wflog.logno <> 1
                       AND wflog.nodeno <> 1
                       AND ( wflog.businesstype = 'C'
                              OR wflog.businesstype = 'Y' )
                       AND nodestatus IN( '1', '2', '3', '5' )
                       AND flowintime >= '2025-06-21 00:00:00'
                       AND flowintime <= '2025-07-21 23:59:59'
                UNION
                SELECT DISTINCT wflog.*
                FROM   wflog
                WHERE  (( wflog.nodeno <= 2
                          AND ( wflog.comcode IN (SELECT comcode
                                                  FROM   prpdcompany
                                                  START WITH comcode = '40'
                                                  CONNECT BY PRIOR
                                                  comcode = uppercomcode
                                                  AND PRIOR comcode != comcode
                                                            AND validstatus =
                                                                '1')
                                AND ( wflog.classcode IN ( 'F' )
                                       OR wflog.riskcode IN ( 'F01', 'F02' ) ) )
                        ))
                       AND wflog.logno <> 1
                       AND wflog.nodeno <> 1
                       AND ( wflog.businesstype = 'C'
                              OR wflog.businesstype = 'Y' )
                       AND businessno = 'C180225TALA528401'
                       AND nodestatus IN( '1', '2', '3', '5' )
                       AND flowintime >= '2025-06-21 00:00:00'
                       AND flowintime <= '2025-07-21 23:59:59'
                UNION
                SELECT DISTINCT wflog.*
                FROM   wflog
                WHERE  (( wflog.nodeno <= 2
                          AND ( wflog.comcode IN (SELECT comcode
                                                  FROM   prpdcompany
                                                  START WITH comcode = '40'
                                                  CONNECT BY PRIOR
                                                  comcode = uppercomcode
                                                  AND PRIOR comcode != comcode
                                                            AND validstatus =
                                                                '1')
                                AND ( wflog.classcode IN ( 'E' )
                                       OR wflog.riskcode IN (
                                          'BP', 'CA', 'CP', 'EA',
                                          'EE', 'CE', 'MB' ) ) ) ))
                       AND wflog.logno <> 1
                       AND wflog.nodeno <> 1
                       AND ( wflog.businesstype = 'C'
                              OR wflog.businesstype = 'Y' )
                       AND businessno = 'C180225TALA528401'
                       AND nodestatus IN( '1', '2', '3', '5' )
                       AND flowintime >= '2025-06-21 00:00:00'
                       AND flowintime <= '2025-07-21 23:59:59'
                UNION
                SELECT DISTINCT wflog.*
                FROM   wflog
                WHERE  (( wflog.nodeno <= 2
                          AND ( wflog.comcode IN (SELECT comcode
                                                  FROM   prpdcompany
                                                  START WITH comcode = '40'
                                                  CONNECT BY PRIOR
                                                  comcode = uppercomcode
                                                  AND PRIOR comcode != comcode
                                                            AND validstatus =
                                                                '1')
                                AND ( wflog.classcode IN ( 'C1' )
                                       OR wflog.riskcode IN ( 'HG', 'HP' ) ) ) )
                       )
                       AND wflog.logno <> 1
                       AND wflog.nodeno <> 1
                       AND ( wflog.businesstype = 'C'
                              OR wflog.businesstype = 'Y' )
                       AND businessno = 'C180225TALA528401'
                       AND nodestatus IN( '1', '2', '3', '5' )
                       AND flowintime >= '2025-06-21 00:00:00'
                       AND flowintime <= '2025-07-21 23:59:59'
                UNION
                SELECT DISTINCT wflog.*
                FROM   wflog
                WHERE  (( wflog.nodeno <= 2
                          AND ( wflog.comcode IN (SELECT comcode
                                                  FROM   prpdcompany
                                                  START WITH comcode = '40'
                                                  CONNECT BY PRIOR
                                                  comcode = uppercomcode
                                                  AND PRIOR comcode != comcode
                                                            AND validstatus =
                                                                '1')
                                AND ( wflog.classcode IN ( 'C' )
                                       OR wflog.riskcode IN (
                                          'AB', 'AE', 'AR', 'BB',
                                          'BL', 'BN', 'BR', 'CB',
                                          'CC', 'CN', 'DI', 'DO',
                                          'DS', 'EM', 'FC', 'FD',
                                          'GC', 'GF', 'GS', 'LF',
                                          'MF', 'MN', 'MP', 'PB',
                                          'PC', 'PM', 'PR', 'SB',
                                          'SC', 'SP', 'ST', 'TC',
                                          'TD', 'TL', 'TP' ) ) ) ))
                       AND wflog.logno <> 1
                       AND wflog.nodeno <> 1
                       AND ( wflog.businesstype = 'C'
                              OR wflog.businesstype = 'Y' )
                       AND businessno = 'C180225TALA528401'
                       AND nodestatus IN( '1', '2', '3', '5' )
                       AND flowintime >= '2025-06-21 00:00:00'
                       AND flowintime <= '2025-07-21 23:59:59'
                UNION
                SELECT DISTINCT wflog.*
                FROM   wflog
                WHERE  (( wflog.nodeno <= 2
                          AND ( wflog.comcode IN (SELECT comcode
                                                  FROM   prpdcompany
                                                  START WITH comcode = '40'
                                                  CONNECT BY PRIOR
                                                  comcode = uppercomcode
                                                  AND PRIOR comcode != comcode
                                                            AND validstatus =
                                                                '1')
                                AND ( wflog.classcode IN ( 'C' )
                                       OR wflog.riskcode IN ( 'TE' ) ) ) ))
                       AND wflog.logno <> 1
                       AND wflog.nodeno <> 1
                       AND ( wflog.businesstype = 'C'
                              OR wflog.businesstype = 'Y' )
                       AND businessno = 'C180225TALA528401'
                       AND nodestatus IN( '1', '2', '3', '5' )
                       AND flowintime >= '2025-06-21 00:00:00'
                       AND flowintime <= '2025-07-21 23:59:59')
        ORDER  BY flowintime DESC
        
  
SELECT MAX(DISTINCT(NODENO)) AS NODENO FROM UTIUWLEVEL WHERE USERCODE = 'CC081'--測試
SELECT MAX(DISTINCT(NODENO)) AS NODENO FROM UTIUWLEVEL WHERE USERCODE = 'CD058'--測試

  SELECT *  
  from BUSINESS.UTIUWLEVEL@PROD this_ where 
 USERCODE = 'BJ037'
  
  List<UtiUwLevel> utiUwLevelList = this.getUtiUwLevelService().findByConditions(conditions);
  SELECT *  
  from BUSINESS.UTIUWLEVEL this_ where 
  UWTYPE = 'C' 
  AND VALIDSTATUS  = '1' 
  AND USERCODE = 'CD091'
  
  SELECT * FROM UTIUWLEVEL u WHERE USERCODE = 'CD091'
  
  SELECT * 
  from PRPLCOMPENSATE this_ where  claimno='null' and underwriteflag in ('1','3')
  
  
  select this_.FLOWID as FLOWID394_0_, this_.LINENO as LINENO394_0_, this_.LOGNO as LOGNO394_0_, this_.BUSINESSNO as BUSINESSNO394_0_, this_.CLAIMNO as CLAIMNO394_0_, this_.FLAG as FLAG394_0_, this_.HANDLETEXT as HANDLETEXT394_0_, this_.NOTION as NOTION394_0_ 
  from UWNOTION this_ where  claimNo=(
  	select ClaimNo from PrpLcompensate where compensateNo='C180225TALA528401') order by businessno,logno,lineno
  	
  	
  	select p.HANDLERCODE,p.HANDLER1CODE,p.* from PrpLcompensate p where p.compensateNo='C180225TALA528401'
  	
  	SELECT p.HANDLERCODE,p.HANDLER1CODE,p.INPUTDATE,p.* FROM BUSINESS.PRPLCLAIM p WHERE p.handlercode = 'CC081'
  	
  	*****
  	prpdRiskConfig = prpDriskConfigService.findByPrimaryKey(prpDcompany.getComCode(), "0000", "FIRST_UNDWRT_FLAG");
  	select * from PRPDRISKCONFIG prpdriskco0_ where prpdriskco0_.COMCODE='00' and prpdriskco0_.CONFIGCODE='FIRST_UNDWRT_FLAG' and prpdriskco0_.RISKCODE='0000'
  	if (prpdRiskConfig == null) {
		throw new Exception("請在prpdriskconfig中維護該機構的二級機構是否含有核賠初審崗配置項，請與管理員聯系！");
	}
	*****
	
	
	
	
  	
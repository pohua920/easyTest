------PET SQL  ccicinsDataDource BUSINESS
--classCode
SELECT * FROM prpdclass WHERE validind='1'
--riskCode
select * FROM prpdrisk where classcode='T9' and validind='1' 

--MENU FROM UTIMENU
SELECT * FROM UTIMENU where systemCode='prpins'  AND validstatus='1'  ORDER BY DisplayNo 

--ACCOUNT
SELECT * FROM PRPDUSER WHERE USERCODE LIKE '%0713%'

SELECT * FROM INTFPRPJPAYREFREC WHERE DRAWOUTDATE IS NULL AND HANDLER1CODE LIKE 'DP07%'

with TA AS (
	select xa.certino FROM INTFPRPJPAYREFREC xa 
	LEFT OUTER JOIN INTFPRPJPAYREFKIND xb on xa.certitype=xb.certitype and xa.certino =xb.certino 
	where xa.TTOPDATE IS NOT NULL  
     AND xa.CERTITYPE='E'
     AND xa.RISKCODE in ('A01','B01') 
     AND xa.JFEEFLAG = '1' AND SUBSTR(xa.CERTINO, 7, 1) NOT IN ('B', 'F', 'H') 
	 AND xb.kindfee > 0 AND xa.realpayrefflag <> 1
	 AND ( (0=1 AND to_char(xa.DRAWOUTDATE,'yyyymmdd') = '20200527') OR (1=1 AND xa.DRAWOUTDATE IS NULL) ) 
	group by xa.certino
)
SELECT a.rowid,
	a.CERTITYPE AS INFC01, /*業務類型(PE)*/
       a.CERTINO AS INFC02, /*業務單號(要保單號)*/
      -- a.CERTINO||'_X' AS INFC02, -- * for test
       a.SERIALNO AS INFC03, /*交費計畫序號*/
       a.PAYREFREASON AS INFC04, /*收費原因*/
       a.PAYREFTIMES AS INFC05, /*收費次數*/
       a.OWNERNAME AS INFC06, /*收付對象*/
       a.PLANFEE AS INFC07, /*收付金額*/
       ' ' AS INFC08, /*收付單號*/
       0 AS INFC09, /*收付日期*/
       0 AS INFC10, /*本位幣金額*/
       0 AS INFC11, /*匯率*/
       ' ' AS INFC12, /*本位幣金額*/
       0 AS INFC13, /*回寫日期*/
       ' ' AS INFC14, /*回寫錯誤註記*/
       ' ' AS INFCZZ,
       a.CERTITYPE AS "_CERTITYPE",  
       a.PLANFEE AS "_PLANFEE",
       a.JFEEFLAG AS "_JFEEFLAG",
       a.PAYREFNO AS "_PAYREFNO",
       to_number(to_char(a.UNDERWRITEDATE,'YYYYMMDD')) AS "_UNDERWRITEDATE" 
FROM INTFPRPJPAYREFREC  a
WHERE a.TTOPDATE IS NOT NULL  
     AND a.CERTITYPE IN ('P', 'E')  
     AND a.RISKCODE in ('A01','B01') /* 車 */ 
     AND ( a.JFEEFLAG='0' OR  /* 非收費出單 */
     		(a.JFEEFLAG='1' AND a.CERTITYPE = 'P') OR /* 收費出單保單 全撈 */
     		(a.JFEEFLAG='1' AND a.CERTITYPE = 'E' AND  /* 收費出單批單 */
     			(
     				a.certino not in (select certino from TA)  /* 文批 及 金批(全為批減) */
     				or 
     				exists( select 1 from prpphead ph where ph.ENDORSENO=a.certino and ph.endorType='130') /* 佣金批改 */
     			) 
     		)
     			
     	) 
     AND ( (0=1 AND to_char(a.DRAWOUTDATE,'yyyymmdd') = '20200527') OR (1=1 AND a.DRAWOUTDATE IS NULL) ) 
     
     
     
     
SELECT * FROM INTFPRPJPAYREFREC WHERE POLICYNO = '185219C1000004'


SELECT a.* FROM INTFPRPJPAYREFREC a WHERE LENGTH(a.POLICYNO)>14  OR LENGTH(a.CERTINO) >= 14

--SELECT * FROM INTFFORVISA WHERE LENGTH(PRINTNO)>13 AND TTOPDATE is NULL

SELECT * FROM INTFPRPJPAYREFREC WHERE rowid='AAAaBlAAHAAAV3VAAK'


UserCode = 'dp0713' AND (ComCode = '00')  AND (GradeCode IN ('121','950')) 
UserCode = 'DP0713' AND (ComCode = '00')  AND (GradeCode IN ('121','950')) 
SELECT * FROM PRPDUSER WHERE USERCODE LIKE '%0713%'
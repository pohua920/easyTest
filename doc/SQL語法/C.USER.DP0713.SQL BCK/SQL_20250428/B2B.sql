--## B2B2C trip

--
SELECT CL.GROUP_NO,CG.GROUP_NAME,CL.CKLIST_NO,CL.CKLIST_NAME,CL.GITEM_NO, CL.CKLIST_ORDER  FROM AGNBS_PB_CKLIST CL LEFT JOIN AGNBS_PB_CKLIST_GP CG ON CL.GROUP_NO = CG.GROUP_NO  WHERE CL.SDTYPE_NO ='01' AND CL.VALID_FALG = '1' AND CL.VALID_DATE < =getdate() AND CL.INVALID_DATE > =getdate()  ORDER BY CG.GROUP_ORDER, CL.CKLIST_ORDER

--MS SQL Server TLGAGNBS
SELECT RISKCODE, KINDCODE, KINDNAME FROM TLGAGNBS.AGNBS_PB_ADDITION_TERM  WHERE CALC_TYPE = '3' AND VALID_FALG = '1' AND VALID_DATE < =getdate() AND INVALID_DATE > =getdate()  ORDER BY ORDER_ID 


--B2B0085 UI (B2B) ORACLE-ccicinsDataDource-BUSINESS
-- B2B0119 
Select * from BUSINESS.FOOD_INSURED_OBJECT
Select OID , CNAME , DESP from BUSINESS.FOOD_INSURED_OBJECT

Select OID , CNAME from BUSINESS.FOOD_SALES_AMOUNT
Select * from BUSINESS.FOOD_SALES_AMOUNT

SELECT ENAME , CNAME, AOP, AOA, AOAF, AOAAOAF, AGG, PREDEDUCT,DCREATE,ICREATE from BUSINESS.FOOD_INSURED_CASE

select a.FEE,a.* from BUSINESS.FOOD_INSURED_FEE a

select FEE from FOOD_INSURED_FEE where OBJECT_OID= '2' And CASE_ENAME = 'C'
select FEE from FOOD_INSURED_FEE where OBJECT_OID= '2'  And CASE_ENAME = 'B'  And AMOUNT_OID = '2' 

SELECT * FROM FIR_BUSSINESS_PRINT_TMP WHERE STARTDATE > sysdate -1

SELECT * FROM FIR_BUSSINESS_PRINT_INSURED WHERE CREATE_DATE > sysdate -1

select CODECODE,CODEcNAME from BUSINESS.PrpDnewCode where codetype='PostAddress' and validstatus='1'

Select * from FIR_BUSSINESS_PRINT_TMP WHERE OID > 1110


Select ENAME , CNAME, AOP, AOA, AOAF, AOAAOAF, AGG, PREDEDUCT from BUSINESS.FOOD_INSURED_CASE

SELECT "OID", OID_FIR_PREMCALC_TMP, INSURED_NAME, INSURED_ID, INSURED_PRINCIPAL, INSURED_PRINCIPAL_ID, INSURED_ZIPCODE, INSURED_ZIPNAME, INSURED_ADDRESS, INSURED_PHONENUMBER, INSURED_TYPE, CREATE_DATE
FROM BUSINESS.FIR_BUSSINESS_PRINT_INSURED;

--INSERT INTO FIR_BUSSINESS_PRINT_INSURED

--INSERT INTO FIR_BUSSINESS_PRINT_TMP(OID,FIR_PREMCALC_TMP_AMT_OID,FIR_PREMCALC_TMP_FEE_OID,CREATE_DATE,USERPRINTNO)
VALUES(0,100,101,TO_DATE('12/01/2020', 'MM/DD/YYYY'),'DP0713FB2009001')

--INSERT INTO FIR_BUSSINESS_PRINT_INSURED(OID,OID_FIR_BUSSINESS_PRINT_TMP,INSURED_TYPE,CREATE_DATE)
VALUES(0,0,1,TO_DATE('12/01/2020', 'MM/DD/YYYY'))

--INSERT INTO FIR_BUSSINESS_PRINT_INSURED(OID,OID_FIR_BUSSINESS_PRINT_TMP,INSURED_TYPE,CREATE_DATE)
VALUES(0,0,2,TO_DATE('12/01/2020', 'MM/DD/YYYY'))

SELECT * FROM FIR_BUSSINESS_PRINT_TMP ORDER BY "OID" DESC

SELECT * FROM FIR_BUSSINESS_PRINT_INSURED ORDER BY "OID" DESC

select CODECODE,CODEcNAME from PrpDnewCode where codetype='PostAddress' and validstatus='1'

--UPDATE FIR_BUSSINESS_PRINT_INSURED SET INSURED_NAME = 'TEST0_0_1' 
WHERE OID = 0 AND OID_FIR_BUSSINESS_PRINT_TMP = 0 AND INSURED_TYPE = 1

--UPDATE FIR_BUSSINESS_PRINT_INSURED SET INSURED_NAME = 'TEST0_0_2' 
WHERE OID = 0 AND OID_FIR_BUSSINESS_PRINT_TMP = 0 AND INSURED_TYPE = 2

DELETE FIR_BUSSINESS_PRINT_INSURED WHERE INSURED_NAME LIKE '%TEST%'

SELECT FIR_BUSS_PRINT_TMP_SEQ.nextval FROM DUAL;

SELECT MAX(USERPRINTNO) USERPRINTNO FROM FIR_BUSSINESS_PRINT_TMP WHERE USERPRINTNO LIKE '%DP0713FB2008%'

SELECT USERPRINTNO FROM FIR_BUSSINESS_PRINT_TMP WHERE USERPRINTNO LIKE '%DP0713FB202009%'

select * from prpdnewcode where codetype= 'ChannelType'

select * from CCICDMS.PRPDNEWCODERISK where codetype= 'ChannelType' and riskcode = 'EM'

select *  from CCICDMS.PRPDNEWCODE where codetype='TypeOfInsuranceEL'

select *  from CCICDMS.PRPDNEWCODE pc JOIN CCICDMS.prpdnewcoderisk pcr 
ON pc.CODETYPE = pcr.CODETYPE AND pc.CODECODE = pcr.CODECODE AND pcr.RISKCODE = 'EM'
where pc.codetype='EMProjectCategory'


    select
        *
    from
        prpdnewcode prpdnewcod0_,
        prpdnewcoderisk prpdnewcod1_ 
    where
        (
            prpdnewcod1_.riskcode in (
                'PUB' , 'EM' , 'E'
            )
        ) 
        and prpdnewcod1_.codetype='EMGeneralCategory' 
        and prpdnewcod0_.codecode=prpdnewcod1_.codecode 
        and prpdnewcod0_.codetype=prpdnewcod1_.codetype 
        and (
            prpdnewcod0_.codecode like '%%' 
            or prpdnewcod0_.codecname like '%%'
        ) 
        and (
            prpdnewcod0_.codecname like '%' 
            or prpdnewcod0_.codeename like '%'
        ) 
        and prpdnewcod0_.validstatus=1

select *  from CCICDMS.prpdnewcoderisk where riskCode='EM' AND codetype='ChannelType'
select *  from CCICDMS.prpdnewcoderisk where riskCode='EL' AND codetype='EMGeneralCategory'

SELECT * FROM PRPCMAINLIAB p

select
        prpdnewcod1_.codetype,prpdnewcod0_.codecode,prpdnewcod1_.codecode
    from
        CCICDMS.prpdnewcode prpdnewcod0_,
        CCICDMS.prpdnewcoderisk prpdnewcod1_ 
    where
         prpdnewcod1_.codetype='EMProjectCategory' 
        --and prpdnewcod0_.codecode=prpdnewcod1_.codecode 
        --and prpdnewcod0_.codetype=prpdnewcod1_.codetype 
        and prpdnewcod0_.validstatus=1
        
select *  from CCICDMS.PRPDNEWCODE where codetype='TypeOfInsuranceEL' and VALIDSTATUS='1'
        
  select * from CCICDMS.PRPDNEWCODE  where CODETYPE='EMProjectCategory'
  
  
  
select
        prpdnewcod0_.codecode,prpdnewcod1_.codecode, prpdnewcod0_.codetype,prpdnewcod1_.codetype 
    from
        CCICDMS.prpdnewcode prpdnewcod0_,
        CCICDMS.prpdnewcoderisk prpdnewcod1_ 
    where
        (
            prpdnewcod1_.riskcode in (
                --'PUB' , 
                'EL' , 'E'
            )
        ) 
        AND
        prpdnewcod1_.codetype='EMProjectCategory' 
        and prpdnewcod0_.codetype='EMProjectCategory' 
        --and prpdnewcod0_.codecode=prpdnewcod1_.codecode 
        and prpdnewcod0_.codetype=prpdnewcod1_.codetype 
        and (
            prpdnewcod0_.codecode like '%%' 
            or prpdnewcod0_.codecname like '%%'
        ) 
        and (
            prpdnewcod0_.codecname like '%' 
            or prpdnewcod0_.codeename like '%'
        ) 
        and prpdnewcod0_.validstatus=1

        
        select *  from CCICDMS.PRPDNEWCODE where codetype='PolicyKindEL'
        
SELECT * FROM BUSINESS.PRPCMAIN WHERE RISKCODE = 'MC' AND POLICYNO = '180021CF500004'
        SELECT WORKSTATUS FROM BUSINESS.PRPTMAIN WHERE PROPOSALNO in ('9EL202002000005')
        update BUSINESS.PRPTMAIN set WORKSTATUS='04' WHERE PROPOSALNO in ('9EL202002000005');

       
       SELECT REPORTNO,a.* FROM PRPDCLAUSEREPORT a WHERE a.CLAUSECODE='EL' AND a.TCOL1 is NULL;


select WAC56,WAC57,WAC58,WAC59,wac60 from ecwac where wac02='187020EM0000002' and WAC04='EM'

SELECT WAC00, WAC01, WAC02, WAC03, WAC04, WAC05, WAC06, WAC07, WAC08, WAC09, WAC10, WAC11, WAC12, WAC13, WAC14, WAC15, WAC16, WAC17, WAC18, WAC19, WAC20, WAC21, WAC22, WAC23, WAC24, WAC25, WAC26, WAC27, WAC28, WAC29, WAC30, WAC31, WAC32, WAC33, WAC34, WAC35, WAC36, WAC37, WAC38, WAC39, WAC40, WAC41, WAC42, WAC43, WAC44, WAC45, WAC46, WAC47, WAC48, WAC49, WAC50, WAC51, WAC52, WAC53, WAC54, WAC55, WAC56, WAC57, WAC58, WAC59, WAC60, WAC61, WAC62, WAC63, WAC64, WAC65, WAC66
FROM TSTORACLE.ZCAC;

--D:\workspaceB2b2c\workspaceB2b2cUAT\CtbcTA_b2b2c\src\main\java\tw\com\tlg\ctbc\b2b2c\schedule\DebitAndTransPolicyToCoreSchedule.java
--B2B2C PET SQL SERVER TLGCTBCUAT
select * from CTBC_PET_POLICY cpp order by cpp.id DESC

SELECT WAC56,WAC57,WAC58,WAC59,DRAWOUTDATE  FROM ECWAc WHERE WAc02='187020EM0000003' and WAC04='EM'


SELECT ODRNO, SAVESEQ, SALSRC, CUSID, INSTYP, PLCYNO, ENDOSNO, SUBNO, GDSSEQNO, HLDRID, HLDRIDVF, HLDRNAME, TPAREA, TPCTY, GRPNME, TPFMDT, TPFMTM, ZIPCDE, CMMADDR, CMMTEL, EMGYTEL, POSTZIPCDE, POSTADDR, EMAIL, PRMAMT, INSDCNT, DFFAMT, CRTUSR, CRTDTTM, EDTUSR, EDTDTTM, DELUSR, DELDTTM, PRTDTTM, PRTCNT, TICKCDE, SALORGZN, SALPSNID, SALPSNNAME, INTRDCRID, BRKSALSRC, DATASRC, CFMFLG, CFMUSR, CFMDTTM, TPLDER, TPGWFGTNUM, TPRTNFGTNUM, CONTRACTID, DERATE, TRVLID, MEMO1, ENDOSFLAG, PREENDOSNO, EDTUSR2, EDTDTTM2, INSTALMENT, FROMCHINA, HLTHDBLINS, ENDOSNMEMO, TRQUOTEID, TRQUOTEVER, UPAGE15, TRASTAT, PAYMNTTYPE, CRDTCRDNO, CRDTCRDYM, AUTHFLG, SNDADDRCTL, USRPAYCTL, ODRPNTCTL, DOCCFMUSR, DOCCFMDT, CCV2, AUTHMSG, AUTHCODE, AUTHDTTM, CAPFLAG, UDWDPT, PROMOTERID, SVRPSNID, HLDRBTHDY, BROKERCONFIG1, SALPSNLICENSE, PRTCNT2
FROM TLGTRIPBST.dbo.TRIPBS_TTRNS;

--MSSQL/TLGTRIPBST
select this_.INSTYP as INSTYP13_0_, this_.DELUSR as DELUSR13_0_ 
from TLGTRIPBST.dbo.TRIPBS_TTRNS this_ where this_.PLCYNO like '180220TEW01688%' 
and this_.INSTYP='2' and this_.DELUSR='' AND SALSRC = ''


--crtusr = TLGINS
--CUSID = BI083
--salsrc = 000040
--180610TEC00005
select INSTYP,DELUSR,CFMFLG,PLCYNO,ENDOSNO,SALSRC
,ODRNO,tpfmdt,CUSID,crtusr
from TLGTRIPBST.dbo.TRIPBS_TTRNS where INSTYP='2' and DELUSR='' 
--and ( CFMFLG = 'n' OR CFMFLG ='N' OR CFMFLG ='') and PLCYNO = ENDOSNO
--and PLCYNO like '???%'
select * from TLGTRIPBST.dbo.TRIPBS_TTRNS where PLCYNO ='181219TEW00020'


--alsrc
SELECT aas.SYSUSR10,*  FROM AGNBS_SYSUSR aas WHERE aas.SYSUSR02 = 'PROTLG' --sit=6  --prod?  AND SALSRC='0000040'
--SYSUSR10 org = 6
update AGNBS_SYSUSR set SYSUSR10 = 0 where SYSUSR02='BI083'
update 

--plcyno = 180610TEC00001
select ENDOSNO,PLCYNO,INSTYP,SALSRC,CUSID,CRTUSR,DELUSR from TLGTRIPBST.dbo.TRIPBS_TTRNS this_ where 
--this_.PLCYNO like '180610TEC00005%' and 
this_.INSTYP='2' and 
this_.SALSRC='000040' and 
this_.CUSID='BI083' and 
this_.CRTUSR='TLGINS' and 
this_.DELUSR=?



select * from TLGTRIPBST.dbo.TRIPBS_INSDINFO where ordno like '180610TEC00005%'

select INSTYP, DELUSR  ,PLCYNO,CFMFLG,ENDOSNO from TLGTRIPBST.dbo.TRIPBS_TTRNS


select ac.cusinfo10,* from AGNBS_CUSINFO ac WHERE ac.CUSINFO01 = 'TLGINS' 

select this_.INSTYP as INSTYP13_0_, this_.DELUSR as DELUSR13_0_ ,CUSID,crtusr
from TLGTRIPBST.dbo.TRIPBS_TTRNS this_ where this_.PLCYNO like '180220TEW01688%' 
and this_.INSTYP='2' and this_.DELUSR='' AND SALSRC = 'ac.cusinfo10'



select * from TLGTRIPBST.dbo.TRIPBS_TTRNS where PLCYNO ='181219TEW00020'

select * from TLGTRIPBST.dbo.TRIPBS_TTRNSLOG where PLCYNO ='181219TEW00020'

select * from TLGTRIPBST.dbo.TRIPBS_GOODSBYUSER  where GDSSEQNO = '864'

select * from TLGTRIPBST.dbo.TRIPBS_CONTRACT  where TRVLID = '19J12100'

select * from TLGTRIPBST.dbo.TRIPBS_TATRANSFER  where PLCYNO like '%TEW%'


select * from tripbs_gdsrsklvl where INSTYP = '2'



select GDSSEQNO from TLGTRIPBST.dbo.TRIPBS_TTRNS where PLCYNO ='181219TEW00020';
select * from TLGTRIPBST.dbo.TRIPBS_GOODS where GDSSEQNO = '864';

select *
from TLGTRIPBST.dbo.TRIPBS_GDSRSKLVL where 
--
PRMRTECDE = 'TE001C' --TRIPBS_GOODS.PRMRTECDE
and 
--
PRMRTEVER = '01'-- TRIPBS_GOODS.PRMRTEVER
AND RSKLMT1 = '200'--
AND Rsklmt2 = '20' --
AND RSKDYS = '3' --
--AND RSKLMT3 = '0' --Rsklmt3 0
--AND RSKLMT4 = '0'--
AND INSTYP = '2' --

select 
*
from TLGTRIPBST.dbo.TRIPBS_TTRNS 
where 1=1 
--AND TRVLID='19J12100' --
--AND TPFMDT='20200701' --
--AND SALSRC = 'J12100'
AND SALSRC = 'J12104'
order by CRTDTTM DESC 

SELECT *  FROM TLGAGNBS.dbo.AGNBS_SYSUSR where sysusr02 like 'J1210%'

SELECT * FROM TLGTRIPBST.dbo.TRIPBS_GOODS WHERE 1=1 
AND SALSRC = 'J12104' OR SALSRC = 'J12100'
--AND INSTYP = '2'
--AND OPNDT <= '20200630'
--AND STPDT > = '20200630'
AND GDSSEQNO = '858'


select * from TLGTRIPBST.dbo.TRIPBS_GDSSFTCNT 
where sftcde = 'TE00' --TLGTRIPBST.dbo.TRIPBS_GOODS.GDSCDE 
and gdsseqno = '865' --TLGTRIPBST.dbo.TRIPBS_GOODS.GDSSEQNO

select tripbsgdss0_.GDSSEQNO as GDSSEQNO3_0_, tripbsgdss0_.SFTCDE as SFTCDE3_0_, 
tripbsgdss0_.DDCLTYP as DDCLTYP3_0_, tripbsgdss0_.RSKDDCL as RSKDDCL3_0_, 
tripbsgdss0_.RSKLMT as RSKLMT3_0_, tripbsgdss0_.RSKLMTTYP as RSKLMTTYP3_0_, 
tripbsgdss0_.RSKLMTUNT as RSKLMTUNT3_0_, tripbsgdss0_.SAVESEQ as SAVESEQ3_0_ 
from TRIPBS_GDSSFTCNT tripbsgdss0_ 
where tripbsgdss0_.GDSSEQNO=? and tripbsgdss0_.SFTCDE=?


select * from TLGTRIPBST.dbo.TRIPBS_AGNTINFO where salsrc = 'J12100' -- <--
select * from TLGTRIPBST.dbo.TRIPBS_AGNTINFO where SALSRC = 'J00064' -- <--

select * from TLGTRIPBST.dbo.TRIPBS_GDSSFTCNT where SFTCDE='TE02' AND GDSSEQNO >= 860


select  * from TLGTRIPBST.dbo.TRIPBS_GDSSFTCNT this_ where this_.GDSSEQNO=860 
and this_.SFTCDE<>'TA00' and this_.SFTCDE<>'TA01' 
and this_.SFTCDE<>'TR20' and this_.SFTCDE<>'TR21' 
and this_.SFTCDE<>'TE00' and this_.SFTCDE<>'TE02' 
and this_.SFTCDE<>'TR30'

--exception
select * from TLGTRIPBST.dbo.TRIPBS_CONTRACT where 
1=1 
and salsrc= 'J12100'
and trvlId= '0119F09800373 '
and cnctstrdt < YEAR(GETDATE()) * 10000 + MONTH(GETDATE()) * 100 + DAY(GETDATE())
and cnctstpdt > YEAR(GETDATE()) * 10000 + MONTH(GETDATE()) * 100 + DAY(GETDATE())

--
SELECT * from TLGAGNBS.dbo.AGNBS_SYSUSR WHERE 
SYSUSR05 = '1' AND 
SYSUSR16 !='' 

AND SYSUSR81 = 'Y'

SELECT * from TLGAGNBS.dbo.AGNBS_SYSUSR 
where 
--sysusr01 = 'J12100'
sysusr02 = 'SUPER02';

SELECT * from TLGAGNBS.dbo.AGNBS_SYSUSR 
where 
--sysusr01 = 'J12100'
--sysusr02 = 'SUPER02';
sysusr02 = 'BI083';

--policyNo--180220TEA00694

select ac.cusinfo10,* from AGNBS_CUSINFO ac WHERE ac.CUSINFO01 = 'J00013' --

select ac.cusinfo10,* from AGNBS_CUSINFO ac WHERE ac.CUSINFO01 = 'TLGINS' --

select * from TLGTRIPBST.dbo.TRIPBS_TTRNS this_ 
where 
this_.PLCYNO like '180220TEA00694%' and 
this_.INSTYP='2' and 
this_.SALSRC='000040' and 
this_.DELUSR=''

select * from TLGTRIPBST.dbo.TRIPBS_TTRNS this_ 
where 
this_.PLCYNO like '180220TEA00694%' and 
this_.INSTYP='2' and 
this_.SALSRC='J00013' and 
this_.DELUSR=''


select * from TLGTRIPBST.dbo.TRIPBS_TTRNS this_ where this_.PLCYNO like '180220TEA00694%' 
and this_.INSTYP='2' 
and this_.SALSRC='J00013' 
--and this_.CUSID='TLGINS' 
--and this_.CRTUSR='BI083' 
and this_.DELUSR=''

select  ac.cusinfo10,* from agnbs_cusinfo ac where ac.cusinfo10 = 'J12100' 

select *  from AGNBS_AGNTAUTH  where AGNTAUTH01 = 'J00074'  and AGNTAUTH02 = 'J00074'  and AGNTAUTH03 = 1  order by AGNTAUTH04

select * from TLGTRIPBST.dbo.TRIPBS_AGELMT this_ where this_.SALSRC='J00074' and this_.INSTYP='' order by this_.FMAGE ASC


select 
 this_.OHSPRMCDE,*
from TLGTRIPBST.dbo.TRIPBS_GOODS this_ where GDSSEQNO='843'

select * from TLGTRIPBST.dbo.TRIPBS_GDSRSKLVL tripbsgdsr0_ 
where tripbsgdsr0_.INSTYP='1' and 
tripbsgdsr0_.PRMRTECDE='TR0621' and 
tripbsgdsr0_.PRMRTEVER='01' and 
tripbsgdsr0_.RSKDYS='1' and 
tripbsgdsr0_.RSKLMT1='300' and 
tripbsgdsr0_.RSKLMT2='15' and 
tripbsgdsr0_.RSKLMT3='0' and 
tripbsgdsr0_.RSKLMT4='0'


select  * from TLGTRIPBST.dbo.TRIPBS_GDSRSKLVL tripbsgdsr0_ 
where tripbsgdsr0_.INSTYP='1' and 
--tripbsgdsr0_.PRMRTECDE='' and -- <=(OHSPRMCDE)
tripbsgdsr0_.PRMRTEVER='01' and 
tripbsgdsr0_.RSKDYS='1' and 
tripbsgdsr0_.RSKLMT1='0' and 
tripbsgdsr0_.RSKLMT2='0' and 
tripbsgdsr0_.RSKLMT3='0' 
and tripbsgdsr0_.RSKLMT4='5'


select * FROM TLGTRIPBST.dbo.TRIPBS_TTRNS where ODRNO='95065717410330' ;
select * from TLGTRIPBST.dbo.TRIPBS_INSDINFO this_ where this_.ORDNO='95065717410330' and  ORDNO = ? ORDER BY  CONVERT( DECIMAL( 5,0 ) , INSDSEQ )


select * FROM TLGTRIPBST.dbo.TRIPBS_TTRNS where ODRNO='95065717410590' ;
select * from TLGTRIPBST.dbo.TRIPBS_INSDINFO this_ where this_.ORDNO='95065717410590'


select tpfmdt,tpfmdt,* from TLGTRIPBST.dbo.TRIPBS_TTRNS where PLCYNO ='181220TEA00002';
--181220TEW03688
select * from TRIPBS_TTRNS where

select * from TLGTRIPBST.dbo.TRIPBS_TTRNS this_ 
where this_.TPFMDT>='20200711' and this_.TPFMDT<='20200711' and  
odrno in  ( select MAX(odrno) AS odrno from TLGTRIPBST.dbo.TRIPBS_TTRNS
where tpfmdt >= '20200711' and  tpfmdt <= '20200711'  GROUP BY plcyno ) 
and this_.DELUSR='' order by this_.INSTYP asc, this_.CUSID asc, this_.PLCYNO asc


select tpfmdt,tpfmdt,* from TLGTRIPBST.dbo.TRIPBS_TTRNS where PLCYNO ='181220TEA00002'; --
select tpfmdt,tpfmdt,* from TLGTRIPBST.dbo.TRIPBS_TTRNS where PLCYNO ='181220TEF00012'; --


select GDSSEQNO,ODRNO from TLGTRIPBST.dbo.TRIPBS_TTRNS where PLCYNO ='181220TEA00002';--ODRNO='95065717410636'
select * from TLGTRIPBST.dbo.TRIPBS_GOODS  where GDSSEQNO='64';
select * from TLGTRIPBST.dbo.TRIPBS_INSDINFO this_ where this_.ORDNO='95065717410590'
select INSDIDVF,* from TLGTRIPBST.dbo.TRIPBS_INSDINFO  where ORDNO='95065717410636' 


select * from PRPTENGAGE where proposalno='9F01202001000004'


select * from PRPCENGAGE where policyno='180120FAH00002'

--RISKCODE, ENGAGECODE, ENGAGECNAME, AUTOFLAG, VALIDIND,
SELECT * FROM CCICDMS.PRPDRISKENGAGE 
WHERE RISKCODE = 'F01' 
AND AUTOFLAG = '1' 
AND VALIDIND = '1';

select
        prpdrisken0_.clausecode as clausecode122_,
        prpdrisken0_.riskcode as riskcode122_,
        prpdrisken0_.engagecode as engagecode122_,
        prpdrisken0_.language as language122_,
        prpdrisken0_.flag as flag122_,
        prpdrisken0_.remark as remark122_,
        prpdrisken0_.validind as validind122_,
        prpdrisken0_.areacode as areacode122_,
        prpdrisken0_.invaliddate as invalidd9_122_,
        prpdrisken0_.engagecname as engagec10_122_,
        prpdrisken0_.validdate as validdate122_,
        prpdrisken0_.arealevel as arealevel122_,
        prpdrisken0_.tcol1 as tcol13_122_,
        prpdrisken0_.areaname as areaname122_,
        prpdrisken0_.tcol2 as tcol15_122_,
        prpdrisken0_.tcol3 as tcol16_122_,
        prpdrisken0_.engageType as engageType122_,
        prpdrisken0_.changeable as changeable122_,
        prpdrisken0_.autoflag as autoflag122_,
        prpdrisken0_.engagedesc as engagedesc122_,
        prpdrisken0_.engagelevel as engagel21_122_,
        prpdrisken0_.oldengagecode as oldenga22_122_,
        prpdrisken0_.areamappingcode as areamap23_122_,
        prpdrisken0_.engageename as engagee24_122_ 
    from
        CCICDMS.prpdriskengage prpdrisken0_ 
    where
        prpdrisken0_.validind=1 
        and (
            prpdrisken0_.riskcode in (
                'F01' , 'PUB'
            )
        ) 
        and (
            prpdrisken0_.clausecode like '%'
        ) 
        and prpdrisken0_.language='C' 
        and (
            prpdrisken0_.engagecode like 'SBC24%'
        ) 
    order by
        riskCode,
        clauseCode,
        engageCode


 --續保篩選
--恢復可續保 sql FIR0279 187021F0H00002 187021F0H00013
--測試保單號：180021F0H00035 商火
--UPDATE BUSINESS.PRPCMAIN SET OTHFLAG ='000000YY000000N00000' WHERE RISKCODE = 'F01' AND POLICYNO = '180021R0H00176';
--UPDATE BUSINESS.PRPCMAIN SET OTHFLAG = '000000YY000000N00000' WHERE POLICYNO = '180021F0H00075';//變更為可續保狀態
SELECT * from BUSINESS.PRPTENGAGE WHERE LINENO = '1' AND RISKCODE = 'F01'
SELECT * from BUSINESS.PRPTENGAGE WHERE PROPOSALNO = '9F01202100000151'
SELECT * from BUSINESS.PRPTENGAGE WHERE PROPOSALNO = '9F01202100000155'
SELECT POLICYNO,OTHFLAG, RISKCODE,ENDORSETIMES, CLAIMTIMES FROM PRPCMAIN WHERE POLICYNO = '180021R0H00176';

SELECT PROPOSALNO, ENDORSETIMES, CLAIMTIMES,
CASE WORKSTATUS WHEN '00' THEN '00不執行' WHEN '01' THEN '01待再查詢' WHEN '02' THEN '02查詢中' WHEN '03' THEN '03收到回覆拒保' 
	WHEN '04' THEN '04收到回覆可承保' WHEN '05' THEN '05查詢異常' WHEN '06' THEN '06查詢超時' WHEN '07' THEN '07人工審核註記' WHEN '08' THEN '08人工審核完成'
END AS WORKSTATUS作業狀態,
CASE REFUSELIMITEINSURANCE WHEN '00' THEN '00未命中' WHEN '01' THEN '01命中未判定' WHEN '02' THEN '02命中已判定True' WHEN '03' THEN '03命中已判定False'
END AS REFUSELIMITEINSURANCE拒限保,
CASE LISTDETECTION WHEN '01' THEN '01未命中' WHEN '02' THEN '02命中未判定' WHEN '03' THEN '03命中已判定'
END AS LISTDETECTION名單檢測,
CASE RISKRATING WHEN '00' THEN '00高風險未處理' WHEN '01' THEN '01高風險已處理' WHEN '02' THEN '02中風險未處理' WHEN '03' THEN '03中風險已處理' WHEN '04' THEN '04低風險'
END AS RISKRATING風險評級,
SENDTYPE
FROM PRPTMAIN WHERE PROPOSALNO in ('9F02202100000458','9F02202100000459','9F02202100000460','9F02202100000461');

SELECT 
UPDATEDATE,
SIGNDATE,
OPERATEDATE,
UNDERWRITEENDDATE 
 FROM PRPTMAIN WHERE PROPOSALNO ='9F02202100000458';

select prpdrisken0_.*
    from
        CCICDMS.prpdriskengage prpdrisken0_
    where
        prpdrisken0_.validind=1
        and (
            prpdrisken0_.riskcode in (
                'F01' , 'PUB'
            )
        ) 
        and (
            prpdrisken0_.clausecode like '%'
        ) 
        and prpdrisken0_.language='C' 
    order by
        riskCode,
        clauseCode,
        engageCode
        
        
        
        select
        prpdrisken0_.AUTOFLAG,prpdrisken0_.*
    from
        CCICDMS.prpdriskengage prpdrisken0_ 
    where
        prpdrisken0_.validind=1 
--AND        prpdrisken0_.AUTOFLAG != '1'
--AND        prpdrisken0_.AUTOFLAG != '0'
        AND  prpdrisken0_.AUTOFLAG like '1%'
        and (
            prpdrisken0_.riskcode in (
                'F01' , 'PUB'
            )
        ) 
        and (
            prpdrisken0_.clausecode like '%'
        ) 
        and prpdrisken0_.language='C' 
    order by
        riskCode,
        clauseCode,
        engageCode
   

SELECT * FROM SALES.PRPYDDAGENT 
WHERE ORGICODE = '001311'
AND SERIALNO != '167'
AND VERIFYREMARK = '1'
        
SELECT BUSINESSSOURCE, ORGICODE,AGENTID, COUNT(*) AS AA FROM SALES.PRPYDDAGENT 
WHERE ORGICODE = '001311' 
AND SERIALNO != '167'
AND VERIFYREMARK = '1'
GROUP BY BUSINESSSOURCE, ORGICODE,AGENTID 
ORDER BY BUSINESSSOURCE,ORGICODE        

select
        * 
    from
        ( select
            this_.ID,
            this_.AGENTNAME,
            this_.IDENTIFYNUMBER,
            this_.BUSINESSSOURCE,
            this_.BUSINESSSOURCENAME,
            this_.SERIALNO,
            this_.UNITCODE,
            this_.SALECOMNAME,
            this_.SERVICEPERSONNAME,
            this_.SALECOMCODE,
            this_.INTRODUCERNAME,
            this_.SERVICEPERSONCODE,
            this_.ORGICODE,
            this_.VERIFYREMARK,
            this_.AGENTID,
            this_.INTRODUCERID 
        from
            SALES.PRPYDDAGENT this_ 
        where
            1=1  
            and (
                businessSource = 'I99004' --業務來源代號
            )  
            and (
                agentID = 'NB1H010889' --登錄證號 
            )  
            and (
                unitCode = '54159603'--統一編號
            )  
            and (
                identifyNumber = 'N221872475'--身分證字號
            )  
            and (
                orgiCode = '099475'--業代員編
            )  
            and (
                saleComCode = '0042444'--營單位代號
            )  
            and (
                servicePersonCode = 'AJ058'--服務員代碼
            )  
            and (
                verifyreMark = '1'--校驗注記
            ) ) 
    where
        rownum <= 15
        
        
        
        
     select
        count(*) as col_0_0_ 
    from
        prpdnewcode prpdnewcod0_ 
    where
        prpdnewcod0_.codetype='BusinessChannel2' 
        and (
            prpdnewcod0_.codecode like '000000%'
        ) 
        and (
            prpdnewcod0_.codecname like '%' 
            or prpdnewcod0_.codeename like '%'
        ) 
        and prpdnewcod0_.validstatus=1
        
 
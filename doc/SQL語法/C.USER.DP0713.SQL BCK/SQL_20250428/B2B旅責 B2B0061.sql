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
--台壽保旅平/旅責險保期調整查詢 必要條件
--and ( CFMFLG = 'n' OR CFMFLG ='N' OR CFMFLG ='') and PLCYNO = ENDOSNO
--and PLCYNO like '???%'
select * from TLGTRIPBST.dbo.TRIPBS_TTRNS where PLCYNO ='181219TEW00020'


--查詢salsrc
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


select ac.cusinfo10,* from AGNBS_CUSINFO ac WHERE ac.CUSINFO01 = 'TLGINS' --帶進去的

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
--自商品檔中取出,費率代號
PRMRTECDE = 'TE001C' --TRIPBS_GOODS.PRMRTECDE
and 
--自商品檔中取出,費率版次
PRMRTEVER = '01'-- TRIPBS_GOODS.PRMRTEVER
AND RSKLMT1 = '200'-- 死殘保保額
AND Rsklmt2 = '20' -- 醫療限額
AND RSKDYS = '3' --天數
--AND RSKLMT3 = '0' --Rsklmt3 0
--AND RSKLMT4 = '0'--海外突發疾病保額
AND INSTYP = '2' --險種別-旅責

select 
*
from TLGTRIPBST.dbo.TRIPBS_TTRNS 
where 1=1 
--AND TRVLID='19J12100' --交易編號
--AND TPFMDT='20200701' --出團日
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
where sftcde = 'TE00' --TLGTRIPBST.dbo.TRIPBS_GOODS.GDSCDE 前四碼
and gdsseqno = '865' --TLGTRIPBST.dbo.TRIPBS_GOODS.GDSSEQNO

select tripbsgdss0_.GDSSEQNO as GDSSEQNO3_0_, tripbsgdss0_.SFTCDE as SFTCDE3_0_, 
tripbsgdss0_.DDCLTYP as DDCLTYP3_0_, tripbsgdss0_.RSKDDCL as RSKDDCL3_0_, 
tripbsgdss0_.RSKLMT as RSKLMT3_0_, tripbsgdss0_.RSKLMTTYP as RSKLMTTYP3_0_, 
tripbsgdss0_.RSKLMTUNT as RSKLMTUNT3_0_, tripbsgdss0_.SAVESEQ as SAVESEQ3_0_ 
from TRIPBS_GDSSFTCNT tripbsgdss0_ 
where tripbsgdss0_.GDSSEQNO=? and tripbsgdss0_.SFTCDE=?


select * from TLGTRIPBST.dbo.TRIPBS_AGNTINFO where salsrc = 'J12100' -- <--上次發生的
select * from TLGTRIPBST.dbo.TRIPBS_AGNTINFO where SALSRC = 'J00064' -- <--這次發生的

select * from TLGTRIPBST.dbo.TRIPBS_GDSSFTCNT where SFTCDE='TE02' AND GDSSEQNO >= 860


select  * from TLGTRIPBST.dbo.TRIPBS_GDSSFTCNT this_ where this_.GDSSEQNO=860 
and this_.SFTCDE<>'TA00' and this_.SFTCDE<>'TA01' 
and this_.SFTCDE<>'TR20' and this_.SFTCDE<>'TR21' 
and this_.SFTCDE<>'TE00' and this_.SFTCDE<>'TE02' 
and this_.SFTCDE<>'TR30'

--查解開全部僅能查一筆  多筆程式會出exception
select * from TLGTRIPBST.dbo.TRIPBS_CONTRACT where 
1=1 
and salsrc= 'J12100'
and trvlId= '0119F09800373 '
and cnctstrdt < YEAR(GETDATE()) * 10000 + MONTH(GETDATE()) * 100 + DAY(GETDATE())
and cnctstpdt > YEAR(GETDATE()) * 10000 + MONTH(GETDATE()) * 100 + DAY(GETDATE())

--找銀行系列帳號
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

select ac.cusinfo10,* from AGNBS_CUSINFO ac WHERE ac.CUSINFO01 = 'J00013' --帶進去的

select ac.cusinfo10,* from AGNBS_CUSINFO ac WHERE ac.CUSINFO01 = 'TLGINS' --帶進去的

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
--tripbsgdsr0_.PRMRTECDE='' and -- <=遺漏這項(OHSPRMCDE)
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


select tpfmdt,tpfmdt,* from TLGTRIPBST.dbo.TRIPBS_TTRNS where PLCYNO ='181220TEA00002'; --第一筆 手動 第二三 匯入
select tpfmdt,tpfmdt,* from TLGTRIPBST.dbo.TRIPBS_TTRNS where PLCYNO ='181220TEF00012'; --一筆 手動


select GDSSEQNO,ODRNO from TLGTRIPBST.dbo.TRIPBS_TTRNS where PLCYNO ='181220TEA00002';--ODRNO='95065717410636'
select * from TLGTRIPBST.dbo.TRIPBS_GOODS  where GDSSEQNO='64';
select * from TLGTRIPBST.dbo.TRIPBS_INSDINFO this_ where this_.ORDNO='95065717410590'
select INSDIDVF,* from TLGTRIPBST.dbo.TRIPBS_INSDINFO  where ORDNO='95065717410636' 


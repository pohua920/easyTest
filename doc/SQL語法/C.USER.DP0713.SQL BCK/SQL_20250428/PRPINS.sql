ORACLE:BUSINESS.CCICDMS.
MSSQL:TLGCTBCSIT.TLGCTBCUAT.
--## prpins 核心

-->>--衝破洗錢
SELECT WORKSTATUS FROM BUSINESS.PRPTMAIN WHERE PROPOSALNO in ('9EL202000000057');
--update BUSINESS.PRPTMAIN set WORKSTATUS='04' WHERE PROPOSALNO in ('9EL202000000057');


-->>--FIR0232
--保額1500000 判斷式式oldamount=1288800 這個來源?
SELECT KINDCODE,AMOUNT,OLDAMOUNT FROM PRPTITEMKIND WHERE PROPOSALNO = '9F02202060000001' AND KINDCODE = 'FR2';
SELECT KINDCODE,AMOUNT,OLDAMOUNT FROM PRPTITEMKIND WHERE PROPOSALNO = '9F02202000001236' AND KINDCODE = 'FR2';

--結構對應坪價
--http://localhost:7001/prpins/prpins/policy/mrisk/m03001/UIPrpslPoliItemKindInput.js
--  function checkAmountValue(field) {
SELECT this_.ROOFNO ,
        this_.VALIDDATE,
        this_.WALLNO ,
        this_.FLAG ,
        this_.INVALIDDATE ,
        this_.STRUCTURENO  --<<=price	"70800"
    from
        BUSINESS.PRPDPROPSTRUCT this_ 
    where
        --this_.WALLNO=? 
        --and this_.ROOFNO=? 
        --and 
        validDate <= to_date('2020-11-12', 'yyyy-MM-dd') 
        and invalidDate > to_date('2020-11-12', 'yyyy-MM-dd') 

--FIR0237
SELECT * FROM BUSINESS.PRPMAXNO WHERE GROUPNO = '9F01202000'

SELECT * FROM BUSINESS.PRPTMAIN WHERE PROPOSALNO = '9F01202000000088'
   

    select
        this_.FIELDNAME,
        this_.TABLENAME,
        this_.COLLENGTH,
        this_.FIELDMEANING,
        this_.FLAG,
        this_.HEADID,--<<<<<<  [18]
        this_.inserttimeforhis,
        this_.operatetimeforhis 
    from
        BUSINESS.UTIKEY this_ 
    where
        this_.TABLENAME like 'prpcmain' 
        and this_.FIELDNAME like 'policyno'

--F01 + F0H + 2020 + 00
select
   GROUPNO,--<<<<<<  [0020F0H]
   SUBGROUPNO,
   FLAG
from
   BUSINESS.PRPGROUP
where
   SUBGROUPNO LIKE 'F01FA%202000'
   
   --strGroupNo == HEADID + GROUPNO = 180020F0H
   
--給完單號後 MAXNO單號+1  導致下一個拿到單號的就是下一號碼
SELECT * FROM BUSINESS.PRPMAXNO WHERE GROUPNO = '180020F0H'

--已經使用過的號碼
SELECT * FROM BUSINESS.prpmaxuse WHERE GROUPNO = '180020F0H'

--180020F0C00001(共保測試查詢-查詢已經使用的號碼)
SELECT * FROM BUSINESS.prpmaxuse WHERE GROUPNO like '1800%FAC%'
   

SELECT * FROM BUSINESS.PRPCMAIN WHERE POLICYNO='180014F0C00057'
SELECT * FROM BUSINESS.PRPTMAIN WHERE POLICYNO='180018FAC00002'

SELECT * FROM BUSINESS.PRPCNAME WHERE POLICYNO='182020EL0000002' AND PROPOSALNO = '182020ELE000003'

--mantis： FIR0239，處理人員：DP0713，需求單編號：FIR0239 更名_住火要保書調整
   select
        prptengage0_.PROPOSALNO as PROPOSALNO1_,
        prptengage0_.LINENO as LINENO1_,
        prptengage0_.SERIALNO as SERIALNO1_,
        prptengage0_.LINENO as LINENO741_0_,
        prptengage0_.PROPOSALNO as PROPOSALNO741_0_,
        prptengage0_.SERIALNO as SERIALNO741_0_,
        prptengage0_.CLAUSECODE as CLAUSECODE741_0_,
        prptengage0_.CLAUSENAME as CLAUSENAME741_0_,
        prptengage0_.CLAUSES as CLAUSES741_0_,
        prptengage0_.FLAG as FLAG741_0_,
        prptengage0_.RISKCODE as RISKCODE741_0_,
        prptengage0_.TITLEFLAG as TITLEFLAG741_0_ 
    from
        PRPTENGAGE prptengage0_ 
    where
        prptengage0_.PROPOSALNO='9F02202000001259'
        
       

 SELECT p.PUREPREMIUM FROM BUSINESS.PRPCITEMKIND p WHERE 
 
--#interface #if #CAR0288
  SELECT i.DRAWOUTDATE ,i.* FROM BUSINESS.INTFPRPJPAYREFREC i  where i.policyno in ('180020D00050','180020C1D00041');
  SELECT * FROM BUSINESS.PRPCOPYINSURED WHERE POLICYNO IN ('180020D00050','180020C1D00041');
 
--更新
  --update BUSINESS.intfprpjpayrefrec set DRAWOUTDATE ='' where policyno in ('180020D00050','180020C1D00041');
  --PRPCOPYINSURED email
 
 SELECT * FROM BUSINESS.PRPCLIMIT WHERE POLICYNO = '180021F0H00002'
 SELECT OTHFLAG,a.* FROM BUSINESS.PRPCMAIN a WHERE a.RISKCODE = 'F01' AND a.POLICYNO = '180021F0H00002';


 SELECT OTHFLAG,a.* FROM BUSINESS.PRPTMAIN a WHERE a.RISKCODE = 'F01' AND PROPOSALNO = '9F01202150000003';
 SELECT OTHFLAG,a.* FROM BUSINESS.PRPTMAIN a WHERE a.RISKCODE = 'F01' AND PROPOSALNO = '9F01202150000005';

 --基本費率(F01商火險) FIR0261
 SELECT KINDCODE,BASERATE,RISKCODE,VALIDFLAG,STATISTICSNUM FROM BUSINESS.PrpDbaseRate WHERE  RISKCODE='F01' AND VALIDFLAG='1' AND KINDCODE=主險的險種代碼 AND STATISTICSNUM=使用性質代碼
 
 --續保篩選
 --要用詩雅帳號登入 並且 18[70]21F0H00013 ...單位得用70
--恢復可續保 sql FIR0261 187021F0H00002 187021F0H00013
--UPDATE BUSINESS.PRPCMAIN SET OTHFLAG ='000000YY000000N00000' WHERE RISKCODE = 'F01' AND POLICYNO = '187021F0H00013';
SELECT OTHFLAG FROM BUSINESS.PRPCMAIN WHERE RISKCODE = 'F01' AND POLICYNO = '180021F0H00002';
 SELECT BASERATE,KINDCODE,STATISTICSNUM FROM BUSINESS.PrpDbaseRate 
 WHERE  
        kindCode='FB2' 
        and riskcode='F01' 
        and validFlag='1' 
        and STATISTICSNUM='C0301A1' 
        and BUILDINGLEVEL='1'
 
 --FIR0261  9F01202170000003  187021F0H00002 
0.37	
 --180020F0H00063 
 --已經續保OTHFLAG:010000YY000000N00000,
 --還未續保OTHFLAG:000000YY000000N00000 (轉成此狀態才能繼續測試 才能 續保 查詢 以及 續保)
 
 --FIR0261 
 SELECT 
 p.DANGERRATE,p.RATE,
 p.POLICYNO,p.KINDCODE,
 p.BASERATE,p.HIGHRISEFEE,p.OPERATINGFEE,p.SBFEE,p.FIREFEE,
 p.UNDERWRITCONSIDERFEE,p.DEDUCTIBLERATE,p.SUBRATE 
 --SELECT * 
 FROM BUSINESS.PRPCITEMKIND p 
 WHERE 
 p.POLICYNO = '180021F0H00002' AND p.RISKCODE='F01' 
 AND (
 p.KINDCODE='FB1' 
 --OR 
 --p.KINDCODE='FB2'
 )--'180020F0H00063'
 
--UAT上測試資料:
--保單號: 180120FAH00044
 --要用詩雅帳號登入 並且 18'01'20FAH00044 ...單位得用01

--FIR300變更為可續保狀態:
--UPDATE BUSINESS.PRPCMAIN SET OTHFLAG = '000000YY000000N00000' WHERE POLICYNO = '180120FAH00044';
 select * FROM BUSINESS.PRPCITEMKIND p  WHERE p.RISKCODE = 'F01' AND p.POLICYNO = '180120FAH00044' 
 select * FROM BUSINESS.PRPCENGAGE p WHERE p.POLICYNO = '180120FAH00044' --跳號產生錯誤
select * FROM BUSINESS.PRPTENGAGE p WHERE p.proposalno = '9F01202101000021'

SELECT * FROM BUSINESS.PRPPENGAGE p WHERE p.POLICYNO = '9F01202100000190'
select * FROM BUSINESS.PRPTENGAGE p WHERE p.proposalno = '9F01202100000190'
 
 --FIR0261 主險貨物 預收比例  貨物預約 prpCitemKind.salePre (應為1或0.75) //2021/02/02 
 select p.SALEPRE FROM BUSINESS.PRPCITEMKIND p  WHERE p.RISKCODE = 'F01' AND p.POLICYNO = '180021F0H00002' 
 
--FIR0264  
SELECT * FROM PRPCITEMKIND p WHERE KINDCODE = 'B9'


SELECT * FROM PRPTCOMMISSIONDETAIL p WHERE  PROPOSALNO = '9F01202101000087'
--要保
SELECT * FROM PRPTITEMKIND p WHERE proposalno = '9F01201802000009' 
SELECT * FROM PRPTCOMMISSIONDETAIL p WHERE  PROPOSALNO = '9F01201802000009'
--要保
SELECT * FROM PRPTITEMKIND p WHERE proposalno = '9F01201936000001' 
SELECT * FROM PRPTCOMMISSIONDETAIL p WHERE  PROPOSALNO = '9F01201936000001'
--要保
SELECT * FROM PRPTITEMKIND p WHERE proposalno = '9F01201901000035' 
SELECT * FROM PRPTCOMMISSIONDETAIL p WHERE  PROPOSALNO = '9F01201901000035'

--第三人
    select
        *
    from
        CCICDMS.prpdriskclausekind prpdriskcl0_ 
    where
        1=1 
        and prpdriskcl0_.riskcode='F01' 
        and prpdriskcl0_.validind=1 
        and (
            sysdate between validdate and invaliddate 
            or sysdate>=validdate 
            and (
                invaliddate is null
            )
        ) 
        and prpdriskcl0_.tcol1='3'
        
        
 ----附加      

    select
        * 
    from
        CCICDMS.prpdriskclausekind prpdriskcl0_ 
    where
        1=1 
        and prpdriskcl0_.riskcode='A' 
        and prpdriskcl0_.validind=1 
        and (
            sysdate between validdate and invaliddate 
            or sysdate>=validdate 
            and (
                invaliddate is null
            )
        ) 
        and prpdriskcl0_.tcol1='2'
        
        
Hibernate: 
    select
        * 
    from
        ( select
            *
        from
            prpdriskclausekind prpdriskcl0_ 
        where
            1=1 
            and prpdriskcl0_.riskcode='A' 
            and prpdriskcl0_.validind=1 
            and (
                sysdate between validdate and invaliddate 
                or sysdate>=validdate 
                and (
                    invaliddate is null
                )
            ) 
            and prpdriskcl0_.tcol1='2' 
        order by
            prpdriskcl0_.upperkindcode ) 
    where
        rownum <= ?
        
        
------FIR0267 普批 保批  危險費率
----主險
--取出原保單中基本費率的SQL
SELECT bp.BASERATE,bp.* FROM BUSINESS.PRPCITEMKIND bp WHERE bp.POLICYNO='180021FAH00005'
--取出基本費率設定檔中的SQL
SELECT * from BUSINESS.PrpDbaseRate  where kindCode='FB1'  and statisticsNum = 'B0001A1' and buildingLevel = '2' and validFlag = '1'


----附加險
--取出原保單中基本費率的SQL
SELECT bp.BASERATE,bp.* FROM BUSINESS.PRPCITEMKIND bp WHERE bp.POLICYNO='180021FAH00005'
--取出基本費率設定檔中的SQL
SELECT * from BUSINESS.PrpDbaseRate  where kindCode='BB' and  validFlag = '1'

----CAR296 稽核改善項目，台壽12通路邏輯鎖控
--找出 通路別12 單位32開頭的  

Select * from FIR_BUSSINESS_PRINT_TMP
select
        *
    from
        BUSINESS.PRPDEXCH prpdexch0_
        where
        prpdexch0_.BASECURRENCY='CAD' 
        and (
            prpdexch0_.EXCHCURRENCY in (
                'USD'
            )
        ) 
        --and prpdexch0_.EXCHDATE<=to_date('2021-05-18', 'yyyy-MM-dd') 
    order by
        exchdate DESC

--MAR0027
select
        *
    from
        CCICDMS.PRPDEXCH prpdexch0_
        
    select
        prpdexch0_.BASECURRENCY as BASECURR1_79_,
        prpdexch0_.EXCHCURRENCY as EXCHCURR2_79_,
        prpdexch0_.EXCHDATE as EXCHDATE79_,
        prpdexch0_.BASE as BASE79_,
        prpdexch0_.FLAG as FLAG79_,
        prpdexch0_.VALIDSTATUS as VALIDSTA6_79_,
        prpdexch0_.invaliddate as invalidd7_79_,
        prpdexch0_.CASHPRICE as CASHPRICE79_,
        prpdexch0_.SALEPRICE as SALEPRICE79_,
        prpdexch0_.BUYPRICE as BUYPRICE79_,
        prpdexch0_.EXCHRATE as EXCHRATE79_,
        prpdexch0_.validdate as validdate79_ 
    from
        CCICDMS.PRPDEXCH prpdexch0_ 
    where
        prpdexch0_.BASECURRENCY='USD' 
        and (
            prpdexch0_.EXCHCURRENCY in (
                'NTD'
            )
        ) 
        and prpdexch0_.EXCHDATE<=to_date('2021-05-18', 'yyyy-MM-dd') 
    order by
        exchdate DESC
        
        
     select
        prpdexch0_.BASECURRENCY as BASECURR1_79_,
        prpdexch0_.EXCHCURRENCY as EXCHCURR2_79_,
        prpdexch0_.EXCHDATE as EXCHDATE79_,
        prpdexch0_.BASE as BASE79_,
        prpdexch0_.FLAG as FLAG79_,
        prpdexch0_.VALIDSTATUS as VALIDSTA6_79_,
        prpdexch0_.invaliddate as invalidd7_79_,
        prpdexch0_.CASHPRICE as CASHPRICE79_,
        prpdexch0_.SALEPRICE as SALEPRICE79_,
        prpdexch0_.BUYPRICE as BUYPRICE79_,
        prpdexch0_.EXCHRATE as EXCHRATE79_,
        prpdexch0_.validdate as validdate79_ 
    from
        CCICDMS.PRPDEXCH prpdexch0_ 
    where
        prpdexch0_.BASECURRENCY='CAD' 
        and (
            prpdexch0_.EXCHCURRENCY in (
                'NTD'
            )
        ) 
        and prpdexch0_.EXCHDATE<=to_date('2021-05-18', 'yyyy-MM-dd') 
    order by
        exchdate DESC
 
--MAR0027 MAR0028
select EXCHDATE, EXCHRATE
  from CCICDMS.PRPDEXCH
 where BASECURRENCY='SEK'
   and (EXCHCURRENCY in ('NTD'))
   and EXCHDATE<=to_date('2021-05-18', 'yyyy-MM-dd')
 order by exchdate DESC;


select
        prpdnewcod0_.codecode as col_0_0_,
        prpdnewcod0_.codecname as col_1_0_ 
    from
        CCICDMS.prpdnewcode prpdnewcod0_ 
    where
        prpdnewcod0_.codetype='CurrencyM' 
        and (
            prpdnewcod0_.codecode like '%'
        ) 
        and (
            prpdnewcod0_.codecname like '%' 
            or prpdnewcod0_.codeename like '%'
        ) 
        and prpdnewcod0_.validstatus=1
        
 
 SELECT p.EMAIL,p.IDENTIFYNUMBER,p.* FROM BUSINESS.PRPCINSURED p 
WHERE 
--email = 'dp0713@tlg-insurance.com'
POLICYNO ='18020PETEST0CB'

select * from BUSINESS.prpcmain t1 where t1.policyno = '180116000100'
SELECT  p.* FROM BUSINESS.PRPCMAIN p WHERE rownum < 10

CALL BUSINESS.temp_proc;

CALL  EXISTS_TABLEB_REC1

select * from prpcmain t1 where t1.policyno = '187020EM0000001';


SELECT * FROM tlginsapp.prpmcode WHERE codetype='CarModelNew'
ZZ00000
36012940


create or replace procedure BUSINESS.temp_proc is
begin
  DBMS_OUTPUT.PUT_LINE('Test');
end
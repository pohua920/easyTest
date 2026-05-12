SELECT "OID", OID_FIR_PREMCALC_TMP, INSURED_NAME, INSURED_ID, INSURED_PRINCIPAL, INSURED_PRINCIPAL_ID, INSURED_ZIPCODE, INSURED_ZIPNAME, INSURED_ADDRESS, INSURED_PHONENUMBER, INSURED_TYPE, CREATE_DATE
FROM BUSINESS.FIR_BUSSINESS_PRINT_INSURED;

INSERT INTO FIR_BUSSINESS_PRINT_INSURED

INSERT INTO FIR_BUSSINESS_PRINT_TMP(OID,FIR_PREMCALC_TMP_AMT_OID,FIR_PREMCALC_TMP_FEE_OID,CREATE_DATE,USERPRINTNO)
VALUES(0,100,101,TO_DATE('12/01/2020', 'MM/DD/YYYY'),'DP0713FB2009001')

INSERT INTO FIR_BUSSINESS_PRINT_INSURED(OID,OID_FIR_BUSSINESS_PRINT_TMP,INSURED_TYPE,CREATE_DATE)
VALUES(0,0,1,TO_DATE('12/01/2020', 'MM/DD/YYYY'))

INSERT INTO FIR_BUSSINESS_PRINT_INSURED(OID,OID_FIR_BUSSINESS_PRINT_TMP,INSURED_TYPE,CREATE_DATE)
VALUES(0,0,2,TO_DATE('12/01/2020', 'MM/DD/YYYY'))

SELECT * FROM FIR_BUSSINESS_PRINT_TMP ORDER BY "OID" DESC

SELECT * FROM FIR_BUSSINESS_PRINT_INSURED ORDER BY "OID" DESC

select CODECODE,CODEcNAME from PrpDnewCode where codetype='PostAddress' and validstatus='1'

UPDATE FIR_BUSSINESS_PRINT_INSURED SET INSURED_NAME = 'TEST0_0_1' 
WHERE OID = 0 AND OID_FIR_BUSSINESS_PRINT_TMP = 0 AND INSURED_TYPE = 1

UPDATE FIR_BUSSINESS_PRINT_INSURED SET INSURED_NAME = 'TEST0_0_2' 
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
        

        SELECT WORKSTATUS FROM BUSINESS.PRPTMAIN WHERE PROPOSALNO in ('9EL202002000005')
        update BUSINESS.PRPTMAIN set WORKSTATUS='04' WHERE PROPOSALNO in ('9EL202002000005');

       
       SELECT REPORTNO,a.* FROM PRPDCLAUSEREPORT a WHERE a.CLAUSECODE='EL' AND a.TCOL1 is NULL;
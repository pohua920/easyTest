select * from PRPLREGIST
where registno='6PB000880000240000018';

delete from PRPLREGIST
where registno='6PB000880000240000018';

select * from PRPLREGISTRPOLICY
where registno='6PB000880000240000018';

delete from PRPLREGISTRPOLICY
where registno='6PB000880000240000018';

select * from PRPLCLAIM
where registno='6PB000880000240000018';

delete from PRPLCLAIM
where registno='6PB000880000240000018';

select * from PRPLCLAIMSTATUS
where POLICYNO='188823PB0000193';

delete from PRPLCLAIMSTATUS
where POLICYNO='188823PB0000193';

select * from PRPLCLAIMLOSS
where registno='6PB000880000240000018';

delete from PRPLCLAIMLOSS
where registno='6PB000880000240000018';

select * from SWFLOG
where flowid='LPB00000000025000110';

delete from SWFLOG
where flowid='LPB00000000025000110';

select * from SWFPATHLOG
where flowid='LPB00000000025000110';

delete from SWFPATHLOG
where flowid='LPB00000000025000110';

select * from SWFNOTION
where flowid='LPB00000000025000110';

delete from SWFNOTION
where flowid='LPB00000000025000110';

select * from SWFFLOWMAIN
where flowid='LPB00000000025000110';

delete from SWFFLOWMAIN
where flowid='LPB00000000025000110';

select * from PRPLCOMPENSATE
where COMPENSATENO='R188824PBL0503301';

delete from PRPLCOMPENSATE
where COMPENSATENO='R188824PBL0503301';

select * from PRPLPAYOBJECTINFO
where COMPENSATENO='R188824PBL0503301';

delete from PRPLPAYOBJECTINFO
where COMPENSATENO='R188824PBL0503301';

select * from PRPLLOSS
where COMPENSATENO='R188824PBL0503301';

delete from PRPLLOSS
where COMPENSATENO='R188824PBL0503301';

select * from WFLOG
where policyno='188823PB0000193';

delete from WFLOG
where policyno='188823PB0000193';

select * from taskparaminfo
where businessid='R188824PBL0503301';

delete from taskparaminfo
where businessid='R188824PBL0503301';


select processins0_.PROCESSINSTANCEID,processins0_.* 
from ProcessInstanceBOInfo processins0_ 
where 
processins0_.businessId = 'R188824PBL0503301'
--processins0_.businessId like 'R188824PBL05033%'

select * from task
where processinstanceid=1644879;

delete from task
where processinstanceid=1644879;

delete from PROCESSINSTANCEBOINFO
where businessid='R188824PBL0503301';



-----------------------------------------------------------------------------------------------------
--保單刪除，-------------(我改了)順序由下到上

--2
select * from PRPJPAYREFREC
where policyno='188823PB0000193';
--1
delete from PRPJPAYREFREC
where policyno='188823PB0000193';


--4
select * from PRPCOPYLIMIT
where policyno='188823PB0000193';
--3
delete from PRPCOPYLIMIT
where policyno='188823PB0000193';


--6
select * from PRPCLIMIT
where policyno='188823PB0000193';
--5
delete from PRPCLIMIT
where policyno='188823PB0000193';


--8
select * from PRPCOPYITEMKIND
where policyno='188823PB0000193';
--7
delete from PRPCOPYITEMKIND
where policyno='188823PB0000193';


--10
select * from PRPCITEMKIND
where policyno='188823PB0000193';
--9
delete from PRPCITEMKIND
where policyno='188823PB0000193';


--12
select * from PRPCOPYINSURED
where policyno='188823PB0000193';
--11
delete from PRPCOPYINSURED
where policyno='188823PB0000193';



--14
select * from PRPCOPYMAIN
where policyno='188823PB0000193';
--13=
delete from PRPCOPYMAIN
where policyno='188823PB0000193';


--16
select * from PRPCINSURED
where policyno='188823PB0000193';
--15
delete from PRPCINSURED
where policyno='188823PB0000193';


--18
select * from prpcmain
where policyno='188823PB0000193';
--17
delete from prpcmain
where policyno='188823PB0000193';




--遺漏補回

SELECT * FROM PrpLcompensate s WHERE s.compensateNo = 'R188824PBL0503301'

select distinct(registno) from BUSINESS.prplregistrpolicy@PROD where 1=1  and  claimNo='188824PBL05033' 

SELECT 
id --功能測試 可使用ID做 扣款,公會,失敗,通知,核心,重復頭寶鹼和
,error_code--查詢程式 StatusCode.java
, owner_id, owner_name
, accept_no--每筆不可重復號碼
, customer_status
, policy_status -- 啟動 WATTING 等待處理   FINISH完成
, traveling_day,beg_date_sgn, end_date_sgn--[注意]必須是現在之後的時間
, debit_type --1:轉帳扣款, 2:本行信用卡
, deduction_return_code--似乎不是0 都是錯誤
, deduction_return_message--錯誤代碼
, card_no --變為 4311********9999 已經跑過 改回明碼4003611000257942似乎可以再跑
, card_check_code, card_expired_date--信用卡訊息
, message_id, prod_code, project_faceamt, project_name, project_premium
, benefit_name, benefit_relative, benefit_ssn, benefit_type, bphone, brigade_amount, brigade_premium, bsend_addr, bsend_city, bsend_country, bsend_zip_code, cellur_phone_no, client_dt, correlation_id, customer_status, death_disability_amount, death_disability_premium, debit_account, debit_success, disability_amount, disability_premium, email, household_income, individual_assets, individual_income, insco_no, io_chan_code, is_in_taiwan, mobile, oi_traveling, other_income, other_insured, other_insured_amt, overseas_amount, overseas_premium, owner_birth, phone, plan_code, plan_version, policy_no, register_code, return_code, return_message, send_addr, send_city, send_country, send_zip_code, system_id, tel_interview_status, travel_inc, travel_premium, txn_id, is_guard, b_birthday, b_nationality, traveling_purpose, send_type, sign_type, signatory_name, benefit_ssn2, benefit_name2, benefit_relative2, bsend_zip_code2, bsend_city2, bsend_country2, bsend_addr2, bphone2, bcellur_phoneno2, b_birthday2, b_nationality2, benefit_ssn3, benefit_name3, benefit_relative3, bsend_zip_code3, bsend_city3, bsend_country3, bsend_addr3, bphone3, bcellur_phoneno3, b_birthday3, b_nationality3, has_other_inj_ins, has_other_med_ins, transportation, transportation_desc, bene_alloc_type, bene_ratio1, bene_priority1, bene_ratio2, bene_priority2, bene_ratio3, bene_priority3, error_code, lia_time
,created_time, modified_by, modified_time, actual_paid_amount, actual_paid_premium, bcellur_phoneno,created_by

FROM TLGCTBCSIT.dbo.ctbc_ta_policy where error_code = '1802099' policy_no like '%PE%'
--where id ='20110'
--order by modified_time DESC,accept_no;
order by id DESC;

--select * FROM TLGCTBCUAT.dbo.ctbc_ta_policy;prpCitemKindService

--UWWAITING
--WAITTING
2020100601ABCDE
SELECT 
*
FROM TLGCTBCUAT.dbo.ctbc_pet_policy where 
policy_status='UWWAITING' 
--policy_no = '180620PEC000356'
--owner_mobile = '0981838359'
order by id DESC
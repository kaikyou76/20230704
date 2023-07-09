UPDATE trn_phone
SET update_status = '1', update_date = NOW()
FROM rel_cucm_user_phone
INNER JOIN trn_user ON trn_user.user_id = rel_cucm_user_phone.user_id
WHERE trn_user.biz_employee_id = '1984568';

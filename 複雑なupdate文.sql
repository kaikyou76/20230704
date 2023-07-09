UPDATE trn_phone
SET update_status = '1', update_date = NOW()
FROM rel_cucm_user_phone
INNER JOIN trn_user ON trn_user.user_id = rel_cucm_user_phone.user_id
WHERE trn_user.biz_employee_id = '1984568';


UPDATE trn_phone
SET update_status = '1', update_date = NOW()
WHERE trn_phone.phone_id IN (
  SELECT rel_cucm_user_phone.phone_id
  FROM rel_cucm_user_phone
  INNER JOIN trn_user ON trn_user.user_id = rel_cucm_user_phone.user_id
  WHERE trn_user.biz_employee_id = '1984568'
);

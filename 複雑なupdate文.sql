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

<select id="selAdditionUserSectionDetail" resultType="Map">
SELECT
    A.user_id,
    E.company_cd,
    IPAD(E.department_cd, 5, '0'),
    E.department_nm,
    O.print_order
FROM
    tmp_integratedid_employee E
    INNER JOIN mst_section M ON E.department_cd = M.section_cd
    INNER JOIN trn_user A ON E.employee_cd = A.biz_employee_id
	INNER JOIN rel_user_section R ON A.user_id = R.user_id
    INNER JOIN tmp_integratedid_organization O ON E.organization_cd = O.Organization_cd
WHERE
    E.company_cd = #{companyCode}
    AND LPAD(E.department_cd, 5, '0') = #{departmentCode}
    AND E.employee_cd = #{employeeCode}
</select>

<select id="selAdditionUserSectionDetail" resultType="Map">
SELECT
    A.user_id,
    E.company_cd,
    LPAD(E.department_cd, 5, '0') AS department_cd,
    E.department_nm,
    O.print_order
FROM
    tmp_integratedid_employee E
    INNER JOIN mst_section M ON E.department_cd = M.section_cd
    INNER JOIN trn_user A ON E.employee_cd = A.biz_employee_id
    INNER JOIN rel_user_section R ON A.user_id = R.user_id
    INNER JOIN tmp_integratedid_organization O ON E.organization_cd = O.Organization_cd
WHERE
    E.company_cd = #{companyCode}
    AND LPAD(E.department_cd, 5, '0') = #{departmentCode}
    AND E.employee_cd = #{employeeCode}
</select>

INSERT INTO biz_shift(user_id, section_id)
SELECT
    '2' AS user_id,
    CAST(LPAD(department_cd, 5, '0') AS INTEGER) AS section_id
FROM
    your_table_name;
	
	
SELECT COUNT(1)
FROM rel_cucm_user_phone
INNER JOIN trn_phone ON rel_cucm_user_phone.phone_id = trn_phone.phone_id
WHERE rel_cucm_user_phone.user_id = CAST(#{appUserId} AS INTEGER)
  AND LPAD(CAST(trn_phone.section_id AS TEXT), 5, '0') = LPAD(#{sectionId}, 5, '0')
  AND delete_flg != '1';


  
SELECT
    trn_user.last_nm,
    trn_user.first_nm,
    REPLACE(trn_user.telephone_no, '_', '') AS telephone_no,
    LOWER(trn_user.last_nm) || '_' || LOWER(trn_user.first_nm) AS full_name
FROM
    trn_user
    INNER JOIN rel_cucm_user_phone ON rel_cucm_user_phone.user_id = trn_user.user_id
    INNER JOIN rel_user_section ON rel_user_section.user_id = trn_user.user_id
WHERE
    rel_cucm_user_phone.delete_flg = '1'
    AND rel_user_section.delete_reserve = '1'
ORDER BY
    trn_user.telephone_no ASC;
 
 
<insert id="insAdditionUserSection" parameterType="Map">
INSERT INTO rel_user_section (
    user_id,
    section_id,
    delete_reserve,
    create_date,
    update_date
)
SELECT
    A.user_id,
    CAST(LPAD(E.department_cd, 5, '0') AS INTEGER) AS section_id,
    'O' AS delete_reserve,
    NOW(),
    NOW()
FROM
    tmp_integratedid_employee E
    INNER JOIN trn_user A ON E.employee_cd = A.biz_employee_id
    INNER JOIN tmp_integratedid_organization O ON E.organization_cd = O.Organization_cd
WHERE
    E.company_cd = #{companyCode}
    AND LPAD(E.department_cd, 5, '0') = #{departmentCode}
    AND E.employee_cd = #{employeeCode}
LIMIT 1;
</insert>




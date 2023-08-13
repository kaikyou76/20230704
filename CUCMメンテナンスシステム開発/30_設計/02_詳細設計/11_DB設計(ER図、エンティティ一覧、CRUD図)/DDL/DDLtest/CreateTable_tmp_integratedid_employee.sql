CREATE TABLE tmp_integratedid_employee (
employee_id  SERIAL, 
organization_cd varchar(19) NOT NULL, 
company_cd varchar(3) NOT NULL, 
department_cd varchar(5) NOT NULL, 
department_nm varchar(60) NOT NULL, 
employee_cd varchar(7) NOT NULL, 
employee_nm_kanji varchar(30) NOT NULL, 
employee_nm_kana varchar(60) NOT NULL, 
executive_post_cd varchar(3) NOT NULL, 
post_lineage_cd varchar(2), 
class varchar(4), 
sex_cd varchar(1) NOT NULL, 
birthday varchar(8) NOT NULL, 
mail_address varchar(60), 
assign_grade varchar(2) NOT NULL, 
class_tel_addressbook varchar(1) NOT NULL, 
class_temporary_transfer varchar(1) NOT NULL, 
mail_address_automade_flg varchar(1) NOT NULL, 
class_data_input varchar(2) NOT NULL, 
update_date timestamp NOT NULL, 
 PRIMARY KEY (employee_id)
);
COMMENT ON TABLE tmp_integratedid_employee IS 'TMP_統合ID_社員情報';
COMMENT ON COLUMN tmp_integratedid_employee.employee_id IS '社員ID';
COMMENT ON COLUMN tmp_integratedid_employee.organization_cd IS '組織コード';
COMMENT ON COLUMN tmp_integratedid_employee.company_cd IS '会社コード';
COMMENT ON COLUMN tmp_integratedid_employee.department_cd IS '店部課コード';
COMMENT ON COLUMN tmp_integratedid_employee.department_nm IS '店部課名';
COMMENT ON COLUMN tmp_integratedid_employee.employee_cd IS '社員コード';
COMMENT ON COLUMN tmp_integratedid_employee.employee_nm_kanji IS '社員氏名';
COMMENT ON COLUMN tmp_integratedid_employee.employee_nm_kana IS '社員氏名カナ';
COMMENT ON COLUMN tmp_integratedid_employee.executive_post_cd IS '役職コード';
COMMENT ON COLUMN tmp_integratedid_employee.post_lineage_cd IS '職系コード';
COMMENT ON COLUMN tmp_integratedid_employee.class IS 'クラス';
COMMENT ON COLUMN tmp_integratedid_employee.sex_cd IS '性別コード';
COMMENT ON COLUMN tmp_integratedid_employee.birthday IS '生年月日';
COMMENT ON COLUMN tmp_integratedid_employee.mail_address IS 'メールアドレス';
COMMENT ON COLUMN tmp_integratedid_employee.assign_grade IS '所属順位';
COMMENT ON COLUMN tmp_integratedid_employee.class_tel_addressbook IS '掲載区分';
COMMENT ON COLUMN tmp_integratedid_employee.class_temporary_transfer IS '出向区分';
COMMENT ON COLUMN tmp_integratedid_employee.mail_address_automade_flg IS 'メール作成フラグ';
COMMENT ON COLUMN tmp_integratedid_employee.class_data_input IS 'データ入力区分';
COMMENT ON COLUMN tmp_integratedid_employee.update_date IS '更新日時';

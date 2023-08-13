CREATE TABLE tmp_integratedid_department (
department_id  SERIAL, 
organization_cd varchar(19) NOT NULL, 
company_cd varchar(3) NOT NULL, 
company_nm varchar(60) NOT NULL, 
control_cd varchar(5) NOT NULL, 
control_nm varchar(60) NOT NULL, 
charge_cd varchar(5) NOT NULL, 
charge_nm varchar(60) NOT NULL, 
parent_department_cd varchar(5) NOT NULL, 
parent_department_nm varchar(60) NOT NULL, 
department_cd varchar(5) NOT NULL, 
department_nm varchar(60) NOT NULL, 
department_nm_en varchar(80), 
zip_cd varchar(7), 
address varchar(100), 
telephone_no varchar(15), 
fax_no varchar(15), 
extension_no varchar(15), 
class_sales varchar(2) NOT NULL, 
class_data_input varchar(2) NOT NULL, 
update_date timestamp NOT NULL, 
 PRIMARY KEY (department_id)
);
COMMENT ON TABLE tmp_integratedid_department IS 'TMP_統合ID_店部課情報';
COMMENT ON COLUMN tmp_integratedid_department.department_id IS '店部課ID';
COMMENT ON COLUMN tmp_integratedid_department.organization_cd IS '組織コード';
COMMENT ON COLUMN tmp_integratedid_department.company_cd IS '会社コード';
COMMENT ON COLUMN tmp_integratedid_department.company_nm IS '会社名';
COMMENT ON COLUMN tmp_integratedid_department.control_cd IS '統轄コード';
COMMENT ON COLUMN tmp_integratedid_department.control_nm IS '統轄名';
COMMENT ON COLUMN tmp_integratedid_department.charge_cd IS '担当コード';
COMMENT ON COLUMN tmp_integratedid_department.charge_nm IS '担当名';
COMMENT ON COLUMN tmp_integratedid_department.parent_department_cd IS '親店部課コード';
COMMENT ON COLUMN tmp_integratedid_department.parent_department_nm IS '親店部課名';
COMMENT ON COLUMN tmp_integratedid_department.department_cd IS '店部課コード';
COMMENT ON COLUMN tmp_integratedid_department.department_nm IS '店部課名';
COMMENT ON COLUMN tmp_integratedid_department.department_nm_en IS '店部課名英語';
COMMENT ON COLUMN tmp_integratedid_department.zip_cd IS '郵便番号';
COMMENT ON COLUMN tmp_integratedid_department.address IS '住所';
COMMENT ON COLUMN tmp_integratedid_department.telephone_no IS '電話番号';
COMMENT ON COLUMN tmp_integratedid_department.fax_no IS 'FAX番号';
COMMENT ON COLUMN tmp_integratedid_department.extension_no IS '内線番号';
COMMENT ON COLUMN tmp_integratedid_department.class_sales IS '営業・非営業区分';
COMMENT ON COLUMN tmp_integratedid_department.class_data_input IS 'データ入力区分';
COMMENT ON COLUMN tmp_integratedid_department.update_date IS '更新日時';

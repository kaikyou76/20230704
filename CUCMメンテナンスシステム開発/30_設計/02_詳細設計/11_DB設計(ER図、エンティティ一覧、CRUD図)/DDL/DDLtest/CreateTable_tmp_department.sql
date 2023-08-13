CREATE TABLE tmp_department (
department_id  SERIAL, 
company_cd varchar(3) NOT NULL, 
department_cd varchar(5) NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (department_id)
);
COMMENT ON TABLE tmp_department IS 'TMP_店部課情報';
COMMENT ON COLUMN tmp_department.department_id IS '店部課ID';
COMMENT ON COLUMN tmp_department.company_cd IS '会社コード';
COMMENT ON COLUMN tmp_department.department_cd IS '店部課コード';
COMMENT ON COLUMN tmp_department.create_date IS '作成日時';
COMMENT ON COLUMN tmp_department.update_date IS '更新日時';

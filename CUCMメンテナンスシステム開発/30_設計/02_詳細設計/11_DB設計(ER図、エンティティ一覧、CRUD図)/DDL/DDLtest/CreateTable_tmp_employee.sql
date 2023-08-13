CREATE TABLE tmp_employee (
employee_id  SERIAL, 
company_cd varchar(3) NOT NULL, 
employee_cd varchar(7) NOT NULL, 
assign_grade varchar(2) NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (employee_id)
);
COMMENT ON TABLE tmp_employee IS 'TMP_社員情報';
COMMENT ON COLUMN tmp_employee.employee_id IS '社員ID';
COMMENT ON COLUMN tmp_employee.company_cd IS '会社コード';
COMMENT ON COLUMN tmp_employee.employee_cd IS '社員コード';
COMMENT ON COLUMN tmp_employee.assign_grade IS '所属順位';
COMMENT ON COLUMN tmp_employee.create_date IS '作成日時';
COMMENT ON COLUMN tmp_employee.update_date IS '更新日時';

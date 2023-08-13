CREATE TABLE mst_section (
section_id  SERIAL, 
company_cd varchar(3) NOT NULL, 
section_cd varchar(5) NOT NULL, 
section_nm varchar(60) NOT NULL, 
parent_section_cd varchar(5) NOT NULL, 
organization_cd varchar(19) NOT NULL, 
print_order varchar(4) NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (section_id)
);
COMMENT ON TABLE mst_section IS 'MST_店部課';
COMMENT ON COLUMN mst_section.section_id IS '店部課ID';
COMMENT ON COLUMN mst_section.company_cd IS '会社コード';
COMMENT ON COLUMN mst_section.section_cd IS '店部課コード';
COMMENT ON COLUMN mst_section.section_nm IS '店部課名';
COMMENT ON COLUMN mst_section.parent_section_cd IS '親店部課コード';
COMMENT ON COLUMN mst_section.organization_cd IS '組織コード';
COMMENT ON COLUMN mst_section.print_order IS 'プリント順';
COMMENT ON COLUMN mst_section.create_date IS '作成日時';
COMMENT ON COLUMN mst_section.update_date IS '更新日時';

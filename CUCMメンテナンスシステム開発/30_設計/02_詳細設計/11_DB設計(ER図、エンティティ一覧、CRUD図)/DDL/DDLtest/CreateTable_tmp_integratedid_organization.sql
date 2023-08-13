CREATE TABLE tmp_integratedid_organization (
organization_id  SERIAL, 
organization_cd varchar(19) NOT NULL, 
organization_nm varchar(60) NOT NULL, 
organization_no varchar(7) NOT NULL, 
organization_abbreviated_nm varchar(15) NOT NULL, 
print_order varchar(4) NOT NULL, 
class_sales varchar(2) NOT NULL, 
class_data_input varchar(2) NOT NULL, 
update_date timestamp NOT NULL, 
 PRIMARY KEY (organization_id)
);
COMMENT ON TABLE tmp_integratedid_organization IS 'TMP_統合ID_組織情報';
COMMENT ON COLUMN tmp_integratedid_organization.organization_id IS '組織ID';
COMMENT ON COLUMN tmp_integratedid_organization.organization_cd IS '組織コード';
COMMENT ON COLUMN tmp_integratedid_organization.organization_nm IS '組織名';
COMMENT ON COLUMN tmp_integratedid_organization.organization_no IS '組織No.';
COMMENT ON COLUMN tmp_integratedid_organization.organization_abbreviated_nm IS '組織名略';
COMMENT ON COLUMN tmp_integratedid_organization.print_order IS 'プリント順';
COMMENT ON COLUMN tmp_integratedid_organization.class_sales IS '営業・非営業区分';
COMMENT ON COLUMN tmp_integratedid_organization.class_data_input IS 'データ入力区分';
COMMENT ON COLUMN tmp_integratedid_organization.update_date IS '更新日時';

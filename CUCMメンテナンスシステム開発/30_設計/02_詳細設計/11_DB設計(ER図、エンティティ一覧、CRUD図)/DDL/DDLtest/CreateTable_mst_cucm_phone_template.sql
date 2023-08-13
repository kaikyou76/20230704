CREATE TABLE mst_cucm_phone_template (
phone_template_id  SERIAL, 
phone_template_nm varchar(100) NOT NULL, 
device_type_id integer NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (phone_template_id)
);
COMMENT ON TABLE mst_cucm_phone_template IS 'MST_CUCM_電話テンプレート';
COMMENT ON COLUMN mst_cucm_phone_template.phone_template_id IS '電話テンプレートID';
COMMENT ON COLUMN mst_cucm_phone_template.phone_template_nm IS '電話テンプレート名';
COMMENT ON COLUMN mst_cucm_phone_template.device_type_id IS 'デバイスタイプID';
COMMENT ON COLUMN mst_cucm_phone_template.create_date IS '作成日時';
COMMENT ON COLUMN mst_cucm_phone_template.update_date IS '更新日時';

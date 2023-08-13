CREATE TABLE mst_cucm_device_type (
device_type_id  SERIAL, 
device_type_nm varchar(25) NOT NULL, 
device_type_no integer, 
device_protocol varchar(4) NOT NULL, 
rel_device_type_no integer, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (device_type_id)
);
COMMENT ON TABLE mst_cucm_device_type IS 'MST_CUCM_デバイスタイプ';
COMMENT ON COLUMN mst_cucm_device_type.device_type_id IS 'デバイスタイプID';
COMMENT ON COLUMN mst_cucm_device_type.device_type_nm IS 'デバイスタイプ名';
COMMENT ON COLUMN mst_cucm_device_type.device_type_no IS 'デバイスタイプ番号';
COMMENT ON COLUMN mst_cucm_device_type.device_protocol IS 'デバイスプロトコル';
COMMENT ON COLUMN mst_cucm_device_type.rel_device_type_no IS '関連デバイスタイプ番号';
COMMENT ON COLUMN mst_cucm_device_type.create_date IS '作成日時';
COMMENT ON COLUMN mst_cucm_device_type.update_date IS '更新日時';

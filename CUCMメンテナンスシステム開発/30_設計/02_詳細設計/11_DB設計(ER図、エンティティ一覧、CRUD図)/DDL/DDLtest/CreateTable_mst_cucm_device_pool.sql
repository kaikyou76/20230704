CREATE TABLE mst_cucm_device_pool (
device_pool_id  SERIAL, 
device_pool_nm varchar(100) NOT NULL, 
cisco_unified_callmanager_group varchar(36) NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (device_pool_id)
);
COMMENT ON TABLE mst_cucm_device_pool IS 'MST_CUCM_デバイスプール';
COMMENT ON COLUMN mst_cucm_device_pool.device_pool_id IS 'デバイスプールID';
COMMENT ON COLUMN mst_cucm_device_pool.device_pool_nm IS 'デバイスプール名';
COMMENT ON COLUMN mst_cucm_device_pool.cisco_unified_callmanager_group IS '呼出管理グループ';
COMMENT ON COLUMN mst_cucm_device_pool.create_date IS '作成日時';
COMMENT ON COLUMN mst_cucm_device_pool.update_date IS '更新日時';

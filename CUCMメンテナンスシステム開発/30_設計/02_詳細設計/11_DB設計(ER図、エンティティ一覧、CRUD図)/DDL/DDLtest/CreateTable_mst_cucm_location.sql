CREATE TABLE mst_cucm_location (
location_id  SERIAL, 
location_nm varchar(8) NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (location_id)
);
COMMENT ON TABLE mst_cucm_location IS 'MST_CUCM_ロケーション';
COMMENT ON COLUMN mst_cucm_location.location_id IS 'ロケーションID';
COMMENT ON COLUMN mst_cucm_location.location_nm IS 'ロケーション名';
COMMENT ON COLUMN mst_cucm_location.create_date IS '作成日時';
COMMENT ON COLUMN mst_cucm_location.update_date IS '更新日時';

CREATE TABLE mst_cucm_pickup_group (
pickup_group_id  SERIAL, 
pickup_group_nm varchar(19) NOT NULL, 
pickup_group_no integer NOT NULL, 
branch_cd varchar(5) NOT NULL, 
section_cd varchar(5) NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (pickup_group_id)
);
COMMENT ON TABLE mst_cucm_pickup_group IS 'MST_CUCM_ピックアップグループ';
COMMENT ON COLUMN mst_cucm_pickup_group.pickup_group_id IS 'ピックアップグループID';
COMMENT ON COLUMN mst_cucm_pickup_group.pickup_group_nm IS 'ピックアップグループ名';
COMMENT ON COLUMN mst_cucm_pickup_group.pickup_group_no IS 'ピックアップグループ番号';
COMMENT ON COLUMN mst_cucm_pickup_group.branch_cd IS '拠点CD';
COMMENT ON COLUMN mst_cucm_pickup_group.section_cd IS '店部課コード';
COMMENT ON COLUMN mst_cucm_pickup_group.create_date IS '作成日時';
COMMENT ON COLUMN mst_cucm_pickup_group.update_date IS '更新日時';

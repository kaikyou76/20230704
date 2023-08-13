CREATE TABLE mst_shared_nm (
shared_id  SERIAL, 
shared_nm varchar(40) NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (shared_id)
);
COMMENT ON TABLE mst_shared_nm IS 'MST_共用名';
COMMENT ON COLUMN mst_shared_nm.shared_id IS '共用名ID';
COMMENT ON COLUMN mst_shared_nm.shared_nm IS '共用名';
COMMENT ON COLUMN mst_shared_nm.create_date IS '作成日時';
COMMENT ON COLUMN mst_shared_nm.update_date IS '更新日時';

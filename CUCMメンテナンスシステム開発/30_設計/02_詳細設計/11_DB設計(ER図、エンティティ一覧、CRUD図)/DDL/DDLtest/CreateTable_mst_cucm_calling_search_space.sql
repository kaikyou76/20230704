CREATE TABLE mst_cucm_calling_search_space (
calling_search_space_id  SERIAL, 
calling_search_space_nm varchar(100) NOT NULL, 
cd1 integer NOT NULL, 
cd2 integer NOT NULL, 
cd3 integer NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (calling_search_space_id)
);
COMMENT ON TABLE mst_cucm_calling_search_space IS 'MST_CUCM_コーリングサーチスペース';
COMMENT ON COLUMN mst_cucm_calling_search_space.calling_search_space_id IS 'コーリングサーチスペースID';
COMMENT ON COLUMN mst_cucm_calling_search_space.calling_search_space_nm IS 'コーリングサーチスペース名';
COMMENT ON COLUMN mst_cucm_calling_search_space.cd1 IS 'コード1';
COMMENT ON COLUMN mst_cucm_calling_search_space.cd2 IS 'コード2';
COMMENT ON COLUMN mst_cucm_calling_search_space.cd3 IS 'コード3';
COMMENT ON COLUMN mst_cucm_calling_search_space.create_date IS '作成日時';
COMMENT ON COLUMN mst_cucm_calling_search_space.update_date IS '更新日時';

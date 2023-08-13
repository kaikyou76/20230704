CREATE TABLE mst_branch (
branch_id  SERIAL, 
branch_cd varchar(5) NOT NULL, 
branch_nm varchar(40) NOT NULL, 
create_user integer NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_user integer NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (branch_id)
);
COMMENT ON TABLE mst_branch IS 'MST_拠点';
COMMENT ON COLUMN mst_branch.branch_id IS '拠点ID';
COMMENT ON COLUMN mst_branch.branch_cd IS '拠点コード';
COMMENT ON COLUMN mst_branch.branch_nm IS '拠点名';
COMMENT ON COLUMN mst_branch.create_user IS '作成ユーザー';
COMMENT ON COLUMN mst_branch.create_date IS '作成日時';
COMMENT ON COLUMN mst_branch.update_user IS '更新ユーザー';
COMMENT ON COLUMN mst_branch.update_date IS '更新日時';

CREATE TABLE tmp_executive (
executive_post_id  SERIAL, 
executive_post_cd varchar(3) NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (executive_post_id)
);
COMMENT ON TABLE tmp_executive IS 'TMP_役職';
COMMENT ON COLUMN tmp_executive.executive_post_id IS '役職ID';
COMMENT ON COLUMN tmp_executive.executive_post_cd IS '役職コード';
COMMENT ON COLUMN tmp_executive.create_date IS '作成日時';
COMMENT ON COLUMN tmp_executive.update_date IS '更新日時';

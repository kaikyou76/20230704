CREATE TABLE tmp_organization (
organization_id  SERIAL, 
organization_cd varchar(19) NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (organization_id,organization_cd)
);
COMMENT ON TABLE tmp_organization IS 'TMP_組織情報';
COMMENT ON COLUMN tmp_organization.organization_id IS '組織ID';
COMMENT ON COLUMN tmp_organization.organization_cd IS '組織コード';
COMMENT ON COLUMN tmp_organization.create_date IS '作成日時';
COMMENT ON COLUMN tmp_organization.update_date IS '更新日時';

CREATE TABLE trn_cuc_association (
cuc_id  SERIAL, 
phone_id integer NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (cuc_id,phone_id)
);
COMMENT ON TABLE trn_cuc_association IS 'TRN_CUC連携';
COMMENT ON COLUMN trn_cuc_association.cuc_id IS 'CUC_ID';
COMMENT ON COLUMN trn_cuc_association.phone_id IS '電話機ID';
COMMENT ON COLUMN trn_cuc_association.create_date IS '作成日時';
COMMENT ON COLUMN trn_cuc_association.update_date IS '更新日時';

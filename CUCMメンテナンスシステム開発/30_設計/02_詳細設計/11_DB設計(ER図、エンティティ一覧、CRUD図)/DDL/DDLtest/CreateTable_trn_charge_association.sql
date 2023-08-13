CREATE TABLE trn_charge_association (
charge_id  SERIAL, 
branch_id integer NOT NULL, 
section_id integer NOT NULL, 
remarks text, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (charge_id)
);
COMMENT ON TABLE trn_charge_association IS 'TRN_課金連携';
COMMENT ON COLUMN trn_charge_association.charge_id IS '課金ID';
COMMENT ON COLUMN trn_charge_association.branch_id IS '拠点ID';
COMMENT ON COLUMN trn_charge_association.section_id IS '店部課ID';
COMMENT ON COLUMN trn_charge_association.remarks IS '備考';
COMMENT ON COLUMN trn_charge_association.create_date IS '作成日時';
COMMENT ON COLUMN trn_charge_association.update_date IS '更新日時';

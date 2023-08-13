CREATE TABLE trn_officelink_fmc (
fmc_id  SERIAL, 
directory_no varchar(8) NOT NULL, 
foma_no varchar(11) NOT NULL, 
sip_id varchar(8), 
sip_pw varchar(8), 
web_cuscom_user_nm varchar(8) NOT NULL, 
web_cuscom_pw text NOT NULL, 
update_status varchar(1) DEFAULT 0 NOT NULL, 
delete_flg varchar(1) DEFAULT 0 NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (fmc_id)
);
COMMENT ON TABLE trn_officelink_fmc IS 'TRN_オフィスリンク_FMC端末情報';
COMMENT ON COLUMN trn_officelink_fmc.fmc_id IS 'FMC ID';
COMMENT ON COLUMN trn_officelink_fmc.directory_no IS '内線番号';
COMMENT ON COLUMN trn_officelink_fmc.foma_no IS 'FOMA番号';
COMMENT ON COLUMN trn_officelink_fmc.sip_id IS 'SIP ID';
COMMENT ON COLUMN trn_officelink_fmc.sip_pw IS 'SIP パスワード';
COMMENT ON COLUMN trn_officelink_fmc.web_cuscom_user_nm IS 'Web カスコンユーザ名';
COMMENT ON COLUMN trn_officelink_fmc.web_cuscom_pw IS 'Web カスコン パスワード';
COMMENT ON COLUMN trn_officelink_fmc.update_status IS '更新ステータス';
COMMENT ON COLUMN trn_officelink_fmc.delete_flg IS '削除フラグ';
COMMENT ON COLUMN trn_officelink_fmc.create_date IS '作成日時';
COMMENT ON COLUMN trn_officelink_fmc.update_date IS '更新日時';

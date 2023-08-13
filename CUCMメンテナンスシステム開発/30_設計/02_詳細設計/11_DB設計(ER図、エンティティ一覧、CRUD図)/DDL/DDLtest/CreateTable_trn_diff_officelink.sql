CREATE TABLE trn_diff_officelink (
diff_officelink_id  SERIAL, 
office_link_directory_no varchar(8), 
office_link_foma_no varchar(11), 
office_link_sip_id varchar(8), 
office_link_sip_pw varchar(8), 
office_link_web_cuscom_user_nm varchar(8), 
office_link_web_cuscom_pw text, 
app_directory_no varchar(8), 
app_foma_no varchar(11), 
app_sip_id varchar(8), 
app_sip_pw varchar(8), 
app_web_cuscom_user_nm varchar(8), 
app_web_cuscom_pw text, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (diff_officelink_id)
);
COMMENT ON TABLE trn_diff_officelink IS 'TRN_オフィスリンク差分';
COMMENT ON COLUMN trn_diff_officelink.diff_officelink_id IS 'オフィスリンク差分ID';
COMMENT ON COLUMN trn_diff_officelink.office_link_directory_no IS '内線番号（オフィスリンク）';
COMMENT ON COLUMN trn_diff_officelink.office_link_foma_no IS 'FOMA番号（オフィスリンク）';
COMMENT ON COLUMN trn_diff_officelink.office_link_sip_id IS 'SIP ID（オフィスリンク）';
COMMENT ON COLUMN trn_diff_officelink.office_link_sip_pw IS 'SIP パスワード（オフィスリンク）';
COMMENT ON COLUMN trn_diff_officelink.office_link_web_cuscom_user_nm IS 'Web カスコンユーザ名（オフィスリンク）';
COMMENT ON COLUMN trn_diff_officelink.office_link_web_cuscom_pw IS 'Web カスコン パスワード（オフィスリンク）';
COMMENT ON COLUMN trn_diff_officelink.app_directory_no IS '内線番号（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_officelink.app_foma_no IS 'FOMA番号（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_officelink.app_sip_id IS 'SIP ID（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_officelink.app_sip_pw IS 'SIP パスワード（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_officelink.app_web_cuscom_user_nm IS 'Web カスコンユーザ名（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_officelink.app_web_cuscom_pw IS 'Web カスコン パスワード（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_officelink.create_date IS '作成日時';
COMMENT ON COLUMN trn_diff_officelink.update_date IS '更新日時';

CREATE TABLE mst_cucm_voice_mail_profile (
voice_mail_profile_id  SERIAL, 
voice_mail_profile_nm varchar(100) NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (voice_mail_profile_id)
);
COMMENT ON TABLE mst_cucm_voice_mail_profile IS 'MST_CUCM_ボイスメールプロファイル';
COMMENT ON COLUMN mst_cucm_voice_mail_profile.voice_mail_profile_id IS 'ボイスメールプロファイルID';
COMMENT ON COLUMN mst_cucm_voice_mail_profile.voice_mail_profile_nm IS 'ボイスメールプロファイル名';
COMMENT ON COLUMN mst_cucm_voice_mail_profile.create_date IS '作成日時';
COMMENT ON COLUMN mst_cucm_voice_mail_profile.update_date IS '更新日時';

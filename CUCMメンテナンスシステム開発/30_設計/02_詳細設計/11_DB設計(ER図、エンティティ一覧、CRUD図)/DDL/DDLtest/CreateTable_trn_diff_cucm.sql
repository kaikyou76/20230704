CREATE TABLE trn_diff_cucm (
diff_cucm_id  SERIAL, 
cucm_mac_address varchar(13), 
cucm_directory_no varchar(8), 
cucm_calling_search_space_nm varchar(100), 
cucm_location_nm varchar(8), 
cucm_device_pool_nm varchar(100), 
cucm_phone_button_template_nm varchar(100), 
cucm_addon_module_nm_1 varchar(100), 
cucm_addon_module_nm_2 varchar(100), 
cucm_fwd_all_css varchar(100), 
cucm_fwd_busy_css varchar(100), 
cucm_fwd_busy_destination varchar(100), 
cucm_fwd_noans_css varchar(100), 
cucm_fwd_noans_destination varchar(100), 
cucm_call_pickup_group_nm varchar(100), 
cucm_voice_mail_profile_nm varchar(100), 
cucm_no integer, 
cucm_external_phone_no_mask varchar(100), 
cucm_line_txt_label varchar(10), 
cucm_ring_setting_nm varchar(18), 
app_mac_address varchar(13), 
app_directory_no varchar(8), 
app_calling_search_space_nm varchar(100), 
app_location_nm varchar(8), 
app_device_pool_nm varchar(100), 
app_phone_button_template_nm varchar(100), 
app_addon_module_nm_1 varchar(100), 
app_addon_module_nm_2 varchar(100), 
app_fwd_all_css varchar(100), 
app_fwd_busy_css varchar(100), 
app_fwd_busy_destination varchar(100), 
app_fwd_noans_css varchar(100), 
app_fwd_noans_destination varchar(100), 
app_call_pickup_group_nm varchar(100), 
app_voice_mail_profile_nm varchar(100), 
app_no integer, 
app_external_phone_no_mask varchar(100), 
app_line_txt_label varchar(10), 
app_ring_setting_nm varchar(18), 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (diff_cucm_id)
);
COMMENT ON TABLE trn_diff_cucm IS 'TRN_CUCM差分';
COMMENT ON COLUMN trn_diff_cucm.diff_cucm_id IS 'CUCM差分ID';
COMMENT ON COLUMN trn_diff_cucm.cucm_mac_address IS 'MACアドレス（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_directory_no IS '内線番号（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_calling_search_space_nm IS 'コーリングサーチスペース名（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_location_nm IS 'ロケーション名（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_device_pool_nm IS 'デバイスプール名（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_phone_button_template_nm IS 'ボタンテンプレート名（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_addon_module_nm_1 IS '拡張モジュール１（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_addon_module_nm_2 IS '拡張モジュール２（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_fwd_all_css IS '全転送CSS（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_fwd_busy_css IS '話中転送先（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_fwd_busy_destination IS '話中転送先CSS（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_fwd_noans_css IS '不応答転送先（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_fwd_noans_destination IS '不応答転送先CSS（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_call_pickup_group_nm IS 'ピックアップグループ名（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_voice_mail_profile_nm IS 'ボイスメールプロファイル名（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_no IS '連番（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_external_phone_no_mask IS '外線通知番号（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_line_txt_label IS 'テキストラベル（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.cucm_ring_setting_nm IS '鳴動設定名（CUCM）';
COMMENT ON COLUMN trn_diff_cucm.app_mac_address IS 'MACアドレス（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_directory_no IS '内線番号（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_calling_search_space_nm IS 'コーリングサーチスペース名（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_location_nm IS 'ロケーション名（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_device_pool_nm IS 'デバイスプール名（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_phone_button_template_nm IS 'ボタンテンプレート名（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_addon_module_nm_1 IS '拡張モジュール１（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_addon_module_nm_2 IS '拡張モジュール２（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_fwd_all_css IS '全転送CSS（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_fwd_busy_css IS '話中転送先（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_fwd_busy_destination IS '話中転送先CSS（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_fwd_noans_css IS '不応答転送先（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_fwd_noans_destination IS '不応答転送先CSS（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_call_pickup_group_nm IS 'ピックアップグループ名（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_voice_mail_profile_nm IS 'ボイスメールプロファイル名（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_no IS '連番（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_external_phone_no_mask IS '外線通知番号（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_line_txt_label IS 'テキストラベル（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.app_ring_setting_nm IS '鳴動設定名（メンテナンスシステム）';
COMMENT ON COLUMN trn_diff_cucm.create_date IS '作成日時';
COMMENT ON COLUMN trn_diff_cucm.update_date IS '更新日時';

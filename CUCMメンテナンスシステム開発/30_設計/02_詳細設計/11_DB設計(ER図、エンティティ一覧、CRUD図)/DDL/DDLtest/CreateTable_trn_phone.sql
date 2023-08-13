CREATE TABLE trn_phone (
phone_id  SERIAL, 
branch_id integer NOT NULL, 
section_id integer NOT NULL, 
device_pool_id integer NOT NULL, 
phone_template_id integer NOT NULL, 
calling_search_space_id integer NOT NULL, 
location_id integer NOT NULL, 
device_type_id integer NOT NULL, 
owner_user_id integer, 
device_nm varchar(25) NOT NULL, 
user_locale varchar(50), 
built_in_bridge varchar(7), 
privacy varchar(7), 
addon_module_id_1 integer, 
addon_module_id_2 integer, 
addon_module_id_3 integer, 
speed_dial json NOT NULL, 
remarks text, 
update_status varchar(1) DEFAULT 0 NOT NULL, 
delete_flg varchar(1) DEFAULT 0 NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (phone_id)
);
COMMENT ON TABLE trn_phone IS 'TRN_電話機';
COMMENT ON COLUMN trn_phone.phone_id IS '電話ID';
COMMENT ON COLUMN trn_phone.branch_id IS '拠点ID';
COMMENT ON COLUMN trn_phone.section_id IS '店部課ID';
COMMENT ON COLUMN trn_phone.device_pool_id IS 'デバイスプールID';
COMMENT ON COLUMN trn_phone.phone_template_id IS '電話テンプレートID';
COMMENT ON COLUMN trn_phone.calling_search_space_id IS 'コーリングサーチスペースID';
COMMENT ON COLUMN trn_phone.location_id IS 'ロケーションID';
COMMENT ON COLUMN trn_phone.device_type_id IS 'デバイスタイプID';
COMMENT ON COLUMN trn_phone.owner_user_id IS 'オーナーユーザーID';
COMMENT ON COLUMN trn_phone.device_nm IS 'デバイス名';
COMMENT ON COLUMN trn_phone.user_locale IS 'ユーザーロケール';
COMMENT ON COLUMN trn_phone.built_in_bridge IS 'ビルトインブリッジ';
COMMENT ON COLUMN trn_phone.privacy IS 'プライバシー';
COMMENT ON COLUMN trn_phone.addon_module_id_1 IS '拡張モジュール１';
COMMENT ON COLUMN trn_phone.addon_module_id_2 IS '拡張モジュール２';
COMMENT ON COLUMN trn_phone.addon_module_id_3 IS '拡張モジュール３';
COMMENT ON COLUMN trn_phone.speed_dial IS 'スピードダイアル';
COMMENT ON COLUMN trn_phone.remarks IS '備考';
COMMENT ON COLUMN trn_phone.update_status IS '更新ステータス';
COMMENT ON COLUMN trn_phone.delete_flg IS '削除フラグ';
COMMENT ON COLUMN trn_phone.create_date IS '作成日時';
COMMENT ON COLUMN trn_phone.update_date IS '更新日時';

CREATE TABLE trn_line (
line_id  SERIAL, 
fmc_id integer, 
charge_id integer NOT NULL, 
voice_mail_profile_id integer, 
pickup_group_id integer, 
directory_no varchar(8) NOT NULL, 
fwd_all_destination varchar(100), 
fwd_all_css integer, 
fwd_busy_destination varchar(100), 
fwd_busy_css integer, 
fwd_noans_destination varchar(100), 
fwd_noans_css integer, 
fwd_noans_ring_duration integer NOT NULL, 
maximum_no_of_calls integer NOT NULL, 
busy_trigger integer NOT NULL, 
calling_party_transformation_mask varchar(10), 
gw_repletion_special_no varchar(3), 
voice_logger varchar(1) DEFAULT 0 NOT NULL, 
representative_pickup integer DEFAULT 0 NOT NULL, 
update_status varchar(1) DEFAULT 0 NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (line_id)
);
COMMENT ON TABLE trn_line IS 'TRN_ライン';
COMMENT ON COLUMN trn_line.line_id IS 'ラインID';
COMMENT ON COLUMN trn_line.fmc_id IS 'FMC ID';
COMMENT ON COLUMN trn_line.charge_id IS '課金ID';
COMMENT ON COLUMN trn_line.voice_mail_profile_id IS 'ボイスメールプロファイルID';
COMMENT ON COLUMN trn_line.pickup_group_id IS 'ピックアップグループID';
COMMENT ON COLUMN trn_line.directory_no IS '内線番号';
COMMENT ON COLUMN trn_line.fwd_all_destination IS '全転送';
COMMENT ON COLUMN trn_line.fwd_all_css IS '全転送CSS';
COMMENT ON COLUMN trn_line.fwd_busy_destination IS '話中転送先';
COMMENT ON COLUMN trn_line.fwd_busy_css IS '話中転送先CSS';
COMMENT ON COLUMN trn_line.fwd_noans_destination IS '不応答転送先';
COMMENT ON COLUMN trn_line.fwd_noans_css IS '不応答転送先CSS';
COMMENT ON COLUMN trn_line.fwd_noans_ring_duration IS '不応答転送待ち時間';
COMMENT ON COLUMN trn_line.maximum_no_of_calls IS '最大コール数';
COMMENT ON COLUMN trn_line.busy_trigger IS 'ビジートリガー';
COMMENT ON COLUMN trn_line.calling_party_transformation_mask IS '発呼側トランスフォーメーションマスク';
COMMENT ON COLUMN trn_line.gw_repletion_special_no IS 'GW補足特番';
COMMENT ON COLUMN trn_line.voice_logger IS '通録設定';
COMMENT ON COLUMN trn_line.representative_pickup IS '代表ピックアップ設定';
COMMENT ON COLUMN trn_line.update_status IS '更新ステータス';
COMMENT ON COLUMN trn_line.create_date IS '作成日時';
COMMENT ON COLUMN trn_line.update_date IS '更新日時';

CREATE TABLE rel_cucm_phone_line (
phone_id integer NOT NULL, 
line_id integer NOT NULL, 
no integer NOT NULL, 
line_txt_label varchar(10), 
external_phone_no_mask varchar(100), 
ring_setting_nm varchar(18), 
dialin varchar(24), 
remarks text, 
delete_flg varchar(1) DEFAULT 0 NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (phone_id,line_id,no)
);
COMMENT ON TABLE rel_cucm_phone_line IS 'REL_電話機-ライン';
COMMENT ON COLUMN rel_cucm_phone_line.phone_id IS '電話ID';
COMMENT ON COLUMN rel_cucm_phone_line.line_id IS 'LINEID';
COMMENT ON COLUMN rel_cucm_phone_line.no IS '連番';
COMMENT ON COLUMN rel_cucm_phone_line.line_txt_label IS 'テキストラベル';
COMMENT ON COLUMN rel_cucm_phone_line.external_phone_no_mask IS '外線通知番号';
COMMENT ON COLUMN rel_cucm_phone_line.ring_setting_nm IS '鳴動設定名';
COMMENT ON COLUMN rel_cucm_phone_line.dialin IS 'ダイアルイン';
COMMENT ON COLUMN rel_cucm_phone_line.remarks IS '備考';
COMMENT ON COLUMN rel_cucm_phone_line.delete_flg IS '削除フラグ';
COMMENT ON COLUMN rel_cucm_phone_line.create_date IS '作成日時';
COMMENT ON COLUMN rel_cucm_phone_line.update_date IS '更新日時';

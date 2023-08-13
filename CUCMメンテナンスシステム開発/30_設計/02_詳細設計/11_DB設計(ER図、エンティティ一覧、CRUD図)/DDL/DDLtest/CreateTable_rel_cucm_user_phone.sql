CREATE TABLE rel_cucm_user_phone (
user_id integer NOT NULL, 
phone_id integer NOT NULL, 
delete_flg varchar(1) DEFAULT 0 NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (user_id,phone_id)
);
COMMENT ON TABLE rel_cucm_user_phone IS 'REL_ユーザ-電話機';
COMMENT ON COLUMN rel_cucm_user_phone.user_id IS 'ユーザーID';
COMMENT ON COLUMN rel_cucm_user_phone.phone_id IS '電話機ID';
COMMENT ON COLUMN rel_cucm_user_phone.delete_flg IS '削除フラグ';
COMMENT ON COLUMN rel_cucm_user_phone.create_date IS '作成日時';
COMMENT ON COLUMN rel_cucm_user_phone.update_date IS '更新日時';

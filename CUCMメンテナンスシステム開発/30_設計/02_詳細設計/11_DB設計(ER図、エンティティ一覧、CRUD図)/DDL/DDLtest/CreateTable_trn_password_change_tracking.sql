CREATE TABLE trn_password_change_tracking (
tracking_id  SERIAL, 
user_id integer NOT NULL, 
no integer NOT NULL, 
before_pw text NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (tracking_id,user_id,no)
);
COMMENT ON TABLE trn_password_change_tracking IS 'TRN_パスワード変更履歴';
COMMENT ON COLUMN trn_password_change_tracking.tracking_id IS '履歴ID';
COMMENT ON COLUMN trn_password_change_tracking.user_id IS 'ユーザーID';
COMMENT ON COLUMN trn_password_change_tracking.no IS '連番';
COMMENT ON COLUMN trn_password_change_tracking.before_pw IS '前回パスワード';
COMMENT ON COLUMN trn_password_change_tracking.create_date IS '作成日時';
COMMENT ON COLUMN trn_password_change_tracking.update_date IS '更新日時';

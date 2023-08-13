CREATE TABLE trn_user (
user_id  SERIAL, 
user_role varchar(1) DEFAULT 0 NOT NULL, 
shared_id integer, 
enabled_shared_use varchar(1) NOT NULL, 
fulltime_employee varchar(1) NOT NULL, 
biz_employee_id varchar(7) NOT NULL, 
login_id varchar(7) NOT NULL, 
login_pw text NOT NULL, 
cucm_login_id varchar(7) NOT NULL, 
user_nm_kanji varchar(20), 
user_nm_kana varchar(40), 
birthday varchar(8), 
last_nm varchar(128) NOT NULL, 
first_nm varchar(128) NOT NULL, 
pin varchar(5) NOT NULL, 
telephone_no varchar(15), 
enable_cti_application_use varchar(1), 
manager_user_id text, 
department text, 
last_pw_update timestamp NOT NULL, 
authenticate_failure_num integer DEFAULT 0 NOT NULL, 
account_lock varchar(1) DEFAULT 0 NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (user_id)
);
COMMENT ON TABLE trn_user IS 'TRN_ユーザー';
COMMENT ON COLUMN trn_user.user_id IS 'ユーザーID';
COMMENT ON COLUMN trn_user.user_role IS 'ユーザー権限';
COMMENT ON COLUMN trn_user.shared_id IS '共用化ID';
COMMENT ON COLUMN trn_user.enabled_shared_use IS '共用ユーザー区分';
COMMENT ON COLUMN trn_user.fulltime_employee IS '正社員区分';
COMMENT ON COLUMN trn_user.biz_employee_id IS '人事情報側ユーザー区分';
COMMENT ON COLUMN trn_user.login_id IS 'ログインID';
COMMENT ON COLUMN trn_user.login_pw IS 'ログインパスワード';
COMMENT ON COLUMN trn_user.cucm_login_id IS 'CUCMログインID';
COMMENT ON COLUMN trn_user.user_nm_kanji IS 'ユーザー名・漢字';
COMMENT ON COLUMN trn_user.user_nm_kana IS 'ユーザー名・カナ';
COMMENT ON COLUMN trn_user.birthday IS '生年月日';
COMMENT ON COLUMN trn_user.last_nm IS 'ユーザー名・姓(CUCM)';
COMMENT ON COLUMN trn_user.first_nm IS 'ユーザー名・名(CUCM)';
COMMENT ON COLUMN trn_user.pin IS 'PIN(CUCM)';
COMMENT ON COLUMN trn_user.telephone_no IS '電話番号(CUCM)';
COMMENT ON COLUMN trn_user.enable_cti_application_use IS 'CTIアプリ利用フラグ(CUCM)';
COMMENT ON COLUMN trn_user.manager_user_id IS 'マネージャID(CUCM)';
COMMENT ON COLUMN trn_user.department IS '部署名(CUCM)';
COMMENT ON COLUMN trn_user.last_pw_update IS '最終パスワード変更日時';
COMMENT ON COLUMN trn_user.authenticate_failure_num IS '認証失敗回数';
COMMENT ON COLUMN trn_user.account_lock IS 'アカウントロック';
COMMENT ON COLUMN trn_user.create_date IS '作成日時';
COMMENT ON COLUMN trn_user.update_date IS '更新日時';

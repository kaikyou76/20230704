CREATE TABLE tmp_ad (
ad_id  SERIAL, 
login_nm varchar(7) NOT NULL, 
disp_nm varchar(128), 
last_nm varchar(128) NOT NULL, 
first_nm varchar(128) NOT NULL, 
mail varchar(256), 
position  NOT NULL, 
 PRIMARY KEY (ad_id)
);
COMMENT ON TABLE tmp_ad IS 'TMP_AD情報';
COMMENT ON COLUMN tmp_ad.ad_id IS 'AD_ID';
COMMENT ON COLUMN tmp_ad.login_nm IS 'ログイン名';
COMMENT ON COLUMN tmp_ad.disp_nm IS '英語姓名';
COMMENT ON COLUMN tmp_ad.last_nm IS '英語姓';
COMMENT ON COLUMN tmp_ad.first_nm IS '英語名';
COMMENT ON COLUMN tmp_ad.mail IS 'メールアドレス';
COMMENT ON COLUMN tmp_ad.position IS '社員区分';

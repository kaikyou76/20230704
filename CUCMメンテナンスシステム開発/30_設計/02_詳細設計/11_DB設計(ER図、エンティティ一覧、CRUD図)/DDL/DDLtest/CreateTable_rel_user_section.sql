CREATE TABLE rel_user_section (
user_id integer NOT NULL, 
section_id integer NOT NULL, 
delete_reserve varchar(1) DEFAULT 0 NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (user_id,section_id)
);
COMMENT ON TABLE rel_user_section IS 'REL_ユーザー-店部課';
COMMENT ON COLUMN rel_user_section.user_id IS 'ユーザーID';
COMMENT ON COLUMN rel_user_section.section_id IS '店部課ID';
COMMENT ON COLUMN rel_user_section.delete_reserve IS '削除予約';
COMMENT ON COLUMN rel_user_section.create_date IS '作成日時';
COMMENT ON COLUMN rel_user_section.update_date IS '更新日時';

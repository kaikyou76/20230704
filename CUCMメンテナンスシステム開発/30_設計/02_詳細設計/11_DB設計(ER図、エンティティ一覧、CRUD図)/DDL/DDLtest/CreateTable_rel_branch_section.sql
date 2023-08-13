CREATE TABLE rel_branch_section (
branch_id integer NOT NULL, 
section_id integer NOT NULL, 
create_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
update_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 PRIMARY KEY (branch_id,section_id)
);
COMMENT ON TABLE rel_branch_section IS 'REL_拠点-店部課';
COMMENT ON COLUMN rel_branch_section.branch_id IS '拠点ID';
COMMENT ON COLUMN rel_branch_section.section_id IS '店部課ID';
COMMENT ON COLUMN rel_branch_section.create_date IS '作成日時';
COMMENT ON COLUMN rel_branch_section.update_date IS '更新日時';

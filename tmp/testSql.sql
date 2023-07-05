
/*除外拠点ダミー情報*/
INSERT INTO tmp_organization (organization_cd, create_date, update_date) VALUES
('Org001', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('Org002', '2023-07-02 12:30:00.393544', '2023-07-02 12:30:00.393544'),
('Org003', '2023-07-03 15:45:00.393544', '2023-07-03 15:45:00.393544'),
('Org004', '2023-07-04 09:15:00.393544', '2023-07-04 09:15:00.393544'),
('Org005', '2023-07-05 14:20:00.393544', '2023-07-05 14:20:00.393544'),
('Org006', '2023-07-06 11:30:00.393544', '2023-07-06 11:30:00.393544');


/*除外店部課ダミー情報*/
INSERT INTO tmp_department (company_cd, department_cd, create_date, update_date) VALUES
('ABC', 'DEPT1', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('ABC', 'DEPT2', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('DEF', 'DEPT3', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('DEF', 'DEPT4', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('GHI', 'DEPT5', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('GHI', 'DEPT6', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544');


/*除外社員ダミー情報*/
INSERT INTO tmp_employee (company_cd, employee_cd, assign_grade, create_date, update_date) VALUES
('ABC', 'EMP001', 'A1', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('ABC', 'EMP002', 'A2', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('DEF', 'EMP003', 'B1', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('DEF', 'EMP004', 'B2', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('GHI', 'EMP005', 'C1', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('GHI', 'EMP006', 'C2', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544');

/*除外役職ダミー情報*/
INSERT INTO tmp_executive (executive_post_cd, create_date, update_date) VALUES
('EXE001', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('EXE002', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('EXE003', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544');

/*trn_user情報*/
INSERT INTO trn_user (user_role, shared_id, enabled_shared_use, fulltime_employee, biz_employee_id, login_id, login_pw, cucm_login_id, user_nm_kanji, user_nm_kana, birthday, last_nm, first_nm, pin, telephone_no, enable_cti_application_use, manager_user_id, department, last_pw_update, authenticate_failure_num, account_lock, create_date, update_date)
VALUES
('O', NULL, 'Y', 'Y', 'EMP001', 'user001', 'password001', 'cucm001', '田中太郎', 'たなかたろう', '19900101', '田中', '太郎', '12345', '0123456789', 'Y', NULL, '部署A', '2023-07-01 10:00:00.393544', 0, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('O', NULL, 'Y', 'Y', 'EMP002', 'user002', 'password002', 'cucm002', '山田花子', 'やまだはなこ', '19900202', '山田', '花子', '23456', '0123456789', 'Y', NULL, '部署B', '2023-07-01 10:00:00.393544', 0, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('O', NULL, 'Y', 'Y', 'EMP003', 'user003', 'password003', 'cucm003', '佐藤健太', 'さとうけんた', '19900303', '佐藤', '健太', '34567', '0123456789', 'Y', NULL, '部署C', '2023-07-01 10:00:00.393544', 0, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('O', NULL, 'Y', 'Y', 'EMP004', 'user004', 'password004', 'cucm004', '鈴木雅子', 'すずきまさこ', '19900404', '鈴木', '雅子', '45678', '0123456789', 'Y', NULL, '部署D', '2023-07-01 10:00:00.393544', 0, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('O', NULL, 'Y', 'Y', 'EMP005', 'user005', 'password005', 'cucm005', '木村裕子', 'きむらゆうこ', '19900505', '木村', '裕子', '56789', '0123456789', 'Y', NULL, '部署E', '2023-07-01 10:00:00.393544', 0, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('O', NULL, 'Y', 'Y', 'EMP006', 'user006', 'password006', 'cucm006', '伊藤悠斗', 'いとうゆうと', '19900606', '伊藤', '悠斗', '67890', '0123456789', 'Y', NULL, '部署F', '2023-07-01 10:00:00.393544', 0, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('O', NULL, 'Y', 'Y', 'EMP007', 'user007', 'password007', 'cucm007', '渡辺美香', 'わたなべみか', '19900707', '渡辺', '美香', '78901', '0123456789', 'Y', NULL, '部署G', '2023-07-01 10:00:00.393544', 0, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('O', NULL, 'Y', 'Y', 'EMP008', 'user008', 'password008', 'cucm008', '中村健太郎', 'なかむらけんたろう', '19900808', '中村', '健太郎', '89012', '0123456789', 'Y', NULL, '部署H', '2023-07-01 10:00:00.393544', 0, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('O', NULL, 'Y', 'Y', 'EMP009', 'user009', 'password009', 'cucm009', '小林直人', 'こばやしなおと', '19900909', '小林', '直人', '90123', '0123456789', 'Y', NULL, '部署I', '2023-07-01 10:00:00.393544', 0, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
('O', NULL, 'Y', 'Y', 'EMP010', 'user010', 'password010', 'cucm010', '高橋美咲', 'たかはしみさき', '19901010', '高橋', '美咲', '01234', '0123456789', 'Y', NULL, '部署J', '2023-07-01 10:00:00.393544', 0, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544');

/*rel_user_section情報*/
INSERT INTO rel_user_section (user_id, section_id, delete_reserve, create_date, update_date)
VALUES
(1, 1, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(1, 2, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(2, 1, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(2, 3, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(3, 2, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(3, 3, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(4, 1, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(4, 4, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(5, 2, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(5, 4, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544');

/*rel_cucm_user_phone情報*/
INSERT INTO rel_cucm_user_phone (user_id, phone_id, delete_flg, create_date, update_date)
VALUES
(1, 1, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(1, 2, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(2, 1, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(2, 3, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(3, 2, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(3, 3, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(4, 1, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(4, 4, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(5, 2, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(5, 4, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(6, 1, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(6, 5, 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544');

/*trn_phone情報*/
INSERT INTO trn_phone (branch_id, section_id, device_pool_id, phone_template_id, calling_search_space_id, location_id, device_type_id, owner_user_id, device_nm, user_locale, built_in_bridge, privacy, addon_module_id_1, addon_module_id_2, addon_module_id_3, speed_dial_json, remarks, update_status, delete_flg, create_date, update_date)
VALUES
(1, 1, 1, 1, 1, 1, 1, NULL, 'Phone 1', 'English', 'Yes', 'Yes', 1, 2, 3, '{"1": "John"}', 'Remarks 1', 'O', 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(1, 1, 1, 1, 1, 1, 1, NULL, 'Phone 2', 'English', 'Yes', 'Yes', 4, 5, 6, '{"2": "Jane"}', 'Remarks 2', 'O', 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(2, 1, 1, 1, 1, 1, 1, NULL, 'Phone 3', 'English', 'Yes', 'Yes', 7, 8, 9, '{"3": "Mike"}', 'Remarks 3', 'O', 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(2, 1, 1, 1, 1, 1, 1, NULL, 'Phone 4', 'English', 'Yes', 'Yes', 10, 11, 12, '{"4": "Emily"}', 'Remarks 4', 'O', 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(3, 2, 2, 2, 2, 2, 2, NULL, 'Phone 5', 'English', 'Yes', 'Yes', 13, 14, 15, '{"5": "David"}', 'Remarks 5', 'O', 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(3, 2, 2, 2, 2, 2, 2, NULL, 'Phone 6', 'English', 'Yes', 'Yes', 16, 17, 18, '{"6": "Sarah"}', 'Remarks 6', 'O', 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(4, 2, 2, 2, 2, 2, 2, NULL, 'Phone 7', 'English', 'Yes', 'Yes', 19, 20, 21, '{"7": "Alex"}', 'Remarks 7', 'O', 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(4, 3, 3, 3, 3, 3, 3, NULL, 'Phone 8', 'English', 'Yes', 'Yes', 22, 23, 24, '{"8": "Jessica"}', 'Remarks 8', 'O', 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(5, 3, 3, 3, 3, 3, 3, NULL, 'Phone 9', 'English', 'Yes', 'Yes', 25, 26, 27, '{"9": "Michael"}', 'Remarks 9', 'O', 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(5, 3, 3, 3, 3, 3, 3, NULL, 'Phone 10', 'English', 'Yes', 'Yes', 28, 29, 30, '{"10": "Sophia"}', 'Remarks 10', 'O', 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(6, 4, 4, 4, 4, 4, 4, NULL, 'Phone 11', 'English', 'Yes', 'Yes', 31, 32, 33, '{"11": "Daniel"}', 'Remarks 11', 'O', 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(6, 4, 4, 4, 4, 4, 4, NULL, 'Phone 12', 'English', 'Yes', 'Yes', 34, 35, 36, '{"12": "Olivia"}', 'Remarks 12', 'O', 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(7, 4, 4, 4, 4, 4, 4, NULL, 'Phone 13', 'English', 'Yes', 'Yes', 37, 38, 39, '{"13": "Matthew"}', 'Remarks 13', 'O', 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(7, 4, 4, 4, 4, 4, 4, NULL, 'Phone 14', 'English', 'Yes', 'Yes', 40, 41, 42, '{"14": "Emma"}', 'Remarks 14', 'O', 'O', '2023-07-01 10:00:00.393544', '2023-07-01 10:00:00.393544'),
(8, 5, 5, 5, 5, 5, 5, NULL, 'Phone 15', 'English', '







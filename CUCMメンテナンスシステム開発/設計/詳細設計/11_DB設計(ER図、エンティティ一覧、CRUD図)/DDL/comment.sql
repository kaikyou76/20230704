COMMENT ON TABLE mst_branch IS 'MST_拠点';
COMMENT ON COLUMN mst_branch.branch_id IS '拠点ID';
COMMENT ON COLUMN mst_branch.branch_cd IS '拠点コード';
COMMENT ON COLUMN mst_branch.branch_nm IS '拠点名';
COMMENT ON COLUMN mst_branch.create_user IS '作成ユーザー';
COMMENT ON COLUMN mst_branch.create_date IS '作成日時';
COMMENT ON COLUMN mst_branch.update_user IS '更新ユーザー';
COMMENT ON COLUMN mst_branch.update_date IS '更新日時';

COMMENT ON TABLE mst_cucm_calling_search_space IS 'MST_CUCM_コーリングサーチスペース';
COMMENT ON COLUMN mst_cucm_calling_search_space.calling_search_space_id IS 'コーリングサーチスペースID';
COMMENT ON COLUMN mst_cucm_calling_search_space.calling_search_space_nm IS 'コーリングサーチスペース名';
COMMENT ON COLUMN mst_cucm_calling_search_space.cd1 IS 'コード1';
COMMENT ON COLUMN mst_cucm_calling_search_space.cd2 IS 'コード2';
COMMENT ON COLUMN mst_cucm_calling_search_space.cd3 IS 'コード3';
COMMENT ON COLUMN mst_cucm_calling_search_space.create_date IS '作成日時';
COMMENT ON COLUMN mst_cucm_calling_search_space.update_date IS '更新日時';

COMMENT ON TABLE mst_cucm_device_pool IS 'MST_CUCM_デバイスプール';
COMMENT ON COLUMN mst_cucm_device_pool.device_pool_id IS 'デバイスプールID';
COMMENT ON COLUMN mst_cucm_device_pool.device_pool_nm IS 'デバイスプール名';
COMMENT ON COLUMN mst_cucm_device_pool.cisco_unified_callmanager_group IS '呼び出し管理グループ';
COMMENT ON COLUMN mst_cucm_device_pool.create_date IS '作成日時';
COMMENT ON COLUMN mst_cucm_device_pool.update_date IS '更新日時';

COMMENT ON TABLE mst_cucm_device_type IS 'MST_CUCM_デバイスタイプ';

COMMENT ON COLUMN mst_cucm_device_type.device_type_id IS 'デバイスタイプID';

COMMENT ON COLUMN mst_cucm_device_type.device_type_nm IS 'デバイスタイプ名';

COMMENT ON COLUMN mst_cucm_device_type.device_no IS 'デバイスタイプ番号';

COMMENT ON COLUMN mst_cucm_device_type.device_protocol IS 'デバイスタイププロトコル';

COMMENT ON COLUMN mst_cucm_device_type.rel_device_type_no IS '関連デバイスタイプ番号';

COMMENT ON COLUMN mst_cucm_device_type.create_date IS '作成日時';

COMMENT ON COLUMN mst_cucm_device_type.update_date IS '更新日時';


COMMENT ON TABLE mst_cucm_location IS 'MST_CUCM_ロケーション';

COMMENT ON COLUMN mst_cucm_location.location_id IS 'ロケーションID';

COMMENT ON COLUMN mst_cucm_location.location_nm IS 'ロケーション名';

COMMENT ON COLUMN mst_cucm_location.create_date IS '作成日時';

COMMENT ON COLUMN mst_cucm_location.update_date IS '更新日時';


COMMENT ON TABLE mst_cucm_phone_template IS 'MST_CUCM_電話テンプレート';

COMMENT ON COLUMN mst_cucm_phone_template.phone_template_id IS '電話テンプレートID';

COMMENT ON COLUMN mst_cucm_phone_template.phone_template_nm IS '電話テンプレート名';

COMMENT ON COLUMN mst_cucm_phone_template.device_type_id IS 'デバイスタイプID';

COMMENT ON COLUMN mst_cucm_phone_template.create_date IS '作成日時';

COMMENT ON COLUMN mst_cucm_phone_template.update_date IS '更新日時';

COMMENT ON TABLE mst_cucm_pickup_group IS 'MST_CUCM_ピックアップグループ';

COMMENT ON COLUMN mst_cucm_pickup_group.pickup_group_id IS 'ピックアップグループID';

COMMENT ON COLUMN mst_cucm_pickup_group.pickup_group_nm IS 'ピックアップグループ名';

COMMENT ON COLUMN mst_cucm_pickup_group.pickup_group_no IS 'ピックアップグループ番号';

COMMENT ON COLUMN mst_cucm_pickup_group.branch_cd IS '拠点コード';

COMMENT ON COLUMN mst_cucm_pickup_group.section_cd IS '店部課コード';

COMMENT ON COLUMN mst_cucm_pickup_group.create_date IS '作成日時';

COMMENT ON COLUMN mst_cucm_pickup_group.update_date IS '更新日時';


COMMENT ON TABLE mst_cucm_voice_mail_profile IS 'MST_CUCM_ボイスメールプロファイル';

COMMENT ON COLUMN mst_cucm_voice_mail_profile.voice_mail_profile_id IS 'ボイスメールプロファイルID';

COMMENT ON COLUMN mst_cucm_voice_mail_profile.voice_mail_profile_nm IS 'ボイスメールプロファイル名';

COMMENT ON COLUMN mst_cucm_voice_mail_profile.create_date IS '作成日時';

COMMENT ON COLUMN mst_cucm_voice_mail_profile.update_date IS '更新日時';


COMMENT ON TABLE mst_section IS 'MST_店部課';

COMMENT ON COLUMN mst_section.section_id IS '店部課ID';

COMMENT ON COLUMN mst_section.company_cd IS '会社コード';

COMMENT ON COLUMN mst_section.section_cd IS '店部課コード';

COMMENT ON COLUMN mst_section.section_nm IS '店部課名';

COMMENT ON COLUMN mst_section.parent_section_cd IS '親店部課コード';

COMMENT ON COLUMN mst_section.organization_cd IS '組織コード';

COMMENT ON COLUMN mst_section.print_order IS 'プリント順';

COMMENT ON COLUMN mst_section.create_date IS '作成日時';

COMMENT ON COLUMN mst_section.update_date IS '更新日時';

COMMENT ON TABLE mst_shared_nm IS 'MST_共用名';

COMMENT ON COLUMN mst_shared_nm.shared_id IS '共用名ID';

COMMENT ON COLUMN mst_shared_nm.shared_nm IS '共用名';

COMMENT ON COLUMN mst_shared_nm.create_date IS '作成日時';

COMMENT ON COLUMN mst_shared_nm.update_date IS '更新日時';


COMMENT ON TABLE rel_branch_section IS 'REL_拠点-店部課';

COMMENT ON COLUMN rel_branch_section.branch_id IS '拠点ID';

COMMENT ON COLUMN rel_branch_section.section_id IS '店部課ID';

COMMENT ON COLUMN rel_branch_section.create_date IS '作成日時';

COMMENT ON COLUMN rel_branch_section.update_date IS '更新日時';


COMMENT ON TABLE rel_cucm_phone_line IS 'REL_電話機-ライン';

COMMENT ON COLUMN rel_cucm_phone_line.phone_id IS '電話ID';

COMMENT ON COLUMN rel_cucm_phone_line.line_id IS 'LINE ID';

COMMENT ON COLUMN rel_cucm_phone_line.no IS '連番';

COMMENT ON COLUMN rel_cucm_phone_line.line_txt_label IS 'テキストラベル';

COMMENT ON COLUMN rel_cucm_phone_line.external_phone_no_mask IS '外線通知番号';

COMMENT ON COLUMN rel_cucm_phone_line.ring_setting_nm IS '鳴動設定名';

COMMENT ON COLUMN rel_cucm_phone_line.dialin IS 'タイアルイン';

COMMENT ON COLUMN rel_cucm_phone_line.remarks IS '備考';

COMMENT ON COLUMN rel_cucm_phone_line.delete_flg IS '削除フラグ';

COMMENT ON COLUMN rel_cucm_phone_line.create_date IS '作成日時';

COMMENT ON COLUMN rel_cucm_phone_line.update_date IS '更新日時';

COMMENT ON TABLE rel_cucm_user_phone IS 'REL_ユーザー-電話機';

COMMENT ON COLUMN rel_cucm_user_phone.user_id IS 'ユーザーID';

COMMENT ON COLUMN rel_cucm_user_phone.phone_id IS '電話機ID';

COMMENT ON COLUMN rel_cucm_user_phone.delete_flg IS '削除フラグ';

COMMENT ON COLUMN rel_cucm_user_phone.create_date IS '作成日時';

COMMENT ON COLUMN rel_cucm_user_phone.update_date IS '更新日時';


COMMENT ON TABLE rel_user_section IS 'REL_ユーザー-店部課';

COMMENT ON COLUMN rel_user_section.user_id IS 'ユーザーID';

COMMENT ON COLUMN rel_user_section.section_id IS '店部課ID';

COMMENT ON COLUMN rel_user_section.delete_reserve IS '削除予約';

COMMENT ON COLUMN rel_user_section.create_date IS '作成日時';

COMMENT ON COLUMN rel_user_section.update_date IS '更新日時';


COMMENT ON TABLE tmp_ad IS 'TMP_AD情報';

COMMENT ON COLUMN tmp_ad.ad_id IS 'AD_ID';

COMMENT ON COLUMN tmp_ad.login_nm IS 'ログイン名';

COMMENT ON COLUMN tmp_ad.disp_nm IS '英語姓名';

COMMENT ON COLUMN tmp_ad.last_nm IS '英語姓';

COMMENT ON COLUMN tmp_ad.first_nm IS '英語名';

COMMENT ON COLUMN tmp_ad.mail IS 'メールアドレス';

COMMENT ON COLUMN tmp_ad.position IS '社員区分';

COMMENT ON TABLE tmp_department IS 'TMP_店部課情報';

COMMENT ON COLUMN tmp_department.department_id IS '店部課ID';

COMMENT ON COLUMN tmp_department.company_cd IS '会社コード';

COMMENT ON COLUMN tmp_department.department_cd IS '店部課コード';

COMMENT ON COLUMN tmp_department.create_date IS '作成日時';

COMMENT ON COLUMN tmp_department.update_date IS '更新日時';


COMMENT ON TABLE tmp_employee IS 'TMP_社員情報';

COMMENT ON COLUMN tmp_employee.employee_id IS '社員ID';

COMMENT ON COLUMN tmp_employee.company_cd IS '会社コード';

COMMENT ON COLUMN tmp_employee.employee_cd IS '社員コード';

COMMENT ON COLUMN tmp_employee.assign_grade IS '役職';

COMMENT ON COLUMN tmp_employee.create_date IS '作成日時';

COMMENT ON COLUMN tmp_employee.update_date IS '更新日時';


COMMENT ON TABLE tmp_executive IS 'TMP_役職';

COMMENT ON COLUMN tmp_executive.executive_post_id IS '役職ID';

COMMENT ON COLUMN tmp_executive.executive_post_cd IS '役職コード';

COMMENT ON COLUMN tmp_executive.create_date IS '作成日時';

COMMENT ON COLUMN tmp_executive.update_date IS '更新日時';

COMMENT ON TABLE tmp_integratid_department IS 'TMP統合ID_店部課情報';

COMMENT ON COLUMN tmp_integratid_department.department_id IS '店部課ID';

COMMENT ON COLUMN tmp_integratid_department.organization_cd IS '組織コード';

COMMENT ON COLUMN tmp_integratid_department.company_cd IS '会社コード';

COMMENT ON COLUMN tmp_integratid_department.company_nm IS '会社名';

COMMENT ON COLUMN tmp_integratid_department.control_cd IS '統轄コード';

COMMENT ON COLUMN tmp_integratid_department.control_nm IS '統轄名';

COMMENT ON COLUMN tmp_integratid_department.charge_cd IS '担当コード';

COMMENT ON COLUMN tmp_integratid_department.charge_nm IS '担当名';

COMMENT ON COLUMN tmp_integratid_department.parent_department_cd IS '親店部課コード';

COMMENT ON COLUMN tmp_integratid_department.parent_department_nm IS '親店部課名';

COMMENT ON COLUMN tmp_integratid_department.department_cd IS '店部課コード';

COMMENT ON COLUMN tmp_integratid_department.department_nm IS '店部課名';

COMMENT ON COLUMN tmp_integratid_department.department_nm_en IS '店部課名英語';

COMMENT ON COLUMN tmp_integratid_department.zip_cd IS '郵便番号';

COMMENT ON COLUMN tmp_integratid_department.address IS '住所';

COMMENT ON COLUMN tmp_integratid_department.telephone_no IS '電話番号';

COMMENT ON COLUMN tmp_integratid_department.fax_no IS 'FAX番号';

COMMENT ON COLUMN tmp_integratid_department.extension_no IS '内線番号';

COMMENT ON COLUMN tmp_integratid_department.class_sales IS '営業・非営業区分';

COMMENT ON COLUMN tmp_integratid_department.class_data_input IS '更データ入力区分';

COMMENT ON COLUMN tmp_integratid_department.update_date IS '更新日時';


COMMENT ON TABLE tmp_integratidid_employee IS 'TMP統合ID_社員情報'; 

COMMENT ON COLUMN tmp_integratidid_employee.employee_id IS '社員ID'; 

COMMENT ON COLUMN tmp_integratidid_employee.organization_cd IS '組織コード'; 

COMMENT ON COLUMN tmp_integratidid_employee.company_cd IS '会社コード';

COMMENT ON COLUMN tmp_integratidid_employee.department_cd IS '店部課コード';

COMMENT ON COLUMN tmp_integratidid_employee.department_nm IS '店部課名'; 

COMMENT ON COLUMN tmp_integratidid_employee.employee_cd IS '社員コード'; 

COMMENT ON COLUMN tmp_integratidid_employee.employee_nm_kanji IS '社員氏名';

COMMENT ON COLUMN tmp_integratidid_employee.employee_nm_kana IS '社員氏名カナ'; 

COMMENT ON COLUMN tmp_integratidid_employee.executive_post_cd IS '役職コード'; 

COMMENT ON COLUMN tmp_integratidid_employee.post_lineage_cd IS '職系コード'; 

COMMENT ON COLUMN tmp_integratidid_employee.class IS 'クラス'; 

COMMENT ON COLUMN tmp_integratidid_employee.sex_cd IS '性別コード'; 

COMMENT ON COLUMN tmp_integratidid_employee.birthday IS '生年月日';

COMMENT ON COLUMN tmp_integratidid_employee.mail_address IS 'メールアドレス'; 

COMMENT ON COLUMN tmp_integratidid_employee.assign_grade IS '所属順位';

COMMENT ON COLUMN tmp_integratidid_employee.class_tel_addressbook IS '掲載区分'; 

COMMENT ON COLUMN tmp_integratidid_employee.class_temporary_transfer IS '出向区分';

COMMENT ON COLUMN tmp_integratidid_employee.mail_address_automade_flg IS 'メール作成フラグ'; 

COMMENT ON COLUMN tmp_integratidid_employee.class_data_input IS 'データ入力区分'; 

COMMENT ON COLUMN tmp_integratidid_employee.update_date IS '更新日時';

COMMENT ON TABLE tmp_integratidid_organization IS 'TMP統合ID_組織情報';

COMMENT ON COLUMN tmp_integratidid_organization.organization_id IS '組織ID'; 

COMMENT ON COLUMN tmp_integratidid_organization.organization_cd IS '組織コード';

COMMENT ON COLUMN tmp_integratidid_organization.organization_nm IS '組織名';

COMMENT ON COLUMN tmp_integratidid_organization.organization_no IS '組織No';

COMMENT ON COLUMN tmp_integratidid_organization.organization_abbreviated_nm IS '組織名略';		 

COMMENT ON COLUMN tmp_integratidid_organization.print_order IS 'プリント順';

COMMENT ON COLUMN tmp_integratidid_organization.class_sales IS '営業・非営業区分';

COMMENT ON COLUMN tmp_integratidid_organization.class_data_input IS 'データ入力区分'; 

COMMENT ON COLUMN tmp_integratidid_organization.update_date IS '更新日時';


COMMENT ON TABLE tmp_organization IS 'TMP_組織情報';

COMMENT ON COLUMN tmp_organization.organization_id IS '組織ID'; 

COMMENT ON COLUMN tmp_organization.organization_cd IS '組織コード'; 

COMMENT ON COLUMN tmp_organization.create_date IS '作成日時';

COMMENT ON COLUMN tmp_organization.update_date IS '更新日時';


COMMENT ON TABLE trn_charge_association IS 'TRN_課金連携';

COMMENT ON COLUMN trn_charge_association.charge_id IS '課金ID'; 

COMMENT ON COLUMN trn_charge_association.branch_id IS '拠点ID'; 

COMMENT ON COLUMN trn_charge_association.section_id IS '店部課ID'; 

COMMENT ON COLUMN trn_charge_association.remarks IS '備考';

COMMENT ON COLUMN trn_charge_association.create_date IS '作成日時'; 

COMMENT ON COLUMN trn_charge_association.update_date IS '更新日時';

COMMENT ON TABLE trn_cuc_association IS 'TRN_CUC連携';

COMMENT ON COLUMN trn_cuc_association.cuc_id IS 'CUC_ID';

COMMENT ON COLUMN trn_cuc_association.phone_id IS '電話機ID';

COMMENT ON COLUMN trn_cuc_association.create_date IS '作成日時';

COMMENT ON COLUMN trn_cuc_association.update_date IS '更新日時';


COMMENT ON TABLE trn_diff_cucm IS 'TRN_CUCM_差分';

COMMENT ON COLUMN trn_diff_cucm.diff_cucm_id IS 'CUCM_差分';

COMMENT ON COLUMN trn_diff_cucm.cucm_mac_address IS 'MACアドレス(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_directory_no IS '内線番号(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_calling_search_space_nm IS 'コーリングサーチスペース名(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_location_nm IS 'ロケーション名(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_device_pool_nm IS 'デバイスプール名(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_phone_button_template_nm IS 'ボタンテンプレート名(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_addon_module_nm_1 IS '拡張モジュール1(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_addon_module_nm_2 IS '拡張モジュール2(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_fwd_att_css IS '全転送CSS(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_fwd_busy_css IS '話中転送先(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_fwd_busy_destination IS '話中転送先CSS(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_fwd_noans_css IS '不応答転送先(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_fwd_noans_destination IS '不応答転送先CSS(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_call_pickup_group_nm IS 'ピックアップグループ名(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_voice_mail_profile_nm IS 'ボイスメールプロファイル名(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_no IS '連番(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_external_phone_no_mask IS '外線通知番号(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_line_txt_label IS 'テキストラベル(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.cucm_ring_setting_nm IS '鳴動設定名(CUCM)';

COMMENT ON COLUMN trn_diff_cucm.app_mac_address IS 'MACアドレス(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_directory_no IS '内線番号(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_calling_search_space_nm IS 'コーリンクサーチスペース名(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_location_nm IS 'ロケーション名(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_device_pool_nm IS 'デバイスプール名(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_phone_button_template_nm IS 'ボタンテンプレート名(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_addon_module_nm_1 IS '拡張モジュール1(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_addon_module_nm_2 IS '拡張モジュール2(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_fwd_att_css IS '全転送CSS(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_fwd_busy_css IS '話中転送先(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_fwd_busy_destination IS '話中転送先CSS(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_fwd_noans_css IS '不応答転送先(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_fwd_noans_destination IS '不応答転送先CSS(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_call_pickup_group_nm IS 'ピックアップグループ名(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_voice_mail_profile_nm IS 'ボイスメールプロファイル名(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_no IS '連番(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_external_phone_no_mask IS '外線通知番号(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_line_txt_label IS 'テキストラベル(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.app_ring_setting_nm IS '鳴動設定名(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_cucm.create_date IS '作成日時';

COMMENT ON COLUMN trn_diff_cucm.update_date IS '更新日時';

COMMENT ON TABLE trn_diff_officelink IS 'TRN_オフィスリンク差分';

COMMENT ON COLUMN trn_diff_officelink.diff_officelink_id IS 'オフィスリンク差分ID';

COMMENT ON COLUMN trn_diff_officelink.officelink_directory_no IS '内線番号(オフィスリンク)';

COMMENT ON COLUMN trn_diff_officelink.officelink_foma_no IS 'FOMA番号(オフィスリンク)';

COMMENT ON COLUMN trn_diff_officelink.officelink_sip_id IS 'SIP ID(オフィスリンク)';

COMMENT ON COLUMN trn_diff_officelink.officelink_sip_pw IS 'SIPパスワード(オフィスリンク)';

COMMENT ON COLUMN trn_diff_officelink.officelink_web_cuscom_user_nm IS 'Webカスコンユーザー名(オフィスリンク)';

COMMENT ON COLUMN trn_diff_officelink.officelink_web_cuscom_pw IS 'Webカスコンパスワード(オフィスリンク)';

COMMENT ON COLUMN trn_diff_officelink.app_directory_no IS '内線番号(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_officelink.app_foma_no IS 'FOMA番号(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_officelink.app_sip_id IS 'SIP ID(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_officelink.app_sip_pw IS 'SIPパスワード(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_officelink.app_web_cuscom_user_nm IS 'Webカスコンユーザー名(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_officelink.app_web_cuscom_pw IS 'Webカスコンパスワード(メンテナンスシステム)';

COMMENT ON COLUMN trn_diff_officelink.create_date IS '作成日時';

COMMENT ON COLUMN trn_diff_officelink.update_date IS '更新日時';


COMMENT ON TABLE trn_line IS 'TRN_ライン';

COMMENT ON COLUMN trn_line.line_id IS 'ラインID';

COMMENT ON COLUMN trn_line.fmc_id IS 'FMC ID';

COMMENT ON COLUMN trn_line.charge_id IS '課金ID';

COMMENT ON COLUMN trn_line.voice_mail_profile_id IS 'ボイスメールプロファイルID';

COMMENT ON COLUMN trn_line.pickup_group_id IS 'ピックアップグループID';

COMMENT ON COLUMN trn_line.directory_no IS '内線番号';

COMMENT ON COLUMN trn_line.fwd_all_destination IS '全転送先';

COMMENT ON COLUMN trn_line.fwd_all_css IS '全転送CSS';

COMMENT ON COLUMN trn_line.fwd_busy_destination IS '話中転送先';

COMMENT ON COLUMN trn_line.fwd_busy_css IS '話中転送先CSS';

COMMENT ON COLUMN trn_line.fwd_noans_destination IS '不応答転送先';

COMMENT ON COLUMN trn_line.fwd_noans_css IS '不応答転送先CSS';

COMMENT ON COLUMN trn_line.fwd_noans_ring_duration IS '不応答転送先待ち時間';

COMMENT ON COLUMN trn_line.maximum_no_of_calls IS '最大コール数';

COMMENT ON COLUMN trn_line.busy_trigger IS 'ビジートリガー';

COMMENT ON COLUMN trn_line.calling_party_transformation_mask IS '発呼側トランスフォーメーションマスク';

COMMENT ON COLUMN trn_line.gw_repletion_special_no IS 'GW補足特番';

COMMENT ON COLUMN trn_line.voice_logger IS '通話録音設定';

COMMENT ON COLUMN trn_line.representative_pickup IS '代表ピックアップ設定';

COMMENT ON COLUMN trn_line.update_status IS '更新ステータス';

COMMENT ON COLUMN trn_line.create_date IS '作成日時';

COMMENT ON COLUMN trn_line.update_date IS '更新日時';


COMMENT ON TABLE trn_officelink_fmc IS 'TRN_オフィスリンク_FMC端末情報';

COMMENT ON COLUMN trn_officelink_fmc.fmc_id IS 'FMC ID';

COMMENT ON COLUMN trn_officelink_fmc.directory_no IS '内線番号';

COMMENT ON COLUMN trn_officelink_fmc.foma_no IS 'FOMA番号';

COMMENT ON COLUMN trn_officelink_fmc.sip_id IS 'SIP ID';

COMMENT ON COLUMN trn_officelink_fmc.sip_pw IS 'SIP パスワード';

COMMENT ON COLUMN trn_officelink_fmc.web_cuscom_user_nm IS 'Web カスコンユーザー名';

COMMENT ON COLUMN trn_officelink_fmc.web_cuscom_pw IS 'Web カスコンパスワード';

COMMENT ON COLUMN trn_officelink_fmc.update_status IS '更新ステータス';

COMMENT ON COLUMN trn_officelink_fmc.delete_flg IS '削除フラグ';

COMMENT ON COLUMN trn_officelink_fmc.create_date IS '作成日時';

COMMENT ON COLUMN trn_officelink_fmc.update_date IS '更新日時';

COMMENT ON TABLE trn_password_change_tracking IS 'TRN_パスワード変更履歴';

COMMENT ON COLUMN trn_password_change_tracking.tracking_id IS '履歴ID';

COMMENT ON COLUMN trn_password_change_tracking.user_id IS 'ユーザーID';

COMMENT ON COLUMN trn_password_change_tracking.no IS '連番';

COMMENT ON COLUMN trn_password_change_tracking.before_pw IS '前回パスワード';

COMMENT ON COLUMN trn_password_change_tracking.create_date IS '作成日時';

COMMENT ON COLUMN trn_password_change_tracking.update_date IS '更新日時';

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

COMMENT ON COLUMN trn_phone.addon_module_id_1 IS '拡張モジュール1';

COMMENT ON COLUMN trn_phone.addon_module_id_2 IS '拡張モジュール2';

COMMENT ON COLUMN trn_phone.addon_module_id_3 IS '拡張モジュール3';

COMMENT ON COLUMN trn_phone.speed_dial IS 'スピードダイアル';

COMMENT ON COLUMN trn_phone.remarks IS '備考';

COMMENT ON COLUMN trn_phone.update_status IS '更新ステータス';

COMMENT ON COLUMN trn_phone.delete_flg IS '削除フラグ';

COMMENT ON COLUMN trn_phone.create_date IS '作成日時';

COMMENT ON COLUMN trn_phone.update_date IS '更新日時';

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

COMMENT ON COLUMN trn_user.last_nm IS 'ユーザー名・名(CUCM)';

COMMENT ON COLUMN trn_user.first_nm IS 'ユーザー名・姓(CUCM)';

COMMENT ON COLUMN trn_user.pin IS 'PIN(CUCM)';

COMMENT ON COLUMN trn_user.telephone_no IS '電話番号(CUCM)';

COMMENT ON COLUMN trn_user.enable_cti_application_use IS 'CTIアプリ利用フラグ(CUCM)';

COMMENT ON COLUMN trn_user.manager_user_id IS 'マネージャーID(CUCM)';

COMMENT ON COLUMN trn_user.department IS '部署名(CUCM)';

COMMENT ON COLUMN trn_user.last_pw_update IS '最終パスワード変更日時';

COMMENT ON COLUMN trn_user.authenticate_failure_num IS '認証失敗回数';

COMMENT ON COLUMN trn_user.account_lock IS 'アカウントロック';

COMMENT ON COLUMN trn_user.create_date IS '作成日時';

COMMENT ON COLUMN trn_user.update_date IS '更新日時';




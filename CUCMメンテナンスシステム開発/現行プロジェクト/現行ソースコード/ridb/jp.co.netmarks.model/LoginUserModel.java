package jp.co.netmarks.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * <pre>
 * ログインユーザの属性情報を格納するモデルクラス
 *
 * ・spring security Userモデルを継承
 * ・認証状態にてアクセスされるクラス
 *
 * </pre>
 */
public class LoginUserModel extends User {
	private static final long serialVersionUID = 4387960616525114229L;

	/* ユーザーID */
	private BigDecimal userId;
	/* ログインID */
	private String loginId;
	/* ユーザ名：漢字 */
	private String kanjiUserName;
	/* ユーザ名：漢字 */
	private String kanaUserName;
	/* 権限 */
	private String userRole;
	/* パスワード更新日 */
	private Timestamp lastPasswordUpdatetime;

	/**
	 * コンストラクタ
	 */
	public LoginUserModel(
		String username,
		String password,
		Collection<GrantedAuthority> authorities) {

		super(username, password, false, false, false, false, authorities);
	}

	/**
	 * コンストラクタ
	 */
	public LoginUserModel(
		String username,
		String password,
		boolean enabled,
		boolean accountNonExpired,
		boolean credentialsNonExpired,
		boolean accountNonLocked,
		Collection<GrantedAuthority> authorities) {

		super(username, password, enabled, accountNonExpired,
			  credentialsNonExpired, accountNonLocked, authorities);
	}

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return userId
	 */
	public BigDecimal getUserId() {
		return userId;
	}

	/**
	 * @param userId セットする userId
	 */
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	/**
	 * @return loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId セットする loginId
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return kanjiUserName
	 */
	public String getKanjiUserName() {
		return kanjiUserName;
	}

	/**
	 * @param kanjiUserName セットする kanjiUserName
	 */
	public void setKanjiUserName(String kanjiUserName) {
		this.kanjiUserName = kanjiUserName;
	}

	/**
	 * @return kanaUserName
	 */
	public String getKanaUserName() {
		return kanaUserName;
	}

	/**
	 * @param kanaUserName セットする kanaUserName
	 */
	public void setKanaUserName(String kanaUserName) {
		this.kanaUserName = kanaUserName;
	}

	/**
	 * @return userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole セットする userRole
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	/**
	 * @return lastPasswordUpdatetime
	 */
	public Timestamp getLastPasswordUpdatetime() {
		return lastPasswordUpdatetime;
	}

	/**
	 * @param lastPasswordUpdatetime セットする lastPasswordUpdatetime
	 */
	public void setLastPasswordUpdatetime(Timestamp lastPasswordUpdatetime) {
		this.lastPasswordUpdatetime = lastPasswordUpdatetime;
	}

}


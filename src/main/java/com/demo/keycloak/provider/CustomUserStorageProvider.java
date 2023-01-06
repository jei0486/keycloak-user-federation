package com.demo.keycloak.provider;

import com.demo.keycloak.adaptor.CustomUserAdapter;
import com.demo.keycloak.entity.UserEntity;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.*;
import org.keycloak.models.credential.OTPCredentialModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.keycloak.storage.user.UserRegistrationProvider;

import javax.ejb.Local;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 외부 DB 의 데이터를 사용하여 인증, 사용자 관리등을 구현
 * @author osc
 *
 */
//StateFul Bean 으로 선언
@Stateful
//EJB 클라이언트와 동일한 환경에 있을 경우 선언 즉, 여기선 WildFly 에 배포되기 때문에 동일한 환경으로 간주함
@Local(CustomUserStorageProvider.class)			// User Storage SPI 
public class CustomUserStorageProvider implements UserStorageProvider, 
// 사용자 조회
UserLookupProvider, 
// 사용자 조회
UserQueryProvider, 
// 사용자 등록
UserRegistrationProvider, 
// 비밀번호 검사
CredentialInputValidator, 
// 비밀번호 변경 
CredentialInputUpdater  {

	protected ComponentModel model;

	protected KeycloakSession session;

	@PersistenceContext
	protected EntityManager entityManager;

	public CustomUserStorageProvider() {}

	public CustomUserStorageProvider(ComponentModel model, KeycloakSession session) {
		this.model = model;
		this.session = session;
	}

	// 작업이 끝나면 EntityManager 세션 종료등을 한꺼번에 수행하는 메소드
	@Remove
	@Override
	public void close() {}

	@Override
	public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {
		return false;
	}

	/**
	 * 비밀번호 종류(Otp, Password, 등) 을 비활성화
	 */
	@Override
	public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {
	}

	/**
	 * Disabled credential types
	 * 비활성화 된 비밀번호 종류(Otp, Password) 조회, 여기서 OTP 는 사용하지 않을 것이므로 OTP 를 반환
	 * 
	 */
	@Override
	public Set<String> getDisableableCredentialTypes(RealmModel realm, UserModel user) {
		System.out.println("getDisableableCredentialTypes \n\n");
		Set<String> set = new HashSet<>();
		set.add(OTPCredentialModel.TYPE);
		return set;
		//return null;
	}

	/**
	 * Credential Type (password , otp ...)
	 * 지원되는 비밀번호 종류 (Password, Otp, ...) 가 현재 사용한 종류와 맞는지 확인
	 */
	@Override
	public boolean supportsCredentialType(String credentialType) {
		System.out.println("\n\n supportsCredentialType : " + credentialType + "\n\n");
		//return false;
		return PasswordCredentialModel.TYPE.equals(credentialType);
	}

	@Override
	public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
		return false;
	}

	/**
	 * 비밀번호 검사 메소드
	 */
	@Override
	public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {

		System.out.println("isValid \n\n");
		if (!(credentialInput instanceof UserCredentialModel))
			return false;

		if (supportsCredentialType(credentialInput.getType())) {
			UserEntity userInfo = (UserEntity) session.getAttribute("userInfo");

			return org.apache.commons.codec.binary.StringUtils.equals(credentialInput.getChallengeResponse(), userInfo.getPassword());
		} else {
			return false;
		}
	}

	@Override
	public UserModel addUser(RealmModel realm, String username) {
		System.out.println("addUser \n\n");
		return null;
	}

	@Override
	public boolean removeUser(RealmModel realm, UserModel user) {
		System.out.println("removeUser \n\n");
		return false;
	}

	@Override
	public int getUsersCount(RealmModel realm) {
		System.out.println("getUsersCount \n\n");
		return 0;
	}

	@Override
	public List<UserModel> getUsers(RealmModel realm) {
		System.out.println("getUsers , RealmModel realm \n\n");
		return null;
	}

	@Override
	public List<UserModel> getUsers(RealmModel realm, int firstResult, int maxResults) {
		System.out.println("getUsers , RealmModel realm, int firstResult, int maxResults \n\n");
		return null;
	}

	@Override
	public List<UserModel> searchForUser(String search, RealmModel realm) {
		System.out.println("searchForUser , String search, RealmModel realm \n\n");
		return null;
	}

	@Override
	public List<UserModel> searchForUser(String search, RealmModel realm, int firstResult, int maxResults) {
		return null;
	}

	@Override
	public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm) {
		System.out.println("searchForUser , Map<String, String> params, RealmModel realm \n\n");
		System.out.println("params >>>>>>>>>>>>>>>>>>>>>");
		System.out.println(String.valueOf(params));
		System.out.println("RealmModel >>>>>>>>>>>>>>>>>>>>>");
		System.out.println(String.valueOf(realm));
		return null;
	}

	/** 
	 * keycloak admin console User List 조회
	 * 
	 */
	@Override
	public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm, int firstResult,
			int maxResults) {
		System.out.println("searchForUser , Map<String, String> params, RealmModel realm \n\n");
		System.out.println("firstResult : " + firstResult);//0
		System.out.println("maxResults : " + maxResults);//19
		System.out.println("params >>>>>>>>>>>>>>>>>>>>>");
		System.out.println(String.valueOf(params));
		System.out.println("RealmModel >>>>>>>>>>>>>>>>>>>>>");
		System.out.println(String.valueOf(realm));// nsmall@c23ca959
		return null;
	}

	@Override
	public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) {
		System.out.println("getGroupMembers , RealmModel realm, GroupModel group, int firstResult, int maxResults \n\n");
		return null;
	}

	@Override
	public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
		System.out.println("getGroupMembers , RealmModel realm, GroupModel group \n\n");
		return null;
	}

	@Override
	public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) {
		System.out.println("searchForUserByUserAttribute , String attrName, String attrValue, RealmModel realm \n\n");
		return null;
	}

	/**
	 * 사용자가 로그인을 할때 입력된 Id 로 사용자 정보를 조회
	 */
	@Override
	public UserModel getUserById(String keycloakId, RealmModel realm) {

		String userId = StorageId.externalId(keycloakId);
		UserEntity userInfo = entityManager.createNamedQuery("findByUserId",UserEntity.class)
				.setParameter("id", userId)
				.getResultList().stream().findFirst().orElse(null);
		return new CustomUserAdapter(session, realm, model,userInfo);
	}

	/**
	 * 사용자가 로그인을 할때 입력된 username 으로 사용자 정보를 조회
	 */
	@Override
	public UserModel getUserByUsername(String username, RealmModel realm) {

		UserEntity userInfo = entityManager.createNamedQuery("findByUserId", UserEntity.class)
				.setParameter("id", username)
				.getResultList().stream().findFirst().orElse(null);
		return userInfo == null ? null : new CustomUserAdapter(session, realm, model, userInfo);
	}

	/**
	 * Provider 에서 사용되는 Setter
	 */
	public void setModel(ComponentModel model) {
		this.model = model;
	}

	/**
	 * Provider 에서 사용되는 Setter
	 */
	public void setSession(KeycloakSession session) {
		this.session = session;
	}

	@Override
	public UserModel getUserByEmail(String email, RealmModel realm) {
		System.out.println("getUserByEmail  \n\n");
		return null;
	}

}
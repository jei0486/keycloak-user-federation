package com.demo.keycloak.adaptor;

import com.demo.keycloak.entity.UserEntity;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;


// 외부 DB 컬럼과 Keycloak DB 를 매핑시켜주는 어댑터
public class CustomUserAdapter extends AbstractUserAdapterFederatedStorage {

	protected UserEntity userInfo;

	protected String keycloakId;

	public CustomUserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, UserEntity userInfo) {
		super(session, realm, model);
		this.userInfo = userInfo;

		if (userInfo != null) {
			this.keycloakId = StorageId.keycloakId(model, String.valueOf(userInfo.getId()));
			session.setAttribute("userInfo", userInfo);
		}
	}

	@Override
	public String getUsername() {
		return userInfo != null ? userInfo.getName() : null;
	}

	@Override
	public void setUsername(String username) {
		userInfo.setName(username);
	}

	public String getPassword() {
		return this.userInfo.getPassword();
	}

//	public String getEmail() {
//		return this.userInfo.getEmail();
//	}

}

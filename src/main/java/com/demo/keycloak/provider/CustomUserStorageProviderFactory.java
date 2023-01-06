package com.demo.keycloak.provider;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

import javax.naming.InitialContext;

/**
 * Provider Id 를 설정하고 Keycloak 과 연결
 *
 */
public class CustomUserStorageProviderFactory implements UserStorageProviderFactory<CustomUserStorageProvider>{

	@Override
	public CustomUserStorageProvider create(KeycloakSession session, ComponentModel model) {
		try {
			InitialContext ctx = new InitialContext();
			CustomUserStorageProvider provider = (CustomUserStorageProvider) ctx.lookup("java:global/user-federation-mariadb/" + CustomUserStorageProvider.class.getSimpleName());
			provider.setModel(model);
			provider.setSession(session);
			return provider;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getId() {
		// 2) configuration.xml 에서 <spi name="{id값}"> 에 작성
		return "custom-user-storage";
	}
	

}

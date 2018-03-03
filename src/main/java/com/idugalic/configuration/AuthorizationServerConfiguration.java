package com.idugalic.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	static final String CLIEN_ID = "testjwtclientid";
	static final String GRANT_TYPE = "password";
	static final String SCOPE_READ = "read";
	static final String SCOPE_WRITE = "write";
	static final String RESOURCES_IDS = "testjwtresourceid";

	@Autowired
	private JwtTokenStore jwtTokenStore;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer.inMemory()
		.withClient(CLIEN_ID)
		.secret(SecurityConfiguration.SIGNING_KEY)
		.authorizedGrantTypes(GRANT_TYPE).scopes(SCOPE_READ, SCOPE_WRITE);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
		endpoints.tokenStore(jwtTokenStore)
		.accessTokenConverter(accessTokenConverter)
		.tokenEnhancer(enhancerChain)
		.authenticationManager(authenticationManager);
	}
}

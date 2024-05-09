//package com.isttmicroservice.authservice;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
//
//@SuppressWarnings("deprecation")
//@Configuration
//@EnableAuthorizationServer
//public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
//
//	@Autowired
//	private AuthenticationManager authenticationManager;
//
//	@Autowired
//	PasswordEncoder passwordEncoder;
//
////	@Autowired
////	UserDetailsService userDetailsService;
//
////	@Autowired
////	DataSource dataSource;
//
//	@Override
//	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.inMemory().withClient("admin").secret(passwordEncoder.encode("123"))
//				.authorizedGrantTypes("password", "implicit", "client_credentials", "authorization_code",
//						"refresh_token")
//				.redirectUris("https://oauthdebugger.com/debug").scopes("read", "write")
//				.accessTokenValiditySeconds(3600) // 1 hour
//				.refreshTokenValiditySeconds(2592000);
////		
////		.and()
////		.withClient("accountservice").secret(passwordEncoder.encode("123"))
////		.authorizedGrantTypes("client_credentials")
////		.scopes("notification", "log")
////		.accessTokenValiditySeconds(300); // 5 minutes
////		clients.jdbc(dataSource);
//	}
//
//    @Bean
//    public TokenStore tokenStore() {
//	return new InMemoryTokenStore();
//    }
//
//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore());
////				.accessTokenConverter(accessTokenConverter());
////				.userDetailsService(userDetailsService);
//	}
//
//	@Override
//	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//		oauthServer.passwordEncoder(passwordEncoder)
//		.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
////				.allowFormAuthenticationForClients();
//	}
//
//	// JWT
////	@Bean
////	public TokenStore tokenStore() {
////		return new JwtTokenStore(accessTokenConverter());
////	}
////
//
////	@Bean
////	public JwtAccessTokenConverter accessTokenConverter() {
////		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
////		converter.setSigningKey("123");// symmetric key
////
////		return converter;
////	}
//}

package com.DeskBooking.DeskBooking.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.DeskBooking.DeskBooking.jwt.AuthenticationFilter;
import com.DeskBooking.DeskBooking.jwt.AuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests.anyRequest()).authenticated());
		http.formLogin(Customizer.withDefaults());
		http.httpBasic(Customizer.withDefaults());
		return (SecurityFilterChain)http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}

//Spring boot 2.x old config
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	private final UserDetailsService userDetailsService;
//	private final PasswordEncoder passwordEncoder;
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManagerBean());
//		http
//		.csrf().disable()
//		.cors()
//		.and()
//		.sessionManagement().sessionCreationPolicy(STATELESS)
//		.and()
//		.authorizeRequests()
//			.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//			.antMatchers("/profile/token/refresh","/login/**","/reset/**","/register/**","/images/**","/css/**","/profile/reset/password/**").permitAll()
//			.antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_ENJOYING_ADMIN")
//		.and()
//		.addFilter(authenticationFilter)
//		.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//		http
//		.formLogin()
//		.loginPage("/superadmin/login").passwordParameter("password").usernameParameter("username").defaultSuccessUrl("/superadmin/panel", true).failureUrl("/superadmin/login/error").failureForwardUrl("/superadmin/login/error")
//		.and()
//		.exceptionHandling().accessDeniedPage("/superadmin/403")
//		.and()
//		.logout().logoutUrl("/superadmin/logout").logoutSuccessUrl("/superadmin/login?logout")
//		.and()
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//		.and()
//		.authorizeRequests()
//		.antMatchers("/superadmin/login").permitAll()
//		.antMatchers("/superadmin/panel**").hasAuthority("ROLE_ENJOYING_ADMIN")
//		.antMatchers("/superadmin/changeActivity").hasAuthority("ROLE_ENJOYING_ADMIN")
//		.antMatchers("/superadmin/changeAdminPrivilege").hasAuthority("ROLE_ENJOYING_ADMIN")
//		.antMatchers("/superadmin/searchUser").hasAuthority("ROLE_ENJOYING_ADMIN")
//		.anyRequest().authenticated();
//	}
//
//	@Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        return source;
//    }
//
//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//}


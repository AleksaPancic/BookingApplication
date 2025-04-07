package com.DeskBooking.deskbooking.config;


import com.DeskBooking.deskbooking.exception.UnauthenticatedUserException;
import com.DeskBooking.deskbooking.exception.ForbiddenUserException;
import com.DeskBooking.deskbooking.filter.CsrfCookieFilter;
import com.DeskBooking.deskbooking.registration.token.ConfirmationTokenService;
import com.DeskBooking.deskbooking.repository.RoleRepository;
import com.DeskBooking.deskbooking.repository.UsersRepository;
import com.DeskBooking.deskbooking.repository.WorkingUnitsRepository;
import com.DeskBooking.deskbooking.service.impl.CustomUserDetailService;
import com.DeskBooking.deskbooking.service.EmailSenderService;
import com.DeskBooking.deskbooking.service.HtmlData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import com.DeskBooking.deskbooking.jwt.AuthenticationFilter;
import com.DeskBooking.deskbooking.jwt.AuthorizationFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UsersRepository usersRepository;
	private final RoleRepository roleRepository;
	private final WorkingUnitsRepository workingUnitsRepository;
	private final ConfirmationTokenService confirmationTokenService;
	private final EmailSenderService emailSenderService;
	private final HtmlData htmlData;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, DataSource dataSource) throws Exception {

		CsrfTokenRequestAttributeHandler csrfTokenRequestHandler = new CsrfTokenRequestAttributeHandler();
//		http.requiresChannel(rcc -> rcc.anyRequest().requiresSecure()) //Only HTTPS when its requiresSecure, only for prod i can add @Profile and stuff
			http
				.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
						.ignoringRequestMatchers("/superadmin/logout", "/register")
						.csrfTokenRequestHandler(csrfTokenRequestHandler))
				.authorizeHttpRequests(auth -> auth
						.anyRequest().permitAll()
				)
				.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
				.addFilter(new AuthenticationFilter(authenticationManager(), authenticationEventPublisher()))
				.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling((exception) -> exception.authenticationEntryPoint(new UnauthenticatedUserException()))
				.exceptionHandling((exception) -> exception.accessDeniedHandler(new ForbiddenUserException()));

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authProvider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailService(usersRepository, roleRepository, workingUnitsRepository, passwordEncoder(), confirmationTokenService, emailSenderService, htmlData);
	}

	@Bean
	public CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}

	@Bean
	public AuthenticationEventPublisher authenticationEventPublisher() {
		return new DefaultAuthenticationEventPublisher();
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


package com.DeskBooking.DeskBooking.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


import com.DeskBooking.DeskBooking.registration.Token.ConfirmationTokenService;
import com.DeskBooking.DeskBooking.repository.RoleRepository;
import com.DeskBooking.DeskBooking.repository.UsersRepository;
import com.DeskBooking.DeskBooking.repository.WorkingUnitsRepository;
import com.DeskBooking.DeskBooking.service.CustomUserDetailService;
import com.DeskBooking.DeskBooking.service.EmailSenderService;
import com.DeskBooking.DeskBooking.service.EmailValidator;
import com.DeskBooking.DeskBooking.service.HtmlData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.DeskBooking.DeskBooking.jwt.AuthenticationFilter;
import com.DeskBooking.DeskBooking.jwt.AuthorizationFilter;

import lombok.RequiredArgsConstructor;

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
	private final EmailValidator emailValidator;
	private final HtmlData htmlData;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, DataSource dataSource) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
//				.sessionManagement(session -> session
//						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//				)
				.authorizeHttpRequests(auth -> auth
						.anyRequest().permitAll()
				)
				// Add your custom filter to the chain
				.addFilter(new AuthenticationFilter(authenticationManager()))
				.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

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
		return new CustomUserDetailService(usersRepository, roleRepository, workingUnitsRepository, passwordEncoder(), confirmationTokenService, emailSenderService, emailValidator, htmlData);
	}

	@Bean
	public CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
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


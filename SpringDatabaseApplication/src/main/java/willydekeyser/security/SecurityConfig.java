package willydekeyser.security;


import static willydekeyser.controller.NamenLijst.LOGIN_ADMIN;
import static willydekeyser.controller.NamenLijst.LOGIN_USER;
import static willydekeyser.controller.NamenLijst.ROLE_ADMIN;
import static willydekeyser.controller.NamenLijst.ROLE_USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig {

	@Configuration
	@Order(1)
	public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity httpSecurity) throws Exception {
			httpSecurity.csrf().disable();
			httpSecurity.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/kasboek/**").hasRole(ROLE_USER)
				.antMatchers("/lidgeld/**").hasRole(ROLE_USER)
				.antMatchers("/leden/**").hasRole(ROLE_USER)
				.antMatchers("/rubriek/**").hasRole(ROLE_USER)
				.antMatchers("/soortenleden/**").hasRole(ROLE_USER)
				.antMatchers("/restcontroller/**").hasRole(ROLE_ADMIN)
				.and()
				.formLogin().loginPage("/login").permitAll()
				.and()
				.logout().permitAll()
				.and()
				.logout().deleteCookies("JSESSIONID")
				.and()
				.rememberMe().key("willydekeyser").tokenValiditySeconds(3600)
				.and()
				.exceptionHandling().accessDeniedPage("/");

		}
	}

	@Configuration
	@Order(2)
	public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity httpSecurity) throws Exception {
			httpSecurity.authorizeRequests().anyRequest().permitAll();
		}
	}

	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().regexMatchers("/resources/static/*");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser(LOGIN_USER).password(passwordEncoder().encode("willy")).roles(ROLE_USER)
			.and()
			.withUser(LOGIN_ADMIN).password(passwordEncoder().encode("willy")).roles(ROLE_ADMIN, ROLE_USER);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
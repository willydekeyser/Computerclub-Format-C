package willydekeyser.security;


import static willydekeyser.controller.NamenLijst.ROLE_ADMIN;
import static willydekeyser.controller.NamenLijst.ROLE_GOLD;
import static willydekeyser.controller.NamenLijst.ROLE_USER;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


public class SecurityConfig {
	
	@Autowired
	private DataSource dataSource;

	@Configuration
	@Order(1)
	public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {

		@Bean
	    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
	        return new MyAuthenticationSuccessHandler();
	    }
		
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
				.antMatchers("/actuator/**").hasRole(ROLE_GOLD)
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
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
			.authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");
			
		
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
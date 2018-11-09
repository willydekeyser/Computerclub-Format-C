package willydekeyser.security;

import static willydekeyser.controller.NamenLijst.ROLE_GOLD;
import static willydekeyser.controller.NamenLijst.ROLE_USER;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	BCryptPasswordEncoder paswoordencoder;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
				
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(paswoordencoder)
			.usersByUsernameQuery("select username,password, enabled from users where username=?")
			.authoritiesByUsernameQuery("select username, role from user_roles where username=?");
		
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
			.antMatchers("/restcontroller/**").hasRole(ROLE_GOLD)
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
	

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

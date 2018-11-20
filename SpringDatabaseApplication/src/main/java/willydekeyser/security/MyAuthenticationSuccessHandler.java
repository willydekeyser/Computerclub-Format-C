package willydekeyser.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		Collection <? extends GrantedAuthority> authorities = authentication.getAuthorities();
		authorities.forEach(authority -> {
			System.out.println("MyAuthenticationSuccessHandler " + authority.getAuthority());
			try {
				redirectStrategy.sendRedirect(request, response, "/testen");
			} catch (IOException e) {
				System.out.println("ERROR: " + e.getMessage());;
			}
	});
	}
}

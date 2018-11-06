package willydekeyser.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TestInterceptor implements HandlerInterceptor {

	//private HttpSession session;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// response.sendRedirect("/login");

		//session = request.getSession();
		//System.out.println("Last pre Handler access: " + new Date(session.getLastAccessedTime()));

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		// modelAndView.setViewName("testen.html");
		// Enumeration<?> headerNames = request.getHeaderNames();
		// while (headerNames.hasMoreElements()) {
		// String key = (String) headerNames.nextElement();
		// String value = request.getHeader(key);
		// System.out.println("Brwoser " + key + " - " + value);
		// }
		// System.out.println("Brwoser " + request.getHeader("X-Requested-With"));
		//session = request.getSession();
		//System.out.println("Last post Handler access: " + new Date(session.getLastAccessedTime()));

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {

	}

}

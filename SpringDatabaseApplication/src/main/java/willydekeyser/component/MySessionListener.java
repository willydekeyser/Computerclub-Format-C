package willydekeyser.component;

import java.sql.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

@Component
public class MySessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("==== Session is created ==== " + event.getSession().getMaxInactiveInterval() + " - " + new Date(event.getSession().getCreationTime()));
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("==== Session is destroyed ==== " + new Date(event.getSession().getCreationTime()));
	}

}

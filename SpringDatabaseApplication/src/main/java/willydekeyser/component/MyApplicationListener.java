package willydekeyser.component;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

//@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent>{

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		System.out.println("MyApplicationListener: " + event.toString());
		
	}

}

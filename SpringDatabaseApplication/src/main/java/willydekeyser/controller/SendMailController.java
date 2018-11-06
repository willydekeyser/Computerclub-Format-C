package willydekeyser.controller;

import java.util.Date;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import willydekeyser.sendmail.model.Mail;
import willydekeyser.sendmail.service.MailSenderService;

@Controller
@RequestMapping("mail")
public class SendMailController {

	
	@Autowired
    private MailSenderService senderService;
	
	private String to = "wdkeyser@gmail.com";
	private String subject = "Simple Email Subject";
	private String htmlSubject = "HTML Email Subject";
	private String content = "Hello client,\n This a simple email content !";
	private String htmlContent = "<html> <body> <p>Hello client,</p> <p>This an <strong>HTML</strong> email content !</p> </body> </html>";
	
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("title", "Computerclub Format C");
		model.addAttribute("message", "Crunchify Spring MVC with Ajax and JQuery Demo..");
		try {
			senderService.sendHTMLMail(new Mail(to, htmlSubject, htmlContent));
			senderService.sendSimpleMail(new Mail(to, subject,content));
		} catch (MessagingException | InterruptedException e) {
			System.out.println("Fout: " + e.getMessage());
		}
		return "/mail/mail";
	}
	
	@GetMapping("/mail")
	public String mail(Model model) {
		model.addAttribute("message", "Computerclub Format C Agenda Versturen..");
		return "/mail/mail";
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
    public @ResponseBody String getTime() {
        String result = "Verstuur E-mail <b>" + senderService.getMailTeller() + "</b>. op <b>" + new Date().toString() + "</b></br>" + 
        "<div class='progress' style='width: 25%'><div id='mailProgress' class='progress-bar' role='progressbar' style='width: " +  
        		senderService.getMailTeller() + "%;' aria-valuenow=" +  senderService.getMailTeller() + 
        		" aria-valuemin='0' aria-valuemax='100' teller = '" + senderService.getMailTeller() + "'>" + senderService.getMailTeller() + "%</div></div>";
        return result;
    }
	
}

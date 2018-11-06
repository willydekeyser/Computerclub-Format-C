package willydekeyser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("agenda")
public class SendAgendaController {
	
	private String tekst = "";

	@GetMapping("/agenda")
	public String agenda(Model model) {
		model.addAttribute("title", "Computerclub Format C");
		model.addAttribute("tekst", tekst);
		return "/agenda/agenda";
	}
	
	@RequestMapping(value="/post", method=RequestMethod.POST)
	public @ResponseBody String post(@RequestBody String tekst) {
		System.out.println("Tekst: " + tekst);
		this.tekst = tekst;
		return "OK";
	}
	
}

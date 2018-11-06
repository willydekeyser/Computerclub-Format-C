package willydekeyser.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import willydekeyser.model.SoortenLeden;
import willydekeyser.service.ISoortenLedenService;

@Controller
@RequestMapping("soortenleden")
public class SoortenledenController {

	@Autowired
	private ISoortenLedenService soortenLedenservice;
	
	private List<SoortenLeden> soortenLeden = new ArrayList<>();

	@PostConstruct
	public void init() {

	}

	@GetMapping("/")
	public String soortenleden(Model model) {
		soortenLeden = soortenLedenservice.getAllSoortenLeden();
		model.addAttribute("title", "Computerclub Format C");
		model.addAttribute("soortenleden", soortenLeden);
		model.addAttribute("aantal", soortenLeden.size());
		return "soortenleden/soortenLeden :: soortenleden_tabel_start";
	}
	
    @GetMapping("/soortenLedenById")
    public String soortenLedenById(@RequestParam(name="id", required=true, defaultValue="1") Integer id, Model model) {
    	model.addAttribute("title", "Computerclub Format C");
    	model.addAttribute("soortenLeden", soortenLedenservice.getSoortenLedenById(id));
        return "soortenleden/soortenLedenById";
    }
    
	@GetMapping(value="/editSoortenleden")
	public String editSoortenleden() {
		return "soortenleden/fragmenten/soortenledenmodal :: editSoortenledenModal";
	}
	
	@GetMapping(value="/existLedenBySoortenledenId/{id}")
	@ResponseBody
	public String existLedenBySoortenledenId(@PathVariable Integer id) {
		return soortenLedenservice.ledenExistsBySoortenledenId(id).toString();
	}
	
	@PostMapping(value="/save_newSoortenleden")
	public @ResponseBody List<SoortenLeden> save_newSoortenleden(@Validated SoortenLeden soortenleden) {
		soortenLedenservice.addSoortenLeden(soortenleden);
		return soortenLedenservice.getAllSoortenLeden();
	}
	
	@PostMapping(value="/save_updateSoortenleden")
	public @ResponseBody List<SoortenLeden> save_updateSoortenleden(@Validated SoortenLeden soortenleden) {
		soortenLedenservice.updateSoortenLeden(soortenleden);
		return soortenLedenservice.getAllSoortenLeden();
	}
	
	@PostMapping(value="/save_deleteSoortenleden")
	public @ResponseBody List<SoortenLeden> save_deleteSoortenleden(@Validated SoortenLeden soortenleden) {
		soortenLedenservice.deleteSoortenLeden(soortenleden.getId());;
		return soortenLedenservice.getAllSoortenLeden();
	}
	
 /**
 * 
 * 
 * Rest Controllers
 *
 * 
 */
	
	@GetMapping("/restcontroller/soortenleden")
	public @ResponseBody List<SoortenLeden> soortenleden() {	
		return soortenLedenservice.getAllSoortenLeden();
	}
	
	@GetMapping("/restcontroller/soortenleden/leden")
	public @ResponseBody List<SoortenLeden> soortenledenLeden() {	
		return soortenLedenservice.getAllSoortenLedenLeden();
	}
	
}

package willydekeyser.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import willydekeyser.model.Rubriek;
import willydekeyser.service.IRubriekService;

@Controller
@RequestMapping("rubriek")
public class RubriekController {

	@Autowired
	private IRubriekService rubriekservice;

	private List<Rubriek> rubriekLijst = new ArrayList<>();

	@RequestMapping("/")
	public String rubriek(Model model) {	
		rubriekLijst = rubriekservice.getAllRubriek();
		model.addAttribute("title", "Computerclub Format C");
		model.addAttribute("rubriek", rubriekLijst);
		model.addAttribute("aantal", rubriekLijst.size());
		return "rubriek/rubriek :: rubriek_tabel_start";
	}
	
    @GetMapping("/rubriekById")
    public String rubriekById(@RequestParam(name="id", required=true, defaultValue="1") Integer id, Model model) {
    	Rubriek rubriek = rubriekservice.getRubriekById(id);
    	model.addAttribute("title", "Computerclub Format C");
    	model.addAttribute("rubriek", rubriek);
    	return "rubriek/rubriekbyId";
    }
    
	@GetMapping(value="/editRubriek")
	public String editRubriek() {
		return "rubriek/fragmenten/rubriekmodal :: editRubriekModal";
	}
	
	@GetMapping(value="/existKasboekByRubriekId/{id}")
	@ResponseBody
	public String existKasboekByRubriekId(@PathVariable Integer id) {
		return rubriekservice.kasboekExistsByRubriekId(id).toString();
	}
	
	@PostMapping(value="/save_newRubriek")
	public @ResponseBody List<Rubriek> save_newRubriek(@Validated Rubriek rubriek) {
		rubriekservice.addRubriek(rubriek);
		return rubriekservice.getAllRubriek();
	}
	
	@PostMapping(value="/save_updateRubriek")
	public @ResponseBody List<Rubriek> save_updateRubriek(@Validated Rubriek rubriek) {
		rubriekservice.updateRubriek(rubriek);
		return rubriekservice.getAllRubriek();
	}
	
	@PostMapping(value="/save_deleteRubriek")
	public @ResponseBody List<Rubriek> save_deleteRubriek(@Validated Rubriek rubriek) {
		rubriekservice.deleteRubriek(rubriek.getId());
		return rubriekservice.getAllRubriek();
	}
	
 /**
 * 
 * 
 * Rest Controllers
 *
 * 
 */
	
	@GetMapping("/restcontroller/rubriek")
	public @ResponseBody List<Rubriek> rubriek() {	
		return rubriekservice.getAllRubriek();
	}
	
	@GetMapping("/restcontroller/rubriek/kasboek")
	public @ResponseBody List<Rubriek> rubriekKasboek() {	
		return rubriekservice.getAllRubriekKasboek();
	}
	
	@GetMapping("/restcontroller/rubriek/kasboek/response")
	public @ResponseBody ResponseEntity<List<Rubriek>> rubriekKasboekresponse() {	
		return new ResponseEntity<List<Rubriek>>(rubriekservice.getAllRubriekKasboek(), HttpStatus.OK);
	}
	
}

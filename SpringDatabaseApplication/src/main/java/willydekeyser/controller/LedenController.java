package willydekeyser.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import willydekeyser.model.Leden;
import willydekeyser.model.SoortLeden;
import willydekeyser.model.SoortenLeden;
import willydekeyser.service.ILedenService;
import willydekeyser.service.ISoortenLedenService;

@Controller
@RequestMapping("leden")
public class LedenController {

	@Autowired
	private ILedenService ledenservice;
	@Autowired
	private ISoortenLedenService soortenledenservice;

	private List<SoortenLeden> soortenleden = new ArrayList<>();
	private List<Leden> leden = new ArrayList<>();
	private Leden lid = null;
	private SoortLeden soort = new SoortLeden();
	private List<SoortLeden> selectSoorten;
	
	@PostConstruct
	public void init() {
		selectSoorten = soort.SoortLedenLijst();
	}
	
	/**
	 * 
	 * Leden start scherm /index
	 * 
	 */
	
	@GetMapping(value = "/leden")
	public ModelAndView leden() {
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("leden/leden_start :: leden_start");
		return modelandview;	
	}
	
	@GetMapping(value = "/leden_menu/{soortId}")
	public ModelAndView leden_menu(@PathVariable Integer soortId) {
		ModelAndView modelandview = new ModelAndView();
		selectSoorten = soort.SoortLedenLijst(soortId);
		modelandview.addObject("soorten", selectSoorten);
		modelandview.setViewName("leden/fragmenten/leden_menu :: leden_menu");
		return modelandview;	
	}
	
	/**
	 * 
	 * Leden scherm kies soort leden /indexid
	 * 
	 * 
	 */
	
	@GetMapping(value = "/namenlijst/{soortId}/{lidId}")
	public String ledenid(@PathVariable Integer soortId, @PathVariable Integer lidId, Model model) {
		leden = ledenservice.getAllLedenNamenlijst(soortId);
		model.addAttribute("leden", leden);
		return "leden/fragmenten/leden_namenlijst :: leden_namenlijst";	
	}
	
	@GetMapping("/leden_main/{id}")
	public String ledennamenlijst(@PathVariable Integer id, Model model) {	
		model.addAttribute("id", id);
		return "leden/fragmenten/leden_main :: #leden_main";
	}
	
	@GetMapping("/leden_gegevens")
	public String ledengegevens(Model model) {	
		return "leden/fragmenten/leden_ledengegevenstabel :: leden_ledengegevenstabel";
	}
		
	@GetMapping("/leden_lidgeldlijst")
	public String lidgeldlijst(Model model) {	
		return "leden/fragmenten/leden_lidgeldtabel :: leden_lidgeldtabel";
	}
	
	
	/**
	 * 
	 * Leden tabel
	 * 
	 * 
	 */
		
	
	@GetMapping("/leden_tabel")
	public String ledentabel(Model model) {	
		return "leden/leden_tabel_start :: leden_tabel_start";
	}
	
	@GetMapping(value = "/leden_tabel_menu/{soortId}")
	public String leden_tabel_menu(@PathVariable Integer soortId, Model model) {
		selectSoorten = soort.SoortLedenLijst(soortId);
		leden = ledenservice.getAllLedenNamenlijst(soortId);
		soortenleden = soortenledenservice.getAllSoortenLeden();
		Integer firstId = 0;
		if (!leden.isEmpty()) {
			firstId = leden.get(0).getId();
			System.out.println("Leden is niet leeg: " + leden.toString());
		} else {
			System.out.println("Leden is leeg: " + leden.toString());
		}
		model.addAttribute("leden", leden);
		model.addAttribute("id", firstId);
		model.addAttribute("aantal", leden.size());
		model.addAttribute("soorten", selectSoorten);
		model.addAttribute("ledensoorten", soortenleden);
		model.addAttribute("soortId", soortId);
		return "leden/leden_tabel_menu :: leden_tabel_menu";	
	}
	
    @GetMapping("/leden_tabel_ledenlijst/{id}")
    public String leden_tabel_ledenlijst(@PathVariable Integer id, Model model) {
    	leden = ledenservice.getAllLedenSoortenleden(id);
    	model.addAttribute("leden", leden);
    	model.addAttribute("aantal", leden.size());
    	return "leden/leden_tabel_ledenlijst :: leden_tabel_ledenlijst";
    }
    
    @GetMapping("/leden_tabel_ById/{id}")
    public String leden_tabel_byid(@PathVariable Integer id, Model model) {
    	lid = ledenservice.getLedenById(id);
    	model.addAttribute("leden", lid);
    	return "leden/leden_tabel_ById :: leden_tabel_byid";
    }
    
    /**
     * starten vanuit static/js/leden.js
	 * 
	 * Modal form in lidgeldmodal.java
	 * 
	 * New Lid
	 * Update Lid
	 * editLid
	 * 
	 * Delete Lid
	 * deleteLid
     * 
     */
    
	@GetMapping(value="/editLid")
	public String newLid(ModelMap model) {
		soortenleden = soortenledenservice.getAllSoortenLeden();
		model.addAttribute("ledensoorten", soortenleden);
		return "leden/fragmenten/ledenmodal :: editLidModal";
	}
	
	@GetMapping(value="/deleteLid")
	public String deleteLid(ModelMap model) {
		return "leden/fragmenten/ledenmodal :: deleteLidModal";
	}
		
	/**
	 * starten vanuit static/js/lidgeld.js
	 * 
	 * Modal form in lidgeldmodal.java
	 * 
	 * New Lidgeld
	 * Update Lidgeld
	 * Delete Lidgeld
	 * 
	 * editLidgeld
	 * 
	 */
	
    @GetMapping("/editLidgeld")
    public String editlidgeld() {
        return "lidgeld/fragmenten/lidgeldmodal :: editLidgeldModal";  
    }
    
 /**
 * 
 * 
 * Rest Controllers
 *
 * 
 */
	@GetMapping("/restcontroller/ledennamenlijstbyid/{soort}")
	public @ResponseBody String restConrollerledenNamenlijstById(@PathVariable Integer soort) {
		leden = ledenservice.getAllLedenNamenlijst(soort);
		String items = "";
		for(Leden lid : leden) {
			String item = "{\"id\":" + lid.getId() + ",\"naam\":\"" + lid.getVoornaam() + " " + lid.getFamilienaam() + "\"}";
			items = items + item + ",";
		};
		String response = "[" + items.substring(0, items.length() - 1) + "]";
		return response;
	}
	
	@GetMapping("/restcontroller/ledenbyid/{id}")
	public @ResponseBody Leden restConrollerledenById(@PathVariable Integer id) {
		lid = ledenservice.getLedenById(id);
		return lid;
	}
	
	@GetMapping("/restcontroller/leden")
	public @ResponseBody List<Leden> restConrollerLeden() {	
		return ledenservice.getAllLeden();
	}
	
	@GetMapping("/restcontroller/ledenlijst")
	public @ResponseBody String restConrollerledenlijst() {	
		List<Leden> lijst = ledenservice.getAllLeden();
		String link = "<h1>Ledenlijst</h1>";
		for (Leden leden : lijst) {
			link = link + "<a href='/leden/restcontroller/ledenbyid/" + leden.getId() + "'>" + leden.getVoornaam() + " " + leden.getFamilienaam() + "</a><br/>";
		}
		return link;
	}
	
	@GetMapping("/restcontroller/soortenleden")
	public @ResponseBody List<Leden> ledenSoortenleden() {	
		return ledenservice.getAllLedenSoortenleden(1);
	}
	
	@GetMapping("/restcontroller/lidgeld")
	public @ResponseBody List<Leden> ledenLidgeld() {	
		return ledenservice.getAllLedenLidgeld();
	}
	
	@PostMapping("/save_newLid")
	public @ResponseBody Leden save_newLid(@Validated  Leden lid) {
		this.lid = ledenservice.addLeden(lid);
		return this.lid;
	}
	
	@PostMapping("/save_updateLid")
	public @ResponseBody Leden save_updateLid(@Validated  Leden lid, Boolean soort, Boolean naam) {
		ledenservice.updateLeden(lid);
		this.lid = ledenservice.getLedenById(lid.getId());
		return this.lid;
	}
	
	@PostMapping("/save_deleteLid")
	public @ResponseBody Integer save_deleteLid(@Validated  Integer id) {
		return ledenservice.deleteLeden(id);
	}
	
}

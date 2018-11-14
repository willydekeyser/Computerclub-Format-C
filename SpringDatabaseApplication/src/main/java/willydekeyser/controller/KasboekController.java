package willydekeyser.controller;

import static willydekeyser.controller.NamenLijst.AANTAL;
import static willydekeyser.controller.NamenLijst.COMPUTERCLUB;
import static willydekeyser.controller.NamenLijst.INKOMSTEN;
import static willydekeyser.controller.NamenLijst.JAAR;
import static willydekeyser.controller.NamenLijst.JAARTAL;
import static willydekeyser.controller.NamenLijst.KASBOEK;
import static willydekeyser.controller.NamenLijst.PAGINA_TITEL;
import static willydekeyser.controller.NamenLijst.RUBRIEK;
import static willydekeyser.controller.NamenLijst.TOTAAL;
import static willydekeyser.controller.NamenLijst.UITGAVEN;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import willydekeyser.model.Kasboek;
import willydekeyser.model.KasboekJaartal;
import willydekeyser.model.Rubriek;
import willydekeyser.service.IKasboekService;
import willydekeyser.service.IRubriekService;

@Controller
@RequestMapping("kasboek")
public class KasboekController {

	@Autowired
	private IKasboekService kasboekservice;
	@Autowired
	private IRubriekService rubriekservice;

	private List<Kasboek> kasboekLijst = new ArrayList<>();
	private List<Rubriek> rubrieken = null;
	private Kasboek kasboek = null;
	
	@GetMapping("/")
	public String index(Model model) {	
		List<KasboekJaartal> jaartal = kasboekservice.getKasboekJaarRubriek();
		model.addAttribute(PAGINA_TITEL, COMPUTERCLUB);
		model.addAttribute(UITGAVEN, kasboekservice.getSom(0, 0)[0]);
		model.addAttribute(INKOMSTEN, kasboekservice.getSom(0, 0)[1]);
		model.addAttribute(TOTAAL, kasboekservice.getSom(0, 0)[2]);
		model.addAttribute(JAARTAL, jaartal);
		model.addAttribute(KASBOEK, kasboekLijst);
		model.addAttribute(AANTAL, kasboekLijst.size());
		return "kasboek/kasboek_menu :: kasboek_start";
	}
	
	@GetMapping("/kasboek")
	public String kasboek(Model model) {	
		kasboekLijst = kasboekservice.getAllKasboekRubriek();
		model.addAttribute(PAGINA_TITEL, COMPUTERCLUB);
		model.addAttribute(UITGAVEN, kasboekservice.getSom(0, 0)[0]);
		model.addAttribute(INKOMSTEN, kasboekservice.getSom(0, 0)[1]);
		model.addAttribute(TOTAAL, kasboekservice.getSom(0, 0)[2]);
		model.addAttribute(KASBOEK, kasboekLijst);
		model.addAttribute(AANTAL, kasboekLijst.size());
		return "kasboek/kasboek";
	}
	
    @GetMapping("/kasboekById")
    public String kasboekById(@RequestParam(name="id", required=true, defaultValue="1") Integer id, @RequestParam(name="cell", required=true, defaultValue="0") Integer cell, Model model) {
    	Kasboek kasboekById = kasboekservice.getKasboekById(id);
    	model.addAttribute(PAGINA_TITEL, "Computerclub Format C");
    	model.addAttribute(KASBOEK, kasboekById);
        return "kasboek/kasboekbyId";
    }
    
    @GetMapping("/kasboekJaarRubriek/{jaar}/{rubriek}")
    public String kasboekJaarRubriek(@PathVariable Integer jaar, @PathVariable Integer rubriek, Model model) {
    	kasboekLijst = kasboekservice.getAllKasboekRubriekJaarRubriek(jaar, rubriek);
    	model.addAttribute(PAGINA_TITEL, COMPUTERCLUB);
    	model.addAttribute(UITGAVEN, kasboekservice.getSom(0, 0)[0]);
		model.addAttribute(INKOMSTEN, kasboekservice.getSom(0, 0)[1]);
		model.addAttribute(TOTAAL, kasboekservice.getSom(0, 0)[2]);
    	model.addAttribute(JAAR, jaar);
    	model.addAttribute(RUBRIEK, rubriek);
    	model.addAttribute(KASBOEK, kasboekLijst);
        return "kasboek/kasboeklijst :: kasboektabel";
    }
    
	@GetMapping(value="/editKasboek")
	public String editKasboek(ModelMap model) {
		rubrieken = rubriekservice.getAllRubriek();
		model.addAttribute(RUBRIEK, rubrieken);
		return "kasboek/fragmenten/kasboekmodal :: editKasboekModal";
	}
	
	@PostMapping("/save_newKasboek/{selected_jaar}/{selected_rubriek}")
	public @ResponseBody List<Kasboek> save_NewKasboek(@Validated Kasboek kasboek, @PathVariable Integer selected_jaar, @PathVariable Integer selected_rubriek) {
		kasboekservice.addKasboek(kasboek);
		kasboekLijst = kasboekservice.getAllKasboekRubriekJaarRubriek(selected_jaar, selected_rubriek); 
		return kasboekLijst;
	}
	
	@PostMapping("/save_updateKasboek/{selected_jaar}/{selected_rubriek}/{change_jaar}/{jaar_menu}")
	public @ResponseBody List<Kasboek> save_updateKasboek(@Validated Kasboek kasboek, @PathVariable Integer selected_jaar, @PathVariable Integer selected_rubriek, 
			@PathVariable Boolean change_jaar, @PathVariable Integer jaar_menu) {
		kasboekservice.updateKasboek(kasboek);
		if (change_jaar) {
			kasboekLijst = kasboekservice.getAllKasboekRubriekJaarRubriek(jaar_menu, 0);
		} else {
			kasboekLijst = kasboekservice.getAllKasboekRubriekJaarRubriek(selected_jaar, selected_rubriek);
		}
		return kasboekLijst;
	}
	
	@PostMapping("/save_deleteKasboek/{selected_jaar}/{selected_rubriek}")
	public @ResponseBody List<Kasboek> save_deleteKasboek(@Validated Kasboek kasboek, @PathVariable Integer selected_jaar, @PathVariable Integer selected_rubriek) {
		kasboekservice.deleteKasboek(kasboek.getId());
		kasboekLijst = kasboekservice.getAllKasboekRubriekJaarRubriek(selected_jaar, selected_rubriek);
		return kasboekLijst;
	}
	
/**
 * 
 * 
 * Rest Controllers
 *
 * 
 */
	@GetMapping("/restcontroller/kasboekTotalen/{jaar}/{rubriekId}")
	public @ResponseBody String restConrollerkaboektotalen(@PathVariable Integer jaar, @PathVariable Integer rubriekId) {
		String rubriek = "";
		String totalen = "";
		if (rubriekId != 0) {
			rubriek = rubriekservice.getRubriekById(rubriekId).getRubriek();
		}
		totalen = "{\"Jaar\": " + jaar + ", \"Rubriek\": \"" + rubriek + "\",";
		BigDecimal[] totaal = kasboekservice.getSom(jaar, rubriekId);
		totalen = totalen + "\"Totalen\" : [{\"Inkomsten\": " + totaal[1] + ", \"Uitgaven\": " + totaal[0] + ", \"Totaal\":" + totaal[2] + "},";
		totaal = kasboekservice.getSom(0, 0);
		totalen = totalen + "{\"Inkomsten\": " + totaal[1] + ", \"Uitgaven\": " + totaal[0] + ", \"Totaal\":" + totaal[2] + "}]}";
		return totalen;
	}
	
	@GetMapping("/restcontroller/kasboekbyid/{id}")
	public @ResponseBody Kasboek restConrollerkasboekById(@PathVariable Integer id) {
		kasboek = kasboekservice.getKasboekById(id);
		return kasboek;
	}
	
	@GetMapping("/restcontroller/kasboekmenu")
	public @ResponseBody List<KasboekJaartal> restConrollerkasboekmenu() {
		return kasboekservice.getKasboekJaarRubriek();
	}
	
	@GetMapping("/restcontroller/kasboek")
	public @ResponseBody List<Kasboek> kasboek() {	
		return kasboekservice.getAllKasboek();
	}
	
	@GetMapping("/restcontroller/kasboek/rubriek")
	public @ResponseBody List<Kasboek> kasboekRubriek() {	
		return kasboekservice.getAllKasboekRubriek();
	}
	
	@GetMapping("/restcontroller/kasboek/jaarrubriek")
	public @ResponseBody List<KasboekJaartal> kasboekJaarRubriek() {	
		return kasboekservice.getKasboekJaarRubriek();
	}
	
}

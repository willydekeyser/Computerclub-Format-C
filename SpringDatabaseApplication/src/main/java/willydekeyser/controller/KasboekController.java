package willydekeyser.controller;

import static willydekeyser.controller.NamenLijst.AANTAL;
import static willydekeyser.controller.NamenLijst.COMPUTERCLUB;
import static willydekeyser.controller.NamenLijst.INKOMSTEN;
import static willydekeyser.controller.NamenLijst.JAAR;
import static willydekeyser.controller.NamenLijst.JAARTAL;
import static willydekeyser.controller.NamenLijst.KASBOEK;
import static willydekeyser.controller.NamenLijst.MODAL_TITEL;
import static willydekeyser.controller.NamenLijst.PAGINA_TITEL;
import static willydekeyser.controller.NamenLijst.RUBRIEK;
import static willydekeyser.controller.NamenLijst.TOTAAL;
import static willydekeyser.controller.NamenLijst.UITGAVEN;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
		Kasboek newKasboek = new Kasboek();
		newKasboek.setId(0);
		newKasboek.setJaartal(Year.now().getValue());
		newKasboek.setRubriek(new Rubriek(0,""));
		newKasboek.setUitgaven(new BigDecimal(0));
		newKasboek.setInkomsten(new BigDecimal(0));
		model.addAttribute(MODAL_TITEL, "New Soort!");
		model.addAttribute(KASBOEK, newKasboek);
		model.addAttribute(RUBRIEK, rubrieken);
		return "kasboek/fragmenten/kasboekmodal :: editKasboekModal";
	}
	
	@PostMapping(value="/save_newKasboek")
	public String saveNewKasboek(@Validated Kasboek kasboek, BindingResult bindingResult, Model model) {
		kasboek = kasboekservice.addKasboek(kasboek);
		kasboek(model);
		return "/kasboek/kasboek";
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

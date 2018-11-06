package willydekeyser.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import willydekeyser.dao.IKasboekDAO;
import willydekeyser.model.Kasboek;
import willydekeyser.model.KasboekJaartal;
import willydekeyser.service.IKasboekService;

@Service
public class KasboekService implements IKasboekService {

	@Autowired
	private IKasboekDAO kasboekDAO;
	
	@Override
	public List<Kasboek> getAllKasboek() {
		return kasboekDAO.getAllKasboek();
	}
	
	@Override
	public List<Kasboek> getAllKasboekRubriek() {
		return kasboekDAO.getAllKasboekRubriek();
	}
	
	@Override
	public List<Kasboek> getAllKasboekRubriekJaarRubriek(Integer jaartal, Integer rubriekId) {
		return kasboekDAO.getAllKasboekRubriekJaarRubriek(jaartal, rubriekId);
	}
	
	@Override
	public BigDecimal[] getSom() {
		return kasboekDAO.getSom();
	}
	
	@Override
	public List<Integer> getJaartal() {
		return kasboekDAO.getJaartal();
	}
	
	@Override
	public Kasboek getKasboekById(int id) {
		return kasboekDAO.getKasboekById(id);
	}
	
	@Override
	public List<KasboekJaartal> getKasboekJaarRubriek() {
		return kasboekDAO.getKasboekJaarRubriek();
	}
	
	@Override
	public Kasboek addKasboek(Kasboek kasboek) {
		return kasboekDAO.addKasboek(kasboek);
	}

	@Override
	public Kasboek updateKasboek(Kasboek kasboek) {
		return kasboekDAO.updateKasboek(kasboek);
	}

	@Override
	public void deleteKasboek(int id) {
		kasboekDAO.deleteKasboek(id);
	}

	@Override
	public Boolean kasboekExists(int id) {
		return kasboekDAO.kasboekExists(id);
	}
	
}

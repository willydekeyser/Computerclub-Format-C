package willydekeyser.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import willydekeyser.dao.IRubriekDAO;
import willydekeyser.model.Rubriek;
import willydekeyser.service.IRubriekService;

@Service
public class RubriekService implements IRubriekService {

	@Autowired
	private IRubriekDAO rubriekDAO;
	
	@Override
	public List<Rubriek> getAllRubriek() {
		return rubriekDAO.getAllRubriek();
	}
	
	@Override
	public List<Rubriek> getAllRubriekKasboek() {
		return rubriekDAO.getAllRubriekKasboek();
	}
	
	@Override
	public Rubriek getRubriekById(int id) {
		return rubriekDAO.getRubriekById(id);
	}
	
	@Override
	public Rubriek addRubriek(Rubriek rubriek) {
		return rubriekDAO.addRubriek(rubriek);
	}

	@Override
	public Rubriek updateRubriek(Rubriek rubriek) {
		return rubriekDAO.updateRubriek(rubriek);
	}

	@Override
	public void deleteRubriek(int id) {
		rubriekDAO.deleteRubriek(id);
	}

	@Override
	public Boolean rubriekExists(int id) {
		return rubriekDAO.rubriekExists(id);
	}
	
	@Override
	public Boolean kasboekExistsByRubriekId(int id) {
		return rubriekDAO.kasboekExistsByRubriekId(id);
	}

}

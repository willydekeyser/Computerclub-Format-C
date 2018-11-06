package willydekeyser.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import willydekeyser.dao.ILedenDAO;
import willydekeyser.model.Leden;
import willydekeyser.service.ILedenService;

@Service
public class LedenService implements ILedenService {

	@Autowired
	private ILedenDAO ledenDAO;
	
	@Override
	public List<Leden> getAllLeden() {
		return ledenDAO.getAllLeden();
	}
	
	@Override
	public List<Leden> getAllLedenNamenlijst(Integer soort) {
		return ledenDAO.getAllLedenNamenlijst(soort);
	}
	
	@Override
	public List<Leden> getAllLedenSoortenleden(Integer soort) {
		return ledenDAO.getAllLedenSoortenleden(soort);
	}
	
	@Override
	public List<Leden> getAllLedenLidgeld() {
		return ledenDAO.getAllLedenLidgeld();
	}
	
	@Override
	public Leden getLedenById(int id) {
		return ledenDAO.getLedenById(id);
	}
	
	@Override
	public Leden addLeden(Leden leden) {
		return ledenDAO.addLeden(leden);
	}

	@Override
	public Leden updateLeden(Leden leden) {
		return ledenDAO.updateLeden(leden);
	}

	@Override
	public Integer deleteLeden(int id) {
		return ledenDAO.deleteLeden(id);
	}

	@Override
	public boolean ledenExists(int id) {
		return ledenDAO.ledenExists(id);
	}

}

package willydekeyser.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import willydekeyser.dao.ISoortenLedenDAO;
import willydekeyser.model.SoortenLeden;
import willydekeyser.service.ISoortenLedenService;

@Service
public class SoortenLedenService implements ISoortenLedenService {

	@Autowired
	private ISoortenLedenDAO soortenLedenDAO;
	
	@Override
	public List<SoortenLeden> getAllSoortenLeden() {
		
		return soortenLedenDAO.getAllSoortenLeden();
	}
	
	@Override
	public List<SoortenLeden> getAllSoortenLedenLeden() {
		
		return soortenLedenDAO.getAllSoortenLedenLeden();
	}
	
	@Override
	public SoortenLeden getSoortenLedenById(int id) {
		return soortenLedenDAO.getSoortenLedenById(id);
	}
	
	@Override
	public SoortenLeden addSoortenLeden(SoortenLeden soort) {
		return soortenLedenDAO.addSoortenLeden(soort);
		
	}

	@Override
	public SoortenLeden updateSoortenLeden(SoortenLeden soort) {
		return soortenLedenDAO.updateSoortenLeden(soort);
		
	}

	@Override
	public void deleteSoortenLeden(int id) {
		soortenLedenDAO.deleteSoortenLeden(id);
		
	}

	@Override
	public Boolean soortenLedenExists(int id) {
		return false;
	}
	
	@Override
	public Boolean ledenExistsBySoortenledenId(int id) {
		return soortenLedenDAO.ledenExistsBySoortenledenId(id);
	}

}

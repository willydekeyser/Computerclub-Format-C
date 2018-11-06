package willydekeyser.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import willydekeyser.dao.ILidgeldDAO;
import willydekeyser.model.Lidgeld;
import willydekeyser.service.ILidgeldService;

@Service
public class LidgeldService implements ILidgeldService {

	@Autowired
	private ILidgeldDAO lidgeldDAO;
	
	@Override
	public List<Lidgeld> getAllLidgeld() {
		return lidgeldDAO.getAllLidgeld();
	}
	
	@Override
	public List<Lidgeld> getAllLidgeldLeden() {
		return lidgeldDAO.getAllLidgeldLeden();
	}
	
	@Override
	public List<Lidgeld> getAllLidgeldByLid(int id) {
		return lidgeldDAO.getAllLidgelByLid(id);
	}
	
	@Override
	public List<Lidgeld> getMAXLidgeldLeden() {
		return lidgeldDAO.getMAXLidgeldLeden();
	}
	
	@Override
	public Lidgeld getLidgeldById(int id) {
		return lidgeldDAO.getLidgeldById(id);
	}
	
	@Override
	public Lidgeld addLidgeld(Lidgeld lidgeld) {
		return lidgeldDAO.addLidgeld(lidgeld);
	}

	@Override
	public Lidgeld updateLidgeld(Lidgeld lidgeld) {
		return lidgeldDAO.updateLidgeld(lidgeld);
	}

	@Override
	public Integer deleteLidgeld(int id) {
		return lidgeldDAO.deleteLidgeld(id);
	}

	@Override
	public boolean lidgeldExists(int id) {
		return lidgeldDAO.lidgeldExists(id);
	}

}

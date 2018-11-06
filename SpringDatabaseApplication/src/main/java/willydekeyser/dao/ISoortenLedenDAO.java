package willydekeyser.dao;

import java.util.List;

import willydekeyser.model.SoortenLeden;

public interface ISoortenLedenDAO {

	List<SoortenLeden> getAllSoortenLeden();
	List<SoortenLeden> getAllSoortenLedenLeden();
	SoortenLeden getSoortenLedenById(int id);
	SoortenLeden addSoortenLeden(SoortenLeden soortenLeden);
	SoortenLeden updateSoortenLeden(SoortenLeden soortenLeden);
    void deleteSoortenLeden(int id);
    Boolean soortenLedenExists(int id);
    Boolean ledenExistsBySoortenledenId(int id);
	
}

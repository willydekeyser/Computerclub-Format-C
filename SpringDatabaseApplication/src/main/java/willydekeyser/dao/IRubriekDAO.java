package willydekeyser.dao;

import java.util.List;

import willydekeyser.model.Rubriek;

public interface IRubriekDAO {

	List<Rubriek> getAllRubriek();
	List<Rubriek> getAllRubriekKasboek();
	Rubriek getRubriekById(int id);
    Rubriek addRubriek(Rubriek rubriek);
    Rubriek updateRubriek(Rubriek rubriek);
    void deleteRubriek(int id);
    Boolean rubriekExists(int id);
    Boolean kasboekExistsByRubriekId(int id);
    
}

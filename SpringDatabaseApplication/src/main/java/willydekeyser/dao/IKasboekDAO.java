package willydekeyser.dao;

import java.math.BigDecimal;
import java.util.List;

import willydekeyser.model.Kasboek;
import willydekeyser.model.KasboekJaartal;

public interface IKasboekDAO {

	List<Kasboek> getAllKasboek();
	List<Kasboek> getAllKasboekRubriek();
	List<Kasboek> getAllKasboekRubriekJaarRubriek(Integer jaartal, Integer rubriekId);
	BigDecimal[] getSom();
	List<Integer> getJaartal();
	List<KasboekJaartal> getKasboekJaarRubriek();
	Kasboek getKasboekById(int id);
	Kasboek addKasboek(Kasboek kasboek);
	Kasboek updateKasboek(Kasboek kasboek);
    void deleteKasboek(int id);
    Boolean kasboekExists(int id);	
    
}

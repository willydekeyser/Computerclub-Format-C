package willydekeyser.service;

import java.math.BigDecimal;
import java.util.List;

import willydekeyser.model.Kasboek;
import willydekeyser.model.KasboekJaartal;

public interface IKasboekService {

	List<Kasboek> getAllKasboek();
	List<Kasboek> getAllKasboekRubriek();
	List<KasboekJaartal> getKasboekJaarRubriek();
	List<Kasboek> getAllKasboekRubriekJaarRubriek(Integer jaartal, Integer rubriekId);
	BigDecimal[] getSom(Integer jaar, Integer rubriekId);
	List<Integer> getJaartal();
	Kasboek getKasboekById(int id);
	Kasboek addKasboek(Kasboek kasboek);
	Kasboek updateKasboek(Kasboek kasboek);
    void deleteKasboek(int id);
    Boolean kasboekExists(int id);
        
}

package willydekeyser.dao.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import willydekeyser.model.KasboekJaartal;
import willydekeyser.model.Rubriek;

public class KasboekByIdJaarRubriekExtractor implements ResultSetExtractor<List<KasboekJaartal>>{

	@Override
	public List<KasboekJaartal> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		Map<Integer, KasboekJaartal> map = new LinkedHashMap<Integer, KasboekJaartal>();
		List<Rubriek> rubrieklijst = new ArrayList<>();
		Integer jaartalTeller = 1;
		while (rs.next()) {
			int kasboekjaartal = rs.getInt("kasboek.Jaartal");
			KasboekJaartal kasboekJaartal = map.get(kasboekjaartal);
			if(kasboekJaartal == null) {
				kasboekJaartal = new KasboekJaartal();
				rubrieklijst = new ArrayList<>();
				kasboekJaartal.setId(jaartalTeller);
				kasboekJaartal.setJaartal(kasboekjaartal);
				map.put(kasboekjaartal, kasboekJaartal);
				jaartalTeller++;
			}
			int rubriekId = rs.getInt("rubriek.Id");
			if (rubriekId > 0) {
				Rubriek rubriek = new Rubriek();
				rubriek.setId(rubriekId);
				rubriek.setRubriek(rs.getString("rubriek.Rubriek"));
				rubrieklijst.add(rubriek);
				kasboekJaartal.setRubriek(rubrieklijst);
			}
		}
		return new ArrayList<KasboekJaartal>(map.values());
	}	
}
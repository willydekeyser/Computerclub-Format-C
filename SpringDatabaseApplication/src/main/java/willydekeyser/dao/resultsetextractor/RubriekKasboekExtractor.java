package willydekeyser.dao.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import willydekeyser.model.Kasboek;
import willydekeyser.model.Rubriek;

public class RubriekKasboekExtractor implements ResultSetExtractor<List<Rubriek>>{

	@Override
	public List<Rubriek> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		Map<Integer, Rubriek> map = new LinkedHashMap<Integer, Rubriek>();
		List<Kasboek> kasboeklijst = new ArrayList<>();
		while (rs.next()) {
			int rubriekId = rs.getInt("rubriek.Id");
			
			Rubriek rubriek = map.get(rubriekId);
			if(rubriek == null) {
				rubriek = new Rubriek();
				kasboeklijst = new ArrayList<>();
				rubriek.setId(rubriekId);
				rubriek.setRubriek(rs.getString("rubriek.Rubriek"));
				map.put(rubriekId, rubriek);	
			}
			int kasboekId = rs.getInt("kasboek.Id");
			if (kasboekId > 0) {
				Kasboek kasboek = new Kasboek();
				kasboek.setId(kasboekId);
				kasboek.setRubriek(new Rubriek(rs.getInt("rubriekId"), rs.getString("rubriek.Rubriek")));
				kasboek.setJaartal(rs.getInt("kasboek.Jaartal"));
				kasboek.setRubriekId(rs.getInt("kasboek.RubriekId"));
				kasboek.setOmschrijving(rs.getString("kasboek.Omschrijving"));
				kasboek.setDatum(rs.getDate("Datum").toLocalDate());
				kasboek.setUitgaven(rs.getBigDecimal("Uitgaven"));
				kasboek.setInkomsten(rs.getBigDecimal("Inkomsten"));
				kasboeklijst.add(kasboek);
				rubriek.setKasboeken(kasboeklijst);
			}
		}
		return new ArrayList<Rubriek>(map.values());
	}	
}

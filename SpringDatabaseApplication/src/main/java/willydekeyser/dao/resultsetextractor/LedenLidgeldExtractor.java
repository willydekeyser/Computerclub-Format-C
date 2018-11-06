package willydekeyser.dao.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import willydekeyser.model.Leden;
import willydekeyser.model.Lidgeld;

public class LedenLidgeldExtractor implements ResultSetExtractor<List<Leden>>{

	@Override
	public List<Leden> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		Map<Integer, Leden> map = new LinkedHashMap<Integer, Leden>();
		List<Lidgeld> lidgeldlijst = new ArrayList<>();
		while (rs.next()) {
			int ledenId = rs.getInt("ledenlijst.Id");
			Leden leden = map.get(ledenId);
			if(leden == null) {
				leden = new Leden();
				lidgeldlijst = new ArrayList<>();
				leden.setId(ledenId);
				leden.setVoornaam(rs.getString("ledenlijst.Voornaam"));
				leden.setFamilienaam(rs.getString("ledenlijst.Familienaam"));
				leden.setStraat(rs.getString("ledenlijst.Straat"));
				leden.setNr(rs.getString("ledenlijst.Nr"));
				leden.setPostnr(rs.getString("ledenlijst.Postnr"));
				leden.setGemeente(rs.getString("ledenlijst.Gemeente"));
				leden.setTelefoonnummer(rs.getString("ledenlijst.Telefoonnummer"));
				leden.setGsmnummer(rs.getString("ledenlijst.Gsmnummer"));
				leden.setEmailadres(rs.getString("ledenlijst.Emailadres"));
				leden.setWebadres(rs.getString("ledenlijst.Webadres"));
				leden.setDatumlidgeld(rs.getDate("ledenlijst.Datumlidgeld").toLocalDate());
				leden.setSoortenledenId(rs.getInt("ledenlijst.SoortlidId"));
				leden.setOntvangMail(rs.getBoolean("ledenlijst.OntvangMail"));
				leden.setMailVlag(rs.getBoolean("ledenlijst.MailVlag"));
				map.put(ledenId, leden);	
			}
			int lidgeldId = rs.getInt("lidgeld.Id");
			if (lidgeldId > 0) {
				Lidgeld lidgeld = new Lidgeld();
				lidgeld.setId(lidgeldId);
				lidgeld.setLedenId(rs.getInt("lidgeld.Lidnr"));
				lidgeld.setDatum(rs.getDate("lidgeld.Datum").toLocalDate());
				lidgeld.setBedrag(rs.getBigDecimal("lidgeld.Bedrag"));
				lidgeldlijst.add(lidgeld);
				leden.setLidgelden(lidgeldlijst);
			}
		}
		return new ArrayList<Leden>(map.values());
	}	
}

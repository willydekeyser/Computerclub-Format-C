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
import willydekeyser.model.SoortenLeden;

public class SoortenLedenLedenExtractor implements ResultSetExtractor<List<SoortenLeden>>{

	@Override
	public List<SoortenLeden> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		Map<Integer, SoortenLeden> map = new LinkedHashMap<Integer, SoortenLeden>();
		List<Leden> ledenlijst = new ArrayList<>();
		while (rs.next()) {
			int soortenLedenId = rs.getInt("soortenleden.Id");
			SoortenLeden soortenLeden = map.get(soortenLedenId);
			if(soortenLeden == null) {
				soortenLeden = new SoortenLeden();
				ledenlijst = new ArrayList<>();
				soortenLeden.setId(soortenLedenId);
				soortenLeden.setSoortenleden(rs.getString("soortenleden.SoortenLeden"));
				map.put(soortenLedenId, soortenLeden);	
			}
			int ledenId = rs.getInt("ledenlijst.Id");
			if (ledenId > 0) {
				Leden leden = new Leden();
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
				leden.setSoortenleden(new SoortenLeden(rs.getInt("soortenleden.Id"), rs.getString("soortenleden.Soortenleden")));
				leden.setOntvangMail(rs.getBoolean("ledenlijst.OntvangMail"));
				leden.setMailVlag(rs.getBoolean("ledenlijst.MailVlag"));
				ledenlijst.add(leden);
				soortenLeden.setLedenlijsten(ledenlijst);
			}
		}
		return new ArrayList<SoortenLeden>(map.values());
	}	
}

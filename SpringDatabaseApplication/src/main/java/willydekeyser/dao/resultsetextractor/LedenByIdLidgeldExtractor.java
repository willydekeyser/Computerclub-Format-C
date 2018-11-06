package willydekeyser.dao.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import willydekeyser.model.Leden;
import willydekeyser.model.Lidgeld;
import willydekeyser.model.SoortenLeden;

public class LedenByIdLidgeldExtractor implements ResultSetExtractor<Leden>{

	@Override
	public Leden extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		Leden leden = new Leden();
		List<Lidgeld> lidgeldlijst = new ArrayList<>();
		Boolean first = true;
		while (rs.next()) {
			if(first) {
				leden = new Leden();
				lidgeldlijst = new ArrayList<>();
				leden.setId(rs.getInt("ledenlijst.Id"));
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
				first = false;
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
		return leden;
	}	
}

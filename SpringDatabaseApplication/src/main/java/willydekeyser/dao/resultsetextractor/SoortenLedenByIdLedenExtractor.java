package willydekeyser.dao.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import willydekeyser.model.Leden;
import willydekeyser.model.SoortenLeden;

public class SoortenLedenByIdLedenExtractor implements ResultSetExtractor<SoortenLeden>{

	@Override
	public SoortenLeden extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		SoortenLeden soortenLeden = new SoortenLeden();
		List<Leden> ledenlijst = new ArrayList<>();
		Boolean first = true;
		while (rs.next()) {
			if(first) {
				soortenLeden = new SoortenLeden();
				ledenlijst = new ArrayList<>();
				soortenLeden.setId(rs.getInt("soortenleden.Id"));
				soortenLeden.setSoortenleden(rs.getString("soortenleden.SoortenLeden"));
				first = false;	
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
		return soortenLeden;
	}	
}

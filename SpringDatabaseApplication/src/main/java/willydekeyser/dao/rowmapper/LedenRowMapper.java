package willydekeyser.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import willydekeyser.model.Leden;

public class LedenRowMapper implements RowMapper<Leden>{

	@Override
	public Leden mapRow(ResultSet rs, int rownum) throws SQLException {
		
		Leden leden = new Leden();
		leden.setId(rs.getInt("Id"));
		leden.setVoornaam(rs.getString("Voornaam"));
		leden.setFamilienaam(rs.getString("Familienaam"));
		leden.setStraat(rs.getString("Straat"));
		leden.setNr(rs.getString("Nr"));
		leden.setPostnr(rs.getString("Postnr"));
		leden.setGemeente(rs.getString("Gemeente"));
		leden.setTelefoonnummer(rs.getString("Telefoonnummer"));
		leden.setGsmnummer(rs.getString("Gsmnummer"));
		leden.setEmailadres(rs.getString("Emailadres"));
		leden.setWebadres(rs.getString("Webadres"));
		leden.setDatumlidgeld(rs.getDate("Datumlidgeld").toLocalDate());
		leden.setSoortenledenId(rs.getInt("SoortlidId"));
		leden.setOntvangMail(rs.getBoolean("OntvangMail"));
		leden.setMailVlag(rs.getBoolean("MailVlag"));
		return leden;
	}

}

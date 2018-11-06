package willydekeyser.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import willydekeyser.model.Leden;
import willydekeyser.model.Lidgeld;

public class LidgeldLedenRowMapper implements RowMapper<Lidgeld>{

	@Override
	public Lidgeld mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Lidgeld lidgeld = new Lidgeld();
		lidgeld.setId(rs.getInt("Id"));
		lidgeld.setLedenId(rs.getInt("Lidnr"));
		lidgeld.setLeden(new Leden(
				rs.getInt("ledenlijst.Id"), 
				rs.getString("ledenlijst.Voornaam"),
				rs.getString("ledenlijst.Familienaam"),
				rs.getString("ledenlijst.Straat"),
				rs.getString("ledenlijst.Nr"),
				rs.getString("ledenlijst.Postnr"),
				rs.getString("ledenlijst.Gemeente"),
				rs.getString("ledenlijst.Telefoonnummer"),
				rs.getString("ledenlijst.Gsmnummer"),
				rs.getString("ledenlijst.Emailadres"),
				rs.getString("ledenlijst.Webadres"),
				rs.getDate("ledenlijst.Datumlidgeld").toLocalDate(),
				rs.getInt("ledenlijst.SoortlidId"),
				rs.getBoolean("ledenlijst.OntvangMail"),
				rs.getBoolean("ledenlijst.Mailvlag")));
		lidgeld.setDatum(rs.getDate("Datum").toLocalDate());
		lidgeld.setBedrag(rs.getBigDecimal("Bedrag"));
		return lidgeld;
	}

}

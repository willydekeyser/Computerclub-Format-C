package willydekeyser.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import willydekeyser.model.Kasboek;
import willydekeyser.model.Rubriek;

public class KasboekRubriekRowMapper implements RowMapper<Kasboek>{

	@Override
	public Kasboek mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Kasboek kasboek = new Kasboek();
		kasboek.setId(rs.getInt("Id"));
		kasboek.setJaartal(rs.getInt("Jaartal"));
		kasboek.setRubriekId(rs.getInt("RubriekId"));
		kasboek.setRubriek(new Rubriek(rs.getInt("rubriek.Id"), rs.getString("rubriek.Rubriek")));
		kasboek.setOmschrijving(rs.getString("Omschrijving"));
		kasboek.setDatum(rs.getDate("Datum").toLocalDate());
		kasboek.setUitgaven(rs.getBigDecimal("Uitgaven"));
		kasboek.setInkomsten(rs.getBigDecimal("Inkomsten"));
		return kasboek;
	}

}
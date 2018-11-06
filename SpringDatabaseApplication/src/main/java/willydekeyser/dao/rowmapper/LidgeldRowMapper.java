package willydekeyser.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import willydekeyser.model.Lidgeld;

public class LidgeldRowMapper implements RowMapper<Lidgeld>{

	@Override
	public Lidgeld mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Lidgeld lidgeld = new Lidgeld();
		lidgeld.setId(rs.getInt("Id"));
		lidgeld.setLedenId(rs.getInt("Lidnr"));
		lidgeld.setDatum(rs.getDate("Datum").toLocalDate());
		lidgeld.setBedrag(rs.getBigDecimal("Bedrag"));
		return lidgeld;
	}

}

package willydekeyser.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import willydekeyser.model.Leden;

public class LedenNamenRowMapper implements RowMapper<Leden>{

	@Override
	public Leden mapRow(ResultSet rs, int rownum) throws SQLException {
		
		Leden leden = new Leden();
		leden.setId(rs.getInt("Id"));
		leden.setVoornaam(rs.getString("Voornaam"));
		leden.setFamilienaam(rs.getString("Familienaam"));
		return leden;
	}

}

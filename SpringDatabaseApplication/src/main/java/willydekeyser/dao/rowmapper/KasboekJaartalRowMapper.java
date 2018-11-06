package willydekeyser.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class KasboekJaartalRowMapper implements RowMapper<Integer> {

	@Override
	public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Integer jaarTal = new Integer(0);
		jaarTal = rs.getInt("jaartal");
		return jaarTal;
	}

}

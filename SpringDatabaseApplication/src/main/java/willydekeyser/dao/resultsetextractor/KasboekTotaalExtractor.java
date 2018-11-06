package willydekeyser.dao.resultsetextractor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class KasboekTotaalExtractor implements ResultSetExtractor<BigDecimal[]>{

	@Override
	public BigDecimal[] extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		BigDecimal uitgaven = new BigDecimal(0);
		BigDecimal inkomsten = new BigDecimal(0);
		
		while (rs.next()) {
			uitgaven = rs.getBigDecimal("uitgaven");
			inkomsten = rs.getBigDecimal("inkomsten");
		}
		
		return new BigDecimal[] {uitgaven ,inkomsten, inkomsten.subtract(uitgaven)};
	}	
}
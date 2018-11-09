package willydekeyser.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import willydekeyser.dao.IKasboekDAO;
import willydekeyser.dao.resultsetextractor.KasboekJaarRubriekExtractor;
import willydekeyser.dao.resultsetextractor.KasboekTotaalExtractor;
import willydekeyser.dao.rowmapper.KasboekJaartalRowMapper;
import willydekeyser.dao.rowmapper.KasboekRowMapper;
import willydekeyser.dao.rowmapper.KasboekRubriekRowMapper;
import willydekeyser.model.Kasboek;
import willydekeyser.model.KasboekJaartal;

@Transactional
@Repository
public class KasboekDAO implements IKasboekDAO {

	private final String sql_getAllKasboek = "SELECT kasboek.* FROM kasboek ORDER BY kasboek.Id";
	private final String sql_getKasboekById = "SELECT kasboek.*, rubriek.* FROM kasboek, rubriek WHERE kasboek.RubriekId = rubriek.Id AND kasboek.Id = ?";
	private final String sql_getAllKasboekRubriek = "SELECT kasboek.*, rubriek.* FROM kasboek, rubriek WHERE kasboek.RubriekId = rubriek.Id ORDER BY kasboek.Id";
	private final String sql_getAllKasboekRubriekJaar = "SELECT kasboek.*, rubriek.* "
			+ "FROM kasboek, rubriek "
			+ "WHERE kasboek.RubriekId = rubriek.Id AND kasboek.Jaartal = ? "
			+ "ORDER BY kasboek.Id";
	private final String sql_getAllKasboekRubriekJaarRubriek = "SELECT kasboek.*, rubriek.* "
			+ "FROM kasboek, rubriek "
			+ "WHERE kasboek.RubriekId = rubriek.Id AND kasboek.Jaartal = ? AND rubriek.Id = ? "
			+ "ORDER BY kasboek.Id";
	private final String sql_getSom = "SELECT SUM(uitgaven) AS uitgaven, SUM(inkomsten) AS inkomsten FROM kasboek";
	private final String sql_getSomJaartal = "SELECT SUM(uitgaven) AS uitgaven, SUM(inkomsten) AS inkomsten FROM kasboek WHERE Jaartal = ?";
	private final String sql_getSomRubriek = "SELECT SUM(uitgaven) AS uitgaven, SUM(inkomsten) AS inkomsten FROM kasboek WHERE Rubriek = ?";
	private final String sql_getSomJaartalRubriek = "SELECT SUM(uitgaven) AS uitgaven, SUM(inkomsten) AS inkomsten FROM kasboek WHERE Jaartal = ? AND RubriekId = ?";
	private final String sql_getKasboekJaarRubriek = "SELECT kasboek.Id, kasboek.Jaartal, rubriek.Id, rubriek.Rubriek "
			+ "FROM kasboek, rubriek WHERE kasboek.RubriekId= rubriek.Id "
			+ "GROUP BY kasboek.Jaartal, kasboek.RubriekId "
			+ "ORDER BY kasboek.Jaartal, rubriek.Rubriek";
	private final String sql_getKasboekJaartal = "SELECT DISTINCT Jaartal AS jaartal FROM kasboek";
	
	private final String sql_addKasboek = "INSERT INTO kasboek (Jaartal, RubriekId, Omschrijving, Datum, Uitgaven, Inkomsten) VALUES (?, ?, ?, ?, ?, ?)";
	private final String sql_updateKasboek = "UPDATE kasboek SET Jaartal = ?, RubriekId = ?, Omschrijving = ?, Datum = ?, Uitgaven = ?, Inkomsten = ? WHERE Id = ?";
	private final String sql_deleteKasboek = "DELETE FROM kasboek WHERE Id = ?";
	private final String sql_kasboekExists = "SELECT EXISTS(SELECT * FROM kasboek WHERE Id = ?)";
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Kasboek> getAllKasboek() {
		return jdbcTemplate.query(sql_getAllKasboek, new KasboekRowMapper());
	}
	
	@Override
	public List<Kasboek> getAllKasboekRubriek() {
		return jdbcTemplate.query(sql_getAllKasboekRubriek, new KasboekRubriekRowMapper());
	}
	
	@Override
	public List<Kasboek> getAllKasboekRubriekJaarRubriek(Integer jaartal, Integer rubriekId) {
		if(jaartal == 0) {
			return jdbcTemplate.query(sql_getAllKasboekRubriek, new KasboekRubriekRowMapper());
		} else {
			if(rubriekId == 0) {
				return jdbcTemplate.query(sql_getAllKasboekRubriekJaar, new KasboekRubriekRowMapper(), jaartal);
			} else {
				return jdbcTemplate.query(sql_getAllKasboekRubriekJaarRubriek, new KasboekRubriekRowMapper(), jaartal, rubriekId);
			}
		}
		
	}
	
	@Override
	public BigDecimal[] getSom(Integer jaar, Integer rubriekId) {
		if (jaar == 0 & rubriekId == 0) {
			return jdbcTemplate.query(sql_getSom, new KasboekTotaalExtractor());
		} else if(jaar != 0 & rubriekId == 0) {
			return jdbcTemplate.query(sql_getSomJaartal, new KasboekTotaalExtractor(), jaar);
		} else if (jaar ==0 & rubriekId != 0) {
			return jdbcTemplate.query(sql_getSomRubriek, new KasboekTotaalExtractor(), rubriekId);
		} else {
			return jdbcTemplate.query(sql_getSomJaartalRubriek, new KasboekTotaalExtractor(), jaar, rubriekId);
		}
		
	}
	
	@Override
	public List<Integer> getJaartal() {
		return jdbcTemplate.query(sql_getKasboekJaartal, new KasboekJaartalRowMapper());
	}
	
	@Override
	public List<KasboekJaartal> getKasboekJaarRubriek() {
		return jdbcTemplate.query(sql_getKasboekJaarRubriek, new KasboekJaarRubriekExtractor());
	}

	@Override
	public Kasboek getKasboekById(int id) {
		return jdbcTemplate.queryForObject(sql_getKasboekById, new KasboekRubriekRowMapper(), id);
	}

	@Override
	public Kasboek addKasboek(Kasboek kasboek) {
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql_addKasboek, Statement.RETURN_GENERATED_KEYS);
			    ps.setInt(1, kasboek.getJaartal());
			    ps.setInt(2, kasboek.getRubriek().getId());
			    ps.setString(3, kasboek.getOmschrijving());
			    ps.setDate(4, java.sql.Date.valueOf(kasboek.getDatum()));	
			    ps.setBigDecimal(5, kasboek.getUitgaven());
			    ps.setBigDecimal(6, kasboek.getInkomsten());
			    return ps;
			}	
		}, key);
	    kasboek.setId(key.getKey().intValue());
	    return kasboek;
	}

	@Override
	public Kasboek updateKasboek(Kasboek kasboek) {
		int key = jdbcTemplate.update(sql_updateKasboek, kasboek.getJaartal(), kasboek.getRubriek().getId(), kasboek.getOmschrijving(),
				kasboek.getDatum(),  kasboek.getUitgaven(), kasboek.getInkomsten(), kasboek.getId());
		if (key == 1) return kasboek;
		return null;
	}

	@Override
	public void deleteKasboek(int id) {
		jdbcTemplate.update(sql_deleteKasboek, id);
	}

	@Override
	public Boolean kasboekExists(int id) {
		Integer count = jdbcTemplate.queryForObject(sql_kasboekExists, Integer.class, id);
		if(count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
}

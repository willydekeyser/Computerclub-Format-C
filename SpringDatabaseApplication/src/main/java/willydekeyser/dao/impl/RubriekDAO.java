package willydekeyser.dao.impl;

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

import willydekeyser.dao.IRubriekDAO;
import willydekeyser.dao.resultsetextractor.RubriekByIdKasboekExtractor;
import willydekeyser.dao.resultsetextractor.RubriekKasboekExtractor;
import willydekeyser.dao.rowmapper.RubriekRowMapper;
import willydekeyser.model.Rubriek;

@Transactional
@Repository
public class RubriekDAO implements IRubriekDAO {

	private final String sql_getAllRubriek = "SELECT rubriek.* FROM rubriek";
	private final String sql_getAllRubriekKasboek = "SELECT rubriek.*, kasboek.* "
			+ "FROM rubriek LEFT JOIN kasboek ON rubriek.Id = kasboek.RubriekId";
	private final String sql_getRubriekById = "SELECT rubriek.*, kasboek.* "
			+ "FROM rubriek LEFT JOIN kasboek ON rubriek.Id = kasboek.RubriekId WHERE rubriek.Id = ?";
	private final String sql_addRubriek = "INSERT INTO rubriek (rubriek) VALUES (?)";
	private final String sql_updateRubriek = "UPDATE rubriek SET rubriek = ? WHERE Id = ?";
	private final String sql_deleteRubriek = "DELETE FROM rubriek WHERE Id = ?";
	private final String sql_rubriekExists = "SELECT count(*) FROM rubriek WHERE id = ?";
	private final String sql_kasboekExistsByRuriekId = "SELECT EXISTS(SELECT * FROM kasboek WHERE RubriekId = ?)";
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public List<Rubriek> getAllRubriek() {
		return jdbcTemplate.query(sql_getAllRubriek, new RubriekRowMapper());
	}
	
	@Override
	public List<Rubriek> getAllRubriekKasboek() {
		return jdbcTemplate.query(sql_getAllRubriekKasboek, new RubriekKasboekExtractor());
	}

	@Override
	public Rubriek getRubriekById(int id) {
		return jdbcTemplate.query(sql_getRubriekById, new RubriekByIdKasboekExtractor(), id);
	}

	@Override
	public Rubriek addRubriek(Rubriek rubriek) {
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql_addRubriek, Statement.RETURN_GENERATED_KEYS);
			    ps.setString(1, rubriek.getRubriek());
			    return ps;
			}
			
		}, key);
	    rubriek.setId(key.getKey().intValue());
	    return rubriek;
	}

	@Override
	public Rubriek updateRubriek(Rubriek rubriek) {
		int key = jdbcTemplate.update(sql_updateRubriek, rubriek.getRubriek(), rubriek.getId());
		if (key == 1) return rubriek;
		return null;
	}

	@Override
	public void deleteRubriek(int id) {
		jdbcTemplate.update(sql_deleteRubriek, id);
	}

	@Override
	public Boolean rubriekExists(int id) {
		Integer count = jdbcTemplate.queryForObject(sql_rubriekExists, Integer.class, id);
		if(count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public Boolean kasboekExistsByRubriekId(int id) {
		Integer count = jdbcTemplate.queryForObject(sql_kasboekExistsByRuriekId, Integer.class, id);
		if(count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
}

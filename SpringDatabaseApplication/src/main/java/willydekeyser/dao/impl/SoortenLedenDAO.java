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

import willydekeyser.dao.ISoortenLedenDAO;
import willydekeyser.dao.resultsetextractor.SoortenLedenByIdLedenExtractor;
import willydekeyser.dao.resultsetextractor.SoortenLedenLedenExtractor;
import willydekeyser.dao.rowmapper.SoortenLedenRowMapper;
import willydekeyser.model.SoortenLeden;

@Transactional
@Repository
public class SoortenLedenDAO implements ISoortenLedenDAO {

	private final String sql_getAllSoortenleden = "SELECT soortenleden.* FROM soortenleden";
	private final String sql_getSoortenledenById = "SELECT soortenleden.*, ledenlijst.* "
			+ "FROM soortenleden LEFT JOIN ledenlijst ON soortenleden.Id = ledenlijst.SoortlidId WHERE soortenleden.Id = ?";
	private final String sql_getAllSoortenLedenLeden = "SELECT soortenleden.*, ledenlijst.* "
			+ "FROM soortenleden LEFT JOIN ledenlijst ON soortenleden.Id = ledenlijst.SoortlidId";
	private final String sql_addSoortenleden = "INSERT INTO soortenleden (Soortenleden) VALUES (?)";
	private final String sql_updateSoortenleden = "UPDATE soortenleden SET Soortenleden = ? WHERE Id = ?";
	private final String sql_deleteSoortenleden = "DELETE FROM soortenleden WHERE Id = ?";
	private final String sql_soortenledenExists = "SELECT count(*) FROM soortenleden WHERE Id = ?";
	private final String sql_ledenExistsBySoortenledenId = "SELECT EXISTS(SELECT * FROM ledenlijst WHERE SoortlidId = ?)";
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public List<SoortenLeden> getAllSoortenLeden() {
		return jdbcTemplate.query(sql_getAllSoortenleden, new SoortenLedenRowMapper());
	}
	
	@Override
	public List<SoortenLeden> getAllSoortenLedenLeden() {
		return jdbcTemplate.query(sql_getAllSoortenLedenLeden, new SoortenLedenLedenExtractor());
	}

	@Override
	public SoortenLeden getSoortenLedenById(int id) {
		return  jdbcTemplate.query(sql_getSoortenledenById, new SoortenLedenByIdLedenExtractor(), id);
	}

	@Override
	public SoortenLeden addSoortenLeden(SoortenLeden soortenLeden) {
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql_addSoortenleden, Statement.RETURN_GENERATED_KEYS);
			    ps.setString(1, soortenLeden.getSoortenleden());
			    return ps;
			}
			
		}, key);
	    soortenLeden.setId(key.getKey().intValue());
	    return soortenLeden;
	}

	@Override
	public SoortenLeden updateSoortenLeden(SoortenLeden soortenLeden) {
		int key = jdbcTemplate.update(sql_updateSoortenleden, soortenLeden.getSoortenleden(), soortenLeden.getId());
		if (key == 1) return soortenLeden;
		return null;
	}

	@Override
	public void deleteSoortenLeden(int id) {
		jdbcTemplate.update(sql_deleteSoortenleden, id);
	}

	@Override
	public Boolean soortenLedenExists(int id) {
		Integer count = jdbcTemplate.queryForObject(sql_soortenledenExists, Integer.class, id);
		if(count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public Boolean ledenExistsBySoortenledenId(int id) {
		Integer count = jdbcTemplate.queryForObject(sql_ledenExistsBySoortenledenId, Integer.class, id);
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}
}

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

import willydekeyser.dao.ILidgeldDAO;
import willydekeyser.dao.rowmapper.LidgeldLedenRowMapper;
import willydekeyser.dao.rowmapper.LidgeldRowMapper;
import willydekeyser.model.Lidgeld;

@Transactional
@Repository
public class LidgeldDAO implements ILidgeldDAO {

	private final String sql_AllLidgeld = "SELECT * FROM lidgeld ORDER BY lidgeld.Id";
	private final String sql_AllLidgeldLeden = "SELECT * FROM lidgeld, ledenlijst WHERE lidgeld.Lidnr = ledenlijst.Id ORDER BY lidgeld.Id";
	private final String sql_AllLidgeldByLid = "SELECT * FROM lidgeld WHERE lidgeld.Lidnr = ? ORDER BY lidgeld.Id";
	private final String sql_MAXLidgeldLeden = "SELECT lidgeld.*, ledenlijst.* FROM lidgeld, ledenlijst "
			+ "WHERE (ledenlijst.Id=lidgeld.Lidnr) AND  (ledenlijst.SoortlidId = 1 OR ledenlijst.SoortlidId = 2 OR ledenlijst.SoortlidId = 3) "
			+ "AND (lidgeld.Datum = (SELECT MAX(Datum) FROM lidgeld AS lidgeld_sub WHERE lidgeld.Lidnr = lidgeld_sub.Lidnr)) "
			+ "ORDER BY lidgeld.Datum ASC, lidgeld.Id ASC";
	private final String sql_getLidgeldById = "SELECT lidgeld.*, ledenlijst.*  FROM lidgeld, ledenlijst WHERE lidgeld.Id = ? AND lidgeld.Lidnr = ledenlijst.Id";
	
	private final String sql_newLidgeld = "INSERT INTO lidgeld (Lidnr, Datum, Bedrag) value (?, ?, ?)";
	private final String sql_updateLidgeld = "UPDATE lidgeld SET Lidnr = ?, Datum = ?, Bedrag = ? WHERE id = ?";
	private final String sql_deleteLidgeld = "DELETE FROM lidgeld WHERE id = ?";
	private final String sql_lidgeldExists = "SELECT count(*) FROM lidgeld WHERE id = ?";
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public List<Lidgeld> getAllLidgeld() {
		return jdbcTemplate.query(sql_AllLidgeld, new LidgeldRowMapper());
	}
	
	@Override
	public List<Lidgeld> getAllLidgeldLeden() {
		return jdbcTemplate.query(sql_AllLidgeldLeden, new LidgeldLedenRowMapper());
	}
	
	@Override
	public List<Lidgeld> getAllLidgelByLid(int id) {
		return jdbcTemplate.query(sql_AllLidgeldByLid, new LidgeldRowMapper(), id);
	}
	
	@Override
	public List<Lidgeld> getMAXLidgeldLeden() {
		return jdbcTemplate.query(sql_MAXLidgeldLeden, new LidgeldLedenRowMapper());
	}

	@Override
	public Lidgeld getLidgeldById(int id) {
		return jdbcTemplate.queryForObject(sql_getLidgeldById, new LidgeldLedenRowMapper(), id);
	}

	@Override
	public Lidgeld addLidgeld(Lidgeld lidgeld) {
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql_newLidgeld, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, lidgeld.getLeden().getId());
				ps.setDate(2, java.sql.Date.valueOf(lidgeld.getDatum()));
				ps.setBigDecimal(3, lidgeld.getBedrag());
				return ps;
			}
		}, key);
		lidgeld.setId(key.getKey().intValue());
		return lidgeld;
	}

	@Override
	public Lidgeld updateLidgeld(Lidgeld lidgeld) {
		int key = jdbcTemplate.update(sql_updateLidgeld,lidgeld.getLeden().getId(), lidgeld.getDatum(), lidgeld.getBedrag(), lidgeld.getId());
		if (key == 1) return lidgeld;
		return null;
	}

	@Override
	public Integer deleteLidgeld(int id) {
		jdbcTemplate.update(sql_deleteLidgeld, id);
		return id;
	}

	@Override
	public boolean lidgeldExists(int id) {
		Integer count = jdbcTemplate.queryForObject(sql_lidgeldExists, Integer.class, id);
		if(count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
}

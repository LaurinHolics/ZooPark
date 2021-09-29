package ro.itschool.curs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ro.itschool.curs.connection.ConnectionFactory;
import ro.itschool.curs.enums.ConservationStatus;
import ro.itschool.curs.pojo.Snail;
import ro.itschool.curs.pojo.Zookeeper;

public class SnailDao {

	
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private ZookeeperDao zookeeperDao;

	public SnailDao() {
		connection = ConnectionFactory.getConnection();
		zookeeperDao = new ZookeeperDao();
	}
	// INSERT (Create), SELECT (Read), UPDATE (Update), and DELETE (Delete).

	public Snail getSnailById(int id) {
		try {
			preparedStatement = connection.prepareStatement("select * from snail where id = ? ;");// read
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			return mapSnailFromDB(resultSet);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	public void removeSnailByName(String name) throws SQLException {// delete
		preparedStatement = connection.prepareStatement("delete from snail where name= ? ; ");
		preparedStatement.setString(1, name);
		preparedStatement.executeUpdate();
	}

	public void removeSnaiById(int id) throws SQLException {
		preparedStatement = connection.prepareStatement("delete from snail where id= ? ; ");
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();

	}

	public Snail searchSnailByName(String name) throws SQLException {
		preparedStatement = connection.prepareStatement("select * from snail where name = ?");
		preparedStatement.setString(1, name);
		resultSet = preparedStatement.executeQuery();
		Snail snail = mapSnailFromDB(resultSet);
		if (snail.getName() == null) {
			throw new SQLException("Snail cu numele " + name + " nu exista!");
		}
		return snail;
	}

	public void updateSnail(int id, Snail snail) throws SQLException {
		preparedStatement = connection.prepareStatement(
				"update snail set age=?, birthDate=? , conservationStatus=?, id=? , mammal=?, name=?, shellColour=?, vertebrate=?, weight=?");
		preparedStatement.setInt(1, snail.getAge());
		preparedStatement.setDate(2, snail.getBirthDate());
		preparedStatement.setString(3, snail.getConservationStatus().toString());
		preparedStatement.setInt(4, snail.getId());
		preparedStatement.setBoolean(5, snail.isMammal());
		preparedStatement.setString(6, snail.getName());
		preparedStatement.setString(7, snail.getShellColour());
		preparedStatement.setBoolean(8, snail.isVertebrate());
		preparedStatement.setInt(9, snail.getWeight());

		preparedStatement.executeUpdate();
	}

	public List<Snail> getAllSnails() throws SQLException {// read
		resultSet = preparedStatement.executeQuery(" select * from snail ");
		return getResultSet(resultSet);
	}

	private List<Snail> getResultSet(ResultSet resultSet2) {
		List<Snail> snailList = new ArrayList<Snail>();
		try {
			while (resultSet.next()) {
				Snail snail = new Snail();
				snail.setAge(resultSet.getInt("age"));
				snail.setBirthDate(resultSet.getDate("birthDate"));
				snail.setConservationStatus(ConservationStatus.valueOf(resultSet.getString("conservationStatus")));
				snail.setId(resultSet.getInt("id"));
				snail.setMammal(resultSet.getBoolean("mammal"));
				snail.setName(resultSet.getString("name"));
				snail.setShellColour(resultSet.getString("shellColor"));
				snail.setVertebrate(resultSet.getBoolean("vertebrate"));
				snail.setWeight(resultSet.getInt("weight"));
				Zookeeper zookeeper = zookeeperDao.getZookeeperById(resultSet.getInt("zookeeperid"));
				snail.setZookeeper(zookeeper);
				snailList.add(snail);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return snailList;
	}

// map snail from DB
	private Snail mapSnailFromDB(ResultSet resultSet) {
		Snail snail = new Snail();
		try {
			if (resultSet.next()) {
				snail.setAge(resultSet.getInt("age"));
				snail.setBirthDate(resultSet.getDate("birthDate"));
				snail.setConservationStatus(ConservationStatus.valueOf(resultSet.getString("conservationStatus")));
				snail.setId(resultSet.getInt("id"));
				snail.setMammal(resultSet.getBoolean("mammal"));
				snail.setName(resultSet.getString("name"));
				snail.setShellColour(resultSet.getString("shellColor"));
				snail.setVertebrate(resultSet.getBoolean("vertebrate"));
				snail.setWeight(resultSet.getInt("weight"));
				Zookeeper zookeeper = zookeeperDao.getZookeeperById(resultSet.getInt("zookeeperid"));
				snail.setZookeeper(zookeeper);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return snail;
	}

}

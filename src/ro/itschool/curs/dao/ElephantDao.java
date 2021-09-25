package ro.itschool.curs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.java.Log;
import ro.itschool.curs.connection.ConnectionFactory;
import ro.itschool.curs.enums.ConservationStatus;
import ro.itschool.curs.pojo.Elephant;
import ro.itschool.curs.pojo.Zookeeper;


@Log
public class ElephantDao {

	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private ZookeeperDao zookeeperDao;

	public ElephantDao() {
		connection = ConnectionFactory.getConnection();
	}

	public Elephant getElephantById(int id) {
		Elephant elephant = new Elephant();
		try {
			preparedStatement = connection.prepareStatement("select * from elephant where id = ? ;");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			elephant = mapElephantFromDB(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return elephant;

	}

	public void createElephant(Elephant elephant) {
		try {
			preparedStatement = connection
					.prepareStatement("insert into elephant values (default, ?,?,?,?,?,?,?,?,?)");
			preparedStatement.setBoolean(1, elephant.isVertebrate());
			preparedStatement.setString(2, elephant.getConservationStatus().toString());
			preparedStatement.setBoolean(3, elephant.isMammal());
			preparedStatement.setInt(4, elephant.getAge());
			preparedStatement.setDate(5, elephant.getBirthDate());
			preparedStatement.setString(6, elephant.getName());
			preparedStatement.setInt(7, elephant.getWeight());
			preparedStatement.setInt(8, elephant.getMaxKgOfNutsPerDay());
			preparedStatement.setInt(9, elephant.getZookeeper().getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeElephantById(int id) throws SQLException {
		preparedStatement = connection.prepareStatement("delete from elephant where id= ? ; ");
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();

	}

	public Elephant searchElephantByName(String name) throws Exception {
		preparedStatement = connection.prepareStatement("select * from elephant where name = ?");
		preparedStatement.setString(1, name);
		resultSet = preparedStatement.executeQuery();
		Elephant elephant = mapElephantFromDB(resultSet);
		if (elephant.getName() == null) {
			throw new Exception("Elefantul cu numele " + name + " nu exista!");
		}
		return elephant;
	}

	public void updateElephant(int id, Elephant elephant) throws SQLException {
		preparedStatement = connection.prepareStatement(
				"update elephant set age=?, birthDate=? , conservationStatus=?, id=? , maxKgOfNutsPerDay=?, name=?, vertebrate=?, weight=?, mammal=?");
		preparedStatement.setInt(1, elephant.getAge());
		preparedStatement.setDate(2, (Date) elephant.getBirthDate());
		preparedStatement.setString(3, elephant.getConservationStatus().toString());
		preparedStatement.setInt(4, elephant.getId());
		preparedStatement.setInt(5, elephant.getMaxKgOfNutsPerDay());
		preparedStatement.setString(6, elephant.getName());
		preparedStatement.setBoolean(7, elephant.isVertebrate());
		preparedStatement.setInt(8, elephant.getWeight());
		preparedStatement.setBoolean(9, elephant.isMammal());
		preparedStatement.executeUpdate();
	}

	public List<Elephant> getAllElephants() throws SQLException {
		preparedStatement = connection.prepareStatement("select * from elephant ");
		resultSet = preparedStatement.executeQuery();
		Elephant elephant = new Elephant();
		List<Elephant> elephantList = new ArrayList<>();
		ZookeeperDao zookeeperDao = new ZookeeperDao();

		while (resultSet.next()) {
			elephant.setAge(resultSet.getInt("age"));
			elephant.setBirthDate(resultSet.getDate("birthDate"));
			elephant.setConservationStatus(ConservationStatus.valueOf(resultSet.getString("conservationStatus")));
			elephant.setId(resultSet.getInt("id"));
			elephant.setMaxKgOfNutsPerDay(resultSet.getInt("maxKgOfNutsPerDay"));
			elephant.setName(resultSet.getString("name"));
			elephant.setVertebrate(resultSet.getBoolean("vertebrate"));
			elephant.setWeight(resultSet.getInt("weight"));
			elephant.setMammal(resultSet.getBoolean("mammal"));
			log.info(" " + resultSet.getInt("zookeeperID"));
			Zookeeper zookeeper = zookeeperDao.getZookeeperById(resultSet.getInt("zookeeperID"));
			elephant.setZookeeper(zookeeper);
			elephantList.add(elephant);
		}
		return elephantList;
	}

	private Elephant mapElephantFromDB(ResultSet resultSet) {
		Elephant elephant = new Elephant();

		try {
			if (resultSet.next()) {
				elephant.setAge(resultSet.getInt("age"));
				elephant.setBirthDate(resultSet.getDate("birthDate"));
				elephant.setConservationStatus(ConservationStatus.valueOf(resultSet.getString("conservationStatus")));
				elephant.setId(resultSet.getInt("id"));
				elephant.setMaxKgOfNutsPerDay(resultSet.getInt("maxKgOfNutsPerDay"));
				elephant.setName(resultSet.getString("name"));
				elephant.setVertebrate(resultSet.getBoolean("vertebrate"));
				elephant.setWeight(resultSet.getInt("weight"));
				elephant.setMammal(resultSet.getBoolean("mammal"));

				Zookeeper zookeeper = zookeeperDao.getZookeeperById(resultSet.getInt("ZooKeeperID"));
				elephant.setZookeeper(zookeeper);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return elephant;
	}

}
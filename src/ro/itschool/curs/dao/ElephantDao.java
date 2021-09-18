package ro.itschool.curs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ro.itschool.curs.connection.ConnectionFactory;
import ro.itschool.curs.enums.ConservationStatus;
import ro.itschool.curs.pojo.Elephant;
import ro.itschool.curs.pojo.Zookeeper;

public class ElephantDao {

	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public ElephantDao() {
		connection = ConnectionFactory.getConnection();

	}

	public Elephant getElephantById(int id) {
		try {
			preparedStatement = connection.prepareStatement("select * from elephant where id = ? ;");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			return mapElephantFromDB(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	private Elephant mapElephantFromDB(ResultSet resultSet) {
		Elephant elephant = new Elephant();
		
		try {
			if (resultSet.next()) {
				elephant.setAge(resultSet.getInt("age"));
				elephant.setBirthDate(resultSet.getDate("birthDate"));
				elephant.setConservationStatus(ConservationStatus.valueOf(resultSet.getString("conservationStatus")));
				elephant.setId(resultSet.getInt("id"));
				elephant.setMammal(resultSet.getBoolean("mammal"));
				elephant.setMaxKgOfNutsPerDay(resultSet.getInt("maxKgOfNutsPerDay"));
				elephant.setName(resultSet.getString("name"));
				elephant.setVertebrate(resultSet.getBoolean("vertebrate"));
				elephant.setWeight(resultSet.getInt("weight"));				
				Zookeeper zookeeper = ZookeeperDao.getZooKeeperById(resultSet.getInt("ZooKeeperID"));
				elephant.setZookeeper(zookeeper);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return elephant;

	}

	public void insertElephant(Elephant elephant) throws SQLException {
		System.out.println("Am apelat insertElephant");
		preparedStatement.getConnection()
				.prepareStatement("insert into zoopark.elephant values(default, ?, ?, ?, ?, ?, ?, ?, ?)");
		preparedStatement.setBoolean(1, elephant.isVertebrate());
		preparedStatement.setString(2,
				elephant.getConservationStatus().toString());
		preparedStatement.setBoolean(3, elephant.isMammal());
		preparedStatement.setInt(4, elephant.getAge());
		preparedStatement.setDate(5, (Date) elephant.getBirthDate());
		preparedStatement.setString(6, elephant.getName());
		preparedStatement.setInt(7, elephant.getWeight());
		preparedStatement.setInt(8, elephant.getMaxKgOfNutsPerDay());
		preparedStatement.executeUpdate();
	}

	public Elephant readElephantById(int id) throws Exception {
		System.out.println("Am apelat readElephantById");
		preparedStatement = connection.prepareStatement("select * from elephant where if = ? ;");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		Elephant elephant = new Elephant();
		if (elephant.getName() == null) {
			throw new Exception("Elephant is empty");
		}
		return elephant;
	}
	
	public void updateElephant(int id,Elephant elephant)throws SQLException{
		preparedStatement = connection.prepareStatement("update elephant set vertebrate=?, conservationstatus=?, mammal=?, age=?, birthdate=?, name=?, weight=?, maxkgofnutsperday=? where id=?");
		preparedStatement.setBoolean(1, elephant.isVertebrate());
		preparedStatement.setString(2,
				elephant.getConservationStatus().toString());
		preparedStatement.setBoolean(3, elephant.isMammal());
		preparedStatement.setInt(4, elephant.getAge());
		preparedStatement.setDate(5, (Date) elephant.getBirthDate());
		preparedStatement.setString(6, elephant.getName());
		preparedStatement.setInt(7, elephant.getWeight());
		preparedStatement.setInt(8, elephant.getMaxKgOfNutsPerDay());
		preparedStatement.setInt(9, id);
		preparedStatement.executeUpdate();
	}
	
	public void removeCommentById(int id)throws SQLException{
		preparedStatement = connection.prepareStatement("delete from elephant where id=?;");
		preparedStatement.setInt(1, id);
		preparedStatement.executeQuery();
	}

}
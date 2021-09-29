package ro.itschool.curs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ro.itschool.curs.connection.ConnectionFactory;
import ro.itschool.curs.enums.ConservationStatus;
import ro.itschool.curs.pojo.Whale;

public class WhaleDao {

	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public WhaleDao() {
		connection = ConnectionFactory.getConnection();
	}

	public Whale getWhaleById(int id) {
		try {
			preparedStatement = connection.prepareStatement("select * from  whale where id = ? ;");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			return mapWhaleFromDB(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void removeWhaleById(int id) throws SQLException {
		preparedStatement = connection.prepareStatement("delete from whale where id= ? ; ");
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
	}

	public Whale searchWhaleByName(String name) throws Exception {
		preparedStatement = connection.prepareStatement("select * from whale where name = ?");
		preparedStatement.setString(1, name);
		resultSet = preparedStatement.executeQuery();
		Whale whale = mapWhaleFromDB(resultSet);
		if (whale.getName() == null) {
			throw new Exception("Balena cu numele " + name + " nu exista!");
		}
		return whale;
	}

	public void updateWhale(int id, Whale whale) throws SQLException {
		preparedStatement = connection.prepareStatement(
				"update elephant set age=?, birthDate=? , conservationStatus=?, id=? , mammal=?, name=?, optimalSalinityLevel=?, vertebrate=?, weight=?");
		preparedStatement.setInt(1, whale.getAge());
		preparedStatement.setDate(2, (Date) whale.getBirthDate());
		preparedStatement.setString(3, whale.getConservationStatus().toString());
		preparedStatement.setInt(4, whale.getId());
		preparedStatement.setBoolean(5, whale.isMammal());
		preparedStatement.setString(6, whale.getName());
		preparedStatement.setString(7, whale.getOptimalSalinityLevel());
		preparedStatement.setBoolean(8, whale.isVertebrate());
		preparedStatement.setInt(9, whale.getWeight());
		preparedStatement.executeUpdate();
	}

	public List<Whale> getAllWhales() throws SQLException {
		preparedStatement = connection.prepareStatement("select * from whale");
		resultSet = preparedStatement.executeQuery();
		Whale whale = new Whale();
		List<Whale> whaleList = new ArrayList<>();

		while (resultSet.next()) {
			whale.setAge(resultSet.getInt("age"));
			whale.setBirthDate(resultSet.getDate("birthDate"));
			whale.setConservationStatus(ConservationStatus.valueOf(resultSet.getString("conservationStatus")));
			whale.setId(resultSet.getInt("id"));
			whale.setMammal(resultSet.getBoolean("mammal"));
			whale.setName(resultSet.getString("name"));
			whale.setOptimalSalinityLevel(resultSet.getString("optimalSalinityLevel"));
			whale.setVertebrate(resultSet.getBoolean("vertebrate"));
			whale.setWeight(resultSet.getInt("weight"));
			whaleList.add(whale);
		}
		return whaleList;
	}

	private Whale mapWhaleFromDB(ResultSet resultSet) {
		Whale whale = new Whale();
		try {
			if (resultSet.next()) {
				whale.setAge(resultSet.getInt("age"));
				whale.setBirthDate(resultSet.getDate("birthDate"));
				whale.setConservationStatus(ConservationStatus.valueOf(resultSet.getString("conservationStatus")));
				whale.setId(resultSet.getInt("id"));
				whale.setMammal(resultSet.getBoolean("mammal"));
				whale.setName(resultSet.getString("name"));
				whale.setOptimalSalinityLevel(resultSet.getString("optimalSalinityLevel"));
				whale.setVertebrate(resultSet.getBoolean("vertebrate"));
				whale.setWeight(resultSet.getInt("weight"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return whale;
	}

	public void removeWhaleByName(String name) throws SQLException {
		preparedStatement = connection.prepareStatement("delete from whale where name= ? ; ");
		preparedStatement.setString(1, name);
		preparedStatement.executeUpdate();
	}
}

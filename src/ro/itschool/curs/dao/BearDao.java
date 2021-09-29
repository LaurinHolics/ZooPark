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
import ro.itschool.curs.pojo.Bear;

public class BearDao {
	
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public BearDao() {
		connection = ConnectionFactory.getConnection();
	}

	public Bear getBearById(int id) {
		Bear bear = new Bear();
		try {
			preparedStatement = connection.prepareStatement("select * from bear where id = ? ;");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			bear = mapBearFromDB(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bear;

	}

	public void createBear(Bear bear) {
		try {
			preparedStatement = connection.prepareStatement("insert into bear values (default, ?,?,?,?,?,?,?,?)");
			preparedStatement.setBoolean(7, bear.isVertebrate());
			preparedStatement.setString(6, bear.getConservationStatus().toString());
			preparedStatement.setBoolean(8, bear.isMammal());
			preparedStatement.setInt(2, bear.getAge());
			preparedStatement.setDate(5, bear.getBirthDate());
			preparedStatement.setString(1, bear.getName());
			preparedStatement.setInt(4, bear.getNumberOfMonthsOfHibernation());
			preparedStatement.setString(3, bear.getSpecies());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeBearById(int id) throws SQLException {
		preparedStatement = connection.prepareStatement("delete from bear where id= ? ; ");
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();

	}

	public Bear searchBearByName(String name) throws Exception {
		preparedStatement = connection.prepareStatement("select * from bear where name = ?");
		preparedStatement.setString(1, name);
		resultSet = preparedStatement.executeQuery();
		Bear bear = mapBearFromDB(resultSet);
		if (bear.getName() == null) {
			throw new Exception("Elefantul cu numele " + name + " nu exista!");
		}
		return bear;
	}

	public void updateBear(int id, Bear bear) throws SQLException {
		preparedStatement = connection.prepareStatement(
				"update bear set id=?, vertebrate=? , conservationStatus=?, mammal=?, age=?, birthDate=?, name=?, NumberOfMonthsOfHibernation=?, species=?");
		preparedStatement.setInt(4, bear.getId());
		preparedStatement.setBoolean(7, bear.isVertebrate());
		preparedStatement.setString(3, bear.getConservationStatus().toString());
		preparedStatement.setBoolean(9, bear.isMammal());
		preparedStatement.setInt(1, bear.getAge());
		preparedStatement.setDate(2, (Date) bear.getBirthDate());
		preparedStatement.setString(6, bear.getName());
		preparedStatement.setInt(5, bear.getNumberOfMonthsOfHibernation());
		preparedStatement.setString(8, bear.getSpecies());
		preparedStatement.executeUpdate();
	}

	public List<Bear> getAllBears() throws SQLException {
		preparedStatement = connection.prepareStatement("select * from bear ");
		resultSet = preparedStatement.executeQuery();
		Bear bear = new Bear();
		List<Bear> BearList = new ArrayList<>();

		while (resultSet.next()) {
			bear.setId(resultSet.getInt("id"));
			bear.setVertebrate(resultSet.getBoolean("vertebrate"));
			bear.setConservationStatus(ConservationStatus.valueOf(resultSet.getString("conservationStatus")));
			bear.setMammal(resultSet.getBoolean("mammal"));
			bear.setAge(resultSet.getInt("age"));
			bear.setBirthDate(resultSet.getDate("birthDate"));
			bear.setName(resultSet.getString("name"));
			bear.setNumberOfMonthsOfHibernation(resultSet.getInt("NumberOfMonthsOfHibernation"));
			bear.setSpecies(resultSet.getString("species"));

		}
		return BearList;
	}

	private Bear mapBearFromDB(ResultSet resultSet) {
		Bear bear = new Bear();

		try {
			if (resultSet.next()) {
				bear.setId(resultSet.getInt("id"));
				bear.setVertebrate(resultSet.getBoolean("vertebrate"));
				bear.setConservationStatus(ConservationStatus.valueOf(resultSet.getString("conservationStatus")));
				bear.setMammal(resultSet.getBoolean("mammal"));
				bear.setAge(resultSet.getInt("age"));
				bear.setBirthDate(resultSet.getDate("birthDate"));
				bear.setName(resultSet.getString("name"));
				bear.setNumberOfMonthsOfHibernation(resultSet.getInt("NumberOfMonthsOfHibernation"));
				bear.setSpecies(resultSet.getString("species"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bear;
	}

}

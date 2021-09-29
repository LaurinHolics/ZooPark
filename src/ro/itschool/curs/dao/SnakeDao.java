package ro.itschool.curs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ro.itschool.curs.connection.ConnectionFactory;
import ro.itschool.curs.enums.ConservationStatus;
import ro.itschool.curs.pojo.Elephant;
import ro.itschool.curs.pojo.Snake;
import ro.itschool.curs.pojo.Zookeeper;

public class SnakeDao {

	
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public SnakeDao() {
		connection = ConnectionFactory.getConnection();
	}

	public Snake getSnakeById(int id) {
		try {
			preparedStatement = connection.prepareStatement("select * from snake where id = ? ;");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			return mapSnakeFromDB(resultSet);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;

	}

	public void removeSnakeById(int id) throws SQLException {
		preparedStatement = connection.prepareStatement("delete from snake where id= ? ; ");
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
	}

	public Snake searchSnakeByName(String name) throws Exception {
		preparedStatement = connection.prepareStatement("select * from snake where name = ?");
		preparedStatement.setString(1, name);
		resultSet = preparedStatement.executeQuery();
		Snake snake = mapSnakeFromDB(resultSet);
		if (snake.getName() == null) {
			throw new Exception("Sarpele cu numele " + name + " nu exista!");
		}
		return snake;
	}

	public void updateSnake(int id, Snake snake) throws SQLException {
		preparedStatement = connection.prepareStatement(
				"update elephant set age=?, birthDate=? , conservationStatus=?, id=? , length=?, mammal=?, name=?, venomous=?, vertebrate=?, weight=?");
		preparedStatement.setInt(1, snake.getAge());
		preparedStatement.setDate(2, (Date) snake.getBirthDate());
		preparedStatement.setString(3, snake.getConservationStatus().toString());
		preparedStatement.setInt(4, snake.getId());
		preparedStatement.setInt(5, snake.getLength());
		preparedStatement.setBoolean(6, snake.isMammal());
		preparedStatement.setString(7, snake.getName());
		preparedStatement.setBoolean(8, snake.isVenomous());
		preparedStatement.setBoolean(9, snake.isVertebrate());
		preparedStatement.setInt(10, snake.getWeight());
		preparedStatement.executeUpdate();
	}

	public List<Snake> getAllSnakes() throws SQLException {
		preparedStatement = connection.prepareStatement("select * from snake ");
		resultSet = preparedStatement.executeQuery();
		Snake snake = new Snake();
		List<Snake> snakeList = new ArrayList<>();

		while (resultSet.next()) {
			snake.setAge(resultSet.getInt("age"));
			snake.setBirthDate(resultSet.getDate("birthDate"));
			snake.setConservationStatus(ConservationStatus.valueOf(resultSet.getString("conservationStatus")));
			snake.setId(resultSet.getInt("id"));
			snake.setLenght(resultSet.getInt("length"));
			snake.setMammal(resultSet.getBoolean("mammal"));
			snake.setVenomous(resultSet.getBoolean("venomous"));
			snake.setVertebrate(resultSet.getBoolean("vertebrate"));
			snake.setWeight(resultSet.getInt("weight"));
			
			snakeList.add(snake);
		}
		return snakeList;
	}
	private Snake mapSnakeFromDB(ResultSet resultSet) {
		Snake snake = new Snake();
		try {
			if (resultSet.next()) {
				snake.setAge(resultSet.getInt("age"));
				snake.setBirthDate(resultSet.getDate("birthDate"));
				snake.setConservationStatus(ConservationStatus.valueOf(resultSet.getString("conservationStatus")));
				snake.setId(resultSet.getInt("id"));
				snake.setLenght(resultSet.getInt("length"));
				snake.setMammal(resultSet.getBoolean("mammal"));
				snake.setName(resultSet.getString("name"));
				snake.setVenomous(resultSet.getBoolean("venomous"));
				snake.setVertebrate(resultSet.getBoolean("vertebrate"));
				snake.setWeight(resultSet.getInt("weight"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return snake;
	}
}

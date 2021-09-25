package ro.itschool.curs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.java.Log;
import ro.itschool.curs.connection.ConnectionFactory;
import ro.itschool.curs.pojo.Zookeeper;

@Log
public class ZookeeperDao {

	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public ZookeeperDao() {
		connection = ConnectionFactory.getConnection();
		log.info("Am apelat constructorul ZooKeeperDAO");
	}

	public Zookeeper mapZookeeperFromDB(ResultSet resultSet) {
		Zookeeper zookeeper = new Zookeeper();
		try {
			if (resultSet.next()) {
				zookeeper.setAge(resultSet.getInt("age"));
				zookeeper.setGender(resultSet.getString("gender"));
				zookeeper.setName(resultSet.getString("name"));
				zookeeper.setId(resultSet.getInt("id"));
				zookeeper.setWeight(resultSet.getInt("weight"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info(zookeeper.toString());
		return zookeeper;
	}

	public void createZookeeper(Zookeeper zookeeper) {
		try {
			preparedStatement = connection.prepareStatement("insert into zookeeper values (default, ?,?,?,?)");
			preparedStatement.setString(1, zookeeper.getName());
			preparedStatement.setInt(2, zookeeper.getAge());
			preparedStatement.setInt(3, zookeeper.getWeight());
			preparedStatement.setString(4, zookeeper.getGender());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeZookeeperById(int id) throws SQLException {
		preparedStatement = connection.prepareStatement("delete from zookeeper where id= ? ; ");
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();

	}

	public void removeZookeeperByName(String name) throws SQLException {
		preparedStatement = connection.prepareStatement("delete from zookeeper where name= ? ; ");
		preparedStatement.setString(1, name);
		preparedStatement.executeUpdate();

	}

	public void updateZookeeper(int id, Zookeeper zookeeper) throws SQLException {
		preparedStatement = connection.prepareStatement("update zookeeper set id=?, age=?, name=?, weight=?, gender=?");
		preparedStatement.setInt(1, zookeeper.getId());
		preparedStatement.setInt(2, zookeeper.getAge());
		preparedStatement.setString(3, zookeeper.getName());
		preparedStatement.setInt(4, zookeeper.getWeight());
		preparedStatement.setString(5, zookeeper.getGender());
		preparedStatement.executeUpdate();
	}

	public List<Zookeeper> getAllZookeepers() throws SQLException {
		preparedStatement = connection.prepareStatement("select * from zookeeper ");
		resultSet = preparedStatement.executeQuery();
		Zookeeper zookeeper = new Zookeeper();
		List<Zookeeper> zookeeperList = new ArrayList<>();

		while (resultSet.next()) {
			zookeeper.setAge(resultSet.getInt("age"));
			zookeeper.setId(resultSet.getInt("id"));
			zookeeper.setName(resultSet.getString("name"));
			zookeeper.setWeight(resultSet.getInt("weight"));
			zookeeper.setGender(resultSet.getString("gender"));
			zookeeperList.add(zookeeper);
		}
		return zookeeperList;
	}

	public Zookeeper getZookeeperById(int id) throws SQLException {

		preparedStatement = connection.prepareStatement("select * from zookeeper where id = ? ;");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		return mapZookeeperFromDB(resultSet);

	}

	public Zookeeper getZookeeperByName(String name) throws SQLException {

		preparedStatement = connection.prepareStatement("select * from zookeeper where name = ? ;");
		preparedStatement.setString(1, name);
		resultSet = preparedStatement.executeQuery();
		return mapZookeeperFromDB(resultSet);

	}

}
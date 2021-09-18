package ro.itschool.curs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.extern.java.Log;
import ro.itschool.curs.connection.ConnectionFactory;
import ro.itschool.curs.enums.ConservationStatus;
import ro.itschool.curs.pojo.Elephant;
import ro.itschool.curs.pojo.Zookeeper;

@Log
public class ZookeeperDao {

	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public ZookeeperDao() {
		connection = ConnectionFactory.getConnection();
		log.info("Am apelat constructorul ZookeeperDao");
	}

	public Zookeeper getZookeeperById(int id) {
		try {
			preparedStatement = connection.prepareStatement("select * from zookeeper where id = ? ;");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			return mapZookeeperFromDB(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	private Zookeeper mapZookeeperFromDB(ResultSet resultSet) {
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
		return zookeeper;

	}

}
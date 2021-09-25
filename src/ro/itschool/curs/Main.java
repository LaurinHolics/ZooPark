package ro.itschool.curs;

import java.sql.SQLException;

import ro.itschool.curs.dao.ElephantDao;
import ro.itschool.curs.dao.ZookeeperDao;
import ro.itschool.curs.enums.ConservationStatus;
import ro.itschool.curs.pojo.Elephant;

public class Main {

	public static void main(String[] args) throws SQLException {
		ElephantDao elephantDao = new ElephantDao();
		ZookeeperDao zookeeperDao = new ZookeeperDao();
//		System.out.println(elephantDao.getElephantById(1));
//		System.out.println(elephantDao.getAllElephants());
		Elephant e = new Elephant();
		e.setAge(10);
		e.setBirthDate(new java.sql.Date(System.currentTimeMillis()));
		e.setConservationStatus(ConservationStatus.LC);
		e.setMammal(true);
		e.setMaxKgOfNutsPerDay(8);
		e.setName("Mimoo");
		e.setVertebrate(true);
		e.setWeight(999);
		e.setZookeeper(zookeeperDao.getZookeeperById(1));
		elephantDao.createElephant(e);
		
	}

}

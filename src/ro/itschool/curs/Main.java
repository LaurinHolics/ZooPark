package ro.itschool.curs;

import java.sql.SQLException;

import ro.itschool.curs.dao.ElephantDao;




public class Main {

	public static void main(String[] args) throws SQLException {
		ElephantDao elephantDao = new ElephantDao();
		
//		System.out.println(elephantDao.getElephantById(1));
		System.out.println(elephantDao.getAllElephants());
	}

}

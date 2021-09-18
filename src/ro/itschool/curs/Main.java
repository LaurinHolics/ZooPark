package ro.itschool.curs;

import ro.itschool.curs.dao.ElephantDao;

public class Main {

	public static void main(String[] args) {
		ElephantDao dumbo = new ElephantDao();
		System.out.println(dumbo.getElephantById(1));

	}

}

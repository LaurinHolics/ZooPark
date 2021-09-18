package ro.itschool.curs.pojo;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper=true, includeFieldNames=true)
@Getter
@Setter

public class Zookeeper {
	
	private String name;
	private int weight;
	private int age;
	private String gender;
	private int id;
	
	

}

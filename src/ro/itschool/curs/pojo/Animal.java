package ro.itschool.curs.pojo;

import java.sql.Date;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ro.itschool.curs.enums.ConservationStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Animal {
	
	private boolean vertebrate;
	private ConservationStatus conservationStatus;
	private boolean mammal;
	private int age;
	private Date birthDate;
	private int id;
	private String name;
	private int weight;

}

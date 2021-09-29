package ro.itschool.curs.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Bear extends Animal{
	
	private int numberOfMonthsOfHibernation;
	private String species;

}

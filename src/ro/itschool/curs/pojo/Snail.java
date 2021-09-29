package ro.itschool.curs.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Snail extends Animal{

	private String shellColour;
	private Zookeeper zookeeper;
	@Override
	public String toString() {
		return "\nSnail " + super.toString() + " shellColour=" + shellColour + ", zookeeper=" + zookeeper ;
	}
}

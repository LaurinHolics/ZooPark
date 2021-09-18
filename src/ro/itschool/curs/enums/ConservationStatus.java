package ro.itschool.curs.enums;

public enum ConservationStatus {
	
	EX("Extinct"),
	EW("Extinct in the wild"),
	CR("Critically Endangered"),
	EN("Endangered"),
	VU("Vulnerable"),
	NT("Near Threatened"),
	CD("Conservation Dependent"),
	LC("Least Concern"); 
	
	public final String label;
	
	private ConservationStatus(String label) {
		this.label = label;
	}
	

}

package willydekeyser.model;

import java.util.ArrayList;
import java.util.List;

public class SoortenLeden {

	private Integer id;
    private String soortenleden;
    private List<Leden> leden = new ArrayList<>();
    
    public SoortenLeden() {
		
	}
    
    public SoortenLeden(Integer id, String soortenleden) {
		this.id = id;
		this.soortenleden = soortenleden;
	}
    
	public SoortenLeden(Integer id, String soortenleden, List<Leden> leden) {
	
		this.id = id;
		this.soortenleden = soortenleden;
		this.leden = leden;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSoortenleden() {
		return soortenleden;
	}
	public void setSoortenleden(String soortenleden) {
		this.soortenleden = soortenleden;
	}
	public List<Leden> getLedenlijsten() {
		return leden;
	}
	public void setLedenlijsten(List<Leden> ledenlijsten) {
		this.leden = ledenlijsten;
	}
	@Override
	public String toString() {
		return "SoortenLeden [id=" + id + ", soortenleden=" + soortenleden + ", ledenlijsten=" + leden + "]";
	}
    
}

package willydekeyser.model;

import java.util.ArrayList;
import java.util.List;

public class SoortLeden {
	
	private Integer id;
    private String soortenleden;
    private Boolean actief;
    
    private List<SoortLeden> soort = new ArrayList<>();
	
    public SoortLeden() {
	}

	public SoortLeden(Integer id, String soortenleden, Boolean actief) {
		this.id = id;
		this.soortenleden = soortenleden;
		this.actief = actief;
	}
	
	public List<SoortLeden> SoortLedenLijst() {
		soort.add(new SoortLeden(1,"Leden",true));
		soort.add(new SoortLeden(2,"Werkende leden",false));
		soort.add(new SoortLeden(3,"Bestuursleden",false));
		soort.add(new SoortLeden(4,"Geen leden",false));
		soort.add(new SoortLeden(5,"Iedereen",false));
		return soort;
	}
	
	public List<SoortLeden> SoortLedenLijst(Integer id) {
		switch (id) {
		case 1:
			return setLeden(true);
		case 2:
			return setWerkendeLeden(true);
		case 3:
			return setBestuursLeden(true);
		case 4:
			return setGeenLeden(true);
		case 5:
			return setIedereen(true);
		default:
			return setLeden(true);
		}
	}

	public List<SoortLeden> setLeden(Boolean actief) {
		soort.set(0, new SoortLeden(1,"Leden", actief));
		soort.set(1, new SoortLeden(2,"Werkende leden",false));
		soort.set(2, new SoortLeden(3,"Bestuursleden",false));
		soort.set(3, new SoortLeden(4,"Geen leden",false));
		soort.set(4, new SoortLeden(5,"Iedereen",false));
		return soort;
	}
	
	public List<SoortLeden> setWerkendeLeden(Boolean actief) {
		soort.set(0, new SoortLeden(1,"Leden", false));
		soort.set(1, new SoortLeden(2,"Werkende leden",actief));
		soort.set(2, new SoortLeden(3,"Bestuursleden",false));
		soort.set(3, new SoortLeden(4,"Geen leden",false));
		soort.set(4, new SoortLeden(5,"Iedereen",false));
		return soort;
	}
	
	public List<SoortLeden> setBestuursLeden(Boolean actief) {
		soort.set(0, new SoortLeden(1,"Leden", false));
		soort.set(1, new SoortLeden(2,"Werkende leden",false));
		soort.set(2, new SoortLeden(3,"Bestuursleden",actief));
		soort.set(3, new SoortLeden(4,"Geen leden",false));
		soort.set(4, new SoortLeden(5,"Iedereen",false));
		return soort;
	}
	
	public List<SoortLeden> setGeenLeden(Boolean actief) {
		soort.set(0, new SoortLeden(1,"Leden", false));
		soort.set(1, new SoortLeden(2,"Werkende leden",false));
		soort.set(2, new SoortLeden(3,"Bestuursleden",false));
		soort.set(3, new SoortLeden(4,"Geen leden",actief));
		soort.set(4, new SoortLeden(5,"Iedereen",false));
		return soort;
	}
	
	public List<SoortLeden> setIedereen(Boolean actief) {
		soort.set(0, new SoortLeden(1,"Leden", false));
		soort.set(1, new SoortLeden(2,"Werkende leden",false));
		soort.set(2, new SoortLeden(3,"Bestuursleden",false));
		soort.set(3, new SoortLeden(4,"Geen leden",false));
		soort.set(4, new SoortLeden(5,"Iedereen",actief));
		return soort;
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

	public Boolean getActief() {
		return actief;
	}

	public void setActief(Boolean actief) {
		this.actief = actief;
	}

	public List<SoortLeden> getSoort() {
		return soort;
	}

	public void setSoort(List<SoortLeden> soort) {
		this.soort = soort;
	}

	@Override
	public String toString() {
		return "SoortLeden [id=" + id + ", soortenleden=" + soortenleden + ", actief=" + actief + "]";
	}
    
}

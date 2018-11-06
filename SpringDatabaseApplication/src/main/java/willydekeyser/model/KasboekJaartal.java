package willydekeyser.model;

import java.util.ArrayList;
import java.util.List;

public class KasboekJaartal {
	
	private Integer id;
    private Integer jaartal;
    private List<Rubriek> rubriek = new ArrayList<>();
    
    public KasboekJaartal() {
		
	}
    
	public KasboekJaartal(Integer id, Integer jaartal) {
		this.id = id;
		this.jaartal = jaartal;
	}

	public KasboekJaartal(Integer id, Integer jaartal, List<Rubriek> rubriek) {
		this.id = id;
		this.jaartal = jaartal;
		this.rubriek = rubriek;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getJaartal() {
		return jaartal;
	}
	
	public void setJaartal(Integer jaartal) {
		this.jaartal = jaartal;
	}

	public List<Rubriek> getRubriek() {
		return rubriek;
	}

	public void setRubriek(List<Rubriek> rubriek) {
		this.rubriek = rubriek;
	}

	@Override
	public String toString() {
		return "Jaartal [id=" + id + ", jaartal=" + jaartal + ", rubriek=" + rubriek + "]";
	}
    
}

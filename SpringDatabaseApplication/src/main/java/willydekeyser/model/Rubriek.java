package willydekeyser.model;

import java.util.ArrayList;
import java.util.List;

public class Rubriek {

	private Integer id;
    private String rubriek;
    private List<Kasboek> kasboeken = new ArrayList<>();
    
	public Rubriek() {
		
	}

	public Rubriek(Integer id, String rubriek) {
		this.id = id;
		this.rubriek = rubriek;
	}

	public Rubriek(Integer id, String rubriek, List<Kasboek> kasboeken) {
		this.id = id;
		this.rubriek = rubriek;
		this.kasboeken = kasboeken;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRubriek() {
		return rubriek;
	}

	public void setRubriek(String rubriek) {
		this.rubriek = rubriek;
	}

	public List<Kasboek> getKasboeken() {
		return kasboeken;
	}

	public void setKasboeken(List<Kasboek> kasboeken) {
		this.kasboeken = kasboeken;
	}

	@Override
	public String toString() {
		return "Rubriek [id=" + id + ", rubriek=" + rubriek + ", kasboeken=" + kasboeken + "]";
	}
    
}

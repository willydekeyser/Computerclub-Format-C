package willydekeyser.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class Lidgeld {

	private Integer id;
    private Leden leden;
    private Integer ledenId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datum;
    private BigDecimal bedrag;
    
	public Lidgeld() {
		
	}

	public Lidgeld(Integer id, Leden leden, LocalDate datum, BigDecimal bedrag) {
		this.id = id;
		this.leden = leden;
		this.datum = datum;
		this.bedrag = bedrag;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Leden getLeden() {
		return leden;
	}

	public void setLeden(Leden leden) {
		this.leden = leden;
	}

	public Integer getLedenId() {
		return ledenId;
	}

	public void setLedenId(Integer ledenId) {
		this.ledenId = ledenId;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public BigDecimal getBedrag() {
		return bedrag;
	}

	public void setBedrag(BigDecimal bedrag) {
		this.bedrag = bedrag;
	}

	@Override
	public String toString() {
		return "Lidgeld [id=" + id + ", leden=" + leden + ", datum=" + datum + ", bedrag=" + bedrag + "]";
	}
        
}

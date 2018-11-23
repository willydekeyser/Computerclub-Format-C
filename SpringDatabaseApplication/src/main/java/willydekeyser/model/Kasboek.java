package willydekeyser.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
public class Kasboek {

	private Integer id;
    private Rubriek rubriek;
    private Integer rubriekId;
    @NotNull(message="Jaar invullen")
    private Integer jaartal;
    private String omschrijving;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datum;
    private BigDecimal uitgaven;
    private BigDecimal inkomsten;
    
	public Kasboek() {
		
	}
	
	public Kasboek(Integer id, Rubriek rubriek, Integer jaartal, String omschrijving, LocalDate datum, BigDecimal uitgaven,
			BigDecimal inkomsten) {
		this.id = id;
		this.rubriek = rubriek;
		this.jaartal = jaartal;
		this.omschrijving = omschrijving;
		this.datum = datum;
		this.uitgaven = uitgaven;
		this.inkomsten = inkomsten;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Rubriek getRubriek() {
		return rubriek;
	}
	
	public void setRubriek(Rubriek rubriek) {
		this.rubriek = rubriek;
	}
	
	public Integer getRubriekId() {
		return rubriekId;
	}

	public void setRubriekId(Integer rubriekId) {
		this.rubriekId = rubriekId;
	}

	public Integer getJaartal() {
		return jaartal;
	}
	
	public void setJaartal(Integer jaartal) {
		this.jaartal = jaartal;
	}
	
	public String getOmschrijving() {
		return omschrijving;
	}
	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}
	
	public LocalDate getDatum() {
		return datum;
	}
	
	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}
	
	public BigDecimal getUitgaven() {
		return uitgaven;
	}
	
	public void setUitgaven(BigDecimal uitgaven) {
		this.uitgaven = uitgaven;
	}
	
	public BigDecimal getInkomsten() {
		return inkomsten;
	}
	
	public void setInkomsten(BigDecimal inkomsten) {
		this.inkomsten = inkomsten;
	}
	
	@Override
	public String toString() {
		return "Kasboek [id=" + id + ", rubriek=" + rubriek + ", jaartal=" + jaartal + ", omschrijving=" + omschrijving
				+ ", datum=" + datum + ", uitgaven=" + uitgaven + ", inkomsten=" + inkomsten + "]";
	}
    
}

package willydekeyser.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class Leden {

	private Integer id;
	@Size(min=2, max=40, message="Voornaam invullen!")
    private String voornaam;
	@Size(min=2, max=40, message="Familienaam invullen!")
    private String familienaam;
    private String straat;
    private String nr;
    private String postnr;
    private String gemeente;
    private String telefoonnummer;
    private String gsmnummer;
    private String emailadres;
    private String webadres;
    @NotNull(message="Datum invullen")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datumlidgeld;
    private boolean ontvangMail;
    private boolean mailVlag;
    @NotNull(message="Soort kiezen!")
    private SoortenLeden soortenleden;
    private Integer soortenledenId;
    private List<Lidgeld> lidgelden = new ArrayList<>();
    
    public Leden() {
		
	}
    
    public Leden(Integer id, String voornaam, String familienaam, String straat, String nr, String postnr,
			String gemeente, String telefoonnummer, String gsmnummer, String emailadres, String webadres,
			LocalDate datumlidgeld, Integer soortenledenId, boolean ontvangMail, boolean mailVlag) {
		this.id = id;
		this.voornaam = voornaam;
		this.familienaam = familienaam;
		this.straat = straat;
		this.nr = nr;
		this.postnr = postnr;
		this.gemeente = gemeente;
		this.telefoonnummer = telefoonnummer;
		this.gsmnummer = gsmnummer;
		this.emailadres = emailadres;
		this.webadres = webadres;
		this.datumlidgeld = datumlidgeld;
		this.ontvangMail = ontvangMail;
		this.mailVlag = mailVlag;
		this.soortenledenId = soortenledenId;
	}

	public Leden(Integer id, String voornaam, String familienaam, String straat, String nr, String postnr,
			String gemeente, String telefoonnummer, String gsmnummer, String emailadres, String webadres,
			LocalDate datumlidgeld, boolean ontvangMail, boolean mailVlag, SoortenLeden soortenleden,
			List<Lidgeld> lidgelden) {
		this.id = id;
		this.voornaam = voornaam;
		this.familienaam = familienaam;
		this.straat = straat;
		this.nr = nr;
		this.postnr = postnr;
		this.gemeente = gemeente;
		this.telefoonnummer = telefoonnummer;
		this.gsmnummer = gsmnummer;
		this.emailadres = emailadres;
		this.webadres = webadres;
		this.datumlidgeld = datumlidgeld;
		this.ontvangMail = ontvangMail;
		this.mailVlag = mailVlag;
		this.soortenleden = soortenleden;
		this.lidgelden = lidgelden;
	}
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getVoornaam() {
		return voornaam;
	}
	
	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}
	
	public String getFamilienaam() {
		return familienaam;
	}
	
	public void setFamilienaam(String familienaam) {
		this.familienaam = familienaam;
	}
	
	public String getStraat() {
		return straat;
	}
	
	public void setStraat(String straat) {
		this.straat = straat;
	}
	
	public String getNr() {
		return nr;
	}
	
	public void setNr(String nr) {
		this.nr = nr;
	}
	
	public String getPostnr() {
		return postnr;
	}
	
	public void setPostnr(String postnr) {
		this.postnr = postnr;
	}
	
	public String getGemeente() {
		return gemeente;
	}
	
	public void setGemeente(String gemeente) {
		this.gemeente = gemeente;
	}
	
	public String getTelefoonnummer() {
		return telefoonnummer;
	}
	
	public void setTelefoonnummer(String telefoonnummer) {
		this.telefoonnummer = telefoonnummer;
	}
	
	public String getGsmnummer() {
		return gsmnummer;
	}
	
	public void setGsmnummer(String gsmnummer) {
		this.gsmnummer = gsmnummer;
	}
	
	public String getEmailadres() {
		return emailadres;
	}
	
	public void setEmailadres(String emailadres) {
		this.emailadres = emailadres;
	}
	
	public String getWebadres() {
		return webadres;
	}
	
	public void setWebadres(String webadres) {
		this.webadres = webadres;
	}
	
	public LocalDate getDatumlidgeld() {
		return datumlidgeld;
	}
	
	public void setDatumlidgeld(LocalDate datumlidgeld) {
		this.datumlidgeld = datumlidgeld;
	}
	
	public boolean isOntvangMail() {
		return ontvangMail;
	}
	
	public void setOntvangMail(boolean ontvangMail) {
		this.ontvangMail = ontvangMail;
	}
	
	public boolean isMailVlag() {
		return mailVlag;
	}
	
	public void setMailVlag(boolean mailVlag) {
		this.mailVlag = mailVlag;
	}
	
	public SoortenLeden getSoortenleden() {
		return soortenleden;
	}
	
	public void setSoortenleden(SoortenLeden soortenleden) {
		this.soortenleden = soortenleden;
	}

	public Integer getSoortenledenId() {
		return soortenledenId;
	}

	public void setSoortenledenId(Integer soortenledenId) {
		this.soortenledenId = soortenledenId;
	}

	public List<Lidgeld> getLidgelden() {
		return lidgelden;
	}
	
	public void setLidgelden(List<Lidgeld> lidgelden) {
		this.lidgelden = lidgelden;
	}
	
	@Override
	public String toString() {
		return "Leden [id=" + id + ", voornaam=" + voornaam + ", familienaam=" + familienaam + ", straat=" + straat
				+ ", nr=" + nr + ", postnr=" + postnr + ", gemeente=" + gemeente + ", telefoonnummer=" + telefoonnummer
				+ ", gsmnummer=" + gsmnummer + ", emailadres=" + emailadres + ", webadres=" + webadres
				+ ", datumlidgeld=" + datumlidgeld + ", ontvangMail=" + ontvangMail + ", mailVlag=" + mailVlag
				+ ", soortenleden=" + soortenleden + ", lidgelden=" + lidgelden + ", getId()=" + getId()
				+ ", getVoornaam()=" + getVoornaam() + ", getFamilienaam()=" + getFamilienaam() + ", getStraat()="
				+ getStraat() + ", getNr()=" + getNr() + ", getPostnr()=" + getPostnr() + ", getGemeente()="
				+ getGemeente() + ", getTelefoonnummer()=" + getTelefoonnummer() + ", getGsmnummer()=" + getGsmnummer()
				+ ", getEmailadres()=" + getEmailadres() + ", getWebadres()=" + getWebadres() + ", getDatumlidgeld()="
				+ getDatumlidgeld() + ", isOntvangMail()=" + isOntvangMail() + ", isMailVlag()=" + isMailVlag()
				+ ", getSoortenleden()=" + getSoortenleden() + ", getLidgelden()=" + getLidgelden() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
    
}

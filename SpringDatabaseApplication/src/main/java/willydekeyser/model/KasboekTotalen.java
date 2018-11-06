package willydekeyser.model;

import java.math.BigDecimal;

public class KasboekTotalen {

    private BigDecimal uitgaven;
    private BigDecimal inkomsten;
        
	public KasboekTotalen(BigDecimal uitgaven, BigDecimal inkomsten) {
		this.uitgaven = uitgaven;
		this.inkomsten = inkomsten;
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
	
	public BigDecimal getTotaal() {
		return inkomsten.subtract(uitgaven);
	}

	@Override
	public String toString() {
		return "KasboekTotalen [uitgaven=" + uitgaven + ", inkomsten=" + inkomsten + "]";
	}
	
}

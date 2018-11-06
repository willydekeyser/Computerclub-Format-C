package willydekeyser.sendmail.model;

public class Mail {

	private String to;
	private String subject;
	private String content;

    public Mail(String to, String subjct, String content) {
        this.to = to;
        this.subject = subjct;
        this.content = content;
    }

    public void setTo(String to) {
		this.to = to;
	}
    
    public String getTo() {
        return to;
    }

    public void setSubject(String subject) {
		this.subject = subject;
	}
    
    public String getSubject() {
        return subject;
    }

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
        return content;
    }
}

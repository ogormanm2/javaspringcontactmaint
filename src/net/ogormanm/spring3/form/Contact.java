package net.ogormanm.spring3.form;

public class Contact {
	private int contact_id;
	private String firstname;
    private String lastname;
    private String email;
    private String phone;
 
    public Contact() {
    }
 
    public Contact(int contact_id, String firstname, String lastname, String email, String phone) {
    	this.contact_id = contact_id;
    	this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }
    
  //.. getter and setter for all above fields.
    public String getEmail() {
		return email;
	}
    public String getPhone() {
		return phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public int getContact_id() {
		return contact_id;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public void setContact_id(int contact_id) {
		this.contact_id = contact_id;
	}
}

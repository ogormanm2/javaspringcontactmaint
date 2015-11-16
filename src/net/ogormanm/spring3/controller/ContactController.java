package net.ogormanm.spring3.controller;

import java.util.ArrayList;
import java.util.List;
 
import net.ogormanm.spring3.form.Contact;
import net.ogormanm.spring3.form.ContactForm;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.sql.DataSource;

@Controller
public class ContactController {
	private static Connection conn = null;
	private static Statement st = null;
	private static ResultSet rs = null; 
	
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/ogormanm";

	   //  Database credentials
	   static final String USER = "springtest";
	   static final String PASS = "abc123";
    
    private static List<Contact> contacts = new ArrayList<Contact>();
 
    static {
        //contacts.add(new Contact("Barack", "Obama", "barack.o@whitehouse.com", "147-852-965"));
        //contacts.add(new Contact("George", "Bush", "george.b@whitehouse.com", "785-985-652"));
        //contacts.add(new Contact("Bill", "Clinton", "bill.c@whitehouse.com", "236-587-412"));
        //contacts.add(new Contact("Ronald", "Reagan", "ronald.r@whitehouse.com", "369-852-452"));
        // Pull in the the DB contact dynamically
        try {
			
        	Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM contacts");

			while (rs.next()) {
				int contact_id = rs.getInt("contact_id");
				String firstName = rs.getString("firstname");
				String lastName = rs.getString("lastname");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				contacts.add(new Contact(contact_id, firstName, lastName, email, phone));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (st != null) st.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
    }
     
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ModelAndView get() {
         
        ContactForm contactForm = new ContactForm();
        contactForm.setContacts(contacts);
         
        return new ModelAndView("add_contact" , "contactForm", contactForm);
    }
     
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("contactForm") ContactForm contactForm) {
        System.out.println(contactForm);
        System.out.println(contactForm.getContacts());
        List<Contact> contacts = contactForm.getContacts();
        
        
       
        
        if(null != contacts && contacts.size() > 0) {
            ContactController.contacts = contacts;
            try {
	            Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				st = conn.createStatement();
	            for (Contact contact : contacts) {
	                System.out.printf("%s \t %s \n", contact.getFirstname(), contact.getLastname());
	                // Save contact update code here
	                String query = "UPDATE contacts SET firstname=?,lastname=?,email=?," +
	                               "phone=? WHERE contact_id=?";
	                PreparedStatement preparedStmt = conn.prepareStatement(query);
	                preparedStmt.setString(1, contact.getFirstname());
	                preparedStmt.setString(2, contact.getLastname());
	                preparedStmt.setString(3, contact.getEmail());
	                preparedStmt.setString(4, contact.getPhone());
	                preparedStmt.setInt(5, contact.getContact_id());
	                
	                // execute the java preparedstatement
	                preparedStmt.executeUpdate();
	                
	            }
            } catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
				try { if (st != null) st.close(); } catch (SQLException e) { e.printStackTrace(); }
				try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
        }
         
        return new ModelAndView("show_contact", "contactForm", contactForm);
    }
}

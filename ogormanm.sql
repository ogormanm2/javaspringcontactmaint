CREATE table contacts(
   contact_id INT NOT NULL AUTO_INCREMENT,
   firstname VARCHAR(100) NOT NULL,
   lastname VARCHAR(100) NOT NULL,
   email VARCHAR(100) NOT NULL,
   phone VARCHAR(40) NOT NULL,
   submission_date DATE,
   PRIMARY KEY ( contact_id )
);
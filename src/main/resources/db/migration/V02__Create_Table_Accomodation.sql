CREATE TABLE accomodation (
  id INTEGER PRIMARY KEY, 
  customer_id INTEGER NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES customer(id), 
  check_in_date TIMESTAMP NOT NULL, 
  check_out_date TIMESTAMP, 
  garage_needed boolean DEFAULT FALSE, 
  active boolean DEFAULT TRUE, 
  accomodation_value NUMERIC
);
CREATE SEQUENCE accomodation_seq OWNED BY accomodation.id;

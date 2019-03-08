CREATE TABLE customer (
  id INTEGER PRIMARY KEY, 
  name VARCHAR(100) NOT NULL, 
  document VARCHAR(50) NOT NULL, 
  phone VARCHAR(50) NOT NULL
);
CREATE SEQUENCE customer_seq OWNED BY customer.id;
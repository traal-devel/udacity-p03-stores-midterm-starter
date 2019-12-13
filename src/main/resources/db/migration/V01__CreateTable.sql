-- write sql to create member table

CREATE TABLE product (
  product_id INT AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(1000) NOT NULL,
  created_time TIMESTAMP not null,
  CONSTRAINT member_pk PRIMARY KEY (product_id)
);

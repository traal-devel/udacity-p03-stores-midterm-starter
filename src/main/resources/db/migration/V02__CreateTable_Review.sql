-- write sql to create member table

CREATE TABLE review (
  review_id INT AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  body VARCHAR(1000) NOT NULL,
  author VARCHAR(255) NOT NULL,
  created_time TIMESTAMP not null,
  product_id INT,
  constraint review_pk primary key (review_id),
  constraint review_product_pk FOREIGN KEY (product_id) REFERENCES product(product_id)
);
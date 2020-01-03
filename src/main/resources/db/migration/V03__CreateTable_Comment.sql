-- write sql to create comment table

CREATE TABLE comment (
  comment_id INT AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  content VARCHAR(1000) NOT NULL,
  created_time TIMESTAMP not null,
  review_id INT,
  CONSTRAINT comment_pk PRIMARY KEY (comment_id),
  CONSTRAINT review_review_pk FOREIGN KEY (review_id) REFERENCES review(review_id)
);
DROP TABLE IF EXISTS step;
DROP TABLE IF EXISTS "transaction";

CREATE TABLE step (
  id bigint AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL UNIQUE,
  url VARCHAR(2048) NOT NULL
);



CREATE TABLE transaction (
  id bigint AUTO_INCREMENT PRIMARY KEY,
  price double NOT NULL,
  "timestamp" timestamp NOT NULL,
  status VARCHAR(100) NOT NULL,
  stepId bigint NOT NULL,
  FOREIGN KEY (stepId) REFERENCES step(id)
);


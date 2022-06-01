create table if not exists Book (
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  createdAt timestamp not null,
  name varchar(50) not null,
  author varchar(50) not null,
  free BOOLEAN not null
);
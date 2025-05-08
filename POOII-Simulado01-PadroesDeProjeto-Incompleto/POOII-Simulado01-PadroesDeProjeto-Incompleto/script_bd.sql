CREATE TABLE alunos(
   codigo serial      NOT NULL,
   nome varchar(50) NOT NULL,
   matricula varchar(50) NOT NULL,
   idade int NOT NULL,
   CONSTRAINT pk_alunos
      PRIMARY KEY(codigo)
);

INSERT INTO alunos(nome, matricula, idade) VALUES('Alberto','IMI20161','14');
INSERT INTO alunos(nome, matricula, idade) VALUES('Bernardo','IMI20162','15');
INSERT INTO alunos(nome, matricula, idade) VALUES('Carlos','IMI20163','16');

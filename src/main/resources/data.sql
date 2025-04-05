DELETE FROM produto;
DELETE FROM categoria; 
DELETE FROM USUARIO;
TRUNCATE TABLE USUARIO RESTART IDENTITY;


INSERT INTO categoria (categoria_id, categoria_nome) VALUES (1, 'Informatica');
INSERT INTO categoria (categoria_id, categoria_nome) VALUES (2, 'Celulares');

INSERT INTO produto (produto_id, produto_nome, categoria_id) VALUES (1, 'Notebook DELL', 1);
INSERT INTO produto (produto_id, produto_nome, categoria_id) VALUES (2, 'Samsung Galaxy', 2);

INSERT INTO USUARIO (USER_ID, EMAIL, USER_PASSWORD, NOME_USUARIO) 
VALUES (1, 'teste@email.com', '$2a$12$2wkS.ovNxIkZfuxtTqQbCOuhNfTHgfY8lm6NJsWbTDVaBJvRJ/8WC', 'TesteEE');

COMMIT;

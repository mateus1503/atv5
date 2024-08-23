INSERT INTO Produto (descricao, valor, status) VALUES ('Processador i3-12100', 753, True);
INSERT INTO Produto (descricao, valor, status) VALUES ('Memória Ram 8GB 3200MHz', 152, True);
INSERT INTO Produto (descricao, valor, status) VALUES ('Mochila Dell Gaming 17', 220, True);
INSERT INTO Produto (descricao, valor, status) VALUES ('WebCam 1080p', 216, True);

INSERT INTO Role (nome) VALUES ('ROLE_ADMIN');
INSERT INTO Role (nome) VALUES ('ROLE_USER');

INSERT INTO Pessoa (nome, telefone) VALUES ('Mateus', '63991458673');
INSERT INTO pessoa_fisica (id, cpf)values (1, '078.486.441-11');
INSERT INTO Pessoa (nome, telefone) VALUES ('Ermeson', '63991511896');
INSERT INTO pessoa_fisica (id, cpf)values (2, '608.794.583-05');

INSERT INTO Usuario (PESSOA_ID, username, password) VALUES (1, 'admin', '$2a$10$WOBM.tFPbqKcQ5bRL26FjeiYr1DoK2h8DGvX9WcCb03xlIs7MN75W'); -- senha: admin
INSERT INTO Usuario (PESSOA_ID, username, password) VALUES (2, 'ermeson', '$2a$10$2/bj6k6vQgSC3k3P0LS9PO1S.UaahXi21GX9RSC5h8NYT7Zg2x9Am'); -- senha: 123

INSERT INTO USUARIO_ROLES (USUARIOS_ID, ROLES_ID) VALUES (1, 1);
INSERT INTO USUARIO_ROLES (USUARIOS_ID, ROLES_ID) VALUES (2, 2);

-- INSERT INTO Pessoa (nome, telefone) VALUES ('Mateus', '63991458673');
-- INSERT INTO pessoa_fisica (id, cpf)values (1, '078.486.441-11');
--
-- INSERT INTO Pessoa (nome, telefone) VALUES ('Marcos', '63991458673');
-- INSERT INTO pessoa_fisica (id, cpf)values (2, '286.963.031-05');
--
-- INSERT INTO Pessoa (nome, telefone) VALUES ('Francisco', '63991458673');
-- INSERT INTO pessoa_fisica (id, cpf)values (3, '002.328.851-50');
--
-- INSERT INTO Pessoa (nome, telefone) VALUES ('MN Gaming Tech', '63923489821');
-- INSERT INTO pessoa_juridica (id, cnpj)values (4, '83.533.550/0001-01');
--
-- INSERT INTO pessoa (nome, telefone)values ('Ermeson', '63981201914');
-- INSERT INTO pessoa_fisica (id, cpf)values (5, '608.794.583-05');
--
-- INSERT INTO pessoa (nome, telefone)values ('João Victor', '63991164826');
-- INSERT INTO pessoa_fisica (id, cpf)values (6, '406.523.511-15');
--
-- INSERT INTO pessoa (nome, telefone)values ('TI Balbinot', '6332325632');
-- INSERT INTO pessoa_juridica (id, cnpj)values (7, '25.774.622/0001-06');
--
-- INSERT INTO Venda (data, pessoa_id) VALUES ('2024-03-14', 1);
-- INSERT INTO Venda (data, pessoa_id) VALUES ('2024-03-15', 2);
-- INSERT INTO Venda (data, pessoa_id) VALUES ('2024-04-18', 2);
--
-- INSERT INTO ITEM_VENDA (quantidade, total_item, produto_id, venda_id) VALUES (2, 1506, 1, 1);
-- INSERT INTO ITEM_VENDA (quantidade, total_item, produto_id, venda_id) VALUES (2, 304, 2, 1);
-- INSERT INTO ITEM_VENDA (quantidade, total_item, produto_id, venda_id) VALUES (1, 220, 3, 2);
-- INSERT INTO ITEM_VENDA (quantidade, total_item, produto_id, venda_id) VALUES (6, 1296, 4, 3);
CREATE TABLE usuarios (
                         id SERIAL PRIMARY KEY,
                         uuid UUID NOT NULL,
                         nome VARCHAR(50) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         senha VARCHAR(255) NOT NULL,
                         data_cadastro TIMESTAMP NOT NULL,
                         permissao VARCHAR(20) NOT NULL
);

CREATE TABLE clientes (
                         id SERIAL PRIMARY KEY,
                         uuid UUID NOT NULL,
                         id_usuario INT NOT NULL,
                         idade INT NOT NULL,
                         cep VARCHAR(9) NOT NULL,
                         estado VARCHAR(2) NOT NULL,
                         cidade VARCHAR(100) NOT NULL,
                         bairro VARCHAR(100) NOT NULL,
                         numero VARCHAR(20) NOT NULL,
                         complemento VARCHAR(100),
                         CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE produtos (
                          id SERIAL NOT NULL PRIMARY KEY,
                          uuid UUID UNIQUE NOT NULL,
                          nome VARCHAR(50) NOT NULL,
                          dosagem VARCHAR(50) NOT NULL,
                          quantidade INT NOT NULL,
                          preco DECIMAL(10, 2) NOT NULL,
                          receita_obrigatoria BOOLEAN
);

CREATE TABLE categorias (

                         id SERIAL NOT NULL PRIMARY KEY,
                         uuid UUID UNIQUE NOT NULL,
                         nome VARCHAR(50) NOT NULL
);

CREATE TABLE produto_categoria (

                         id SERIAL NOT NULL PRIMARY KEY,
                         id_produto INT NOT NULL,
                         id_categoria INT NOT NULL,
                         FOREIGN KEY (id_produto) REFERENCES produtos(id) ON DELETE CASCADE,
                         FOREIGN KEY (id_categoria) REFERENCES categorias(id) ON DELETE CASCADE
);

CREATE TABLE entradas (
                          id SERIAL NOT NULL PRIMARY KEY,
                          uuid UUID UNIQUE NOT NULL,
                          id_produto INT NOT NULL,
                          quantidade INT NOT NULL,
                          data TIMESTAMP NOT NULL,
                          FOREIGN KEY (id_produto) REFERENCES produtos(id)
);

CREATE TABLE saidas (
                        id SERIAL NOT NULL PRIMARY KEY,
                        uuid UUID UNIQUE NOT NULL,
                        id_cliente INT NOT NULL,
                        id_usuario INT NOT NULL,
                        id_produto INT NOT NULL,
                        data TIMESTAMP NOT NULL,
                        quantidade INT NOT NULL,
                        FOREIGN KEY (id_cliente) REFERENCES clientes(id),
                        FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
                        FOREIGN KEY (id_produto) REFERENCES produtos(id)
);
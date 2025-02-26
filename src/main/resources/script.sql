CREATE DATABASE db_hav_imobiliaria;
drop database db_hav_imobiliaria;

use db_hav_imobiliaria;

CREATE TABLE usuario (
                      id INT NOT NULL PRIMARY KEY,
                      role VARCHAR(45) NOT NULL,
                      nome VARCHAR(45) NOT NULL,
                      telefone CHAR(13),
                      email VARCHAR(45) NOT NULL,
                      descricao VARCHAR(500),
                      foto VARCHAR(100)
) ENGINE = InnoDB;

-- Tabela Proprietário
CREATE TABLE proprietario (
                              id INT NOT NULL PRIMARY KEY,
                              nome VARCHAR(45) NOT NULL,
                              telefone CHAR(11) NOT NULL UNIQUE,
                              CPF CHAR(11) NOT NULL UNIQUE,
                              tipo_residencia VARCHAR(45) NOT NULL,
                              numero_casa_predio INT NOT NULL,
                              numero_apartamento INT,
                              id_endereco INT NOT NULL,
                              CONSTRAINT fk_proprietario_endereco
                                  FOREIGN KEY (id_endereco)
                                      REFERENCES endereco (id)
                                      ON DELETE CASCADE
                                      ON UPDATE CASCADE
) ENGINE = InnoDB;

-- Tabela Imóvel
CREATE TABLE imovel (
                        id INT NOT NULL PRIMARY KEY,
                        titulo VARCHAR(45) NOT NULL,
                        imagem_capa VARCHAR(100) NOT NULL,
                        descricao VARCHAR(500) NOT NULL,
                        tamanho INT NOT NULL,
                        qtd_quartos INT NOT NULL,
                        qtd_banheiros INT NOT NULL,
                        qtd_garagens INT,
                        qtd_churrasqueira INT,
                        qtd_piscina INT,
                        finalidade ENUM("ALUGUEL", "COMPRA"), NOT NULL,
                        academia TINYINT(1),
                        preco DOUBLE NOT NULL,
                        preco_promocional DOUBLE,
                        permitir_destaque TINYINT(1) NOT NULL,
                        habilitar_visibilidade TINYINT(1) NOT NULL,
                        tipo_residencia VARCHAR(45) NOT NULL,
                        numero_casa_predio INT NOT NULL,
                        numero_apartamento INT,
                        banner TINYINT(1) NOT NULL,
                        tipo_banner ENUM("DESCONTO", "MELHOR PREÇO", "PROMOÇÃO", "ADQUIRIDO", "ALUGADO"),
                        galeria_imagens VARCHAR(255) NOT NULL,
                        IPTU DOUBLE,
                        valor_condominio DOUBLE,
                        id_proprietario INT NOT NULL,
                        id_endereco INT NOT NULL,
                        CONSTRAINT fk_imovel_proprietario
                            FOREIGN KEY (id_proprietario)
                                REFERENCES proprietario (id)
                                ON DELETE NO ACTION
                                ON UPDATE NO ACTION
                        CONSTRAINT fk_imovel_endereco
                            FOREIGN KEY (id_endereco)
                                REFERENCES endereco (id)
                                ON DELETE CASCADE
                                ON UPDATE CASCADE
) ENGINE = InnoDB;

--Tabela endereço
CREATE TABLE endereco(
                        id INT NOT NULL PRIMARY KEY,
                        bairro VARCHAR(45) NOT NULL,
                        cidade VARCHAR(45) NOT NULL,
                        estado VARCHAR(45) NOT NULL,
                        CEP CHAR(8) NOT NULL UNIQUE,
                        rua VARCHAR(45) NOT NULL
)
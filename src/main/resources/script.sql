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
                      ativo TINYINT(1) DEFAULT true,
                      foto VARCHAR(100)
) ENGINE = InnoDB;

-- Tabela Proprietário
CREATE TABLE proprietario (
                              id INT NOT NULL PRIMARY KEY,
                              nome VARCHAR(45) NOT NULL,
                              telefone CHAR(11) NOT NULL UNIQUE,
                              CPF CHAR(11) NOT NULL UNIQUE,
                              CEP CHAR(8) NOT NULL,
                              rua VARCHAR(45) NOT NULL,
                              tipo_residencia VARCHAR(45) NOT NULL,
                              numero_casa_predio INT NOT NULL,
                              numero_apartamento INT,
                              bairro VARCHAR(45) NOT NULL,
                              cidade VARCHAR(45) NOT NULL,
                              estado VARCHAR(45) NOT NULL
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
                        qtd_churrasqueira VARCHAR(45),
                        qtd_piscina INT,
                        finalidade ENUM("ALUGUEL", "COMPRA"), NOT NULL,
                        academia TINYINT(1),
                        preco DOUBLE NOT NULL,
                        preco_promocional DOUBLE,
                        permitir_destaque TINYINT(1) NOT NULL,
                        habilitar_visibilidade TINYINT(1) NOT NULL,
                        CEP CHAR(8) NOT NULL,
                        tipo_residencia VARCHAR(45) NOT NULL,
                        numero_casa_predio INT NOT NULL,
                        numero_apartamento INT,
                        banner TINYINT(1) NOT NULL,
                        tipo_banner ENUM("DESCONTO", "MELHOR PREÇO", "PROMOÇÃO", "ADQUIRIDO", "ALUGADO"),
                        bairro VARCHAR(45) NOT NULL,
                        cidade VARCHAR(45) NOT NULL,
                        estado VARCHAR(45) NOT NULL,
                        galeria_imagens VARCHAR(255) NOT NULL,
                        IPTU DOUBLE,
                        valor_condominio DOUBLE,
                        idproprietario INT NOT NULL,
                        CONSTRAINT fk_imovel_proprietario
                            FOREIGN KEY (idproprietario)
                                REFERENCES proprietario (idproprietario)
                                ON DELETE NO ACTION
                                ON UPDATE NO ACTION
) ENGINE = InnoDB;

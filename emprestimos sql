CREATE TABLE Usuarios (
   id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
   nome VARCHAR(100),
   cpf VARCHAR(14),
   data_nascimento DATE,
   email VARCHAR(100),
   senha VARCHAR(100)   
);

CREATE TABLE Emprestimos (
   id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    usuario_id INT,
    valor_inicial_emprestimo DECIMAL(10,2),
    valor_pago DECIMAL(10,2),
    valor_faltante DECIMAL(10,2),
    data_vencimento DATE,
    data_emprestimo DATE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

INSERT INTO Usuarios (nome, cpf, data_nascimento, email, senha)
VALUES 
('Gabriel', '439.156.233-28', '2000-10-10', 'gabriel@gmail.com', 'messigoat'),
('Iuri', '231.236.233-74', '2002-05-10', 'iuri@gmail.com', 'neymar10'),
('Irineu', '411.256.233-24', '2001-05-10', 'irineu55@gmail.com', 'cr7omaior'),
('Vitor', '543.626.337-88', '2000-12-20', 'lucas@email.com', 'bapiturtle');

INSERT INTO Emprestimos (usuario_id, valor_inicial_emprestimo, valor_pago, valor_faltante, data_vencimento, data_emprestimo)
VALUES 
(1, 1500.00, 1000.00, 500.00, '2023-12-10', '2023-11-15'),
(2, 2000.00, 500.00, 1500.00, '2023-12-11', '2023-11-15'),
(3, 300.00, 20.00, 280.00, '2024-02-11', '2023-11-15'),
(4, 200.00, 100.00, 100.00, '2024-01-11', '2023-11-15');

SELECT * FROM Emprestimos;
SELECT * FROM Usuarios;

UPDATE Usuarios
SET senha = 'neymar00'
WHERE id = 2;

UPDATE Emprestimos
SET valor_pago = 1100.00
WHERE id = 1;

UPDATE Emprestimos
SET valor_faltante = 400.00
WHERE id = 1;

DELETE FROM Usuarios;
DELETE FROM Emprestimos;

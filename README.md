Scripts para a criação das tabela no banco postgress:
CREATE TABLE bank.clients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    email VARCHAR(100),
    account_number VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE bank.transactions (
    id SERIAL PRIMARY KEY,
    client_id INT REFERENCES bank.clients(id),
    type VARCHAR(10) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

Script para inserção de dados das tabelas no banco:

INSERT INTO bank.clients (name, age, email, account_number) VALUES
('Igor Vieira', 24, 'igor.vieira@email.com', '1234567890'),
('Marcos Gil', 25, 'marcos.gil@email.com', '9876543210'),
('Luiz Antonio', 65, 'luiz.antonio@email.com', '2468135790');

INSERT INTO bank.transactions (client_id, type, amount, transaction_date) VALUES
(1, 'credit', 1000.00, CURRENT_TIMESTAMP),
(1, 'debit', 500.00, CURRENT_TIMESTAMP),
(2, 'credit', 1500.00, CURRENT_TIMESTAMP),
(3, 'debit', 700.00, CURRENT_TIMESTAMP),
(3, 'credit', 2000.00, CURRENT_TIMESTAMP);

Alterando fkey transcation para deletar junto com cliente
ALTER TABLE bank.transactions
DROP CONSTRAINT transactions_client_id_fkey;

ALTER TABLE bank.transactions
ADD CONSTRAINT transactions_client_id_fkey
FOREIGN KEY (client_id)
REFERENCES bank.clients(id)
ON DELETE CASCADE;

CREATE TABLE bank."user" (
id SERIAL PRIMARY KEY,
userName VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL
);

INSERT INTO bank."user" (user_name, password) VALUES ('admin', 'admin');


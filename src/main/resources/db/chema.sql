CREATE TABLE IF NOT EXISTS product
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    measure varchar(20) NOT NULL,
    price INT NOT NULL CHECK ( price > 0 ),
    promotional BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS discount_card
(
    card_number INT PRIMARY KEY,
    discount_percentage INT NOT NULL CHECK ( discount_percentage >= 0 AND discount_percentage <= 100)
);
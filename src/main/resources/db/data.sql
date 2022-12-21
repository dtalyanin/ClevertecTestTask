INSERT INTO product (name, measure, price, promotional)
VALUES ('Milk 1.5%', '0.9 l.', 176, false),
       ('Milk 2.5%', '0.9 l.', 200, false),
       ('Pork', '1 kg.', 1570, false),
       ('Chicken', '1 kg.', 1120, false),
       ('Banana', '1 kg.', 520, true),
       ('Orange', '1 kg.', 450, false),
       ('Alpen Gold%', '1 pcs', 255, true),
       ('Snickers SUPER', '1 pcs', 199, false),
       ('Coca-Cola Zero', '2 l.', 350, false),
       ('Coca-Cola', '1 l.', 259, false),
       ('Coca-Cola', '2 l.', 360, true),
       ('Sausages', '0.6 kg.', 676, false),
       ('Potato', '1 kg.', 90, false),
       ('Lays chips', '140 g.', 399, true),
       ('Salted peanuts', '150 g.', 188, false)
ON CONFLICT DO NOTHING;

INSERT INTO discount_card
VALUES (1234, 2),
       (1598, 8),
       (299, 10),
       (1466, 12),
       (8846, 19),
       (97, 1),
       (9873, 16),
       (5671, 9),
       (9773, 8),
       (156, 17),
       (6887, 20)
ON CONFLICT (card_number) DO NOTHING;
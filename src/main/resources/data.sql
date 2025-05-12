INSERT INTO batteries (id, name, postcode, capacity)
VALUES (1, 'Cannington', '6107', 13500),
       (2, 'Midland', '6057', 50500),
       (3, 'Hay Street', '6000', 23500),
       (4, 'Mount Adams', '6525', 12000),
       (5, 'Koolan Island', '6733', 10000),
       (6, 'Armadale', '6992', 25000),
       (7, 'Lesmurdie', '6076', 13500),
       (8, 'Kalamunda', '6076', 13500),
       (9, 'Carmel', '6076', 36000),
       (10, 'Bentley', '6102', 85000),
       (11, 'Akunda Bay', '2084', 13500),
       (12, 'Werrington County', '2747', 13500),
       (13, 'Bagot', '0820', 27000),
       (14, 'Yirrkala', '0880', 13500),
       (15, 'University of Melbourne', '3010', 85000),
       (16, 'Norfolk Island', '2899', 13500),
       (17, 'Ootha', '2875', 13500),
       (18, 'Kent Town', '5067', 13500),
       (19, 'Northgate Mc', '9464', 13500),
       (20, 'Gold Coast Mc', '9729', 50000);

SELECT setval('batteries_id_seq', (SELECT MAX(id) FROM batteries));

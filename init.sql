DROP TABLE IF EXISTS public.products;

CREATE TABLE IF NOT EXISTS products (
    id  SERIAL PRIMARY KEY,
    barcode VARCHAR(255) NOT NULL,
    item VARCHAR(255) NOT NULL,
    category VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL,
    discount DECIMAL(10, 2),
    available INTEGER
);

INSERT INTO products (barcode, item, category, price, discount, available) VALUES
                                                                               ('74001755', 'Ball Gown', 'Full Body Outfits', 3548.00, 7.00, 1),
                                                                               ('74002423', 'Shawl', 'Accessories', 758.00, 12.00, 1),
                                                                               ('74003489', 'Evening Dress', 'Full Body Outfits', 2350.00, 5.00, 3),
                                                                               ('74004567', 'Leather Jacket', 'Outerwear', 1250.00, 0.00, 5),
                                                                               ('74005678', 'Silk Scarf', 'Accessories', 450.00, 10.00, 8);
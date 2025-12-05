INSERT INTO orders (user_id, total_amount, status, created_by, updated_by, created_at)
VALUES (1, 1248.00, 'NEW', 1, 1, CURRENT_TIMESTAMP - INTERVAL '2 days');

INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase, subtotal, created_by, updated_by)
VALUES
((SELECT id FROM orders ORDER BY id DESC LIMIT 1), 1, 1, 999.00, 999.00, 1, 1),
((SELECT id FROM orders ORDER BY id DESC LIMIT 1), 3, 1, 249.00, 249.00, 1, 1);

INSERT INTO orders (user_id, total_amount, status, created_by, updated_by, created_at)
VALUES (2, 2298.00, 'NEW', 2, 2, CURRENT_TIMESTAMP - INTERVAL '10 days');

INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase, subtotal, created_by, updated_by)
VALUES
((SELECT id FROM orders ORDER BY id DESC LIMIT 1), 2, 1, 1299.00, 1299.00, 2, 2),
((SELECT id FROM orders ORDER BY id DESC LIMIT 1), 4, 1, 899.00, 899.00, 2, 2);

INSERT INTO orders (user_id, total_amount, status, created_by, updated_by, created_at)
VALUES (3, 249.00, 'NEW', 3, 3, CURRENT_TIMESTAMP - INTERVAL '5 days');

INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase, subtotal, created_by, updated_by)
VALUES
((SELECT id FROM orders ORDER BY id DESC LIMIT 1), 3, 1, 249.00, 249.00, 3, 3);

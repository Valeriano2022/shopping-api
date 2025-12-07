INSERT INTO carts (user_id, created_by, updated_by)
SELECT u.id, u.id, u.id
FROM users u
LEFT JOIN carts c ON c.user_id = u.id
WHERE c.id IS NULL;
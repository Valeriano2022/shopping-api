CREATE OR REPLACE FUNCTION create_cart_for_new_user()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO carts (user_id, created_by, updated_by)
    VALUES (NEW.id, NEW.id, NEW.id);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER tg_create_cart_after_user_insert
AFTER INSERT ON users
FOR EACH ROW
EXECUTE FUNCTION create_cart_for_new_user();
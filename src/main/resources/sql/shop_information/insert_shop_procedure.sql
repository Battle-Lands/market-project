CREATE OR REPLACE PROCEDURE insert_shop (
    in _owner varchar(16),
    in _location text,
    in _description text
)
BEGIN
    IF ( SELECT 1 from shop_information WHERE owner=_owner ) IS NOT NULL THEN
        UPDATE shop_information SET
        location=_location, description=_description WHERE owner=_owner;
    ELSE
        INSERT INTO shop_information ( owner, location, description )
        VALUES (_owner, _location, _description);
    END IF;
END
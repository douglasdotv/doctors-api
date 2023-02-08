ALTER TABLE doctors
    ADD is_active TINYINT;
UPDATE doctors
SET is_active = 1;
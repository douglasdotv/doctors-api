ALTER TABLE appointments
    ADD is_active TINYINT;
UPDATE appointments
SET is_active = 1;
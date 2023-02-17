ALTER TABLE patients
    ADD is_active TINYINT;
UPDATE patients
SET is_active = 1;

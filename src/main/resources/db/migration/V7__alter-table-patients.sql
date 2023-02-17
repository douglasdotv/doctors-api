ALTER TABLE patients
    CHANGE streetAddress street_address VARCHAR(100) NOT NULL,
    CHANGE zipCode zip_code VARCHAR(100) NOT NULL;

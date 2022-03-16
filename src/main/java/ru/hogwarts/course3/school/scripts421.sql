ALTER TABLE student
    ADD CONSTRAINT constraint_age CHECK (age >= 16),
    ALTER
        COLUMN age SET DEFAULT '20',
    ADD CONSTRAINT name_unique UNIQUE (name),
    ALTER COLUMN name SET NOT NULL;


ALTER TABLE faculty
    ADD CONSTRAINT name_color_unique UNIQUE (name, color);
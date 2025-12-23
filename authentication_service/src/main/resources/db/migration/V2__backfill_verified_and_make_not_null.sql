UPDATE restaurant_profile
SET verified = FALSE
WHERE verified IS NULL;

ALTER TABLE restaurant_profile
    ALTER COLUMN verified SET NOT NULL;

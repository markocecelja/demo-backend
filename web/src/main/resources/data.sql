INSERT INTO customer_status (name, created_date_time, updated_date_time)
VALUES ('ACTIVE', now(), now()),
       ('INACTIVE', now(), now());

INSERT INTO customer (first_name, last_name, status_id, created_date_time, updated_date_time)
VALUES ('Pero', 'Perić', 1, now(), now()),
       ('Ana', 'Anić', 2, now(), now());
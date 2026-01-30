-- 1. Services anlegen
INSERT INTO service_entity (id, name, duration, price) VALUES (1, 'Haarschnitt Herren', 30, 25.00);
INSERT INTO service_entity (id, name, duration, price) VALUES (2, 'Haarschnitt Damen', 60, 50.00);
INSERT INTO service_entity (id, name, duration, price) VALUES (3, 'Bartpflege', 20, 15.00);

-- 2. Kunden anlegen
INSERT INTO customer (id, first_name, last_name, email, phone, password) VALUES (1, 'Max', 'Mustermann', 'max@web.de', '0170123456', 'pass123');
INSERT INTO customer (id, first_name, last_name, email, phone, password) VALUES (2, 'Erika', 'Musterfrau', 'erika@web.de', '0170987654', 'geheim');

-- 3. Mitarbeiter anlegen
INSERT INTO employee (id, first_name, last_name) VALUES (1, 'Barber', 'Bob');
INSERT INTO employee (id, first_name, last_name) VALUES (2, 'Friseurin', 'Alice');

-- 4. Verknüpfung: Welcher Mitarbeiter kann welchen Service?
-- Bob macht Herren & Bart
INSERT INTO employee_services (employee_id, service_id) VALUES (1, 1);
INSERT INTO employee_services (employee_id, service_id) VALUES (1, 3);
-- Alice macht Damen & Herren
INSERT INTO employee_services (employee_id, service_id) VALUES (2, 1);
INSERT INTO employee_services (employee_id, service_id) VALUES (2, 2);

-- 5. Ein Beispiel-Termin
INSERT INTO appointment (id, customer_id, employee_id, service_id, date, status)
VALUES (1, 1, 1, 1, '2026-02-01 10:00:00', 'ACTIVE');
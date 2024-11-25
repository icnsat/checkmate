INSERT INTO roles (id, role_name) VALUES
(1, 'ADMIN'), (2, 'STAFF'), (3, 'CUSTOMER');

INSERT INTO booking_statuses (id, name, description) VALUES
(1, 'PENDING', 'Бронирование в обработке'), (2, 'CONFIRMED', 'Бронирование подтверждено'), (3, 'CANCELLED', 'Бронирование отменено');

SELECT * FROM users;

SELECT * FROM roles;

SELECT * FROM hotels;

SELECT * FROM rooms;

SELECT * FROM booking_statuses;

SELECT * FROM bookings;


INSERT INTO hotels (address, city, country, name, rating) VALUES
('Улица Тверская, дом 10', 'Москва', 'Россия', 'Гранд Москва Отель', 4.5),
('Невский проспект, дом 20', 'Санкт-Петербург', 'Россия', 'Отель Северное Сияние', 4.7),
('Курортный проспект, дом 50', 'Сочи', 'Россия', 'Сочи Солнечный Резорт', 4.3);


-- Номера для отеля в Москве
INSERT INTO rooms (capacity, description, hotel_id, price, room_type) VALUES
(2, 'Комфортный стандартный номер с двуспальной кроватью', 1, 5000.00, 'Стандарт'),
(4, 'Просторный семейный номер с видом на город', 1, 8500.00, 'Семейный'),
(2, 'Роскошный люкс с джакузи и панорамным видом', 1, 15000.00, 'Люкс');

-- Номера для отеля в Санкт-Петербурге
INSERT INTO rooms (capacity, description, hotel_id, price, room_type) VALUES
(2, 'Уютный номер в классическом стиле с видом на Невский проспект', 2, 6000.00, 'Стандарт'),
(3, 'Элегантный номер повышенной комфортности', 2, 9500.00, 'Полулюкс'),
(2, 'Престижный люкс с гостиной зоной и видом на канал', 2, 18000.00, 'Люкс');

-- Номера для отеля в Сочи
INSERT INTO rooms (capacity, description, hotel_id, price, room_type) VALUES
(2, 'Номер с видом на море, оснащённый всем необходимым', 3, 7000.00, 'Стандарт'),
(4, 'Семейный номер с двумя спальнями и балконом', 3, 12000.00, 'Семейный'),
(2, 'Премиальный люкс с террасой и личным бассейном', 3, 25000.00, 'Люкс');

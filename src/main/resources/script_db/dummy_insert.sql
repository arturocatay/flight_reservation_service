
--DUMMY DATA TEST
--RUN THIS SCRIPT IN ORDER TO RUN AND TEST LOCALLY

INSERT INTO flight (id, available_seats, flight_number, total_seats, arrival_time, departure, departure_time, destination)
VALUES
    ('5fc98de4-1533-4c43-8261-5c57ab21b8c7', 180, 'BA202', 180, '2025-03-11 11:30:00', 'London', '2025-03-11 09:00:00', 'Paris'),
    ('8b439ddf-66e9-46d1-bf1f-dc17c824cb8f', 200, 'DL303', 200, '2025-03-12 12:30:00', 'Atlanta', '2025-03-12 10:00:00', 'Chicago'),
    ('e1c1a5b1-3b29-4b75-bfa0-02122e1d9b34', 148, 'AA101', 150, '2025-03-10 11:00:00', 'New York', '2025-03-10 08:00:00', 'Los Angeles');

INSERT INTO seat (id, seat_number, status, flight_id)
VALUES
    ('2c8d7ed7-26d1-4842-9ebd-85f5b167897b', 3, 'AVAILABLE', 'e1c1a5b1-3b29-4b75-bfa0-02122e1d9b34'),
    ('d313df60-10b6-41d3-9c5b-060d85e63645', 4, 'AVAILABLE', 'e1c1a5b1-3b29-4b75-bfa0-02122e1d9b34'),
    ('02c0e8a9-d2c5-4669-85be-bdb9bb5b11b2', 150, 'AVAILABLE', 'e1c1a5b1-3b29-4b75-bfa0-02122e1d9b34'),
    ('c1220cb3-5f91-456b-9577-530d1f7b1619', 1, 'AVAILABLE', '5fc98de4-1533-4c43-8261-5c57ab21b8c7'),
    ('e52c8001-0628-49e5-b06d-e7e10bdb6f6a', 2, 'AVAILABLE', '5fc98de4-1533-4c43-8261-5c57ab21b8c7'),
    ('2a6e0acb-dc92-4b58-8912-b7b24e399697', 3, 'AVAILABLE', '5fc98de4-1533-4c43-8261-5c57ab21b8c7'),
    ('95a38187-399d-4085-b634-45f7a820d91b', 4, 'AVAILABLE', '5fc98de4-1533-4c43-8261-5c57ab21b8c7'),
    ('080a8c8a-c6e3-4768-98c4-3a469774f6fd', 180, 'AVAILABLE', '5fc98de4-1533-4c43-8261-5c57ab21b8c7'),
    ('be59764d-bb85-4b67-92b8-84ed1c5d5c07', 1, 'AVAILABLE', '8b439ddf-66e9-46d1-bf1f-dc17c824cb8f'),
    ('b378302e-ff71-4a7a-9c17-935de97c038f', 2, 'AVAILABLE', '8b439ddf-66e9-46d1-bf1f-dc17c824cb8f'),
    ('fb9262d5-fb58-4a7e-a8fd-c63b4b1f3244', 3, 'AVAILABLE', '8b439ddf-66e9-46d1-bf1f-dc17c824cb8f'),
    ('7ca11097-f1d2-46b0-9f0c-70c6a65366f4', 4, 'AVAILABLE', '8b439ddf-66e9-46d1-bf1f-dc17c824cb8f'),
    ('0a2b2767-4c13-4753-94e9-2de89bfa643b', 200, 'AVAILABLE', '8b439ddf-66e9-46d1-bf1f-dc17c824cb8f'),
    ('2d7d9b9b-6c6a-4e4b-a3b0-9811f70629fe', 2, 'AVAILABLE', 'e1c1a5b1-3b29-4b75-bfa0-02122e1d9b34'),
    ('f8e8bcf5-5554-4f76-b68b-dcf902327500', 1, 'AVAILABLE', 'e1c1a5b1-3b29-4b75-bfa0-02122e1d9b34');
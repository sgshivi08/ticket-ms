CREATE TABLE IF NOT EXISTS TICKET (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id BIGINT,
    user_name VARCHAR(255),
    ticket_type VARCHAR(255),
    number_of_tickets INT,
    payment_amount DOUBLE
);

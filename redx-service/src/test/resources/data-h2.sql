-- Copyright 2022 John Hurst
-- John Hurst
-- 2022-02-03

INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d1', 'MENMSATS_CATSNotification_r39_p1.xml', 2933, TIMESTAMP '2022-02-01 10:59:42', 'VENCORP', 'Vencorp', 'TXUN', 'TXU Networks', 'AAA', TIMESTAMP '2002-01-01 12:00:00', 'CATS', 'Medium', 'NEM');

INSERT INTO transaction (id, document, transaction_date, transaction_id, transaction_name)
VALUES ('t1', 'd1', TIMESTAMP '2002-01-01 12:00:00', 'XXX', 'CATSNotification');
INSERT INTO transaction (id, document, transaction_date, transaction_id, transaction_name)
VALUES ('t2', 'd1', TIMESTAMP '2002-01-02 12:00:00', 'YYY', 'CATSNotification');

INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t1', '123456789012345');
INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t1', '8374635465');
INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t1', 'AGEDDEBT');

INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t2', '123456789012346');
INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t2', '8374635465');
INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t2', 'AGEDDEBT');

COMMIT WORK;

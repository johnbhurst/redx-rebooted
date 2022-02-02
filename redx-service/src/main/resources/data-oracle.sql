-- Copyright 2022 John Hurst
-- John Hurst
-- 2022-02-01

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

INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d2', 'mtrd1.xml', 1024, TIMESTAMP '2022-02-02 09:25:14', 'NEMMCO', 'AEMO', 'CONTACTA', 'Red Energy', 'DOC2', TIMESTAMP '2012-02-12 15:00:00', 'MTRD', 'Medium', 'NEM');

INSERT INTO transaction (id, document, transaction_date, transaction_id, initiating_transaction_id, transaction_name)
VALUES ('t21', 'd2', TIMESTAMP '2012-02-12 09:49:01', 'DOC2TX1', 'INIT21', 'MeterDataNotification');

INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t21', '6012345621');

INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d3', 'mtrd2.xml', 2048, TIMESTAMP '2022-02-03 09:25:14', 'NEMMCO', 'AEMO', 'CONTACTA', 'Red Energy', 'DOC2', TIMESTAMP '2012-02-14 15:00:00', 'MTRD', 'Medium', 'NEM');

INSERT INTO transaction (id, document, transaction_date, transaction_id, initiating_transaction_id, transaction_name)
VALUES ('t31', 'd3', TIMESTAMP '2012-02-13 09:49:01', 'DOC3TX1', 'INIT31', 'MeterDataNotification');

INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t31', '6012345631');

INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d4', 'mtrd3.xml', 4096, TIMESTAMP '2022-02-04 09:25:14', 'NEMMCO', 'AEMO', 'CONTACTA', 'Red Energy', 'DOC2', TIMESTAMP '2012-02-15 15:00:00', 'MTRD', 'Medium', 'NEM');

INSERT INTO transaction (id, document, transaction_date, transaction_id, initiating_transaction_id, transaction_name)
VALUES ('t41', 'd4', TIMESTAMP '2012-02-14 09:49:01', 'DOC4TX1', 'INIT41', 'MeterDataNotification');

INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t41', '6012345641');

COMMIT WORK;

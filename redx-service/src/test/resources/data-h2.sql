-- Copyright 2022 John Hurst
-- John Hurst
-- 2022-02-03

-- file_name
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d01', 'document01.xml', 1001, TIMESTAMP '2022-01-01 10:00:01', 'FROM01', 'from01', 'TO01', 'to01', 'message001', TIMESTAMP '2022-02-01 11:00:01', 'BAR', 'Medium', 'NEM');
-- file_size
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d02', 'document02.xml', 1002, TIMESTAMP '2022-01-01 10:00:02', 'FROM02', 'from02', 'TO02', 'to02', 'message002', TIMESTAMP '2022-02-01 11:00:02', 'CATS', 'Medium', 'NEM');
-- file_date
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d03', 'document03.xml', 1003, TIMESTAMP '2022-01-01 10:00:03', 'FROM03', 'from03', 'TO03', 'to03', 'message003', TIMESTAMP '2022-02-01 11:00:03', 'CUST', 'Medium', 'NEM');
-- message_from
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d04', 'document04.xml', 1004, TIMESTAMP '2022-01-01 10:00:04', 'FROM04', 'from04', 'TO04', 'to04', 'message004', TIMESTAMP '2022-02-01 11:00:04', 'DPRT', 'Medium', 'NEM');
-- from_description
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d05', 'document05.xml', 1005, TIMESTAMP '2022-01-01 10:00:05', 'FROM05', 'from05', 'TO05', 'to05', 'message005', TIMESTAMP '2022-02-01 11:00:05', 'EMMS', 'Medium', 'NEM');
-- message_tp
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d06', 'document06.xml', 1006, TIMESTAMP '2022-01-01 10:00:06', 'FROM06', 'from06', 'TO06', 'to06', 'message006', TIMESTAMP '2022-02-01 11:00:06', 'ERFT', 'Medium', 'NEM');
-- to_description
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d07', 'document07.xml', 1007, TIMESTAMP '2022-01-01 10:00:07', 'FROM07', 'from07', 'TO07', 'to07', 'message007', TIMESTAMP '2022-02-01 11:00:07', 'FLDW', 'Medium', 'NEM');
-- message_id
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d08', 'document08.xml', 1008, TIMESTAMP '2022-01-01 10:00:08', 'FROM08', 'from08', 'TO08', 'to08', 'message008', TIMESTAMP '2022-02-01 11:00:08', 'FLTS', 'Medium', 'NEM');
-- message_date
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d09', 'document09.xml', 1009, TIMESTAMP '2022-01-01 10:00:09', 'FROM09', 'from09', 'TO09', 'to09', 'message009', TIMESTAMP '2022-02-01 11:00:09', 'HMGT', 'High', 'NSWGAS');
-- transaction_group
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d10', 'document10.xml', 1010, TIMESTAMP '2022-01-01 10:00:10', 'FROM10', 'from10', 'TO10', 'to10', 'message010', TIMESTAMP '2022-02-01 11:00:10', 'HSMD', 'High', 'SAGAS');
-- priority
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d11', 'document11.xml', 1011, TIMESTAMP '2022-01-01 10:00:11', 'FROM11', 'from11', 'TO11', 'to11', 'message011', TIMESTAMP '2022-02-01 11:00:11', 'IAIT', 'Low', 'NEM');
-- market
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, market)
VALUES ('d12', 'document12.xml', 1012, TIMESTAMP '2022-01-01 10:00:12', 'FROM12', 'from12', 'TO12', 'to12', 'message012', TIMESTAMP '2022-02-01 11:00:12', 'MDMT', 'Medium', 'VICGAS');

INSERT INTO transaction (id, document, transaction_date, transaction_id, transaction_name)
VALUES ('t0101', 'd01', TIMESTAMP '2002-01-01 12:00:00', 'XXX', 'CATSNotification');
INSERT INTO transaction (id, document, transaction_date, transaction_id, transaction_name)
VALUES ('t0102', 'd01', TIMESTAMP '2002-01-02 12:00:00', 'YYY', 'CATSNotification');

INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t0101', '123456789012345');
INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t0101', '8374635465');
INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t0101', 'AGEDDEBT');

INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t0102', '123456789012346');
INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t0102', '8374635465');
INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t0102', 'AGEDDEBT');

COMMIT WORK;

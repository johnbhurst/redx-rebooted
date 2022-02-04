-- Copyright 2022 John Hurst
-- John Hurst
-- 2022-02-03

-- file_name
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d01', 'document01.xml', 1001, TIMESTAMP '2022-01-01 10:00:01', 'FROM01', 'from01', 'TO01', 'to01', 'message001', TIMESTAMP '2022-02-01 11:00:01', 'BAR', 'Medium', 'NEMMCO01', 'NEM');
-- file_size
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d02', 'document02.xml', 1002, TIMESTAMP '2022-01-01 10:00:02', 'FROM02', 'from02', 'TO02', 'to02', 'message002', TIMESTAMP '2022-02-01 11:00:02', 'CATS', 'Medium', 'NEMMCO02', 'NEM');
-- file_date
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d03', 'document03.xml', 1003, TIMESTAMP '2022-01-01 10:00:03', 'FROM03', 'from03', 'TO03', 'to03', 'message003', TIMESTAMP '2022-02-01 11:00:03', 'CUST', 'Medium', 'NEMMCO03', 'NEM');
-- message_from
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d04', 'document04.xml', 1004, TIMESTAMP '2022-01-01 10:00:04', 'FROM04', 'from04', 'TO04', 'to04', 'message004', TIMESTAMP '2022-02-01 11:00:04', 'DPRT', 'Medium', 'NEMMCO04', 'NEM');
-- from_description
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d05', 'document05.xml', 1005, TIMESTAMP '2022-01-01 10:00:05', 'FROM05', 'from05', 'TO05', 'to05', 'message005', TIMESTAMP '2022-02-01 11:00:05', 'EMMS', 'Medium', 'NEMMCO05', 'NEM');
-- message_tp
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d06', 'document06.xml', 1006, TIMESTAMP '2022-01-01 10:00:06', 'FROM06', 'from06', 'TO06', 'to06', 'message006', TIMESTAMP '2022-02-01 11:00:06', 'ERFT', 'Medium', 'NEMMCO06', 'NEM');
-- to_description
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d07', 'document07.xml', 1007, TIMESTAMP '2022-01-01 10:00:07', 'FROM07', 'from07', 'TO07', 'to07', 'message007', TIMESTAMP '2022-02-01 11:00:07', 'FLDW', 'Medium', 'NEMMCO07', 'NEM');
-- message_id
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d08', 'document08.xml', 1008, TIMESTAMP '2022-01-01 10:00:08', 'FROM08', 'from08', 'TO08', 'to08', 'message008', TIMESTAMP '2022-02-01 11:00:08', 'FLTS', 'Medium', 'NEMMCO08', 'NEM');
-- message_date
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d09', 'document09.xml', 1009, TIMESTAMP '2022-01-01 10:00:09', 'FROM09', 'from09', 'TO09', 'to09', 'message009', TIMESTAMP '2022-02-01 11:00:09', 'HMGT', 'High', 'NEMMCO09', 'NSWGAS');
-- transaction_group
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d10', 'document10.xml', 1010, TIMESTAMP '2022-01-01 10:00:10', 'FROM10', 'from10', 'TO10', 'to10', 'message010', TIMESTAMP '2022-02-01 11:00:10', 'HSMD', 'High', 'NEMMCO10', 'SAGAS');
-- priority
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d11', 'document11.xml', 1011, TIMESTAMP '2022-01-01 10:00:11', 'FROM11', 'from11', 'TO11', 'to11', 'message011', TIMESTAMP '2022-02-01 11:00:11', 'IAIT', 'Low', 'NEMMCO11', 'NEM');
-- market
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d12', 'document12.xml', 1012, TIMESTAMP '2022-01-01 10:00:12', 'FROM12', 'from12', 'TO12', 'to12', 'message012', TIMESTAMP '2022-02-01 11:00:12', 'MDMT', 'Medium', 'NEMMCO12', 'VICGAS');

-- transactions
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d13', 'document13.xml', 1013, TIMESTAMP '2022-01-01 10:00:13', 'FROM13', 'from13', 'TO13', 'to13', 'message013', TIMESTAMP '2022-02-01 11:00:13', 'CATS', 'Medium', 'NEMMCO01', 'NEM');
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d14', 'document14.xml', 1014, TIMESTAMP '2022-01-01 10:00:14', 'FROM14', 'from14', 'TO14', 'to14', 'message014', TIMESTAMP '2022-02-01 11:00:14', 'CATS', 'Medium', 'NEMMCO01', 'NEM');
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d15', 'document15.xml', 1015, TIMESTAMP '2022-01-01 10:00:15', 'FROM15', 'from15', 'TO15', 'to15', 'message015', TIMESTAMP '2022-02-01 11:00:15', 'CATS', 'Medium', 'NEMMCO01', 'NEM');
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d16', 'document16.xml', 1016, TIMESTAMP '2022-01-01 10:00:16', 'FROM16', 'from16', 'TO16', 'to16', 'message016', TIMESTAMP '2022-02-01 11:00:16', 'CATS', 'Medium', 'NEMMCO01', 'NEM');

-- transaction_date
INSERT INTO transaction (id, document, transaction_date, transaction_id, initiating_transaction_id, transaction_name)
VALUES ('t1301', 'd13', TIMESTAMP '2022-03-01 12:13:01', 'trans1301', 'init1301', 'CATSNotification');
INSERT INTO transaction (id, document, transaction_date, transaction_id, initiating_transaction_id, transaction_name)
VALUES ('t1302', 'd13', TIMESTAMP '2022-03-01 12:13:02', 'trans1302', 'init1302', 'CATSNotification');
-- transaction_id
INSERT INTO transaction (id, document, transaction_date, transaction_id, initiating_transaction_id, transaction_name)
VALUES ('t1401', 'd14', TIMESTAMP '2022-03-01 12:14:01', 'trans1401', 'init1401', 'CATSNotification');
-- initiating_transaction_id
INSERT INTO transaction (id, document, transaction_date, transaction_id, initiating_transaction_id, transaction_name)
VALUES ('t1501', 'd15', TIMESTAMP '2022-03-01 12:15:01', 'trans1501', 'init1501', 'CATSNotification');
-- transaction name
INSERT INTO transaction (id, document, transaction_date, transaction_id, initiating_transaction_id, transaction_name)
VALUES ('t1601', 'd16', TIMESTAMP '2022-03-01 12:16:01', 'trans1601', 'init1601', 'CATSChangeRequest');

-- search terms
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d17', 'document17.xml', 1017, TIMESTAMP '2022-01-01 10:00:17', 'FROM17', 'from17', 'TO17', 'to17', 'message017', TIMESTAMP '2022-02-01 11:00:17', 'CATS', 'Medium', 'NEMMCO01', 'NEM');
INSERT INTO document (id, file_name, file_size, file_date, message_from, from_description, message_to, to_description, message_id, message_date, transaction_group, priority, security_context, market)
VALUES ('d18', 'document18.xml', 1018, TIMESTAMP '2022-01-01 10:00:18', 'FROM18', 'from18', 'TO18', 'to17', 'message018', TIMESTAMP '2022-02-01 11:00:18', 'CATS', 'Medium', 'NEMMCO01', 'NEM');

INSERT INTO transaction (id, document, transaction_date, transaction_id, initiating_transaction_id, transaction_name)
VALUES ('t1701', 'd17', TIMESTAMP '2022-03-01 12:17:01', 'trans1701', 'init1701', 'CATSNotification');
INSERT INTO transaction (id, document, transaction_date, transaction_id, initiating_transaction_id, transaction_name)
VALUES ('t1801', 'd18', TIMESTAMP '2022-03-01 12:18:01', 'trans1801', 'init1801', 'CATSNotification');


INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t1701', 'term1701a');
INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t1701', 'term1701b');
INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t1801', 'term1801a');
INSERT INTO transaction_search_term (transaction, search_term) VALUES ('t1801', 'term1801b');

COMMIT WORK;

-- Copyright 2022 John Hurst
-- John Hurst
-- 2022-02-01

CREATE TABLE document (
    id VARCHAR2(50) NOT NULL,
    file_name VARCHAR2(500) NOT NULL,
    file_size NUMBER NOT NULL,
    file_date DATE NOT NULL,
    message_from VARCHAR2(50) NOT NULL,
    from_description VARCHAR2(100),
    message_to VARCHAR2(50) NOT NULL,
    to_description VARCHAR2(100),
    message_id VARCHAR2(36) NOT NULL,
    message_date DATE NOT NULL,
    transaction_group VARCHAR2(4) NOT NULL,
    priority VARCHAR2(10),
    security_context VARCHAR2(15),
    market VARCHAR2(10)
);

ALTER TABLE document ADD CONSTRAINT document_pk PRIMARY KEY (id);

CREATE TABLE transaction (
    id VARCHAR2(50) NOT NULL,
    document VARCHAR2(50) NOT NULL,
    transaction_date DATE NOT NULL,
    transaction_id VARCHAR2(36) NOT NULL,
    initiating_transaction_id VARCHAR2(36),
    transaction_name VARCHAR2(50) NOT NULL
);

ALTER TABLE transaction ADD CONSTRAINT transaction_pk PRIMARY KEY (id);

ALTER TABLE transaction ADD CONSTRAINT transaction_document_fk FOREIGN KEY (document) REFERENCES document(id) ON DELETE CASCADE;

CREATE TABLE transaction_search_term (
    transaction VARCHAR2(50) NOT NULL,
    search_term VARCHAR2(1000) NOT NULL
);

ALTER TABLE transaction_search_term ADD CONSTRAINT transaction_search_term_pk PRIMARY KEY (transaction, search_term);

ALTER TABLE transaction_search_term ADD CONSTRAINT transaction_search_term_trn_fk FOREIGN KEY (transaction) REFERENCES transaction(id) ON DELETE CASCADE;

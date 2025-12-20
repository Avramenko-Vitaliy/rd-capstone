CREATE TABLE statuses (
    id INTEGER CONSTRAINT statuses_pk PRIMARY KEY,
    api_key VARCHAR
);
CREATE UNIQUE INDEX statuses_api_key_uindex ON statuses (api_key);

INSERT INTO statuses (id, api_key)
VALUES (1, 'ACTIVE'),
       (2, 'DONE'),
       (3, 'CANCELED');

CREATE TABLE todos (
    id SERIAL CONSTRAINT todos_pk PRIMARY KEY,
    name VARCHAR NOT NULL,
    status_id INTEGER NOT NULL CONSTRAINT todos_statuses_id_fk REFERENCES statuses (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    description VARCHAR
);

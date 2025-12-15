CREATE TABLE public.todos (
    id SERIAL CONSTRAINT todos_pk PRIMARY KEY,
    name VARCHAR,
    date TIMESTAMP WITHOUT TIME ZONE,
    description VARCHAR
);

-- DummyDomain 테이블

-- DROP TABLE

/*
DROP TABLE IF EXISTS
    public.dummy_domain;
*/

-- CREATE Sequence

/*
CREATE SEQUENCE dummy_id_seq
    START 1
    INCREMENT 1;
*/

-- CREATE TABLE
CREATE TABLE public.dummy_domain (
    dummy_id VARCHAR(6) PRIMARY KEY DEFAULT 'D' || LPAD(nextval('dummy_id_seq')::TEXT, 5, '0'),
    dummy_name VARCHAR(10) NOT NULL,
    dummy_desc VARCHAR(255),
    dummy_service_yn VARCHAR(1) NOT NULL,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ
);

-- INSERT DUMMY

/*
INSERT INTO public.dummy_domain (dummy_name, dummy_desc, dummy_service_yn)
VALUES
('Name1', 'Description1', 'Y'),
('Name2', 'Description2', 'N'),
('Name3', 'Description3', 'Y');
*/
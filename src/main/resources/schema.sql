DROP TABLE IF EXISTS show_seat;

CREATE TABLE show_seat (
  id             IDENTITY     NOT NULL PRIMARY KEY,
  version        INT          NOT NULL,
  show_id        INT          NOT NULL,
  seat_row       CHAR(2)      NOT NULL,
  seat_number    CHAR(2)      NOT NULL,
  availability   VARCHAR(50)  NOT NULL
);

CREATE SEQUENCE show_seat_sequence
  START WITH 1
  INCREMENT BY 1
  MINVALUE 1;
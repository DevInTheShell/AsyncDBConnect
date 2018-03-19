CREATE TABLE "DEVELOPER"."USERS" 
(
    "ID" NUMBER NOT NULL ENABLE, 
	"FIRST_NAME" VARCHAR2(32 BYTE), 
	"LAST_NAME" VARCHAR2(32 BYTE), 
	"DATE_CREATED" DATE, 
	CONSTRAINT "USERS_PK" PRIMARY KEY ("ID")
);

CREATE SEQUENCE USERS_ID_SEQUENCE START WITH 1;

CREATE OR REPLACE TRIGGER "DEVELOPER"."USERS_ID_NEXTVAL" 
BEFORE INSERT ON USERS 
FOR EACH ROW 
BEGIN
    IF INSERTING AND :NEW.ID IS NULL THEN
        SELECT USERS_ID_SEQUENCE.NEXTVAL INTO :NEW.ID FROM SYS.DUAL;
    END IF;
END;
/
ALTER TRIGGER "DEVELOPER"."USERS_ID_NEXTVAL" ENABLE;

CREATE OR REPLACE TRIGGER "DEVELOPER"."USERS_DATE_CREATED_SYSDATE"
BEFORE INSERT ON USERS
FOR EACH ROW
BEGIN
    :NEW.DATE_CREATED := SYSDATE;
END;
/
ALTER TRIGGER "DEVELOPER"."USERS_DATE_CREATED_SYSDATE" ENABLE;
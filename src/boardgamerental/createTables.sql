CREATE TABLE "CUSTOMER" (
    "ID" INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "NAME" VARCHAR(30),
    "ADDRESS" VARCHAR(255),
    "PHONENUMBER" VARCHAR(20),
);

CREATE TABLE "BOARDGAME" (
    "ID" INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "NAME" VARCHAR(40),
    "MAXPLAYERS" INTEGER,
    "MINPLAYERS" INTEGER,
    "PRICEPERDAY" DECIMAL,
);

CREATE TABLE "CATEGORY" (
    "BOARDGAMEID" INTEGER NOT NULL PRIMARY KEY
    "CATEGORY" VARCHAR(10),
);
 
CREATE TABLE "LENDING" (
    "ID" INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "CUSTOMERID" INTEGER,
    "BOARDGAMEID" INTEGER,
    "STARTTIME" DATE,
    "EXPECTEDENDTIME" DATE,
    "REALENDTIME" DATE,
);
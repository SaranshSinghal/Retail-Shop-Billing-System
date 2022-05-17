CREATE DATABASE RetailShop;
USE RetailShop;

CREATE TABLE Product (
    P_Id INT PRIMARY KEY,
    ProductName VARCHAR(25) NOT NULL,
    Category VARCHAR(20) NOT NULL,
    Price DOUBLE NOT NULL,
    Quantity INT DEFAULT 0
);

CREATE TABLE Customer (
    C_Id INT PRIMARY KEY AUTO_INCREMENT,
    C_Name VARCHAR(30) NOT NULL,
    C_Password VARCHAR(20) NOT NULL,
    PhoneNumber VARCHAR(15) NOT NULL,
    Address VARCHAR(100)
);

    
CREATE TABLE Bill (
    Bill_Id INT PRIMARY KEY AUTO_INCREMENT,
    Time_Stamp DATETIME,
    C_Id INT,
    P_Id INT,
    Quantity INT,
    TotalAmount DOUBLE,
    FOREIGN KEY (C_ID)
        REFERENCES Customer (C_Id),
    FOREIGN KEY (P_ID)
        REFERENCES Product (P_Id)
);

CREATE TABLE Cart (
    Cart_Id INT PRIMARY KEY AUTO_INCREMENT,
    C_Id INT,
    P_Id INT,
    Quantity INT,
    TotalAmount DOUBLE,
    FOREIGN KEY (C_ID)
        REFERENCES Customer (C_Id),
    FOREIGN KEY (P_ID)
        REFERENCES Product (P_Id)
);

INSERT INTO Product
VALUES(101,"IKIGAI","book",499,1),
(102,"Harry Potter","book",599,1),
(201,"Evergreen Songs","cd",200,1),
(202,"New Songs","cd",250,1),
(301,"Lipstick","cosmetics",650,1),
(302,"Nail Paint","cosmetics",300,1);

INSERT INTO Customer
VALUES(1,"Shivam","12345","9960330617","Rohini, Delhi"),
(2,"Saransh","abcde","9811357600","Krishna Nagar, Delhi");

INSERT INTO Cart
VALUES(DEFAULT,1,101,2,998),
(DEFAULT,1,102,2,1198),
(DEFAULT,2,201,1,200);

SELECT 
    *
FROM
    Product;
SELECT 
    *
FROM
    Customer;
SELECT 
    *
FROM
    Cart;
SELECT 
    *
FROM
    Bill;

SET sql_safe_updates = 0;

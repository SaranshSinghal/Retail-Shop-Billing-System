create database RetailShop;
use RetailShop;

create table Product(
P_Id int primary key,
ProductName varchar(25) not null,
Category varchar(20) not null,
Price double not null,
Quantity int default 0);

CREATE TABLE Customer(
    C_Id int primary key auto_increment,
    C_Name varchar(30) NOT NULL ,
    C_Password varchar(20) not null,
    PhoneNumber varchar(15) not null,
    Address varchar(100));

CREATE TABLE Bill(
    Bill_Id int primary key auto_increment,
    C_Id int,
    Time_Stamp datetime,
    TotalAmount double,
    foreign key (C_ID) references Customer(C_Id));
    
CREATE TABLE Transaction(
    T_Id int primary key auto_increment,
    Bill_Id int,
    P_Id int,
    Quantity int,
    foreign key (Bill_ID) references Bill(Bill_Id),
    foreign key (P_ID) references Product(P_Id));
    
    CREATE TABLE Cart(
    Cart_Id int primary key auto_increment,
    C_Id int,
    P_Id int,
    Quantity int,
    TotalAmount double,
    foreign key (C_ID) references Customer(C_Id),
    foreign key (P_ID) references Product(P_Id));
    
    drop table Cart;

insert into Product
values(101,"IKIGAI","book",499,1),
(102,"Harry Potter","book",599,1),
(201,"Evergreen Songs","cd",200,1),
(202,"New Songs","cd",250,1),
(301,"Lipstick","cosmetics",650,1),
(302,"Nail Paint","cosmetics",300,1);

insert into Customer
values(1,"Shivam","12345","9960330617","Rohini,Delhi"),
(2,"Saransh","abcde","9811357600","Krishna Nagar, Delhi");

insert into Cart
values(default,1,101,2,998),
(default,1,102,2,1198),
(default,2,201,1,200);

select * from Product;
select * from Customer;
select * from Cart;

delete from cart;
set sql_safe_updates = 0;


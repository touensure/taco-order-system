--table name and it's dields would better to use "xx_xx_xx" pattern
create table if not exists Ingredient (
    id varchar(25) primary key,
    name varchar(25) not null,
    type varchar(10) not null
);

create table if not exists Taco(
    id varchar(25) primary key,
    orderId varchar(25) not null,
    name varchar(50) not null,
    createdAt timestamp not null
);

create table if not exists Taco_Order(
    id varchar(25) primary key,
    deliveryName varchar(50) not null,
    deliveryStreet varchar(50) not null,
    deliveryCity varchar(50) not null,
    deliveryState varchar(2) not null,
    deliveryZip varchar(10) not null,
    ccNumber varchar(16) not null,
    ccExpiration varchar(5) not null,
    ccCVV varchar(3) not null,
    placedAt timestamp not null
);

create table if not exists Taco_Ingredients(
    taco_Id varchar(25) not null,
    ingredients_Id varchar(25) not null
);

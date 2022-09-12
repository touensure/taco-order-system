create table if not exists Ingredient (
    id varchar(25) primary key,
    name varchar(25) not null,
    type varchar(10) not null
);

create table if not exists Taco(
    id varchar(25) primary key,
    name varchar(50) not null,
    createdAt timestamp not null
);

create table if not exists Taco_Ingredients(
    tacoId varchar(25) not null,
    ingredientId varchar(25) not null
);

alter table Taco_Ingredients add constraint taco_id_foreign_key foreign key(tacoId) references Taco(id);
alter table Taco_Ingredients add constraint ingredient_id_foreign_key foreign key(ingredientId) references Ingredient(id);

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

create table if not exists Taco_Order_Tacos(
    tacoOrderId varchar(25) not null,
    tacoId varchar(25) not null
);

alter table Taco_Order_Tacos add constraint tacoOrder_id_foreign_key foreign key(tacoOrderId) references Taco_Order(id);
alter table Taco_Order_Tacos add constraint taco_id_foreign_key foreign key(tacoId) references Taco(id);


create table patients
(
    id            bigint       not null auto_increment,
    name          varchar(100) not null,
    email         varchar(100) not null unique,
    cpf           varchar(11)  not null unique,
    streetAddress varchar(100) not null,
    city          varchar(100) not null,
    state         char(2)      not null,
    zipCode       varchar(9)   not null,

    primary key (id)
);

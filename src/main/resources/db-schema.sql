
    drop table BOOKING if exists;

    drop table RESERVED_DATE if exists;

    drop sequence if exists BOOKING_SEQ;

    drop sequence if exists RESERVED_DATE_SEQ;
create sequence BOOKING_SEQ start with 10 increment by 1;
create sequence RESERVED_DATE_SEQ start with 10 increment by 1;

    create table BOOKING (
       ID_BOOKING bigint not null,
        ARRIVAL_DATE timestamp not null,
        DEPARTURE_DATE timestamp not null,
        EMAIL_ADDRESS varchar(100) not null,
        USER_FULLNAME varchar(100) not null,
        primary key (ID_BOOKING)
    );

    create table RESERVED_DATE (
       ID_RESERVED_DATE bigint not null,
        DATE timestamp not null,
        ID_BOOKING bigint,
        primary key (ID_RESERVED_DATE)
    );

    alter table RESERVED_DATE 
       add constraint FKny12a0cyvtnd71c2hqgld4veh 
       foreign key (ID_BOOKING) 
       references BOOKING;

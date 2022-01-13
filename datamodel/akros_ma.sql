create table kanton
(
  kanton_id      integer not null,
  name           varchar(100) not null,
  short_name     varchar(2) not null,
  constraint kanton_pk primary key (kanton_id)
);


create table address
(
  address_id     integer not null,
  kanton_id      integer not null,
  street_name    varchar(100) not null,
  street_number  varchar(20) not null,
  postal_code    varchar(20) not null,
  city           varchar(100) not null,
  constraint address_pk primary key (address_id),
  constraint address_kanton_fk foreign key (kanton_id) references kanton(kanton_id)
);


create table real_estate_kind
(
  real_estate_kind_id   integer not null,
  description           varchar(100) not null,
  constraint real_estate_kind_pk primary key (real_estate_kind_id)
);


create table advertiser
(
  advertiser_id  integer not null,
  first_name     varchar(100) not null,
  surename       varchar(100) not null,
  email          varchar(100) not null,
  phone_number   varchar(50) not null,
  constraint advertiser_pk primary key (advertiser_id)
);


create table real_estate
(
  real_estate_id        integer not null,
  advertiser_id         integer not null,
  real_estate_kind_id   integer not null,
  address_id            integer not null,
  room_count            number(9,1) not null,
  size_sqm              integer not null,
  valid_from            date not null,
  valid_to              date not null,
  headline              varchar(100) not null,
  short_description     varchar(500) not null,
  long_description      varchar(1000) not null,
  constraint real_estate_pk primary key (real_estate_id),
  constraint real_estate_advertiser_fk foreign key (advertiser_id) references advertiser(advertiser_id),
  constraint real_estate_real_estate_kind_fk foreign key (real_estate_kind_id) references real_estate_kind(real_estate_kind_id),
  constraint real_estate_address_fk foreign key (address_id) references address(address_id)
);





select 'drop table ' || object_name || ';' from user_objects where object_type = 'TABLE';

drop table address;
drop table kanton;
drop table topic_value;
drop table topic;
drop table advertiser;
drop table field_type_choose;
drop table field_type;
drop table field_type_definition;
drop table theme;


create table theme
(
  theme_id              serial,
  description           varchar(1000) not null,
  short_description     varchar(100) not null,
  constraint theme_pk primary key (theme_id)
);


create table field_type_definition
(
  field_type_definition_id  integer not null,
  description               varchar(1000) not null,
  constraint field_type_definition_pk primary key (field_type_definition_id)
);


create table field_type
(
  field_type_id             serial,
  field_type_definition_id  integer not null,
  theme_id                  integer not null,
  description               varchar(1000) not null,
  short_description         varchar(100) not null,
  min_value                 integer not null,
  max_value                 integer not null,
  sort_number               integer not null,
  required                  boolean default false not null,
  searchable                boolean default false not null,
  search                    boolean default false not null,
  offer                     boolean default false not null,
  constraint field_type_pk primary key (field_type_id),
  constraint field_type_theme_fk foreign key (theme_id) references theme(theme_id),
  constraint field_type_field_type_definition_fk foreign key (field_type_definition_id) references field_type_definition(field_type_definition_id)
);


create table field_type_choose
(
  field_type_choose_id  serial,
  field_type_id         integer not null,
  description           varchar(1000) not null,
  sort_number           integer not null,
  constraint field_type_choose_pk primary key (field_type_choose_id),
  constraint field_type_choose_field_type_fk foreign key (field_type_id) references field_type(field_type_id)
);


create table advertiser
(
  advertiser_id  serial,
  first_name     varchar(100) not null,
  surename       varchar(100) not null,
  email          varchar(100) not null,
  phone_number   varchar(50) not null,
  constraint advertiser_pk primary key (advertiser_id)
);


create table topic
(
  topic_id              serial,
  advertiser_id         integer not null,
  theme_id              integer not null,
  valid_from            date not null,
  valid_to              date not null,
  search_or_offer       varchar(5) check(search_or_offer in ('SEARCH', 'OFFER')) not null,
  constraint topic_pk primary key (topic_id),
  constraint topic_theme_fk foreign key (theme_id) references theme(theme_id),
  constraint topic_advertiser_fk foreign key (advertiser_id) references advertiser(advertiser_id)
);


create table topic_value
(
  topic_value_id        serial,
  topic_id              integer not null,
  theme_id              integer not null,
  field_type_id         integer not null,
  value_num             numeric(18,2),
  value_varchar         varchar(4000),
  value_date            date,
  constraint topic_value_pk primary key (topic_value_id),
  constraint topic_value_topic_fk foreign key (topic_id) references topic(topic_id),
  constraint topic_value_theme_fk foreign key (theme_id) references theme(theme_id),
  constraint topic_value_field_type_fk foreign key (field_type_id) references field_type(field_type_id)
);


create table kanton
(
  kanton_id      integer not null,
  name           varchar(100) not null,
  short_name     varchar(2) not null,
  constraint kanton_pk primary key (kanton_id)
);


create table address
(
  address_id     serial,
  kanton_id      integer not null,
  street_name    varchar(100) not null,
  street_number  varchar(20) not null,
  postal_code    varchar(20) not null,
  city           varchar(100) not null,
  constraint address_pk primary key (address_id),
  constraint address_kanton_fk foreign key (kanton_id) references kanton(kanton_id)
);


create view vw_theme
as
select theme_id,
       short_description,
       description,
       (select count(1) from topic where search_or_offer='SEARCH') search_count, 
       (select count(1) from topic where search_or_offer='OFFER') offer_count
  from theme;

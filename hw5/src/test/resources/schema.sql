DROP ALL OBJECTS;

create table if not exists author
(
    id        uuid default random_uuid() not null
        constraint pk_author_id
            primary key,
    name      varchar(255)                   not null,
    last_name varchar(255)                   not null
);

create table book
(
    id               uuid default random_uuid() not null
        constraint pk_book_id
            primary key,
    title            varchar(255)                   not null,
    publication_year smallint,
    page_count       smallint
);

create table genre
(
    id   uuid  default random_uuid() not null
        constraint pk_genre_id
            primary key,
    name varchar(255)                   not null
);

create table author2book
(
    id        uuid default random_uuid() not null
        constraint pk_author2book_id
            primary key,
    author_id uuid
        constraint fk_author_id
            references author
            on delete cascade,
    book_id   uuid
        constraint fk_book_id1
            references book
            on delete cascade
);

create table book2genre
(
    id       uuid default random_uuid() not null
        constraint pk_book2genre_id
            primary key,
    book_id  uuid
        constraint fk_book_id2
            references book
            on delete cascade,
    genre_id uuid
        constraint fk_genre_id
            references genre
            on delete cascade
);
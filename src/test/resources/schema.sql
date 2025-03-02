DROP TABLE IF EXISTS piece;
drop table IF EXISTS game;

create table game
(
    id varchar(100) not null unique,
    password varchar(100) not null,
    turn varchar(10) not null,
    force_end_flag tinyint(1) not null default false
);

create table piece
(
    id int AUTO_INCREMENT PRIMARY KEY,
    name varchar(10) not null,
    color varchar(10) not null,
    position varchar(10) not null,
    game_id varchar(100) not null,
    foreign key (game_id) references game (id)
);

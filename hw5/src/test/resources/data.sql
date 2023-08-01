INSERT INTO author (id, name, last_name) VALUES ('2ed2a976-7826-47d4-8015-f43d86b863ac', 'Михаил', 'Булгаков');
INSERT INTO author (id, name, last_name) VALUES ('062f2cb8-3cd3-4572-bb55-9a64c36b28ef', 'Федор', 'Достоевский');
INSERT INTO author (id, name, last_name) VALUES ('2bb3ed4c-ac60-4b46-9861-3fba27fc0ab3', 'Эрих Мария', 'Ремарк');
INSERT INTO author (id, name, last_name) VALUES ('a800e9e9-f488-4f4c-a62e-b67231f639f6', 'Джером', 'Сэлинджер');
INSERT INTO author (id, name, last_name) VALUES ('51819507-bcc9-46ac-ada6-8378ae881505', 'Михаил', 'Лермонтов');
INSERT INTO author (id, name, last_name) VALUES ('9c96bca5-ba30-4228-9a46-f2862926da3a', 'Оскар', 'Уайльд');
INSERT INTO author (id, name, last_name) VALUES ('4e37b705-4dd9-48f3-93b1-395668123335', 'Никита', 'Алпатов');

INSERT INTO book (id, title, publication_year, page_count) VALUES ('80e9ace3-1a83-40ae-b812-32b92785fb3b', 'Мастер и Маргарита', 1876, 432);
INSERT INTO book (id, title, publication_year, page_count) VALUES ('9b7cc78d-edc0-473a-9980-f3645e37282f', 'Преступление и наказание', 1956, 321);
INSERT INTO book (id, title, publication_year, page_count) VALUES ('b518423a-ffd0-41dd-ad92-8fe88f17fa14', 'Три товарища', 1853, 543);
INSERT INTO book (id, title, publication_year, page_count) VALUES ('65628c24-1a9e-4ec9-a8b4-9cd48c185880', 'Над пропастью во ржи', 1936, 543);
INSERT INTO book (id, title, publication_year, page_count) VALUES ('9be85ff4-ad4b-416e-b7f4-9e402aa6648d', 'Герой нашего времени', 1956, 234);
INSERT INTO book (id, title, publication_year, page_count) VALUES ('eb5d843e-eeb1-4bdf-80a1-a61b2aee16fb', 'Портрет Дориана Грея', 1875, 324);
INSERT INTO book (id, title, publication_year, page_count) VALUES ('7c8ad2d5-36d0-420e-8b03-5927cab22e14', 'Горе,от,ума', 2002, 444);

INSERT INTO genre (id, name) VALUES ('52673326-6f0b-41e3-a7fb-32392fff84b7', 'Роман');
INSERT INTO genre (id, name) VALUES ('02918ba2-76d5-4d19-b862-49f841eeac3b', 'Драма');
INSERT INTO genre (id, name) VALUES ('dad84477-3e6f-4b63-865c-bcdeb9a9ec24', 'Комедия');
INSERT INTO genre (id, name) VALUES ('83514ba5-1b00-46d9-8f03-e2f6cd4bcdb3', 'Трагедия');
INSERT INTO genre (id, name) VALUES ('ea2a3c7e-36fa-495a-9c67-ba270b7f5069', 'Повесть');
INSERT INTO genre (id, name) VALUES ('d3dd4149-cfd8-4516-998c-bddf387fe35f', 'Рассказ');
INSERT INTO genre (id, name) VALUES ('5c8b9fc7-dadf-4cc5-9d18-39470dd475e5', 'Поэма');
INSERT INTO genre (id, name) VALUES ('ce48cc03-321c-4122-b43c-c4450cf62a0a', 'Сказка');
INSERT INTO genre (id, name) VALUES ('0501242e-4e2f-45fa-893e-d894460e9699', 'Детектив');
INSERT INTO genre (id, name) VALUES ('849f0952-43dc-4a30-81b9-8f846b272896', 'Утопия');

INSERT INTO book2genre (id, book_id, genre_id) VALUES ('2309a957-24f6-4188-9f0e-11bc72104caa', '80e9ace3-1a83-40ae-b812-32b92785fb3b', '52673326-6f0b-41e3-a7fb-32392fff84b7');
INSERT INTO book2genre (id, book_id, genre_id) VALUES ('f2521b82-d148-410c-a8a7-3245f5af855f', '9b7cc78d-edc0-473a-9980-f3645e37282f', '02918ba2-76d5-4d19-b862-49f841eeac3b');
INSERT INTO book2genre (id, book_id, genre_id) VALUES ('24c5daab-5a84-4301-b3fe-c362c15542bf', 'b518423a-ffd0-41dd-ad92-8fe88f17fa14', 'dad84477-3e6f-4b63-865c-bcdeb9a9ec24');
INSERT INTO book2genre (id, book_id, genre_id) VALUES ('c2db36c4-a27d-49e0-811b-7606fcf249c2', '65628c24-1a9e-4ec9-a8b4-9cd48c185880', '83514ba5-1b00-46d9-8f03-e2f6cd4bcdb3');
INSERT INTO book2genre (id, book_id, genre_id) VALUES ('3df5a158-9749-4f6e-a147-e606cdfd6786', '9be85ff4-ad4b-416e-b7f4-9e402aa6648d', 'ea2a3c7e-36fa-495a-9c67-ba270b7f5069');
INSERT INTO book2genre (id, book_id, genre_id) VALUES ('63151f33-38a3-4d45-b651-fcecfa369701', 'eb5d843e-eeb1-4bdf-80a1-a61b2aee16fb', 'd3dd4149-cfd8-4516-998c-bddf387fe35f');
INSERT INTO book2genre (id, book_id, genre_id) VALUES ('16c232be-bb8a-4f38-9a5e-69da54ebd2ed', '9be85ff4-ad4b-416e-b7f4-9e402aa6648d', '5c8b9fc7-dadf-4cc5-9d18-39470dd475e5');
INSERT INTO book2genre (id, book_id, genre_id) VALUES ('fc5206f3-d015-4d1d-8c32-468d66fa1dae', 'eb5d843e-eeb1-4bdf-80a1-a61b2aee16fb', 'ce48cc03-321c-4122-b43c-c4450cf62a0a');
INSERT INTO book2genre (id, book_id, genre_id) VALUES ('40b59e0a-635d-4f4d-8ee6-f85a66e156a3', '7c8ad2d5-36d0-420e-8b03-5927cab22e14', '849f0952-43dc-4a30-81b9-8f846b272896');

INSERT INTO author2book (id, author_id, book_id) VALUES ('209eff0b-74de-4c8d-925d-abd0762b0435', '2ed2a976-7826-47d4-8015-f43d86b863ac', '80e9ace3-1a83-40ae-b812-32b92785fb3b');
INSERT INTO author2book (id, author_id, book_id) VALUES ('5dd2da97-b54d-4260-9ca3-d0426967e3c8', '062f2cb8-3cd3-4572-bb55-9a64c36b28ef', '9b7cc78d-edc0-473a-9980-f3645e37282f');
INSERT INTO author2book (id, author_id, book_id) VALUES ('488d43a3-7930-406e-a74c-57209832747a', '2bb3ed4c-ac60-4b46-9861-3fba27fc0ab3', 'b518423a-ffd0-41dd-ad92-8fe88f17fa14');
INSERT INTO author2book (id, author_id, book_id) VALUES ('6c7a7a78-ddb3-4ce0-81ba-48c84dd7d7e0', 'a800e9e9-f488-4f4c-a62e-b67231f639f6', '65628c24-1a9e-4ec9-a8b4-9cd48c185880');
INSERT INTO author2book (id, author_id, book_id) VALUES ('d4f3115d-ef2e-4cab-b5a9-57c76d80095c', '51819507-bcc9-46ac-ada6-8378ae881505', '9be85ff4-ad4b-416e-b7f4-9e402aa6648d');
INSERT INTO author2book (id, author_id, book_id) VALUES ('433d0709-159c-4e43-a7e7-78bd50c2caf1', '9c96bca5-ba30-4228-9a46-f2862926da3a', 'eb5d843e-eeb1-4bdf-80a1-a61b2aee16fb');
INSERT INTO author2book (id, author_id, book_id) VALUES ('2ce2a00c-1748-4117-b352-8659a01fe615', '4e37b705-4dd9-48f3-93b1-395668123335', '7c8ad2d5-36d0-420e-8b03-5927cab22e14');




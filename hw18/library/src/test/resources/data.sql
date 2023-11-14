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


insert into public.comment (id, comment, book_id)
values  ('c094c253-1a81-4848-9ef5-3c41667514fb', 'Комментарий1', '80e9ace3-1a83-40ae-b812-32b92785fb3b'),
        ('de690fb2-2795-4d59-bc64-9d9a80f974bc', 'Комментарий2', '9b7cc78d-edc0-473a-9980-f3645e37282f'),
        ('1259ecac-a2a2-48e3-ae2d-a7eea9a9c0a8', 'Комментарий3', 'b518423a-ffd0-41dd-ad92-8fe88f17fa14'),
        ('4275b0ef-0530-4690-8b10-26d77131a699', 'Комментарий4', '65628c24-1a9e-4ec9-a8b4-9cd48c185880'),
        ('15ee263a-c112-4c33-8572-d4f06ce2604f', 'Комментарий5', '9be85ff4-ad4b-416e-b7f4-9e402aa6648d'),
        ('5e830856-1c08-4df4-a1d2-b9f50467fe30', 'Комментарий6', '80e9ace3-1a83-40ae-b812-32b92785fb3b'),
        ('30863a74-de74-4a1d-babb-97f216fe818e', 'Комментарий7', '9b7cc78d-edc0-473a-9980-f3645e37282f'),
        ('ff1d2a7b-b581-47f6-a979-5a27e4197c39', 'Комментарий8', 'b518423a-ffd0-41dd-ad92-8fe88f17fa14'),
        ('d298e090-2797-4ef8-a7e5-7ccd6c53f51a', 'Комментарий9', '65628c24-1a9e-4ec9-a8b4-9cd48c185880'),
        ('a6800f4c-a2c1-49fd-b2ff-66bfbe10f620', 'Комментарий10', '9be85ff4-ad4b-416e-b7f4-9e402aa6648d'),
        ('5d419679-58e8-4146-9519-bc94c12f0eb3', 'Комментарий11', '80e9ace3-1a83-40ae-b812-32b92785fb3b'),
        ('35124a02-10a8-4792-a1ea-184303cb3bfb', 'Комментарий12', '9b7cc78d-edc0-473a-9980-f3645e37282f'),
        ('d4bac31e-4744-433b-9e63-a260f80e9bf0', 'Комментарий13', 'b518423a-ffd0-41dd-ad92-8fe88f17fa14'),
        ('1c7d9b4f-877c-4ab1-b1a6-c10e8d213e58', 'Комментарий14', '65628c24-1a9e-4ec9-a8b4-9cd48c185880'),
        ('2d23d368-348a-43ff-a43c-1dfa46b086cf', 'Комментарий15', '9be85ff4-ad4b-416e-b7f4-9e402aa6648d'),
        ('db512922-e5af-41ef-ade9-ae8367fea9be', 'Комментарий16', '80e9ace3-1a83-40ae-b812-32b92785fb3b'),
        ('7907fb65-d448-49e5-892e-3e7104c1fbdd', 'Комментарий17', '9b7cc78d-edc0-473a-9980-f3645e37282f'),
        ('d9eaf8da-d40e-44af-a8d6-4e9e94cf408d', 'Комментарий18', 'b518423a-ffd0-41dd-ad92-8fe88f17fa14'),
        ('559a7dbd-84be-4987-ba6d-cfd007be733e', 'Комментарий19', '65628c24-1a9e-4ec9-a8b4-9cd48c185880'),
        ('8e831125-fa0b-48c1-bc8f-542d1d78f305', 'Комментарий20', '9be85ff4-ad4b-416e-b7f4-9e402aa6648d'),
        ('1c299e99-3897-4598-a437-689b6432d2d1', 'Комментарий21', '9be85ff4-ad4b-416e-b7f4-9e402aa6648d');
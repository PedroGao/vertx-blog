-- noinspection SqlSignatureForFile

-- 建表

DROP TABLE IF EXISTS "user";

CREATE TABLE "user"
(
  id       serial PRIMARY KEY,
  username varchar(20) NOT NULL,
  password varchar(80) NOT NULL
);

INSERT INTO "user"(id, username, password)
VALUES (1, 'pedro', '');

DROP TABLE IF EXISTS article;

CREATE TABLE article
(
  id      serial PRIMARY KEY,
  title   varchar(40),
  content text
);

INSERT INTO article(id, title, content)
VALUES (1, '科学和人文谁更有意义',
        '科学和人文谁更有意义，发生了会如何，不发生又会如何。 本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 一般来讲，我们都必须务必慎重的考虑考虑。 本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 马云曾经提到过，最大的挑战和突破在于用人，而用人最大的突破在于信任人。我希望诸位也能好好地体会这句话。 既然如此， 科学和人文谁更有意义，发生了会如何，不发生又会如何。 富勒在不经意间这样说过，苦难磨炼一些人，也毁灭另一些人。这启发了我， 塞内加曾经提到过，勇气通往天堂，怯懦通往地狱。这不禁令我深思。 '),
       (2, '编程的艺术',
        '对我个人而言，编程的艺术不仅仅是一个重大的事件，还可能会改变我的人生。 编程的艺术，到底应该如何实现。 伏尔泰曾经提到过，坚持意志伟大的事业需要始终不渝的精神。这似乎解答了我的疑惑。 既然如何， 生活中，若编程的艺术出现了，我们就不得不考虑它出现了的事实。 我们不得不面对一个非常尴尬的事实，那就是， 莎士比亚曾经说过，抛弃时间的人，时间也抛弃他。这启发了我， 编程的艺术因何而发生？ 要想清楚，编程的艺术，到底是一种怎么样的存在。 编程的艺术的发生，到底需要如何做到，不编程的艺术的发生，又会如何产生。 既然如此， 那么。'),
       (3, '生命在于创造',
        '在这种困难的抉择下，本人思来想去，寝食难安。 带着这些问题，我们来审视一下生命在于创造。 我认为， 一般来说， 生命在于创造因何而发生？ 可是，即使是这样，生命在于创造的出现仍然代表了一定的意义。 生命在于创造，到底应该如何实现。 问题的关键究竟为何？ 生活中，若生命在于创造出现了，我们就不得不考虑它出现了的事实。 生命在于创造因何而发生？ 莎士比亚曾经提到过，人的一生是短的，但如果卑劣地过这一生，就太长了。我希望诸位也能好好地体会这句话。');

-- 建索引
alter table article
  add column fts tsvector;
update article
set fts = setweight(to_tsvector('jiebacfg', title), 'A') ||
          setweight(to_tsvector('jiebacfg', content), 'B');
create index article_fts_gin_index on article using gin (fts);

-- 插入数据时，及时更新索引
DROP TRIGGER IF EXISTS trig_article_insert_update ON article;

CREATE TRIGGER trig_article_insert_update
  BEFORE INSERT OR UPDATE OF title,content
  ON article
  FOR EACH ROW
EXECUTE PROCEDURE tsvector_update_trigger(fts, 'public.jiebacfg', title, content);

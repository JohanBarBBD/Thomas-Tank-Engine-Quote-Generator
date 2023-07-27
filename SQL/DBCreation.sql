USE master;
GO

DROP DATABASE IF EXISTS TTTEADB;

CREATE DATABASE TTTEADB;
GO

USE TTTEADB;
GO

CREATE TABLE [characters] (
	[id] BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1),
	[name] VARCHAR(100)
);
GO

CREATE TABLE [users] (
	[id] BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1) ,
	[email] VARCHAR(250) UNIQUE NOT NULL,
	[name] VARCHAR(100) NOT NULL
);
GO

CREATE TABLE [quotes] (
	[id] BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1),
	[quote_ep] INT NOT NULL,
	[quote_season] INT NOT NULL,
	[quote_text] VARCHAR(400) NOT NULL,
	[characterid] BIGINT NOT NULL FOREIGN KEY REFERENCES [Characters](id) ON DELETE CASCADE
);
GO

CREATE TABLE [favourites] (
	[id] BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1),
	[quoteid] BIGINT NOT NULL,
	[userid] BIGINT NOT NULL,
	[date_favourited] DATETIME NOT NULL,
	CONSTRAINT [QT_FK] FOREIGN KEY ([quoteid]) REFERENCES [quotes]([id]) ON DELETE CASCADE,
	CONSTRAINT [UT_FK] FOREIGN KEY ([userid]) REFERENCES [users]([id]) ON DELETE CASCADE,
);
GO


CREATE PROCEDURE getCharacterIDFromName @CharName VARCHAR(100)
AS
SELECT TOP 1 [id]
FROM [characters]
WHERE [characters].name = @CharName;
GO

CREATE PROCEDURE insertQuoteWithCharacterName @Quote VARCHAR(400), @CharName VARCHAR(100), @EPNum INT, @SeasonNum INT
AS
DECLARE @id INT
SELECT  @id = [characters].id
FROM    [characters]
WHERE [characters].name = @CharName;

INSERT INTO [quotes] VALUES
(
	@EPNum,
	@SeasonNum,
	@Quote,
	@id
);
GO

CREATE PROCEDURE insertFavourite @QuoteID INT, @UserID INT
AS
INSERT INTO [favourites] VALUES
(
	@QuoteID,
	@UserID,
	GETDATE()
);
GO

CREATE VIEW userAndFavs
AS
SELECT
	[users].email,
	[users].[name],
	[quotes].quote_text,
	[quotes].quote_ep AS [Episode],
	[quotes].quote_season AS [Season],
	[characters].[name] AS [Spoken by]
FROM users
INNER JOIN favourites ON [users].id = [favourites].userid
INNER JOIN quotes ON [quotes].id = favourites.quoteid
INNER JOIN characters ON [quotes].characterid = characters.id
GO

INSERT INTO [users] VALUES
(
	'TestUser@gmail.com',
	'Test'
),
(
	'Jaco@gmail.com',
	'Jaco'
),
(
	'Jorge@gmail.com',
	'Jorge'
),
(
	'Jarred@gmail.com',
	'Jarred'
),
(
	'Jackson@gmail.com',
	'Jackson'
),
(
	'Jerry@gmail.com',
	'Jerry'
),
(
	'Jean@gmail.com',
	'Jean'
),
(
	'Justin@gmail.com',
	'Justin'
),
(
	'Jack@gmail.com',
	'Jack'
),
(
	'Joel@gmail.com',
	'Joel'
),
(
	'Jude@gmail.com',
	'Jude'
),
(
	'Jacob@gmail.com',
	'Jacob'
),
(
	'John@gmail.com',
	'John'
),
(
	'Jace@gmail.com',
	'Jace'
),
(
	'Jayden@gmail.com',
	'Jayden'
),
(
	'Jesse@gmail.com',
	'Jesse'
),
(
	'Jasper@gmail.com',
	'Jasper'
),
(
	'Julian@gmail.com',
	'Julian'
),
(
	'Jake@gmail.com',
	'Jake'
)
GO

INSERT INTO [characters] VALUES
('James'),
('Lorenzo'),
('Gordon'),
('Emily'),
('Beppe'),
('Sam'),
('Nia'),
('Rebecca'),
('Toby'),
('Reg'),
('Ryan'),
('Skiff'),
('Percy'),
('Stefano'),
('Sailor Jhon'),
('Rex'),
('Thomas'),
('Bert'),
('Annie'),
('Mike'),
('Hongmei'),
('Arthur'),
('Axel'),
('Ashima'),
('Bash'),
('Bill'),
('Belle'),
('Ben'),
('Bertie'),
('Butch'),
('Carlos'),
('Clarabel'),
('Charlie'),
('Henry'),
('Sir Topham Hatt'),
('Clarabel'),
('Stephen Hatt'),
('Jeremiah Jobling'),
('Coaches'),
('George Carlin'),
('Edward'),
('Edward''s Driver'),
('Cars'),
('Porter'),
('Driver'),
('The Fat Controller'),
('Everyone'),
('Ringo Starr');
GO

--Season 1 Ep 1

EXEC insertQuoteWithCharacterName @Quote = 'Thomas is a tank engine who lives at the big station on the Island of Sodor',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He''s a cheeky little engine with six small wheels, a short stumpy funnel, a short stumpy boiler and a short stumpy dome',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He''s a fussy little engine too',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Always pulling coaches about ready for the big engines can take on long journeys',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'And when trains come in, he pulls the empty coaches away so that the big engines can go on rest',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Thomas thinks no engine works has hard as he does',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He loves playing tricks on them, including Gordon the biggest and proudest engine of all',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Thomas likes whistling rudely at him',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Wake up, lazybones, why don''t you work hard like me?',  @CharName = 'Thomas', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'One day after pulling the big express, Gordon had arrived back at the sidings very tired',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He was just going to sleep when Thomas came up in his cheeky way',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Wake up, lazybones, do some hard work for a change! You can''t catch me!',  @CharName = 'Thomas', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'And off he ran laughing',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Instead of going to sleep again, Gordon thought how he can back at Thomas',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'One morning, Thomas wouldn''t wake up',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'His driver and fireman couldn''t make him start',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'His fire went out and there was not enough steam',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'It was nearly time for the express',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'People were waiting, but the coaches weren''t ready',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'At last, Thomas started',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Oh dear, oh dear!',  @CharName = 'Thomas', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He yawned',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He fussed into the station where Gordon was waiting',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Hurry up, you!',  @CharName = 'Gordon', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Said Gordon',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Hurry yourself!',  @CharName = 'Thomas', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Replied Thomas',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Gordon began making his plan',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Yes',  @CharName = 'Gordon', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Said Gordon',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'I will',  @CharName = 'Gordon', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'And almost before the coaches had stopped moving, Gordon reversed quickly and was coupled to the train',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Get in quickly, please!',  @CharName = 'Gordon', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He whistled',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Thomas usually pushed behind the big trains to help them start, but he was always uncoupled first',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'This time, Gordon start so quickly they forgot to uncouple Thomas',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Gordon''s chance had come',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Come on, come on!',  @CharName = 'Gordon', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Puffed Gordon to the coaches',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'The train went faster and faster',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Too fast for Thomas, he wanted to stop, but he couldn''t',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Peep peep, stop, stop!',  @CharName = 'Thomas', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Hurry, hurry, hurry!',  @CharName = 'Gordon', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Laughed Gordon',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'You can''t get away, you can''t get away!',  @CharName = 'Coaches', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Laughed the coaches',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Poor Thomas was going faster than he had ever gone before',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He was out of breath and his wheels hurt him, but he had to go on',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'I shall never be the same again!',  @CharName = 'Thomas', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He thought sadly',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'My wheels will be quite worn out!',  @CharName = 'Thomas', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'At last, they stopped at the station',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Thomas was uncoupled and he felt very silly and exhausted',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Next he went on to the turntable thinking of everyone laughing at him',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'And then he ran on to a siding out of the way',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Well, little Thomas',  @CharName = 'Gordon', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Chuckled Gordon',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Now you know what hard work means, don''t you?',  @CharName = 'Gordon', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Poor Thomas couldn''t answer',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He had no breath',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He just puffed slowly away to rest and had a long long drink',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He went home very slowly, and was careful afterwards never to be cheeky to Gordon again',  @CharName = 'Ringo Starr', @EPNum = 1, @SeasonNum = 1;

--Season 1 Ep 2
EXEC insertQuoteWithCharacterName @Quote = 'One day, Edward was in the shed where he lived with the other engines',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'They were all bigger than Edward and boasted about it',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'The driver won''t choose you again',  @CharName = 'Gordon', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Said Gordon',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He wants strong engines like us',  @CharName = 'Gordon', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'But the driver and fireman felt sorry for Edward',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Would you like to come out today?',  @CharName = 'Edward''s Driver', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Oh yes, please',  @CharName = 'Edward', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Said Edward',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'So they lit his fire, made lots of steam and Edward puffed away',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'The other engines were very cross of being left behind',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Edward worked hard all day',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'The coaches thought he was very kind and the driver was very pleased',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'I''m going out again tomorrow',  @CharName = 'Edward', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Edward told the other engines that night',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'What do you think at that?',  @CharName = 'Edward', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'But he didn''t hear what they thought, for he was so tired and happy that he fell asleep at once',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Next morning, Edward woke up to find nothing had change',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Gordon was still boasting',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'You watch me, little Edward, as I rush through with the express',  @CharName = 'Gordon', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'That will be a splendid sight for you',  @CharName = 'Gordon', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Goodbye, little Edward',  @CharName = 'Gordon', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Look out for me this afternoon',  @CharName = 'Gordon', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Edward went off to do some shunting',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He liked shunting',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'It was fun playing with freight cars',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He would come out quietly and gave them a push',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Then he would stop, and the silly freight cars will go bump into the each other',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'OH!',  @CharName = 'Cars', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'They cried',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Whatever is happening?',  @CharName = 'Cars', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Edward played till there were no more freight cars',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Then he stopped to rest',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Presently, he heard a whistle',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Gordon was cross',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Instead of nice new coaches, he was pulling a dirty freight train',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'A freight train! A freight train! A freight train!',  @CharName = 'Gordon', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He grumbled',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'The shame of it! The shame of it! Oh, the shame of it!',  @CharName = 'Gordon', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Edward laughed and went to find some more freight cars',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Then, there was trouble',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Gordon can''t get up the hill',  @CharName = 'Porter', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'The porter called to Edward''s Driver',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Will you take Edward and push him please?',  @CharName = 'Porter', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'They found Gordon halfway up, and very cross',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'His driver and fireman were talking to him severely',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'You''re not trying',  @CharName = 'Driver', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'I can''t do it!',  @CharName = 'Gordon', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Said Gordon',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'The noisy freight cars hold an engine back so!',  @CharName = 'Gordon', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Edward''s driver came up',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'We''ve come to push',  @CharName = 'Edward''s Driver', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'No use at all!',  @CharName = 'Gordon', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Said Gordon',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'You wait and see',  @CharName = 'Edward''s Driver', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Replied Edward''s driver',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'They brought the train back to the bottom of the hill',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'I''m ready',  @CharName = 'Edward', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Said Edward',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'No good',  @CharName = 'Gordon', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Grumbled Gordon',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'They pulled and pushed as hard as they could',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'I can''t do it! I can''t do it! I can''t do it!',  @CharName = 'Gordon', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Puffed Gordon',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'I will do it! I will do it! I will do it!',  @CharName = 'Edward', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Puffed Edward',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Edward pushed and puffed and puffed and pushed as hard as ever he could',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'And almost before he realized it, Gordon found himself at the top of the hill',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'I''ve done it! I''ve done it! I''ve done it!',  @CharName = 'Gordon', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He said proudly',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He forgot all about kind Edward and didn''t say thank you',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Edward was left out of breath and far behind, but he was happy because he had been so helpful',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'At the next station, he found that the driver and fireman were very pleased with him',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'The fireman gave him a nice long drink and the driver said',  @CharName = 'George Carlin', @EPNum = 2, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'I''ll get out my paint tomorrow, and give you your beautiful coat of blue with red stripes, then you''ll be the smartest engine in the shed',  @CharName = 'Driver', @EPNum = 2, @SeasonNum = 1;

--Season 1 Ep 3
EXEC insertQuoteWithCharacterName @Quote = 'Once an engine attached to a train was afraid of a few drops of rain',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'It went into a tunnel and squeaked through its funnel, and wouldn''t come out again',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'The engine''s name is Henry',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'His driver and fireman argued with him, but he would not move',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'The rain will spoil my lovely green paint and red stripes',  @CharName = 'Henry', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He said',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'The guard blew his whistle till he had no more breath, and waved his flag till his arms ached, but Henry still stayed in the tunnel and blew steam at him',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'I''m not going to spoil my lovely green paint and red stripes for you',  @CharName = 'Henry', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Then, along came Sir Topham Hatt, the man in charge of all the engines on Sodor',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'They call him the Fat Controller',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'We will pull you out',  @CharName = 'The Fat Controller', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Said the Fat Controller',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'But Henry only blew steam at him',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Everyone pulled except the Fat Controller',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Because',  @CharName = 'The Fat Controller', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = '(clears his throat)',  @CharName = 'The Fat Controller', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He said',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'my doctor has forbidden me to pull',  @CharName = 'The Fat Controller', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'But still, Henry stayed in the tunnel',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Then, they tried pushing from the other end',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'The Fat Controller said',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = '1, 2, 3, push!',  @CharName = 'The Fat Controller', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'but he didn''t help',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = '(clears his throat again) My doctor has forbidden me to push',  @CharName = 'The Fat Controller', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He said',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'They pushed, and pushed, and pushed, but still, Henry stayed in the tunnel',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'At last, Thomas came along',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'The guard waved his red flag and stopped him',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Everyone argued with Henry',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Look, it has stopped raining',  @CharName = 'Everyone', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'They said',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Yes, but it will begin again soon',  @CharName = 'Henry', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Said Henry',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'And what will become of my green paint with red stripes then?',  @CharName = 'Henry', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Thomas pushed and puffed and pushed as hard as ever he could',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'But still Henry stayed in the tunnel',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Eventually, even the Fat Controller gave up',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'We shall take away your rails',  @CharName = 'The Fat Controller', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He said',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'and leave you here for always and always and always',  @CharName = 'The Fat Controller', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'They took up the old rails, and built a wall in front of him, so that Henry couldn''t get out of the tunnel anymore',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'All he could do was to watch the trains rushing through the other tunnel',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He was very sad, because he thought no one would see his lovely green paint with red stripes again',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'As time went on, Edward and Gordon would often pass by',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Edward would say',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Peep! Peep! Hello!',  @CharName = 'Edward', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'And Gordon would say',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Boop! Boop! Boop! Serves you right!',  @CharName = 'Gordon', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Poor Henry had no steam to answer',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'His fire had gone out',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'Soot and dirt from the tunnel had spoiled his lovely green paint with red stripes anyway',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'He wondered if he would ever be allowed to pull trains again',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
EXEC insertQuoteWithCharacterName @Quote = 'But I think he deserved his punishment, don''t you?',  @CharName = 'Ringo Starr', @EPNum = 3, @SeasonNum = 1;
GO

EXEC insertFavourite @QuoteID =  188, @UserID =    1;
EXEC insertFavourite @QuoteID =    5, @UserID =    1;
EXEC insertFavourite @QuoteID =  129, @UserID =    1;
EXEC insertFavourite @QuoteID =    9, @UserID =    1;
EXEC insertFavourite @QuoteID =  175, @UserID =    2;
EXEC insertFavourite @QuoteID =   11, @UserID =    2;
EXEC insertFavourite @QuoteID =   15, @UserID =    2;
EXEC insertFavourite @QuoteID =  164, @UserID =    2;
EXEC insertFavourite @QuoteID =  121, @UserID =    2;
EXEC insertFavourite @QuoteID =   45, @UserID =    2;
EXEC insertFavourite @QuoteID =  125, @UserID =    2;
EXEC insertFavourite @QuoteID =  169, @UserID =    3;
EXEC insertFavourite @QuoteID =  161, @UserID =    3;
EXEC insertFavourite @QuoteID =  182, @UserID =    3;
EXEC insertFavourite @QuoteID =   23, @UserID =    3;
EXEC insertFavourite @QuoteID =  171, @UserID =    3;
EXEC insertFavourite @QuoteID =   90, @UserID =    3;
EXEC insertFavourite @QuoteID =   11, @UserID =    3;
EXEC insertFavourite @QuoteID =  135, @UserID =    3;
EXEC insertFavourite @QuoteID =    2, @UserID =    4;
EXEC insertFavourite @QuoteID =    1, @UserID =    4;
EXEC insertFavourite @QuoteID =  100, @UserID =    4;
EXEC insertFavourite @QuoteID =   19, @UserID =    5;
EXEC insertFavourite @QuoteID =  160, @UserID =    5;
EXEC insertFavourite @QuoteID =  174, @UserID =    5;
EXEC insertFavourite @QuoteID =  170, @UserID =    5;
EXEC insertFavourite @QuoteID =   26, @UserID =    5;
EXEC insertFavourite @QuoteID =  112, @UserID =    5;
EXEC insertFavourite @QuoteID =   78, @UserID =    6;
EXEC insertFavourite @QuoteID =   25, @UserID =    7;
EXEC insertFavourite @QuoteID =  162, @UserID =    7;
EXEC insertFavourite @QuoteID =  154, @UserID =    7;
EXEC insertFavourite @QuoteID =   92, @UserID =    7;
EXEC insertFavourite @QuoteID =  130, @UserID =    7;
EXEC insertFavourite @QuoteID =   48, @UserID =    7;
EXEC insertFavourite @QuoteID =   69, @UserID =    7;
EXEC insertFavourite @QuoteID =    3, @UserID =    8;
EXEC insertFavourite @QuoteID =   13, @UserID =    8;
EXEC insertFavourite @QuoteID =  175, @UserID =    8;
EXEC insertFavourite @QuoteID =  108, @UserID =    9;
EXEC insertFavourite @QuoteID =   86, @UserID =    9;
EXEC insertFavourite @QuoteID =   23, @UserID =    9;
EXEC insertFavourite @QuoteID =   60, @UserID =    9;
EXEC insertFavourite @QuoteID =  106, @UserID =    9;
EXEC insertFavourite @QuoteID =  137, @UserID =    9;
EXEC insertFavourite @QuoteID =  102, @UserID =    9;
EXEC insertFavourite @QuoteID =   10, @UserID =    9;
EXEC insertFavourite @QuoteID =  162, @UserID =   10;
EXEC insertFavourite @QuoteID =  180, @UserID =   10;
EXEC insertFavourite @QuoteID =   47, @UserID =   10;
EXEC insertFavourite @QuoteID =  118, @UserID =   12;
EXEC insertFavourite @QuoteID =   69, @UserID =   12;
EXEC insertFavourite @QuoteID =   63, @UserID =   12;
EXEC insertFavourite @QuoteID =   24, @UserID =   12;
EXEC insertFavourite @QuoteID =  178, @UserID =   12;
EXEC insertFavourite @QuoteID =  110, @UserID =   12;
EXEC insertFavourite @QuoteID =   84, @UserID =   13;
EXEC insertFavourite @QuoteID =  138, @UserID =   14;
EXEC insertFavourite @QuoteID =   84, @UserID =   14;
EXEC insertFavourite @QuoteID =    9, @UserID =   14;
EXEC insertFavourite @QuoteID =   57, @UserID =   14;
EXEC insertFavourite @QuoteID =  186, @UserID =   14;
EXEC insertFavourite @QuoteID =  165, @UserID =   14;
EXEC insertFavourite @QuoteID =  188, @UserID =   14;
EXEC insertFavourite @QuoteID =  186, @UserID =   15;
EXEC insertFavourite @QuoteID =   16, @UserID =   15;
EXEC insertFavourite @QuoteID =  124, @UserID =   15;
EXEC insertFavourite @QuoteID =   54, @UserID =   15;
EXEC insertFavourite @QuoteID =  106, @UserID =   15;
EXEC insertFavourite @QuoteID =   55, @UserID =   15;
EXEC insertFavourite @QuoteID =   25, @UserID =   16;
EXEC insertFavourite @QuoteID =  111, @UserID =   16;
EXEC insertFavourite @QuoteID =   47, @UserID =   16;
EXEC insertFavourite @QuoteID =   23, @UserID =   17;
EXEC insertFavourite @QuoteID =   69, @UserID =   17;
EXEC insertFavourite @QuoteID =  147, @UserID =   17;
EXEC insertFavourite @QuoteID =   97, @UserID =   17;
EXEC insertFavourite @QuoteID =  145, @UserID =   17;
EXEC insertFavourite @QuoteID =   13, @UserID =   17;
EXEC insertFavourite @QuoteID =  130, @UserID =   17;
EXEC insertFavourite @QuoteID =   63, @UserID =   17;
EXEC insertFavourite @QuoteID =  101, @UserID =   18;
EXEC insertFavourite @QuoteID =  124, @UserID =   18;
EXEC insertFavourite @QuoteID =   22, @UserID =   18;
EXEC insertFavourite @QuoteID =  100, @UserID =   18;
EXEC insertFavourite @QuoteID =   92, @UserID =   18;
EXEC insertFavourite @QuoteID =  139, @UserID =   18;
EXEC insertFavourite @QuoteID =   38, @UserID =   18;
EXEC insertFavourite @QuoteID =   25, @UserID =   18;
EXEC insertFavourite @QuoteID =  180, @UserID =   18;

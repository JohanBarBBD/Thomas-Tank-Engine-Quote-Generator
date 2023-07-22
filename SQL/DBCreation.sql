USE master;
GO

DROP DATABASE IF EXISTS TTTEADB;

CREATE DATABASE TTTEADB;
GO

USE TTTEADB;
GO

CREATE TABLE [character] (
	character_id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
	character_name VARCHAR(100)
);
GO

CREATE TABLE [user] (
	[user_id] INT NOT NULL PRIMARY KEY IDENTITY(1,1) ,
	user_email VARCHAR(250) UNIQUE NOT NULL,
	[user_name] VARCHAR(100) NOT NULL
);
GO

CREATE TABLE [quote] (
	quote_id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
	quote_text VARCHAR(400) NOT NULL,
	quote_episode INT NOT NULL,
	quote_season INT NOT NULL,
	character_id INT NOT NULL FOREIGN KEY REFERENCES [character](character_id) ON DELETE CASCADE
);
GO

CREATE TABLE [favoritequote] (
	quote_id INT NOT NULL,
	[user_id] INT NOT NULL,
	date_favorited DATE NOT NULL,
	CONSTRAINT FQ_PK PRIMARY KEY (quote_id,[user_id]),
	CONSTRAINT [QT_FK] FOREIGN KEY (quote_id) REFERENCES [quote](quote_id) ON DELETE CASCADE,
	CONSTRAINT [UT_FK] FOREIGN KEY ([user_id]) REFERENCES [user]([user_id]) ON DELETE CASCADE,
);
GO

CREATE PROCEDURE [createcharacter]
	@CharacterName VARCHAR(100)
AS
	INSERT INTO [character] (character_name) VALUES (@CharacterName);
GO

CREATE PROCEDURE [createuser]
	@UserEmail VARCHAR(250) ,
	@UserName VARCHAR(100)
AS
	INSERT INTO [user] (user_email,[user_name]) VALUES (@UserEmail,@UserName );
GO
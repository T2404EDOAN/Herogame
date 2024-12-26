CREATE DATABASE HeroGame;
USE HeroGame;

CREATE TABLE National (
    NationalId INT PRIMARY KEY AUTO_INCREMENT,
    NationalName VARCHAR(100) NOT NULL
);

CREATE TABLE Player (
    PlayerId INT PRIMARY KEY AUTO_INCREMENT,
    NationalId INT,
    PlayerName VARCHAR(100) NOT NULL,
    HighScore INT DEFAULT 0,
    Level INT DEFAULT 1,
    FOREIGN KEY (NationalId) REFERENCES National(NationalId)
);

-- Insert some sample data
INSERT INTO National (NationalName) VALUES 
('Vietnam'),
('USA'),
('Japan');

INSERT INTO Player (PlayerName, NationalId, HighScore, Level) VALUES 
('Player 1', 1, 100, 2),
('Player 2', 2, 1050, 10),
('Player 3', 3, 200, 5);
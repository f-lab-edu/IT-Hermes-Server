DROP TABLE IF EXISTS `CrawlingContentsLastUrl`;
DROP TABLE IF EXISTS `Job`;
DROP TABLE IF EXISTS `Keyword`;
DROP TABLE IF EXISTS `Subscribe`;
DROP TABLE IF EXISTS `UrlRecord`;
DROP TABLE IF EXISTS `User`;
DROP TABLE IF EXISTS `UserKeywordRegistry`;
DROP TABLE IF EXISTS `YoutubeAndNews`;

CREATE TABLE CrawlingContentsLastUrl (
                       `id` bigint NOT NULL AUTO_INCREMENT,
                       `contentsProvider` varchar(255) NOT NULL,
                       `grade` varchar(255) DEFAULT NULL,
                       `job` varchar(255) DEFAULT NULL,
                       `lastUrl` varchar(1000) NOT NULL,
                       PRIMARY KEY (`id`)
);

CREATE TABLE Job (
                       id bigint NOT NULL AUTO_INCREMENT,
                       createdAt datetime(6) DEFAULT NULL,
                       updatedAt datetime(6) DEFAULT NULL,
                       company varchar(255) NOT NULL,
                       contentsEndAt datetime(6) NOT NULL,
                       contentsProvider varchar(255) NOT NULL,
                       contentsStartAt datetime(6) NOT NULL,
                       grade varchar(255) NOT NULL,
                       isDelete bit(1) NOT NULL,
                       location varchar(255) NOT NULL,
                       title varchar(255) NOT NULL,
                       url varchar(255) NOT NULL,
                       viewCount bigint NOT NULL,
                       PRIMARY KEY (id)
);

CREATE TABLE Keyword (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `createdAt` datetime(6) DEFAULT NULL,
                           `updatedAt` datetime(6) DEFAULT NULL,
                           `name` varchar(255) NOT NULL,
                           PRIMARY KEY (`id`)
);

CREATE TABLE User (
                      `id` bigint NOT NULL AUTO_INCREMENT,
                      `createdAt` datetime(6) DEFAULT NULL,
                      `updatedAt` datetime(6) DEFAULT NULL,
                      `isDelete` bit(1) NOT NULL,
                      `job` varchar(255) NOT NULL,
                      `loginId` varchar(255) NOT NULL,
                      `nickname` varchar(255) NOT NULL,
                      `password` varchar(255) NOT NULL,
                      `refreshToken` varchar(255) DEFAULT NULL,
                      `telegramId` varchar(255) DEFAULT NULL,
                      `yearOfExperience` int NOT NULL,
                      PRIMARY KEY (`id`)
);

CREATE TABLE Subscribe (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `createdAt` datetime(6) DEFAULT NULL,
                             `updatedAt` datetime(6) DEFAULT NULL,
                             `alarmLastUrl` varchar(255) DEFAULT NULL,
                             `category` varchar(255) NOT NULL,
                             `contentsProvider` varchar(255) NOT NULL,
                             `isActive` varchar(255) NOT NULL,
                             `userId` bigint DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             CONSTRAINT  FOREIGN KEY (`userId`) REFERENCES User (`id`)
);

CREATE TABLE UrlRecord (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `createdAt` datetime(6) DEFAULT NULL,
                             `updatedAt` datetime(6) DEFAULT NULL,
                             `clientIpAddress` varchar(255) NOT NULL,
                             `url` varchar(255) NOT NULL,
                             PRIMARY KEY (`id`)
);

CREATE TABLE UserKeywordRegistry (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `createdAt` datetime(6) DEFAULT NULL,
                           `updatedAt` datetime(6) DEFAULT NULL,
                           `keywordId` bigint DEFAULT NULL,
                           `userId` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           CONSTRAINT FOREIGN KEY (`keywordId`) REFERENCES Keyword (`id`),
                           CONSTRAINT FOREIGN KEY (`userId`) REFERENCES User (`id`)
);

CREATE TABLE YoutubeAndNews (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `createdAt` datetime(6) DEFAULT NULL,
                          `updatedAt` datetime(6) DEFAULT NULL,
                          `category` varchar(255) NOT NULL,
                          `contentsProvider` varchar(255) NOT NULL,
                          `contentsStartAt` datetime(6) NOT NULL,
                          `description` varchar(1000) NOT NULL,
                          `image` varchar(255) DEFAULT NULL,
                          `isDelete` bit(1) NOT NULL,
                          `title` varchar(255) NOT NULL,
                          `url` varchar(255) NOT NULL,
                          `viewCount` bigint NOT NULL,
                          PRIMARY KEY (`id`)
);
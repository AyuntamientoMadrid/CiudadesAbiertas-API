-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 27-02-2020 a las 12:51:45
-- Versión del servidor: 5.7.28-0ubuntu0.16.04.2
-- Versión de PHP: 7.0.33-0ubuntu0.16.04.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `baseMadrid`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `DATABASECHANGELOG`
--

CREATE TABLE `DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `DATABASECHANGELOG`
--

INSERT INTO `DATABASECHANGELOG` (`ID`, `AUTHOR`, `FILENAME`, `DATEEXECUTED`, `ORDEREXECUTED`, `EXECTYPE`, `MD5SUM`, `DESCRIPTION`, `COMMENTS`, `TAG`, `LIQUIBASE`, `CONTEXTS`, `LABELS`) VALUES
('empty-ciudades-abiertas', 'Localidata', 'liquibase/db-changelog-script-0.0-EMPTY.xml', '2020-02-27 12:51:21', 1, 'EXECUTED', '7:bbfce268af0d2d9b0cd2a79fb056cd15', 'tagDatabase', '', '0.0', '3.4.2', NULL, NULL),
('table-estadistica', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:21', 2, 'EXECUTED', '7:c6a7f0cec14432c3580ddb06f076665d', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('table-oauth-access-token', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:21', 3, 'EXECUTED', '7:5b193f221ba44f8eb280b29fd59d4bd9', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('table-oauth-approvals', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:21', 4, 'EXECUTED', '7:62688a22372dd8f7c8eb59d9b73e5367', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('table-oauth-client-details', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 5, 'EXECUTED', '7:9c97e2436866044a413f80a52588f6ec', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('table-oauth-client-token', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 6, 'EXECUTED', '7:9f9984fff938a4d5116ef6eceef4b01a', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('table-oauth-code', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 7, 'EXECUTED', '7:4c9bea4afc85cd184227f460427a2a36', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('table-oauth-refresh-token', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 8, 'EXECUTED', '7:e3ff3683926345e15d014b7a28cc0854', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('table-user-roles', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 9, 'EXECUTED', '7:f97726314258c37899248c395df39665', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('table-users', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 10, 'EXECUTED', '7:e652fd1e9b69e8738bf20613721ffd39', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('table-group-authorities', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 11, 'EXECUTED', '7:c9f4157fb185eb137532c79cc89c175d', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('table-group-members', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 12, 'EXECUTED', '7:b116abd678e2679558ff6031f31802de', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('table-groups', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 13, 'EXECUTED', '7:a1332aa076e0b118d770d2bae9b42838', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('entidadBase', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 14, 'EXECUTED', '7:75f9711a6dc621c6635f22013af7334a', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('PK-table-base', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 15, 'EXECUTED', '7:b814949a382eba3f205c37cf4c15a835', 'addPrimaryKey', '', NULL, '3.4.2', NULL, NULL),
('PK-table-estadistica', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 16, 'EXECUTED', '7:cf7262df414fc451d07c883ea8427f7d', 'addPrimaryKey', '', NULL, '3.4.2', NULL, NULL),
('PK-table-oauth-access-token', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 17, 'EXECUTED', '7:991ead7eb536d8f74a80d5b90d218f04', 'addPrimaryKey', '', NULL, '3.4.2', NULL, NULL),
('PK-table-oauth-client-details', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 18, 'EXECUTED', '7:886facdc9619f43493dd0a19e244603c', 'addPrimaryKey', '', NULL, '3.4.2', NULL, NULL),
('PK-table-oauth-client-token', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 19, 'EXECUTED', '7:52ec1ab295b5e32febc60b64be33e9c3', 'addPrimaryKey', '', NULL, '3.4.2', NULL, NULL),
('PK-table-user-roles', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:22', 20, 'EXECUTED', '7:7332d9c46f9bb3dbeeee064f3ccfd49c', 'addPrimaryKey', '', NULL, '3.4.2', NULL, NULL),
('PK-table-users', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:23', 21, 'EXECUTED', '7:01de5d6fb10427b23ef21c9989e6d46b', 'addPrimaryKey', '', NULL, '3.4.2', NULL, NULL),
('Unique-field-user_roles', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:23', 22, 'EXECUTED', '7:96f86e27cdb2422b854506bb1fa4c84a', 'addUniqueConstraint', '', NULL, '3.4.2', NULL, NULL),
('Indice-username-table-user_roles', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-table.xml', '2020-02-27 12:51:23', 23, 'EXECUTED', '7:fbac76edc3b9fbfbe0d64e954e5335e6', 'createIndex', '', NULL, '3.4.2', NULL, NULL),
('data-user-roles', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-data.xml', '2020-02-27 12:51:23', 24, 'EXECUTED', '7:ac1092dcc0f09000fcf50228877d4e5a', 'insert (x3)', '', NULL, '3.4.2', NULL, NULL),
('data-users', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-data.xml', '2020-02-27 12:51:23', 25, 'EXECUTED', '7:510f8602fefea9b6edb555dafb672949', 'insert (x2)', '', NULL, '3.4.2', NULL, NULL),
('data-groups', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-data.xml', '2020-02-27 12:51:23', 26, 'EXECUTED', '7:7ca7bbea357ee7c45482cc9354d94296', 'insert', '', NULL, '3.4.2', NULL, NULL),
('data-group-authorities', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-data.xml', '2020-02-27 12:51:23', 27, 'EXECUTED', '7:3f8747c4c08ac9ea4ed9ccc39fc62093', 'insert', '', NULL, '3.4.2', NULL, NULL),
('data-group-members', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-data.xml', '2020-02-27 12:51:23', 28, 'EXECUTED', '7:c641227f106de85fffe90f7a50316206', 'insert', '', NULL, '3.4.2', NULL, NULL),
('PK-table-group-authorities', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-data.xml', '2020-02-27 12:51:23', 29, 'EXECUTED', '7:e1e0ad7e580044e3f6f7629dbee76df0', 'addPrimaryKey', '', NULL, '3.4.2', NULL, NULL),
('PK-table-group-members', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-data.xml', '2020-02-27 12:51:23', 30, 'EXECUTED', '7:565c0dfcc407b9e477d41bc6ffd558af', 'addPrimaryKey', '', NULL, '3.4.2', NULL, NULL),
('PK-table-groups', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-data.xml', '2020-02-27 12:51:23', 31, 'EXECUTED', '7:d17293c9f0aacd1f652b0d5b64ec81d1', 'addPrimaryKey', '', NULL, '3.4.2', NULL, NULL),
('FK-USER-ROLE-USERNAME', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-data.xml', '2020-02-27 12:51:23', 32, 'EXECUTED', '7:76bd6afc490b2fed2aff27036df3791b', 'addForeignKeyConstraint', '', NULL, '3.4.2', NULL, NULL),
('FK-GROUP-MEMBERS-USERS', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-data.xml', '2020-02-27 12:51:23', 33, 'EXECUTED', '7:34873c68b5f685c58167c1cd4650afaf', 'addForeignKeyConstraint', '', NULL, '3.4.2', NULL, NULL),
('FK-GROUP-MEMBERS-GROUP', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-data.xml', '2020-02-27 12:51:23', 34, 'EXECUTED', '7:8dcbba0717c053e81702277e406a4c9d', 'addForeignKeyConstraint', '', NULL, '3.4.2', NULL, NULL),
('FK-GROUP-AUTHORI-GROUP', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-data.xml', '2020-02-27 12:51:23', 35, 'EXECUTED', '7:553a44914ab92b994237f8e1b522c9da', 'addForeignKeyConstraint', '', NULL, '3.4.2', NULL, NULL),
('DATA-CORE-tag-1.0', 'Localidata', 'liquibase/db-changelog-script-1.0-CORE-data.xml', '2020-02-27 12:51:23', 36, 'EXECUTED', '7:5560655641c5ef77bca6b90c5b234724', 'tagDatabase', '', '1.0', '3.4.2', NULL, NULL),
('table-query', 'Localidata', 'liquibase/db-changelog-script-2.0-DYNAMIC_SQL-table.xml', '2020-02-27 12:51:23', 37, 'EXECUTED', '7:9306cef60bb7a9f607fb42b0dc764bb6', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('table-param', 'Localidata', 'liquibase/db-changelog-script-2.0-DYNAMIC_SQL-table.xml', '2020-02-27 12:51:23', 38, 'EXECUTED', '7:399edc8fb4adf2c2f6f9323343e14e9a', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('PK-table-query', 'Localidata', 'liquibase/db-changelog-script-2.0-DYNAMIC_SQL-table.xml', '2020-02-27 12:51:24', 39, 'EXECUTED', '7:a5a26e86150267669c42019d515dbd20', 'addPrimaryKey', '', NULL, '3.4.2', NULL, NULL),
('PK-table-param', 'Localidata', 'liquibase/db-changelog-script-2.0-DYNAMIC_SQL-table.xml', '2020-02-27 12:51:24', 40, 'EXECUTED', '7:4491b7be30662cc062b2e313564f0eaa', 'addPrimaryKey', '', NULL, '3.4.2', NULL, NULL),
('Index-code-param', 'Localidata', 'liquibase/db-changelog-script-2.0-DYNAMIC_SQL-table.xml', '2020-02-27 12:51:24', 41, 'EXECUTED', '7:71324acfced822683c74215ecb48bfad', 'createIndex', '', NULL, '3.4.2', NULL, NULL),
('FK-param-query', 'Localidata', 'liquibase/db-changelog-script-2.0-DYNAMIC_SQL-table.xml', '2020-02-27 12:51:24', 42, 'EXECUTED', '7:46c4b7abea340f35a139a595b6d32aae', 'addForeignKeyConstraint', '', NULL, '3.4.2', NULL, NULL),
('DATA-CORE-tag-2.0', 'Localidata', 'liquibase/db-changelog-script-2.0-DYNAMIC_SQL-table.xml', '2020-02-27 12:51:24', 43, 'EXECUTED', '7:aea2a994f82fdf3ace7794cc62e3a10f', 'tagDatabase', '', '2.0', '3.4.2', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `DATABASECHANGELOGLOCK`
--

CREATE TABLE `DATABASECHANGELOGLOCK` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `DATABASECHANGELOGLOCK`
--

INSERT INTO `DATABASECHANGELOGLOCK` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
(1, b'0', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entidad_base`
--

CREATE TABLE `entidad_base` (
  `id` varchar(50) NOT NULL,
  `texto` varchar(100) NOT NULL,
  `texto_largo` varchar(4000) DEFAULT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `numero_entero` int(11) DEFAULT NULL,
  `numero_decimal` decimal(12,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estadistica`
--

CREATE TABLE `estadistica` (
  `id` varchar(50) NOT NULL,
  `url` varchar(2000) NOT NULL,
  `fecha_peticion` datetime NOT NULL,
  `origen` varchar(20) NOT NULL,
  `user_agent` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `groups`
--

CREATE TABLE `groups` (
  `id` bigint(20) NOT NULL,
  `group_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `groups`
--

INSERT INTO `groups` (`id`, `group_name`) VALUES
(1, 'Grupo Ciudades Abiertas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `group_authorities`
--

CREATE TABLE `group_authorities` (
  `group_id` bigint(20) NOT NULL,
  `authority` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `group_authorities`
--

INSERT INTO `group_authorities` (`group_id`, `authority`) VALUES
(1, 'consulta');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `group_members`
--

CREATE TABLE `group_members` (
  `id` bigint(20) NOT NULL,
  `username` varchar(45) NOT NULL,
  `group_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `group_members`
--

INSERT INTO `group_members` (`id`, `username`, `group_id`) VALUES
(1, 'localidata', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `oauth_access_token`
--

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` mediumblob,
  `authentication_id` varchar(255) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `authentication` mediumblob,
  `refresh_token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `oauth_approvals`
--

CREATE TABLE `oauth_approvals` (
  `userId` varchar(255) DEFAULT NULL,
  `clientId` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModifiedAt` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `oauth_client_details`
--

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(2000) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `oauth_client_token`
--

CREATE TABLE `oauth_client_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` mediumblob,
  `authentication_id` varchar(255) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `oauth_code`
--

CREATE TABLE `oauth_code` (
  `code` varchar(255) DEFAULT NULL,
  `authentication` mediumblob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `oauth_refresh_token`
--

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` mediumblob,
  `authentication` mediumblob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `param`
--

CREATE TABLE `param` (
  `id` int(11) NOT NULL,
  `query_code` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `type` varchar(10) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `example` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `query`
--

CREATE TABLE `query` (
  `code` varchar(20) NOT NULL,
  `texto` varchar(4000) NOT NULL,
  `database_con` varchar(20) NOT NULL,
  `summary` varchar(100) DEFAULT NULL,
  `tags` varchar(255) NOT NULL,
  `definition` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `username` varchar(45) NOT NULL,
  `pass` varchar(60) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`username`, `pass`, `enabled`) VALUES
('admin', '$2a$10$8YBOCvb.UC0Lk40f2Gd2Uugxmk7QPeGSnT1oDRjxzuRrAVYNO62ke', 1),
('localidata', '$2a$10$8YBOCvb.UC0Lk40f2Gd2Uugxmk7QPeGSnT1oDRjxzuRrAVYNO62ke', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_roles`
--

CREATE TABLE `user_roles` (
  `user_role_id` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `user_roles`
--

INSERT INTO `user_roles` (`user_role_id`, `username`, `role`) VALUES
(2, 'admin', 'ROLE_ADMIN'),
(1, 'admin', 'ROLE_USER'),
(3, 'localidata', 'ROLE_USER');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `DATABASECHANGELOGLOCK`
--
ALTER TABLE `DATABASECHANGELOGLOCK`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `entidad_base`
--
ALTER TABLE `entidad_base`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `estadistica`
--
ALTER TABLE `estadistica`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `group_authorities`
--
ALTER TABLE `group_authorities`
  ADD PRIMARY KEY (`group_id`);

--
-- Indices de la tabla `group_members`
--
ALTER TABLE `group_members`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_group_memebers_users` (`username`),
  ADD KEY `fk_group_memebers_groups` (`group_id`);

--
-- Indices de la tabla `oauth_access_token`
--
ALTER TABLE `oauth_access_token`
  ADD PRIMARY KEY (`authentication_id`);

--
-- Indices de la tabla `oauth_client_details`
--
ALTER TABLE `oauth_client_details`
  ADD PRIMARY KEY (`client_id`);

--
-- Indices de la tabla `oauth_client_token`
--
ALTER TABLE `oauth_client_token`
  ADD PRIMARY KEY (`authentication_id`);

--
-- Indices de la tabla `param`
--
ALTER TABLE `param`
  ADD PRIMARY KEY (`id`),
  ADD KEY `code` (`query_code`);

--
-- Indices de la tabla `query`
--
ALTER TABLE `query`
  ADD PRIMARY KEY (`code`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);

--
-- Indices de la tabla `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_role_id`),
  ADD UNIQUE KEY `uni_username_role` (`role`,`username`),
  ADD KEY `index_username_idx` (`username`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `group_authorities`
--
ALTER TABLE `group_authorities`
  ADD CONSTRAINT `fk_group_authori_users` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`);

--
-- Filtros para la tabla `group_members`
--
ALTER TABLE `group_members`
  ADD CONSTRAINT `fk_group_memebers_groups` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  ADD CONSTRAINT `fk_group_memebers_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`);

--
-- Filtros para la tabla `param`
--
ALTER TABLE `param`
  ADD CONSTRAINT `param_ibfk_1` FOREIGN KEY (`query_code`) REFERENCES `query` (`code`);

--
-- Filtros para la tabla `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `fk_user_role_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

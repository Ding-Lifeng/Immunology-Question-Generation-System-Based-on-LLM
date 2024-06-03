-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- 主机： db
-- 生成日期： 2023-10-28 23:57:35
-- 服务器版本： 8.0.27
-- PHP 版本： 7.4.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `ssadmin`
--
-- 创建新数据库 ssadmin 并选择
CREATE DATABASE IF NOT EXISTS `ssadmin` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `ssadmin`;

-- --------------------------------------------------------

--
-- 表的结构 `system_users`
--

CREATE TABLE `system_users` (
  `id` bigint NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '0',
  `login_ip` varchar(255) DEFAULT NULL,
  `login_date` datetime DEFAULT NULL,
  `tenant_id` int DEFAULT '1',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creator` varchar(255) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 转存表中的数据 `system_users`
--

INSERT INTO `system_users` (`id`, `username`, `password`, `nickname`, `status`, `login_ip`, `login_date`, `tenant_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`) VALUES
(1, 'sysadmin', '$2a$10$2n91ncs5vNaKEwcuk9dFVuwpm8o3kpj17YW0Ne.6.e.AF.m0MuBPa', 'admin', 0, NULL, '2023-10-28 10:57:02', 1, '2023-10-28 10:57:30', '2023-10-28 10:57:30', NULL, NULL, 0),
(2, 'user', '$2a$10$2n91ncs5vNaKEwcuk9dFVuwpm8o3kpj17YW0Ne.6.e.AF.m0MuBPa', 'admin', 0, NULL, '2023-10-28 10:57:02', 1, '2023-10-28 10:57:30', '2023-10-28 10:57:30', NULL, NULL, 0);

--
-- 转储表的索引
--

--
-- 表的索引 `system_users`
--
ALTER TABLE `system_users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `system_users`
--
ALTER TABLE `system_users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

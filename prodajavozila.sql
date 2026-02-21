-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 09, 2026 at 12:00 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `apoteka`
--
CREATE DATABASE IF NOT EXISTS `apoteka` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `apoteka`;

-- --------------------------------------------------------

--
-- Table structure for table `leknarudzbina`
--

CREATE TABLE `leknarudzbina` (
  `id` int(11) NOT NULL,
  `Id_leka` int(15) NOT NULL,
  `Id_narudzbine` int(11) NOT NULL,
  `kolicina` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `leknarudzbina`
--

INSERT INTO `leknarudzbina` (`id`, `Id_leka`, `Id_narudzbine`, `kolicina`) VALUES
(4, 1, 2, 20),
(5, 3, 2, 18),
(6, 4, 2, 27),
(7, 1, 3, 50),
(8, 2, 3, 60),
(9, 3, 3, 70),
(22, 1, 4, 200),
(23, 2, 4, 250),
(24, 3, 4, 350),
(25, 4, 5, 90),
(34, 4, 6, 90),
(35, 5, 1, 50);

-- --------------------------------------------------------

--
-- Table structure for table `lekovi`
--

CREATE TABLE `lekovi` (
  `id` int(15) NOT NULL,
  `naziv` varchar(50) NOT NULL,
  `tip` varchar(50) NOT NULL,
  `proizvodjac` varchar(255) NOT NULL,
  `rok_trajanja` datetime(6) DEFAULT NULL,
  `cena` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lekovi`
--

INSERT INTO `lekovi` (`id`, `naziv`, `tip`, `proizvodjac`, `rok_trajanja`, `cena`) VALUES
(1, 'Lek1', 'Tip1', 'Pera', '2024-01-31 00:00:00.000000', 0),
(2, 'Lek2', 'Tip1', 'Mika', '2024-01-31 00:00:00.000000', 0),
(3, 'Lek3', 'Tip1', 'Mika', '2024-01-31 00:00:00.000000', 0),
(4, 'Lek4', 'Tip2', 'Pera', '2024-01-31 00:00:00.000000', 0),
(5, 'Lek22', 'Tip1', 'Proiz1', '2012-04-03 02:00:00.000000', 25);

-- --------------------------------------------------------

--
-- Table structure for table `lekprodaja`
--

CREATE TABLE `lekprodaja` (
  `id` int(11) NOT NULL,
  `id_leka` int(11) NOT NULL,
  `id_prodaje` int(11) NOT NULL,
  `kolicina` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lekprodaja`
--

INSERT INTO `lekprodaja` (`id`, `id_leka`, `id_prodaje`, `kolicina`) VALUES
(4, 1, 1, 2),
(5, 2, 1, 5),
(6, 3, 1, 15);

-- --------------------------------------------------------

--
-- Table structure for table `narudzbenica`
--

CREATE TABLE `narudzbenica` (
  `id` int(11) NOT NULL,
  `datum` datetime DEFAULT NULL,
  `status_isporuke` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `narudzbenica`
--

INSERT INTO `narudzbenica` (`id`, `datum`, `status_isporuke`) VALUES
(1, '2019-02-13 01:00:00', 'Izmenjeno'),
(2, '2024-01-08 17:11:12', 'Isporuceno'),
(3, '2012-04-03 02:00:00', 'Kreirano'),
(4, '2018-01-20 01:00:00', 'Azurirano'),
(5, '2023-01-12 01:00:00', 'Dodato'),
(6, '2023-01-12 01:00:00', 'Dodato');

-- --------------------------------------------------------

--
-- Table structure for table `prodaja`
--

CREATE TABLE `prodaja` (
  `id` int(3) NOT NULL,
  `datum` datetime DEFAULT NULL,
  `ukupna_cena` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `prodaja`
--

INSERT INTO `prodaja` (`id`, `datum`, `ukupna_cena`) VALUES
(1, '2024-01-01 18:26:00', 25000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `leknarudzbina`
--
ALTER TABLE `leknarudzbina`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_narudzbina` (`Id_narudzbine`),
  ADD KEY `fk_lek1` (`Id_leka`);

--
-- Indexes for table `lekovi`
--
ALTER TABLE `lekovi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `lekprodaja`
--
ALTER TABLE `lekprodaja`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_lek2` (`id_leka`),
  ADD KEY `fk_prodaje` (`id_prodaje`);

--
-- Indexes for table `narudzbenica`
--
ALTER TABLE `narudzbenica`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `prodaja`
--
ALTER TABLE `prodaja`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `leknarudzbina`
--
ALTER TABLE `leknarudzbina`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `lekovi`
--
ALTER TABLE `lekovi`
  MODIFY `id` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `lekprodaja`
--
ALTER TABLE `lekprodaja`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `narudzbenica`
--
ALTER TABLE `narudzbenica`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `prodaja`
--
ALTER TABLE `prodaja`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `leknarudzbina`
--
ALTER TABLE `leknarudzbina`
  ADD CONSTRAINT `fk_lek1` FOREIGN KEY (`Id_leka`) REFERENCES `lekovi` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_narudzbina` FOREIGN KEY (`Id_narudzbine`) REFERENCES `narudzbenica` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `lekprodaja`
--
ALTER TABLE `lekprodaja`
  ADD CONSTRAINT `fk_lek2` FOREIGN KEY (`id_leka`) REFERENCES `lekovi` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_prodaje` FOREIGN KEY (`id_prodaje`) REFERENCES `prodaja` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;
--
-- Database: `biblioteka`
--
CREATE DATABASE IF NOT EXISTS `biblioteka` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `biblioteka`;
--
-- Database: `crud`
--
CREATE DATABASE IF NOT EXISTS `crud` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `crud`;

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE `test` (
  `id` int(100) NOT NULL,
  `ime` varchar(100) NOT NULL,
  `prezime` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `test`
--

INSERT INTO `test` (`id`, `ime`, `prezime`, `email`, `password`) VALUES
(6, 'Pera', 'Peric', 'pera@gmail.com', '$2y$10$B0ftX/lqgL8nYHyJNg8oIuXr32yR8Pxr24LEGolq1gXO1lfmvwEQS');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `test`
--
ALTER TABLE `test`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- Database: `ispit`
--
CREATE DATABASE IF NOT EXISTS `ispit` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `ispit`;

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `id` int(100) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`id`, `title`, `description`) VALUES
(5, 'a', 'b');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- Database: `phpmyadmin`
--
CREATE DATABASE IF NOT EXISTS `phpmyadmin` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `phpmyadmin`;

-- --------------------------------------------------------

--
-- Table structure for table `pma__bookmark`
--

CREATE TABLE `pma__bookmark` (
  `id` int(10) UNSIGNED NOT NULL,
  `dbase` varchar(255) NOT NULL DEFAULT '',
  `user` varchar(255) NOT NULL DEFAULT '',
  `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `query` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- --------------------------------------------------------

--
-- Table structure for table `pma__central_columns`
--

CREATE TABLE `pma__central_columns` (
  `db_name` varchar(64) NOT NULL,
  `col_name` varchar(64) NOT NULL,
  `col_type` varchar(64) NOT NULL,
  `col_length` text DEFAULT NULL,
  `col_collation` varchar(64) NOT NULL,
  `col_isNull` tinyint(1) NOT NULL,
  `col_extra` varchar(255) DEFAULT '',
  `col_default` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Central list of columns';

-- --------------------------------------------------------

--
-- Table structure for table `pma__column_info`
--

CREATE TABLE `pma__column_info` (
  `id` int(5) UNSIGNED NOT NULL,
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `table_name` varchar(64) NOT NULL DEFAULT '',
  `column_name` varchar(64) NOT NULL DEFAULT '',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `mimetype` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `transformation` varchar(255) NOT NULL DEFAULT '',
  `transformation_options` varchar(255) NOT NULL DEFAULT '',
  `input_transformation` varchar(255) NOT NULL DEFAULT '',
  `input_transformation_options` varchar(255) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Column information for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__designer_settings`
--

CREATE TABLE `pma__designer_settings` (
  `username` varchar(64) NOT NULL,
  `settings_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Settings related to Designer';

-- --------------------------------------------------------

--
-- Table structure for table `pma__export_templates`
--

CREATE TABLE `pma__export_templates` (
  `id` int(5) UNSIGNED NOT NULL,
  `username` varchar(64) NOT NULL,
  `export_type` varchar(10) NOT NULL,
  `template_name` varchar(64) NOT NULL,
  `template_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Saved export templates';

-- --------------------------------------------------------

--
-- Table structure for table `pma__favorite`
--

CREATE TABLE `pma__favorite` (
  `username` varchar(64) NOT NULL,
  `tables` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Favorite tables';

-- --------------------------------------------------------

--
-- Table structure for table `pma__history`
--

CREATE TABLE `pma__history` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `username` varchar(64) NOT NULL DEFAULT '',
  `db` varchar(64) NOT NULL DEFAULT '',
  `table` varchar(64) NOT NULL DEFAULT '',
  `timevalue` timestamp NOT NULL DEFAULT current_timestamp(),
  `sqlquery` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='SQL history for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__navigationhiding`
--

CREATE TABLE `pma__navigationhiding` (
  `username` varchar(64) NOT NULL,
  `item_name` varchar(64) NOT NULL,
  `item_type` varchar(64) NOT NULL,
  `db_name` varchar(64) NOT NULL,
  `table_name` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Hidden items of navigation tree';

-- --------------------------------------------------------

--
-- Table structure for table `pma__pdf_pages`
--

CREATE TABLE `pma__pdf_pages` (
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `page_nr` int(10) UNSIGNED NOT NULL,
  `page_descr` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='PDF relation pages for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__recent`
--

CREATE TABLE `pma__recent` (
  `username` varchar(64) NOT NULL,
  `tables` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Recently accessed tables';

--
-- Dumping data for table `pma__recent`
--

INSERT INTO `pma__recent` (`username`, `tables`) VALUES
('root', '[{\"db\":\"prodajavozila\",\"table\":\"vehicle_images\"},{\"db\":\"prodajavozila\",\"table\":\"vehicles\"},{\"db\":\"prodajavozila\",\"table\":\"categories\"},{\"db\":\"prodajavozila\",\"table\":\"conversations\"},{\"db\":\"prodajavozila\",\"table\":\"favorites\"},{\"db\":\"prodajavozila\",\"table\":\"messages\"},{\"db\":\"prodajavozila\",\"table\":\"notifications\"},{\"db\":\"prodajavozila\",\"table\":\"offers\"},{\"db\":\"prodajavozila\",\"table\":\"reservations\"},{\"db\":\"prodajavozila\",\"table\":\"roles\"}]');

-- --------------------------------------------------------

--
-- Table structure for table `pma__relation`
--

CREATE TABLE `pma__relation` (
  `master_db` varchar(64) NOT NULL DEFAULT '',
  `master_table` varchar(64) NOT NULL DEFAULT '',
  `master_field` varchar(64) NOT NULL DEFAULT '',
  `foreign_db` varchar(64) NOT NULL DEFAULT '',
  `foreign_table` varchar(64) NOT NULL DEFAULT '',
  `foreign_field` varchar(64) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Relation table';

-- --------------------------------------------------------

--
-- Table structure for table `pma__savedsearches`
--

CREATE TABLE `pma__savedsearches` (
  `id` int(5) UNSIGNED NOT NULL,
  `username` varchar(64) NOT NULL DEFAULT '',
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `search_name` varchar(64) NOT NULL DEFAULT '',
  `search_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Saved searches';

-- --------------------------------------------------------

--
-- Table structure for table `pma__table_coords`
--

CREATE TABLE `pma__table_coords` (
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `table_name` varchar(64) NOT NULL DEFAULT '',
  `pdf_page_number` int(11) NOT NULL DEFAULT 0,
  `x` float UNSIGNED NOT NULL DEFAULT 0,
  `y` float UNSIGNED NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table coordinates for phpMyAdmin PDF output';

-- --------------------------------------------------------

--
-- Table structure for table `pma__table_info`
--

CREATE TABLE `pma__table_info` (
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `table_name` varchar(64) NOT NULL DEFAULT '',
  `display_field` varchar(64) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table information for phpMyAdmin';

--
-- Dumping data for table `pma__table_info`
--

INSERT INTO `pma__table_info` (`db_name`, `table_name`, `display_field`) VALUES
('recept', 'recepti', 'naziv'),
('recept', 'recepti_sastojci', 'kolicina');

-- --------------------------------------------------------

--
-- Table structure for table `pma__table_uiprefs`
--

CREATE TABLE `pma__table_uiprefs` (
  `username` varchar(64) NOT NULL,
  `db_name` varchar(64) NOT NULL,
  `table_name` varchar(64) NOT NULL,
  `prefs` text NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Tables'' UI preferences';

-- --------------------------------------------------------

--
-- Table structure for table `pma__tracking`
--

CREATE TABLE `pma__tracking` (
  `db_name` varchar(64) NOT NULL,
  `table_name` varchar(64) NOT NULL,
  `version` int(10) UNSIGNED NOT NULL,
  `date_created` datetime NOT NULL,
  `date_updated` datetime NOT NULL,
  `schema_snapshot` text NOT NULL,
  `schema_sql` text DEFAULT NULL,
  `data_sql` longtext DEFAULT NULL,
  `tracking` set('UPDATE','REPLACE','INSERT','DELETE','TRUNCATE','CREATE DATABASE','ALTER DATABASE','DROP DATABASE','CREATE TABLE','ALTER TABLE','RENAME TABLE','DROP TABLE','CREATE INDEX','DROP INDEX','CREATE VIEW','ALTER VIEW','DROP VIEW') DEFAULT NULL,
  `tracking_active` int(1) UNSIGNED NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Database changes tracking for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__userconfig`
--

CREATE TABLE `pma__userconfig` (
  `username` varchar(64) NOT NULL,
  `timevalue` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `config_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='User preferences storage for phpMyAdmin';

--
-- Dumping data for table `pma__userconfig`
--

INSERT INTO `pma__userconfig` (`username`, `timevalue`, `config_data`) VALUES
('root', '2026-01-08 23:00:12', '{\"Console\\/Mode\":\"collapse\"}');

-- --------------------------------------------------------

--
-- Table structure for table `pma__usergroups`
--

CREATE TABLE `pma__usergroups` (
  `usergroup` varchar(64) NOT NULL,
  `tab` varchar(64) NOT NULL,
  `allowed` enum('Y','N') NOT NULL DEFAULT 'N'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='User groups with configured menu items';

-- --------------------------------------------------------

--
-- Table structure for table `pma__users`
--

CREATE TABLE `pma__users` (
  `username` varchar(64) NOT NULL,
  `usergroup` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Users and their assignments to user groups';

--
-- Indexes for dumped tables
--

--
-- Indexes for table `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pma__central_columns`
--
ALTER TABLE `pma__central_columns`
  ADD PRIMARY KEY (`db_name`,`col_name`);

--
-- Indexes for table `pma__column_info`
--
ALTER TABLE `pma__column_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `db_name` (`db_name`,`table_name`,`column_name`);

--
-- Indexes for table `pma__designer_settings`
--
ALTER TABLE `pma__designer_settings`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_user_type_template` (`username`,`export_type`,`template_name`);

--
-- Indexes for table `pma__favorite`
--
ALTER TABLE `pma__favorite`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__history`
--
ALTER TABLE `pma__history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`,`db`,`table`,`timevalue`);

--
-- Indexes for table `pma__navigationhiding`
--
ALTER TABLE `pma__navigationhiding`
  ADD PRIMARY KEY (`username`,`item_name`,`item_type`,`db_name`,`table_name`);

--
-- Indexes for table `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  ADD PRIMARY KEY (`page_nr`),
  ADD KEY `db_name` (`db_name`);

--
-- Indexes for table `pma__recent`
--
ALTER TABLE `pma__recent`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__relation`
--
ALTER TABLE `pma__relation`
  ADD PRIMARY KEY (`master_db`,`master_table`,`master_field`),
  ADD KEY `foreign_field` (`foreign_db`,`foreign_table`);

--
-- Indexes for table `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_savedsearches_username_dbname` (`username`,`db_name`,`search_name`);

--
-- Indexes for table `pma__table_coords`
--
ALTER TABLE `pma__table_coords`
  ADD PRIMARY KEY (`db_name`,`table_name`,`pdf_page_number`);

--
-- Indexes for table `pma__table_info`
--
ALTER TABLE `pma__table_info`
  ADD PRIMARY KEY (`db_name`,`table_name`);

--
-- Indexes for table `pma__table_uiprefs`
--
ALTER TABLE `pma__table_uiprefs`
  ADD PRIMARY KEY (`username`,`db_name`,`table_name`);

--
-- Indexes for table `pma__tracking`
--
ALTER TABLE `pma__tracking`
  ADD PRIMARY KEY (`db_name`,`table_name`,`version`);

--
-- Indexes for table `pma__userconfig`
--
ALTER TABLE `pma__userconfig`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__usergroups`
--
ALTER TABLE `pma__usergroups`
  ADD PRIMARY KEY (`usergroup`,`tab`,`allowed`);

--
-- Indexes for table `pma__users`
--
ALTER TABLE `pma__users`
  ADD PRIMARY KEY (`username`,`usergroup`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__column_info`
--
ALTER TABLE `pma__column_info`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__history`
--
ALTER TABLE `pma__history`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  MODIFY `page_nr` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- Database: `prodajavozila`
--
CREATE DATABASE IF NOT EXISTS `prodajavozila` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `prodajavozila`;

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`, `description`, `created_at`) VALUES
(1, 'Hatchback', 'Hatchback', '2026-01-05 22:57:23'),
(2, 'Limousine', 'Limousine', '2026-01-05 22:57:23'),
(3, 'SUV', 'SUV', '2026-01-05 22:57:47'),
(4, 'Cabrio', 'Cabrio', '2026-01-05 22:57:47'),
(5, 'Coupe', 'Coupe', '2026-01-05 22:57:57');

-- --------------------------------------------------------

--
-- Table structure for table `conversations`
--

CREATE TABLE `conversations` (
  `id` int(11) NOT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `vehicle_id` int(11) DEFAULT NULL,
  `offer_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `favorites`
--

CREATE TABLE `favorites` (
  `user_id` int(11) NOT NULL,
  `vehicle_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `conversation_id` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `is_read` bit(1) DEFAULT NULL,
  `sender_id` int(11) NOT NULL,
  `sent_at` datetime(6) NOT NULL,
  `content` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL,
  `is_read` bit(1) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `message` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`id`, `is_read`, `user_id`, `created_at`, `message`, `title`) VALUES
(1, b'0', 52, '2026-01-08 23:40:11.000000', 'Buyer sent offer for buyer | Price: 8000 | Vehicle: Peugeot 508', 'New offer for your vehicle'),
(2, b'0', 51, '2026-01-08 23:40:37.000000', 'Unfortunately, your offer for Peugeot was not accepted by the seller.', 'Offer Rejected');

-- --------------------------------------------------------

--
-- Table structure for table `offers`
--

CREATE TABLE `offers` (
  `id` int(11) NOT NULL,
  `offer_price` decimal(38,2) NOT NULL,
  `status` enum('WAITING','ACCEPTED','DENIED') NOT NULL DEFAULT 'WAITING',
  `message` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `vehicle_id` int(11) NOT NULL,
  `buyer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `offers`
--

INSERT INTO `offers` (`id`, `offer_price`, `status`, `message`, `created_at`, `updated_at`, `vehicle_id`, `buyer_id`) VALUES
(4, 8000.00, 'DENIED', 'Ponuda poslata sa web platforme', '2026-01-08 22:40:11', '2026-01-08 22:40:11', 21, 51);

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
  `id` int(11) NOT NULL,
  `offer_id` int(11) NOT NULL,
  `total_price` decimal(38,2) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `expiration_date` datetime(6) DEFAULT NULL,
  `reservation_date` datetime(6) NOT NULL,
  `status` enum('активна','завршена','отказана') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL CHECK (`name` like 'ROLE_%')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `enabled` tinyint(1) DEFAULT 1,
  `account_non_expired` tinyint(1) DEFAULT 1,
  `account_non_locked` tinyint(1) DEFAULT 1,
  `credentials_non_expired` tinyint(1) DEFAULT 1,
  `adress` varchar(255) DEFAULT NULL,
  `roles` enum('ADMIN','USER') DEFAULT NULL,
  `role` varbinary(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `first_name`, `last_name`, `phone`, `address`, `created_at`, `updated_at`, `enabled`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `adress`, `roles`, `role`) VALUES
(50, 'admin', '$2a$10$dzs9wrw7v5ydZ9oJS8mrMOnkNvwzsjBWT67HDnWZ7Yv2jjuVn7kNG', NULL, 'Admin', 'Admin', NULL, NULL, '2026-01-08 22:14:46', '2026-01-08 22:14:46', 1, 1, 1, 1, NULL, NULL, NULL),
(51, 'buyer', '$2a$10$Xdu6LmyUtN5rv7jimzVcEusfBfvz062L/rDlo7X9a4TjYwZmnwQFe', 'buyer@gmail.com', 'Buyer', 'Buyer', '0601122333', NULL, '2026-01-08 22:20:54', '2026-01-08 22:20:54', 1, 1, 1, 1, NULL, NULL, NULL),
(52, 'seller', '$2a$10$R5aWZzcx//SOIrpexvSguean4jd9i9P9N3bT39y5pcpHoQ1oqQy1S', 'seller@gmail.com', 'Seller', 'Seller', '0641122333', NULL, '2026-01-08 22:21:41', '2026-01-08 22:21:41', 1, 1, 1, 1, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users_seq`
--

CREATE TABLE `users_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users_seq`
--

INSERT INTO `users_seq` (`next_val`) VALUES
(251);

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `role` enum('ADMIN','BUYER','SELLER') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role`) VALUES
(50, 'ADMIN'),
(51, 'BUYER'),
(52, 'SELLER');

-- --------------------------------------------------------

--
-- Table structure for table `vehicles`
--

CREATE TABLE `vehicles` (
  `id` int(11) NOT NULL,
  `make` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `year` int(11) NOT NULL,
  `mileage` int(11) DEFAULT NULL,
  `price` double NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fuel_type` varchar(255) DEFAULT NULL,
  `transmission` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `engine_capacity` double DEFAULT NULL,
  `features` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `category_id` int(11) NOT NULL,
  `seller_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vehicles`
--

INSERT INTO `vehicles` (`id`, `make`, `model`, `year`, `mileage`, `price`, `description`, `fuel_type`, `transmission`, `color`, `engine_capacity`, `features`, `status`, `created_at`, `updated_at`, `category_id`, `seller_id`) VALUES
(19, 'Mercedes Benz', 'S Class', 2018, 235000, 27400, '', 'DIESEL', 'Automatski', 'Black', 3197, '', 'AVAILABLE', '2026-01-08 22:26:46', '2026-01-08 22:26:46', 2, 52),
(21, 'Peugeot', '508', 2012, 265400, 8300, '', 'DIESEL', 'Manual', 'Black', 1580, '', 'AVAILABLE', '2026-01-08 22:31:34', '2026-01-08 22:31:34', 2, 52);

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_images`
--

CREATE TABLE `vehicle_images` (
  `id` int(11) NOT NULL,
  `is_primary` bit(1) DEFAULT NULL,
  `vehicle_id` int(11) NOT NULL,
  `image_url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vehicle_images`
--

INSERT INTO `vehicle_images` (`id`, `is_primary`, `vehicle_id`, `image_url`) VALUES
(10, b'0', 19, '1767911206490_viber_image_2023-04-11_17-06-08-606.jpg'),
(11, b'0', 19, '1767911206500_viber_image_2023-04-11_17-06-10-043.jpg'),
(12, b'0', 19, '1767911206509_viber_image_2023-04-11_17-07-08-336.jpg'),
(13, b'0', 19, '1767911206519_viber_image_2023-04-11_17-07-09-213.jpg'),
(14, b'0', 19, '1767911206527_viber_image_2023-04-11_17-07-09-539.jpg'),
(15, b'0', 19, '1767911206536_viber_image_2023-04-11_17-07-09-765.jpg'),
(16, b'0', 19, '1767911206545_viber_image_2023-04-11_17-07-09-958.jpg'),
(17, b'0', 19, '1767911206556_viber_image_2023-04-11_17-07-10-154.jpg'),
(22, b'0', 21, '1767911494373_20220724_200529.heic'),
(23, b'0', 21, '1767911494383_20220724_200633.heic'),
(24, b'0', 21, '1767911494392_20220724_200711.heic'),
(25, b'0', 21, '1767911494402_20220724_200759.heic');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `conversations`
--
ALTER TABLE `conversations`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKb7pciye4u85okjcnfwjmw2avd` (`offer_id`),
  ADD KEY `FKe33ldqgwlk4ymw3fuddnllwnp` (`vehicle_id`);

--
-- Indexes for table `favorites`
--
ALTER TABLE `favorites`
  ADD PRIMARY KEY (`user_id`,`vehicle_id`),
  ADD KEY `FKgdo3ecsmcd8h2hogrhgsyyudr` (`vehicle_id`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt492th6wsovh1nush5yl5jj8e` (`conversation_id`),
  ADD KEY `FK4ui4nnwntodh6wjvck53dbk9m` (`sender_id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9y21adhxn0ayjhfocscqox7bh` (`user_id`);

--
-- Indexes for table `offers`
--
ALTER TABLE `offers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgc18i3k5pqq7ik9x23nw0ndau` (`buyer_id`),
  ADD KEY `FKlx6v6eaab62aafhscs4gvjw6g` (`vehicle_id`);

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKpg50n1ui5ilyn9pt3xuculwu1` (`offer_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD KEY `FKhfh9dx7w3ubf1co1vdev94g3f` (`user_id`);

--
-- Indexes for table `vehicles`
--
ALTER TABLE `vehicles`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKdjuwisect6diyjv2bk0j4wlcv` (`category_id`),
  ADD KEY `FKh8561asry394e9y3oc5lgdqtq` (`seller_id`);

--
-- Indexes for table `vehicle_images`
--
ALTER TABLE `vehicle_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKp6gw8mt61ktmsk5nuc4qid7i8` (`vehicle_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `conversations`
--
ALTER TABLE `conversations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `offers`
--
ALTER TABLE `offers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `reservations`
--
ALTER TABLE `reservations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT for table `vehicles`
--
ALTER TABLE `vehicles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `vehicle_images`
--
ALTER TABLE `vehicle_images`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `conversations`
--
ALTER TABLE `conversations`
  ADD CONSTRAINT `FKe33ldqgwlk4ymw3fuddnllwnp` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`id`),
  ADD CONSTRAINT `FKoj0kb2ga9n13e34w10o6m19ys` FOREIGN KEY (`offer_id`) REFERENCES `offers` (`id`),
  ADD CONSTRAINT `conversations_ibfk_1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`id`),
  ADD CONSTRAINT `conversations_ibfk_2` FOREIGN KEY (`offer_id`) REFERENCES `offers` (`id`);

--
-- Constraints for table `favorites`
--
ALTER TABLE `favorites`
  ADD CONSTRAINT `FKgdo3ecsmcd8h2hogrhgsyyudr` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`id`),
  ADD CONSTRAINT `FKk7du8b8ewipawnnpg76d55fus` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `FK4ui4nnwntodh6wjvck53dbk9m` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKt492th6wsovh1nush5yl5jj8e` FOREIGN KEY (`conversation_id`) REFERENCES `conversations` (`id`);

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `FK9y21adhxn0ayjhfocscqox7bh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `offers`
--
ALTER TABLE `offers`
  ADD CONSTRAINT `FKgc18i3k5pqq7ik9x23nw0ndau` FOREIGN KEY (`buyer_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKlx6v6eaab62aafhscs4gvjw6g` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`id`),
  ADD CONSTRAINT `offers_ibfk_1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`id`),
  ADD CONSTRAINT `offers_ibfk_2` FOREIGN KEY (`buyer_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `FKjk5eau9ty1r4m4ibk0tat5ulr` FOREIGN KEY (`offer_id`) REFERENCES `offers` (`id`);

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `vehicles`
--
ALTER TABLE `vehicles`
  ADD CONSTRAINT `FKdjuwisect6diyjv2bk0j4wlcv` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  ADD CONSTRAINT `FKh8561asry394e9y3oc5lgdqtq` FOREIGN KEY (`seller_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `vehicles_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  ADD CONSTRAINT `vehicles_ibfk_2` FOREIGN KEY (`seller_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `vehicle_images`
--
ALTER TABLE `vehicle_images`
  ADD CONSTRAINT `FKp6gw8mt61ktmsk5nuc4qid7i8` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`id`);
--
-- Database: `recept`
--
CREATE DATABASE IF NOT EXISTS `recept` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `recept`;

-- --------------------------------------------------------

--
-- Table structure for table `kategorija`
--

CREATE TABLE `kategorija` (
  `id` int(100) NOT NULL,
  `naziv` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `korisnici`
--

CREATE TABLE `korisnici` (
  `id` int(100) NOT NULL,
  `ime` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `recepti`
--

CREATE TABLE `recepti` (
  `id` int(100) NOT NULL,
  `naziv` varchar(100) NOT NULL,
  `opis` varchar(100) NOT NULL,
  `vreme_pripreme` time(6) NOT NULL,
  `slozenost` varchar(100) NOT NULL,
  `fk_korisnik` int(100) NOT NULL,
  `fk_kategorija` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `recepti_sastojci`
--

CREATE TABLE `recepti_sastojci` (
  `id` int(100) NOT NULL,
  `kolicina` varchar(100) NOT NULL,
  `jedinica_mere` varchar(100) NOT NULL,
  `fk_recepti` int(100) NOT NULL,
  `fk_sastojak` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sastojci`
--

CREATE TABLE `sastojci` (
  `id` int(100) NOT NULL,
  `naziv` varchar(100) NOT NULL,
  `jedinica_mere` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kategorija`
--
ALTER TABLE `kategorija`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `korisnici`
--
ALTER TABLE `korisnici`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `recepti`
--
ALTER TABLE `recepti`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_korisnik` (`fk_korisnik`),
  ADD KEY `fk_kategorija` (`fk_kategorija`);

--
-- Indexes for table `recepti_sastojci`
--
ALTER TABLE `recepti_sastojci`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_recepti` (`fk_recepti`),
  ADD KEY `fk_sastojak` (`fk_sastojak`);

--
-- Indexes for table `sastojci`
--
ALTER TABLE `sastojci`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kategorija`
--
ALTER TABLE `kategorija`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `korisnici`
--
ALTER TABLE `korisnici`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `recepti`
--
ALTER TABLE `recepti`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `recepti_sastojci`
--
ALTER TABLE `recepti_sastojci`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sastojci`
--
ALTER TABLE `sastojci`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `recepti`
--
ALTER TABLE `recepti`
  ADD CONSTRAINT `fk_kategorija` FOREIGN KEY (`fk_kategorija`) REFERENCES `kategorija` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_korisnik` FOREIGN KEY (`fk_korisnik`) REFERENCES `korisnici` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `recepti_sastojci`
--
ALTER TABLE `recepti_sastojci`
  ADD CONSTRAINT `fk_recepti` FOREIGN KEY (`fk_recepti`) REFERENCES `recepti` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_sastojak` FOREIGN KEY (`fk_sastojak`) REFERENCES `sastojci` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- Database: `restoran`
--
CREATE DATABASE IF NOT EXISTS `restoran` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `restoran`;

-- --------------------------------------------------------

--
-- Table structure for table `korisnici`
--

CREATE TABLE `korisnici` (
  `id` int(10) NOT NULL,
  `ime` varchar(25) NOT NULL,
  `prezime` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `korisnici`
--

INSERT INTO `korisnici` (`id`, `ime`, `prezime`, `email`, `username`, `password`) VALUES
(25, 'test', 'test', 'test@gmail.com', 'test', '$2y$10$KVVdUSpFpX/zYmzjfD341eSKu/FHDYI8kH8YVOmHwMQbnu/aj6.u6');

-- --------------------------------------------------------

--
-- Table structure for table `restoran`
--

CREATE TABLE `restoran` (
  `id` int(100) NOT NULL,
  `naziv` varchar(100) NOT NULL,
  `lokacija` varchar(100) NOT NULL,
  `telefon` int(30) NOT NULL,
  `email` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `restoran`
--

INSERT INTO `restoran` (`id`, `naziv`, `lokacija`, `telefon`, `email`) VALUES
(4, 'palas', 'kg', 611122, 'palas@gmail.com'),
(5, 'tradicija', 'bulevar', 68921, 'tradicija@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `rezervacije`
--

CREATE TABLE `rezervacije` (
  `id` int(100) NOT NULL,
  `ime` int(255) NOT NULL,
  `datum` date NOT NULL,
  `vreme` time(6) NOT NULL,
  `rezervacija_sto` int(100) NOT NULL,
  `rezervacija_korisnik` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `stolovi`
--

CREATE TABLE `stolovi` (
  `id` int(100) NOT NULL,
  `broj` int(100) NOT NULL,
  `kapacitet` int(30) NOT NULL,
  `fk_restoran` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stolovi`
--

INSERT INTO `stolovi` (`id`, `broj`, `kapacitet`, `fk_restoran`) VALUES
(2, 1, 4, 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `korisnici`
--
ALTER TABLE `korisnici`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `restoran`
--
ALTER TABLE `restoran`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rezervacije`
--
ALTER TABLE `rezervacije`
  ADD PRIMARY KEY (`id`),
  ADD KEY `rezervacija_sto` (`rezervacija_sto`),
  ADD KEY `rezervacija_korisnik` (`rezervacija_korisnik`);

--
-- Indexes for table `stolovi`
--
ALTER TABLE `stolovi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_restoran` (`fk_restoran`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `korisnici`
--
ALTER TABLE `korisnici`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `restoran`
--
ALTER TABLE `restoran`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `rezervacije`
--
ALTER TABLE `rezervacije`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `stolovi`
--
ALTER TABLE `stolovi`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `rezervacije`
--
ALTER TABLE `rezervacije`
  ADD CONSTRAINT `rezervacija_korisnik` FOREIGN KEY (`rezervacija_korisnik`) REFERENCES `korisnici` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `rezervacija_sto` FOREIGN KEY (`rezervacija_sto`) REFERENCES `stolovi` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `stolovi`
--
ALTER TABLE `stolovi`
  ADD CONSTRAINT `fk_restoran` FOREIGN KEY (`fk_restoran`) REFERENCES `restoran` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

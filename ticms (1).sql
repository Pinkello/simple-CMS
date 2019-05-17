-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 03 Maj 2019, 18:03
-- Wersja serwera: 10.1.34-MariaDB
-- Wersja PHP: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `ticms`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `elements`
--

CREATE TABLE `elements` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `FONT_SIZE` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `FONT_COLOR` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `FONT_WEIGHT` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `HTML` varchar(1000) COLLATE utf8_polish_ci NOT NULL,
  `BACKGROUND` varchar(45) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `elements`
--

INSERT INTO `elements` (`ID`, `NAME`, `FONT_SIZE`, `FONT_COLOR`, `FONT_WEIGHT`, `HTML`, `BACKGROUND`) VALUES
(1, 'HEADER_HTML', '25px', 'GREEN', 'bold', 'HEADER', '#123456'),
(2, 'FOOTER_HTML', '10px', '#f6f6f6', 'bold', 'footer', '#123456'),
(3, 'DESKTOP_HTML', '12px', 'black', 'light', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', '#f6f6f6'),
(4, 'LINKS_HTML', '15px', '#333', 'bold', 'Link page here, and link below:', '#f6f6f6'),
(5, 'LINKSADD_HTML', '15px', 'black', 'bold', 'Add new link', 'white'),
(6, 'LINKSEDIT_HTML', '20px', 'red', '100', 'Edit link here', 'yellow'),
(7, 'LOGIN_HTML', '15px', 'black', 'normal', 'Logowanie', 'pink'),
(8, 'REGISTER_HTML', '15px', 'black', 'bold', 'REJESTRACJA', 'green'),
(9, 'PROFIL_HTML', '12px', 'blue', '100', 'YOUR PROFILE', 'BLACK'),
(10, 'EDITPROFIL_HTML', '16px', 'red', 'normal', 'EDIT PROFILEE', 'black');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `link`
--

CREATE TABLE `link` (
  `ID` int(11) NOT NULL,
  `HREF` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `DISPLAY_NAME` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `NAME` varchar(45) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `link`
--

INSERT INTO `link` (`ID`, `HREF`, `DISPLAY_NAME`, `NAME`) VALUES
(1, 'www.onet.pl', 'Onet', 'onet'),
(2, 'www.google.pl', 'GOOGLE', 'GOOGLE'),
(8, 'GRY.PL', 'GRY', 'GRY'),
(11, 'ADD.PL', 'ADD', 'add'),
(12, 'facebook.pl', 'facebook', 'facebook'),
(14, 'nowy.pl', 'nowyyyyy', 'nowy');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user`
--

CREATE TABLE `user` (
  `FIRST_NAME` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `LAST_NAME` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `EMAIL` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `PASSWORD` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `LOGIN` varchar(45) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `user`
--

INSERT INTO `user` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`, `LOGIN`) VALUES
('Admin', 'Admin', 'admin@admin.pl', 'admin', 'admin'),
('Piotr', 'Pindel', 'piotr@gmail.pl', 'piotr', 'piotr');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `elements`
--
ALTER TABLE `elements`
  ADD PRIMARY KEY (`ID`);

--
-- Indeksy dla tabeli `link`
--
ALTER TABLE `link`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `elements`
--
ALTER TABLE `elements`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT dla tabeli `link`
--
ALTER TABLE `link`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Sam 11 Janvier 2020 à 12:32
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `schoolsm`
--

-- --------------------------------------------------------

--
-- Structure de la table `t_bulletins`
--

CREATE TABLE `t_bulletins` (
  `id` int(4) NOT NULL,
  `idEleve` int(4) NOT NULL,
  `nom` text,
  `prenom` text,
  `genre` text,
  `date` text,
  `decision` text,
  `moyenneGenerale` double DEFAULT NULL,
  `effectif` int(4) DEFAULT NULL,
  `rang` int(4) DEFAULT NULL,
  `classe` text,
  `semestre` text
) ;

--
-- Contenu de la table `t_bulletins`
--

INSERT INTO `t_bulletins` (`id`, `idEleve`, `nom`, `prenom`, `genre`, `date`, `decision`, `moyenneGenerale`, `effectif`, `rang`, `classe`, `semestre`) VALUES
(1, 1, 'KABORE', 'Eric', 'Homme', '2019-08-06', 'Semestre ValidÃ©', 11, 2, 2, 'LPTI-1', 'Semestre 1'),
(2, 2, 'NDIAYE', 'Racine', 'Homme', '2019-08-06', 'Semestre ValidÃ©', 18, 2, 1, 'LPTI-1', 'Semestre 1'),
(3, 3, 'KOUSSOUBE', 'OUSSENI', 'Homme', '2019-08-06', 'Semestre ValidÃ©', 15, 2, 1, 'LPTI-2', 'Semestre 1'),
(4, 4, 'DIABATE', 'tahirou', 'Homme', '2019-08-06', 'Semestre ValidÃ©', 13, 2, 2, 'LPTI-2', 'Semestre 1');

-- --------------------------------------------------------

--
-- Structure de la table `t_bulletins_notes`
--

CREATE TABLE `t_bulletins_notes` (
  `id` int(4) NOT NULL,
  `idEleve` int(4) NOT NULL,
  `idBulletin` int(4) NOT NULL,
  `note` double DEFAULT NULL,
  `coef` int(4) DEFAULT NULL,
  `notePondere` double DEFAULT NULL,
  `libelle` text
) ;

--
-- Contenu de la table `t_bulletins_notes`
--

INSERT INTO `t_bulletins_notes` (`id`, `idEleve`, `idBulletin`, `note`, `coef`, `notePondere`, `libelle`) VALUES
(1, 1, 1, 11, 8, 88, 'Python'),
(2, 2, 2, 18, 8, 144, 'Python'),
(3, 3, 3, 15, 3, 45, 'CCNA3'),
(4, 4, 4, 13, 3, 39, 'CCNA3');

-- --------------------------------------------------------

--
-- Structure de la table `t_candidatures`
--

CREATE TABLE `t_candidatures` (
  `id` int(4) NOT NULL,
  `idEnseignant` int(4) DEFAULT NULL,
  `nom` text NOT NULL,
  `prenom` text NOT NULL,
  `email` text NOT NULL,
  `telephone` text NOT NULL,
  `genre` text NOT NULL,
  `path` text,
  `birthday` text NOT NULL,
  `cv` text,
  `state` text,
  `commentaires` text
) ;

--
-- Contenu de la table `t_candidatures`
--

INSERT INTO `t_candidatures` (`id`, `idEnseignant`, `nom`, `prenom`, `email`, `telephone`, `genre`, `path`, `birthday`, `cv`, `state`, `commentaires`) VALUES
(3, 2, 'KABORE', 'Eric', 'mail@mail', '44444', 'Homme', '', '11-11-1991', 'C:\\Users\\Eric\\Documents\\KABORE Eric.pdf', 'Ouverte - Non encore affectÃ©e', ''),
(4, 3, 'NDIAYE', 'RACINE', 'dsdcsd', '5641546', 'Homme', '', '', '', 'Ouverte - Non encore affectÃ©e', 'movais'),
(5, 1, 'SEMASSA', 'LUDOVIC', 'email@email', '42424', 'Homme', '', '11-02-1992', 'C:\\Users\\Eric\\Documents\\KABORE Eric.pdf', 'Ouverte - Non encore affectÃ©e', '');

-- --------------------------------------------------------

--
-- Structure de la table `t_classes`
--

CREATE TABLE `t_classes` (
  `id` int(4) NOT NULL,
  `libelle` text,
  `description` text
) ;

--
-- Contenu de la table `t_classes`
--

INSERT INTO `t_classes` (`id`, `libelle`, `description`) VALUES
(1, 'LPTI-1', 'premiÃ¨re annee generaliste'),
(2, 'LPTI-2', 'deuxieme annee generaliste'),
(3, 'LP3-DAR', 'specialisation developpement d\'application'),
(4, 'LP3-ASR', 'Specialisation securite reseau');

-- --------------------------------------------------------

--
-- Structure de la table `t_cours`
--

CREATE TABLE `t_cours` (
  `id` int(4) NOT NULL,
  `idModule` int(4) NOT NULL,
  `idClasse` int(4) DEFAULT NULL,
  `description` text,
  `dateHeure` text,
  `duree` int(4) DEFAULT NULL
) ;

--
-- Contenu de la table `t_cours`
--

INSERT INTO `t_cours` (`id`, `idModule`, `idClasse`, `description`, `dateHeure`, `duree`) VALUES
(1, 4, 1, 'Presentatio', 'Mon Aug 05 01:27:20 WAT 2019', 70),
(2, 5, 2, '', 'Wed Aug 21 01:26:00 WAT 2019', 60),
(3, 4, 1, 'gggg', 'Thu Aug 22 01:35:00 WAT 2019', 60),
(4, 6, 1, '', 'Mon Aug 05 01:56:31 WAT 2019', 60),
(5, 2, 2, '', 'Mon Aug 05 01:56:37 WAT 2019', 60),
(6, 6, 1, 'fdfdf', 'Wed Aug 14 02:07:00 WAT 2019', 60),
(7, 3, 1, '', 'Thu Aug 15 02:07:00 WAT 2019', 60);

-- --------------------------------------------------------

--
-- Structure de la table `t_devoirs`
--

CREATE TABLE `t_devoirs` (
  `id` int(4) NOT NULL,
  `idModule` int(4) NOT NULL,
  `idClasse` int(4) DEFAULT NULL,
  `statut` text,
  `description` text,
  `dateHeure` text,
  `duree` int(4) DEFAULT NULL,
  `coef` int(4) DEFAULT NULL
) ;

--
-- Contenu de la table `t_devoirs`
--

INSERT INTO `t_devoirs` (`id`, `idModule`, `idClasse`, `statut`, `description`, `dateHeure`, `duree`, `coef`) VALUES
(1, 4, 1, 'pas encore effectue', 'cxcx', 'Tue Aug 13 01:28:00 WAT 2019', 60, 7),
(2, 5, 2, 'pas encore effectue', '', 'Wed Aug 14 01:29:00 WAT 2019', 60, 10),
(3, 6, 1, 'pas encore effectue', 'fff', 'Thu Aug 15 01:32:00 WAT 2019', 60, 8),
(4, 3, 2, 'pas encore effectue', 'bbbb', 'Thu Aug 22 01:32:00 WAT 2019', 60, 3);

-- --------------------------------------------------------

--
-- Structure de la table `t_diplomes`
--

CREATE TABLE `t_diplomes` (
  `id` int(4) NOT NULL,
  `idCandidature` int(4) DEFAULT NULL,
  `url` text NOT NULL
) ;

--
-- Contenu de la table `t_diplomes`
--

INSERT INTO `t_diplomes` (`id`, `idCandidature`, `url`) VALUES
(1, 0, 'C:\\Users\\Eric\\Documents\\Certificate Of Completion - FRE.pdf'),
(2, 4, '/etc/mop'),
(3, 5, 'C:\\Users\\Eric\\Desktop\\MASTER-SI\\Criteres de performance d\'un SI.pdf');

-- --------------------------------------------------------

--
-- Structure de la table `t_eleves`
--

CREATE TABLE `t_eleves` (
  `id` int(4) NOT NULL,
  `idClasse` int(4) DEFAULT NULL,
  `nom` text NOT NULL,
  `prenom` text NOT NULL,
  `genre` text NOT NULL,
  `telephone` text NOT NULL,
  `login` text NOT NULL,
  `password` text NOT NULL,
  `email` text,
  `path` text
) ;

--
-- Contenu de la table `t_eleves`
--

INSERT INTO `t_eleves` (`id`, `idClasse`, `nom`, `prenom`, `genre`, `telephone`, `login`, `password`, `email`, `path`) VALUES
(1, 1, 'KABORE', 'Eric', 'Homme', '45454545', 'eric', 'eric', 'eric@eric.com', 'C:\\Users\\Eric\\Desktop\\MASTER-SI\\ProjetJava\\image\\33645487-icÃ´ne-de-profil-avatar-portrait-masculin-personne-dÃ©contractÃ©e.jpg'),
(2, 1, 'NDIAYE', 'Racine', 'Homme', '77777777', 'racine', 'racine', 'racine@racine', 'C:\\Users\\Eric\\Desktop\\MASTER-SI\\ProjetJava\\image\\82760945-icÃ´ne-de-l-homme-raster-illustration-homme-d-affaires-en-design-plat-d-avatar-costume-noir-ava.jpg'),
(3, 2, 'KOUSSOUBE', 'OUSSENI', 'Homme', '55555555', 'kouss', 'kouss', 'kouss', 'C:\\Users\\Eric\\Desktop\\MASTER-SI\\ProjetJava\\image\\1548216489_avatarkartunmuslim4.jpg'),
(4, 2, 'DIABATE', 'tahirou', 'Homme', '888888', 'diabate', 'diabate', 'diabate', 'C:\\Users\\Eric\\Desktop\\MASTER-SI\\ProjetJava\\image\\1548216489_avatarkartunmuslim4.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `t_enseignants`
--

CREATE TABLE `t_enseignants` (
  `Id` int(4) NOT NULL,
  `nom` text NOT NULL,
  `prenom` text NOT NULL,
  `genre` text NOT NULL,
  `path` text,
  `telephone` text NOT NULL,
  `login` text NOT NULL,
  `password` text NOT NULL,
  `email` text NOT NULL
) ;

--
-- Contenu de la table `t_enseignants`
--

INSERT INTO `t_enseignants` (`Id`, `nom`, `prenom`, `genre`, `path`, `telephone`, `login`, `password`, `email`) VALUES
(1, 'AKINOCHO', 'Ghislain', 'Homme', 'C:\\Users\\Eric\\Desktop\\MASTER-SI\\ProjetJava\\image\\avatar-3637425_960_720.png', '8888888', 'ghislain', 'ghislain', 'ghilslain@ghislain.com'),
(2, 'PRAIRA', 'Jean Marie', 'Homme', 'C:\\Users\\Eric\\Desktop\\MASTER-SI\\ProjetJava\\image\\82760945-icÃ´ne-de-l-homme-raster-illustration-homme-d-affaires-en-design-plat-d-avatar-costume-noir-ava.jpg', '99999', 'jean', 'jean', 'jeanmarie@gmail.com'),
(3, 'BAH', 'Samba Oumar', 'Homme', 'C:\\Users\\Eric\\Desktop\\MASTER-SI\\ProjetJava\\image\\pngtree-man-avatar-icon-professional-man-character-png-image_1055448.jpg', '111111111', 'boss', 'boss', 'boss@boss');

-- --------------------------------------------------------

--
-- Structure de la table `t_enseignants_classes_modules`
--

CREATE TABLE `t_enseignants_classes_modules` (
  `Id` int(4) NOT NULL,
  `IdEns` int(4) DEFAULT NULL,
  `IdClasse` int(4) DEFAULT NULL,
  `IdModule` int(4) DEFAULT NULL
) ;

--
-- Contenu de la table `t_enseignants_classes_modules`
--

INSERT INTO `t_enseignants_classes_modules` (`Id`, `IdEns`, `IdClasse`, `IdModule`) VALUES
(1, 1, 1, 4),
(2, 3, 1, 6),
(3, 1, 2, 5),
(4, 3, 2, 2),
(5, 3, 4, 3),
(6, 3, 4, 1),
(7, 3, 3, 1);

-- --------------------------------------------------------

--
-- Structure de la table `t_enseignants_cours`
--

CREATE TABLE `t_enseignants_cours` (
  `Id` int(4) NOT NULL,
  `IdEnseignant` int(4) NOT NULL,
  `IdCours` int(4) NOT NULL
) ;

--
-- Contenu de la table `t_enseignants_cours`
--

INSERT INTO `t_enseignants_cours` (`Id`, `IdEnseignant`, `IdCours`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 3, 4),
(5, 3, 5),
(6, 3, 6),
(7, 3, 7);

-- --------------------------------------------------------

--
-- Structure de la table `t_enseignants_devoirs`
--

CREATE TABLE `t_enseignants_devoirs` (
  `Id` int(4) NOT NULL,
  `IdEnseignant` int(4) NOT NULL,
  `IdDevoirs` int(4) NOT NULL
) ;

--
-- Contenu de la table `t_enseignants_devoirs`
--

INSERT INTO `t_enseignants_devoirs` (`Id`, `IdEnseignant`, `IdDevoirs`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 3, 3),
(4, 3, 4);

-- --------------------------------------------------------

--
-- Structure de la table `t_gestionnaires`
--

CREATE TABLE `t_gestionnaires` (
  `id` int(4) NOT NULL,
  `nom` varchar(45) DEFAULT NULL,
  `prenom` varchar(45) DEFAULT NULL,
  `adresse` varchar(45) DEFAULT NULL,
  `date_naissance` varchar(45) NOT NULL,
  `telephone` varchar(45) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `login` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL
) ;

--
-- Contenu de la table `t_gestionnaires`
--

INSERT INTO `t_gestionnaires` (`id`, `nom`, `prenom`, `adresse`, `date_naissance`, `telephone`, `email`, `login`, `password`) VALUES
(29, 'Hane', 'Abdoul Aziz', 'Sacre-Coeur3', '', '781696498', '', 'abdoul', 'passer'),
(30, 'Marega', 'marega', 'sssss', '11-05-199', '777777', 'marega@marega', 'marega', 'marega');

-- --------------------------------------------------------

--
-- Structure de la table `t_modules`
--

CREATE TABLE `t_modules` (
  `id` int(4) NOT NULL,
  `coefficient` int(4) DEFAULT NULL,
  `libelle` text,
  `description` text
) ;

--
-- Contenu de la table `t_modules`
--

INSERT INTO `t_modules` (`id`, `coefficient`, `libelle`, `description`) VALUES
(1, 3, 'CCNA1', 'premier module de cisco'),
(2, 3, 'CCNA2', 'Deuxieme moule de cisco'),
(3, 3, 'CCNA3', 'troisieme module de cisco'),
(4, 7, 'JAVA SE', 'java standart'),
(5, 10, 'JAVA EE', 'Java enterprise'),
(6, 8, 'Python', 'base en python');

-- --------------------------------------------------------

--
-- Structure de la table `t_note`
--

CREATE TABLE `t_note` (
  `id` int(4) NOT NULL,
  `idDevoirs` int(4) NOT NULL,
  `note` double DEFAULT NULL,
  `coef` int(4) DEFAULT NULL,
  `idEleve` int(4) NOT NULL
) ;

--
-- Contenu de la table `t_note`
--

INSERT INTO `t_note` (`id`, `idDevoirs`, `note`, `coef`, `idEleve`) VALUES
(1, 4, 15, 3, 3),
(2, 4, 13, 3, 4),
(3, 3, 11, 8, 1),
(4, 3, 18, 8, 2);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `t_bulletins`
--
ALTER TABLE `t_bulletins`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idEleve` (`idEleve`);

--
-- Index pour la table `t_bulletins_notes`
--
ALTER TABLE `t_bulletins_notes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idEleve` (`idEleve`),
  ADD KEY `idBulletin` (`idBulletin`);

--
-- Index pour la table `t_candidatures`
--
ALTER TABLE `t_candidatures`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_classes`
--
ALTER TABLE `t_classes`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_cours`
--
ALTER TABLE `t_cours`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_devoirs`
--
ALTER TABLE `t_devoirs`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_diplomes`
--
ALTER TABLE `t_diplomes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IdCandidature_idx` (`idCandidature`);

--
-- Index pour la table `t_eleves`
--
ALTER TABLE `t_eleves`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_enseignants`
--
ALTER TABLE `t_enseignants`
  ADD PRIMARY KEY (`Id`);

--
-- Index pour la table `t_enseignants_classes_modules`
--
ALTER TABLE `t_enseignants_classes_modules`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `UNICITE` (`IdClasse`,`IdModule`),
  ADD KEY `IdEns` (`IdEns`),
  ADD KEY `IdModule` (`IdModule`);

--
-- Index pour la table `t_enseignants_cours`
--
ALTER TABLE `t_enseignants_cours`
  ADD PRIMARY KEY (`Id`);

--
-- Index pour la table `t_enseignants_devoirs`
--
ALTER TABLE `t_enseignants_devoirs`
  ADD PRIMARY KEY (`Id`);

--
-- Index pour la table `t_gestionnaires`
--
ALTER TABLE `t_gestionnaires`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_modules`
--
ALTER TABLE `t_modules`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_note`
--
ALTER TABLE `t_note`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idDevoirs` (`idDevoirs`),
  ADD KEY `idEleve` (`idEleve`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `t_bulletins`
--
ALTER TABLE `t_bulletins`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_bulletins_notes`
--
ALTER TABLE `t_bulletins_notes`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_candidatures`
--
ALTER TABLE `t_candidatures`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_classes`
--
ALTER TABLE `t_classes`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_cours`
--
ALTER TABLE `t_cours`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_devoirs`
--
ALTER TABLE `t_devoirs`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_diplomes`
--
ALTER TABLE `t_diplomes`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_eleves`
--
ALTER TABLE `t_eleves`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_enseignants`
--
ALTER TABLE `t_enseignants`
  MODIFY `Id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_enseignants_classes_modules`
--
ALTER TABLE `t_enseignants_classes_modules`
  MODIFY `Id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_enseignants_cours`
--
ALTER TABLE `t_enseignants_cours`
  MODIFY `Id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_enseignants_devoirs`
--
ALTER TABLE `t_enseignants_devoirs`
  MODIFY `Id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_gestionnaires`
--
ALTER TABLE `t_gestionnaires`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_modules`
--
ALTER TABLE `t_modules`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_note`
--
ALTER TABLE `t_note`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

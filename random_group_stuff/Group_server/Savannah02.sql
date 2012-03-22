drop table `TestDB`.`Department`;
drop table `TestDB`.`Subdepartment`;
drop table `TestDB`.`Certificate`;
drop table `TestDB`.`Profile`;
drop table `TestDB`.`Apps`;
drop table `TestDB`.`HasSubdepartment`;
drop table `TestDB`.`ListOfApps`;
drop table `TestDB`.`HasGuardian`;
drop table `TestDB`.`Tags`;
drop table `TestDB`.`Media`;
drop table `TestDB`.`HasTag`;
drop table `TestDB`.`HasLink`;
drop table `TestDB`.`HasMedia`;

CREATE  TABLE `TestDB`.`Department` (

  `idDepartment` INT NOT NULL ,

  `Name` VARCHAR(45) NULL ,

  `Address` VARCHAR(45) NULL ,

  `Phone` BIGINT NULL ,

  `Email` VARCHAR(45) NULL ,

  PRIMARY KEY (`idDepartment`) );

CREATE  TABLE `TestDB`.`Subdepartment` (

  `idSubdepartment` INT NOT NULL ,

  `idDepartment` INT NOT NULL ,

  `Name` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`idSubdepartment`) ,

  INDEX `idDepartment` (`idDepartment` ASC) );

CREATE  TABLE `TestDB`.`Certificate` (

  `idCertificate` INT NOT NULL ,

  `AuthKey` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`idCertificate`) );

CREATE  TABLE `TestDB`.`Profile` (

  `idProfile` INT NOT NULL ,

  `Firstname` VARCHAR(45) NOT NULL ,

  `Surname` VARCHAR(45) NOT NULL ,

  `Middlename` VARCHAR(45) NULL ,

  `Role` INT NOT NULL ,

  `Phone` BIGINT NOT NULL ,

  `Picture` VARCHAR(45) NULL ,

  `idCert` INT NOT NULL ,

  PRIMARY KEY (`idProfile`) ,

  INDEX `idCert` (`idCert` ASC) );

CREATE  TABLE `TestDB`.`Apps` (

  `idApps` INT NOT NULL ,

  `Name` VARCHAR(45) NOT NULL ,

  `Version` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`idApps`) );

CREATE  TABLE `TestDB`.`HasSubdepartment` (

  `idProfile` INT NOT NULL ,

  `idSubdepartment` INT NOT NULL ,

  PRIMARY KEY (`idProfile`, `idSubdepartment`) ,

  INDEX `idProfile` (`idProfile` ASC) ,

  INDEX `idSubdepartment` (`idSubdepartment` ASC) );

CREATE  TABLE `TestDB`.`ListOfApps` (

  `idApps` INT NOT NULL ,

  `idProfile` INT NOT NULL ,

  `Settings` BLOB NULL ,

  `Stats` BLOB NULL ,

  PRIMARY KEY (`idApps`, `idProfile`) ,

  INDEX `idApps` (`idApps` ASC) ,

  INDEX `idProfile` (`idProfile` ASC) );

CREATE  TABLE `TestDB`.`HasGuardian` (

  `idGuardian` INT NOT NULL ,

  `idChild` INT NOT NULL ,

  PRIMARY KEY (`idGuardian`, `idChild`) ,

  INDEX `idGuardian` (`idGuardian` ASC) ,

  INDEX `idChild` (`idChild` ASC) );

CREATE  TABLE `TestDB`.`Tags` (

  `idTags` INT NOT NULL ,

  `Caption` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`idTags`) );

CREATE  TABLE `TestDB`.`Media` (

  `idMedia` INT NOT NULL ,

  `Path` VARCHAR(45) NOT NULL ,

  `Name` VARCHAR(45) NOT NULL ,

  `Public` INT NOT NULL ,

  `Type` VARCHAR(45) NOT NULL ,

  `OwnerID` INT NOT NULL ,

  PRIMARY KEY (`idMedia`) ,

  INDEX `OwnerID` (`OwnerID` ASC) );

CREATE  TABLE `TestDB`.`HasTag` (

  `idMedia` INT NOT NULL ,

  `idTag` INT NOT NULL ,

  PRIMARY KEY (`idMedia`, `idTag`) ,

  INDEX `idMedia` (`idMedia` ASC) ,

  INDEX `idTag` (`idTag` ASC) );

CREATE  TABLE `TestDB`.`HasLink` (

  `idParrent` INT NOT NULL ,

  `idChild` INT NOT NULL ,

  PRIMARY KEY (`idParrent`, `idChild`) ,

  INDEX `idParrent` (`idParrent` ASC) ,

  INDEX `idChild` (`idChild` ASC) );

CREATE  TABLE `TestDB`.`HasMedia` (

  `idProfile` INT NOT NULL ,

  `idMedia` INT NOT NULL ,

  PRIMARY KEY (`idProfile`, `idMedia`) ,

  INDEX `idProfile` (`idProfile` ASC) ,

  INDEX `idMedia` (`idMedia` ASC) );




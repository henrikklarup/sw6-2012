CREATE  TABLE `test`.`Department` (

  `idDepartment` INT NOT NULL ,

  `Name` VARCHAR(45) NOT NULL ,

  `Address` VARCHAR(45) NOT NULL ,

  `Phone` INT NOT NULL ,

  `Email` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`idDepartment`) ,

  UNIQUE INDEX `idDepartment_UNIQUE` (`idDepartment` ASC) );

CREATE  TABLE `test`.`Media` (

  `idMedia` INT NOT NULL ,

  `Path` VARCHAR(45) NOT NULL ,

  `Name` VARCHAR(45) NOT NULL ,

  `Public` TINYINT NOT NULL ,

  `Tags` VARCHAR(45) NOT NULL ,

  `OwnerID` INT NOT NULL ,

  PRIMARY KEY (`idMedia`) ,

  UNIQUE INDEX `idMedia_UNIQUE` (`idMedia` ASC) ,
  INDEX `ProfileID` (`OwnerID` ASC) );

CREATE  TABLE `test`.`Profile` (

  `idProfile` INT NOT NULL ,

  `Name` VARCHAR(45) NOT NULL ,

  `Surname` VARCHAR(45) NOT NULL ,

  `Middlename` VARCHAR(45) NULL ,

  `Role` TINYINT NOT NULL ,

  `Phone` INT NOT NULL ,

  `DepartmentID` INT NOT NULL ,

  `AppID` INT NOT NULL ,

  PRIMARY KEY (`idProfile`) ,

  UNIQUE INDEX `idProfile_UNIQUE` (`idProfile` ASC) ,

  INDEX `DepartmentID` (`DepartmentID` ASC) );

CREATE  TABLE `test`.`Stats` (

  `idStats` INT NOT NULL ,

  `Stats` BLOB NULL ,

  `ProfileID` INT NOT NULL ,

  PRIMARY KEY (`idStats`) ,

  UNIQUE INDEX `idStats_UNIQUE` (`idStats` ASC) ,

  INDEX `ProfileID` (`ProfileID` ASC) );

CREATE  TABLE `test`.`Apps` (

  `idApps` INT NOT NULL ,

  `Name` VARCHAR(45) NOT NULL ,

  `StatsID` INT NOT NULL ,

  `SettingsID` INT NOT NULL ,

  `ProfileID` INT NOT NULL ,

  PRIMARY KEY (`idApps`) ,

  UNIQUE INDEX `idApps_UNIQUE` (`idApps` ASC) ,

  INDEX `StatsID` (`StatsID` ASC) ,

  INDEX `ProfileID` (`ProfileID` ASC) );

CREATE  TABLE `test`.`Settings` (

  `idSettings` INT NOT NULL ,

  `Settings` BLOB NULL ,

  `AppsID` INT NOT NULL ,

  PRIMARY KEY (`idSettings`) ,

  UNIQUE INDEX `idSettings_UNIQUE` (`idSettings` ASC) ,

  INDEX `AppsID` (`AppsID` ASC) );

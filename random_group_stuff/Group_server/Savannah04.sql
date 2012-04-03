drop table `04`.`Apps`;

drop table `04`.`AuthUsers`;

drop table `04`.`Department`;

drop table `04`.`HasDepartment`;

drop table `04`.`HasGuardian`;

drop table `04`.`HasLink`;

drop table `04`.`HasMedia`;

drop table `04`.`HasSubDepartment`;

drop table `04`.`HasTag`;

drop table `04`.`ListOfApps`;

drop table `04`.`Media`;

drop table `04`.`MediaDepartmentAccess`;

drop table `04`.`MediaProfileAccess`;

drop table `04`.`Profile`;

drop table `04`.`Tags`;



CREATE  TABLE `04`.`AuthUsers` (

  `idUser` INT NOT NULL ,

  `QRCode` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`idUser`) );

CREATE  TABLE `04`.`Apps` (

  `idApps` INT NOT NULL ,

  `Name` VARCHAR(45) NOT NULL ,

  `Version` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`idApps`) );

CREATE  TABLE `04`.`Tags` (

  `idTags` INT NOT NULL ,

  `Caption` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`idTags`) ,

  UNIQUE INDEX `Caption_UNIQUE` (`Caption` ASC) );

CREATE  TABLE `04`.`Profile` (

  `idProfile` INT NOT NULL ,

  `Firstname` VARCHAR(45) NOT NULL ,

  `Surname` VARCHAR(45) NOT NULL ,

  `Middlename` VARCHAR(45) NULL ,

  `PRole` INT NOT NULL ,

  `Phone` BIGINT NOT NULL ,

  `Picture` VARCHAR(45) NULL ,

  `Settings` BLOB NULL ,
  
  FOREIGN KEY (`idProfile` )

  REFERENCES `04`.`AuthUsers` (`idUser` ),

  PRIMARY KEY (`idProfile`) );

CREATE  TABLE `04`.`ListOfApps` (

  `idApps` INT NOT NULL ,

  `idProfile` INT NOT NULL ,
  
  FOREIGN KEY (`idApps` )

  REFERENCES `04`.`Apps` (`idApps` ),
  
  FOREIGN KEY (`idProfile` )

  REFERENCES `04`.`Profile` (`idProfile` ),

  PRIMARY KEY (`idApps`) );

CREATE  TABLE `04`.`Department` (

  `idDepartment` INT NOT NULL ,

  `Navn` VARCHAR(45) NOT NULL ,

  `Address` VARCHAR(45) NOT NULL ,

  `Phone` BIGINT NOT NULL ,

  `Email` VARCHAR(45) NOT NULL ,
  
  FOREIGN KEY (`idDepartment` )

  REFERENCES `04`.`AuthUsers` (`idUser` ),

  PRIMARY KEY (`idDepartment`, `Navn`) );

CREATE  TABLE `04`.`HasDepartment` (

  `idProfile` INT NOT NULL ,

  `idDepartment` INT NOT NULL ,
  
  FOREIGN KEY (`idProfile` )

  REFERENCES `04`.`Profile` (`idProfile` ),

  FOREIGN KEY (`idDepartment` )

  REFERENCES `04`.`Department` (`idDepartment` ),


  PRIMARY KEY (`idProfile`) );

CREATE  TABLE `04`.`HasGuardian` (

  `idGuardian` INT NOT NULL ,

  `idChild` INT NOT NULL ,
  
  FOREIGN KEY (`idGuardian` )

  REFERENCES `04`.`Profile` (`idProfile` ),
  
  FOREIGN KEY (`idChild` )

  REFERENCES `04`.`Profile` (`idProfile` ),

  PRIMARY KEY (`idGuardian`) );

CREATE  TABLE `04`.`HasSubDepartment` (

  `idDepartment` INT NOT NULL ,

  `idSubDepartment` INT NOT NULL ,
  
  FOREIGN KEY (`idDepartment` )

  REFERENCES `04`.`Department` (`idDepartment` ),
  
  FOREIGN KEY (`idSubDepartment` )

  REFERENCES `04`.`Department` (`idDepartment` ),

  PRIMARY KEY (`idDepartment`, `idSubDepartment`) );

CREATE  TABLE `04`.`Media` (

  `idMedia` INT NOT NULL ,

  `MPath` VARCHAR(45) NOT NULL ,

  `Name` VARCHAR(45) NOT NULL ,

  `MPublic` TINYINT NOT NULL ,

  `Type` VARCHAR(45) NOT NULL ,

  `OwnerID` INT NOT NULL ,
  
  FOREIGN KEY (`OwnerID` )

  REFERENCES `04`.`Profile` (`idProfile` ),

  PRIMARY KEY (`idMedia`) );

CREATE  TABLE `04`.`HasTag` (

  `idMedia` INT NOT NULL ,

  `idTag` INT NOT NULL ,
  
  FOREIGN KEY (`idMedia` )

  REFERENCES `04`.`Media` (`idMedia` ),
  
  FOREIGN KEY (`idTag` )

  REFERENCES `04`.`Tags` (`idTags` ),

  PRIMARY KEY (`idMedia`, `idTag`) );

CREATE  TABLE `04`.`HasLink` (

  `idParent` INT NOT NULL ,

  `idChild` INT NOT NULL ,
  
  FOREIGN KEY (`idParent` )

  REFERENCES `04`.`Media` (`idMedia` ),
  
  FOREIGN KEY (`idChild` )

  REFERENCES `04`.`Media` (`idMedia` ),

  PRIMARY KEY (`idParent`, `idChild`) );

CREATE  TABLE `04`.`MediaProfileAccess` (

  `idProfile` INT NOT NULL ,

  `idMedia` INT NOT NULL ,
  
  FOREIGN KEY (`idProfile` )

  REFERENCES `04`.`Profile` (`idProfile` ),
  
  FOREIGN KEY (`idMedia` )

  REFERENCES `04`.`Media` (`idMedia` ),

  PRIMARY KEY (`idProfile`, `idMedia`) );

CREATE  TABLE `04`.`MediaDepartmentAccess` (

  `idDepartment` INT NOT NULL ,

  `idMedia` INT NOT NULL ,
  
  FOREIGN KEY (`idDepartment` )

  REFERENCES `04`.`Department` (`idDepartment` ),
  
  FOREIGN KEY (`idMedia` )

  REFERENCES `04`.`Media` (`idMedia` ),

  PRIMARY KEY (`idDepartment`, `idMedia`) );



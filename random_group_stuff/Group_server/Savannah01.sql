
drop table `test`.`listofapps`;

drop table `test`.`media`;

drop table `test`.`apps`;

drop table `test`.`profile`;

drop table `test`.`department`;


CREATE  TABLE `test`.`Department` (

  `idDepartment` INT NOT NULL ,

  `Name` VARCHAR(45) NOT NULL ,

  `Address` VARCHAR(45) NOT NULL ,

  `Phone` INT NOT NULL ,

  `Email` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`idDepartment`) ,

  UNIQUE INDEX `idDepartment_UNIQUE` (`idDepartment` ASC) );


CREATE  TABLE `test`.`Profile` (

  `idProfile` INT NOT NULL ,

  `Name` VARCHAR(45) NOT NULL ,

  `Surname` VARCHAR(45) NOT NULL ,

  `Middlename` VARCHAR(45) NULL ,

  `Role` INT NOT NULL ,

  `Phone` INT NOT NULL ,

  `Picture` VARCHAR(45) NULL ,

  `DepartmentID` INT NOT NULL ,

  FOREIGN KEY (`DepartmentID` )  REFERENCES `test`.`department` (`idDepartment` ),

  PRIMARY KEY (`idProfile`) ,

  INDEX `DepartmentID` (`DepartmentID` ASC) );
  

CREATE  TABLE `test`.`Apps` (

  `idApps` INT NOT NULL ,

  `Name` VARCHAR(45) NOT NULL ,

  `Version` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`idApps`) );

CREATE  TABLE `test`.`ListOfApps` (

  `AppID` INT NULL ,
    FOREIGN KEY (`AppID` )  REFERENCES `test`.`apps` (`idApps` ),

  `ProfileID` INT NOT NULL ,
   FOREIGN KEY (`ProfileID` )  REFERENCES `test`.`profile` (`idProfile` ),

  `Settings` BLOB NULL ,

  `Stats` BLOB NULL ,

  INDEX `AppID` (`AppID` ASC) ,

  INDEX `ProfileID` (`ProfileID` ASC) );

CREATE  TABLE `test`.`Media` (

  `idMedia` INT NOT NULL ,

  `Path` VARCHAR(45) NOT NULL ,

  `Name` VARCHAR(45) NOT NULL ,

  `Public` TINYINT NOT NULL ,

  `Tags` VARCHAR(45) NOT NULL ,

  `OwnerID` INT NOT NULL ,
  
  FOREIGN KEY (`OwnerID` )  REFERENCES `test`.`profile` (`idProfile` ),

  PRIMARY KEY (`idMedia`) ,

  INDEX `OwerID` (`OwnerID` ASC) );


insert into Department values (1,'Uni','Selma',12345678,'dr@dr.dk');
insert into Department values (2,'Skole','Vejgaard',87654321,'skole@vej.dk');

insert into Profile values (1,'Jesper','Bromose',null,1,12345678,null,1);
insert into Profile values (2,'Sebste','trololo',null,1,33333333,null,2);

insert into Media values (1,'http://www.dr.dk','Dr.DK',1,'<url>,<dr>,<dk>',1);
insert into Media values (2,'http://www.eb.dk','eb.DK',1,'<url>,<eb>,<dk>',2);

insert into apps values (1,'TestApp1',1);
insert into apps values (2,'TestApp2',1);
insert into apps values (3,'TestApp3',1);

insert into ListOfApps values (null,1,null,null);

insert into ListOfApps values(1,1,'stuff','stuff');
insert into ListOfApps values(3,1,'stuff','stuff');

insert into ListOfApps values(2,2,'stuff','stuff');



select Name from Apps
    where idApps IN (select AppID from ListOfApps where ProfileID =1);
    
select Name from Apps
    where idApps IN (select AppID from ListOfApps where ProfileID =2);

select Settings from ListOfApps
    where AppID = 1 and ProfileID =1;
    
select Settings from ListOfApps
    where AppID = 2 and ProfileID =2;
    
select Stats from ListOfApps
    where AppID = 1 and ProfileID =1;
    
select Stats from ListOfApps
    where AppID = 2 and ProfileID =2;
    
select Path, Name, Tags from Media
    where OwnerID = 1;
    
select Path, Name, Tags from Media
    where OwnerID = 2;
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

  `certificate` VARCHAR(45) NOT NULL ,

  `idUser` INT NOT NULL AUTO_INCREMENT ,
  
  `type` INT NOT NULL,
    
  `username` VARCHAR(45) UNIQUE NOT NULL,
  
  `password` VARCHAR(45) NOT NULL,

  PRIMARY KEY (`certificate`) ,

  UNIQUE INDEX `idUser_UNIQUE` (`idUser` ASC) );


CREATE  TABLE `04`.`Apps` (

  `idApp` INT NOT NULL AUTO_INCREMENT,

  `name` VARCHAR(45) NOT NULL ,

  `version` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`idApp`) );

CREATE  TABLE `04`.`Tags` (

  `idTags` INT NOT NULL AUTO_INCREMENT,

  `caption` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`idTags`) ,

  UNIQUE INDEX `caption_UNIQUE` (`caption` ASC) );

CREATE  TABLE `04`.`Profile` (

  `idProfile` INT NOT NULL ,

  `firstname` VARCHAR(45) NOT NULL ,

  `surname` VARCHAR(45) NOT NULL ,

  `middlename` VARCHAR(45) NULL ,

  `pRole` INT NOT NULL ,

  `phone` BIGINT NOT NULL ,

  `picture` VARCHAR(45) NULL ,

  `settings` BLOB NULL ,
  
  FOREIGN KEY (`idProfile` )

  REFERENCES `04`.`AuthUsers` (`idUser` ),

  PRIMARY KEY (`idProfile`) );

CREATE  TABLE `04`.`ListOfApps` (

  `idApp` INT NOT NULL ,

  `idProfile` INT NOT NULL ,
  
  FOREIGN KEY (`idApp` )

  REFERENCES `04`.`Apps` (`idApp` ),
  
  FOREIGN KEY (`idProfile` )

  REFERENCES `04`.`Profile` (`idProfile` ),

  PRIMARY KEY (`idApp`,`idProfile`) );

CREATE  TABLE `04`.`Department` (

  `idDepartment` INT NOT NULL ,

  `name` VARCHAR(45) NOT NULL ,

  `address` VARCHAR(45) NOT NULL ,

  `phone` BIGINT NOT NULL ,

  `email` VARCHAR(45) NOT NULL ,
  
  FOREIGN KEY (`idDepartment` )

  REFERENCES `04`.`AuthUsers` (`idUser` ),

  PRIMARY KEY (`idDepartment`) );

CREATE  TABLE `04`.`HasDepartment` (

  `idProfile` INT NOT NULL ,

  `idDepartment` INT NOT NULL ,
  
  FOREIGN KEY (`idProfile` )

  REFERENCES `04`.`Profile` (`idProfile` ),

  FOREIGN KEY (`idDepartment` )

  REFERENCES `04`.`Department` (`idDepartment` ),


  PRIMARY KEY (`idProfile`, `idDepartment`) );

CREATE  TABLE `04`.`HasGuardian` (

  `idGuardian` INT NOT NULL ,

  `idChild` INT NOT NULL ,
  
  FOREIGN KEY (`idGuardian` )

  REFERENCES `04`.`Profile` (`idProfile` ),
  
  FOREIGN KEY (`idChild` )

  REFERENCES `04`.`Profile` (`idProfile` ),

  PRIMARY KEY (`idGuardian`,`idChild`) );

CREATE  TABLE `04`.`HasSubDepartment` (

  `idDepartment` INT NOT NULL ,

  `idSubDepartment` INT NOT NULL ,
  
  FOREIGN KEY (`idDepartment` )

  REFERENCES `04`.`Department` (`idDepartment` ),
  
  FOREIGN KEY (`idSubDepartment` )

  REFERENCES `04`.`Department` (`idDepartment` ),

  PRIMARY KEY (`idDepartment`, `idSubDepartment`) );

CREATE  TABLE `04`.`Media` (

  `idMedia` INT NOT NULL AUTO_INCREMENT,

  `mPath` VARCHAR(45) NOT NULL ,

  `name` VARCHAR(45) NOT NULL ,

  `mPublic` TINYINT NOT NULL ,

  `mType` VARCHAR(45) NOT NULL ,

  `ownerID` INT NOT NULL ,
  
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


insert into AuthUsers values ('abekat',null,0);
insert into AuthUsers values ('abekat2',null,0);
insert into AuthUsers values ('Dep:111',null,1);
insert into AuthUsers values ('Pæd:',null,1);
insert into AuthUsers values ('Gaurd',null,0);
insert into AuthUsers values ('Gaurd2',null,0);
insert into Apps values(1,'TestAppe1','1.0.0');
insert into Apps values(2,'TestAppe2','1.0.0');
insert into Tags values(1,'Bil');
insert into Profile values(1,'Jesper','Bromose',null,1,12345678,'dr.dk','<XML>STEERINGS</XML>','Jesper','tester1');
insert into Profile values(5,'Foo','Bar',null,1,12212121,'dr.dk','<XML>STEERINGS</XML>','pæd','123');
insert into Profile values(7,'Guardian1','Guardian2',null,1,12212121,'dr.dk','<XML>STEERINGS</XML>','G','1');
insert into Profile values(8,'TOKE','TOKEN',null,1,12212121,'dr.dk','<XML>STEERINGS</XML>','Toke','loke');
insert into ListOfApps values(1,1);
insert into ListOfApps values(2,1);
insert into Media values(1,'c:\test','MyTest',1,'public',1);
insert into HasTag values(1,1);
insert into MediaProfileAccess values(1,1);
insert into Department values (3,'Skolebakken','Vejlevej 1',12345678,'vejle@dr.dk');
insert into HasDepartment values (1,3);
insert into HasDepartment values (5,3);
insert into HasGuardian values (7,1);
insert into HasGuardian values (8,1);

select * from HasGuardian;

insert into HasGuardian values(1,5);

select * from Profile;

select password from Profile where idProfile = 7;
select idDepartment, name from Department
    where idDepartment in (select idDepartment from HasDepartment
                    where idProfile =5);

select type from AuthUsers where idUser = 5;

select * from Profile
							where idProfile in (select idGuardian from HasGuardian
							where idChild = 5);

UPDATE `04`.`Profile` SET `firstname`='+newFirstname+'  WHERE `idProfile`=1;

select firstname from Profile
where idProfile in (select idGuardian from HasGuardian
    where idChild = 1);

select * from Profile;
SELECT password from AuthUsers where username = 'Jesper';

select * from AuthUsers; 

SELECT firstname, middlename, surname from Profile where idProfile = 1;

select * from Apps
    where 1 in (select idProfile from ListOfApps);
    
select idProfile, firstname from Profile
    where 'abekat' in (select certificate from AuthUsers);
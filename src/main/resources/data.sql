

insert into categories( name)
values ('kat'),('hond');
insert into pets(name,categoryid,photourls,status)
values
       ('Miro',(select id from categories where name ='kat'),'https://placekitten.com/g/200/300','SOLD'),
       ('Dali',(select id from categories where name ='kat'),'https://placekitten.com/g/200/300','AVAILABLE'),
       ('Booster',(select id from categories where name ='hond'),'https://placekitten.com/g/200/300','PENDING');


insert into tags(petid,id, name)
values
       ((select id from pets where name ='Miro'),1,'Grayfox'),
       ((select id from pets where name ='Dali'),2,'Snake'),
       ((select id from pets where name ='Booster'),3,'Wolf');
insert into categories( name)
values ('testCat1'),('testCat2');
insert into pets(name,categoryid,photourls,status)
values
    ('test1',(select id from categories where name ='testCat1'),'https://placekitten.com/g/200/300','SOLD'),
    ('test2',(select id from categories where name ='testCat1'),'https://placekitten.com/g/200/300','AVAILABLE'),
    ('test3',(select id from categories where name ='testCat2'),'https://placekitten.com/g/200/300','PENDING');


insert into tags(petid,id, name)
values
    ((select id from pets where name ='test1'),4,'testTag1'),
    ((select id from pets where name ='test2'),5,'testTag2'),
    ((select id from pets where name ='test3'),6,'testTag3');
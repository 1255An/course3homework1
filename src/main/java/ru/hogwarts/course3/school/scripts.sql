select * from student;

select * from student where age >= 10
                        and age <= 20;

select s.name from student as s;

select * from student where name like '%i%';

select * from student where age < id*10;

select * from student order by age;
declare
    type names_array is varray(24) of varchar2(10);
    array names_array := names_array('Matt', 'Joanne', 'Robert', 'Tom', 'David', 'John', 'Rafael', 'Jack', 'Paul', 'Jan', 'Ann', 'Christina', 'Lee', 'Stalone', 'Rotten', 'Matlok', 'Cook', 'Sigal', 'McLaren', 'Freeman', 'Biggs', 'Strong', 'Vicious', 'Star');
    random_firstname_index pls_integer;
    random_lastname_index pls_integer;
begin
    for i in 1..1000000 loop
        random_firstname_index := dbms_random.value(1,12);
        random_lastname_index := dbms_random.value(13,24);
        --dbms_output.put_line(array(random_firstname_index) || ' ' || array(random_lastname_index));
        insert into "DEVELOPER"."USERS" (FIRST_NAME, LAST_NAME) values (array(random_firstname_index), array(random_lastname_index));
    end loop;
end;
commit;
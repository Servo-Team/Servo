-- Get User with ID of 1(Each diffrent)
SELECT * FROM USERS
WHERE ID = 1;

-- Get user from username/pass
SELECT * FROM USERS
WHERE USERNAME = 'amanuel2'
AND   PASSWORD = 'passwd';

--Check uniqueness of username
SELECT * FROM USERS
WHERE USERNAME = 'amanuel2'
--Then handle in java code

--Get specific binary avatar
SELECT AVATAR
FROM USERS
WHERE USERNAME='amanuel2'
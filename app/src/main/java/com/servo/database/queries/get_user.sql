-- Get User with ID of 1(Each diffrent)
SELECT * FROM USERS
WHERE ID = 1;

-- Get user from username/pass
SELECT * FROM USERS
WHERE USERNAME = 'amanuel2'
AND   PASSWORD = 'passwd';
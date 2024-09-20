post http://localhost:8080/api/user/super

get http://localhost:8080/api/users
get http://localhost:8080/api/user/super

put http://localhost:8080/api/user/tester/newname

delete http://localhost:8080/api/user/tester

1 to 1:
post http://localhost:8080/api/students

1 to M:
post http://localhost:8080/api/posts

M to M:
post http://localhost:8080/api/courses

transaction
post http://localhost:8080/api/transaction

docker => mongo:latest
port:27017
## Проверка подключения к серверу с клиентским сертификатом
## Как использовать
```sh
javac TestJKSConnection.java
java TestJKSConnection /path/to/client.jks password https://host/link
```
Мы увидим ошибку или response code 400. Либо  другой http response code в случае успеха (403, 404, 200)

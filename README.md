## Проверка подключения к серверу с клиентским сертификатом
## Как использовать
```sh
javac TestJKSConnection.java
java TestJKSConnection /path/to/client.jks password https://host/link
```
Мы увидим либо ошибку либо http response code

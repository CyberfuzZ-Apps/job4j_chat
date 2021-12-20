# Job4j_chat

[![Java CI with Maven](https://github.com/CyberfuzZ-Apps/job4j_chat/actions/workflows/maven.yml/badge.svg)](https://github.com/CyberfuzZ-Apps/job4j_chat/actions/workflows/maven.yml)

## О проекте:

Job4j_chat - REST API для приложения "чат с комнатами".

Использованы технологии:

- Spring Boot 2:
    - Spring Web MVC
    - Spring Data JPA
    - Spring Security / JWT authorization
    - Spring Validator
- DTO (Data Transport Object)
- Java 11
- PostgreSQL
- Maven
- Tomcat

## Подробнее:

### REST API

### для всех пользователей:

<table>
  <thead>
  <th>Команда</th>
    <th>Запрос</th>
    <th>Ответ</th>
    </thead>
  <tbody>
    <tr>
      <td>Регистрация</td>
      <td> 
		POST https://sitename/person/sign-up<br>
        Body:<br>
        {<br>
        "nickname": "User", <br>
        "username": "user@local", <br>
        "password": "123"<br>
        } </td>
      <td> Код состояния: 201<br>
        Body:<br>
        {<br>
        "id": 1,<br>
        "nickname": "User",<br>
        "username": "user@local",<br>
        "password": "$2a$10$V4...",<br>
        "authority": "ROLE_USER"<br>
        } </td>
    </tr>
    <tr>
      <td>Авторизация</td>
      <td>
		POST https://sitename/login<br>
        Body: <br>
		  {<br>
		  "username": "user@local" , <br>
		  "password": "123"<br>
		  }<br>
		</td>
      <td>Код состояния: 200 OR 403<br>
        Header: "Authorization: Bearer eyJ0eXAiO..."</td>
    </tr>
  </tbody>
</table>

### для авторизованного пользователя

#### rooms

<table>
  <thead>
  <th>Команда</th>
    <th>Запрос</th>
    <th>Ответ</th>
    </thead>
  <tbody>
    <tr>
      <td>Получить список всех комнат</td>
      <td>GET https://sitename/room/<br>
        Header: "Authorization: Bearer eyJ0eXAiO..."</td>
      <td>Код состояния: 200<br>
        Header: "Content-Type: application/json"<br>
        Body:<br>
        [
        {<br>
        "id": 1,<br>
        "name": "First room",<br>
        "personId": 5<br>
        },<br>
        {<br>
        "id": 2,<br>
        "name": "Second room",<br>
        "personId": 5<br>
        }, ... 
        ]<br>
      </td>
    </tr>
    <tr>
      <td>Получить комнату по id</td>
      <td>GET https://sitename/room/1<br>
        Header: "Authorization: Bearer eyJ0eXAiO..."</td>
      <td>Код состояния: 200 (или 404 NOT FOUND)<br>
        Header: "Content-Type: application/json"<br>
        Body: <br>
        {<br>
        "id": 1,<br>
        "name": "First room",<br>
        "personId": 5<br>
        } <br>
      </td>
    </tr>
    <tr>
      <td>Создать новую комнату</td>
      <td>POST https://sitename/room/<br>
        Header: "Authorization: Bearer eyJ0eXAiO..."<br>
        "Content-Type: application/json",<br>
        Body: <br>
        { <br>
        "name": "Another room",<br>
        "personId": 5<br>
        } <br>
      </td>
      <td>Код состояния: 201<br>
        Header: "Content-Type: application/json"<br>
        Body: <br>
        {<br>
        "id": 11,<br>
        "name": "Another room",<br>
        "personId": 5<br>
        } <br>
      </td>
    </tr>
    <tr>
      <td>Обновить комнату (все поля)</td>
      <td>PUT https://sitename/room/11<br>
        Header: "Authorization: Bearer eyJ0eXAiO..."<br>
        "Content-Type: application/json", <br>
        Body: <br>
        { <br>
        "name": "Updated room",<br>
        "personId": 5<br>
        }<br>
      </td>
      <td>Код состояния: 200<br>
        Header: "Content-Type: application/json"<br>
        Body: <br>
        {<br>
        "id": 11,<br>
        "name": "Updated room",<br>
        "personId": 5<br>
        }<br>
      </td>
    </tr>
    <tr>
      <td>Обновить комнату (частично)</td>
      <td>PATCH https://sitename/room/11<br>
        Header: "Authorization: Bearer eyJ0eXAiO..."<br>
        "Content-Type: application/json",<br>
        Body: <br>
        { <br>
        "name": "Patched room"<br>
        } <br>
      </td>
      <td>Код состояния: 200<br>
        Header: "Content-Type: application/json"<br>
        Body:<br>
        {<br>
        "id": 11,<br>
        "name": "Patched room",<br>
        "personId": 5<br>
        } <br>
      </td>
    </tr>
    <tr>
      <td>Удалить комнату по id</td>
      <td>DELETE https://sitename/room/11<br>
        Header: "Authorization: Bearer eyJ0eXAiO..."</td>
      <td>Код состояния: 200 (или 404 NOT FOUND)</td>
    </tr>
  </tbody>
</table>

#### messages

<table>
  <thead>
  <th>Команда</th>
    <th>Запрос</th>
    <th>Ответ</th>
    </thead>
  <tbody>
    <tr>
      <td>Получить все сообщения из выбранной комнаты</td>
      <td>GET https://sitename/room/1/message/<br>
        Header: "Authorization: Bearer eyJ0eXAiOiJKV1Q..."</td>
      <td>Код состояния: 200<br>
        Header: "Content-Type: application/json"<br>
        Body: <br>
        [
        {<br>
        "id": 1,<br>
        "message": "Message 1",<br>
        "created": "2021-12-16T10:28:41.000+00:00",<br>
        "personId": 5,<br>
        "roomId": 1<br>
        },<br>
        {<br>
        "id": 2,<br>
        "message": "MESSAGE 2",<br>
        "created": "2021-12-16T10:33:18.000+00:00",<br>
        "personId": 5,<br>
        "roomId": 1<br>
        }, ...
        ] <br>
      </td>
    </tr>
    <tr>
      <td>Получить сообщение из выбранной комнаты по id-сообщения</td>
      <td>GET https://sitename/room/1/message/1<br>
        Header: "Authorization: Bearer eyJ0eXAiOiJKV1Q..."</td>
      <td>Код состояния: 200 (или 404 NOT FOUND)<br>
        Header: "Content-Type: application/json"<br>
        Body: <br>
        {<br>
        "id": 1,<br>
        "message": "Message 1",<br>
        "created": "2021-12-16T10:28:41.000+00:00",<br>
        "personId": 5,<br>
        "roomId": 1<br>
        }
      </td>
    </tr>
    <tr>
      <td>Создать новое сообщение</td>
      <td>POST https://sitename/room/1/message/<br>
        Header: "Authorization: Bearer eyJ0eXAiOiJKV1Q...,<br>
        "Content-Type: application/json",<br>
        Body: <br>
        { <br>
        "message": "New message",<br>
        "personId": 5,<br>
        "roomId": 1<br>
        } </td>
      <td>Код состояния: 201<br>
        Header: "Content-Type: application/json"<br>
        Body: <br>
        {<br>
        "id": 8,<br>
        "message": "New message",<br>
        "created": "2021-12-20T14:37:27.293+00:00",<br>
        "personId": 5,<br>
        "roomId": 1<br>
        } </td>
    </tr>
    <tr>
      <td>Обновить сообщение (все поля)</td>
      <td>PUT https://sitename/room/1/message/8<br>
        Header: "Authorization: Bearer eyJ0eXAiOiJKV1Q...,<br>
        "Content-Type: application/json",<br>
        Body: <br>
        { <br>
        "message": "Updated message",<br>
        "personId": 5,<br>
        "roomId": 1<br>
        } </td>
      <td>Код состояния: 200<br>
        Header: "Content-Type: application/json"<br>
        Body: <br>
        {<br>
        "id": 8,<br>
        "message": "Updated message",<br>
        "created": "2021-12-20T14:37:27.293+00:00",<br>
        "personId": 5,<br>
        "roomId": 1<br>
        } </td>
    </tr>
    <tr>
      <td>Обновить сообщение (частично)</td>
      <td>PATCH https://sitename/room/1/message/8<br>
        Header: "Authorization: Bearer eyJ0eXAiOiJKV1Q...,<br>
        "Content-Type: application/json",<br>
        Body: <br>
        { <br>
        "message": "Patched message"<br>
        } </td>
      <td>Код состояния: 200<br>
        Header: "Content-Type: application/json"<br>
        Body: <br>
        {<br>
        "id": 8,<br>
        "message": "Patched message",<br>
        "created": "2021-12-20T14:37:27.293+00:00",<br>
        "personId": 5,<br>
        "roomId": 1<br>
        } </td>
    </tr>
    <tr>
      <td>Удалить сообщение по id</td>
      <td>DELETE https://sitename/room/1/message/8<br>
        Header: "Authorization: Bearer eyJ0eXAiOiJKV1Q...</td>
      <td>Код состояния: 200 (или 404 NOT FOUND)</td>
    </tr>
  </tbody>
</table>

## Контакты:

Если у вас есть какие-либо вопросы, не стесняйтесь обращаться ко мне:

Евгений Зайцев

[cyberfuzzapps@gmail.com](mailto:cyberfuzzapps@gmail.com)
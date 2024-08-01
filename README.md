# Глобальные изменения

Если будем добалять пуши об изменении в расписании, то пользователь сам сможет подписываться на топики, которые ему нужны с помощью firebase messaging.

### Базовая структура таблицы users

| id            | external_user_id | access_token | refresh_token | additional_data | profile_info |
|---------------|------------------|--------------|---------------|-----------------|--------------|
| autoincrement | not null         | not null     | not null      |                 |              |
| integer       | string           | string       | string        | json            | json         |

`additional_data` - Содержит техническую информацию о пользователе (токены и прочее)<br>
`profile_info` - Содержит варианты, имя, группу и т.д
`external_user_id` - Сторонний неизменный идентификатор пользователя из VK, Google, Telegram, при котором можно ассоциировать пользователя при повторном входе

# Сторонняя авторизация (GOOGLE | VK)

#### POST
`account/auth`

> Payload
```
{
  "authMethod": "VK | GOOGLE | TG",
  "idToken": "все что угодно",
  "fullName": "OLEG ALO" | null,
  "avatarUrl": "uri" | null
}
```

*idToken* - токен из апи авторизации VK или Google подтверждающий подленность отправленных данных.<br>
С помощью апи от Google и VK ты подтверждаешь токен у себя и если он правильный, то возвращаешь response иначе 403<br>

*fullName* и avatarUrl могут быть null при типе авторизации TG, так как их может получить только сервер.

> Response
```
{
  "accessToken": "String",
  "refreshToken": "String"
}
```

Респонс был заметно сокращен, так как никакие данные кроме refresh и access token клиенту не нужны.

# Сторонняя авторизация (TELEGRAM)

Приложение стартует телеграм бота с параметром `start?=idToken...`

Сервер обрабатывает запрос, добавляя в таблицу auth_codes запись с id пользователя telegram (@injent или какой там формат id).<br>
Запись будет хранится на протяжении 5 минут.<br>
Далее с клиента поступит запрос:

#### POST
`account/auth`

> Payload
```
{
  "authMethod": "TG",
  "idToken": "все что угодно"
}
```

*authCode* из того самого диплинка на бота `start?=idToken...`

Если idToken совпадает с таблицей, то по telegram id из нее получаешь имя пользователя и аватарку, отправляешь мне и удаляешь запись.

> Response
```
{
  "accessToken": "String",
  "refreshToken": "String"
}
```

> # Важно отметить
Обязанность бэкенда сохранить  и avatarUrl в БД самостоятельно после подтверждения кода авторизации

# Подтверждение кода авторизации
К сожалению у нас не получится сделать общий способ подтверждения авторизации для всех поэтому тебе придется следовать гайдам подтверждения авторизации:

### [Google](https://developers.google.com/identity/gsi/web/guides/verify-google-id-token?hl=ru#%D0%BF%D0%B8%D1%82%D0%BE%D0%BD)

### VK

#### POST
`https://id.vk.com/oauth2/public_info`<br>

> Payload - x-www-form-urlencoded
```
client_id = 51989581
id_token = тот, который пришлет клиент
```

> Response - 200 OK
```
{
    "user": {
        "user_id": "492110993",
        "first_name": "Елисей",
        "last_name": "В.",
        "phone": "+7 *** *** ** 54",
        "avatar": "https://sun126-1.userapi.com/s/v1/ig2/I0rD4tt1gPhipJT-lGDMFCSDJLVY_-cy7XkAkf0kkiWUbDCzZrcAc1yo8AaBGMSbQKuCXTx2nAcXz5kOE2nGrMG-.jpg?size=50x50&quality=95&crop=42,114,525,525&ava=1",
        "email": "el***@gmail.com"
    }
}
```

> Error Response - 200 OK
```
{
    "error": "invalid_request",
    "error_description": "id_token is missing or invalid"
}
```

К сожалению в вк даже на ошибки отправляют 200 OK так что, если токен не подошел, то нужно при наличии поля error в json отправить 403 ошибку.

# Получение данных профиля пользователя

#### GET
Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...<br>
`account/profile?userId=31`<br>

> Response
```
{
  "id": 31,
  "bio": "Да это я решаю",
  "fullName": "Алеков Алек Алекович или студент 1 При-101",
  "role": "STUDENT | DEV | AWARDED",
  "contacts": {
    "tg": "t.me/oleg",
    "vk": "vk.com/oleg"
  },
  "variants": [
    {
      "subjectName": "Физика",
      "variant": 5
    },
    {
      "subjectName": "Математика",
      "variant": 5
    },
    {
      "subjectName": "Веб",
      "variant": 6
    }
  ]
}
```

# Роут с версиями для авторизованных пользователей

#### GET
Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...<br>
`account/dataVersions`<br>

> Response
```
{
  "scheduleVersion": 0,
  "profileVersion": 0
}
```

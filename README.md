
# Токены
Генерация токена с помощью HS256 (HMAC with SHA-256)
Длительность жизни: 1 месяц отсчитывая от времени запроса
#### Поля используемые в базовой авторизации:
- accessToken - сам токен
- refreshToken - токен для обновления доступа к API
- expirationDate - дата в формате "2024-01-31" истечения срока действия токена

При запросах ко всем методам используется -H Authorization: "Bearer {accessToken}" получаемый с клиента чтобы идентифицировать пользователя по userId
#### Исключения
- `/account/registerGuest`
- `/account/auth`

# Гостевая авторизация
<b>POST</b> `/account/registerGuest`
#### Payload:
```json
{
  "appId": "SHA256",
  "groupId": 0
}
```
#### Response:
```json
{
  "userId": 0,
  "groupId": 0,
  "groupName": "ПрИ-101",
  "credentials": {
    "accessToken": "",
    "refreshToken": "",
    "expirationDate": "2024-02-12"
  }
}
```
#### Логика:
Существует БД с пользователями. Требуется создать нового пользователя в БД с неповторяющимися *userId*. Необходимо создать запись, присвоить уникальный userId: integer, присвоить groupId из Payload, создать токен (см. `Токены`).

Далее отослать сгенерированные данные и groupName из БД по groupId

appId - создан для того, чтобы пользователи не плодили анонимные аккаунты. Для каждой инстанции приложения свой appId. 
Если с одного и того же appId поступит запрос на создание записи, то нужно просто заменить прошлую с таким же appId и обновить токены в ней

# Подтверждение аккаунта
<b>POST</b> `/account/verify`
#### Payload
```json
{
    "userId": 0,
    "eosUserId": 0,
    "fullName": "Фамилия Имя Отчество",
    "avatarUrl": "https://url",
    "credentialsHash": "SHA256"
}
```
#### Response
```json
{
    "userId": 0,
    "eosUserId": 0,
    "groupId": 0,
    "groupName": "ПРИ-101",
    "fullName": "Фамилия Имя Отчество",
    "avatarUrl": "https://url",
    "role": "STUDENT",
    "permissions": ["ADD_HOMEWORK"],
    "data": {}
}
```

#### Логика
*role* - поле получаемое из атрибута таблицы users
*permissions* - поле получаемое из атрибута таблицы users. На данный момент хранится в виде `ADD_HOMEWORK;OTHER_VALUE;...`
*data* - поле получаемое из атрибута таблицы users. Используется клиентом для хранения любых данных пользователя в формате json, не требует обработки со стороны сервера.

При подтверждении аккаунты у пользователя нет данных о его *fullName*, *avatarUrl*, *role*, *permissions*. *fullName* и *avatarUrl* приходит с клиента, остальное создает сервер по схеме, когда происходит первое использовании метода `/account/verify`

*role* = `STUDENT` (DEFAULT)
*permissions* = `ADD_HOMEWORK` (DEFAULT)
Далее присвоить значение атрибуту `is_verified` значение true
Далее к credentialsHash добавить не рандомный keyword, снова захэшировать и добавить в атрибут credentials_hash

Выбрасывать ошибку `403` с body:
```json
{
  "details": "Вы уже подтвердили свой аккаунт"
}
```

# Получение данных о подтвержденном пользователе
**GET** `/account`
#### Response
```json
{
    "userId": 0,
    "eosUserId": 0,
    "groupId": 0,
    "groupName": "ПРИ-101",
    "fullName": "Фамилия Имя Отчество",
    "avatarUrl": "https://",
    "role": "STUDENT",
    "permissions": ["ADD_HOMEWORK"],
    "data": {}
}
```

#### Логика

По Authorization хэдеру определить пользователя и отослать данные если его токен не истек

Если у пользователя атрибут `is_verified` стоит на false бросить ошибку `404` body:
```json
{
  "details": "Вы не являетесь подтвержденным пользователем"
}
```

# Авторизация подтвержденного пользователя
**POST** `account/auth`
#### Payload
```json
{
    "eosUserId": 0,
    "credentialsHash": "SHA256"
}
```
#### Response
```json
{
  "userId": 0,
  "eosUserId": 0,
  "groupId": 0,
  "credentials": {
    "accessToken": "",
    "refreshToken": "",
    "expirationDate": "2024-02-12"
  }
}
```

#### Логика
Найти по *credentialsHash* + не рандомный keyword (все хэшировано) и eosUserId запись в таблице users.

**Cases**
- Если найдено: обновить все токены (*accessToken*, *refreshToken*) у user, отправить **Response**

- Если не найдено, но *eosUserId* совпадает, отправить ошибку `401` body:
```json
{
  "details": "Пользователь найден, но пароли не совпадают"
}
```

- Eсли записи не найдено вообще, то делать следующее:
Отправить ошибку `100`
Далее я обработаю ее как просьбу ввести больше данных и выполню запрос на регистрацию эосовца

# Регистрация пользователя ЭОС
**POST** `/account/registerEosUser`
#### Payload
```json
{
    "eosUserId": 0,
    "eosGroupName": "ПрИ-101",
    "fullName": "Фамилия Имя Отчество",
    "avatarUrl": "https://url",
    "credentialsHash": "SHA256"
}
```
#### Response
```json
{
  "userId": 0,
  "eosUserId": 0,
  "groupId": 0,
  "credentials": {
    "accessToken": "",
    "refreshToken": "",
    "expirationDate": "2024-02-12"
  }
}
```

#### Cases
Метод нужен только для того, чтобы приложение зарегистрировало нового пользователя ЭОС, который не был в нашей системе и его данные не мигрировали с прошлой БД.
Метод используется только после отправки ошибки 100 от сервера в методе `account/auth` из раздела **Авторизация подтвержденного пользователя**.
Создавать данные как в разделе **Подтверждение аккаунта**, получить *groupId* для присвоения к пользователю требует поиска в таблице groups по имени из поля `eosGroupName`.

В Response *groupId* может быть null если не удалось определить группу (Названия могут быть разными по написанию, но одинаковыми по значению. Пример: АД-109 (А) практ. - ожидалось АД-109 a).
Если groupId будет null, приложение запросить выбор группы в разделе **Выбор группы**

# Выбор группы
PUT `/account/chooseGroup`
#### Payload
{
  "groupId": 0
}

#### Cases
Если возникнет любого рода ошибка или конфликт на сервере отсылать `403` body:
```json
{
  "details": "Нет прав"
}
```

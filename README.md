# ЦРК МИРЭА
> Android-приложение для аттестации Samsung

## Описание

Android-приложение для образовательного портала ЦРК МИРЭА. \
Ссылка на dev-версию сайта: https://ckmirea.rtuitlab.dev/

### Технологии

* **Retrofit 2** - сетевые запросы к API;
* **SQLite** - хранение ресурсов, полученных с сервера (использование без интернета);
* **Picasso** - получение и хранение изображений;
* **EncryptedSharedPreferences** - хранение токенов и учётных данных пользователя.

### Перед сборкой

Указать в `./identity.properties` `CLIENT_ID` и `CLIENT_SECRET` identity-сервера с OAuth2.0.

```env
CLIENT_ID="samsung-app"
CLIENT_SECRET="BYG9pzsOUn2rk4LJeIeG63btep6u3kNv"

```

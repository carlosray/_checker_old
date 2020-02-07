# Start
##### 1. **Инициализация базы с ролями и типами уведомлений**
Файл ```initDB.sql```

##### 2. **Параметры виртуальной машины JAVA**
```-Djava.security.auth.login.config=jaas.conf```
сделать это можно в **TOMCAT_HOME/bin/catalina.bat** (```CATALINA_OPTS="-Djava.security.auth.login.config=%CATALINA_HOME%/conf/jaas.config"```)

##### 3. **Добавить Realm в Tomcat**
```
<Realm className="org.apache.catalina.realm.JAASRealm"
         appName="Checker"
         userClassNames="com.whitedream.autheticate.principal.UserPrincipal,com.whitedream.autheticate.principal.PasswordPrincipal"
         roleClassNames="com.whitedream.autheticate.principal.RolePrincipal"/>```

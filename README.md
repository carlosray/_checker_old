# Welcome :)
#### 1. **Инициализация базы с ролями и типами уведомлений**
Файл ```initDB.sql```
##### 1.1 поправить данные БД в project.properties (`db.`)
#### 2. **Параметры виртуальной машины JAVA**
##### 2.1 для работы JAAS (конфиг файл)
```
-Djava.security.auth.login.config=jaas.conf
```
сделать это можно в **TOMCAT_HOME/bin/startup.bat**
```
set JAVA_OPTS=%JAVA_OPTS% -Djava.security.auth.login.config=%CATALINA_HOME%/conf/jaas.config
```

#### 3. **Добавить Realm в Tomcat**
```
<Realm className="org.apache.catalina.realm.JAASRealm"
         appName="Checker"
         userClassNames="com.whitedream.autheticate.principal.UserPrincipal,com.whitedream.autheticate.principal.PasswordPrincipal"
         roleClassNames="com.whitedream.autheticate.principal.RolePrincipal"/>
```


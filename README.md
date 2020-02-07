    Start
1. **Инициализация базы с ролями и типами уведомлений** <br>
Файл `initDB.sql`
<br>
<br>
2. **Параметры виртуальной машины JAVA** <br>
`-Djava.security.auth.login.config=jaas.conf` <br>
<i>сделать это можно в **TOMCAT_HOME/bin/catalina.bat** (`CATALINA_OPTS="-Djava.security.auth.login.config=%CATALINA_HOME%/conf/jaas.config"`)</i>
<br>
3. **Добавить Realm в Tomcat**<br>
`<Realm className="org.apache.catalina.realm.JAASRealm"
         appName="Checker"
         userClassNames="com.whitedream.autheticate.principal.UserPrincipal,com.whitedream.autheticate.principal.PasswordPrincipal"
         roleClassNames="com.whitedream.autheticate.principal.RolePrincipal"/>`


package com.whitedream.dao.realdb;

import com.whitedream.dao.DaoFactory;
import com.whitedream.dao.NotificationDao;
import com.whitedream.dao.RoleDao;
import com.whitedream.dao.UserDao;
import com.whitedream.dao.util.TestDBConnectionBuilder;
import com.whitedream.model.Notification;
import com.whitedream.model.Role;
import com.whitedream.model.User;
import com.whitedream.utils.PasswordUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.List;

public class RealDbTest {
    public static void main(String[] args) throws Exception {

        System.out.println("----------------------------------\n" +
                "---------------Start--------------\n" +
                "----------------------------------");
        TestDBConnectionBuilder builder = new TestDBConnectionBuilder();
        Connection connection = builder.getConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String command = "";

        while (command != null) {
            System.out.print(" > ");
            command = in.readLine();
            long time = System.currentTimeMillis();
            try {
                if (command.equals("getRole")) {
                    RoleDao dao = DaoFactory.getRoleDao(connection);
                    Role role = dao.getRole("admin");
                    System.out.println(role);
                    role = dao.getRole(2);
                    System.out.println(role);
                }

                if (command.equals("createRole")) {
                    RoleDao dao = DaoFactory.getRoleDao(connection);
                    Role role = new Role(1, "test_role");
                    Role createdRole = dao.createRole(role);
                    System.out.println(createdRole);
                }

                if (command.equals("changeRoleName")) {
                    String newName = "test_role";
                    RoleDao dao = DaoFactory.getRoleDao(connection);
                    Role role = new Role("test_role_new");
                    dao.changeRoleName(role, newName);
                    System.out.println("Название роли изменено");
                    System.out.println(dao.getRole(newName));
                }

                if (command.equals("deleteRole")) {
                    RoleDao dao = DaoFactory.getRoleDao(connection);
                    Role role = new Role("test_role");
                    dao.deleteRole(role);
                }

                if (command.equals("createUser")) {
                    UserDao dao = DaoFactory.getUserDao(connection);
                    String testPass = "testpass";
                    User user = new User("testuser", PasswordUtils.getSaltedHash(testPass), new Role("member"));
                    User createdUser = dao.createUser(user);
                    System.out.println("user: " + createdUser);
                }

                if (command.equals("getUser")) {
                    UserDao dao = DaoFactory.getUserDao(connection);
                    User user = dao.getUser("testuser");
                    System.out.println("user: " + user);
                }

                if (command.equals("deleteUser")) {
                    UserDao dao = DaoFactory.getUserDao(connection);
                    User user = new User("testuser", "asdadad", new Role("member"));
                    dao.deleteUser(user);
                }

                if (command.equals("getAllUsers")) {
                    UserDao dao = DaoFactory.getUserDao(connection);
                    List<User> allUsers = dao.getAllUsers();
                    System.out.println(allUsers);
                }

                if (command.equals("getNotifByAddress")) {
                    NotificationDao dao = DaoFactory.getNotificationDao(connection);
                    List<Notification> testAddress = dao.getNotificationsByAddress("testAddress");
                    System.out.println(testAddress);
                }


                if (command.equals("passUtils")) {
                    UserDao dao = DaoFactory.getUserDao(connection);
                    User testuser = dao.getUser("testuser");
                    System.out.println("Пароль в базе: " + testuser.getPassword());
                    char[] password = new char[]{'t', 'e', 's', 't', 'p', 'a', 's', 's'};
                    System.out.println(password);
                    boolean check = PasswordUtils.check(password, testuser.getPassword());
                    System.out.println(check);
                }

                if (command.equals("exit")) {
                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("time (ms): " + (System.currentTimeMillis() - time));
        }
        connection.close();
    }
}
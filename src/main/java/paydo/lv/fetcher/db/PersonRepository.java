package paydo.lv.fetcher.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import paydo.lv.fetcher.MainAppController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRepository{
    Connection conn;
    PreparedStatement st;

    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS Users ";
        try {
            conn = MyConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertUser(Person entity) {
        String sql = "INSERT INTO USERS (user, pass) VALUES(?,?)";
        try {
            conn = MyConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, entity.getLogin());
            st.setString(2, entity.getPass());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Person entity) {
            String sql = "UPDATE USERS set allEmail = ?, c0Date = ?, c1Date = ?, c2Date = ?, c0Start = ?, c0End = ?, c1Start = ?, c1End = ?, c2Start = ?, c2End = ? WHERE user = ?";
        try {
            conn = MyConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, entity.getAllEmail());
            st.setString(2, entity.getC0Date());
            st.setString(3, entity.getC1Date());
            st.setString(4, entity.getC2Date());
            st.setInt(5, entity.getC0Start());
            st.setInt(6, entity.getC0End());
            st.setInt(7, entity.getC1Start());
            st.setInt(8, entity.getC1End());
            st.setInt(9, entity.getC2Start());
            st.setInt(10, entity.getC2End());
            st.setString(11, entity.getLogin());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("3");
                e.printStackTrace();
            }
        }
    }

    public Person getByName(String name) {
        String sql = "SELECT pass, allEmail, c0Date, c1Date, c2Date, c0Start, c0End, c1Start, c1End, c2Start, c2End FROM USERS WHERE user = ?";
        Person user = null;
        try {
            conn = MyConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                user = new Person(name, rs.getString(1));
                user.setAllEmail(rs.getInt(2));
                user.setC0Date(rs.getString(3));
                user.setC1Date(rs.getString(4));
                user.setC2Date(rs.getString(5));
                user.setC0Start(rs.getInt(6));
                user.setC0End(rs.getInt(7));
                user.setC1Start(rs.getInt(8));
                user.setC1End(rs.getInt(9));
                user.setC2Start(rs.getInt(10));
                user.setC2End(rs.getInt(11));
            }
        } catch (SQLException e) {
            MainAppController.addToLog("DB allready closed", "r");
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public void delete(String user) {
        String sql = "DELETE FROM Users WHERE user = ?";
        try {
            conn = MyConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, user);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ObservableList<Person> get() {
        String sql = "SELECT user, pass, allEmail, c0Date, c1Date, c2Date, c0Start, c0End, c1Start, c1End, c2Start, c2End FROM Users";
        ObservableList<Person> list = FXCollections.observableArrayList ();
        try {
            conn = MyConnection.getConnection();
            st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Person p = new Person(rs.getString(1), rs.getString(2));
                p.setAllEmail(rs.getInt(3));
                p.setC0Date(rs.getString(4));
                p.setC1Date(rs.getString(5));
                p.setC2Date(rs.getString(6));
                p.setC0Start(rs.getInt(7));
                p.setC0End(rs.getInt(8));
                p.setC1Start(rs.getInt(9));
                p.setC1End(rs.getInt(10));
                p.setC2Start(rs.getInt(11));
                p.setC2End(rs.getInt(12));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}

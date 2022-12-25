package com.company.dao.impl;

import com.company.entity.Country;
import com.company.entity.User;
import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.UserDaoInter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDAO implements UserDaoInter {

    private User getUser(ResultSet rs) throws Exception {

        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String phone = rs.getString("phone");
        String profileDesc = rs.getString("profileDescription");
        String email = rs.getString("email");
        String address=rs.getString("address");
        int nationalityId = rs.getInt("nationality_id");
        int birthplaceId = rs.getInt("birthplace_id");
        String nationalityStr = rs.getString("nationality");
        String birthplaceStr = rs.getString("birthplace");
        Date birthdate = rs.getDate("birthdate");

        Country nationality = new Country(nationalityId, null, nationalityStr);
        Country birthplace = new Country(birthplaceId, birthplaceStr, null);

        return new User(id, name, surname, phone, profileDesc, email,address, birthdate, nationality, birthplace);
    }

    public List<User> getAll() {
        List<User> result = new ArrayList<>();

        try ( Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("select "
                    + "    u.*,  "
                    + "		n.nationality  "
                    + "		c.name as birthplace  "
                    + "from user u  "
                    + "left join country n on u.nationality_id = n.id  "
                    + "left join country c on u.birthplace_id = c.id");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                User u = getUser(rs);
                result.add(u);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public boolean updateUser(User u) {
        try ( Connection c = connect()) {
            PreparedStatement stmt = c.prepareStatement("update user set name=?, surname=?, phone=?,profileDescription=?,email=?,address=?,birthdate=?, birthplace_id=?,nationality_id=? where id=?");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getPhone());
            stmt.setString(4, u.getProfileDesc());
            stmt.setString(5, u.getEmail());
            stmt.setString(6, u.getAddress());
            stmt.setDate(7, u.getBirthDate());
            stmt.setInt(8, u.getBirthPlace().getId());
            stmt.setInt(9, u.getNationality().getId());
            stmt.setInt(10, u.getId());
            return stmt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean removeUser(int id) {
        try ( Connection c = connect()) {
            Statement stmt = c.createStatement();
            return stmt.execute("delete from user where id= " + id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public User getById(int userId) {
        User result = null;

        try ( Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("select \n"
                    + "u.*,\n"
                    + "n.nationality ,\n"
                    + "c.name as birthplace \n"
                    + "from user u  \n"
                    + "left join country n on u.nationality_id = n.id  \n"
                    + "left join country c on u.birthplace_id = c.id where u.id=" + userId);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                result = getUser(rs);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean addUser(User u) {
        try ( Connection c = connect()) {
            PreparedStatement stmt = c.prepareStatement("insert into user(name,surname,phone,profileDesc,email,address) values(?,?,?,?,?,?)");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getPhone());
            stmt.setString(4, u.getProfileDesc());
            stmt.setString(5, u.getEmail());
            stmt.setString(5, u.getAddress());
            return stmt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}

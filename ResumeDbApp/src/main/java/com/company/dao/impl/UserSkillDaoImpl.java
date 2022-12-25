package com.company.dao.impl;

import com.company.entity.Skill;
import com.company.entity.User;
import com.company.entity.UserSkill;
import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.UserSkillDaoInter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserSkillDaoImpl extends AbstractDAO implements UserSkillDaoInter {

    private UserSkill getUserSkill(ResultSet rs) throws Exception {
        int userSkillId = rs.getInt("userSkillId");
        int userId = rs.getInt("id");
        int skillId = rs.getInt("skill_id");
        String skillName = rs.getString("skill_name");
        int power = rs.getInt("power");

        Skill skill = new Skill(skillId, skillName);
        User user = new User(userId);
        return new UserSkill(userSkillId, user, skill, power);

    }

    @Override
    public List<UserSkill> getAllSkillByUserId(int userId) {
        List<UserSkill> result = new ArrayList<>();

        try ( Connection c = connect()) {

            PreparedStatement stmt = c.prepareStatement("SELECT"
                    + "	us.id as userSkillId, "
                    + " u.*,"
                    + "	us.skill_id,"
                    + "	s.NAME AS skill_name,"
                    + "	us.power "
                    + "FROM"
                    + "	user_skill us"
                    + "	LEFT JOIN USER u ON us.user_id = u.id"
                    + "	LEFT JOIN skill s ON us.skill_id = s.id "
                    + "WHERE"
                    + "	us.user_id = ?");
            stmt.setInt(1, userId);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                UserSkill u = getUserSkill(rs);
                result.add(u);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public boolean removeUserSkill(int id) {
        try ( Connection c = connect()) {
            Statement stmt = c.createStatement();
            return stmt.execute("delete from user_skill where id= " + id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addUserSkill(UserSkill us) {

        try ( Connection c = connect()) {

            PreparedStatement stmt = c.prepareStatement("insert into user_skill(skill_id,user_id,power) values(?,?,?)");

            stmt.setInt(1, us.getSkill().getId());
            stmt.setInt(2, us.getUser().getId());
            stmt.setInt(3, us.getPower());
            stmt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean updateUserSkill(UserSkill us) {

        try ( Connection c = connect()) {

            PreparedStatement stmt = c.prepareStatement("update user_skill set skill_id=?,user_id=?,power=? where id=?");
            stmt.setInt(1, us.getSkill().getId());
            stmt.setInt(2, us.getUser().getId());
            stmt.setInt(3, us.getPower());
            stmt.setInt(4, us.getId());
            stmt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

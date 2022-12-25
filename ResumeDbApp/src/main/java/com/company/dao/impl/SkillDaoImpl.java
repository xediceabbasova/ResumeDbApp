package com.company.dao.impl;

import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.SkillDaoInter;
import com.company.entity.Skill;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SkillDaoImpl extends AbstractDAO implements SkillDaoInter {
    
    public Skill getSkill(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        
        Skill skill = new Skill(id, name);
        return skill;
    }
    
    @Override
    public List<Skill> getAll() {
        List<Skill> result = new ArrayList<>();
        try ( Connection c = connect()) {
            Statement stmt = c.createStatement();
            stmt.execute("select * from skill");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                
                Skill skill = getSkill(rs);
                result.add(skill);
                
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    public boolean addSkill(Skill s) {
        
        try ( Connection c = connect()) {
            
            PreparedStatement stmt = c.prepareStatement("insert into skill(name) values(?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, s.getName());
            stmt.execute();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                s.setId(generatedKeys.getInt(1));
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
        
    }
    
}

package com.company.dao.inter;

import com.company.entity.UserSkill;
import java.util.List;

public interface UserSkillDaoInter {

    public List<UserSkill> getAllSkillByUserId(int userId);

    public boolean removeUserSkill(int id);

    public boolean addUserSkill(UserSkill us);

    public boolean updateUserSkill(UserSkill us);
}

package com.company.dao.inter;

import com.company.entity.EmploymentHistory;
import com.company.entity.User;
import java.util.List;

public interface EmploymentHistoryDaoInter {

    public List<EmploymentHistory> getAllEmploymentHistoryByUserId(int userId);

    public boolean addEmploymentHistory(EmploymentHistory emp);

    public boolean updateEmploymentHistory(EmploymentHistory emp);

    public boolean removeEmploymentHistory(int id);



}

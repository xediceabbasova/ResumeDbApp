package com.company.main;

import com.company.dao.inter.CountryDaoInter;
import com.company.dao.inter.EmploymentHistoryDaoInter;
import com.company.dao.inter.UserDaoInter;
import com.company.entity.User;

public class Main {
    
    public static void main(String[] args) throws Exception {

        CountryDaoInter dao=Context.instanceCountryDao();
        dao.getAll();
        
        
    }
}

package com.simona.dao;


import com.simona.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by alex on 3/14/18.
 */
@Repository
public interface AccountDao extends JpaRepository <Account, Long> {

    default Account findByAccountName(String name) {
        for (Account account : findAll()) {
            if (account.getAccountName().equals(name))
                return account;
        }
        return null;
    };
}

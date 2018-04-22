//package com.simona.dao; TODO it is for Spring security
//
//import com.simona.model.Account;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface AccountDao extends JpaRepository<Account, Long> {
//
//    default Account findByAccountName(String name) {
//        for (Account account : findAll()) {
//            if (account.getAccountName().equals(name))
//                return account;
//        }
//        Account account = new Account();
//        account.setAccountName("account_1");
//        account.setAccountPassword("password_1");
//        account.setId(1l);
//        return account;
//    };
//}

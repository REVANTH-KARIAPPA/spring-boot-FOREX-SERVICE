package com.furniture.Repository;

import com.furniture.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByAccountNumber(String accountNumber);
}

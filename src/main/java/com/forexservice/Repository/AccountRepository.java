package com.forexservice.Repository;

import com.forexservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByAccountNumber(String accountNumber);
}

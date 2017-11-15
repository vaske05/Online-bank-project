package com.userfront.service;

import java.security.Principal;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;

public interface AccountService {
	PrimaryAccount createPrimaryAccount();
	SavingsAccount createSavingsAccount();
	public void deposit(String accountType, double amount, Principal principal);

}

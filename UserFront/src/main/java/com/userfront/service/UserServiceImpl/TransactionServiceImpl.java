package com.userfront.service.UserServiceImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.DataAccessObject.PrimaryAccountDao;
import com.userfront.DataAccessObject.PrimaryTransactionDao;
import com.userfront.DataAccessObject.SavingsAccountDao;
import com.userfront.DataAccessObject.SavingsTransactionDao;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.PrimaryTransaction;
import com.userfront.domain.Recipient;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.SavingsTransaction;
import com.userfront.domain.User;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;



@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private PrimaryTransactionDao primaryTransactionDao;
	
	@Autowired
	private SavingsTransactionDao savingsTransactionDao;
	
	@Autowired
	private PrimaryAccountDao primaryAccountDao;
	
	@Autowired
	private SavingsAccountDao savingsAccountDao;
	
	public List<PrimaryTransaction> findPrimaryTransactionList(String username) {
		User user = userService.findByUsername(username);
		List<PrimaryTransaction> primaryTransactionList = user.getPrimaryAccount().getPrimaryTransactionList();
		
		return primaryTransactionList;
	}
	
	public List<SavingsTransaction> findSavingsTransactionList(String username) {
		User user = userService.findByUsername(username);
		List<SavingsTransaction> savingsTransaction = user.getSavingsAccount().getSavingsTransactionList();
		
		return savingsTransaction;
	}
	
	public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction) {
		primaryTransactionDao.save(primaryTransaction);
	}
	
	public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
		savingsTransactionDao.save(savingsTransaction);
	}
	
	public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction) {
		primaryTransactionDao.save(primaryTransaction);
	}
	
	public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
		savingsTransactionDao.save(savingsTransaction);
	}

	@Override
	public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount,
			PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception {
		
		if(transferFrom.equalsIgnoreCase("Primary") && transferTo.equalsIgnoreCase("Savings")) {
			//Transfer sa Primary acc. na Savings acc.
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount); //Snimamo promenjena novcana stanja na akauntima.
			savingsAccountDao.save(savingsAccount);
			
			Date date = new Date();
			
			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,"Between account transfer from " + transferFrom + " to " + transferTo, "Account", "Finished", Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount);                                                  
			primaryTransactionDao.save(primaryTransaction);
			//Ako je transakcija izmedju dva akaunta logicno je da se pamti transakcija za oba. Tako da ovde nedostaje 
			//snimanje u Savings akaunt tabeli. Moze da se doda. Otom potom.
			
		}
		else if(transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Primary")) {
			//Transfer sa Savings acc. na Primary acc.
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount); //Snimamo promenjena novcana stanja na akauntima.
			savingsAccountDao.save(savingsAccount);
			
			Date date = new Date();
			
			SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Between account transfer from " + transferFrom + " to " + transferTo, "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);                                                  
			savingsTransactionDao.save(savingsTransaction); //Snimamo transakciju izmedju dva akaunta u tabeli.
		}
		else {
			
			throw new Exception("Invalid Transfer.");
		}
	
		
	}
	
	public List<Recipient> findRecipientList(Principal principal) {
		String username = principal.getName();
		List<Recipient> recipientList = recipientDao.findAll().stream() //Convert list to stream -> findAll vraca sve user-e iz baze
				.filter(recipient -> username.equals(recipient.getUser().getUsername()))
				.collect(Collectors.toList());
		//recipientList -> Lista recipijenata koji su povezani(bind-ovani) sa ovim User-om
		
		return recipientList;
	}
	
	public Recipient saveRecipient(Recipient recipient) {
		return recipient.save(recipient);
	}
	
	public Recipient findRecipientByName(String recipientName) {
		return recipientDao.findByName(recipientName);
	}
	
	public void deleteRecipientByName(String recipientName) {
		recipientDao.deleteByName(recipientName);
	}
	
	

}

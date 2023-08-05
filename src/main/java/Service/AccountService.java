package Service;

import java.util.ArrayList;
import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;
    
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    List<Account> accountList = new ArrayList<>();
    
    public Account register(Account account) {
        if ( accountList.stream()
        .filter(acc -> acc.getUsername().equals(account.getUsername()))
        .findAny()
        .orElse(null) == null ) {
            Account accountToBeRegistered = accountDAO.register(account);
        if(!account.getUsername().equals("") &&
            account.getPassword().length() >= 4) {
            accountList.add(account);
            return accountToBeRegistered;
        } else {
            return null;
        }
        } else {
            return null;
        }
    }

    public Account login(Account account) {
        if (account == null) return null;
        return accountDAO.login(account);
    }
}

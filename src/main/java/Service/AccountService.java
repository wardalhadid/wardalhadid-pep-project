package Service;

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
    
    public Account register(Account account) {
        if (!accountDAO.confirmAccountIdExists(account.getAccount_id())) {
            Account accountToBeRegistered = accountDAO.register(account);
        if(!account.getUsername().equals("") &&
            account.getPassword().length() >= 4) {
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

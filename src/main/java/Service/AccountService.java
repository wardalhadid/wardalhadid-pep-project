package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO = new AccountDAO();
    
    public Account register(Account account) {
        return accountDAO.register(account);
    }
}

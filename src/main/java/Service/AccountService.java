package Service;

import java.util.ArrayList;
import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO = new AccountDAO();
    List<Account> accountList = new ArrayList<>();
    
    public Account register(Account account) {
        if(!accountList.contains(account) & !account.getUsername().equals("") & account.getPassword().length() > 4) {
            accountList.add(account);
            return accountDAO.register(account);
        }
        return null;
    }
}

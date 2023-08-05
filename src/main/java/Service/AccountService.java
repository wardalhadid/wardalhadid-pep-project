package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO = new AccountDAO();
    List<Account> acountList = new ArrayList<>();
    
    public Account register(Account account) {
        if(!accountList.contains(account) & !account.getUsername().equals("") & account.getPassword().length > 4) {
            return accountDAO.register(account);
            accountList.add(account);
        }
        return null;
    }
}

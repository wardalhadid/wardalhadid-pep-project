package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import java.util.List;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
        accountDAO = new AccountDAO();
    }

    public List<Message> getAllMessages() {
        List<Message> messages = messageDAO.getAllMessages();
        return messages;
    }

    public Message newMessage(Message message) {
        boolean idExists  = accountDAO.confirmAccountIdExists(message.getPosted_by());
        Message createdMessage = messageDAO.newMessage(message);
            if (idExists && 
            message.getMessage_text().length() < 255 &&
            message.getMessage_text().length() > 0
            ) {
                return createdMessage;
            }
        return null;
    }

    public Message getMessageById(int id) {
        Message returnMessage = messageDAO.getMessageById(id);
        return returnMessage;
    }

    public Message deleteMessage (int id) {
        return messageDAO.deleteMessage(id);
    }

    public Message updateMessage (int id, String message_text) {
        if (message_text.length() > 0 && message_text.length() < 255) {
            return messageDAO.updateMessage(id, message_text);
        } else {
            return null;
        }
    }

    public List<Message> getAllMessagesFromAccountId(int id) {
        return messageDAO.getAllMessagesFromAccountId(id);
    }
}

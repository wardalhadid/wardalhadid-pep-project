package Service;

import DAO.MessageDAO;
import java.util.List;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public List<Message> getAllMessages() {
        List<Message> messages = messageDAO.getAllMessages();
        return messages;
    }
}

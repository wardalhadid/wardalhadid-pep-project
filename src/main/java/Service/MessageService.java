package Service;

import DAO.MessageDAO;
import java.util.List;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages() {
        List<Message> messages = messageDAO.getAllMessages();
        return messages;
    }

    public Message newMessage(Message message) {
        if (message.getMessage_text().length() < 255 &&
            message.getMessage_text().length() > 0) {
            return messageDAO.newMessage(message);
        }
        return null;
    }
}

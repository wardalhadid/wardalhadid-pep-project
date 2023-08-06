package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

     MessageService messageService;
     AccountService accountService;

     public SocialMediaController() {
        messageService = new MessageService();
        accountService = new AccountService();
     }

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.get("example-endpoint", this::exampleHandler);

        app.post("register", this::register);
        app.post("login", this::login);

        app.post("messages", this::createNewMessage);
        app.get("messages", this::getAllMessages);

        app.get("messages/{message_id}", this::getMessageById);
        app.delete("messages/{message_id}", this::deleteMesssage);
        app.patch("messages/{message_id}", this::updateMessage);

        app.get("accounts/{account_id}/messages", this::getAllMessagesFromUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void register(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(); 
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account accountToRegister = accountService.register(account);
        if (accountToRegister == null ) {
            ctx.status(400);
        } else {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(accountToRegister));
        }
    }

    private void login(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account inputAccount = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.login(inputAccount);
        if (loginAccount == null) {
            ctx.status(401);
        } else {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(loginAccount));
        }
    }

    private void getAllMessages(Context ctx) {
        ctx.json(messageService.getAllMessages());
    }

    private void createNewMessage(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message inputMessage = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.newMessage(inputMessage);
        if (newMessage == null) {
            ctx.status(400);
        } else {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(newMessage));
        }
    }

    private void getMessageById(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        try {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message returnedMessage = messageService.getMessageById(id);
        if (returnedMessage.getMessage_text() == null) {
            ctx.json("");
        } else {
        ctx.json(mapper.writeValueAsString(returnedMessage));
        }
        } catch (NumberFormatException e) {
            ctx.json("");
        } finally {
        ctx.status(200);
        }
    }

    private void deleteMesssage(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        try {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(id);
        if (deletedMessage == null || deletedMessage.getMessage_text() == null) {
            ctx.json("");
        } else {
        ctx.json(mapper.writeValueAsString(deletedMessage));
        }
        } catch (NumberFormatException e) {
            ctx.json("");
        } finally {
        ctx.status(200);
        }
    }

    private void updateMessage(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        try {
            int id = Integer.parseInt(ctx.pathParam("message_id"));
            String message_text = mapper.readValue(ctx.body(), Message.class).getMessage_text();
            Message updatedMessage = messageService.updateMessage(id, message_text);
            if (updatedMessage == null || updatedMessage.getMessage_text() == null) {
                ctx.status(400);
            } else {
                ctx.status(200);
                ctx.json(mapper.writeValueAsString(updatedMessage));
            }
        }  catch (NumberFormatException e) {
            ctx.status(400);
        }
    }

    private void getAllMessagesFromUser(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            int id = Integer.parseInt(ctx.pathParam("account_id"));
            List<Message> allMessages = messageService.getAllMessagesFromAccountId(id);
            ctx.json(mapper.writeValueAsString(allMessages));
        } catch (NumberFormatException e) {
            ctx.status(200);
        }
    }
}
package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
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

    private void createNewMessage(Context ctx) {
        ctx.status(200);
    }

    private void getMessageById(Context ctx) {
        ctx.status(200);
    }

    private void deleteMesssage(Context ctx) {
        ctx.status(200);
    }

    private void updateMessage(Context ctx) {
        ctx.status(200);
    }

    private void getAllMessagesFromUser(Context ctx) {
        ctx.status(200);
    }
}
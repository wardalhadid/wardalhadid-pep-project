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

    private void register(Context ctx) {
        AccountService accountService = new AccountService();
        ObjectMapper mapper = new ObjectMapper(); 
        Account account;
        try {
            account = mapper.readValue(ctx.body(), Account.class);
        Account accountToRegister = accountService.register(account);
        if (accountToRegister == null ) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(accountToRegister));
            ctx.status(200);
        }
    } catch (JsonMappingException e) {
        e.printStackTrace();
    } catch (JsonProcessingException e) {
        e.printStackTrace();
    }
    }

    private void login(Context ctx) {

    }

    private void getAllMessages(Context ctx) {
        ctx.json(messageService.getAllMessages());
    }

    private void createNewMessage(Context ctx) {

    }

    private void getMessageById(Context ctx) {

    }

    private void deleteMesssage(Context ctx) {

    }

    private void updateMessage(Context ctx) {

    }

    private void getAllMessagesFromUser(Context ctx) {

    }
}
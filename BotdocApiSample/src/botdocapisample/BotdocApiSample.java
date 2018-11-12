package botdocapisample;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rafael Juzwiak, Botdoc Inc.
 */

public class BotdocApiSample {
    

    public static void main(String[] args) {
        BotdocConnector test = new BotdocConnector();
        
        try{
            Map requestData = test.createRequest();
            
            try{
                //holds all contact_notification_send that were sent
                //or are pending send
                List contactNotifiactionSends = test.sendRequest(requestData.get("id").toString());
                System.out.println("The request was sent to");
                System.out.println(contactNotifiactionSends);
            }
            catch(IOException err){
                System.out.println("Error trying to send the request to the user");
                System.out.println(err.getMessage());
            }

        }
        catch(IOException err){
            System.out.println("Error while creating new Request");
            System.out.println(err.getMessage());
        }
        
    }
}

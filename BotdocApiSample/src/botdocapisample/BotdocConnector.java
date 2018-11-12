package botdocapisample;

import java.lang.reflect.Type;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.entity.StringEntity;


/**
 *
 * @author Rafael Juzwiak - Botdoc Inc.
 */
public class BotdocConnector {
    
    //you API key found in the developer portal
    private final String apikey = "<PUT YOUR APIKEY HERE>";
    
    //the email of the Botdoc account
    private final String account_email = "<PUT YOUR EMAIL ADDRESS HERE>";
    
    //the endpoint that should be used
    //you may replace here with the sandbox link or live link
    private final String endpoint_base_url = "https://sandboxapi.botdoc.io";
    
    //stores the current token
    private String token;
    
    
    BotdocConnector() {
        //try to get the JWT token from Botdoc API
        try{
            this.token = this.getToken();
        }
        catch(IOException err){
            System.out.println("We were unable to generate the JWT token");
            System.out.println(err.getMessage());
        }
    }
    

    /**
     * Sends a request with the given id to the ContactMethods 
     * sent uppon the time the Request was created
     * It returns a List<Map> of ContactMethodNotificationSend() associated with
     * this request, and the status of each of them.
     */
    public List sendRequest(String id) throws IOException{
        
        //creates a HTTP Client
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        //we expect the id as string in order to concact with the URL string
        //however, in this sample we are not filtering the response from 
        //createRequest() function, therefore we are doing this cast here in
        //order to remove "." from the string.
        long longId = Math.round(Double.parseDouble(id));
        
        
        //Define URL and headers of this request
        String url = this.endpoint_base_url + "request/" + new Long(longId).toString() + "/send_notification/";
	    HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "JWT " + this.token);
        
        //executes the request - sends to botdoc api
        HttpResponse response = httpClient.execute(post);

        //read return
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        
        while ((line = rd.readLine()) != null) {
                result.append(line);
        }
            
        
        //converts response back to json
        Gson gson = new Gson();
        List<Map<String,Object>> map =new ArrayList<>();
        map = gson.fromJson(result.toString(), map.getClass());
        return map;
    }
    


    /**
     * Returns a Map of the Request object with all information 
     * returned from the Botdoc API
     */
    public Map createRequest() throws UnsupportedEncodingException, IOException{
        //creates a HTTP Client
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        //Define URL and headers of this request
        String url = this.endpoint_base_url + "request/";
	    HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "JWT " + this.token);
        
        //create a Push
        Request push = new Request();
        push.message = "This is the message the user see at the Push/Pull page";
        push.type = "push";
        
        //Create a list of contacts
        List<Map<String,Object>> contacts = new ArrayList<>();
        
        //create first contact
        Map<String, Object> contact1 = new HashMap<>();
        contact1.put("first_name", "Rafael");
        contact1.put("last_name", "Juzwiak");
        
        //create a list of contact methods for first contact
        List<Map<String,String>> contactMethods = new ArrayList<>();
        
        //creates first contact_method for this contact
        Map<String, String> contactMethod1 = new HashMap<>();
        contactMethod1.put("value", "rjuzwiak@botdoc.io");
        contactMethod1.put("interface_class", "email");
        
        //creates second contact_method for this contact
        Map<String, String> contactMethod2 = new HashMap<>();
        contactMethod2.put("value", "7199604460");
        contactMethod2.put("interface_class", "sms");
        
        //append contact_method data to the contact_method Map
        contactMethods.add(0, contactMethod1);
        contactMethods.add(1, contactMethod2);
        
        //apend the contact_method to the contact Map
        contact1.put("contact_method", contactMethods);
        
        //append contact to contact List
        contacts.add(0, contact1);
        
        //set the contact attribute in the push we created earlier
        push.contact = contacts;
        
        //convert the Push we just created to a json
        Gson gson = new Gson();
        String json = gson.toJson(push);
        
        //sets the json into the HttpClient to be sent
        post.setEntity(new StringEntity(json));
        
        
        //executes the request - sends to botdoc api
        HttpResponse response = httpClient.execute(post);

        //read return
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        
        while ((line = rd.readLine()) != null) {
                result.append(line);
        }
            
        //converts response back to json
        Map<String,Object> map = new HashMap<String,Object>();
        map = (Map<String,Object>) gson.fromJson(result.toString(), map.getClass());
        
        return map;
    }
        
        
        
        
    /**
     * Set in the current class the token that should
     * be used to perform the request to Botdoc API endpoints
     */
    public String getToken() throws UnsupportedEncodingException, IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        String url = this.endpoint_base_url + "auth/get_token/";
        HttpPost post = new HttpPost(url);
            
            
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("api_key", this.apikey));
        urlParameters.add(new BasicNameValuePair("email", this.account_email));
        
        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        
        HttpResponse response = httpClient.execute(post);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        
        
        while ((line = rd.readLine()) != null) {
                result.append(line);
        }
        
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<String,Object>();
        map = (Map<String,Object>) gson.fromJson(result.toString(), map.getClass());
        
        return map.get("token").toString();
    }
}




/**
 * We define a Request class just to convert back and forth to Json
 * in order to create the POST variables from a class.
 * We could also just create a Map variable instead of a class.
 */
class Request{
    public Integer id;
    public Map<String,String> requester = null;
    public List<Map<String,Object>> contact = new ArrayList<Map<String,Object>>();
    public List<Map<String,String>> contact_notification_send = null;
    public List<Map<String,String>> contact_method = null;
    public String identifier;
    public String message;
    public String receiver_message;
    public String requester_privatenotes;
    public Boolean complete;
    public Boolean is_draft;
    public String type;
    public String short_message;
    public String long_message_subject;
    public String long_message;
    public String callback_url;
    public Boolean callback_status;
    public Integer callback_tries;
    public String tfa_send_to;
    public String tfa_send_to_value;
    public Integer tfa_times_sent;
    public String email_template_slug;
    public String created;
    public String updated;
    
   
}
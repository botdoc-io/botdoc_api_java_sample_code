# This package is a Code Sample demonstrates how to Athenticate and Create a Push with Botdoc-API

## Installing
We added few packages that are necessary in order to user the code, you could change for other packages that execute the same functions
The Packages are inside "additional_libs.rar", extract the packages and import in your project in order to work this Code Sample.

# Botdoc.io API Java Sample code
Sample code on how to run the Botdoc API on Java

## Use Your API Credentials
On the following lines of "src/botdocapisample/BotdocConnector.java" update the credentail to your use your own, and replace the endpoint to this environment.
You can get your api key at https://sandboxdev.botdoc.io/ for testing on sandbox, or https://dev.botdoc.io/ for live tests.
``` Java
public class BotdocConnector {
    private final String apikey = "<PUT YOUR APIKEY HERE>";
    private final String account_email = "<PUT YOUR EMAIL ADDRESS HERE>";
    private final String endpoint_base_url = "https://sandboxapi.botdoc.io";
    ...
```

## Update the contact information and request information
This example has all Request and Contact attributes hardcoded as it 
serves only as a sample how to perform a call to our API.
If you want to update those details, you may update any of the attributes
at "src/BotdocConnector.java" inside the method "createRequest()".
This method is responsable for creating a Request (PUSH or PULL) and, associate
all provided contact information to it.



## Open the terminal an run
``` bash
java BotdocApiSample.java
``` 

## [JOIN US ON SLACK](https://botdoc.io/slack/)
If you have any question let us know and we will be more than happy to help you on your Botdoc API path!


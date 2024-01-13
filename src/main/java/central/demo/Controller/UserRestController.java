package central.demo.Controller;

import central.demo.model.ResponeAPI;
import central.demo.model.ResponseUrl;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@RestController

public class UserRestController {

    @RequestMapping("/getUser")
    public String getUser(){
        String result = "";
        String json  = getUrlContents("https://randomuser.me/api/?seed=foobar");
        Gson gson = new Gson();
        ResponseUrl res = gson.fromJson(json, ResponseUrl.class);
        if(res != null){
            if(res.getResults() != null && res.getResults().size() > 0){
                result = "Name : "+res.getResults().get(0).getName().getFirst() +" " +res.getResults().get(0).getName().getLast() +"</br>"+
                         "Gender : "+res.getResults().get(0).getGender()+"</br>" +
                         "Email : "+res.getResults().get(0).getEmail();
            }
        }
        return result;
    }

    @RequestMapping("/getUserObject")
    public ResponeAPI getUserObject(){
        ResponeAPI responeAPI = null;
        String json  = getUrlContents("https://randomuser.me/api/?seed=foobar");
        Gson gson = new Gson();
        ResponseUrl res = gson.fromJson(json, ResponseUrl.class);
        if(res != null){
            if(res.getResults() != null && res.getResults().size() > 0){
                responeAPI = new ResponeAPI();
                if(res.getResults().get(0).getName() != null){
                    responeAPI.setFirstName(res.getResults().get(0).getName().getFirst());
                    responeAPI.setLastName(res.getResults().get(0).getName().getLast());
                }
                responeAPI.setEmail(res.getResults().get(0).getEmail());
                responeAPI.setGender(res.getResults().get(0).getGender());
            }
        }
        return responeAPI;
    }

    private static String getUrlContents(String theUrl)
    {
        StringBuilder content = new StringBuilder();
        try
        {
            URL url = new URL(theUrl); // creating a url object
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }
}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Test_HTTP_XML {
    public static void main(String[] args) {
        String text = "Hello";
        String language = "en-cs";
        Test_HTTP_XML oobj_Test_HTTP_XML=new Test_HTTP_XML();
        text = oobj_Test_HTTP_XML.get_response(text,language);
        System.out.println(text);
    }
    public String get_response(String textToTranslate, String languageToTranslate){
        try {
            String api_key="trnsl.1.1.20180324T223720Z.76b5399cb4e09ca5.200e3b755ebf85fa2912551b42f7eeb601fc30c5";
            String text= textToTranslate;
            if(text.contains(" ")){
                text = text.replace(" ", "%20");
            }
            String language = languageToTranslate;
            String url = "https://translate.yandex.net/api/v1.5/tr/translate?key="+api_key+"&text="+text+"&lang="+language;
            System.out.println(url);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            if(responseCode == 200){
                System.out.println("Connection good.");
            }else{
                System.out.println("Connection bad.");
            }


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print in String
            // System.out.println(response.toString());
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new InputSource(new StringReader(response.toString())));
            NodeList errNodes = doc.getElementsByTagName("Translation");
            if (errNodes.getLength() > 0) {
                Element err = (Element)errNodes.item(0);
                //System.out.println("Translation = "+err.getElementsByTagName("text").item(0).getTextContent());
                textToTranslate  = err.getElementsByTagName("text").item(0).getTextContent();
            } else {
                // success
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return textToTranslate;
    }


}

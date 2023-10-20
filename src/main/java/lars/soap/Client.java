package lars.soap;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

class Client {

  public static void main(String[] args) throws IOException {
    new Client().simpleSoapRequest();
  }

  public void simpleSoapRequest() throws IOException {
    String outputString = "";
    String wsURL = "http://dwws.herokuapp.com/application/soap/hello";
    URL url = new URL(wsURL);
    URLConnection connection = url.openConnection();
    HttpURLConnection httpConn = (HttpURLConnection) connection;
    byte[] b;
    try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
      String xmlInput =
          """
          <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:dwws="http://dwws/">
             <soapenv:Header/>
             <soapenv:Body>
                <dwws:sayHello/>
             </soapenv:Body>
          </soapenv:Envelope>
          """;

      byte[] buffer = xmlInput.getBytes(StandardCharsets.UTF_8);
      bout.write(buffer);
      b = bout.toByteArray();
    }
    httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
    httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
    // httpConn.setRequestProperty("SOAPAction", SOAPAction);
    httpConn.setRequestMethod("POST");
    httpConn.setDoOutput(true);
    httpConn.setDoInput(true);
    try (OutputStream out = httpConn.getOutputStream()) {
      out.write(b);
    }
    String responseString;
    InputStreamReader isr =
        new InputStreamReader(httpConn.getInputStream(), StandardCharsets.UTF_8);
    try (BufferedReader in = new BufferedReader(isr)) {
      while ((responseString = in.readLine()) != null) {
        outputString = outputString + responseString;
      }
    }
    System.out.println(outputString);
  }
}

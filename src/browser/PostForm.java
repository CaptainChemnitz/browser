package browser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class PostForm {

	// Proxy Server
	private Proxy PROXY;

	// Adresse des Proxys
	private String proxyAdress;
	// Port des Proxys
	private Integer proxyPort;

	public static void main(String[] args) throws InterruptedException,
			IOException {

		// Ueberwachung starten
		while (true) {
			new PostForm().testIt();
			Thread.sleep(5000);
		}
	}

	private void testIt() throws InterruptedException, IOException {
		// PROXY zuruecksetzen
		this.PROXY = null;

		// Konfiguration (Proxy) auslesen
		proxyAdress = "172.30.1.20";
		proxyPort = Integer.valueOf("3128");

		// Proxy einstellen
		this.PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				proxyAdress, proxyPort.intValue()));

		// Ziel URL konfigurieren
		// String https_url = "https://instagram.com/" + victimsName + "/";
		String https_url = "https://fun-sport-vision.com/gewinnspiel.html";

		// Verbindungen bereitstellen
		URL url = new URL(https_url);
		HttpsURLConnection con;

		try {
			// Verbindung mit Proxy
			if (this.PROXY != null) {
				con = (HttpsURLConnection) url.openConnection(this.PROXY);
			} else {
				// Verbindung ohne Proxy
				con = (HttpsURLConnection) url.openConnection();
			}

			// dump all the content

			String input;
			boolean isPrivate = false;
			
		    Map<String, String> data = new HashMap<String, String>();
		    data.put("gewinnspiel_15", "pic15");
		    
		    
		    
//		    URL siteUrl = new URL(url);
//	        HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
	        con.setRequestMethod("POST");
	        con.setRequestProperty("Content-Type", 
	               "application/x-www-form-urlencoded");
	        con.setUseCaches (true);
	        con.setDoOutput(true);
	        con.setDoInput(true);

	        DataOutputStream out = new DataOutputStream(con.getOutputStream());

	        Set keys = data.keySet();
	        Iterator keyIter = keys.iterator();
	        String content = "";
	        for(int i=0; keyIter.hasNext(); i++) {
	            Object key = keyIter.next();
	            if(i!=0) {
	                content += "&";
	            }
	            content += key + "=" + URLEncoder.encode(data.get(key), "UTF-8");
	        }
	        System.out.println(content);
	        out.writeBytes(content);
	        out.flush();
	        out.close();
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String line = "";
	        while((line=in.readLine())!=null) {
	            System.out.println(line);
	        }
	        in.close();

	        
//	        BufferedReader br = new BufferedReader(new InputStreamReader(
//					con.getInputStream()));
//			List<String> pageCode = new ArrayList<String>();
//
//			while ((input = br.readLine()) != null) {
//				if (input.contains("is_private\":true")) {
//					isPrivate = true;
//				}
//				pageCode.add(input);
//			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
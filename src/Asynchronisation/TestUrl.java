package Asynchronisation;

import java.net.*;

public class TestUrl {

	public static void main(String[] args) throws URISyntaxException, MalformedURLException {
		String sUrl = "http://example.com:80/docs/books/tutorial"
                + "/index.html?name=networking#DOWNLOADING";
		URL url = new URL(sUrl);
		URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
		String canonical = uri.toString();

		System.out.println("le protocole :"+url.getProtocol());
		System.out.println("le userInfo "+url.getUserInfo());
		System.out.println("le host est :"+url.getHost());
		System.out.println("le port est :"+url.getPort());
		System.out.println("le Query est :"+url.getQuery());
		System.out.println("le ref est :"+url.getRef());
		System.out.println(canonical);

		if(sUrl.endsWith("name=")){
			System.out.println("Vrai");
		}else{
			System.out.println("faux");
		}
	}

}

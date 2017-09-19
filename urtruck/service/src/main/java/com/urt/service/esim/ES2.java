package com.urt.service.esim;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class ES2 {
	public static void main(String[] args) throws Exception {
		// downloadOrder 预绑定
		// ConfirmOrder 确认绑定或者解绑
		// ReleaseProfile 发布profile
		// CancelOrder 解绑
		try {
			// Open a secure connection.
			String downloadOrderUrl="https://cnoem.lab.ids-odc.gemalto.com:11912/gsma/rsp2/es2plus/downloadOrder";
			URL url = new URL(downloadOrderUrl);
			String requestParams = "{\"header\":{\"functionRequesterIdentifier\":\"CNOEMRequestID\",\"functionCallIdentifier\":\"\"},\"iccid\":\"89354010170301000026\",\"eid\":\"89033023421730000000000003215903\"}";
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			System.out.println(requestParams);
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore tks = KeyStore.getInstance("JKS");

			String CLIENT_KEY_STORE_PASSWORD = "lenovoes2";
			String CLIENT_TRUST_KEY_STORE_PASSWORD = "lenovoes2";

			ks.load(new FileInputStream("C:/Users/syf/Desktop/Esim/LevnovoEs2/lenovo_client_bundle_2.jks"),
					CLIENT_KEY_STORE_PASSWORD.toCharArray());
			tks.load(new FileInputStream("C:/Users/syf/Desktop/Esim/LevnovoEs2/truststore.jks"),
					CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());

			kmf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());
			tmf.init(tks);

			SSLContext context = SSLContext.getInstance("TLS");
			context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			SSLSocketFactory sockFact = context.getSocketFactory();
			con.setSSLSocketFactory(sockFact);

			// Set up the connection properties
			con.setRequestProperty("Connection", "close");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setConnectTimeout(30000);
			con.setReadTimeout(30000);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("X-Admin-Protocol", "gsma/rsp2");
			con.setRequestProperty("Host", "cnoem.lab.ids-odc.gemalto.com");
			// con.setRequestProperty( "Accept", "application/json" );

			// Send the request
			OutputStream outputStream = con.getOutputStream();
			outputStream.write(requestParams.getBytes("UTF-8"));
			outputStream.close();

			// Check for errors
			int responseCode = con.getResponseCode();
			InputStream inputStream;
			System.out.println("----" + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) {
				inputStream = con.getInputStream();
			} else {
				inputStream = con.getErrorStream();
			}

			// Process the response
			BufferedReader reader;
			String line = null;
			reader = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = reader.readLine()) != null) {
				System.out.println("---" + line);
			}

			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static KeyStore getKeyStore(String password, String keyStorePath) throws Exception {
		// 实例化密钥库
		KeyStore ks = KeyStore.getInstance("JKS");
		// 获得密钥库文件流
		FileInputStream is = new FileInputStream(keyStorePath);
		// 加载密钥库
		ks.load(is, password.toCharArray());
		// 关闭密钥库文件流
		is.close();
		return ks;
	}

}

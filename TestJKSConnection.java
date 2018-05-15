import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

public class TestJKSConnection {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, KeyManagementException {
        if (args.length != 3) {
            System.out.println("no parameters");
            return;
        };
        KeyStore clientStore = KeyStore.getInstance("JKS");
        clientStore.load(new FileInputStream(args[0]), args[1].toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(clientStore, args[1].toCharArray());
        KeyManager[] kms = kmf.getKeyManagers();
        TrustManager trm = new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
            }
        };
        TrustManager[] tms = new TrustManager[1];
        tms[0] = trm;
        SSLContext sslContext = null;
        sslContext = SSLContext.getInstance("SSL");
        sslContext.init(kms, tms, new SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        URL url = new URL(args[2]);
        try {
            HttpsURLConnection urlConn = (HttpsURLConnection) url.openConnection();
            System.out.println("Response code: " + urlConn.getResponseCode());

            urlConn.disconnect();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

}


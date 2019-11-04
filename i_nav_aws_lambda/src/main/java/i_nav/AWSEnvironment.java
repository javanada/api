package i_nav;

//
//import java.nio.ByteBuffer;
//import java.nio.charset.Charset;
//import java.util.Map;
//
//import com.amazonaws.services.kms.AWSKMS;
//import com.amazonaws.services.kms.AWSKMSClientBuilder;
//import com.amazonaws.services.kms.model.DecryptRequest;
////import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.util.Base64;
//
//public class AWSEnvironment {
//
//    // This variable will hold your decrypted key. Decryption happens on first
//    // invocation when the container is initialized and never again for
//    // subsequent invocations.
////    private static String DECRYPTED_KEY = decryptKey();
////
////    public String myHandler(int someInput, Context context) {
////        // Implement your business logic here
////        // Use DECRYPTED_KEY to refer to the plaintext key
////        return DECRYPTED_KEY;
////    }
//
//    public static String decryptKey(String name) throws Exception {
////        System.out.println("Decrypting key");
//    	if (System.getenv(name) == null) {
//    		Map<String, String> sMap = System.getenv();
//    		String str = "";
//    		for (String s : sMap.keySet()) {
//    			str += s + "=" + sMap.get(s) + "   ";
//    		}
//    		return str;
//    	} else if (System.getenv(name).length() > 0) {
//    		return System.getenv(name);
//    	}
//        byte[] encryptedKey = Base64.decode(System.getenv(name));
//
//        AWSKMS client = AWSKMSClientBuilder.defaultClient();
//
//        DecryptRequest request = new DecryptRequest()
//                .withCiphertextBlob(ByteBuffer.wrap(encryptedKey));
//
//        ByteBuffer plainTextKey = client.decrypt(request).getPlaintext();
//        return new String(plainTextKey.array(), Charset.forName("UTF-8"));
//    }
//
////    public static void main(String args[]){
////        Hello h = new Hello();
////        System.out.println(h.myHandler(1, null));
////    }
//}

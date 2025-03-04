package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Map;

public interface IJWTTokenService {

    RSAKey generateRsaKey();

    String getPublicKey()
            throws NoSuchAlgorithmException;

    String decodePwd(String pwd)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    Map<Integer, String> getKeyPair()
            throws NoSuchAlgorithmException;

    String encrypt(String str,
                   String publicKey)
            throws Exception;

    String decrypt(String str,
                   String privateKey)
            throws Exception;

    String getToken(String userId,
                    String pwd);

    String getToken(String uuid);

    String generateTokenByRSA(String payloadStr,
                              RSAKey rsaKey)
            throws JOSEException;

    String verifyToken(String token,
                       RSAKey rsaKey)
            throws ParseException, JOSEException;

    DecodedJWT verify(String token);

}

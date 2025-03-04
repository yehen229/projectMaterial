package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IJWTTokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.*;


@Service
public class JwtTokenServiceImpl implements IJWTTokenService {

    static final long EXPIRE_TIME = 5 * 60 * 1000;//有效时间5 min
    private static final Logger log =
            LoggerFactory.getLogger(JwtTokenServiceImpl.class);
    /**
     * 密钥长度，DSA算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到65536位之间
     */
    private static final int KEY_SIZE = 1024;
    private static final String SECERT = "world_secret";
    public static String RSA_ALGORITHM = "RSA";
    public static String UTF8 = "UTF-8";
    static RSAPrivateKey privateKey = null;
    static String privateKeyString = null;
    static RSAPublicKey publicKey = null;
    static String publicKeyString = null;

    /**
     * 生成Token信息
     *
     * @return
     */
    public static String getToken(Map<String, String> map) {
        log.info("JwtTokenServiceImpl:getToken");
        JWTCreator.Builder builder = JWT.create();
        // 设置 payload
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        // 设置过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7); // 默认的过期时间是7天
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        return builder.withHeader(header)
                      .withExpiresAt(calendar.getTime())
                      .sign(Algorithm.HMAC256(SECERT));
    }

    /**
     * 验证Token
     *
     * @return DecodedJWT  可以用来获取用户信息
     */
    @Override
    public DecodedJWT verify(String token) {
        // 如果不抛出异常说明验证通过，否则验证失败
        DecodedJWT verify = null;
        try {
            verify = JWT.require(Algorithm.HMAC256(SECERT)).build().verify(token);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verify;
    }

    /**
     * 生成RAS密钥对
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    @Override
    public Map<Integer, String> getKeyPair()
            throws NoSuchAlgorithmException {
        Map<Integer, String> map = new HashMap<>();

        //生成公钥和私钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);

        //初始化密钥生成对，密钥大小为KEY_SIZE位
        keyPairGenerator.initialize(KEY_SIZE);

        //生成一个密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        //获得公钥私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();//私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();//公钥

        //将密钥变成字符串
        String privateKeyString = new String(Base64.getEncoder().encode(privateKey.getEncoded()));
        String publicKeyString = new String(Base64.getEncoder().encode(publicKey.getEncoded()));
        map.put(0, publicKeyString);//0：公钥
        map.put(1, privateKeyString);//1：私钥
        return map;

    }

    /**
     * RAS公钥加密
     *
     * @param str
     * @param publicKey
     * @return
     * @throws Exception
     */
    @Override
    public String encrypt(String str,
                          String publicKey)
            throws Exception {
        byte[] decoded = Base64.getDecoder().decode(publicKey);

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        //初始化公钥,根据给定的编码密钥创建一个新的 X509EncodedKeySpec。
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decoded);
        PublicKey rsaPublicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(UTF8)));

    }

    /**
     * RSA私钥解密
     *
     * @param str
     * @param privateKey
     * @return
     * @throws Exception
     */
    @Override
    public String decrypt(String str,
                          String privateKey)
            throws Exception {
        byte[] inputByte = Base64.getDecoder().decode(str.getBytes(UTF8));
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        PrivateKey rsaPrivateKey = KeyFactory.getInstance(RSA_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
        return new String(cipher.doFinal(inputByte));
    }

    /**
     * 获取密钥对
     *
     * @return RSAKey
     */
    @Override
    public RSAKey generateRsaKey() {
        log.info("JwtTokenServiceImpl:generateRsaKey");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
        //RSA公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //RSA私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey).privateKey(privateKey).build();
    }

    /**
     * 得到公钥,客户端采用此公钥对数据进行加密，服务器使用对应私钥进行解密
     * <p>
     * 注意：整个程序只有一个公钥和私钥，公钥对外公开，私钥不公开
     *
     * @return
     */
    @Override
    public String getPublicKey()
            throws NoSuchAlgorithmException {


        if (publicKey != null && publicKeyString != null)
            return publicKeyString;


        //生成公钥和私钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);

        //初始化密钥生成对，密钥大小为KEY_SIZE位
        keyPairGenerator.initialize(KEY_SIZE);

        //生成一个密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        //获得公钥私钥
        privateKey = (RSAPrivateKey) keyPair.getPrivate();//私钥
        publicKey = (RSAPublicKey) keyPair.getPublic();//公钥

        //将密钥变成字符串
        privateKeyString = new String(Base64.getEncoder().encode(privateKey.getEncoded()));
        publicKeyString = new String(Base64.getEncoder().encode(publicKey.getEncoded()));


        return publicKeyString;
    }

    /**
     * 用户密码解密
     *
     * @param pwd
     * @return
     */
    @Override
    public String decodePwd(String pwd)
            throws UnsupportedEncodingException,
            NoSuchPaddingException,
                   NoSuchAlgorithmException,
            IllegalBlockSizeException,
            BadPaddingException,
                   InvalidKeyException {


        byte[] inputByte = Base64.getDecoder().decode(pwd.getBytes(UTF8));

        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);


        return new String(cipher.doFinal(inputByte));
    }

    @Override
    public String getToken(String userId,
                           String pwd) {
        log.info("JwtTokenServiceImpl:getToken 1");

        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token = JWT.create().withAudience(userId).withExpiresAt(date).sign(Algorithm.HMAC256(pwd));
        return token;
    }

    @Override
    public String getToken(String uuid) {
        log.info("JwtTokenServiceImpl:getToken 2");
        Map<String, String> map = new HashMap<>();
        map.put("tokenId", uuid);
        return getToken(map);
    }

    /**
     * 生成JWT字符串
     *
     * @param payloadStr 作为payload的信息
     * @param rsaKey     密钥对
     * @return jwt字符串
     */
    @Override
    public String generateTokenByRSA(String payloadStr,
                                     RSAKey rsaKey)
            throws JOSEException {
        log.info("JwtTokenServiceImpl:generateTokenByRSA");
        //JWS头
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .build();

        //荷载
        Payload payload = new Payload(payloadStr);
        //签名
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        //生成签名器
        RSASSASigner rsassaSigner = new RSASSASigner(rsaKey);
        jwsObject.sign(rsassaSigner);

        return jwsObject.serialize();
    }

    /**
     * 验签
     *
     * @param token  jwt字符串
     * @param rsaKey rsaKey
     * @return 荷载信息
     * @throws ParseException
     * @throws JOSEException
     */
    @Override
    public String verifyToken(String token,
                              RSAKey rsaKey)
            throws ParseException, JOSEException {
        log.info("JwtTokenServiceImpl:verifyToken");
        //由jwt字符串生成jwsObject对象
        JWSObject jwsObject = JWSObject.parse(token);
        RSAKey publicKey = rsaKey.toPublicJWK();


        RSASSAVerifier verifier = new RSASSAVerifier(publicKey);
        if (!jwsObject.verify(verifier)) {
            return null;    //验证失败则返回空
        }

        return jwsObject.getPayload().toString();
    }
}

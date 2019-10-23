package com.bridgelabz.fundoo.util;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

@Component
public class TokenUtil {

	public static final String TOKEN_SECRET = "BridgeLabz";

	public static String getJWTToken(String email) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

			String token = JWT.create().withClaim("emailId", email).sign(algorithm);
			return token;
		} catch (Exception e) {
			System.out.println("Unable to create JWT Token");
		}
		return null;
	}

	public static String decodeToken(String token) {
		Verification verification = null;
		try {
			verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		JWTVerifier jwtverifier = verification.build();
		DecodedJWT decodedjwt = jwtverifier.verify(token);

		Claim claim = decodedjwt.getClaim("emailId");
		if (claim == null)
			return null;
		return claim.asString();
	}
}

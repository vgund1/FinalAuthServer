package com.mdshkv.md.mediapp.oauth2.authserver.library.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.mdshkv.md.mediapp.oauth2.authserver.library.converter.SerializableObjectConverter;

@Document(collection = "refresh_token")
public class MongoRefreshToken {

    public static final String TOKEN_ID = "tokenId";

    @Id
    private String id;

    private String tokenId;
    private OAuth2RefreshToken token;
    private String authentication;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public OAuth2RefreshToken getToken() {
        return token;
    }

    public void setToken(OAuth2RefreshToken token) {
        this.token = token;
    }

    public OAuth2Authentication getAuthentication() {
        return SerializableObjectConverter.deserialize(authentication);
    }

    public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = SerializableObjectConverter.serialize(authentication);
    }

	@Override
	public String toString() {
		super.toString();
		return "MongoRefreshToken [id=" + id + ", tokenId=" + tokenId + ", token=" + token + ", authentication="
				+ authentication + "]";
	}
    
    
    
    
}
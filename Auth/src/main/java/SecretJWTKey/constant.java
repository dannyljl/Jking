package SecretJWTKey;

import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;

public class constant {

    public static Key key = MacProvider.generateKey();
}

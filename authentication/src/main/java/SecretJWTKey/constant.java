package SecretJWTKey;

import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.crypto.KeyGenerator;
import java.security.Key;

public class constant {

    public static Key key = MacProvider.generateKey();
}

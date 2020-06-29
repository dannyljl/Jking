
-- The encrypted password is `client-secret`
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, authorities, access_token_validity) VALUES ('clientId', '$2a$10$Ych6kpYiY4kPUf8P9wBahexBJk9bS1UYxZDRg8gi71TVHGPkpCEmq', 'read,write', 'password,refresh_token,client_credentials', 'ROLE_CLIENT', 300);

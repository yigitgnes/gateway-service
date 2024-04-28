keytool -genkeypair \
  -storepass client_password \
  -keyalg RSA \
  -keysize 2048 \
  -dname "CN=client" \
  -alias client \
  -ext SAN=DNS:localhost,IP:127.0.0.1 \
  -keystore client.keystore
cp client.keystore \
  ../../../../../../expense-service/src/main/resources/META-INF/resources/client.truststore
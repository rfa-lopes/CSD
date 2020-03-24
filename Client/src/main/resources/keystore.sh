keytool -export -alias walletcert  -file wallet.crt -keystore walletCert.jks
keytool -import -trustcacerts -alias clientcert  -file wallet.crt -keystore client.jks

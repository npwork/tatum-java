# tatum-java

To use tatum library in your project, add following dependency to your Maven's *pom.xml* file:
```
<dependency>
  <groupId>io.tatum</groupId>
  <artifactId>tatum-java</artifactId>
  <version>1.0.4</version>
  <classifier>jar-with-dependencies</classifier>
</dependency>
```

Library provides you these main blockchain functionalities:
- BIP39 - creating/validating mnemonic, convertion to seed
- BIP32 - wallets creation, private key, xpub, xprv, adresses creation
- signing/sending blockchain transactions, checking transactions,...
- BONUS offchain creating and managing users for your own applications

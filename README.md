# tatum-java

To use Tatum library in your project, first you need to add a repository section in your Maven's *pom.xml* file
```
<repositories>
    <!-- ... some other repository definitions -->

    <repository>
      <id>repsy</id>
      <name>Tatumio Maven Repositoty on Repsy</name>
      <url>https://repo.repsy.io/mvn/tatumio/tatumio</url>
    </repository>
  </repositories>
```

then add following dependency
```
<dependencies>
  <!-- ... some other dependency definitions -->

  <dependency>
    <groupId>io.tatum</groupId>
    <artifactId>tatum-java</artifactId>
    <version>2.0.1</version>
  </dependency>
</dependencies>
```

Library provides you these main blockchain functionalities:
- BIP39 - creating/validating mnemonic, convertion to seed
- BIP32 - wallets creation, private key, xpub, xprv, adresses creation
- signing/sending blockchain transactions, checking transactions,...
- BONUS offchain creating and managing users for your own applications

Add your Tatum API-key to your IDE environment variables (or your system variables).
Key name has to be TATUM_API_KEY, value is actual token.

Examples:

`TATUM_API_KEY=replace-with-your-tatum-token`
  
`TATUM_API_KEY=01234ab6-78cd-910e-f111-2g1314151617`

If you plan to run tatum-java tests, for some tests the FAUCET_MNEMONIC environment variable has to be provided.
This mnemonic should contain some funds at index 0 address to be able to successfully send coins in tests.

Example:

`FAUCET_MNEMONIC=surprise ship father sunset hole dizzy must grocery crazy slogan lawsuit special able input jump`

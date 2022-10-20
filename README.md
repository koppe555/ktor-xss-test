# kotor_http_api

## ファイル関連

### build.gradle.kts
ktorサーバーに必要なパッケージなどの依存関係が書いてある。

### main/resources
設定ファイルが入ってる。

### main/kotlin
自動生成されたコードが入ってる。


## 依存関係パッケージ

```
dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
```

### ktor-server-core
コアコンポーネント

### ktor-server-netty
nettyエンジン。外部のアプリケーションコンテナに依存することなくサーバー機能を作れる？

### ktor-server-content-negotiation, ktor-serialization-kotlinx-json
jsonのシリアライザー。kotlinオブジェクトとjsonの変換をよしなにやってくれる。

### logback-classic
いい感じにログ出してくれるやつ。

### ktor-server-test-host, kotlin-test-junit
ユニットテスト用のプラグイン。


## 用語

### netty
非同期のネットワークやり取りをよしなにやってくれるやつ？
後で詳しく調べる。


### ContentNegotiation

ContentNegotiationがヘッダーを確認してjsonならばserializerによってkotlinオブジェクトに変換される。


```
In order for this to work, we need the ContentNegotiation plugin, which is already installed with the json serializer in plugins/Serialization.kt. What does content negotiation do? Let us consider the following request:

GET http://127.0.0.1:8080/customer
Accept: application/json
When a client makes such a request, content negotiation allows the server to examine the Accept header, see if it can serve this specific type of content, and if so, return the result.

JSON support is powered by kotlinx.serialization. We previously used its annotation @Serializable to annotate our Customer data class, meaning that Ktor now knows how to serialize Customers (and collections of Customers!)
```


続きかく
# ktor-xss-test
# ktor-xss-test

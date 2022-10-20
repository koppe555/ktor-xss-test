基本的にこちらの資料から引用
https://www.ipa.go.jp/files/000017316.pdf


# 概要

Webページの出力に対してスクリプトを仕込んで悪用することをXSS（クロスサイトスクリプティング）と呼ぶ。
<img width="575" alt="image" src="https://user-images.githubusercontent.com/44897118/196904733-36549762-bae8-4e84-8efc-8d5a809504ac.png">


悪意ある第三者からスクリプトをサイトに埋め込まれ、そのスクリプトによって別サイトに飛ばされるから「クロスサイト」と名付けられてる。


youtubeやtwtter（tweetdeck）も実例があるくらいメジャーな攻撃方法。
https://www.itmedia.co.jp/enterprise/articles/1007/06/news018.html
https://www.itmedia.co.jp/news/articles/1009/24/news023.html





## 仕込める場所

- style sheet
  - expression()を使っての実行
- URL
  - javascript:<スクリプト>　でURLから実行可能
- charaset
  - ブラウザにUTF-7と認識させる
    - 「「+ADw-script+AD4-alert(+ACI-test+ACI-)+ADsAPA-/script+AD4-」→「「<script>alert('test');</script>」になる
    - エスケープをすり抜けてしまう。


### charasetをもうちょいくわしく

>Content-Type: text/html; charset=UTF-8

charasetを省略してしまうと、ブラウザによってはブラウザ独自の解釈でcharasetを解釈してしまうため危険。テキストの冒頭部分の文字によってcharasetを推測してくれる挙動がある。例えば「+ADw-script+AD4-alert(+ACI-test+ACI-)+ADsAPA-/script+AD4-」の文字を埋め込まれるとcharasetがUTF-7と解釈され、変換後は「<script>alert('test');</script>となる。


<img width="582" alt="image" src="https://user-images.githubusercontent.com/44897118/196904639-b86e7391-112c-4b83-8774-4aedabffd9d5.png">

https://gihyo.jp/dev/serial/01/php-security/0020

charasetを設定すれば防げる。

>3. 外部リソースを指している要素に設定されている charset 属性値したがって、文字コードの指定箇所は、1.の「HTTP ヘッダの Content-Type フィールドの charset パラメータ」であることが望ましいと考えられます。


## 対策

- 基本的な対策はエスケープするscriptを動的に生成しないこと、とにかく意図していないスクリプト実行をjsを防ぐことが対策となる。
- javascript, jsなどを含む文字を排除する方式をブラックリスト方式と呼んでいる。が、ウェブブラウザによっては、「java&#09;script:」や「java(改行コード)script:」等の文字列を「javascript:」と解釈してしまうためあまり有効ではない。





## XSSの分類

### 反射型XSS
攻撃者が罠サイトを用意、そのサイトを被害者が閲覧し、攻撃対象のサーバーに遷移する。

### 持続型XSS
攻撃対象のサーバーにスクリプトが保存されてしまっている。投稿下内容が他のユーザーが見れるサービスに起こる。



## その他

googleがxssを学べるサイトを出してる。
https://xss-game.appspot.com/

解説
https://sagarvd01.medium.com/learning-xss-with-googles-xss-game-f44ff8ee3d8b




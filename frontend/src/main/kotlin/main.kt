
package frontend

import org.w3c.xhr.XMLHttpRequest
import react.dom.*
import kotlin.browser.document
import kotlin.browser.window

import frontend.components.*

fun parseInterests(param: String):List<String> = param.split(",").map { it.trim() }
fun stringifyInterests(param: List<String>): String = param.joinToString("," )


fun main(args: Array<String>) {
    window.onload = {
        val root = document.getElementById("root") ?: throw IllegalStateException()
        val request = XMLHttpRequest()

        request.open("GET", "/interests", true)
        request.onload = {
            console.log(request.responseText)
            val parsed = parseInterests(request.responseText)

            render(root) {
                application(parsed)
            }
        }
        request.send()
    }
}
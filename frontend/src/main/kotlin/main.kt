
package frontend

import react.dom.*
import kotlin.browser.document
import kotlin.browser.window

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialId
import kotlinx.serialization.Serializable
import org.w3c.xhr.XMLHttpRequest

import frontend.components.*
import kotlinx.css.CSSBuilder
import kotlinx.css.body
import kotlinx.css.px
import styled.StyledComponents

/*

@Serializable
data class InterestList(@SerialId(1) @Optional val list: List<String> = emptyList())
@Serializable
data class Idea(@SerialId(1) val text: String, @SerialId(2) val interests: InterestList)
*/
fun parseInterests(param: String):List<String> = param.split(",").map { it.trim() }
fun stringifyInterests(param: List<String>): String = param.joinToString("," )

fun foo(): Int {
    return 10
}

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

package frontend

import react.dom.*
import kotlin.browser.document
import kotlin.browser.window

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialId
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import org.w3c.xhr.XMLHttpRequest

import frontend.components.*


@Serializable
data class InterestList(@SerialId(1) @Optional val list: List<String> = emptyList())
@Serializable
data class Idea(@SerialId(1) val text: String, @SerialId(2) val interests: InterestList)

fun parseResponse(response: String):InterestList {
    console.log("Parsing interest list: $response !!!")
    return JSON.parse<InterestList>(response)
}

fun foo(): Int {
    return 10
}

fun main(args: Array<String>) {
    window.onload = {
        val root = document.getElementById("root") ?: throw IllegalStateException()
        val request = XMLHttpRequest()

        request.open("GET", "/interests", true)
        request.onload = {
            val parsed = parseResponse(request.responseText).list

            render(root) {
                application(parsed)
            }

        }
        request.send()

    }
}
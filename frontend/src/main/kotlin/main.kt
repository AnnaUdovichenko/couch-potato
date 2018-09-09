
import react.dom.*
import kotlin.browser.document
import kotlin.browser.window
import components.*
import kotlinx.serialization.Optional
import kotlinx.serialization.SerialId
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON

import wrappers.*

@Serializable
data class InterestList(@SerialId(1) @Optional val list: List<String> = emptyList())

fun main(args: Array<String>) {
    window.onload = {
        val root = document.getElementById("root") ?: throw IllegalStateException()
        val request = XMLHttpRequest()

        request.open("GET", "/interests", true)
        request.onload = {

            val parsed = JSON.parse<InterestList>(request.response.toString()).list

            render(root) {
                checklist(parsed)
            }
        }
        request.send()

    }
}
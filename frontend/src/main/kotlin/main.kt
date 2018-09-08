
import react.*
import react.dom.*
import kotlin.browser.document
import kotlin.browser.window
import components.*


fun main(args: Array<String>) {
    window.onload = {
        val root = document.getElementById("root") ?: throw IllegalStateException()
        render(root) {
            checklist(arrayOf("abcd", "bcde", "cdef"))
        }
    }
}
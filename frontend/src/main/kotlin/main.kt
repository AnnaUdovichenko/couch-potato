
import react.*
import react.dom.*
import kotlin.browser.document
import kotlin.browser.window
import components.*

interface WelcomeProps: RProps {
    var name: String
}
 
class Welcome: RComponent<WelcomeProps, RState>() {
     override fun RBuilder.render() {
        div {
            +"Hello, ${props.name}"
        }
    }
}

 
fun RBuilder.welcome(name: String = "John") = child(Welcome::class) {
    attrs.name = name
}

fun main(args: Array<String>) {
    window.onload = {
        val root = document.getElementById("root") ?: throw IllegalStateException()
        render(root) {
            welcome("Anuta")
            checklist(arrayOf("a", "b", "c"))
        }
    }
}
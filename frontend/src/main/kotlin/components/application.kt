package frontend.components

import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.asList
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.*

interface ApplicationProps: RProps {
    var interests: List<String>
    var text: String
}

interface ApplicationState: RState{
    var interests: List<String>
    var suggestedIdea: String
}

class Application: RComponent<ApplicationProps, ApplicationState>() {
    override fun RBuilder.render() {
        div{
            form{
                attrs.onSubmitFunction = {
                    console.log("Submitted!")
                }
                label { +"Select your list:"}
                br{}
                selectComponent(props.interests, props.text)
            }

        }
    }
}

fun RBuilder.application(interests: List<String>) = child(Application::class){
    attrs.interests = interests
}


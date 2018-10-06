package frontend.components

import frontend.InterestList
import org.w3c.dom.asList
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.*
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLSelectElement


interface ApplicationProps: RProps {
    var interests: List<String>
}

interface ApplicationState: RState{
    var suggestedIdea: String
    var selected: List<String>
}

class Application: RComponent<ApplicationProps, ApplicationState>() {
    override fun RBuilder.render() {
        div{
            getForm(props.interests)
            suggestForm(props.interests)
        }
    }
}

fun RBuilder.application(interests: List<String>) = child(Application::class){
    attrs.interests = interests
}


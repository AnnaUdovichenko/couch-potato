package frontend.components

import react.*
import react.dom.*


interface ApplicationProps: RProps {
    var interests: List<String>
}

class Application: RComponent<ApplicationProps, RState>() {
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


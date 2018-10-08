package frontend.components

import kotlinx.css.*
import react.*
import styled.styledDiv
import styled.css

interface ApplicationProps: RProps {
    var interests: List<String>
}

class Application: RComponent<ApplicationProps, RState>() {
    override fun RBuilder.render() {
        styledDiv{
            css{
                put("border-radius", "5px")
                backgroundColor = Color("#f2f2f2")
                padding (20.px)
            }
            getForm(props.interests)
            suggestForm(props.interests)
        }
    }
}

fun RBuilder.application(interests: List<String>) = child(Application::class){
    attrs.interests = interests
}


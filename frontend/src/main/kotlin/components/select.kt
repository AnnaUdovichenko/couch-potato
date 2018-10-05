package frontend.components

import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.asList
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.*

interface SelectProps: RProps {
    var interests: List<String>
    var ideaText: String
}
interface SelectState: RState{
    var interests: List<String>
    var ideaText: String
}

class SelectComponent: RComponent<SelectProps, SelectState>(){
    override fun RBuilder.render(){
    }
}

fun RBuilder.selectComponent(interests: List<String>, text: String) = child(SelectComponent::class){

    attrs.ideaText = text
    attrs.interests = interests

}
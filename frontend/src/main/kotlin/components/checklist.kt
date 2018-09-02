package components

import react.*
import react.dom.*

interface OptionsProps: RProps {
    var options: Array<String>
}

interface InterestProps : RProps{
    var text: String
}

interface  InterestState: RState{
    var checked: Boolean
}


class Interest: RComponent<InterestProps, InterestState>(){
    override fun RBuilder.render(){
        p{+props.text }
    }
}

fun RBuilder.interest(text: String) = child(Interest::class){
    attrs.text = text
}

class CheckList: RComponent<OptionsProps, RState>(){
    override fun RBuilder.render(){
        div{
                props.options.map{
                    interest(it)
                }
            }

    }
}

fun RBuilder.checklist(options: Array<String>) = child(CheckList::class){
    attrs.options = options
}
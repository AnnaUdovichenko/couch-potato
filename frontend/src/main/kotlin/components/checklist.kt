package components

import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import kotlinx.html.onSubmit
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.asList
import react.*
import react.dom.*


interface OptionsProps: RProps {
    var options: List<String>
}
interface OptionsState: RState{
    var value: List<String>
}


class CheckList: RComponent<OptionsProps, OptionsState>(){
    override fun RBuilder.render(){
        div{
            form{
                attrs.onSubmitFunction = {
                    console.log("Submitted!")
                    }
                label { +"Select your interests:"}
                br{}
                select {
                    attrs.id = "interests"
                    attrs.multiple = true
                    console.log("Render select, ${state.value}")

                    attrs.onChangeFunction = {
                        val target = it.target as HTMLSelectElement
                        setState {
                            value = target.selectedOptions.asList().map { it.toString() }
                        }
                    }
                    props.options.map {
                        option {
                            attrs {
                                value = it
                            }
                            +it
                        }
                    }
                }
                br{}
                input {
                    attrs {
                        type = InputType.submit
                        value = "Submit"
                        name = "interests"
                        list = "interests"
                        onClickFunction = {
                            console.log("Clicked!")
                        }
                        /*onSubmitFunction = {
                            console.log("Submitted!")
                        }*/
                    }
                }
            }
        }
    }
}

fun RBuilder.checklist(options: List<String>) = child(CheckList::class){
    attrs.options = options
}

/*fun RBuilder.alert(message: String = "") = if (message.isNotEmpty()) {
    div("alert alert-danger") {
        +message
    }
} else {
    div{}
}*/
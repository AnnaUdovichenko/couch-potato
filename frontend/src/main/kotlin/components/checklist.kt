package frontend.components

import frontend.parseResponse
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import kotlinx.html.onSubmit
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.asList
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.*
import wrappers.encodeURIComponent


interface OptionsProps: RProps {
    var options: List<String>
}
interface OptionsState: RState{
    var value: List<String>
    var ideaText: String
}

interface IdeaProps: RProps{
    var text: String
}

interface IdeaState: RState{
    var text: String
}

class IdeaComponent: RComponent<IdeaProps, IdeaState>() {
    override fun RBuilder.render() {
        div {
            +props.text
        }
    }
}

class CheckList: RComponent<OptionsProps, OptionsState>(){
    override fun RBuilder.render(){
        div{
            form{
                attrs.onSubmitFunction = {
                    console.log("Submitted!")
                    }
                label { +"Select your list:"}
                br{}
                select {
                    attrs.id = "list"
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
                        value = "Get random idea!"
                        name = "idea"
                        list = "list"
                        onSubmitFunction = {

                            val interests = JSON.stringify(frontend.InterestList(props.options))
                            val url = "/idea:" + encodeURIComponent(interests)
                            val request = XMLHttpRequest()
                            console.log("Submitted! $url")
                            request.open("GET", url, true)
                            request.onload = {

                                val idea = JSON.parse<frontend.Idea>(request.response.toString())
                                setState{
                                    ideaText = idea.text
                                }
                                console.log("Parsed an idea: $idea")
                            }
                            request.send()

                        }
                    }
                }
                ideaComponent(state.ideaText)
            }
        }
    }
}

fun RBuilder.checklist(options: List<String>) = child(CheckList::class){
    attrs.options = options
}

fun RBuilder.ideaComponent(options: String) = child(IdeaComponent::class){
    attrs.text = options
}

/*fun RBuilder.alert(message: String = "") = if (message.isNotEmpty()) {
    div("alert alert-danger") {
        +message
    }
} else {
    div{}
}*/
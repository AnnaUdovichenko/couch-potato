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
            form{

                label { +"Select your list:"}
                br{}
                select{
                    attrs.id = "interests"
                    attrs.multiple = true
                    // console.log("Render select, ${state.selected}")

                    attrs.onChangeFunction = {
                        val target = it.target as HTMLSelectElement
                        setState {
                            selected = target.selectedOptions.asList().map{it.getAttribute("value")?:""}
                        }
                    }

                    props.interests.map {
                        option {
                            attrs {
                                value = it
                                label = it
                            }
                        }
                    }
                }
                br{}
                input {
                    attrs {
                        type = InputType.button
                        value = "Submit"
                        name = "interests"
                        list = "interests"
                        onClickFunction = {
                            console.log("Submitted!")
                            requestIdea {
                                setState {
                                    suggestedIdea = it
                                }
                            }
                        }
                    }
                }
                br{}
                label{
                    +state.suggestedIdea
                }
            }

        }
    }
    fun requestIdea(callback: (String) -> Unit) {
        val request = XMLHttpRequest()
        val selected = state.selected
        val params = JSON.stringify(InterestList(selected))
        val url = "idea?interests=$params"

        request.open("GET", url, true)
        request.onload = {
            callback(request.response.toString())
        }
        request.send()
    }
}

fun RBuilder.application(interests: List<String>) = child(Application::class){
    attrs.interests = interests
}


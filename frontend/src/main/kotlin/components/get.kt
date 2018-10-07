package frontend.components

import kotlinx.css.padding
import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.asList
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.*
import styled.*


interface GetFormProps: RProps {
    var interests: List<String>
}

interface GetFormState: RState {
    var suggestedIdea: String
    var selected: List<String>
}

class GetForm: RComponent<GetFormProps, GetFormState>() {
    override fun componentWillMount(){
        setState{
            selected = emptyList()
        }
    }
    override fun RBuilder.render() {
        styledDiv{

            styledH2{
                +"Don't know what to do? We can give an advice!" }
            form{
                label { +"Select your interests:"}
                br{}
                styledSelect{
                    css {
                        //width = 400.px
                        padding(12.px, 12.px)
                        margin(10.px, 0.px)
                        put("display", "inline-block")
                        put("box-sizing", "border-box")
                    }
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
                styledInput {
                    css {
                        //width = 400.px
                        backgroundColor = Color("#4CAF50")
                        color = Color.white
                        padding(14.px, 20.px)
                        margin( 8.px, 0.px)
                        border = "none"
                        put("border-radius", "4px")
                        cursor = Cursor.pointer
                        hover {
                            backgroundColor = Color("#45a049")
                        }

                    }
                    attrs {
                        type = InputType.button
                        value = "Get advice!"
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
                br{}
            }
        }
    }
    fun requestIdea(callback: (String) -> Unit) {
        val request = XMLHttpRequest()
        val selected = state.selected.joinToString("," )
        val url = "idea?interests=$selected"
        console.log("!!!$url")

        request.open("GET", url, true)
        request.onload = {
            callback(request.response.toString())
        }
        request.send()
    }
}

fun RBuilder.getForm(interests: List<String>) = child(GetForm::class){
    attrs.interests = interests
}

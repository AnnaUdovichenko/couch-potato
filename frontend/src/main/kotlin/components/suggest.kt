package frontend.components

import org.w3c.dom.asList
import org.w3c.xhr.XMLHttpRequest
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.HTMLTextAreaElement
import react.*
import react.dom.*
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.css.*
import styled.css
import styled.styledInput
import styled.styledSelect
import styled.styledTextArea

import wrappers.*
import frontend.stringifyInterests

interface SuggestFormProps: RProps {
    var interests: List<String>
}

interface SuggestFormState: RState {
    var text: String
    var selected: List<String>
    var status: String
}

class SuggestForm: RComponent<SuggestFormProps, SuggestFormState>() {
    override fun componentWillMount(){
        setState{
            selected = emptyList()
            text = ""
        }
    }
    override fun RBuilder.render() {
        div {
            h2{ +"Don't like our ideas? Suggest yours!" }
            form {
                label {
                    +"Describe the activity you'd like to suggest"
                }
                br{}
                styledTextArea {
                    css {
                        width = 200.px
                        padding (12.px)
                        margin(10.px, 0.px)
                        borderStyle = BorderStyle.solid
                        borderRadius = 4.px
                        put("resize", "vertical")
                    }
                    attrs.required = true
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLTextAreaElement
                        setState {
                            text = target.value
                        }
                    }
                }
                br{}
                label {
                    +"Tag interests connected to the activity"
                }
                br{}
                styledSelect{
                    css {
                        width = 200.px
                        padding(5.px, 12.px)
                        margin(10.px, 0.px)
                        put("display", "inline-block")
                        put("box-sizing", "border-box")
                    }
                    attrs.id = "interests"
                    attrs.multiple = true
                    attrs.required = true

                    attrs.onChangeFunction = {
                        val target = it.target as HTMLSelectElement
                        setState {
                            selected = target.selectedOptions.asList().map{
                                it.getAttribute("value")?:""}
                        }
                    }
                    props.interests.map {
                        option {
                            attrs.value = it
                            +it
                        }
                    }
                }
                br{}
                styledInput {
                    css {
                        width = 200.px
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
                        value = "Suggest"
                        name = "suggest"
                        onClickFunction = {
                            postIdea{
                                setState{
                                    status = it
                                }
                            }
                        }
                    }
                }
                br{}
                label{
                    +state.status
                }
            }
        }
    }
    fun postIdea(callback: (String) -> Unit) {
        val request = XMLHttpRequest()
        val selected = state.selected
        val text = state.text

        if (selected.isEmpty() || text.isBlank()) {
            callback("Please, tag some interests and fill in the text")
            return
        }
        val interests = stringifyInterests(selected)
        val param = "text=${encodeURIComponent(text)}&interests=${encodeURIComponent(interests)}"
        val url = "/idea"

        request.open("POST", url, true)
        request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")

        request.onreadystatechange = {
            if (request.readyState == XMLHttpRequest.DONE &&
                    request.status == 200.toShort()){
                callback("We've added your idea. Thank you!")
            }
            else{
                callback("Sorry. Something went wrong")
            }
        }
        request.send(param)
    }
}

fun RBuilder.suggestForm(interests: List<String>) = child(SuggestForm::class){
    attrs.interests = interests
}

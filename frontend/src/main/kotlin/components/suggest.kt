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

import wrappers.*
import frontend.InterestList
import frontend.Idea

interface SuggestFormProps: RProps {
    var interests: List<String>
}

interface SuggestFormState: RState {
    var text: String
    var selected: List<String>
    var status: String
}

class SuggestForm: RComponent<SuggestFormProps, SuggestFormState>() {
    override fun RBuilder.render() {
        div {
            h2{ +"Don't like our ideas? Suggest yours!" }
            form {

                label {
                    +"Describe the activity you'd like to suggest"
                }
                br{}
                textArea {
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
                select{
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
        val interests = JSON.stringify(InterestList(selected))
        val param = "text=${encodeURIComponent(text)}&interests=${encodeURIComponent(interests)}"
        val url = "/idea"

        request.open("POST", url, true)
        request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")

        request.onreadystatechange = {
            if (request.readyState == XMLHttpRequest.DONE &&
                    request.status == 200.toShort()){
                callback("The idea was successfully saved")
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

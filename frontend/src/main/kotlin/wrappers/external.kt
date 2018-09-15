package wrappers

open external class Event

open external class XMLHttpRequest{
    fun open(method: String,
             url: String,
             async: Boolean,
             username: String? = definedExternally,
             password: String? = definedExternally)
    fun send(body: dynamic = definedExternally)
    open var onload: (Event) -> dynamic
    open val response: Any
}
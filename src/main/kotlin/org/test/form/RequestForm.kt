package org.test.form

/**
 * @author Anton Kurinnoy
 */
data class RequestForm(val expression: String)

data class RequestListForm(val expressions: List<String>)
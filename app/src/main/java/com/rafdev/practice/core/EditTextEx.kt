package com.rafdev.practice.core

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText

fun EditText.onTextChanged(listener: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            listener(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.loseFocusAfterAction(action: Int) {
    Log.i("texto", "${action}")
    this.setOnEditorActionListener { v, actionId, _ ->
        if (actionId == action) {
            Log.i("texto", "actionid${actionId}")
            Log.i("texto", "actioninifd${actionId}")


            this.dismissKeyboard()
            v.clearFocus()
        }
        return@setOnEditorActionListener false
    }
}
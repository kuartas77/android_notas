package com.sofdreamc.notas.common

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class Utils {
    companion object{
        private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
            this.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    afterTextChanged.invoke(s.toString())
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            })
        }

        fun EditText.validate(message: String, parent: TextInputLayout, validator: (String) -> Boolean) {
            this.afterTextChanged {
                parent.error = if (validator(it.trim())) null else message
                parent.isErrorEnabled = !validator(it.trim())
            }
            parent.error = if (validator(this.text.toString().trim())) null else message
            parent.isErrorEnabled = !validator(this.text.toString().trim())
        }
    }
}
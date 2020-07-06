package com.sofdreamc.notas.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sofdreamc.notas.R
import com.sofdreamc.notas.common.Utils.Companion.validate
import com.sofdreamc.notas.data.local.models.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_note.view.*

@AndroidEntryPoint
class NoteDialog : DialogFragment() {

    private val viewModel: NoteViewModel by viewModels()
    private val args: NoteDialogArgs by navArgs()
    private var note: Note? = null
    private var id: Int? = null
    private lateinit var dialogView: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = this.layoutInflater
        dialogView = inflater.inflate(R.layout.dialog_note, null)
        builder.setView(dialogView)

        note = args.note
        note?.let { note ->
            id = note.id
            dialogView.apply {
                txt_title_dialog.text = getString(R.string.title_edit_note)
                txt_input_title.setText(note.title)
                txt_input_content.setText(note.content)
                check_important.isChecked = note.important
                check_done.isChecked = note.done
            }
        } ?: run {
            dialogView.apply {
                txt_title_dialog.text = getString(R.string.title_create_note)
            }
        }

        dialogView.btn_accept.setOnClickListener {
            dialogView.txt_input_title.validate("El título es requerido.",
                dialogView.layout_txt_input_title){ s -> s.isNotEmpty() }

            dialogView.txt_input_content.validate("El título es requerido.",
                dialogView.layout_txt_input_content) { s -> s.isNotEmpty() }

            if (formIsValid()) {
                val title = dialogView.txt_input_title.text.toString()
                val content = dialogView.txt_input_content.text.toString()
                val important = dialogView.check_important.isChecked
                val done = dialogView.check_done.isChecked
                val note = Note(id, title, content, important, done)
                viewModel.upsertNote(note)
                dismiss()
            }
        }

        dialogView.btn_cancel.setOnClickListener {
            dismiss()
        }

        return builder.create()
    }

    private fun formIsValid(): Boolean {
        return when {
            dialogView.layout_txt_input_title.isErrorEnabled -> false
            dialogView.layout_txt_input_content.isErrorEnabled -> false
            else -> true
        }
    }

}
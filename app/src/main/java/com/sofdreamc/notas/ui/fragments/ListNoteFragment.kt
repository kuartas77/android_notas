package com.sofdreamc.notas.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import com.sofdreamc.notas.R
import com.sofdreamc.notas.adapters.NoteAdapter
import com.sofdreamc.notas.common.RecyclerItemTouchHelper
import com.sofdreamc.notas.data.local.models.Note
import com.sofdreamc.notas.ui.interfaces.RecyclerItemTouchHelperListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.empty.*
import kotlinx.android.synthetic.main.list_note_fragment.*


@AndroidEntryPoint
class ListNoteFragment : Fragment(R.layout.list_note_fragment), RecyclerItemTouchHelperListener {

    private val viewModel: NoteViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupRecyclerView()
        setupViewModel()
        setupView()

        val itemTouchHelperCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rv_notes)
        }
    }

    private fun setupView() {
        image_empty.setOnClickListener {
            navigateToDialog(null)
        }
        btn_float_add_note.setOnClickListener {
            navigateToDialog(null)
        }
    }

    private fun navigateToDialog(note: Note?) {
        val bundle = Bundle().apply { putParcelable("note", note) }
        findNavController().navigate(R.id.action_listNoteFragment_to_noteDialog, bundle)
    }

    private fun setupViewModel() {
        viewModel.getAllNotes().observe(viewLifecycleOwner, Observer { notes ->
            showLoading()
            if (notes.isEmpty()) {
                showHideEmpty(true)
            } else {
                showHideEmpty(false)
            }
            hideLoading()
            noteAdapter.submitList(notes)
        })
    }

    private fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progress_bar.visibility = View.INVISIBLE
    }

    private fun showHideEmpty(show: Boolean) {
        if (show) {
            layout_empty.visibility = View.VISIBLE
            rv_notes.visibility = View.GONE
        } else {
            layout_empty.visibility = View.GONE
            rv_notes.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView() {
        
        noteAdapter = NoteAdapter()
        rv_notes.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
        noteAdapter.setOnItemClickListener {
            navigateToDialog(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView: SearchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                noteAdapter.filter.filter(newText)
                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        val note = noteAdapter.differ.currentList[position]
        viewModel.deleteNote(note)
        Snackbar.make(
            viewHolder.itemView,
            getString(R.string.delete_success),
            Snackbar.LENGTH_SHORT
        )
            .apply {
                setAction(getString(R.string.undo)) {
                    viewModel.upsertNote(note)
                }
                show()
            }
    }
}
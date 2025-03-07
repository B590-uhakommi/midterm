package com.example.midterm_section2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.midterm_section2.databinding.FragmentNoteBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import java.util.*

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

    // This will store the ID of the note for editing an existing note
    private var noteId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)

        // Check if we're editing an existing note
        noteId = arguments?.getString("noteId")

        if (noteId != null) {
            // Fetch note from Firestore for editing
            loadNoteData()
        }

        binding.btnSave.setOnClickListener {
            saveNote()
        }

        binding.btnBack.setOnClickListener {
            // Navigate back without saving
            findNavController().navigate(R.id.action_noteFragment_to_postsFragment)
        }

        return binding.root
    }

    private fun loadNoteData() {
        db.collection("notes").document(noteId!!)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val title = document.getString("title") ?: ""
                    val description = document.getString("description") ?: ""
                    binding.etTitle.setText(title)
                    binding.etDescription.setText(description)
                }
            }
            .addOnFailureListener {
                // Handle error (e.g., show a Toast or log the error)
            }
    }

    private fun saveNote() {
        val title = binding.etTitle.text.toString()
        val description = binding.etDescription.text.toString()

        if (title.isEmpty() || description.isEmpty()) {
            // Show error if fields are empty
            binding.etTitle.error = "Title is required"
            binding.etDescription.error = "Description is required"
            return
        }

        // Add timestamp to the note data
        val noteData = hashMapOf(
            "title" to title,
            "description" to description,
            "timestamp" to Timestamp(Date()) // Add current timestamp
        )

        if (noteId == null) {
            // Add a new note
            db.collection("notes").add(noteData)
                .addOnSuccessListener {
                    // Navigate back to PostsFragment
                    findNavController().navigate(R.id.action_noteFragment_to_postsFragment)
                }
                .addOnFailureListener {
                    // Handle failure
                }
        } else {
            // Update the existing note
            db.collection("notes").document(noteId!!).set(noteData)
                .addOnSuccessListener {
                    findNavController().navigate(R.id.action_noteFragment_to_postsFragment)
                }
                .addOnFailureListener {
                    // Handle failure
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

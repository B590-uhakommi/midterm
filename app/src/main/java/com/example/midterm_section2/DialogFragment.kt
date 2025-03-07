package com.example.midterm_section2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FirebaseFirestore

class DialogFragment(private val postId: String) : DialogFragment() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dialog, container, false)

        val btnConfirmDelete = view.findViewById<Button>(R.id.btnConfirmDelete)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)

        btnConfirmDelete.setOnClickListener {
            deletePostFromFirestore(postId)
            dismiss()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }

        return view
    }

    private fun deletePostFromFirestore(postId: String) {
        // Delete the post from Firestore
        db.collection("notes").document(postId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(context, "Post deleted successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error deleting post: $exception", Toast.LENGTH_SHORT).show()
            }
    }
}

package com.example.midterm_section2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.midterm_section2.databinding.FragmentPostsBinding
import com.example.midterm_section2.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.auth.FirebaseAuth
import androidx.recyclerview.widget.RecyclerView
class PostsFragment : Fragment(), PostAdapter.OnItemClickListener {  // Implement OnItemClickListener

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()
    private lateinit var postAdapter: PostAdapter
    private val posts = mutableListOf<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)

        // Setup RecyclerView
        postAdapter = PostAdapter(posts, this,requireActivity())  // Pass this fragment as the listener
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = postAdapter

        // Fetch posts from Firestore
        fetchPostsFromFirestore()

        // Handle logout
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.loginFragment)
        }

        // Handle Add Note button click
        binding.addNoteButton.setOnClickListener {
            findNavController().navigate(R.id.action_postsFragment_to_noteFragment)
        }

        return binding.root
    }

    private fun fetchPostsFromFirestore() {
        // Fetch posts from Firestore and sort by timestamp (newest first)
        db.collection("notes")
            .orderBy("timestamp", Query.Direction.DESCENDING) // Order by timestamp (newest first)
            .get()
            .addOnSuccessListener { documents ->
                if (documents != null && !documents.isEmpty) {
                    posts.clear() // Clear existing posts
                    for (document in documents) {
                        val post = document.toObject(Post::class.java) // Convert document to Post object
                        post.id = document.id // Assign document ID to Post's id field
                        posts.add(post) // Add post to the list
                    }
                    postAdapter.notifyDataSetChanged() // Notify adapter to update the RecyclerView
                } else {
                    Toast.makeText(requireContext(), "No posts found.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Error getting posts: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    // Implement the onItemClick method from OnItemClickListener
    override fun onItemClick(documentId: String, updatedTitle: String, updatedDescription: String) {
        // Update the post in Firestore using the document ID
        val postRef = db.collection("notes").document(documentId)

        // Update fields in the document
        postRef.update(
            "title", updatedTitle,
            "description", updatedDescription
        ).addOnSuccessListener {
            Toast.makeText(requireContext(), "Post updated successfully", Toast.LENGTH_SHORT).show()

            // Update the local posts list to reflect the changes
            val updatedPost = Post(documentId, updatedTitle, updatedDescription) // Create an updated Post
            val position = posts.indexOfFirst { it.id == documentId } // Find the position of the post in the list
            if (position != -1) {
                posts[position] = updatedPost // Update the post in the list
                postAdapter.notifyItemChanged(position) // Notify the adapter to update the UI
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(requireContext(), "Error updating post: $exception", Toast.LENGTH_SHORT).show()
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


package com.example.midterm_section2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midterm_section2.databinding.ListItemPostBinding
import com.example.midterm_section2.model.Post
import android.view.View
import androidx.fragment.app.FragmentActivity


class PostAdapter(
    private val posts: MutableList<Post>,
    private val listener: OnItemClickListener,
    private val activity: FragmentActivity
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(documentId: String, updatedTitle: String, updatedDescription: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ListItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = posts.size
    fun updatePost(position: Int, updatedPost: Post) {
        posts[position] = updatedPost
        notifyItemChanged(position) // Notify the adapter that the item has changed
    }
    inner class PostViewHolder(private val binding: ListItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            binding.tvTitle.text = post.title
            binding.tvContent.text = post.description

            // Set EditText fields for title and description
            binding.etTitle.setText(post.title)
            binding.etContent.setText(post.description)

            // Hide EditText by default
            binding.etTitle.visibility = View.GONE
            binding.etContent.visibility = View.GONE
            binding.tvTitle.visibility = View.VISIBLE
            binding.tvContent.visibility = View.VISIBLE

            // Show EditText on item click for in-place editing
            itemView.setOnClickListener {
                // Toggle visibility of TextViews and EditTexts
                binding.tvTitle.visibility = View.GONE
                binding.tvContent.visibility = View.GONE
                binding.etTitle.visibility = View.VISIBLE
                binding.etContent.visibility = View.VISIBLE
            }

            // Update Firestore when focus changes
            binding.etTitle.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val updatedTitle = binding.etTitle.text.toString()
                    val updatedDescription = binding.etContent.text.toString()
                    listener.onItemClick(post.id, updatedTitle, updatedDescription)

                    // After saving, remove focus and hide EditText fields
                    clearFocusAndHideEditText()
                }
            }

            binding.etContent.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val updatedTitle = binding.etTitle.text.toString()
                    val updatedDescription = binding.etContent.text.toString()
                    listener.onItemClick(post.id, updatedTitle, updatedDescription)

                    // After saving, remove focus and hide EditText fields
                    clearFocusAndHideEditText()
                }
            }
            binding.btnDelete.setOnClickListener {
                showDeleteConfirmationDialog(post.id)
            }
        }

        private fun clearFocusAndHideEditText() {
            // Clear focus from EditText
            binding.etTitle.clearFocus()
            binding.etContent.clearFocus()

            // Switch back to normal view
            binding.tvTitle.visibility = View.VISIBLE
            binding.tvContent.visibility = View.VISIBLE
            binding.etTitle.visibility = View.GONE
            binding.etContent.visibility = View.GONE
        }
        private fun showDeleteConfirmationDialog(postId: String) {
            // Create and show a DialogFragment
            val dialog = DialogFragment(postId)
            dialog.show(activity.supportFragmentManager, "deleteDialog")
        }
    }
}

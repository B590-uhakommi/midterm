package com.example.midterm_section2

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.core.R
import com.example.midterm_section2.network.GitHubApi
import com.example.midterm_section2.network.GitHubFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "PhotoRepository"

class PhotoRepository private constructor(
    context: Context?,
    private val coroutineScope: CoroutineScope = GlobalScope
) {
    private val githubApi: GitHubApi
    private val token = "Bearer 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"
    private val owner = "B590-uhakommi"
    private val repo = "midterm--section2-part3-photostore"
    private val branch = "main"
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        githubApi = retrofit.create(GitHubApi::class.java)
    }

    suspend fun saveImage(base64Image: String, filename: String) {
        // Use withContext for calling the suspend function from non-suspend code
        withContext(Dispatchers.IO) {
            uploadImageToGitHub(base64Image, filename)
        }
    }

    // Companion object to provide a singleton instance
    companion object {

        private var INSTANCE: PhotoRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE=PhotoRepository(context)

                    }
                }

        fun get(): PhotoRepository {
            if (INSTANCE == null) {
                INSTANCE=PhotoRepository(null)

            }
            return INSTANCE!!
        }
    }

    // Function to upload an image to GitHub
   private suspend fun uploadImageToGitHub(base64Image: String, filename: String) {
        val path = "$filename"

        val file = GitHubFile(
            message = "Add $filename",
            content = base64Image
        )

        try {
            val response = githubApi.uploadFile(token, owner, repo, path, file)
            if (response.isSuccessful) {
                Log.d(TAG, "File uploaded successfully: ${response.body()?.content?.path}")
            } else {
                Log.e(TAG, "Error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun getImageUrl(filename: String):String{
        return "http://raw.githubsercontent.com/${owner}/${repo}/${branch}/${filename}\n"
    }

}

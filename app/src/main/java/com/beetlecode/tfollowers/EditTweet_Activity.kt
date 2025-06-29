package com.beetlecode.tfollowers

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.beetlecode.tfollowers.database.MyDatabase
import com.beetlecode.tfollowers.database.MyRepository
import com.beetlecode.tfollowers.database.Post
import com.bumptech.glide.Glide
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class EditTweet_Activity : AppCompatActivity() {

    private lateinit var cardView: CardView
    private lateinit var Xtext: TextView
    private lateinit var prImage: ImageView
    private lateinit var imageView: ImageView
    private lateinit var image_view_in_AET: ImageView
    private lateinit var sharedPreferences: SharedPreferences

    private var post_image_uri: String? = null
    private var name: String? = null
    private var handlername: String? = null
    private var isVerfied: Boolean = false
    private var posttext: String? = null
    private var Viewt: String? = null
    private var comment:String? = null
    private var retweet:String? = null
    private var like:String? = null
    private var viewtweet:String? = null

    private var prUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tweet)

        val db = MyDatabase(this)
        val repo = MyRepository(db.getMyDao())
        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE)
        prImage = findViewById(R.id.profile_image_inETA)
        prUri = sharedPreferences.getString("primage", null)
        name = sharedPreferences.getString("name", null)
        handlername = sharedPreferences.getString("handle", null)
        isVerfied = sharedPreferences.getBoolean("verified", false)


        prUri?.let {
//            val uu = FileUtils().getFile(this, it.toUri())
            Glide.with(this).load(it).into(prImage)
        }
        cardView = findViewById(R.id.card_view2)
        val tweet: TextView = findViewById(R.id.TVpublishTweet)
        val postText: EditText = findViewById(R.id.ETETA)
        val commentText: EditText = findViewById(R.id.ET_no_of_comment)
        val retweettext: EditText = findViewById(R.id.ET_no_of_retweet)
        val liketext: EditText = findViewById(R.id.ET_no_of_like)
        val viewtweet: EditText = findViewById(R.id.ET_no_of_views)
        image_view_in_AET = findViewById(R.id.image_view_in_AET)

        imageView = findViewById(R.id.take_imag_I1)

        imageView.setOnClickListener {
            uploadPostImage()
        }



        Xtext = findViewById(R.id.textViewX)
        Xtext.setOnClickListener {
            finish()
        }

        tweet.setOnClickListener {
            val pText = postText.text
            handlername = sharedPreferences.getString("handle", null)
            comment = commentText.text.toString()
            Viewt = viewtweet.text.toString()
            like = liketext.text.toString()
            retweet = retweettext.text.toString()
            posttext = postText.text.toString()

            val post = Post(
                null,
                prUri,
                name,
                handlername,
                pText.toString(),
                post_image_uri,
                isVerfied,
                comment,
                retweet,
                like,
                Viewt,


            )
            CoroutineScope(Dispatchers.IO).launch {
                repo.insert(post)
            }
            finish()

        }
    }
    private fun uploadPostImage() {
        ImagePicker.with(this)
            .provider(ImageProvider.BOTH) //Or bothCameraGallery()
            .createIntentFromDialog { launcherPr.launch(it) }
    }
    private val launcherPr =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                // Use the uri to load the image
                // Only if you are not using crop feature:
                uri.let { galleryUri ->
//                    val uu = FileUtils().getFile(this, galleryUri)
                    image_view_in_AET.visibility = View.VISIBLE
                    post_image_uri = copyImageToPrivateStorage(this, galleryUri)
                    Glide.with(this).load(post_image_uri).into(image_view_in_AET)
//                    post_image_uri = galleryUri.toString()
                    cardView.visibility = View.VISIBLE
                }
            }
        }




    fun copyImageToPrivateStorage(context: Context, imageUri: Uri): String? {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val privateDir = context.getExternalFilesDir(null)
        val file = File(privateDir, fileName)

        try {
            val outputStream = FileOutputStream(file)
            val bufferSize = 1024
            val buffer = ByteArray(bufferSize)
            var bytesRead: Int

            while (inputStream?.read(buffer).also { bytesRead = it ?: -1 } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            outputStream.close()
            inputStream?.close()

            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

}
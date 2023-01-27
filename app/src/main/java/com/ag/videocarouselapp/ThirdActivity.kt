
package com.ag.videocarouselapp


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


// Song Detail View
private const val LOG_TAG = "Song/ThirdActivity"
//const val PICK_IMAGE_FROM_LOCAL= 1

class ThirdActivity: AppCompatActivity(){

    private lateinit var signOutButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val uploadAudioFragment =this.supportFragmentManager.findFragmentById(R.id.song_view_fragment)

        if(uploadAudioFragment== null){
            val frag = UploadAudioFragement()
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.song_view_fragment, frag)
                .commit()}


    }

    //Reference Code for uploading audio

    //        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//            super.onActivityResult(requestCode, resultCode, data)
//
//
//            if (requestCode == PICK_IMAGE_FROM_LOCAL && resultCode == Activity.RESULT_OK && data != null)
//           val filePath: Uri? = data?.data
//
//            if (filePath != null) {
//                uploadFile(filePath)
//            }

//        }  TODO( "UNCOMMENT IT" )

//    fun uploadFile(filePath: Uri){
//
//        this.enterLyrics = this.findViewById(R.id.enter_lyrics)
//        this.enterTitle = this.findViewById(R.id.enter_title)
//
//        val lyrics: RequestBody = RequestBody.create(MultipartBody.FORM, enterLyrics.text.toString())
//        val title: RequestBody = RequestBody.create(MultipartBody.FORM, enterTitle.text.toString())
//
//
//        val file: File =  File(Environment.getExternalStorageDirectory(),"audio");
//        val uri: Uri= Uri.fromFile(file);
//        val originalFile : File =  File(uri.path)
//
//        val filePart: RequestBody = RequestBody.create(MediaType.parse(contentResolver.getType(filePath)),originalFile)
//
//        val audioFile : MultipartBody.Part = MultipartBody.Part.createFormData("audio", originalFile.name, filePart)
//        //   SongExecutor().postSong(title, "artist", lyrics, image , audioFile)
//        TODO("either have to make lyrics, artist and title as a multipart ot have to figure out typecast Requestbody to String")
//    }



}




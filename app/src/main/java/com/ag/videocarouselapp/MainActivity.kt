
package com.ag.videocarouselapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

const val RC_SIGN_IN= 1
private const val LOG_TAG = "Song/Main Activity"
private val CHANNEL_ID = "channel_id_videoapp"
private val notificationId = 101

class MainActivity : AppCompatActivity() {
    private lateinit var signInButton: SignInButton
    private lateinit var continueButton: Button
    private lateinit var signOutButton: Button
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var account: GoogleSignInAccount? = null
    private var freshSignin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()


        this.signInButton = this.findViewById(R.id.sign_in_button)
        this.signOutButton = this.findViewById(R.id.sign_out_button)
        this.continueButton = this.findViewById(R.id.continue_button)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this , gso);

        // skip login if already authed
        val account = GoogleSignIn.getLastSignedInAccount(this)
        Log.d(LOG_TAG, "Account: $account")


        signInButton.setOnClickListener{
            Log.v(LOG_TAG, "starting Google login")
            val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, RC_SIGN_IN)
            sendNotification()
        }
        signOutButton.setOnClickListener {
            Log.v(LOG_TAG, "signout clicked")
            mGoogleSignInClient.signOut()
                .addOnCompleteListener(this) {
                    Log.i(LOG_TAG, "logout successful")
                    updateUI(null)
                }
        }

        continueButton.setOnClickListener {
            freshSignin = true
            updateUI(account)
        }
        updateUI(account)


    }

    private fun createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val name = "Notification Title"
            val descriptionText= "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description= descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(){
        val intent  = Intent(this, MainActivity::class.java).apply {
            flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent=PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_IMMUTABLE)
        //val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_launcher_foreground)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("App notification")
            .setContentText("User Signed IN Successfully")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            freshSignin = true
            updateUI(account)
        } catch (e: ApiException) {
            Log.v(LOG_TAG, "signInResult:failed code=" + e.statusCode)
            e.printStackTrace()
        }
    }
    fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            Log.i(LOG_TAG, "login successful")

            if (freshSignin) {
                freshSignin = false
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }
            else {
                signInButton.visibility = android.view.View.GONE
                continueButton.visibility = android.view.View.VISIBLE
                signOutButton.visibility = android.view.View.VISIBLE
            }
        }
        else {
            signInButton.visibility = android.view.View.VISIBLE
            continueButton.visibility = android.view.View.GONE
            signOutButton.visibility = android.view.View.GONE
        }
    }
}

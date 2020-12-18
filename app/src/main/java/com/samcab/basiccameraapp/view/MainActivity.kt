package com.samcab.basiccameraapp.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.samcab.basiccameraapp.R
import com.samcab.basiccameraapp.constants.CAPTURE_IMAGE_FILE_PROVIDER
import com.samcab.basiccameraapp.constants.INTERAL_STORAGE_PATH
import com.samcab.basiccameraapp.utility.AppUtils
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import com.scanlibrary.ScanConstants
import com.scanlibrary.ScanActivity
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.data
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat.getExtras
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import android.util.Log
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val requestCode = 100
    private val permissionCode = 101
    private val scanDocumentCode = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        loadFragment(AllImagesFragment())
        btn_openCamera.setOnClickListener(this)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_openCamera -> {
                onCaptureBtnClicked()
            }
        }
    }

    private fun onCaptureBtnClicked() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            val preference = ScanConstants.OPEN_CAMERA
            val intent = Intent(this, ScanActivity::class.java)
            intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference)
            startActivityForResult(intent, scanDocumentCode)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                permissionCode
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == scanDocumentCode && resultCode == RESULT_OK){
 //         retrieveImage()

           /* val resultUri = File(uri.path!!)
                val path = File(
                    this.filesDir,
                    INTERAL_STORAGE_PATH
                )
                val imageFile = File(path.path).listFiles()
                resultUri.renameTo(imageFile!![imageFile.size - 1])
*/
        }
    }

    private fun retrieveImage() {
        val imageFile = AppUtils.getFileListInFolder(
            this,
            INTERAL_STORAGE_PATH
        )

        val imageUri = FileProvider.getUriForFile(
            this, CAPTURE_IMAGE_FILE_PROVIDER,
            imageFile[imageFile.size - 1]
        )

        CropImage.activity(imageUri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(this)
    }

    override fun onBackPressed() {
        finish()
    }
}


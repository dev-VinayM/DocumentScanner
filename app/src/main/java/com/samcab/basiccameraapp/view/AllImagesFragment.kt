package com.samcab.basiccameraapp.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samcab.basiccameraapp.R
import com.samcab.basiccameraapp.adapter.AllImagesAdapter
import com.samcab.basiccameraapp.listener.ViewItemClickListener
import com.samcab.basiccameraapp.utility.AppUtils
import com.samcab.basiccameraapp.constants.CAPTURE_IMAGE_FILE_PROVIDER
import com.samcab.basiccameraapp.constants.INTERAL_STORAGE_PATH
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class AllImagesFragment : Fragment(), ViewItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var allImagesAdapter: AllImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_images, container, false)
        recyclerView = view.findViewById(R.id.rv_allImages)
        return view
    }

    override fun onResume() {
        super.onResume()
        allImagesAdapter = AllImagesAdapter(
            AppUtils.getFileListInFolder
                (activity!!, INTERAL_STORAGE_PATH), this
        )
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = allImagesAdapter
    }


    override fun onItemClickClicked(position: Int) {
        val imageFile = AppUtils.getFileListInFolder(activity!!,
            INTERAL_STORAGE_PATH
        )

        val imageUri = FileProvider.getUriForFile(
            activity!!, CAPTURE_IMAGE_FILE_PROVIDER,
            imageFile[imageFile.size - 1]
        )
        CropImage.activity(imageUri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(activity!!)
    }

}

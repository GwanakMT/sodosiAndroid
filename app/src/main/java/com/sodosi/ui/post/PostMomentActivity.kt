package com.sodosi.ui.post

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.sodosi.R
import com.sodosi.databinding.ActivityPostMomentBinding
import com.sodosi.model.POIDataModel
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.common.extensions.setGone
import com.sodosi.ui.common.extensions.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostMomentActivity : BaseActivity<PostMomentViewModel, ActivityPostMomentBinding>() {
    override val viewModel: PostMomentViewModel by viewModels()
    private var momentPlace: POIDataModel? = null
    private val photoAdapter: PhotoImageAdapter by lazy { PhotoImageAdapter() }

    override fun getViewBinding() = ActivityPostMomentBinding.inflate(layoutInflater)

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val newPhotoList = mutableListOf<Uri>()

                // 1) 사진을 여러장 선택한 경우
                if (it.data?.clipData != null) {
                    val count = it.data?.clipData?.itemCount ?: 0
                    if (count > 5) {
                        SodosiToast.makeText(this, "사진은 최대 5장까지 선택 가능합니다.", Toast.LENGTH_SHORT)
                            .show()
                        return@registerForActivityResult
                    }

                    for (i in 0 until count) {
                        val imageUri =
                            it.data?.clipData?.getItemAt(i)?.uri ?: return@registerForActivityResult
                        newPhotoList.add(imageUri)
                    }
                } else {
                    // 2) 사진을 한 장만 선택한 경우
                    it.data?.data?.let { uri ->
                        newPhotoList.add(uri)
                    }
                }

                photoAdapter.submitList(newPhotoList)
                binding.tvPhotoCount.text = newPhotoList.size.toString()

                if (newPhotoList.size > 0) {
                    binding.photoRecyclerView.setVisible()
                } else {
                    binding.photoRecyclerView.setGone()
                }
            }
        }

    override fun observeData() {

    }

    override fun initViews() {
        momentPlace = intent.getParcelableExtra(KEY_MOMENT_PLACE) as? POIDataModel
        binding.tvPlaceName.text = momentPlace?.placeName

        initAppbar()
        initEditTextView()
        initPhotoRecyclerView()

        setListener()
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }

            initAppbarTitle(getString(R.string.create_sodosi))
        }
    }

    private fun initEditTextView() {
        binding.etMoment.hint = getString(R.string.create_sodosi_name_hint)
        binding.etMoment.addTextChangedListener {
            if ("$it".length > 0) {
                binding.btnSubmit.setTextColor(Color.parseColor("#0F0F10"))
            } else {
                binding.btnSubmit.setTextColor(Color.parseColor("#ADADAD"))
            }
        }
    }

    private fun initPhotoRecyclerView() {
        binding.photoRecyclerView.apply {
            adapter = photoAdapter.apply {
                onPhotoClick = ::photoClickEvent
                onDeleteButtonClick = ::deletePhoto
            }
        }
    }

    private fun setListener() {
        binding.btnSubmit.setOnClickListener {

        }

        binding.ivPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            galleryLauncher.launch(intent)
        }
    }

    private fun photoClickEvent(uri: Uri, position: Int) {
        SodosiToast.makeText(this@PostMomentActivity, "$uri, $position", Toast.LENGTH_SHORT).show()
    }

    private fun deletePhoto(position: Int) {
        if (position < photoAdapter.currentList.size) {
            val newPhotoList = photoAdapter.currentList.toMutableList()
            newPhotoList.removeAt(position)

            photoAdapter.submitList(newPhotoList)
            binding.tvPhotoCount.text = newPhotoList.size.toString()

            if (newPhotoList.size == 0) {
                binding.photoRecyclerView.setGone()
            }
        }
    }

    companion object {
        private const val KEY_MOMENT_PLACE = "KEY_MOMENT_PLACE"

        fun getIntent(context: Context, momentPlace: POIDataModel): Intent {
            return Intent(context, PostMomentActivity::class.java).apply {
                putExtra(KEY_MOMENT_PLACE, momentPlace)
            }
        }
    }
}

package com.example.parabara.ui.product

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.parabara.R
import com.example.parabara.base.BaseActivity
import com.example.parabara.databinding.ActivityProductBinding
import com.example.parabara.ext.toast
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source


@AndroidEntryPoint
class ProductActivity : BaseActivity<ActivityProductBinding>(R.layout.activity_product) {

    private val viewModel: ProductViewModel by viewModels()

    private val adapter by lazy {
        ProductImageListAdapter(viewModel)
    }

    private val getImagesLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageList = mutableListOf<MultipartBody.Part>()
                result.data?.data?.let {
                    it.asMultipart("image", contentResolver)?.let { multipart ->
                        imageList.add(multipart)
                    }
                viewModel.uploadImage(imageList)

                }
                result.data?.clipData?.let { clipData ->
                    for (i in 0 until clipData.itemCount) {
                        clipData.getItemAt(i).uri.asMultipart("image", contentResolver)?.let { multipart ->
                            imageList.add(multipart)
                        }
                    }
                    viewModel.uploadImage(imageList)
                }
            }
        }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
            } else {
            }
        }

    override fun start() {
        with(binding) {
            vm = viewModel
            rvProductImageList.adapter = adapter
        }
    }

    override fun observe() {
        with(viewModel) {
            actionImageChooserClicked.observe(this@ProductActivity, { event ->
                event.getContentIfNotHandled()?.let { maxSelectable ->
                    when (PackageManager.PERMISSION_GRANTED) {
                        ContextCompat.checkSelfPermission(
                            this@ProductActivity,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) -> {
                            val intent = Intent().apply {
                                type = "image/*"
                                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                                action = Intent.ACTION_GET_CONTENT
                            }
                            getImagesLauncher.launch(Intent.createChooser(intent, "최대 ${maxSelectable}장 선택 가능"))
                        }
                        else -> {
                            //권한 요청
                            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }
                }
            })
            productImageList.observe(this@ProductActivity, {
                adapter.submitList(it)
            })
            showToastInt.observe(this@ProductActivity, { event ->
                event.getContentIfNotHandled()?.let {
                    toast(it)
                }

            })
            finishActivity.observe(this@ProductActivity, { event ->
                event.getContentIfNotHandled()?.let { result ->
                    setResult(result)
                    finish()
                }
            })
        }
    }

    fun Uri.asMultipart(name: String, contentResolver: ContentResolver): MultipartBody.Part? {
        return contentResolver.query(this, null, null, null, null)?.let {
            if (it.moveToNext()) {
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                val requestBody = object : RequestBody() {
                    override fun contentType(): MediaType? {
                        return contentResolver.getType(this@asMultipart)?.toMediaType()
                    }

                    override fun writeTo(sink: BufferedSink) {
                        sink.writeAll(contentResolver.openInputStream(this@asMultipart)?.source()!!)
                    }
                }
                it.close()
                MultipartBody.Part.createFormData(name, displayName, requestBody)
            } else {
                it.close()
                null
            }
        }
    }
}
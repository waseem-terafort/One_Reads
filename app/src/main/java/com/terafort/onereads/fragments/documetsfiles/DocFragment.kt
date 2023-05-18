package com.terafort.onereads.fragments.documetsfiles

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.terafort.onereads.R
import com.terafort.onereads.adaptermain.AdapterHomeFiles
import com.terafort.onereads.data.filesdata.FileModel
import com.terafort.onereads.data.filesdata.FileType
import com.terafort.onereads.databinding.FragmentDocBinding
import com.terafort.onereads.databinding.FragmentExelBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DocFragment : Fragment() {
    private lateinit var homeadapter: AdapterHomeFiles
    private lateinit var binding: FragmentDocBinding
    var PDF_SIZE_COUNT_DEFAULT = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDocBinding.inflate(inflater, container, false)
        binding.docrecycleralldocuments.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)
        homeadapter = AdapterHomeFiles(listOf())
        binding.docrecycleralldocuments.adapter=homeadapter
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = binding.docimageView2
        imageView.setOnClickListener {
            findNavController().popBackStack()
        }
        lifecycleScope.launch(Dispatchers.IO){
            val pdfList = getPdfList(requireContext())
            withContext(Dispatchers.Main){
                pdfList?.let { homeadapter.setData(pdfList)}
            }
        }
    }
    private fun convertLongToDate(date: Long): String {
        val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        return formatter.format(Date(date))
    }

    private fun convertLongToTime(time: Long): String {
        val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return formatter.format(Date(time * 1000))  // Multiply by 1000 to convert seconds to milliseconds
    }

    suspend fun getPdfList(context: Context): ArrayList<FileModel>? {
        val pdfList = ArrayList<FileModel>() // Declare and initialize pdfList here

        return withContext(Dispatchers.IO) {
            val collection = MediaStore.Files.getContentUri("external")

            val projection = arrayOf(
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Video.Media._ID
            )

            val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} IN (?, ?)"
            val selectionArgs = arrayOf("application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
            val sortOrder = "${MediaStore.Files.FileColumns.DISPLAY_NAME} ASC"

            try {
                context.contentResolver.query(
                    collection,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
                ).use { cursor ->
                    cursor?.let {
                        if (it.moveToFirst()) {
                            val columnData = it.getColumnIndex(MediaStore.Files.FileColumns.DATA)
                            var columnName =
                                it.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
                            val cursorRange = it.getColumnIndex(MediaStore.Files.FileColumns.DATE_MODIFIED)

                            val id = it.getColumnIndex(MediaStore.Video.Media._ID)

                            do {
                                if (it.getString(columnName) == null) {
                                    columnName = 1
                                }
                                val uri =
                                    MediaStore.Files.getContentUri(
                                        "external",
                                        it.getLong(id)
                                    )
                                val columnDate = if (cursorRange == -1) {
                                    convertLongToDate(1000)
                                } else {
                                    convertLongToDate(it.getLong(cursorRange) * 1000L)
                                }

                                val columnTime = if (cursorRange == -1) {
                                    convertLongToTime(1000)
                                } else {
                                    convertLongToTime(it.getLong(cursorRange) * 1000L)
                                }
                                val fileSizeInBytes = File(it.getString(columnData)).length()
                                val fileSizeInKB = "%.1f KB".format(fileSizeInBytes / 1024.0)
                                if (File(it.getString(columnData)).length() > 1024) {
                                    pdfList.add(
                                        FileModel(
                                            it.getString(columnName),
                                            fileSizeInKB,
                                            columnDate,
                                            columnTime,
                                            R.drawable.doc,
                                            false,
                                            "",
                                            FileType.DOC,
                                            uri
                                        )
                                    )
                                    PDF_SIZE_COUNT_DEFAULT += 1
                                }

                            } while (it.moveToNext())
                        }
                    }
                }
            } catch (ex: Exception) {
                Log.e(ContentValues.TAG, "Could not get pdf list: ${ex.message}")
                null
            }
            pdfList
        }
    }
}
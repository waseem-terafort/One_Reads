package com.terafort.onereads.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.terafort.onereads.R
import com.terafort.onereads.adaptermain.AdapterHomeFiles
import com.terafort.onereads.data.filesdata.FileModel
import com.terafort.onereads.data.filesdata.FileType
import com.terafort.onereads.databinding.FragmentDocumentsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DocumentsFragment : Fragment() {
    private lateinit var homeadapter: AdapterHomeFiles
    private lateinit var binding: FragmentDocumentsBinding
    var PDF_SIZE_COUNT_DEFAULT = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDocumentsBinding.inflate(inflater, container, false)
        binding.recycleralldocuments.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        homeadapter = AdapterHomeFiles(listOf())
        binding.recycleralldocuments.adapter=homeadapter

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = binding.imageView2
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
        return formatter.format(Date(time))
    }


    suspend fun getPdfList(context: Context): ArrayList<FileModel>? {
        val pdfList = ArrayList<FileModel>()

        return withContext(Dispatchers.IO) {
            val collection = MediaStore.Files.getContentUri("external")

            val projection = arrayOf(
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Video.Media._ID,
                MediaStore.Files.FileColumns.MIME_TYPE
            )

            val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} IN (?, ?, ?, ?, ?, ?, ?, ?)"
            val selectionArgs = arrayOf(
                "application/pdf",
                "application/vnd.ms-powerpoint",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/vnd.ms-excel",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "text/plain"
            )
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
                            val cursorRange =
                                it.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED)
                            val id = it.getColumnIndex(MediaStore.Video.Media._ID)

                            do {
                                val fileType = getFileTypeFromExtension(it.getString(columnName))

                                // Set the drawable ID based on the file type
                                val drawableId = when (fileType) {
                                    FileType.PDF -> R.drawable.pdf
                                    FileType.PPT -> R.drawable.ppt
                                    FileType.DOC -> R.drawable.doc
                                    FileType.XLS -> R.drawable.excel
                                    FileType.TXT -> R.drawable.text
                                    else -> R.drawable.text
                                }
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

                                if (fileSizeInBytes > 1024) {
                                    pdfList.add(
                                        FileModel(
                                            it.getString(columnName),
                                            fileSizeInKB,
                                            columnDate,
                                            columnTime,
                                            drawableId,
                                            false,
                                            "",
                                            fileType,
                                            uri
                                        )
                                    )
                                    if (fileType == FileType.PDF) {
                                        PDF_SIZE_COUNT_DEFAULT += 1
                                    }
                                }

                            } while (it.moveToNext())
                        }
                    }
                }
            } catch (ex: Exception) {
                Log.e(TAG, "Could not get pdf list: ${ex.message}")
                null
            }
            pdfList
        }
    }

    private fun getFileTypeFromExtension(fileName: String?): FileType {
        return when (fileName?.substringAfterLast(".")) {
            "pdf" -> FileType.PDF
            "ppt", "pptx" -> FileType.PPT
            "doc", "docx" -> FileType.DOC
            "xls", "xlsx" -> FileType.XLS
            "txt" -> FileType.TXT
            else -> FileType.UNKNOWN
        }
    }
}
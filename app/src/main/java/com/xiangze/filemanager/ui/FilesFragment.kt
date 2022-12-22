package com.xiangze.filemanager.ui

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiangze.filemanager.adapters.FileAdapter
import com.xiangze.filemanager.databinding.FragmentFilesBinding
import java.io.File

class FilesFragment : Fragment() {
    private lateinit var binding: FragmentFilesBinding
    private lateinit var adapter: FileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: FilesFragmentArgs by navArgs()
        val path = if(args.path != null && args.path != "null"){
            args.path!!
        }else{
            Environment.getExternalStorageDirectory().path
        }
        val root = File(path)
        if(root.listFiles()?.toList().isNullOrEmpty()){
            binding.emptyFile.isVisible = true
        }
        root.listFiles()?.let {
            setupAdapter(it.toList())
        }
    }

    private fun setupAdapter(files: List<File>){
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = FileAdapter(files){
            val action = FilesFragmentDirections.actionFilesToSelf(it.path)
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding.rvFiles.layoutManager = layoutManager
        binding.rvFiles.adapter = adapter
    }
}
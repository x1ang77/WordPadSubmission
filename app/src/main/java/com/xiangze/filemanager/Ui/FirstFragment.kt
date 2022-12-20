package com.xiangze.filemanager.Ui

import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiangze.filemanager.adapters.FileAdapter
import com.xiangze.filemanager.databinding.FragmentFirstBinding
import java.io.File

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private lateinit var adapter: FileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: FirstFragmentArgs by navArgs()
        val path = if(args.path != null && args.path != "null"){
            args.path!!
        }else{
            Environment.getExternalStorageDirectory().path
        }
        val root = File(path)
        root.listFiles()?.let {
            setupAdapter(it.toList())
            if(it.toList().isNullOrEmpty()){
                binding.emptyFile.isVisible = true
            }
        }
    }

    private fun setupAdapter(files: List<File>){
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = FileAdapter(files){
            val action = FirstFragmentDirections.actionFirstToSelf(it.path)
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding.rvFiles.layoutManager = layoutManager
        binding.rvFiles.adapter = adapter
    }
}
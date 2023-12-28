package com.example.tugas4.ui.skill

import android.content.res.TypedArray
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugas4.databinding.FragmentSkillBinding
import androidx.appcompat.widget.SearchView
import com.example.tugas4.R
import com.example.tugas4.ui.SkillAdapter
import java.util.Locale

class SkillFragment : Fragment() {
    private lateinit var adapter: SkillAdapter
    private var _binding: FragmentSkillBinding? = null
    private val binding get() = _binding!!

    private var skillItems: List<Pair<String, String>> = emptyList()
    private lateinit var imgResources: TypedArray

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSkillBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val searchView = binding.searchView

        // Mengambil array deskripsi dari sumber daya string
        val descriptionArray = resources.getStringArray(R.array.string_skills_descriptions)

        imgResources = resources.obtainTypedArray(R.array.random_imgs)

        // Mengambil array dari sumber daya string
        val skillArray = resources.getStringArray(R.array.string_skills_array)

        // Inisialisasi skillItems dari array yang diambil
        skillItems = skillArray.map { Pair(it, "Deskripsi $it") }

        // Inisialisasi Map untuk menyimpan ID gambar
        val skillImages = skillArray.mapIndexed { index, skill ->
            skill to imgResources.getResourceId(index, 0)
        }.toMap()

        // Inisialisasi adapter dengan skillItems dan skillImages
        adapter = SkillAdapter(requireContext(), skillItems, skillImages, descriptionArray)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
        return rootView
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<Pair<String, String>>()

            if (query.isBlank()) {
                // If the query is empty, display the original list
                filteredList.addAll(skillItems)
            } else {
                for (i in skillItems) {
                    if (i.first.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
                        filteredList.add(i)
                    }
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(requireContext(), "No Data found", Toast.LENGTH_SHORT).show()
            }

            adapter.setFilteredList(filteredList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        // Recycle TypedArray
        imgResources.recycle()
    }
}

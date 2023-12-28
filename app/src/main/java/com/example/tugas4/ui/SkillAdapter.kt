package com.example.tugas4.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas4.R
import com.example.tugas4.ui.skill.SkillFragmentDirections

class SkillAdapter(
    private val context: Context,
    private var skillItems: List<Pair<String, String>>,
    private val skillImages: Map<String, Int>,
    private val descriptionArray: Array<String>
) : RecyclerView.Adapter<SkillAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.textViewSkillName)
        val descriptionTextView: TextView = view.findViewById(R.id.textViewSkillDescription)
        val detailButton: Button = view.findViewById(R.id.buttonDetail)
        val imageView: ImageView = view.findViewById(R.id.imageViewSkill)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_skill, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val skillItem = skillItems[position]
        val skillName = skillItem.first

        holder.nameTextView.text = skillName

        // Mendapatkan ID gambar dari Map
        val resourceId = skillImages[skillName] ?: R.drawable.all

        // Menetapkan gambar ke ImageView
        holder.imageView.setImageResource(resourceId)

        // Menambahkan OnClickListener untuk tombol detail
        holder.detailButton.setOnClickListener {
            val action = SkillFragmentDirections.actionNavSkillToNavSkilldetail2(skillName)
            holder.itemView.findNavController().navigate(action)
        }

        // Mendapatkan deskripsi dari string array
        val descriptionArrayResourceId = context.resources.getIdentifier(
            "string_skills_descriptions",
            "array",
            context.packageName
        )
        val descriptions = context.resources.getStringArray(descriptionArrayResourceId)
        val index = context.resources.getStringArray(R.array.string_skills_array).indexOf(skillName)
        val skillDescription = if (index != -1 && index < descriptions.size) {
            descriptions[index]
        } else {
            "Deskripsi $skillName"
        }

        holder.descriptionTextView.text = skillDescription
    }

    override fun getItemCount(): Int {
        return skillItems.size
    }

    // Method to set the filtered list
    fun setFilteredList(filteredList: List<Pair<String, String>>) {
        skillItems = filteredList
        notifyDataSetChanged()
    }
}
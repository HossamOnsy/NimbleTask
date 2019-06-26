package com.sam.nimbletask.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sam.nimbletask.R
import com.sam.nimbletask.models.SurveyResponseModel
import com.sam.nimbletask.ui.activities.TakeSurveyActivity


class SurveysAdapter(var list: ArrayList<SurveyResponseModel>) :
    RecyclerView.Adapter<SurveysAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: com.sam.nimbletask.databinding.SurveyItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context)
            , R.layout.survey_item, parent, false
        )
        return ViewHolder(binding)
    }

    fun updateList(newList: List<SurveyResponseModel>) {
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(list.get(position))

    }

    override fun getItemCount() = list.size


    class ViewHolder(private val surveyItemBinding: com.sam.nimbletask.databinding.SurveyItemBinding) :
        RecyclerView.ViewHolder(surveyItemBinding.root) {

        fun bind(item: SurveyResponseModel) {
            surveyItemBinding.surveyModel = item
            var t = surveyItemBinding.takeSurveyBtn


            t.setOnClickListener {

                itemView.context.startActivity(Intent(itemView.context, TakeSurveyActivity::class.java))

            }

        }

    }

}
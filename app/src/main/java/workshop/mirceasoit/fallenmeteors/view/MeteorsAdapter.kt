package workshop.mirceasoit.fallenmeteors.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import workshop.mirceasoit.fallenmeteors.R
import workshop.mirceasoit.fallenmeteors.model.Meteor

class MeteorsAdapter(private val listener: OnClickListener) :
        ListAdapter<Displayable, MeteorBindViewHolder<Displayable, MeteorsAdapter.OnClickListener>>(
                DIFF_CALLBACK
        ) {

    companion object {
        private const val METEOR_ITEM =
                R.layout.meteor_item
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Displayable>() {
            override fun areItemsTheSame(oldItem: Displayable, newItem: Displayable): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Displayable, newItem: Displayable): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeteorBindViewHolder<Displayable, OnClickListener> {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return MeteorBindViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type
    }

    override fun onBindViewHolder(holder: MeteorBindViewHolder<Displayable, OnClickListener>, position: Int) {
        val item = currentList[position]
        holder.bind(item, listener)
    }

    fun submitData(meteors: List<Meteor>) {
        val entries: MutableList<Displayable> = mutableListOf()
        meteors.forEach { meteor ->
            entries.add(
                    Displayable(type = METEOR_ITEM,
                            meteor = meteor)
            )
        }
        submitList(entries)
    }

    interface OnClickListener {
        fun onMeteorClick(meteor: Meteor)
    }
}

data class Displayable(val type: Int,
                       val meteor: Meteor? = null)
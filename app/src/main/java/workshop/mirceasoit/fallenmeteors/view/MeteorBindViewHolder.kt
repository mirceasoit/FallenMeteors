package workshop.mirceasoit.fallenmeteors.view

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import workshop.mirceasoit.fallenmeteors.BR

class MeteorBindViewHolder <Data, Listener> internal constructor(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Data, listener: Listener) {
        binding.setVariable(BR.data, item)
        binding.setVariable(BR.listener, listener)
        binding.executePendingBindings()
    }
}
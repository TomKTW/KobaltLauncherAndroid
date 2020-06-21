package dev.kobalt.launcher

import dev.kobalt.core.application.NativeView
import dev.kobalt.core.extension.dp
import dev.kobalt.core.extension.withAlpha
import dev.kobalt.core.view.*
import dev.kobalt.launcher.entity.PackageEntity
import dev.kobalt.launcher.extension.fromPackage

class PackageRecyclerView : RecyclerView() {

    var onItemTap: ((PackageEntity) -> Unit)?
        get() = adapter.onItemTap
        set(value) {
            adapter.onItemTap = value
        }

    private var adapter: Adapter
        get() = nativeView.adapter as Adapter
        set(value) {
            nativeView.adapter = value
        }

    init {
        adapter = Adapter()
    }

    fun update(list: List<PackageEntity>?) {
        adapter.list = list
        adapter.notifyDataSetChanged()
    }

    class Adapter : RecyclerView.Adapter<Holder>() {

        var onItemTap: ((PackageEntity) -> Unit)? = null

        var list: List<PackageEntity>? = null

        private fun getItem(holder: Holder): PackageEntity? {
            return list?.getOrNull(holder.position)
        }

        override fun onCreateViewHolder(position: Int): Holder {
            return ItemHolder().apply {
                view.onTap = {
                    getItem(this)?.let {
                        onItemTap?.invoke(it)
                    }
                }
            }
        }

        override fun getItem(p0: Int): Any? {
            return list?.getOrNull(p0)
        }

        override fun getCount(): Int {
            return list?.size ?: 0
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            when (holder) {
                is ItemHolder -> (holder.view as? ItemView)?.let { view ->
                    getItem(holder).let { item ->
                        view.titleLabel.text = item?.label.orEmpty()
                        view.thumbnailImage.image = item?.name?.let { images.fromPackage(it) }
                    }
                }
            }
        }

    }

    open class Holder(itemView: NativeView) : RecyclerView.Holder(itemView)

    class ItemHolder : Holder(
        ItemView()
    )

    class ItemView : LayerView() {

        val titleLabel = LabelView()
        val thumbnailImage = ImageView()

        init {
            background = images.tapState(colors.black.withAlpha(0.5f), 0)
            add(
                StackView(StackView.Orientation.Horizontal)
                    .apply {
                        add(
                            view = thumbnailImage, width = 32.dp, height = 32.dp, margin = 8.dp
                        )
                        add(
                            view = titleLabel,
                            width = matchConstraint,
                            height = wrapContent,
                            weight = 1.0f,
                            margin = 8.dp
                        )
                    }, width = matchParent, height = wrapContent
            )
        }
    }

}
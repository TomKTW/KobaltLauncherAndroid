package dev.kobalt.launcher

import dev.kobalt.core.extension.dp
import dev.kobalt.core.extension.toImage
import dev.kobalt.core.view.StackView
import dev.kobalt.launcher.extension.green

class PackageView : StackView(orientation = Orientation.Vertical) {

    val toolbar = ToolbarView().apply {
        background = colors.green.toImage()
    }

    val packageRecycler = PackageRecyclerView().apply {
        background = colors.gray.toImage()
    }

    init {
        add(toolbar, width = matchParent, height = 56.dp)
        add(packageRecycler, width = matchParent, height = matchParent)
    }

}
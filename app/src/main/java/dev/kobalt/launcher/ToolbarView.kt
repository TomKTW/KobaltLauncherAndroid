package dev.kobalt.launcher

import dev.kobalt.core.extension.dp
import dev.kobalt.core.extension.sp
import dev.kobalt.core.view.LabelView
import dev.kobalt.core.view.StackView
import dev.kobalt.launcher.extension.applicationName

class ToolbarView : StackView(orientation = Orientation.Horizontal) {

    val titleLabel = LabelView().apply {
        text = strings.applicationName
        color = colors.white
        bold = true
        size = 18.sp
        gravity = centerVerticalGravity
    }

    init {
        add(titleLabel, width = matchConstraint, height = matchParent, margin = 16.dp, weight = 1f)
    }

}
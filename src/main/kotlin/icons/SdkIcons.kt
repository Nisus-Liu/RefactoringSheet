// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package icons

import com.intellij.openapi.util.IconLoader

object SdkIcons {
    @JvmField
    val Sdk_default_icon = IconLoader.getIcon("/icons/sdk_16.svg", SdkIcons::class.java)
    @JvmField
    val BookIcon = IconLoader.getIcon("/icons/book.png", SdkIcons.javaClass)
    @JvmField
    val PluginIcon = IconLoader.getIcon("/icons/pluginIcon.svg", SdkIcons.javaClass)
    @JvmField
    val BadSmell = IconLoader.getIcon("/icons/badSmell.svg", SdkIcons.javaClass)
}
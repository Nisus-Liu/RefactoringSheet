package com.nisus.refactoringsheet.badsmell

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup

class BadSmellActionGroup: DefaultActionGroup() {

    override fun update(event: AnActionEvent) {
        // 始终显示
    }

}
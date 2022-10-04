package com.nisus.refactoringsheet.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DefaultActionGroup

class RsActionGroup: DefaultActionGroup() {

    override fun update(event: AnActionEvent) {
        // 始终显示
    }

}
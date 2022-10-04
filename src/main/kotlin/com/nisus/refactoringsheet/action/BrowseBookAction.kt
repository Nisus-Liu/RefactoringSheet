package com.nisus.refactoringsheet.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.nisus.refactoringsheet.util.CommandUtil
import java.net.URI

private const val E_BOOK_URL = "https://book-refactoring2.ifmicro.com/"

class BrowseBookAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        CommandUtil.browse(URI(E_BOOK_URL))
    }
}
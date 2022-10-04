package com.nisus.refactoringsheet.util

import org.slf4j.LoggerFactory
import java.awt.Desktop
import java.io.File
import java.io.IOException
import java.net.URI
import java.util.*

/**
 * 常用指令工具类
 *
 * [(86条消息) 使用java完美实现打开默认浏览器访问url_Inovation_Sky的博客-CSDN博客](https://blog.csdn.net/Melody_Susan/article/details/82701219)
 * @author 许畅
 * @since JDK1.7
 * @version 2018年9月14日 许畅 新建
 */
object CommandUtil {
    /** LOG日志  */
    private val LOGGER = LoggerFactory.getLogger(CommandUtil::class.java)

    /**
     * 打开浏览器
     *
     * @param uri uri地址
     * @return 是否成功
     */
    fun browse(uri: URI): Boolean {
        if (openSystemSpecific(uri.toString())) {
            return true
        }
        return if (browseDESKTOP(uri)) {
            true
        } else false
    }

    /**
     * 打开文件
     *
     * @param file 文件
     * @return 是否成功
     */
    fun open(file: File): Boolean {
        if (openSystemSpecific(file.path)) {
            return true
        }
        return if (openDESKTOP(file)) {
            true
        } else false
    }

    /**
     * 编辑指令
     *
     * @param file 文件
     * @return 是否成功
     */
    fun edit(file: File): Boolean {
        // you can try something like
        // runCommand("gimp", "%s", file.getPath())
        // based on user preferences.
        if (openSystemSpecific(file.path)) {
            return true
        }
        return if (editDESKTOP(file)) {
            true
        } else false
    }

    /**
     * 打开系统特殊的指令
     *
     * @param what 指令
     * @return 是否成功
     */
    private fun openSystemSpecific(what: String): Boolean {
        val os = os
        if (os.isLinux) {
            if (runCommand("kde-open", "%s", what)) return true
            if (runCommand("gnome-open", "%s", what)) return true
            if (runCommand("xdg-open", "%s", what)) return true
        }
        if (os.isMac) {
            if (runCommand("open", "%s", what)) return true
        }
        if (os.isWindows) {
            if (runCommand("explorer", "%s", what)) return true
        }
        return false
    }

    /**
     * @param uri URI
     * @return boolean
     */
    private fun browseDESKTOP(uri: URI): Boolean {
        LOGGER.info("Trying to use Desktop.getDesktop().browse() with $uri")
        return try {
            if (!Desktop.isDesktopSupported()) {
                LOGGER.error("Platform is not supported.")
                return false
            }
            if (!Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                LOGGER.error("BROWSE is not supported.")
                return false
            }
            Desktop.getDesktop().browse(uri)
            true
        } catch (t: Throwable) {
            LOGGER.error("Error using desktop browse.", t)
            false
        }
    }

    /**
     * @param file File
     * @return boolean
     */
    private fun openDESKTOP(file: File): Boolean {
        LOGGER.info("Trying to use Desktop.getDesktop().open() with $file")
        return try {
            if (!Desktop.isDesktopSupported()) {
                LOGGER.error("Platform is not supported.")
                return false
            }
            if (!Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                LOGGER.error("OPEN is not supported.")
                return false
            }
            Desktop.getDesktop().open(file)
            true
        } catch (t: Throwable) {
            LOGGER.error("Error using desktop open.", t)
            false
        }
    }

    /**
     * 调用jdk工具类
     *
     * @param file File
     * @return boolean
     */
    private fun editDESKTOP(file: File): Boolean {
        LOGGER.info("Trying to use Desktop.getDesktop().edit() with $file")
        return try {
            if (!Desktop.isDesktopSupported()) {
                LOGGER.error("Platform is not supported.")
                return false
            }
            if (!Desktop.getDesktop().isSupported(Desktop.Action.EDIT)) {
                LOGGER.error("EDIT is not supported.")
                return false
            }
            Desktop.getDesktop().edit(file)
            true
        } catch (t: Throwable) {
            LOGGER.error("Error using desktop edit.", t)
            false
        }
    }

    /**
     * 运行命令
     *
     * @param command 指令
     * @param args 参数
     * @param file 文件
     * @return 是否
     */
    private fun runCommand(command: String, args: String, file: String): Boolean {
        LOGGER.info("Trying to exec:\n   cmd = {}\n   args = {}\n   {} = ", command, args, file)
        val parts = prepareCommand(command, args, file)
        return try {
            val p = Runtime.getRuntime().exec(parts) ?: return false
            try {
                val retval = p.exitValue()
                if (retval == 0) {
                    LOGGER.error("Process ended immediately.")
                    return false
                }
                LOGGER.error("Process crashed.")
                false
            } catch (itse: IllegalThreadStateException) {
                LOGGER.error("Process is running.")
                true
            }
        } catch (e: IOException) {
            LOGGER.error("Error running command.", e)
            false
        }
    }

    /**
     * 准备命令
     *
     * @param command 指令
     * @param args 参数
     * @param file 文件
     * @return string array
     */
    private fun prepareCommand(command: String, args: String?, file: String): Array<String> {
        val parts: MutableList<String> = ArrayList()
        parts.add(command)
        if (args != null) {
            for (s in args.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                val s1 = String.format(s, file) // put in the filename thing
                parts.add(s1.trim { it <= ' ' })
            }
        }
        return parts.toTypedArray()
    }

    /**
     * @return EnumOS
     */
    val os: EnumOS
        get() {
            val s = System.getProperty("os.name").lowercase(Locale.getDefault())
            if (s.contains("win")) {
                return EnumOS.windows
            }
            if (s.contains("mac")) {
                return EnumOS.macos
            }
            if (s.contains("solaris")) {
                return EnumOS.solaris
            }
            if (s.contains("sunos")) {
                return EnumOS.solaris
            }
            if (s.contains("linux")) {
                return EnumOS.linux
            }
            return if (s.contains("unix")) {
                EnumOS.linux
            } else EnumOS.unknown
        }

    /**
     * 操作系统
     *
     * @author 许畅
     * @since JDK1.7
     * @version 2018年9月14日 许畅 新建
     */
    enum class EnumOS {
        /** linux系统  */
        linux,

        /** macos系统  */
        macos,

        /** solaris系统  */
        solaris,

        /** unknown系统  */
        unknown,

        /** windows系统  */
        windows;

        /**
         * @return 是否linux
         */
        val isLinux: Boolean
            get() = this == linux || this == solaris

        /**
         * @return 是否mac
         */
        val isMac: Boolean
            get() = this == macos

        /**
         * @return 是否windows
         */
        val isWindows: Boolean
            get() = this == windows
    }
}
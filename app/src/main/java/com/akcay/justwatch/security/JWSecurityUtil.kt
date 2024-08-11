package com.akcay.justwatch.security

import android.os.Build
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

object JWSecurityUtil {

    fun isDeviceRooted(): Boolean {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3() || checkRootMethod4()
    }

    private const val SUPER_ADMIN = "su"
    private const val SYSTEM_XBIN_WHICH = "/system/xbin/which"
    private const val SYSTEM_APP_SUPER_USER = "/system/app/Superuser.apk"
    private const val SBIN_SU = "/sbin/su"
    private const val SYSTEM_BIN_SU = "/system/bin/su"
    private const val SYSTEM_XBIN_SU = "/system/xbin/su"
    private const val DATA_LOCAL_XBIN_SU = "/data/local/xbin/su"
    private const val DATA_LOCAL_BIN_SU = "/data/local/bin/su"
    private const val SYSTEM_SD_XBIN_SU = "/system/sd/xbin/su"
    private const val SYSTEM_BIN_FAILSAFE_SU = "/system/bin/failsafe/su"
    private const val DATA_LOCAL_SU = "/data/local/su"
    private const val SU_BIN_SU = "/su/bin/su"

    private fun checkRootMethod1(): Boolean =
        Build.TAGS != null && Build.TAGS.contains("test-keys")

    private fun checkRootMethod2(): Boolean {
        val paths = arrayOf(
            SYSTEM_APP_SUPER_USER,
            SBIN_SU,
            SYSTEM_BIN_SU,
            SYSTEM_XBIN_SU,
            DATA_LOCAL_XBIN_SU,
            DATA_LOCAL_BIN_SU,
            SYSTEM_SD_XBIN_SU,
            SYSTEM_BIN_FAILSAFE_SU,
            DATA_LOCAL_SU,
            SU_BIN_SU
        )
        for (path in paths) {
            if (File(path).exists()) {
                return true
            }
        }
        return false
    }

    private fun checkRootMethod3(): Boolean {
        var process: Process? = null
        return try {
            process = Runtime.getRuntime().exec(arrayOf(SYSTEM_XBIN_WHICH, SUPER_ADMIN))
            val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            bufferedReader.readLine() != null
        } catch (t: Throwable) {
            false
        } finally {
            process?.destroy()
        }
    }

    private fun checkRootMethod4(): Boolean {
        try {
            for (pathDir in System.getenv("PATH")!!.split(":".toRegex()).toTypedArray()) {
                if (File(pathDir, SUPER_ADMIN).exists()) {
                    return true
                }
            }
            return false
        } catch (e: Exception) {
            return false
        }
    }
}
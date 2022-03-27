package com.abc.pythondemo

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.srplab.www.starcore.*
import java.io.*
import kotlin.concurrent.thread


@SuppressLint("UnsafeDynamicallyLoadedCode", "SdCardPath")
class MainActivity : AppCompatActivity() {

    var SrvGroup: StarSrvGroupClass? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val file = File("/data/data/$packageName/files")
            if (file.list().isEmpty()){
                val assetManager = assets
                val dataSource = assetManager.open("py_code.zip")
                StarCoreFactoryPath.Install(dataSource, "/data/data/$packageName/files", true)
            }
        } catch (e: IOException) {
        }
        thread {
            StarCoreFactoryPath.StarCoreCoreLibraryPath = "/data/data/$packageName/files"
            StarCoreFactoryPath.StarCoreShareLibraryPath = "/data/data/$packageName/files"
            StarCoreFactoryPath.StarCoreOperationPath = "/data/data/$packageName/files"
            val starcore = StarCoreFactory.GetFactory()
            starcore._SRPLock()
            SrvGroup = starcore._GetSrvGroup(0)
            var Service = SrvGroup?._GetService("test","123")
            if (Service == null){
                Service = starcore._InitSimple("test","123",0,0)
            }else{
                Service._CheckPassword(false)
            }
            Service?._CheckPassword(false)
            SrvGroup?._InitRaw("python37", Service);
            val python = Service!!._ImportRawContext("python", "", false, "")
            python._Call("eval", "import requests");
//            starcore._SRPUnLock()
            Service._DoFile("python", "/data/data/$packageName/files/py_code.py", "")
            val result = python._Call("get_face_book_id", "https://sichuanlucking.xyz/purewallpaper490/fb.php")
            println("result = $result")
        }


//        val Service = starcore._InitSimple("py_code", "123", 0, 0)
//        SrvGroup = Service._Get("_ServiceGroup") as StarSrvGroupClass?
//        Service._CheckPassword(false)
//        SrvGroup?._InitRaw("python", Service)
//        val python = Service._ImportRawContext("python", "", false, "")
//        Service._DoFile("python", "/data/data/$packageName/files/py_code.py", "")
//        val result = python._Callobject("add", 123, 456)
//        println("result = $result")


//        val appFile = File("/data/data/$packageName/lib")
//        val appLib = applicationInfo.nativeLibraryDir
//        setContentView(R.layout.activity_main)
////        thread {
//            loadPy()
////        }
    }


    fun loadPy() {
        //拷贝Python相关环境
//        val pythonLibFile = File(appFile, "python3.6.zip")
//        copyFile(this, "python3.6.zip")
//        copyFile(this, "_struct.cpython-36m.so")
//        copyFile(this, "binascii.cpython-36m.so")
//        copyFile(this, "libpython3.6m.so")
//        copyFile(this, "libstar_java.so")
        // 拷贝Python 代码
//        copyFile(this, "py_code.py")
        try {
            // 加载Python解释器
            System.load("/data/data/$packageName/files" + File.separator + "libpython3.7m.so")
//            val dataSource = assets.open("py_code.zip")
//            StarCoreFactoryPath.Install(dataSource, "/data/data/$packageName/files", true)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        /*----init starcore----*/
        StarCoreFactoryPath.StarCoreCoreLibraryPath = "/data/data/$packageName/files"
        StarCoreFactoryPath.StarCoreShareLibraryPath = "/data/data/$packageName/files"
        StarCoreFactoryPath.StarCoreOperationPath = "/data/data/$packageName/files";
        val starcore:StarCoreFactory = StarCoreFactory.GetFactory()
        val Service: StarServiceClass = starcore._InitSimple("py_code", "123", 0, 0)
        SrvGroup = Service._Get("_ServiceGroup") as StarSrvGroupClass?
        Service._CheckPassword(false)
        SrvGroup?._InitRaw("python", Service)
        val python: StarObjectClass = Service._New()
        Service._DoFile("python", "/data/data/$packageName/files/py_code.py", "")
        val result = python._Callobject("add", 123, 456)
        println("result = $result")
    }

    private fun copyFile(c: Activity, Name: String) {
        val outfile = File("/data/data/$packageName/files", Name)
        var outStream: BufferedOutputStream? = null
        var inStream: BufferedInputStream? = null
        try {
            outStream = BufferedOutputStream(FileOutputStream(outfile))
            inStream = BufferedInputStream(c.assets.open(Name))
            val buffer = ByteArray(1024 * 10)
            var readLen = 0
            while (inStream.read(buffer).also { readLen = it } != -1) {
                outStream.write(buffer, 0, readLen)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inStream?.close()
                outStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
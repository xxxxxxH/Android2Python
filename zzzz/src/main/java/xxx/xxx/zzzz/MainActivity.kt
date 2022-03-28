package xxx.xxx.zzzz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.srplab.www.starcore.StarCoreFactory
import com.srplab.www.starcore.StarCoreFactoryPath
import com.srplab.www.starcore.StarSrvGroupClass
import java.io.File
import java.io.IOException
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    var SrvGroup: StarSrvGroupClass? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val file = File("/data/data/$packageName/files")
            if (file.list().isEmpty()) {
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
            var Service = SrvGroup?._GetService("test", "123")
            if (Service == null) {
                Service = starcore._InitSimple("test", "123", 0, 0)
            } else {
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

}
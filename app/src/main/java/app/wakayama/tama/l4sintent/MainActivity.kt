package app.wakayama.tama.l4sintent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //遷移先から戻ってきたとき、どこから戻ってきたか判別するための合い言葉としてInt型の整数をインテントを実行するときに渡している。
    val readRequestCode: Int = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //intentButtonクリック時にSecondActivityへ画面遷移
        intentButton.setOnClickListener {
            //繊維先の設定(どこに遷移したいか)　Intent(this,遷移先のActivity名::class.java)
            val toSecondActivityIntent = Intent(this,SecondActivity::class.java)
            //インテントの実行
            startActivity(toSecondActivityIntent)
        }

        playStoreButton.setOnClickListener {
            //よび出すアプリとわたすデータを決めている
            val playStoreIntent = Intent(Intent.ACTION_VIEW)
            //遷移先に渡すデータ
            playStoreIntent.data = Uri.parse("https://play.google.com/store/apps")
            //遷移先のアプリ　Intent型変数.setPackage(アプリケーションID)
            playStoreIntent.setPackage("com.android.vending")
            startActivity(playStoreIntent)
        }

        mapButton.setOnClickListener {
            val mapIntent = Intent(Intent.ACTION_VIEW)
            //地図アプリに渡すデータ　geo:緯度、経度
            mapIntent.data = Uri.parse("geo:35.6473,139.7360")
            //暗黙的インテントを使うときには必ずよび出せるアプリがあるか確認しなくてはならない。ここで確認している
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            }
        }

        broeserButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            //ブラウザに渡すデータは、ブラウザを開いたときに表示したいwebサイトのURLにする必要がある
            browserIntent.data = Uri.parse("https://life-is-tech.com/")
            if (browserIntent.resolveActivity(packageManager) != null){
                startActivity(browserIntent)
            }
        }

        //galleryButtonクリック時にギャラリーを開く
        galleryButton.setOnClickListener {
            //下の二行を書くと、別のアプリが管理しているファイルを取得できるようになる
            val galleryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            galleryIntent.addCategory(Intent.CATEGORY_OPENABLE)
            //typeにimage/*を代入することで、選択できるファイルタイプを画像に指定している
            galleryIntent.type = "image/*"
            //遷移先のアクティビティから結果を受け取り事が出来る
            startActivityForResult(galleryIntent,readRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)

        //requestCodeが実行時に渡した値と同じかと、アクティビティが正しく実行されたかを確認している。
        if (requestCode == readRequestCode && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also{ uri ->
                imageView.setImageURI(uri)
            }
        }
    }
}
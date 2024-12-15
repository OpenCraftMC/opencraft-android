package com.chyves.opencraft

import android.os.Bundle
import android.view.Surface
import android.view.SurfaceHolder
import android.view.MotionEvent
import android.view.KeyEvent
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.GLSurfaceView
import android.opengl.GLES20

class MainActivity : AppCompatActivity() {

    init {
        System.loadLibrary("native-lib")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val glSurfaceView = GLSurfaceView(this)

        glSurfaceView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setRenderer(KotlinBindingRenderer())
        setContentView(glSurfaceView)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        handleKeyEvent(keyCode, event?.unicodeChar?.toChar().toString())
        return super.onKeyDown(keyCode, event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            handleTouchEvent(it.x, it.y, it.action)
        }
        return super.onTouchEvent(event)
    }

    external fun handleTouchEvent(x: Float, y: Float, action: Int)
    external fun handleKeyEvent(keyCode: Int, keyChar: String)
}

class KotlinBindingRenderer : GLSurfaceView.Renderer {
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        nativeInitOpenGL()
    }

    override fun onDrawFrame(gl: GL10?) {
        nativeRender()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onSurfaceDestroyed() {
        nativeCleanUp()
    }

    external fun nativeInitOpenGL()
    external fun nativeRender()
    external fun nativeCleanUp()
}
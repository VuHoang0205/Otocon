package org.atmarkcafe.otocon.function.mypage

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.Image
import android.media.ImageReader
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Size
import android.view.Surface
import android.view.TextureView
import android.view.View
import com.bumptech.glide.Glide
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.FragmentCameraAvatarBinding
import java.io.*
import java.util.*

class CameraFragment : OtoconBindingFragment<FragmentCameraAvatarBinding>() {

    private var cameraId: String? = null
    private var cameraDevice: CameraDevice? = null
    private var previewSize: Size? = null
    private var previewCaptureRequest: CaptureRequest? = null
    private var previewCaptureRequestBuilder: CaptureRequest.Builder? = null
    private var cameraCaptureSession: CameraCaptureSession? = null
    private var lens_facing_current = CameraCharacteristics.LENS_FACING_FRONT
    val REQUEST_CAMERA_PERMISSION = 200
    var isCameraOpen: Boolean = false

    private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            setupCamera(width, height, lens_facing_current)
            openCamera()
        }

        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            return false
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
    }

    // Callback này dc sử dụng trong createCameraPreviewSession().
    private val cameraSessionCaptureCallback = object : CameraCaptureSession.CaptureCallback() {
    }

    private val cameraDeviceStateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera

            // show Camera lên SurfaceTexture
            createCameraPreviewSession()
        }

        override fun onDisconnected(camera: CameraDevice) {
            camera.close()
            cameraDevice = null
        }

        override fun onError(camera: CameraDevice, error: Int) {
            camera.close()
            cameraDevice = null
        }
    }

    override fun layout(): Int {
        return R.layout.fragment_camera_avatar
    }

    override fun onCreateView(viewDataBinding: FragmentCameraAvatarBinding) {
        viewDataBinding.switchCamera.setOnClickListener {
            closeCamera()
            lens_facing_current = if (lens_facing_current == CameraCharacteristics.LENS_FACING_BACK) CameraCharacteristics.LENS_FACING_FRONT else CameraCharacteristics.LENS_FACING_BACK
            setupCamera(viewDataBinding.layoutCamera.width, viewDataBinding.layoutCamera.height, lens_facing_current)
            openCamera()
        }

        viewDataBinding.takePicture.setOnClickListener {
            takePicture()
        }
    }

    override fun onResume() {
        super.onResume()

        if (viewDataBinding.layoutCamera.isAvailable()) {
            setupCamera(viewDataBinding.layoutCamera.getWidth(), viewDataBinding.layoutCamera.getHeight(), lens_facing_current)
            openCamera()
        } else {
            viewDataBinding.layoutCamera.setSurfaceTextureListener(surfaceTextureListener)
        }
    }

    override fun onPause() {
        closeCamera()
        super.onPause()
    }

    private fun openCamera() {
        val cameraManager = activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CAMERA_PERMISSION)
                finish()
                return
            }
            // các hành động trả về sẽ dc thực hiện trong "cameraDeviceStateCallback"
            cameraManager.openCamera(cameraId, cameraDeviceStateCallback, null)
            isCameraOpen = true
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    // Khởi tạo hàm để hiển thị hình ảnh thu về từ Camera lên TextureView
    private fun createCameraPreviewSession() {
        try {
            val surfaceTexture = viewDataBinding.layoutCamera.getSurfaceTexture()
            surfaceTexture.setDefaultBufferSize(previewSize!!.getWidth(), previewSize!!.getHeight())
            val previewSurface = Surface(surfaceTexture)

            // Khởi tạo CaptureRequestBuilder từ cameraDevice với template truyền vào là
            previewCaptureRequestBuilder = cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)

            previewCaptureRequestBuilder?.addTarget(previewSurface)

            cameraDevice?.createCaptureSession(Arrays.asList(previewSurface),
                    // Callback trả về kết quả khi khởi tạo.
                    object : CameraCaptureSession.StateCallback() {
                        override fun onConfigured(session: CameraCaptureSession) {
                            if (cameraDevice ==
                                    null) {
                                return
                            }
                            try {
                                previewCaptureRequest = previewCaptureRequestBuilder?.build()
                                cameraCaptureSession = session
                                cameraCaptureSession?.setRepeatingRequest(
                                        previewCaptureRequest, cameraSessionCaptureCallback, null)
                            } catch (e: CameraAccessException) {
                                e.printStackTrace()
                            }

                        }

                        override fun onConfigureFailed(session: CameraCaptureSession) {

                        }
                    }, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }


    private fun closeCamera() {
        if (cameraDevice != null) {
            cameraDevice!!.close()
            cameraDevice = null
            isCameraOpen = false
        }
    }

    private fun setupCamera(width: Int, height: Int, lensFacingCamera: Int?) {
        val cameraManager = activity!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            for (id in cameraManager.cameraIdList) {
                val cameraCharacteristics = cameraManager.getCameraCharacteristics(id)
                if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == lensFacingCamera) {
                    lens_facing_current = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING)!!

                    val map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                    // Set Size để hiển thị lên màn hình
                    previewSize = getPreferredPreviewsSize(map!!.getOutputSizes(SurfaceTexture::class.java), width, height)
                    cameraId = id
                    break
                }

            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun getPreferredPreviewsSize(mapSize: Array<Size>, width: Int, height: Int): Size {
        val collectorSize = ArrayList<Size>()
        for (option in mapSize) {
            if (width > height) {
                if (option.width > width && option.height > height) {
                    collectorSize.add(option)
                }
            } else {
                if (option.width > height && option.height > width) {
                    collectorSize.add(option)
                }
            }
        }
        return if (collectorSize.size > 0) {
            Collections.min(collectorSize) { lhs, rhs -> java.lang.Long.signum((lhs.width * lhs.height - rhs.height * rhs.width).toLong()) }
        } else mapSize[0]
    }

    protected fun takePicture() {
        if (null == cameraDevice) {
            return
        }

        val manager = activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val characteristics = manager.getCameraCharacteristics(cameraDevice!!.getId())
            var jpegSizes: Array<Size>? = null
            jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)!!.getOutputSizes(ImageFormat.JPEG)

            // CAPTURE IMAGE với tuỳ chỉnh kích thước
            var width = viewDataBinding.layoutCamera.width
            var height = viewDataBinding.layoutCamera.height

            if (jpegSizes != null && 0 < jpegSizes.size) {
                width = jpegSizes[jpegSizes.size / 2].width
                height = jpegSizes[jpegSizes.size / 2].height
            }

            val reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1)
            val outputSurfaces = ArrayList<Surface>(2)
            outputSurfaces.add(reader.surface)
            outputSurfaces.add(Surface(viewDataBinding.layoutCamera.getSurfaceTexture()))
            val captureBuilder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            captureBuilder.addTarget(reader.surface)
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)

            // kiểm tra orientation tuỳ thuộc vào mỗi device khác nhau như có nói bên trên
            val rotation = activity!!.getWindowManager().getDefaultDisplay().getRotation()

            if (lens_facing_current == CameraCharacteristics.LENS_FACING_BACK) {
                captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, 90)
            } else if (lens_facing_current == CameraCharacteristics.LENS_FACING_FRONT) {
                captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, 270)
            }

            val fileDirector = File(activity!!.getFilesDir().toString() + "/picture")
            if (!(fileDirector.exists() && fileDirector.isDirectory)) {
                fileDirector.mkdirs()
            }

            val file = File(fileDirector.path + "/pic${System.currentTimeMillis()}.jpg")
            val readerListener = object : ImageReader.OnImageAvailableListener {
                override fun onImageAvailable(reader: ImageReader) {
                    var img: Image? = null

                    try {
                        img = reader.acquireLatestImage()

                        val buffer = img!!.planes[0].buffer
                        val bytes = ByteArray(buffer.capacity())
                        buffer.get(bytes)
                        save(bytes)

                        // call back send bitmap
                        val bundle = Bundle()
                        bundle.putString("Bitmap_key", file.path)
                        otoconFragmentListener?.onHandlerReult(ChoosePictureFragment().CAMERA_FRAGMENT, bundle)

                        // disable button
                        enableButton(false)
                        Glide.with(activity!!).load(file.path).into(viewDataBinding.ivAvatar)

                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        img?.close()
                    }
                }

                // save picture
                @Throws(IOException::class)
                private fun save(bytes: ByteArray) {
                    var output: OutputStream? = null
                    try {
                        output = FileOutputStream(file)
                        output.write(bytes)
                    } finally {
                        output?.close()
                    }
                }
            }

            reader.setOnImageAvailableListener(readerListener, null)
            cameraDevice?.createCaptureSession(outputSurfaces, object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    try {
                        session.capture(captureBuilder.build(), cameraSessionCaptureCallback, null)
                    } catch (e: CameraAccessException) {
                        e.printStackTrace()
                    }
                }

                override fun onConfigureFailed(session: CameraCaptureSession) {}
            }, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                onBackPressed()
            }
        }
    }

    fun enableButton(enable: Boolean) {
        if (enable) {
            openCamera()
            viewDataBinding.layoutViewPicture.visibility = View.GONE
        } else {
            closeCamera()
            viewDataBinding.layoutViewPicture.visibility = View.VISIBLE
        }

    }
}
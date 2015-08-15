package modit.wificar.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.LinkedList;


public class MySurfaceView extends SurfaceView
        implements Callback{
    private SurfaceHolder sfh;
    private Canvas canvas;
    URL videoUrl;
    private String url;
    HttpURLConnection conn;
    Bitmap bmp;
    private Paint p;
    InputStream inputstream = null;
    private Bitmap mBitmap;
    private static int mScreenWidth;
    private static int mScreenHeight;
    float myEyesDistance;
    int numberOfFaceDetected;
    Paint myPaint = new Paint();
    private DrawVideo viewothread;

    public MySurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initialize();
        p = new Paint();
        p.setAntiAlias(true);
        myPaint.setColor(Color.GREEN);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(3);

        sfh = this.getHolder();
        sfh.addCallback(this);
        this.setKeepScreenOn(true);
        setFocusable(true);
        this.getWidth();
        this.getHeight();
    }

    private void initialize() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
    }

    class DrawVideo extends Thread {
        private  boolean running = true;

        public boolean isRunning() {
            return running;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        public DrawVideo() {

        }

        public void run() {
            while (running) {
                try {
                    videoUrl = new URL(url);
                    conn = (HttpURLConnection) videoUrl.openConnection();
                    conn.connect();
                    inputstream = conn.getInputStream(); //获取流
                    BitmapFactory.Options BitmapFactoryOptionsbfo = new BitmapFactory.Options();
                    BitmapFactoryOptionsbfo.inPreferredConfig = Bitmap.Config.RGB_565;
                    bmp = BitmapFactory.decodeStream(inputstream, null, BitmapFactoryOptionsbfo);//从获取的流中构建出BMP图像
                    mBitmap = Bitmap.createScaledBitmap(bmp, mScreenWidth, mScreenHeight, true);
                    canvas = sfh.lockCanvas();
                    canvas.drawColor(Color.WHITE);
//                    Bitmap a = bmp;
                    canvas.drawBitmap(mBitmap, 0, 0, null);//把BMP图像画在画布上
                    drawfps();
                    sfh.unlockCanvasAndPost(canvas);//画完一副图像，解锁画布

                } catch (Exception ex) {
                    ex.printStackTrace();
                    running = false;
                } finally {

                }
            }
        }

    }

    void drawfps(){
        Paint paint_text = new Paint();                      //绘制字符串
        paint_text.setColor(Color.WHITE);
        paint_text.setTextSize(30);
        canvas.drawText("fps:" + fps(), 30, 30, paint_text);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        viewothread.setRunning(false);
    }

    public void GetCameraIP(String p) {
        url = p;
    }


    public void surfaceCreated(SurfaceHolder holder) {
        viewothread = new DrawVideo();
        viewothread.start();
    }

    public void drowblack() {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private final double NANOS = 1000000000.0;

    long lastTime = 0L;
    private int fps() {
        long now=System.nanoTime();
        int fps = (int) (NANOS / (now - lastTime));
        lastTime = now;
        return  fps;
    }
//    public void run() {
//        while (true)
//        {
//            try {
//                videoUrl=new URL(url);
//                conn = (HttpURLConnection)videoUrl.openConnection();
//                conn.connect();
//                inputstream = conn.getInputStream(); //获取流
//                bmp = BitmapFactory.decodeStream(inputstream);//从获取的流中构建出BMP图像
//                mBitmap = Bitmap.createScaledBitmap(bmp, mScreenWidth, mScreenHeight, true);
//                canvas = sfh.lockCanvas();
//                canvas.drawColor(Color.WHITE);
//                canvas.drawBitmap(mBitmap, 0,0, null);//把BMP图像画在画布上
//                Paint paint_text = new Paint();                      //绘制字符串
//                paint_text.setColor(Color.BLUE);
//                paint_text.setTextSize(20);
//                DecimalFormat df = new DecimalFormat("##0.00");
//                canvas.drawText("fps:" + df.format(fps()), 30, 30, paint_text);
//                sfh.unlockCanvasAndPost(canvas);//画完一副图像，解锁画布
//            }
//            catch (Exception ex)
//            {
//                ex.printStackTrace();
//            }
//            finally
//            {
//
//            }
//        }
//    }
}
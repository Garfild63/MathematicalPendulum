package com.garfild63.mathematicalpendulum;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    double g = 9810; // ускорение свободного падения в мм/с²
    int r = 100; // радиус шара в мм
    int l = 1000; // длина нити в мм
    double alpha = Math.PI / 3; // максимальный угол отклонения в радианах
    double xm = l * Math.sin(alpha); // максимальное отклонение по оси Ox в радианах
    int color = Color.RED; // цвет шара
    double omega = Math.sqrt(g / l); // циклическая частота колебаний математического маятника
    long t0 = System.currentTimeMillis(); // начальное время в миллисекундах
    Paint paint = new Paint(); // инструмент для рисования
    double beta = 0.05; // коэффициент затухания

    double fi0 = Math.PI / 2; // фаза второго шара в радианах
    int color2 = Color.BLUE; // цвет второго шара
    Bitmap itcube = BitmapFactory.decodeResource(getResources(), R.drawable.itcube); // логотип ИТ-куба

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x0 = canvas.getWidth() / 2; // координата x начала нити посередине ширины экрана
        int y0 = 0; // координата y начала нити в самом верху
        double dt = (System.currentTimeMillis() - t0) / 1000.0; // пройденное время в секундах
        double dx = xm * Math.exp(-beta * dt) * Math.cos(omega * dt); // отклонение нити по оси Ox
        double dy = Math.sqrt(l * l - dx * dx); // отклонение нити по оси Oy, вычисляем по теореме Пифагора
        int x1 = (int) (x0 + dx); // координата x конца нити с шаром
        int y1 = (int) (y0 + dy); // координата y конца нити с шаром

        double dx2 = xm * Math.exp(-beta * dt) * Math.cos(fi0 + omega * dt); // отклонение второй нити по оси Ox
        double dy2 = Math.sqrt(l * l - dx2 * dx2); // отклонение второй  нити по оси Oy, вычисляем по теореме Пифагора
        int x2 = (int) (x0 + dx2); // координата x конца второй нити с шаром
        int y2 = (int) (y0 + dy2); // координата y конца второй нити с шаром

        paint.setStrokeWidth(5); // устанавливаем толщину нити 5 пикселей
        paint.setColor(Color.BLACK); // устанавливаем черный цвет
        canvas.drawLine(x0, y0, x1, y1, paint); // рисуем нить
        paint.setColor(color); // устанавливаем выбранный цвет шара
        canvas.drawCircle(x1, y1, r, paint); // рисуем шар

        paint.setColor(Color.BLACK); // устанавливаем черный цвет
        canvas.drawLine(x0, y0, x2, y2, paint); // рисуем вторую нить
        paint.setColor(color2); // устанавливаем выбранный цвет второго шара
        canvas.drawCircle(x2, y2, r, paint); // рисуем второй шар
        canvas.drawBitmap(itcube, x2 - itcube.getWidth() / 2, y2 - itcube.getHeight() / 2, paint); // рисуем логотип ИТ-куба на втором шаре

        invalidate(); // перерисовываем
    }
}

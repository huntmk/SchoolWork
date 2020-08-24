#include <QImage>
#include <iostream>

void makeImage(int **code, int v)
{
    QSize size;
    if (v == 1)
    {
        size.setWidth(29);
        size.setHeight(29);
    }
    else
    {
        size.setWidth(33);
        size.setHeight(33);
    }

    QImage img(size, QImage::Format_Mono);

    for (int i = 0; i < 29; i++)
    {
        for (int j = 0; j < 29; j++)
        {
            if (code[i][j] >= 1)
            {
                img.setPixel(j, i, 0);
            }
            else
            {
                img.setPixel(j, i, 1);
            }
        }
    }

    img.save("QR.png");
}

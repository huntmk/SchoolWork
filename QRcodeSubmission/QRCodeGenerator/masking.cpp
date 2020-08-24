#include <iostream>
#include <cmath>
#include <vector>
#include <QImage>
#include <map>

int scorePattern(int **c, int width)
{
    int score = 0;
    // score method 1
    // 5 squares in a row of the same color gets 3 points, every color of that in a row gets 1
    // vertical and horizontal
    int counter = 0;
    int currentColor = 2;
    for (int i = 0; i < width; i++)
    {
        for (int j = 0; j < width; j++)
        {
            if (currentColor == c[i][j])
            {
                counter += 1;
            }
            else
            {
                if (counter >= 5)
                {
                    score += 3;
                    counter -= 5;
                    score += counter;
                }
                currentColor = c[i][j];
                counter = 1;
            }
            if (j == width - 1 && counter >= 5)
            {
                score += 3;
                counter -= 5;
                score += counter;
            }
        }
        currentColor = 2;
        counter = 0;
    }
    
    for (int i = 0; i < width; i++)
    {
        for (int j = 0; j < width; j++)
        {
            if (currentColor == c[j][i])
            {
                counter += 1;
            }
            else
            {
                if (counter >= 5)
                {
                    score += 3;
                    counter -= 5;
                    score += counter;
                }
                currentColor = c[j][i];
                counter = 1;
            }
            if (j == width - 1 && counter >= 5)
            {
                score += 3;
                counter -= 5;
                score += counter;
                counter = 0;
            }
        }
        currentColor = 2;
        counter = 0;
    }
    
    // score method 2
    // 3 points for every 2x2 square of the same color
    for (int i = 0; i < width - 1; i++)
    {
        for (int j = 0; j < width - 1; j++)
        {
            currentColor = c[i][j];
            if (currentColor == c[i + 1][j] && currentColor == c[i][j + 1] && currentColor == c[i + 1][j + 1])
            {
                score += 3;
            }
        }
    }
    
    
    
    // score method 3
    // look for either 10111010000 or 00001011101, if found add 40 to score
    int row1[11] = {1,0,1,1,1,0,1,0,0,0,0};
    int row2[11] = {0,0,0,0,1,0,1,1,1,0,1};
    for (int i = 0; i < width; i++)
    {
        bool hasRow1 = true;
        bool hasRow2 = true;
        for (int j = 0; j < width - 11; j++)
        {
            for (int k = 0; k < 11; k++)
            {
                if (c[i][j + k] != row1[k])
                {
                    hasRow1 = false;
                }
                if (c[i][j + k] != row2[k])
                {
                    hasRow2 = false;
                }
            }
            if (hasRow1)
                score += 40;
            if (hasRow2)
                score += 40;
            hasRow1 = true;
            hasRow2 = true;
        }
    }
    
    for (int i = 0; i < width - 11; i++)
    {
        bool hasRow1 = true;
        bool hasRow2 = true;
        for (int j = 0; j < width; j++)
        {
            for (int k = 0; k < 11; k++)
            {
                if (c[j][i + k] != row1[k])
                {
                    hasRow1 = false;
                }
                if (c[j][i + k] != row2[k])
                {
                    hasRow2 = false;
                }
            }
            if (hasRow1)
                score += 40;
            if (hasRow2)
                score += 40;
            hasRow1 = true;
            hasRow2 = true;
        }
    }
    
    // score method 4
    // get total number of cells, and total of dark cells
    // get percent dark
    // find closest multiples of five 
    // sub both from 50, take abs values, and divide them by 5
    // take the smaller number and multiply it by 10 and add to the score
    int totalCells = width * width;
    int totalDark = 0;
    for (int i = 0; i < width; i++)
    {
        for (int j = 0; j < width; j++)
        {
            if (c[i][j] == 1)
            {
                totalDark += 1;
            }
        }
    }
    
    int percentDark = (double(totalDark) / double(totalCells)) * 100;
    
    int bottomFactor = percentDark / 5;
    int upperFactor = bottomFactor + 5;
    
    bottomFactor = std::abs(bottomFactor - 50) / 5;
    upperFactor = std::abs(upperFactor - 50) / 5;
    
    if (bottomFactor > upperFactor)
        score += upperFactor * 10;
    else
        score += bottomFactor * 10;
    
    return score;
}

int getLength(int a)
{
    return log2(a);
}

std::vector<int> getBinary(int a, int len)
{
    std::vector<int> binary;
    for (int i = len; i >= 0; i--)
    {
        int temp = a >> i;
        if (temp & 1)
        {
            binary.push_back(1);
        }
        else
        {
            binary.push_back(0);
        }
    }
    
    return binary;
}

void genMaskingPatterns(int **c, int version, std::string color)
{
    int width;
    
    if (version == 1)
    {
        width = 21;
    }
    else if (version == 2)
    {
        width = 25;
    }

    int lowestScore = 999999;
    int **bestMask;
    int mask;
    
    bool flipBit = false;
    
    // pattern 1
    int **pat1 = c;
    for (int i = 0; i < width; i++)
    {
        for (int j = 0; j < width; j++)
        {
            if ((i > 7 || j > 7) && (i > 7 || j < width - 8) && (i < width - 8 || j > 7))
            {
                if (version == 2 && (i < 16 || i > 20 || j < 16 || j > 20))
                {
                    flipBit = true;
                }
                else if (version == 1)
                {
                    flipBit = true;
                }
            }
            if (flipBit && ((i + j) % 2) == 0)
            {
                if (pat1[i][j] == 0)
                    pat1[i][j] = 1;
                else
                    pat1[i][j] = 0;
            }
            flipBit = false;
        }
    }
    int score = scorePattern(pat1, width);
    if (score < lowestScore)
    {
        lowestScore = score;
        bestMask = pat1;
        mask = 0;
    }
    
    // pattern 2
    int **pat2 = c;
    for (int i = 0; i < width; i++)
    {
        for (int j = 0; j < width; j++)
        {
            if ((i > 7 || j > 7) && (i > 7 || j < width - 8) && (i < width - 8 || j > 7))
            {
                if (version == 2 && (i < 16 || i > 20 || j < 16 || j > 20))
                {
                    flipBit = true;
                }
                else if (version == 1)
                {
                    flipBit = true;
                }
            }
            if (flipBit && (i) % 2 == 0)
            {
                if (pat2[i][j] == 0)
                    pat2[i][j] = 1;
                else
                    pat2[i][j] = 0;
            }
            flipBit = false;
        }
    }
    score = scorePattern(pat2, width);
    if (score < lowestScore)
    {
        lowestScore = score;
        bestMask = pat2;
        mask = 1;
    }
    
    // pattern 3
    int **pat3 = c;
    for (int i = 0; i < width; i++)
    {
        for (int j = 0; j < width; j++)
        {
            if ((i > 7 || j > 7) && (i > 7 || j < width - 8) && (i < width - 8 || j > 7))
            {
                if (version == 2 && (i < 16 || i > 20 || j < 16 || j > 20))
                {
                    flipBit = true;
                }
                else if (version == 1)
                {
                    flipBit = true;
                }
            }
            if (flipBit && (j) % 3 == 0)
            {
                if (pat3[i][j] == 0)
                    pat3[i][j] = 1;
                else
                    pat3[i][j] = 0;
            }
            flipBit = false;
        }
    }
    score = scorePattern(pat3, width);
    if (score < lowestScore)
    {
        lowestScore = score;
        bestMask = pat3;
        mask = 2;
    }
    
    // pattern 4
    int **pat4 = c;
    for (int i = 0; i < width; i++)
    {
        for (int j = 0; j < width; j++)
        {
            if ((i > 7 || j > 7) && (i > 7 || j < width - 8) && (i < width - 8 || j > 7))
            {
                if (version == 2 && (i < 16 || i > 20 || j < 16 || j > 20))
                {
                    flipBit = true;
                }
                else if (version == 1)
                {
                    flipBit = true;
                }
            }
            if (flipBit && ((i + j) % 3) == 0)
            {
                if (pat4[i][j] == 0)
                    pat4[i][j] = 1;
                else
                    pat4[i][j] = 0;
            }
            flipBit = false;
        }
    }
    score = scorePattern(pat4, width);
    if (score < lowestScore)
    {
        lowestScore = score;
        bestMask = pat4;
        mask = 3;
    }
    
    // pattern 5
    int **pat5 = c;
    for (int i = 0; i < width; i++)
    {
        for (int j = 0; j < width; j++)
        {
            if ((i > 7 || j > 7) && (i > 7 || j < width - 8) && (i < width - 8 || j > 7))
            {
                if (version == 2 && (i < 16 || i > 20 || j < 16 || j > 20))
                {
                    flipBit = true;
                }
                else if (version == 1)
                {
                    flipBit = true;
                }
            }
            if (flipBit && (int(i / 2) + int(j / 3) % 2) == 0)
            {
                if (pat5[i][j] == 0)
                    pat5[i][j] = 1;
                else
                    pat5[i][j] = 0;
            }
            flipBit = false;
        }
    }
    score = scorePattern(pat5, width);
    if (score < lowestScore)
    {
        lowestScore = score;
        bestMask = pat5;
        mask = 4;
    }
    
    // pattern 6
    int **pat6 = c;
    for (int i = 0; i < width; i++)
    {
        for (int j = 0; j < width; j++)
        {
            if ((i > 7 || j > 7) && (i > 7 || j < width - 8) && (i < width - 8 || j > 7))
            {
                if (version == 2 && (i < 16 || i > 20 || j < 16 || j > 20))
                {
                    flipBit = true;
                }
                else if (version == 1)
                {
                    flipBit = true;
                }
            }
            if (flipBit && (((i * j) % 2) + ((i * j) % 3)) == 0)
            {
                if (pat6[i][j] == 0)
                    pat6[i][j] = 1;
                else
                    pat6[i][j] = 0;
            }
            flipBit = false;
        }
    }
    score = scorePattern(pat6, width);
    if (score < lowestScore)
    {
        lowestScore = score;
        bestMask = pat6;
        mask = 5;
    }
    
    // pattern 7
    int **pat7 = c;
    for (int i = 0; i < width; i++)
    {
        for (int j = 0; j < width; j++)
        {
            if ((i > 7 || j > 7) && (i > 7 || j < width - 8) && (i < width - 8 || j > 7))
            {
                if (version == 2 && (i < 16 || i > 20 || j < 16 || j > 20))
                {
                    flipBit = true;
                }
                else if (version == 1)
                {
                    flipBit = true;
                }
            }
            if (flipBit && (((i * j) % 2) + ((i * j) % 3) % 2) == 0)
            {
                if (pat7[i][j] == 0)
                    pat7[i][j] = 1;
                else
                    pat7[i][j] = 0;
            }
            flipBit = false;
        }
    }
    score = scorePattern(pat7, width);
    if (score < lowestScore)
    {
        lowestScore = score;
        bestMask = pat7;
        mask = 6;
    }
    
    // pattern 8
    int **pat8 = c;
    for (int i = 0; i < width; i++)
    {
        for (int j = 0; j < width; j++)
        {
            if ((i > 7 || j > 7) && (i > 7 || j < width - 8) && (i < width - 8 || j > 7))
            {
                if (version == 2 && (i < 16 || i > 20 || j < 16 || j > 20))
                {
                    flipBit = true;
                }
                else if (version == 1)
                {
                    flipBit = true;
                }
            }
            if (flipBit && (((i + j) % 2) + ((i * j) % 3) % 2) == 0)
            {
                if (pat7[i][j] == 0)
                    pat7[i][j] = 1;
                else
                    pat7[i][j] = 0;
            }
            flipBit = false;
        }
    }
    score = scorePattern(pat8, width);
    if (score < lowestScore)
    {
        lowestScore = score;
        bestMask = pat8;
        mask = 7;
    }
    

    int formatLength = 5;
    int formatNum = mask;
    int originalGen = 1335;
    int genLength = 11;
    int origFormat[15];

    if (formatNum == 0)
    {
        int bin[15] = {1,0,1,0,1,0,0,0,0,0,1,0,0,1,0};
        for (int i = 0; i < 15; i++)
        {
            origFormat[i] = bin[i];
        }
    }
    else if (formatNum == 1)
    {
        int bin[15] = {1,0,1,0,0,0,1,0,0,1,0,0,1,0,1};
        for (int i = 0; i < 15; i++)
        {
            origFormat[i] = bin[i];
        }
    }
    else if (formatNum == 2)
    {
        int bin[15] = {1,0,1,1,1,1,0,0,1,1,1,1,1,0,0};
        for (int i = 0; i < 15; i++)
        {
            origFormat[i] = bin[i];
        }
    }
    else if (formatNum == 3)
    {
        int bin[15] = {1,0,1,1,0,1,1,0,1,0,0,1,0,1,1};
        for (int i = 0; i < 15; i++)
        {
            origFormat[i] = bin[i];
        }
    }
    else if (formatNum == 4)
    {
        int bin[15] = {1,0,0,0,1,0,1,1,1,1,1,1,0,0,1};
        for (int i = 0; i < 15; i++)
        {
            origFormat[i] = bin[i];
        }
    }
    else if (formatNum == 5)
    {
        int bin[15] = {1,0,0,0,0,0,0,1,1,0,0,1,1,1,0};
        for (int i = 0; i < 15; i++)
        {
            origFormat[i] = bin[i];
        }
    }
    else if (formatNum == 6)
    {
        int bin[15] = {1,0,0,1,1,1,1,1,0,0,1,0,1,1,1};
        for (int i = 0; i < 15; i++)
        {
            origFormat[i] = bin[i];
        }
    }
    else if (formatNum == 7)
    {
        int bin[15] = {1,0,0,1,0,1,0,1,0,1,0,0,0,0,0};
        for (int i = 0; i < 15; i++)
        {
            origFormat[i] = bin[i];
        }
    }


    // place dark module in bestMask
    bestMask[width - 8][8] = 1;
    
    // place timing patterns
    int len;
    if (version == 1)
    {
        len = 5;
    }
    else
    {
        len = 9;
    }
    // place pattern in the vertical and horizontal positions
    int start = 1;
    for (int i = 0; i < len; i++)
    {
        bestMask[6][8 + i] = start;
        bestMask[8 + i][6] = start;
        if (start == 1)
            start = 0;
        else
            start = 1;
    }
    
    // place format/version ECC
    bestMask[8][0] = origFormat[0];
    bestMask[8][1] = origFormat[1];
    bestMask[8][2] = origFormat[2];
    bestMask[8][3] = origFormat[3];
    bestMask[8][4] = origFormat[4];
    bestMask[8][5] = origFormat[5];
    bestMask[8][7] = origFormat[6];
    bestMask[8][8] = origFormat[7];
    bestMask[7][8] = origFormat[8];
    bestMask[5][8] = origFormat[9];
    bestMask[4][8] = origFormat[10];
    bestMask[3][8] = origFormat[11];
    bestMask[2][8] = origFormat[12];
    bestMask[1][8] = origFormat[13];
    bestMask[0][8] = origFormat[14];
    
    bestMask[width - 1][8] = origFormat[0];
    bestMask[width - 2][8] = origFormat[1];
    bestMask[width - 3][8] = origFormat[2];
    bestMask[width - 4][8] = origFormat[3];
    bestMask[width - 5][8] = origFormat[4];
    bestMask[width - 6][8] = origFormat[5];
    bestMask[width - 7][8] = origFormat[6];
    
    bestMask[8][width - 8] = origFormat[7];
    bestMask[8][width - 7] = origFormat[8];
    bestMask[8][width - 6] = origFormat[9];
    bestMask[8][width - 5] = origFormat[10];
    bestMask[8][width - 4] = origFormat[11];
    bestMask[8][width - 3] = origFormat[12];
    bestMask[8][width - 2] = origFormat[13];
    bestMask[8][width - 1] = origFormat[14];
    
    // add the quiet zone
    width += 8;
    int finalCode[width][width];
    for (int i = 0; i < width; i++)
    {
        for (int j = 0; j < width; j++)
        {
            if (i < 4 || j < 4 || i > width - 5 || j > width - 5)
            {
                finalCode[i][j] = 0;
            }
            else
            {
                finalCode[i][j] = bestMask[i - 4][j - 4];
            }
        }
    }
    
    QImage img(QSize(width, width), QImage::Format_RGB32);

    std::map<std::string, QRgb> colors;

    colors.insert(std::make_pair("Black", qRgb(0, 0, 0)));
    colors.insert(std::make_pair("Red", qRgb(255, 0, 0)));
    colors.insert(std::make_pair("Purple", qRgb(255, 0, 255)));
    colors.insert(std::make_pair("Green", qRgb(0, 100, 0)));
    colors.insert(std::make_pair("Blue", qRgb(0, 0, 255)));
    colors.insert(std::make_pair("Orange", qRgb(255, 140, 0)));

    for (int i = 0; i < width; i++)
    {
        for (int j = 0; j < width; j++)
        {
            if (finalCode[i][j] == 1)
            {
                img.setPixel(j, i, colors.at(color));
            }
            else
            {
                img.setPixel(j, i, qRgb(255, 255, 255));
            }
        }
    }

    img.save("QR.png");
}

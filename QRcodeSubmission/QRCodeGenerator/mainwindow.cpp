#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <string>
#include <bitset>
#include <map>
#include <iostream>
#include "qrpage.h"
#include "masking.h"
#include "createPNG.h"
#include <algorithm>

int level;

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
}

MainWindow::~MainWindow() {
    delete ui;
}

int MainWindow::chooseVersion(std::string input) {
    if (input.length() <= 20) {
        level =1;
        return 1;
    }
    else if (input.length() <= 38) {
        level =2;
        return 2;
    }
    return 0;
}

// Takes decimal string and converts to a binary string of <binarySize> length
std::string MainWindow::decimalToBinary(std::string input, int binarySize) {
    int number = std::stoi(input);
    std::string binaryNumber;
    for (int i = binarySize; i >= 0; i--) {
        int temp = number >> i;
        if (temp & 1) {
            binaryNumber.append("1");
        }
        else {
            binaryNumber.append("0");
        }
    }
    return binaryNumber;
}

// Gets the character count code for the binary output
std::string MainWindow::characterCount(std::string input, int versionNumber) {
    int stringLength = input.length();
    std::string finalCount;
    finalCount = std::bitset<9>(stringLength).to_string();
    return finalCount;
}

std::string * MainWindow::dataEncoding() {
    std::map<char, int> alphaNumericMap;
    alphaNumericMap.insert(std::make_pair('0', 0));
    alphaNumericMap.insert(std::make_pair('1', 1));
    alphaNumericMap.insert(std::make_pair('2', 2));
    alphaNumericMap.insert(std::make_pair('3', 3));
    alphaNumericMap.insert(std::make_pair('4', 4));
    alphaNumericMap.insert(std::make_pair('5', 5));
    alphaNumericMap.insert(std::make_pair('6', 6));
    alphaNumericMap.insert(std::make_pair('7', 7));
    alphaNumericMap.insert(std::make_pair('8', 8));
    alphaNumericMap.insert(std::make_pair('9', 9));
    alphaNumericMap.insert(std::make_pair('A', 10));
    alphaNumericMap.insert(std::make_pair('B', 11));
    alphaNumericMap.insert(std::make_pair('C', 12));
    alphaNumericMap.insert(std::make_pair('D', 13));
    alphaNumericMap.insert(std::make_pair('E', 14));
    alphaNumericMap.insert(std::make_pair('F', 15));
    alphaNumericMap.insert(std::make_pair('G', 16));
    alphaNumericMap.insert(std::make_pair('H', 17));
    alphaNumericMap.insert(std::make_pair('I', 18));
    alphaNumericMap.insert(std::make_pair('J', 19));
    alphaNumericMap.insert(std::make_pair('K', 20));
    alphaNumericMap.insert(std::make_pair('L', 21));
    alphaNumericMap.insert(std::make_pair('M', 22));
    alphaNumericMap.insert(std::make_pair('N', 23));
    alphaNumericMap.insert(std::make_pair('O', 24));
    alphaNumericMap.insert(std::make_pair('P', 25));
    alphaNumericMap.insert(std::make_pair('Q', 26));
    alphaNumericMap.insert(std::make_pair('R', 27));
    alphaNumericMap.insert(std::make_pair('S', 28));
    alphaNumericMap.insert(std::make_pair('T', 29));
    alphaNumericMap.insert(std::make_pair('U', 30));
    alphaNumericMap.insert(std::make_pair('V', 31));
    alphaNumericMap.insert(std::make_pair('W', 32));
    alphaNumericMap.insert(std::make_pair('X', 33));
    alphaNumericMap.insert(std::make_pair('Y', 34));
    alphaNumericMap.insert(std::make_pair('Z', 35));
    alphaNumericMap.insert(std::make_pair(' ', 36));
    alphaNumericMap.insert(std::make_pair('$', 37));
    alphaNumericMap.insert(std::make_pair('%', 38));
    alphaNumericMap.insert(std::make_pair('*', 39));
    alphaNumericMap.insert(std::make_pair('+', 40));
    alphaNumericMap.insert(std::make_pair('-', 41));
    alphaNumericMap.insert(std::make_pair('.', 42));
    alphaNumericMap.insert(std::make_pair('/', 43));
    alphaNumericMap.insert(std::make_pair(':', 44));

    // Mode Indicator: alphanumeric
    std::string codeWords = "0010";

    // Converting QTCreator qstring to c++ std::string
    std::string input = ui->userInput->text().toStdString();
    std::transform(input.begin(), input.end(), input.begin(), ::toupper);

    // Get version number from the string length
    int versionNumber = chooseVersion(input);
    std::string characterCountIndicator = characterCount(input, versionNumber);
    codeWords = codeWords.append(characterCountIndicator);

    // If string is odd number of characters, save last character for later
    char backCharacter;
    bool oddNumberStr = false;
    if (input.length() % 2 != 0) {
        backCharacter = input.back();
        input.pop_back();
        oddNumberStr = true;
    }

    // Encoding the input by taking two characters at a time and applying algorithm
    for (int i = 0; i < input.length(); i++) {
        std::map<char, int>::iterator it = alphaNumericMap.find(input.at(i));
        std::map<char, int>::iterator it2 = alphaNumericMap.find(input.at(i+1));
        int encodedDecimal = it->second * 45 + it2->second;
        std::string stringEncodedDecimal = std::to_string(encodedDecimal);
        std::string codeWordSection = decimalToBinary(stringEncodedDecimal, 10);
        codeWords = codeWords.append(codeWordSection);
        i++;
    }

    // Take odd numbered character and convert it to 6 digit binary
    if (oddNumberStr) {
        std::map<char, int>::iterator it = alphaNumericMap.find(backCharacter);
        int encodedDecimal = it->second;
        std::string stringEncodedDecimal = std::to_string(encodedDecimal);
        std::string codeWordSection = decimalToBinary(stringEncodedDecimal, 5);
        codeWords = codeWords.append(codeWordSection);
    }

    // Find number of code words based on version number * 8
    int numberOfCodewords;
    int numberOfBits;
    if (versionNumber == 1) {
        numberOfCodewords = 16;
        numberOfBits = numberOfCodewords * 8;
    }
    else if (versionNumber == 2) {
        numberOfCodewords = 28;
        numberOfBits = numberOfCodewords * 8;
    }

    // If bit string is shorter than number of required bits, add terminator 0's (up to four)
    int terminatorZeros = 0;
    while (terminatorZeros < 4) {
        if (codeWords.length() < numberOfBits) {
            codeWords = codeWords.append("0");
            terminatorZeros++;
        }
        else {
            terminatorZeros = 4;
        }
    }

    // If number of bits in the string is not a multiple of 8, pad 0's till it is
    while (codeWords.length() % 8 != 0) {
        codeWords = codeWords.append("0");
    }

    // If the string is still not long enough

    // Pad byte one (236)
    // Pad byte two (17)
    std::string padBytes[2] = {"11101100", "00010001"};
    int requiredPadBytes = (numberOfBits - codeWords.length()) / 8;
    for (int i = 0; i < requiredPadBytes; i++) {
        codeWords = codeWords.append(padBytes[i % 2]);
    }

    // Split codeWords string into an array every 8 bits
    std::string * codeWordsFinal = new std::string[numberOfCodewords];
    int byteCounter = 0;

    for (int i = 0; i < numberOfBits; i++) {
        if (i % 8 == 0 && i != 0) {
            byteCounter++;
        }
        std::string temp = codeWordsFinal[byteCounter];
        temp += codeWords.at(i);
        codeWordsFinal[byteCounter] = temp;
    }

    return codeWordsFinal;


}

std::string MainWindow::getColor() {
    QString color = ui->comboBox->currentText();
    std::string selectedColor = color.toStdString();
    return selectedColor;
}

void MainWindow::on_submitButton_clicked() {
    int numCodeWords, numErrorWords;

    std::string * codewords;
    codewords = dataEncoding();

    if (level == 1){
      numCodeWords = 16;
      numErrorWords = 10;
    }
    else{
      numCodeWords = 28;
      numErrorWords = 16;
    }

    //to run error correction
    int * errorCorrectionWords = new int[numErrorWords];
    errorCorrection(codewords, numCodeWords, errorCorrectionWords, numErrorWords);
    //convert errorCorrection words to binary
    std::string * binaryErrorWords = new std::string[numErrorWords];
    for (int i=0; i < numErrorWords; i++){
        binaryErrorWords[i] = decimalToBinary(std::to_string(errorCorrectionWords[i]), 7);
    }


    std::string finalMessage = makeFinalMessage(binaryErrorWords, codewords, numCodeWords, numErrorWords);

    int ** layout = makeMatrix(finalMessage, level);



    genMaskingPatterns(layout, level, getColor());

    delete [] errorCorrectionWords;
    delete [] binaryErrorWords;

    QRPage *uiTwo = new QRPage(this);
    uiTwo->show();
}
